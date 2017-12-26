package com.fintech.modules.platform.common.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.platform.common.dto.ExportTxtDto;
import com.fintech.modules.platform.common.dto.SelectValue;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restclient.http.HttpFactory;
import com.fintech.platform.restclient.http.RestClientConfig;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * <p>导出TxtDEMO</p>
 * @author
 * @2014年12月31日 上午9:44:49
 */
@Controller
@RequestMapping("/exportTxt")
public class ExportTxtController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ExportTxtController.class);
	private String jyptAppId = "ptpt"; // rest服务appId
	private String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);// rest服务地址
	
	/**
	 * <p>导出</p>
	 * @param request
	 * @param response
	 * @param dto
	 * @param dataMsg
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/exportDataByReflect")
	public <T> void exportDataByReflect(HttpServletRequest request,
			HttpServletResponse response, @ModelAttribute DataMsg dataMsg) {
		String json = this.getParameterString("tableJson");
		String fileName = this.getParameterString("targetName");//导出文件名称
		String url = getParameterString("tableUrl");
		JSONObject obj = JSONObject.parseObject(json);
		JSONArray arr = obj.getJSONArray("statuses");
		
		//替换url中的域名为 127.0.0.1，否则服务器中不配置域名则无法导出
        String domain = url.substring(7 , url.indexOf("/", 7));
        if(domain.indexOf(":") > -1){
            domain = domain.substring(0, domain.indexOf(":"));
        }
        url = url.replaceAll(domain, "127.0.0.1");
		
		
		List<ExportTxtDto> listBeans = JSON.parseArray(arr.toString(),ExportTxtDto.class);
		String[] headers = new String[listBeans.size()];
		String[] fileds = new String[listBeans.size()];
		String[] type = new String[listBeans.size()];
		Map<String, SelectValue[]> columnNameOfSelectType = new HashMap<String, SelectValue[]>();
		for (int i = 0; i < listBeans.size(); i++) {
			headers[i] = listBeans.get(i).getDisplay();
			fileds[i] = listBeans.get(i).getCode();
			if("select".equalsIgnoreCase(listBeans.get(i).getType())){//下拉列表类型
				columnNameOfSelectType.put(listBeans.get(i).getCode(), listBeans.get(i).getValue());
			};
		}	
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Map<String, Object> dto = new HashMap<String, Object>();
		Enumeration parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			if ("json".equals(name) || "url".equals(name) || "sName".equals(name) || "fileName".equals(name)) continue;
			String value = request.getParameter(name);
			dto.put(name, value);
		}
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		String responseMsg = null;
		try {
			responseMsg = askUrlByClient(url,
					new TypeReference<ResponseMsg<QueryRespBean>>() {
					}, params, request.getHeader("Cookie"));
		} catch (ClientProtocolException clientProtocolException) {
			logger.error("导出Txt异常", clientProtocolException);
		} catch (IOException ioException) {
			logger.error("导出Txt异常", ioException);
		}
		List<Map> list = resultJsonToListMap(responseMsg,columnNameOfSelectType);
		try {
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-disposition", "attachment;filename="+ new String(fileName.getBytes("gb2312"), "ISO8859-1")+ ".txt");
			BufferedOutputStream buff = null;  
		    StringBuffer buffer = new StringBuffer();  
		    String tab = "  ";  
		    String enter = "\r\n";  
		        ServletOutputStream outSTr = null;  
		        try {  
		            outSTr = response.getOutputStream();// 建立  
		            buff = new BufferedOutputStream(outSTr);  
		            for (int i = 0; i<list.size();i++) {
						Map map = (Map) list.get(i);
						for (int j = 0; j < fileds.length; j++) {
							String fieldName = fileds[j];
							if(map.get(fieldName)==null || "".equalsIgnoreCase(map.get(fieldName).toString())) continue; 
							buffer.append(map.get(fieldName));
							buffer.append(",");
						}
						buffer.append(enter);
					}
		            buff.write(buffer.toString().getBytes("UTF-8"));  
		            buff.flush();  
		            buff.close();  
		        } catch (Exception e) {  
		        	logger.error("导出Txt异常", e);
		        } finally {  
		            try {  
		                buff.close();  
		                outSTr.close();  
		            } catch (Exception e) {  
		                e.printStackTrace();  
		            }  
		        }
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("导出Txt异常", e);
		}
	}
	
	private <T, D> String askUrlByClient(String url,
			TypeReference<ResponseMsg<T>> typeReference, D data, String cookie)
			throws ClientProtocolException, IOException {

		HttpUriRequest httpUriRequest = HttpFactory.getInstance().buildRequest(
				jyptAppId, url, "POST", data);
		CloseableHttpClient httpClient = HttpFactory.getInstance()
				.getHttpClient();
		Header header = new BasicHeader("Cookie", cookie);
		httpUriRequest.setHeader(header);
		CloseableHttpResponse httpResponse = null;
		String result = null;
		int statusCode = 200;
		try {
			httpResponse = httpClient.execute(httpUriRequest);
			statusCode = httpResponse.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				result = EntityUtils.toString(httpResponse.getEntity());
			}
		} catch (Exception e) {
			System.out.println("error!!!___________");
		} finally {
			try {
				httpResponse.close();
			} catch (IOException e) {
			}
		}
		return result;
	}
	
	
	private List<Map> resultJsonToListMap(String result,Map<String, SelectValue[]> columnNameOfSelectType) {
		JSONObject dataObj = JSONObject.parseObject(result);
		JSONArray dataArr = dataObj.getJSONArray("data");
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < dataArr.size(); i++) {
			JSONObject jsonobject = dataArr.getJSONObject(i);
			for(String dataKey : columnNameOfSelectType.keySet()){  
				SelectValue [] selectValue = columnNameOfSelectType.get(dataKey);
				for (int j = 0; j < selectValue.length; j++) {
					if (selectValue[j].getValue().equalsIgnoreCase(jsonobject.getString(dataKey))) {
						jsonobject.put(dataKey, selectValue[j].getValue());
					}
				}
			}
			Map<String, Object> addMap = new HashMap<String, Object>();
			addMap.putAll(jsonobject);
			list.add(addMap);
		}
		return list;
	}
}
