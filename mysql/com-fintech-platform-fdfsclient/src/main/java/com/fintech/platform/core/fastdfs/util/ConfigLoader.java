package com.fintech.platform.core.fastdfs.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader{
	
  public static String configFile;
  private Properties prop;
  private static ConfigLoader configLoader = null;

  private ConfigLoader()
  {
    this.prop = new Properties();
    try {
    	init(configFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static synchronized ConfigLoader getInstance() {
    if (configLoader == null)
      configLoader = new ConfigLoader();

    return configLoader;
  }

  public String getConfigFile() {
    return this.configFile;
  }

  public void setConfigFile(String configFile) {
    this.configFile = configFile;
  }

  public void init(String configFile) throws IOException {
    InputStream inStream = null;
    try {
      setConfigFile(configFile);
      inStream = super.getClass().getResourceAsStream(
        (getConfigFile() == null) ? "/biz_app.properties" : 
        getConfigFile());
      if(inStream == null) inStream = new FileInputStream(getConfigFile());
      this.prop.load(inStream);
    } catch (IOException e) {
      e.printStackTrace();

      if (inStream == null) return;
      inStream.close();
    }
    finally
    {
      if (inStream != null)
        inStream.close();
    }
  }

  public String getValue(String key) {
    return this.prop.getProperty(key);
  }

  public boolean getBoolean(String key, boolean defaultValue) {
    String szValue = getValue(key);
    if (szValue == null) {
      return defaultValue;
    }

    return ((szValue.equalsIgnoreCase("yes")) || 
      (szValue.equalsIgnoreCase("on")) || 
      (szValue.equalsIgnoreCase("true")) || (szValue.equals("1")));
  }

  public String[] getArray(String key) {
    return getValue(key).split(";");
  }

  public int getInt(String name, int defaultValue) {
    String szValue = getValue(name);
    if (szValue == null) {
      return defaultValue;
    }

    return Integer.parseInt(szValue);
  }

  public int getInt(String name)
  {
    String szValue = getValue(name);
    if (szValue == null) {
      return 0;
    }

    return Integer.parseInt(szValue);
  }

  public static final void main(String[] sarg) throws IOException {
    ConfigLoader configLoader = getInstance();
    configLoader.init("/biz_app.properties");
    System.out.println(configLoader.getArray("tracker_server"));
  }
}