package com.jy.platform.jbpm4.drawJbpmPng;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.jy.platform.core.common.JYLoggerUtil;
/**
 * 
 * @Description: 详细的绘制类
 * @author chen
 * @version 1.0, 
 * @date 2014-5-5 下午03:38:53
 */
public class JpdlModelDrawer {
	private static JpdlModelDrawer drawer = new JpdlModelDrawer();
	 public static final int RECT_OFFSET_X = JpdlModel.RECT_OFFSET_X;   
	  public static final int RECT_OFFSET_Y = JpdlModel.RECT_OFFSET_Y;   
	  public static final int RECT_ROUND = 25;   
	  
	  public static final int DEFAULT_FONT_SIZE = 12;   
	  
	  public static final Color DEFAULT_STROKE_COLOR = Color.decode("#03689A");   
	  public static final Stroke DEFAULT_STROKE = new BasicStroke(2);   
	  
	  public static final Color DEFAULT_LINE_STROKE_COLOR = Color.decode("#808080");   
	  public static final Stroke DEFAULT_LINE_STROKE = new BasicStroke(1);   
	  
	  public static final Color DEFAULT_FILL_COLOR = Color.decode("#F6F7FF");   
	  
	  private final static Map<String, Object> nodeInfos = JpdlModel.getNodeInfos();   
	  
	  private JpdlModelDrawer(){
		  
	  }
	  public static JpdlModelDrawer getInstance(){
		  return drawer;
	  }
	  
	  public BufferedImage draw(JpdlModel jpdlModel) throws IOException {   
	    Rectangle dimension = getCanvasDimension(jpdlModel);   
	    BufferedImage bi = new BufferedImage(dimension.width, dimension.height, BufferedImage.TYPE_INT_ARGB);   
	    Graphics2D g2 = bi.createGraphics();   
	    g2.setColor(Color.WHITE);   
	    g2.fillRect(0, 0, dimension.width, dimension.height);   
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);   
	    Font font = new Font("宋体", Font.PLAIN, DEFAULT_FONT_SIZE);   
	    g2.setFont(font);   
	    Map<String, Node> nodes = jpdlModel.getNodes();   
	    drawNode(nodes, g2, font);   
	    drawTransition(nodes, g2);   
	    return bi;   
	  }   
	  
	  /**
	   * 获得图片的矩形大小  
	   *   
	   * @return  
	   */  
	  private Rectangle getCanvasDimension(JpdlModel jpdlModel) {   
	    Rectangle rectangle = new Rectangle();   
	    Rectangle rect;   
	    for (Node node : jpdlModel.getNodes().values()) {   
	      rect = node.getRectangle();   
	      if (rect.getMaxX() > rectangle.getMaxX()) {   
	        rectangle.width = (int) rect.getMaxX();   
	      }   
	      if (rect.getMaxY() > rectangle.getMaxY()) {   
	        rectangle.height = (int) rect.getMaxY();   
	      }   
	      for (Transition transition : node.getTransitions()) {   
	        List<Point> trace = transition.getLineTrace();   
	        for (Point point : trace) {   
	          if (rectangle.getMaxX() < point.x) {   
	            rectangle.width = point.x;   
	          }   
	          if (rectangle.getMaxY() < point.y) {   
	            rectangle.height = point.y;   
	          }   
	        }   
	      }   
	    }   
	    rectangle.width += 60;   
	    rectangle.height += 20;   
	    return rectangle;   
	  }   
	  
	  /**
	   * @param g2  
	   * @throws IOException  
	   */  
	  private void drawTransition(Map<String, Node> nodes, Graphics2D g2) throws IOException {   
	    g2.setStroke(DEFAULT_LINE_STROKE);   
	    g2.setColor(DEFAULT_LINE_STROKE_COLOR);   
	    for (Node node : nodes.values()) {   
	      for (Transition transition : node.getTransitions()) {   
	        String to = transition.getTo();   
	        Node toNode = nodes.get(to);   
	        List<Point> trace = new LinkedList<Point>(transition.getLineTrace());   
	        int len = trace.size() + 2;   
	        trace.add(0, new Point(node.getCenterX(), node.getCenterY()));   
	      
	        trace.add(new Point(toNode.getCenterX(), toNode.getCenterY()));   
	        int[] xPoints = new int[len];   
	        int[] yPoints = new int[len];   
	        for (int i = 0; i < len; i++) {   
	          xPoints[i] = trace.get(i).x;   
	          yPoints[i] = trace.get(i).y;   
	        }   
	        final int taskGrow = 4;   
	        final int smallGrow = -2;   
	        int grow = 0;   
	        if (nodeInfos.get(node.getType()) != null) {   
	          grow = smallGrow;   
	        } else {   
	          grow = taskGrow;   
	        }   
	        Point p = GeometryUtils.getRectangleLineCrossPoint(node.getRectangle(), new Point(xPoints[1],   
	            yPoints[1]), grow);   
	        if (p != null) {   
	          xPoints[0] = p.x;   
	          yPoints[0] = p.y;   
	        }   
	        if (nodeInfos.get(toNode.getType()) != null) {   
	          grow = smallGrow;   
	        } else {   
	          grow = taskGrow;   
	        }   
	        p = GeometryUtils.getRectangleLineCrossPoint(toNode.getRectangle(), new Point(xPoints[len - 2],   
	            yPoints[len - 2]), grow);   
	        if (p != null) {   
	          xPoints[len - 1] = p.x;   
	          yPoints[len - 1] = p.y;   
	        }   
	        g2.drawPolyline(xPoints, yPoints, len);
	        //绘制箭头
	        this.drawArrow(g2, xPoints[len - 2], yPoints[len - 2], xPoints[len - 1], yPoints[len - 1]);   
	        String label = transition.getLabel();   
	        //绘制路径名称
	        if (label != null && label.length() > 0) {   
	          JYLoggerUtil.info(this.getClass(), "label:"+label);
	          int cx, cy;   
	          if (len % 2 == 0) {   
	            cx = (xPoints[len / 2 - 1] + xPoints[len / 2]) / 2;   
	            cy = (yPoints[len / 2 - 1] + yPoints[len / 2]) / 2;   
	          } else {   
	        	 cx = xPoints[(len -1)/ 2 ] ;   
		         cy = yPoints[(len -1)/ 2 ] ;//- label.length()*4;   
	          }   
	          Point labelPoint = transition.getLabelPosition();   
	          if (labelPoint != null) {   
	            cx += labelPoint.x;   
	            cy += labelPoint.y;   
	          }   
	          cy -= RECT_OFFSET_Y + RECT_OFFSET_Y / 2;   
	          g2.drawString(label, cx, cy-12);   
	          //g2.drawString(label, cx, cy);
	        }   
	      }   
	    }   
	  }   
	  /**
	   * 绘制路径的箭头 →
	   * @param g2
	   * @param x1
	   * @param y1
	   * @param x2
	   * @param y2
	   */
	  private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {   
	    final double len = 8.0;   
	    double slopy = Math.atan2(y2 - y1, x2 - x1);   
	    double cosy = Math.cos(slopy);   
	    double siny = Math.sin(slopy);   
	    int[] xPoints = { 0, x2, 0 };   
	    int[] yPoints = { 0, y2, 0 };   
	    double a = len * siny, b = len * cosy;   
	    double c = len / 2.0 * siny, d = len / 2.0 * cosy;   
	    xPoints[0] = x2 - (int) (b + c);   
	    yPoints[0] = y2 - (int) (a - d);   
	    xPoints[2] = x2 - (int) (b - c);   
	    yPoints[2] = y2 - (int) (d + a);   
	       
	    g2.fillPolygon(xPoints, yPoints, 3);   
	  }   
	  
	  /**
	   * @param g2  
	   * @throws IOException  
	   */  
	  private void drawNode(Map<String, Node> nodes, Graphics2D g2, Font font) throws IOException {   
	    for (Node node : nodes.values()) {   
	      String name = node.getName();   
	     
	      	int x = node.getX();   
	        int y = node.getY();   
	        int w = node.getWitdth();   
	        int h = node.getHeight();   
	        JYLoggerUtil.info(this.getClass(), "name:"+name+":"+nodeInfos.get(node.getType())+",w:"+w+",h:"+h);
	      if (nodeInfos.get(node.getType()) != null) { 
	    	  /*String path = "D:/activities/48/"+node.getType()+".png";
	    	  System.out.println(path);
	    	  InputStream is = new FileInputStream(new File(path));
	    	  BufferedImage bi2 = ImageIO.read(is);  
	    	  */
	    	  
	    	//JpdlModelDrawer.class.getResourceAsStream("/com/platform/components/jbpm/drawJbpmPng/png/" + nodeInfos.get(node.getType()) )
	    	  
	    	InputStream filePng = JpdlModelDrawer.class.getResourceAsStream("png48/" + nodeInfos.get(node.getType()) );
	        BufferedImage bi2 = ImageIO.read(filePng);   
	        g2.drawImage(bi2, x, y,w,h, null);   
	      } else {   
	      
	        g2.setColor(DEFAULT_FILL_COLOR);   
	        g2.fillRoundRect(x, y, w, h, RECT_ROUND, RECT_ROUND);   
	        g2.setColor(DEFAULT_STROKE_COLOR);   
	        g2.setStroke(DEFAULT_STROKE);   
	        g2.drawRoundRect(x, y, w, h, RECT_ROUND, RECT_ROUND);   
	  
	        FontRenderContext frc = g2.getFontRenderContext();   
	        Rectangle2D r2 = font.getStringBounds(name, frc);   
	        int xLabel = (int) (node.getX() + ((node.getWitdth() - r2.getWidth()) / 2));   
	        int yLabel = (int) ((node.getY() + ((node.getHeight() - r2.getHeight()) / 2)) - r2.getY());   
	        g2.setStroke(DEFAULT_LINE_STROKE);   
	        g2.setColor(Color.black);   
	        g2.drawString(name, xLabel, yLabel);   
	      }   
	    }   
	  }   
}

