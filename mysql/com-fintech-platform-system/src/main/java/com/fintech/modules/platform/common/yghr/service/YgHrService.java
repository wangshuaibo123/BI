package com.fintech.modules.platform.common.yghr.service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.fintech.modules.platform.common.dto.HrOrgDTO;
import com.fintech.modules.platform.common.dto.YGhrDTO;
import com.fintech.modules.platform.common.yghr.dao.YgHrDao;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: YgHrService
 * @description: 定义  yg_hr 实现类
 * @author:  Administrator
 */
@Service("com.fintech.modules.platform.common.yghr.service.YgHrService")
public class YgHrService implements Serializable {
	private static final Logger logger =  LoggerFactory.getLogger(YgHrService.class);
    private static final long serialVersionUID = 1L;
    @Autowired
	private SysConfigAPI sysConfig;
	@Autowired
	private YgHrDao dao;

	/**
     * @author Administrator
     * @description: 分页查询 yg_hr列表
     * @date 2017-01-13 17:34:05
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<YGhrDTO> searchYgHrByPaging(Map<String,Object> searchParams) throws Exception {
		List<YGhrDTO> dataList =  dao.searchYgHrByPaging(searchParams);
		return dataList;
	}
	/**
     * @author Administrator
     * @description: 按条件查询yg_hr列表
     * @date 2017-01-13 17:34:05
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<YGhrDTO> searchYgHr(Map<String,Object> searchParams) throws Exception {
	    List<YGhrDTO> dataList = dao.searchYgHr(searchParams);
        return dataList;
    }
	/**
     * @author Administrator
     * @description: 查询yg_hr对象
     * @date 2017-01-13 17:34:05
     * @param id
     * @return
     * @throws
     */ 
	public YGhrDTO queryYgHrByPrimaryKey(String id) throws Exception {
		
		YGhrDTO dto = dao.findYgHrByPrimaryKey(id);
		
		if(dto == null) dto = new YGhrDTO();
		
		return dto;
		
	}

	/**
     * @title: insertYgHr
     * @author Administrator
     * @description: 新增 yg_hr对象
     * @date 2017-01-13 17:34:05
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertYgHr(YGhrDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertYgHr(paramMap);
		
		YGhrDTO resultDto = (YGhrDTO) paramMap.get("dto");
		Long keyId = 1L;
		return keyId;
	}
	public void insertBatchYgHr(List<HrOrgDTO> dataList) throws Exception {
		dao.truncateYgHRTable();
		if(dataList != null && dataList.size() >0){
			for(int i = 0;i < dataList.size();i ++){
				HrOrgDTO hrOrg = dataList.get(i);
				YGhrDTO hrDTO = hrOrg.getHr();
				this.insertYgHr(hrDTO);
			}
		}
	}
	/**
     * @title: updateYgHr
     * @author Administrator
     * @description: 修改 yg_hr对象
     * @date 2017-01-13 17:34:05
     * @param paramMap
     * @return
     * @throws
     */
	public void updateYgHr(YGhrDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateYgHr(paramMap);
	}
	/**
     * @title: deleteYgHrByPrimaryKey
     * @author Administrator
     * @description: 删除 yg_hr,按主键
     * @date 2017-01-13 17:34:05
     * @param paramMap
     * @throws
     */ 
	public void deleteYgHrByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteYgHrByPrimaryKey(paramMap);
	}
	/**
	 * 将临时表数据批量插入至系统用户组织机构
	 */
	public void insertBatchToSys() {
		//
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tableName", "sys_user");
		dao.insertImportYGtoSys(paramMap);
		
		/*paramMap.put("tableName", "sys_org");
		dao.insertImportYGtoSys(paramMap);
		*/
		
		paramMap.put("tableName", "sys_org_user");
		dao.insertImportYGtoSys(paramMap);
	}
	
	public void insertBatchOrgToSys() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("tableName", "sys_org");
		dao.insertImportYGtoSys(paramMap);
		
		paramMap.put("tableName", "sys_org_user");
		dao.insertImportYGtoSys(paramMap);
		//完善组织机构 上下级关系
		dao.updateSysOrgRelation();
	}
	
	public List<HrOrgDTO> parseJson(){
		logger.info("===========parseJson======start");
		List<HrOrgDTO> list = null;
		BufferedReader is = null;
		HttpsURLConnection httpsConn = null;
		try {
			/*String jsonPath = "F:\\data\\all_person.json";
			File file = new File(jsonPath);
			BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));*/
			String hrURL = sysConfig.getValue("YG_HR_URL");
			URL url = new URL(hrURL);
			
			if(hrURL.startsWith("https")){
				SSLContext sc = SSLContext.getInstance("SSL");
				sc.init(null, new TrustManager[] { new TrustAnyTrustManager() },
						new java.security.SecureRandom());
				httpsConn = (HttpsURLConnection) url.openConnection();
				httpsConn.setSSLSocketFactory(sc.getSocketFactory());
				httpsConn.setHostnameVerifier(new TrustAnyHostnameVerifier());
				httpsConn.setDoOutput(true);
				httpsConn.connect();
				
				is = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(),"UTF-8"));
			}else{
				is = new BufferedReader(new InputStreamReader(url.openStream(),"GBK"));
			}
			
			StringBuffer sb = new StringBuffer();
			String str = "";
			while((str = is.readLine()) != null){
				sb.append(str);
			}
			
			String src = sb.toString().replaceAll(" ", "");
			src = src.substring(src.indexOf("([") +1,src.length()-1) ;
			//logger.debug(src);
			//System.out.println(src);
			list = JSONArray.parseArray(src,HrOrgDTO.class);
		} catch (Exception e) {
			logger.error("========json转dto error",e);
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (Exception e) {
				}
			}
			
			if(httpsConn != null){
				httpsConn.disconnect();
			}
		}
		logger.info("===========parseJson======end");
		return list;
	}
	
	public void truncateYgORGTable(){
		dao.truncateYgORGTable();
	}
	private static class TrustAnyTrustManager implements X509TrustManager {
		public void checkClientTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
		public void checkServerTrusted(X509Certificate[] chain, String authType)
				throws CertificateException {
		}
		public void checkClientTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			
		}
		public void checkServerTrusted(
				java.security.cert.X509Certificate[] arg0, String arg1)
				throws java.security.cert.CertificateException {
			// TODO Auto-generated method stub
			
		}
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
	}
	
	private static class TrustAnyHostnameVerifier implements HostnameVerifier {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}

