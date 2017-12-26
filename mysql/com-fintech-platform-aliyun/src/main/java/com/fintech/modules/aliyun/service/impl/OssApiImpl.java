package com.fintech.modules.aliyun.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import net.coobird.thumbnailator.Thumbnails;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.fintech.modules.aliyun.pool.copy.OssConnectConfig;
import com.fintech.modules.aliyun.service.IPdfToImage;
import com.fintech.modules.aliyun.service.OssApi;
import com.fintech.modules.aliyun.util.CommonUtil;
import com.fintech.modules.aliyun.util.CreateOssClient;
import com.fintech.modules.aliyun.util.PicZipParam;

/**
 * 
 * @description 功能描述:
 * @author 作 者: 周志伟
 * @param 参
 *            数: oos接口实现
 * @Createdate 建立日期： 2017年3月14日下午2:57:47
 * @Projectname 项目名称: oss
 * @Packageclass 包及类名: com.pt.modules.platform.service.impl.OssApiImpl
 */
public class OssApiImpl extends OssConnectConfig implements OssApi {
	static Logger log = LoggerFactory.getLogger(OssApiImpl.class);

	/**
	 * 
	 * @description 功能描述:
	 * @author 作 者: 周志伟
	 * @description 功能描述: 上传文件
	 * @Createdate 建立日期： 2017年3月24日下午2:57:47
	 * @Projectname 项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	@Override
	public String upload(byte[] file_buff, String fileExt) {
		String filename = CommonUtil.getUUid() + "." + fileExt;
		String remotePath = super.getViedFile()+ filename;
		OSSClient ossClient = null; // 创建OSSClient实例
		try {
			ossClient = CreateOssClient.getInstance();// 获取连接对象
			ossClient.putObject(super.getBucketName(), filename,
					new ByteArrayInputStream(file_buff));
			log.info("★[上传阿里云文件成功]★★[生成路径]["
					+ remotePath + "]");
		} catch (OSSException oe) {
			log.error("Error Message: 上传失败，获取不到OSSClient连接请求"
					+ oe.getErrorCode());
		} catch (Exception e) {
			log.error("上传到阿里云附件失败");
			e.printStackTrace();
		}
		return remotePath;
	}

	/**
	 * 
	 * @description 功能描述:
	 * @author 作 者: 周志伟
	 * @description 功能描述: 下载文件
	 * @Createdate 建立日期： 2017年3月24日下午2:57:47
	 * @Projectname 项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	@Override
	public byte[] download(String path) throws Exception {
		OSSClient ossClient = null;
		InputStream inputStream = null;
		byte[] filebyte = null;
		String key=path.replace(super.getViedFile(), "");
		try {
			ossClient = CreateOssClient.getInstance();// 获取连接对象
			OSSObject ossObject = ossClient.getObject(getBucketName(),key );
			inputStream = ossObject.getObjectContent();
			filebyte = CommonUtil.input2byte(inputStream);
			log.info("★[阿里云下载文件成功]★文件key[" + key + "]");
		} catch (OSSException oe) {
			log.error("Error Message: 下载失败，key不存在"
					+ oe.getErrorCode());
		} catch (ClientException ce) {
			log.error("下载失败");
			log.error("Error Message: " + ce.getMessage());
		} catch (Throwable e) {
			log.error("下载失败");
			log.error("throws Message: " + e.getMessage());
		} finally {
			if(inputStream!=null){
				inputStream.close();
			}
			if (filebyte != null) {
				return filebyte;
			}
		}
		return null;
	}

	/**
	 * 
	 * @description 功能描述:
	 * @author 作 者: 周志伟
	 * @description 功能描述: 上传文件并且异步生成图片
	 * @Createdate 建立日期： 2017年3月24日下午2:57:47
	 * @Projectname 项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	@Override
	public String uploadPDFAndImg(final byte[] file_buff,
			final IPdfToImage pdfToImage, final String imagExt)
			throws Exception {
		String path = "";
		final String uuid = CommonUtil.getUUid();
		try {
			path = this.uploadImg(file_buff, imagExt, uuid + "." + "pdf");
			if (StringUtils.isNotEmpty(path)) {
				new Thread(new Runnable() {// 异步生成图片并且上传
							public void run() {
								try {
									final String imgfileName = uuid + "_IMAGE"
											+ "." + imagExt;
									byte[] imgfile = pdfToImage
											.pdfToImage(file_buff);// 异步生成图片
									uploadImg(imgfile, imagExt, imgfileName); // 异步上传
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}).start();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	/**
	 * 
	 * @description 功能描述:
	 * @author 作 者: 周志伟
	 * @description 功能描述: 上传原图并且生成缩略图
	 * @Createdate 建立日期： 2017年3月24日下午2:57:47
	 * @Projectname 项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	@SuppressWarnings({ "static-access", "unused" })
	@Override
	public String uploadImgAndZip(byte[] file_buff, String imagExt,
			PicZipParam[] picZipParams) throws Exception {
		byte[] tempByte = null;
		CommonUtil c = new CommonUtil();
		ByteArrayOutputStream baos = null;
		String path = c.getPath();
		OSSClient ossClient = null;
		String uuid = c.getUUid();
		String remotePath = super.getViedFile();
		String imgNamePath = path + uuid + "." + imagExt;
		remotePath = this.uploadImg(file_buff, imagExt, uuid + "." + imagExt);// 1.先上传原图
		try {
			c.wirteDataToFile(imgNamePath, file_buff); // 2.先将文件落地到临时目录
			for (PicZipParam picZipParam : picZipParams) { // 3.循环生成缩略图
				baos = new ByteArrayOutputStream();
				Thumbnails.of(new File[] { new File(imgNamePath) })
						.size(picZipParam.getWidth(), picZipParam.getHeight())
						.keepAspectRatio(picZipParam.isKeepAspectRatio())
						.toOutputStream(baos);
				tempByte = baos.toByteArray();
				String prefix_name = uuid + "_" + picZipParam.getWidth() + "X"
						+ picZipParam.getHeight() + "." + imagExt;
				this.uploadImg(tempByte, imagExt, prefix_name);
			}

		} catch (Exception e) {
			e.getStackTrace();
		} finally {
			if (baos != null) {
				try {
					baos.close();
				} catch (IOException localIOException2) {
				}
			}
			if (path != null) {
				c.deleteDirectory(path);// 删除文件临时目录
				return remotePath;
			}
		}
		return null;
	}

	/**
	 * 
	 * @description 功能描述:
	 * @author 作 者: 周志伟
	 * @description 功能描述: 根据key获取临时url可设置超时时间
	 * @Createdate 建立日期： 2017年3月24日下午2:57:47
	 * @Projectname 项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	@SuppressWarnings({ "null" })
	public String generatePresignedUrl(String key) throws Exception {
		OSSClient ossClient = null; // 创建OSSClient实例
		URL url = null;
		try {
			Date expiration = new Date(new Date().getTime()
					+ super.getExpiration());
			url = ossClient.generatePresignedUrl(super.getBucketName(), key,
					expiration, HttpMethod.GET);
			log.info("★★★★★★★★★[根据key获取临时url]★成功★[Key=" + key + "]★★[URL]★★["
					+ url + "]");
		} catch (OSSException oe) {
			log.error("Error Message: 生成临时url失败，获取不到OSSClient连接请求"
					+ oe.getErrorCode());
		} catch (Exception e) {
			log.error("★★★★★★★★★[根据key获取临时url]★失败★[Key=" + key + "]★★");
			e.getStackTrace();
		} finally {
			if (url != null) {
				return url.toString();
			}
		}
		return null;
	}

	/**
	 * 
	 * @description 功能描述:
	 * @author 作 者: 周志伟
	 * @description 功能描述: 上传文件（自定义文件名称）filename
	 * @Createdate 建立日期： 2017年3月24日下午2:57:47
	 * @Projectname 项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	public String uploadImg(byte[] file_buff, String imagExt, String filename)
			throws Exception {
		OSSClient ossClient = null;
		String pathName = super.getViedFile() + filename;
		try {
			ossClient = CreateOssClient.getInstance();// 获取连接对象
			ossClient.putObject(super.getBucketName(), filename,
					new ByteArrayInputStream(file_buff));
			log.info("★[上传阿里云文件成功]★★[生成路径]["
					+ pathName + "]");
		} catch (OSSException oe) {
			log.error("Error Message: 上传失败，获取不到OSSClient连接请求"
					+ oe.getErrorCode());
		} catch (Exception e) {
			log.error("上传到阿里云附件失败");
			e.printStackTrace();
		}
		return pathName;

	}

}
