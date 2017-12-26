package com.fintech.platform.core.fastdfs.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.fintech.platform.core.fastdfs.IPdfToImage;
import com.fintech.platform.core.fastdfs.common.NameValuePair;
import com.fintech.platform.core.fastdfs.task.SynSingleFileUploadWorker;
import com.fintech.platform.core.fastdfs.util.ConfigLoader;
/**
 * 上传PDF时 额外将PDF转PNG
 * @author Administrator
 *
 */
public class PdfToImage implements IPdfToImage {

	public static void main(String[] args) throws Exception {
		PdfToImage test = new PdfToImage();
		String path ="D:/devtool/apache-tomcat-7.0.47/webapps/fintech-face/WEB-INF/classes/biz_app.properties";
		ConfigLoader.configFile = path;
		SynSingleFileUploadWorker sy = new SynSingleFileUploadWorker(path);
		
		String real_file_name ="C:/generate/YG0005266.pdf";
		File pdfFile = new File(real_file_name);
		String url = real_file_name;
		NameValuePair[] metaList = new NameValuePair[1];
		metaList[0] = new NameValuePair("fileName", url);
		//pdf 转图片后的文件后缀
		String imagExt = "png";
		InputStream input = new FileInputStream(pdfFile);
		byte[] file_buff = new byte[(int) pdfFile.length()];
		input.read(file_buff);
		input.close();
		String pdfPath = sy.uploadPDFAndImg(file_buff, metaList, real_file_name, test,imagExt);
		System.out.println("=======pdfPath:"+pdfPath);
	}
	
	public byte[] pdfToImage(byte[] b) throws Exception {
		byte[] pdfByte = null;
//		InputStream is = new FileInputStream(tempFile);
//		byte[] b =new byte[(int)tempFile.length()];
//		is.read(b);
		
		pdfByte = this.pdfChangeImg(b);
		return pdfByte;
	}
	/**
	 * pdf 转图片 返回 图片二进制
	 * @param pdfByte
	 * @return
	 * @throws IOException
	 */
	private byte[] pdfChangeImg(byte[] pdfByte) throws Exception {
		List<BufferedImage> piclist = new ArrayList<BufferedImage>();
		//TODO 引入相关jar包
		/*<dependency>
        <groupId>org.apache.pdfbox</groupId>
        <artifactId>pdfbox</artifactId>
        <version>2.0.7</version>
        </dependency>*/
		
		PDDocument doc = PDDocument.load(pdfByte);
		PDFRenderer renderer = new PDFRenderer(doc);
		int pageCount = doc.getNumberOfPages();
		//piclist = Lists.newArrayList();
		for (int i = 0; i < pageCount; i++) {
			BufferedImage image = renderer.renderImageWithDPI(i, 296);
			piclist.add(image);
			// ImageIO.write(image, "PNG", new File());
		}
		byte[] img = this.yPic(piclist);
		return img;

	}

	// 将宽度相同的图片，竖向追加在一起
	private byte[] yPic(List<BufferedImage> piclist) throws IOException {// 纵向处理图片
		if (piclist == null || piclist.size() <= 0) {
			return null;
		}
		int height = 0, // 总高度
		width = 0, // 总宽度
		_height = 0, // 临时的高度 , 或保存偏移高度
		__height = 0, // 临时的高度，主要保存每个高度
		picNum = piclist.size();// 图片的数量
		int[] heightArray = new int[picNum]; // 保存每个文件的高度
		BufferedImage buffer = null; // 保存图片流
		List<int[]> imgRGB = new ArrayList<int[]>(); // 保存所有的图片的RGB
		int[] _imgRGB; // 保存一张图片中的RGB数据
		for (int i = 0; i < picNum; i++) {
			buffer = piclist.get(i);
			heightArray[i] = _height = buffer.getHeight();// 图片高度
			if (i == 0) {
				width = buffer.getWidth();// 图片宽度
			}
			height += _height; // 获取总高度
			_imgRGB = new int[width * _height];// 从图片中读取RGB
			_imgRGB = buffer.getRGB(0, 0, width, _height, _imgRGB, 0, width);
			imgRGB.add(_imgRGB);
		}
		_height = 0; // 设置偏移高度为0
		// 生成新图片
		BufferedImage imageResult = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < picNum; i++) {
			__height = heightArray[i];
			if (i != 0)
				_height += __height; // 计算偏移高度
			imageResult.setRGB(0, _height, width, __height, imgRGB.get(i), 0,
					width); // 写入流中
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(imageResult, "png", out);
		byte[] img = out.toByteArray();
		out.close();
		return img;
	}
}
