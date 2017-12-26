package com.fintech.modules.platform.common.ygorg.service;
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
import com.fintech.modules.platform.common.dto.YGorgDTO;
import com.fintech.modules.platform.common.dto.YGorgDTO2;
import com.fintech.modules.platform.common.dto.YGorgJSONDTO;
import com.fintech.modules.platform.common.yghr.dao.YgHrDao;
import com.fintech.modules.platform.common.ygorg.dao.YgOrgDao;
import com.fintech.platform.api.sysconfig.SysConfigAPI;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: YgOrgService
 * @description: 定义  yg_org 实现类
 * @author:  Administrator
 */
@Service("com.fintech.modules.platform.common.ygorg.service.YgOrgService")
public class YgOrgService implements Serializable {
	private static final Logger logger =  LoggerFactory.getLogger(YgOrgService.class);
    private static final long serialVersionUID = 1L;
    @Autowired
	private SysConfigAPI sysConfig;
	@Autowired
	private YgOrgDao dao;
	/**
     * @author Administrator
     * @description: 分页查询 yg_org列表
     * @date 2017-01-13 17:39:40
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<YGorgDTO> searchYgOrgByPaging(Map<String,Object> searchParams) throws Exception {
		List<YGorgDTO> dataList =  dao.searchYgOrgByPaging(searchParams);
		return dataList;
	}
	/**
     * @author Administrator
     * @description: 按条件查询yg_org列表
     * @date 2017-01-13 17:39:40
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<YGorgDTO> searchYgOrg(Map<String,Object> searchParams) throws Exception {
	    List<YGorgDTO> dataList = dao.searchYgOrg(searchParams);
        return dataList;
    }
	/**
     * @author Administrator
     * @description: 查询yg_org对象
     * @date 2017-01-13 17:39:40
     * @param id
     * @return
     * @throws
     */ 
	public YGorgDTO queryYgOrgByPrimaryKey(String id) throws Exception {
		
		YGorgDTO dto = dao.findYgOrgByPrimaryKey(id);
		
		if(dto == null) dto = new YGorgDTO();
		
		return dto;
		
	}

	/**
     * @title: insertYgOrg
     * @author Administrator
     * @description: 新增 yg_org对象
     * @date 2017-01-13 17:39:40
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertYgOrg(YGorgDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertYgOrg(paramMap);
		
		YGorgDTO resultDto = (YGorgDTO) paramMap.get("dto");
		Long keyId = 1L;
		return keyId;
	}
	public void insertBatchYgOrg(List<HrOrgDTO> dataList) throws Exception {
		if(dataList != null && dataList.size() >0){
			for(int i = 0;i < dataList.size();i ++){
				HrOrgDTO hrOrg = dataList.get(i);
				YGorgDTO orgDTO = hrOrg.getOrg();
				this.insertYgOrg(orgDTO);
			}
		}
	}
	/**
     * @title: updateYgOrg
     * @author Administrator
     * @description: 修改 yg_org对象
     * @date 2017-01-13 17:39:40
     * @param paramMap
     * @return
     * @throws
     */
	public void updateYgOrg(YGorgDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateYgOrg(paramMap);
	}
	/**
     * @title: deleteYgOrgByPrimaryKey
     * @author Administrator
     * @description: 删除 yg_org,按主键
     * @date 2017-01-13 17:39:40
     * @param paramMap
     * @throws
     */ 
	public void deleteYgOrgByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteYgOrgByPrimaryKey(paramMap);
	}
	
	public void parseOrgJson(String orgURL,List<YGorgJSONDTO> dataList) {
		String orgURLTemp = sysConfig.getValue("YG_ORG_URL");
		//logger.info("===========parseJson======orgURL=="+orgURL);
		List<YGorgJSONDTO> list= null;
		BufferedReader is = null;
		HttpsURLConnection httpsConn = null;
		try {
			/*String jsonPath = "F:\\data\\all_person.json";
			File file = new File(jsonPath);
			BufferedReader is = new BufferedReader(new InputStreamReader(new FileInputStream(file),"GBK"));*/
			
			URL url = new URL(orgURL);
			
			if(orgURL.startsWith("https")){
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
			//if(src == null || src =="[]") return null;
			//logger.debug(src);
			//System.out.println(src);
			list = JSONArray.parseArray(src,YGorgJSONDTO.class);
			if( list != null)
			dataList.addAll(list);
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
			if( list != null){
				for(int i =0;i < list.size() ;i ++){
					YGorgJSONDTO dto = list.get(i);
					String orgId = dto.getId();
					orgURL = orgURLTemp +orgId;
					this.parseOrgJson(orgURL,dataList);
				}
			}
			
		}
		
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

	public void insertBatchYGorgJSONDTO(List<YGorgJSONDTO> dataList) throws Exception {
		//hrdao.truncateYgORGTable();
		if(dataList != null && dataList.size() >0){
			for(int i = 0;i < dataList.size();i ++){
				YGorgJSONDTO jsonDTO = dataList.get(i);
				YGorgDTO2 orgDTO2 = jsonDTO.getOrg();
				YGorgDTO orgDTO = new YGorgDTO();
				orgDTO.setB01100(orgDTO2.getB0110_0());
				orgDTO.setParentid(orgDTO2.getParentid());
				orgDTO.setFullname(orgDTO2.getFullname());
				orgDTO.setFullpath(orgDTO2.getFullpath());
				orgDTO.setUniqueId(orgDTO2.getUnique_id());
				orgDTO.setCodeitemdesc(orgDTO2.getCodeitemdesc());
				this.insertYgOrg(orgDTO);
			}
		}
		
	}
}

