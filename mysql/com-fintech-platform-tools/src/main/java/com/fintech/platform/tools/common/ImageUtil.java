/*
 * 参考资料
 * http://www.cnblogs.com/myzhijie/p/3333807.html
 * http://blog.csdn.net/it_man/article/details/7555272/
 */
package com.fintech.platform.tools.common;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.color.ColorSpace;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;

/**
 * 图片处理工具类：<br>
 * 功能：缩放图像、切割图像、图像类型转换、彩色转黑白、文字水印、图片水印等
 * @author
 * 参考资料:
 * http://www.cnblogs.com/myzhijie/p/3333807.html
 * http://blog.csdn.net/it_man/article/details/7555272/
 */
public final class ImageUtil {
    /**
     * 几种常见的图片格式
     */
    public static final String IMAGE_TYPE_GIF = "gif";// 图形交换格式
    public static final String IMAGE_TYPE_JPG = "jpg";// 联合照片专家组
    public static final String IMAGE_TYPE_JPEG = "jpeg";// 联合照片专家组
    public static final String IMAGE_TYPE_BMP = "bmp";// 英文Bitmap（位图）的简写，它是Windows操作系统中的标准图像文件格式
    public static final String IMAGE_TYPE_PNG = "png";// 可移植网络图形
    public static final String IMAGE_TYPE_PSD = "psd";// Photoshop的专用格式Photoshop

    /**
     * 缩放图像（按比例缩放）
     * @param srcImage 源图像文件
     * @param result 缩放后的图像
     * @param scale 缩放比例(放大到多少倍、缩小到多少分之一)
     * @param zoom 缩放选择:放大、缩小
     * @throws IOException
     */
	public final static void scale(InputStream srcImage, OutputStream result,
			int scale, Zoom zoom) throws IOException {

		BufferedImage src = ImageIO.read(srcImage); // 读入源文件
		int width = src.getWidth(); // 得到源图宽
		int height = src.getHeight(); // 得到源图高
		if (Zoom.ZOOMIN.equals(zoom)) {
			width = width * scale;
			height = height * scale;
		} else {
			width = width / scale;
			height = height / scale;
		}

		// 创建此图像的缩放版本
		Image image = src.getScaledInstance(width, height, Image.SCALE_DEFAULT);

		BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(image, 0, 0, null); // 绘制缩放后的图
		g.dispose();

		ImageIO.write(tag, "JPEG", result);// 输出到流 // TODO formatName需要和源文件格式一致吗？
	}
    
    /**
     * 缩放图像（按指定的高度和宽度缩放）
     * @param srcImage 源图像文件
     * @param result 缩放后的图像
     * @param height 缩放后的高度
     * @param width 缩放后的宽度
     * @param bb 比例不对时是否需要补白：true为补白; false为不补白;
     * @throws IOException
     */
    public final static void scale(InputStream srcImage, OutputStream result,
    		int height, int width, boolean bb) throws IOException {

            double ratio = 0.0; // 缩放比例
            BufferedImage src = ImageIO.read(srcImage); // 读入源文件
            Image itemp = src.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
            // 计算比例
            if ((src.getHeight() > height) || (src.getWidth() > width)) {
                if (src.getHeight() > src.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / src.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / src.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform
                        .getScaleInstance(ratio, ratio), null);
                itemp = op.filter(src, null);
            }
            if (bb) {//补白
                BufferedImage image = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null))
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                else
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0,
                            itemp.getWidth(null), itemp.getHeight(null),
                            Color.white, null);
                g.dispose();
                itemp = image;
            }

            ImageIO.write((BufferedImage) itemp, "JPEG", result); // 输出到文件流 // TODO formatName需要和源文件格式一致吗？

    }
     
     
    /**
     * 图像切割(按指定起点坐标和宽高切割)
     * @param srcImage 源图像
     * @param result 切片后的图像
     * @param x 目标切片起点坐标X
     * @param y 目标切片起点坐标Y
     * @param width 目标切片宽度
     * @param height 目标切片高度
     */
    public final static void cut(InputStream srcImage, OutputStream result,
			int x, int y, int width, int height) throws IOException {

		// 读取源图像
		BufferedImage bi = ImageIO.read(srcImage);
		int srcWidth = bi.getHeight(); // 源图宽度
		int srcHeight = bi.getWidth(); // 源图高度
		if (srcWidth > 0 && srcHeight > 0) {
			Image image = bi.getScaledInstance(srcWidth, srcHeight,
					Image.SCALE_DEFAULT);
			// 四个参数分别为图像起点坐标和宽高
			// 即: CropImageFilter(int x,int y,int width,int height)
			ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
			Image img = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, width, height, null); // 绘制切割后的图
			g.dispose();

			// 输出为结果
			ImageIO.write(tag, "JPEG", result);
		}

	}


    /**
     * 图像类型转换：GIF->JPG、GIF->PNG、PNG->JPG、PNG->GIF(X)、BMP->PNG
     * @param srcImage 源图像
     * @param formatName 包含格式非正式名称的 String：如JPG、JPEG、GIF等
     * @param destImage 目标图像
     */
    public final static void convert(InputStream srcImage, String formatName, 
    		OutputStream destImage) throws IOException {
    	BufferedImage src = ImageIO.read(srcImage);
    	ImageIO.write(src, formatName, destImage);
    }

    /**
     * 彩色转为黑白
     * @param srcImage 源图像
     * @param destImage 目标图像
     */
    public final static void gray(InputStream srcImage, OutputStream destImage) throws IOException {
        BufferedImage src = ImageIO.read(srcImage);
        
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        
        BufferedImage newImg = new BufferedImage(src.getWidth(), src.getHeight(),
        		BufferedImage.TYPE_3BYTE_BGR);
        
        op.filter(src, newImg);

        ImageIO.write(newImg, "JPEG", destImage);
    }
    
    /**
     * 图片瘦身，经过处理后，显示效果不变，大小减少很多
     * @param srcImage 源图像
     * @param destImage 目标图像
     */
    public final static void slim(InputStream srcImage, OutputStream destImage) throws IOException {
        BufferedImage src = ImageIO.read(srcImage);
        
        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_sRGB);
        ColorConvertOp op = new ColorConvertOp(cs, null);
        
        BufferedImage newImg = new BufferedImage(src.getWidth(), src.getHeight(),
        		BufferedImage.TYPE_3BYTE_BGR);
        
        op.filter(src, newImg);

        ImageIO.write(newImg, "JPEG", destImage);
    }

    /**
     * 给图片添加文字水印
     * @param pressText 水印文字
     * @param srcImage 源图像
     * @param destImage 目标图像
     * @param fontName 水印的字体名称
     * @param fontStyle 水印的字体样式
     * @param color 水印的字体颜色
     * @param fontSize 水印的字体大小
     * @param x 位置修正值。 默认在中间
     * @param y 位置修正值。 默认在中间
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressText(String pressText,
            InputStream srcImage, OutputStream destImage, String fontName,
            int fontStyle, Color color, int fontSize,int x,
            int y, float alpha) throws IOException {

        Image src = ImageIO.read(srcImage);
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage image = new BufferedImage(width, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(src, 0, 0, width, height, null);
        g.setColor(color);
        g.setFont(new Font(fontName, fontStyle, fontSize));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        // 在指定坐标绘制水印文字
        g.drawString(pressText, (width - (getLength(pressText) * fontSize))
                / 2 + x, (height - fontSize) / 2 + y);
        g.dispose();
        ImageIO.write((BufferedImage) image, "JPEG", destImage);// 输出到文件流

    }

    /**
     * 给图片添加图片水印
     * @param pressImg 水印图片
     * @param srcImage 源图像
     * @param destImage 目标图像
     * @param x 位置修正值。 默认在中间
     * @param y 位置修正值。 默认在中间
     * @param alpha 透明度：alpha 必须是范围 [0.0, 1.0] 之内（包含边界值）的一个浮点数字
     */
    public final static void pressImage(InputStream pressImg, InputStream srcImage,
    		OutputStream destImage,
            int x, int y, float alpha) throws IOException {

        Image src = ImageIO.read(srcImage);
        int wideth = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage image = new BufferedImage(wideth, height,
                BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.drawImage(src, 0, 0, wideth, height, null);
        // 水印文件
        Image src_biao = ImageIO.read(pressImg);
        int wideth_biao = src_biao.getWidth(null);
        int height_biao = src_biao.getHeight(null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
                alpha));
        g.drawImage(src_biao, (wideth - wideth_biao) / 2,
                (height - height_biao) / 2, wideth_biao, height_biao, null);
        // 水印文件结束
        g.dispose();
        ImageIO.write((BufferedImage) image,  "JPEG", destImage);

    }
    /**
     * 计算text的长度（一个中文算两个字符）
     * @param text
     * @return
     */
    private final static int getLength(String text) {
        int length = 0;
        for (int i = 0; i < text.length(); i++) {
            if (new String(text.charAt(i) + "").getBytes().length > 1) {
                length += 2;
            } else {
                length += 1;
            }
        }
        return length / 2;
    }

    /**
     * 放大/缩小
     * @author
     *
     */
    public static enum Zoom {
    	/** 放大 */
    	ZOOMIN
    	/** 缩小 */
    	, ZOOMOUT
    }
}
