package com.jy.modules.platform.sysauth.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jy.modules.platform.sysauth.dto.SysResourceDTO;

/**
 * 资源导入功能使用的工具类
 * @author lin
 *
 */
public final class ResourceImportUtil {
	
	private static final Logger logger =  LoggerFactory.getLogger(ResourceImportUtil.class);

	/**
     * 解析URL，返回URL对应JSP的真实路径
     * @param url
     * @return
     */
    /*public static  String parseResourceUrl(String url) throws Exception{
    	Document doc = Jsoup.connect(url).get();
		Elements elements = doc.select("script");
		String jspFile = null; 
		for (int i = 0; i < elements.size(); i++) {
			Element element = (Element)elements.get(i);
			if(element.toString().indexOf("jspPath")!=-1){
				String temp = element.toString().replace("<script type=\"text/javascript\">", "").replace("</script>", "").trim();
				jspFile=temp.split("\"")[1].replace("\\", "/");
				if (logger.isDebugEnabled()) {
					logger.debug("导入资源管理：导入URL对应的JSP真实路径为："+jspFile);
				}
				break;
			}
		}
		return jspFile;
    }*/
    
    /**
     * 解析JSP页面中的shiro标签。返回标签数组
     * @param realJspPath
     * @return
     * @throws Exception
     */
    public static List<SysResourceDTO> parseShiroTag(String realJspPath)throws Exception{
    	List<SysResourceDTO> result = new ArrayList<SysResourceDTO>();
    	BufferedReader br = null;
		FileReader fileReader = null;
		if (realJspPath != null) {
			try {
				File file = new File(realJspPath);
					 fileReader = new FileReader(file);
					 br = new BufferedReader(fileReader);
				        String str = null;
				        while((str = br.readLine()) != null) {
				        	if(str.indexOf("<shiro:hasPermission")>-1){
				        		if (logger.isDebugEnabled()) {
									logger.debug("解析shiro标签的内容："+str.trim());
								}
				        		SysResourceDTO resourceDTO = new SysResourceDTO();
				        	    Pattern p = Pattern.compile("name=[\"|'](.*?)[\"|']");
				                Matcher m = p.matcher(str);
				                while (m.find()) {
				                    String ret = m.group();
				                    if (ret!=null) {
				                    	ret = ret.replace("\"", "").replace("'", "").replace("name=", "");
				                    	final boolean isAdd  = ret.indexOf("add") != -1 ?true:false;
				                    	final boolean isModify  = ret.indexOf("modify") != -1 ?true:false;
				                    	final boolean isDelete  = ret.indexOf("delete") != -1 ?true:false;
				                    	if (isAdd) resourceDTO.setResoureName("新增");
				                    	if (isModify) resourceDTO.setResoureName("修改");
				                    	if (isDelete) resourceDTO.setResoureName("删除");
				                    	resourceDTO.setResoureType("button");
				                    	resourceDTO.setPermission(ret);
				                    	resourceDTO.setValidateState("1");
				                    	resourceDTO.setVersion(1L);
									}
				                }
				                result.add(resourceDTO);
				        	}
				        }
			} catch (Exception e) {
				if (logger.isErrorEnabled()) {
					logger.error("导入资源异常", e);
				}
			}finally{
				 try {
					 br.close();
					 fileReader.close();
				} catch (Exception e) {
					if (logger.isErrorEnabled()) {
						logger.error("导入资源异常", e);
					}
				}
			}
    }
		return result;
    }
	
    
    /**
	 * 通用Http请求接口方法
	 * @param url 接口地址
	 * @param map 参数集合（暂时不用）
	 * @return
	 */
	public static int visitInterface(String url,Map<String,String> map){
		HttpClient httpClient=new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		//超时设置
		httpClient.getParams().setIntParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);
		//连接超时
		httpClient.getParams().setIntParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		//设置参数
		List<NameValuePair> nvps=new ArrayList<NameValuePair>();
		Iterator<String> it=null;
		if(map!=null)
			it=map.keySet().iterator();
		if(it!=null){
			Object key=null;
			while(it.hasNext()){
				key=it.next();
				if(key!=null){
					nvps.add(new BasicNameValuePair(key.toString(),map.get(key.toString())));
					key=null;
				}
			}
		}
		HttpResponse response=null;
		HttpEntity entity=null;
		try {
			//添加参数
			//httpget.setEntity(new UrlEncodedFormEntity(nvps,HTTP.UTF_8));
			response=httpClient.execute(post);
			entity=response.getEntity();
			
			StatusLine line=response.getStatusLine();
			if(line.getStatusCode()==503){
				throw new Exception("服务器可能没有开启");
			}
			
			//返回HTTP头信息
			Header []header=response.getAllHeaders();
			System.out.println("状态码:"+line.getStatusCode());
			for(int i=0;i<header.length;i++){
				logger.debug("头信息:"+header[i]);
			}
			
			if(entity!=null){
				return line.getStatusCode();
			}
		} catch (Exception e) {
			logger.error("模拟访问["+url+"异常]",e);
		}finally{
			try{
				httpClient.getConnectionManager().shutdown();
			}catch(Exception e){
				logger.error("模拟访问["+url+"异常]",e);
			}
		}
		return 404;
		
	}
    
}
