package com.fintech.platform.core.fastdfs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Future;

import org.csource.fastdfs.ClientGlobal;

import com.fintech.platform.core.fastdfs.common.NameValuePair;
import com.fintech.platform.core.fastdfs.task.AsyMultiFastExecutor;
import com.fintech.platform.core.fastdfs.task.FutureWatcher;
import com.fintech.platform.core.fastdfs.task.SimpleFutureObserver;

public class FtpClient
{
  private static String conf_filename;
  private static final String ROOT_PATH = "D:\\verifies\\";
  private static InputStream input = null;
  private static int INIT_BUFFER_SIZE = 512;
  private static String local_filename;

  public static void main(String[] args)
    throws IOException
  {
    FutureWatcher futureWatcher = new FutureWatcher();
    futureWatcher.register(new SimpleFutureObserver());
    System.out.println("java.version=" + System.getProperty("java.version"));
    byte[] read = new byte[INIT_BUFFER_SIZE];
    System.out.println("input config file name:");
    input = System.in;
    int length = input.read(read);
    byte[] content = null;
    if (length != 0) {
      content = new byte[length];
      System.arraycopy(read, 0, content, 0, length);
    }
    String tempInput = new String(content).trim();
    conf_filename = (tempInput.length() == 0) ? "/biz_app.properties" : tempInput;
    System.out.println("input upload file name:");
    length = input.read(read);
    byte[] content1 = null;
    if (length != 0) {
      content1 = new byte[length];
      System.arraycopy(read, 0, content1, 0, length);
    }
    tempInput = new String(content1).trim();
    local_filename = "D:\\verifies\\upload\\" + ((tempInput.length() == 0) ? "" : tempInput);
    try
    {
      long start = System.currentTimeMillis();
      ClientGlobal.init(conf_filename);
      System.out.println("network_timeout=" + ClientGlobal.g_network_timeout + "ms");
      System.out.println("charset=" + ClientGlobal.g_charset);
      File file = new File(local_filename);
      NameValuePair[] metaList = new NameValuePair[1];
      File[] files = null;
      if (file.isDirectory()) {
        files = file.listFiles();
      } else {
        files = new File[1];
        files[0] = file;
      }
      AsyMultiFastExecutor master = new AsyMultiFastExecutor();
      for (int i = 0; i < files.length; ++i) {
        metaList[0] = new NameValuePair("fileName", files[i].getAbsolutePath());
        Future future = master.runUploadTask(files[i].getAbsolutePath(), null, metaList);
        futureWatcher.addFuture(future);
      }
      futureWatcher.clearWatcher();
      System.out.println("end time:" + (System.currentTimeMillis() - start));
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}