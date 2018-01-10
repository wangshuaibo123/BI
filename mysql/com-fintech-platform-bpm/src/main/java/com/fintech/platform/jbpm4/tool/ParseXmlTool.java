package com.fintech.platform.jbpm4.tool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
/**
 * 
 * @description:定义 jbpm4.4 xml文件解析类
 * @author
 * @date:2014年11月8日下午5:49:52
 */
public class ParseXmlTool {
	/**
	 * 自定义xml 文件解析 方法
	 * @param element
	 * @param tagName
	 * @return
	 */
	public static  List<Element> elementsMethod(Element element, String tagName) {
	    if (element == null || !element.hasChildNodes()) {
	      return Collections.emptyList();
	    }

	    List<Element> elements = new ArrayList<Element>();
	    for (Node child = element.getFirstChild(); child != null; child = child.getNextSibling()) {
	      if (child.getNodeType() == Node.ELEMENT_NODE) {
	        Element childElement = (Element) child;
	        String childTagName = childElement.getTagName();

	        if (tagName.equals(childTagName)) elements.add(childElement);
	      }
	    }
	    return elements;
	  }
}
