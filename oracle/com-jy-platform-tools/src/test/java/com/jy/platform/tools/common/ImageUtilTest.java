package com.jy.platform.tools.common;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Font;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.junit.Test;

import com.jy.platform.tools.common.ImageUtil.Zoom;

public class ImageUtilTest {
	
	// 测试用源图片
	private static final String fileName = "D:/temp/Penguins.JPG";
	// 要加的水印
	private static final String logoName = "D:/temp/logo.jpg";
	
	// 处理后输出的新图片
	private static String newName(String s) {
		return fileName.replaceAll(".JPG", s);
	}

	// 测试模板
	abstract class TestTemplate {
		
		public void run(String s) {
			InputStream src = null;
			OutputStream dest = null;
			try {
				src = new FileInputStream(fileName);
				dest = new FileOutputStream(newName(s));
				doInTemplate(src, dest);
			} catch (Exception e) {
				e.printStackTrace();
				fail(e.getMessage());
			} finally {
				if (src != null) {
					try {
						src.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (dest != null) {
					try {
						dest.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		}
		
		public abstract void doInTemplate(InputStream src, OutputStream dest) throws Exception;
	}
	
	// 缩放图像：按比例缩放
	//@Test
	public void testScaleInputStreamOutputStreamIntZoom() {
		new TestTemplate() {
			@Override
			public void doInTemplate(InputStream src, OutputStream dest)  throws Exception {
				// 缩放图像：按比例缩放
	    		ImageUtil.scale(src, dest, 2, Zoom.ZOOMIN);
			}
		}.run("-zoom.jpg");
	}

	// 缩放图像：按高度和宽度缩放
	//@Test
	public void testScaleInputStreamOutputStreamIntIntBoolean() {
		new TestTemplate() {
			@Override
			public void doInTemplate(InputStream src, OutputStream dest)  throws Exception {
		        // 缩放图像：按高度和宽度缩放
		        ImageUtil.scale(src, dest, 200, 200, false);				
			}
		}.run("-zoom200x200.jpg");
	}

	// 切割图像：按指定起点坐标和宽高切割
	//@Test
	public void testCut() {
		new TestTemplate() {
			@Override
			public void doInTemplate(InputStream src, OutputStream dest)  throws Exception {
		        // 切割图像：按指定起点坐标和宽高切割
		        ImageUtil.cut(src, dest, 0, 0, 400, 400);
			}
		}.run("-cut.jpg");
	}

	// 图像类型转换：
	//@Test
	public void testConvert() {
		new TestTemplate() {
			@Override
			public void doInTemplate(InputStream src, OutputStream dest)  throws Exception {
		        // 图像类型转换：
		        ImageUtil.convert(src, "GIF", dest);
			}
		}.run("-convert.gif");
	}

	// 彩色转黑白：
	//@Test
	public void testGray() {
		new TestTemplate() {
			@Override
			public void doInTemplate(InputStream src, OutputStream dest)  throws Exception {
		        // 彩色转黑白：
		        ImageUtil.gray(src, dest);				
			}
		}.run("-gray.jpg");
	}
	
	// 图片瘦身
	//@Test
	public void testSlim() {
		new TestTemplate() {
			@Override
			public void doInTemplate(InputStream src, OutputStream dest)  throws Exception {
				// 图片瘦身
				ImageUtil.slim(src, dest);				
			}
		}.run("-slim.jpg");
	}

	// 给图片添加文字水印：
	//@Test
	public void testPressText() {
		new TestTemplate() {
			@Override
			public void doInTemplate(InputStream src, OutputStream dest)  throws Exception {
		        // 给图片添加文字水印：
		        ImageUtil.pressText("我是水印文字",src, dest,"宋体", Font.BOLD, Color.white, 80, 0, 0, 0.5f);
			}
		}.run("-pressText.jpg");
	}

	// 给图片添加图片水印：
	//@Test
	public void testPressImage() {
		new TestTemplate() {
			@Override
			public void doInTemplate(InputStream src, OutputStream dest)  throws Exception {
		        // 给图片添加图片水印：
		        InputStream pressImg = new FileInputStream(logoName);
		        ImageUtil.pressImage(pressImg, src, dest, 0, 0, 0.5f);
		        pressImg.close();
			}
		}.run("-pressImage.jpg");
	}

}
