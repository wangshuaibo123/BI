package com.fintech.modules.aliyun.util;

import com.aliyun.oss.OSSClient;
import com.fintech.modules.aliyun.pool.copy.OssConnectConfig;
import com.fintech.modules.aliyun.pool.copy.SpringApplicationContext;

/**
 * 
 * @description  功能描述: 
 * @author       作        者: 周志伟
 * @description  功能描述: 单利模式连接池（只创建一次）
 * @Createdate   建立日期： 2017年3月27日下午2:57:47
 * @Projectname  项目名称: oss
 * @Packageclass 包及类名: com.fintech.modules.aliyun.util.CreateOssClient
 */
public class CreateOssClient {
	
	private static OSSClient ossclict = null;
	private CreateOssClient() throws Exception {
	}
	
    @SuppressWarnings("static-access")
	public static OSSClient getInstance() throws Exception {
    	if (ossclict == null) {
    		OssConnectConfig config = (OssConnectConfig) SpringApplicationContext.getBean("ossConnectConfig");
    		ossclict = new  OSSClient(config.getEndpoint(), config.getAccessKeyId(), config.getAccessKeySecret());
		}
		return ossclict;
    }
    

}
