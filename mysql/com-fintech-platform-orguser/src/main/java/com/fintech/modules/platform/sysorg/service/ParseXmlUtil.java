package com.fintech.modules.platform.sysorg.service;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 解析xml
 * @author
 *
 */
@SuppressWarnings("all")
public class ParseXmlUtil {
	
	static Logger logger = LoggerFactory.getLogger(ParseXmlUtil.class);
	private static final String CONFIG_LOCATION ="auth-syn.xml";
	
	/**
	 * 
	 * 查询xml的信息
	 * 
	 */
	public static void search(){
			 try{
			 	SAXReader reader = new SAXReader();
				ClassLoader cl=ParseXmlUtil.class.getClassLoader();
				URL url=cl.getResource(CONFIG_LOCATION);
		        Document d =  reader.read(new File(url.getFile()));
				Element root = d.getRootElement();
				Element serviceElement = root.element("authlist");
				List<Element> list = serviceElement.elements("authinfo");
				for(Element e : list){
					String appid = e.attributeValue("appid");
					String apppwd = e.attributeValue("apppwd");
					String validtime = e.attributeValue("validtime");
					String desc = e.attributeValue("desc");
					logger.info("appid="+appid+",apppwd="+apppwd+",validtime="+validtime+",desc="+desc);
				}
			 }catch(Exception e)
			 {
				 logger.error("解析auth-syn.xml文件出错：", e);
			 }
		 
	}
	
	/**
	 * 
	 * 添加信息到xml里面
	 * 
	 */
	public static void insert(){ 
		  try { 
		   // 创建解析器 
		   SAXReader saxReader = new SAXReader(); 
		   ClassLoader cl=ParseXmlUtil.class.getClassLoader();
		   URL url=cl.getResource(CONFIG_LOCATION);
	       Document d =  saxReader.read(new File(url.getFile()));
		   // 得到根节点元素 
		   Element root = d.getRootElement(); 
		   Element serviceElement = root.element("authlist");
		   Element authinfo=serviceElement.addElement("authinfo");
		   authinfo.addAttribute("appid", "test");
		   authinfo.addAttribute("apppwd", "1234");
		   authinfo.addAttribute("validtime", "2014-12-12");
		   authinfo.addAttribute("desc", "测试使用");
		   // 定义格式 
		   OutputFormat outFormat = new OutputFormat("", true); 
		   // 获取写入流对象并写入文件 
		   XMLWriter xmlWriter = new XMLWriter(new FileWriter(url.getFile()), outFormat); 
		   // 将doc对象的属性写入文件 
		   xmlWriter.write(d); 
		   // 关闭流操作 
		   xmlWriter.close(); 
		  } catch (Exception e) { 
			  logger.error("解析auth-syn.xml文件出错：", e);
		  } 
		} 
	
	
	/**
	 * 
	 * 修改信息
	 * 
	 */
	
	public static void update() { 
		  try { 
			SAXReader reader = new SAXReader();
			ClassLoader cl=ParseXmlUtil.class.getClassLoader();
			URL url=cl.getResource(CONFIG_LOCATION);
		    Document doc =  reader.read(new File(url.getFile()));
		    Element root = doc.getRootElement();
			Element serviceElement = root.element("authlist");
			List<Element> list = serviceElement.elements("authinfo");
			for(Element e : list){
				if("app02".equals(e.attributeValue("appid")))
				{
					e.setAttributeValue("appid", "app02test");
					e.setAttributeValue("apppwd", "4564646");
					e.setAttributeValue("validtime", "2014-02-02");
					e.setAttributeValue("desc", "修改成功了");
				}
			}
		   // 定义格式 
		   OutputFormat outFormat = new OutputFormat("", true); 
		   // 获取写入流对象并写入文件 
		   XMLWriter xmlWriter = new XMLWriter(new FileWriter(url.getFile()), outFormat); 
		   // 将doc对象的属性写入文件 
		   xmlWriter.write(doc); 
		   // 关闭流操作 
		   xmlWriter.close(); 
		  } catch (Exception e) { 
			  logger.error("解析auth-syn.xml文件出错：", e); 
		  } 
		} 

	
	
	
	
	
	
	
	
	
}
