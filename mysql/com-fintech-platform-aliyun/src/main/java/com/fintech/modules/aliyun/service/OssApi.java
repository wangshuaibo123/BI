package com.fintech.modules.aliyun.service;

import com.fintech.modules.aliyun.util.PicZipParam;

public abstract interface OssApi  {
	
	/**
	 * 
	 * @description  功能描述: 
	 * @author       作        者: 周志伟
	 * @description  功能描述: 上传文件
	 * @Createdate   建立日期： 2017年3月24日下午2:57:47
	 * @Projectname  项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	public abstract String upload(byte[] file_buff,String fileExt )throws Exception;
	/**
	 * 
	 * @description  功能描述: 
	 * @author       作        者: 周志伟
	 * @description  功能描述: 下载文件
	 * @Createdate   建立日期： 2017年3月24日下午2:57:47
	 * @Projectname  项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	public abstract byte[]  download(String path) throws Exception;
	/**
	 * 
	 * @description  功能描述: 
	 * @author       作        者: 周志伟
	 * @description  功能描述: 上传文件并且异步生成图片
	 * @Createdate   建立日期： 2017年3月24日下午2:57:47
	 * @Projectname  项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	public abstract String uploadPDFAndImg(byte[] file_buff, IPdfToImage toUtil, String imagExt) throws Exception;
	/**
	 * 
	 * @description  功能描述: 
	 * @author       作        者: 周志伟
	 * @description  功能描述: 上传原图片并且生成缩略图
	 * @Createdate   建立日期： 2017年3月24日下午2:57:47
	 * @Projectname  项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	public String uploadImgAndZip(byte[] file_buff, String imagExt, PicZipParam[] picZipParams)  throws Exception;
	/**
	 * 
	 * @description  功能描述: 
	 * @author       作        者: 周志伟
	 * @description  功能描述: 根据key获取临时url可设置超时时间
	 * @Createdate   建立日期： 2017年3月24日下午2:57:47
	 * @Projectname  项目名称: oss
	 * @Packageclass 包及类名: com.pt.modules.platform.util.CommonUtil
	 */
	public String generatePresignedUrl(String key)  throws Exception;

}
