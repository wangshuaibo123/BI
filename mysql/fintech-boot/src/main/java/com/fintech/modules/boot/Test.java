package com.fintech.modules.boot;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.ObjectMetadata;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.platform.sysconfig.dto.SysConfigDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restclient.http.RestClient;
/**
 * 获取用户组织机构信息
 * @author Administrator
 *
 */
public class Test {
	private static Logger logger = LoggerFactory.getLogger(Test.class);
	
	protected static String endpoint = "http://oss-cn-beijing.aliyuncs.com";
	protected static String accessKeyId = "LTAIsTq6lfmYsdxw";
	protected static String accessKeySecret = "tsorFLKbqcI0uUwF6rP3icsYoGqxZh";
	protected static String bucketName = "yghy-test";
	protected static String key = "datasource";

	public static boolean download(final String endpoint,
			final String accessKeyId, final String accessKeySecret,
			final String bucketName, final String key, final String filePath) {
		try {
			// 创建上传Object的Metadata
			ObjectMetadata meta = new ObjectMetadata();
			// 设置上传内容类型
			meta.setContentType("text/plain");
			// 创建OSSClient实例
			OSSClient ossClient = new OSSClient(endpoint, accessKeyId,
					accessKeySecret);
			// 下载文件
			ossClient.getObject(new GetObjectRequest(bucketName, key),
					new File(filePath));
			// 关闭client
			ossClient.shutdown();
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
		return true;
	}

	public static void doPost(){
		SysConfigDTO dto = new SysConfigDTO();
		DataMsg dataMsg = new DataMsg();
		
		Map<String, Object> searchParams = new HashMap<String, Object>();
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);

		String url = "http://127.0.0.1:8080/"
				+ "/api/platform/sysconfig/SysConfigRest/searchByPageNoCache/v1/";
		ResponseMsg<QueryRespBean<SysConfigDTO>> responseMsg = RestClient
				.doPost("ptpt",
						url,
						params,
						new TypeReference<ResponseMsg<QueryRespBean<SysConfigDTO>>>() {
						});
		List<SysConfigDTO> list = responseMsg.getResponseBody().getResult();

		System.out.println("=============="+list);
	}
	public static void main(String[] args) throws Exception {
		/*byte[] source = "111111aA".getBytes();
		KeyGenerator kgen = KeyGenerator.getInstance("AES");  
        kgen.init(128, new SecureRandom(")O[NB]6,YF}".getBytes()));  
        SecretKey secretKey = kgen.generateKey();
        byte[] raw = secretKey.getEncoded();
        
        showByte(")O[NB]6,YF}".getBytes());
        
        showByte(raw);
        
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//AES/CBC/PKCS5Padding
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		
		byte[] original = cipher.doFinal(source);
		//加密
		String originalStr = new Base64().encodeToString(original);
		
		System.out.println("===original===:"+originalStr);
		showByte(originalStr.getBytes());*/
		//test1();
		
		System.out.println("good");
		/*ILoggerFactory factory = StaticLoggerBinder.getSingleton()
				.getLoggerFactory();
		GenericApplicationListener dd = null;

		String filePath = "D:/oss/a.xml";
		download(endpoint, accessKeyId, accessKeySecret, bucketName, key,
				filePath);
		
		
		doPost();*/
		System.out.println("end");
	}
	
	
	public static void test1()throws Exception{
		byte[] source = "111111aA".getBytes();
		System.out.println("111111aA");
		showByte(source);
		
		byte[] iv = new byte[] { 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0 };
		
		System.out.println("iv:"+iv.toString());
		showByte(iv);
		String str = "vzh6cBpfFVtkdWokroWdOQ..";
		//str="ib4acbQFD4M8MXcNTNI6WA..";
		String encryptStr = str.replace('*', '+').replace('-', '/').replace('.', '=').trim();
		encryptStr="J+xPfz/Dq50rS+uozUolkw==";
		String keyword = ")O[NB]6,YF}     ";
		byte[] raw = keyword.getBytes();
		System.out.println("keyword:"+keyword);
		showByte(raw);
		/*byte[] bytes = new byte[16];
		for(int i =0 ;i < bytes.length;i ++){
			if(i> 10){
				bytes[i]=32;
			}else{
				bytes[i]=raw[i];
			}
			
			System.out.print(bytes[i]+" ");
		}*/
		System.out.println();
		byte[] encrypted = encryptStr.getBytes();//Base64.decodeBase64(encryptStr);
		System.out.println("encryptStr:"+encryptStr);
		showByte(encrypted);
		
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//AES/CBC/PKCS5Padding
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		
		byte[] original = cipher.doFinal(source);
		//加密
		String originalStr = new Base64().encodeToString(original);
		
		System.out.println("===original===:"+originalStr);
		showByte(originalStr.getBytes());
		//解密
		System.out.println("===src==============");
		String src= decrypt(originalStr,keyword);
		System.out.println("===src=============="+src);
	}

	// 解密
    public static String decrypt(String sSrc, String sKey) throws Exception {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new Base64().decode(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original,"utf-8");
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
	
	
	private static void showByte(byte[] ss){
		for(int i =0 ;i < ss.length;i ++){
			System.out.print(ss[i]+" ");
		}
		System.out.println();
	}
}
