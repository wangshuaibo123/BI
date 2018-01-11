package com.jy.platform.jbpm4.drawJbpmPng;

import java.awt.Point;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * @Description: 定义流程图的基本信息
 * @author chen
 * @version 1.0,
 * @date 2014-5-5 上午10:47:12
 */
public class JpdlModel {
	private Map<String, Node> nodes = new LinkedHashMap<String, Node>();
	public static final int RECT_OFFSET_X = -7;
	public static final int RECT_OFFSET_Y = -8;
	public static final int DEFAULT_PIC_SIZE = 48;

	private final static Map<String, Object> nodeInfos = new HashMap<String, Object>();
	static {
		nodeInfos.put("start", "start.png");
		nodeInfos.put("end", "end.png");
		nodeInfos.put("end-cancel", "end-cancel.png");
		nodeInfos.put("end-error", "end-error.png");
		nodeInfos.put("decision", "decision.png");
		nodeInfos.put("fork", "fork.png");
		nodeInfos.put("join", "join.png");
		nodeInfos.put("state", null);
		nodeInfos.put("hql", null);
		nodeInfos.put("sql", null);
		nodeInfos.put("java", null);
		nodeInfos.put("script", null);
		nodeInfos.put("task", null);
		nodeInfos.put("sub-process", null);
		nodeInfos.put("custom", null);
		nodeInfos.put("foreach", "foreach.png");
	}

	public JpdlModel(InputStream is) throws Exception {
		// this(new SAXReader(is).getRootElement());
		SAXReader reader = new SAXReader();
		reader.setEncoding("UTF-8");
		Element rootEl = reader.read(is).getRootElement();
		for (Element el : (List<Element>) rootEl.elements()) {
			String type = el.getQName().getName();
			if (!nodeInfos.containsKey(type)) { // 不是可展示的节点
				continue;
			}
			String name = null;
			if (el.attribute("name") != null) {
				name = el.attributeValue("name");
			}
			String[] location = el.attributeValue("g").split(",");
			int x = Integer.parseInt(location[0]);
			int y = Integer.parseInt(location[1]);
			int w = Integer.parseInt(location[2]);
			int h = Integer.parseInt(location[3]);

			if (nodeInfos.get(type) != null) {
				w = DEFAULT_PIC_SIZE;
				h = DEFAULT_PIC_SIZE;
			} else {
				x -= RECT_OFFSET_X;
				y -= RECT_OFFSET_Y;
				w += (RECT_OFFSET_X + RECT_OFFSET_X);
				h += (RECT_OFFSET_Y + RECT_OFFSET_Y);
			}
			Node node = new Node(name, type, x, y, w, h);
			parserTransition(node, el);
			nodes.put(name, node);
		}
	}

	@SuppressWarnings("unchecked")
	private JpdlModel(Element rootEl) throws Exception {
		for (Element el : (List<Element>) rootEl.elements()) {
			String type = el.getQName().getName();
			if (!nodeInfos.containsKey(type)) { // 不是可展示的节点
				continue;
			}
			String name = null;
			if (el.attribute("name") != null) {
				name = el.attributeValue("name");
			}
			String[] location = el.attributeValue("g").split(",");
			int x = Integer.parseInt(location[0]);
			int y = Integer.parseInt(location[1]);
			int w = Integer.parseInt(location[2]);
			int h = Integer.parseInt(location[3]);

			if (nodeInfos.get(type) != null) {
				w = DEFAULT_PIC_SIZE;
				h = DEFAULT_PIC_SIZE;
			} else {
				x -= RECT_OFFSET_X;
				y -= RECT_OFFSET_Y;
				w += (RECT_OFFSET_X + RECT_OFFSET_X);
				h += (RECT_OFFSET_Y + RECT_OFFSET_Y);
			}
			Node node = new Node(name, type, x, y, w, h);
			parserTransition(node, el);
			nodes.put(name, node);
		}
	}

	@SuppressWarnings("unchecked")
	private void parserTransition(Node node, Element nodeEl) {
		for (Element el : (List<Element>) nodeEl.elements("transition")) {
			String label = el.attributeValue("name");
			String to = el.attributeValue("to");
			Transition transition = new Transition(label, to);
			String g = el.attributeValue("g");
			if (g != null && g.length() > 0) {
				if (g.indexOf(":") < 0) {
					transition.setLabelPosition(getPoint(g));
				} else {
					String[] p = g.split(":");
					transition.setLabelPosition(getPoint(p[1]));
					String[] lines = p[0].split(";");
					for (String line : lines) {
						transition.addLineTrace(getPoint(line));
					}
				}
			}
			node.addTransition(transition);
		}
	}

	private Point getPoint(String exp) {
		if (exp == null || exp.length() == 0) {
			return null;
		}
		String[] p = exp.split(",");
		return new Point(Integer.valueOf(p[0]), Integer.valueOf(p[1]));
	}

	public Map<String, Node> getNodes() {
		return nodes;
	}

	public static Map<String, Object> getNodeInfos() {
		return nodeInfos;
	}
}
