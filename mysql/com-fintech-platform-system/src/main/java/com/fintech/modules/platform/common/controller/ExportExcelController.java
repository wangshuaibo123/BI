package com.fintech.modules.platform.common.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jodd.util.StringUtil;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fintech.modules.platform.common.dto.ExportExcelDto;
import com.fintech.modules.platform.sysorg.service.SysUserService;
import com.fintech.platform.api.org.SessionAPI;
import com.fintech.platform.api.org.UserInfo;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.core.message.QueryRespBean;
import com.fintech.platform.core.message.ResponseMsg;
import com.fintech.platform.restclient.http.ExcelHttpFactory;
import com.fintech.platform.restclient.http.RestClientConfig;
import com.fintech.platform.restservice.web.base.BaseController;
import com.fintech.platform.tools.common.ExcelExport;
import com.fintech.platform.tools.excel.SelectValue;

/**
 * <p>导出excelDEMO</p>
 * <p>没有考虑复合表头的实现</p>
 * @author
 * @2014年11月27日 上午10:28:49
 */
@Controller
@RequestMapping("/exportExcel")
public class ExportExcelController extends BaseController implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(ExportExcelController.class);
	private String jyptAppId = "ptpt"; // rest服务appId
	private String jyptURL = RestClientConfig.getServiceUrl(jyptAppId);// rest服务地址

	@Autowired
	private SysUserService service;
	
    @Autowired
    private SessionAPI sessionAPI;
    
    private int requestIndex = 0;
    private int requestPageSize = 20000;
    private String requestUrl = "";
    private String requestCookie = "";
    private QueryReqBean requestParams = null;
    private Map<String, SelectValue[]> requestComboxColumn = null;
    
    private static Lock fairLock = new ReentrantLock(true);
    private Map<Integer, List> mapList = new HashMap<Integer, List>(); 
    
    public ExportExcelController(){
        
    }
    
    public ExportExcelController(int pageIndex , int bigPageSize , String requestUrl, String requestCookie, QueryReqBean requestParams, Map<String, SelectValue[]> requestComboxColumn, Map<Integer, List> mapList){
        this.requestIndex = pageIndex;
        this.requestPageSize = bigPageSize;
        this.requestUrl = requestUrl;
        this.requestCookie = requestCookie;
        this.requestParams = requestParams;
        this.requestComboxColumn = requestComboxColumn;
        this.mapList = mapList;
    }
    
	/**
	 * <p>导出</p> 待优化
	 * @param request
	 * @param response
	 * @param dto
	 * @param dataMsg
	 * @throws Exception 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/exportDataByReflect")
	public <T> void exportDataByReflect(HttpServletRequest request, HttpServletResponse response, @ModelAttribute DataMsg dataMsg) throws Exception {
		String json = request.getParameter("json");
		String sName = request.getParameter("sName");//shell页名称
		String fileName = request.getParameter("fileName");//导出文件名称
		String isAll = request.getParameter("isAll");
		String isTime = request.getParameter("isTime");   //是否包含时分秒
		String isCombox = request.getParameter("isCombox");
		String maxTotalRows = request.getParameter("maxTotalRows");
		int currentPage = 0;
		int pageSize = 0;
		int totalRows = 0;
        //对导出文件进行区分
        UserInfo user=sessionAPI.getCurrentUserInfo();
        if(user != null) fileName = fileName + "_" + user.getLoginName();
        
        //大数据量时使用(导出所有时使用)
        int bigPageSize = 20000;         //分页标准
        int maxRowsNum = 60000;          //大于多少条时写入文件
        String exportPath = "/tmp";      //文件存放路径(默认路径)
        String domainReplace = "";
        int threadNum = 5;
        int maxTRow = 10000;//默认10000
        String temStr = "";
        //获取配置文件参数
        Properties properties = new Properties();   
        ClassPathResource cp = new ClassPathResource("biz_app.properties");
        try{
        	currentPage = Integer.valueOf(request.getParameter("currentPage"));
            pageSize = Integer.valueOf(request.getParameter("pageSize"));
            totalRows = Integer.valueOf(request.getParameter("totalRows"));
            
            properties.load(cp.getInputStream());
            bigPageSize = Integer.valueOf(properties.getProperty("bigPageSize").trim()); 
            maxRowsNum  = Integer.valueOf(properties.getProperty("maxRowsNum").trim());
            threadNum  = Integer.valueOf(properties.getProperty("threadNum").trim()); 
            exportPath  = properties.getProperty("exportPath").trim();
            domainReplace = properties.getProperty("domainReplace").trim();
            if(StringUtil.isEmpty(maxTotalRows)) 
            	temStr = properties.getProperty("maxTotalRows");
        } catch(Exception e) {
            logger.debug("找不到配置文件：biz_app.properties，使用默认值bigPageSize = 20000, maxRowsNum = 40000, exportPath = /tmp",e);
        }
        //如果传递的值为null 则取配置文件
    	if(!"".equals(temStr)) maxTRow = Integer.valueOf(temStr); 
    	if(!StringUtil.isEmpty(maxTotalRows)) maxTRow = Integer.valueOf(maxTotalRows); 
    	
        //导出大于 最大数据
        if(Integer.valueOf(totalRows) > maxTRow) throw new Exception("超过最大导出限制条数："+maxTRow);
        //当获取不到页面总数和页码时，初始默认值
		if(currentPage < 1) currentPage = 1;
		if(pageSize < 0) pageSize = 10;
		
		String url = getParameterString("url");
		
		//替换url中的域名，否则服务器中不配置域名则无法导出
		if(url.contains("http:") && StringUtil.isNotEmpty(domainReplace) && !"none".equals(domainReplace)){
		    url = url.replace(url.substring(7, url.indexOf("/", 7)), domainReplace);
		}
		
		String defaultName = String.valueOf(System.currentTimeMillis());
		if (sName == null || sName.length() == 0) sName = defaultName;
		if (fileName == null || fileName.length() == 0) fileName = defaultName;
		List<ExportExcelDto> exportObj = parseToExcelDtos(json);
		//头部
		String[] headers = new String[exportObj.size()];
		//字段
		String[] fileds = new String[exportObj.size()];
		//字段类型
		String[] type = new String[exportObj.size()];
		//带下拉列表字段code 与  value的映射
		Map<String, SelectValue[]> comboxColumn = new HashMap<String, SelectValue[]>();
		//日期类型的字段
		List<String>  attributesOfDateType = new ArrayList<String>();
		int index = 0;
		for(ExportExcelDto obj:exportObj){
			if ("false".equalsIgnoreCase(obj.getIsExport())) continue;
			headers[index] = obj.getDisplay();
			fileds[index] = obj.getCode();
			type[index] = obj.getType();
			if ("select".equalsIgnoreCase(obj.getType())) comboxColumn.put(obj.getCode(), obj.getValue());
			if (isDate(obj.getType())) attributesOfDateType.add(obj.getCode());
			index++;
		}
		if (logger.isDebugEnabled()) {
			logger.debug("##################导出EXCEl##########################");
			logger.debug("标题："+Arrays.toString(headers));
			logger.debug("字段："+Arrays.toString(fileds));
			logger.debug("类型："+Arrays.toString(type));
		}
		
		String responseMsg = null;
		
		try {
			QueryReqBean params = buildQueryParams(request,dataMsg);
			
			List<Map> list = null;
            ExcelExport util = new ExcelExport();
            SXSSFWorkbook wb= new SXSSFWorkbook(10000);
            StopWatch watch = new StopWatch();
            if ("true".equalsIgnoreCase(isAll)) {
                
                //导出文件名
                String exportExcelFile = exportPath + fileName + ".xlsx";
                
                logger.debug("excel全部导出，共："+ totalRows + " 条数据");
                //1.1 计算页码
                int totalPage = totalRows / bigPageSize;
                if(totalRows%bigPageSize> 0 || totalRows < 1){
                    totalPage = totalPage + 1;
                }
                //1.2 分页导出
                logger.debug("数据分页，共："+ totalPage + "页");
                
                /*  
                 * 单线程处理           
                 for (int i = 1; i <= totalPage; i++) {
                    watch.start();
                    responseMsg = postForm(getPostUrlOfExportAllData(i,bigPageSize,url), new TypeReference<ResponseMsg<QueryRespBean>>() {} , request.getHeader("Cookie"),  params);
                    list = resultJsonToListMap(responseMsg,comboxColumn);
                    watch.stop();
                    logger.debug("第" + i+ "次处理" + list.size() + "条，查询所耗时间："+watch.getTime());
                    watch.reset();
                    watch.start();
                    if (isExportCombox(isCombox)) {
                        wb = util.exportLargeExcel(sName, headers, list, fileds,attributesOfDateType,comboxColumn, wb , i);
                    }else{
                        wb = util.exportLargeExcel(sName, headers, list, fileds,null,comboxColumn, wb , i);
                    }
                    watch.stop();
                    logger.debug("第" + i + "次处理完成,所耗时间："+watch.getTime());
                    watch.reset();
                }*/
                
                /**
                 * 多线程处理
                 */
                watch.start();
                //线程池
                ExecutorService executor = Executors.newFixedThreadPool(threadNum);
                for (int i = 1; i <= totalPage; i++) {
                    logger.debug("数据查询，第 "+ i + " 页");
                    Runnable exClr = new ExportExcelController( i, bigPageSize, url, (String)request.getHeader("Cookie"), params, comboxColumn, mapList);
                    executor.execute(exClr);
                }
                executor.shutdown();
                while (true) {  
                    if (executor.isTerminated()) {
                        logger.debug("数据查询完毕，开始处理列表");
                        //集中处理查询结果
                        for (int j = 1; j < mapList.size() + 1; j++) {
                            List tempList = (List)mapList.get(j);
                            if (isExportCombox(isCombox)) {
                                wb = util.exportLargeExcel(sName, headers, type, tempList, fileds,attributesOfDateType,comboxColumn, wb , j, isTime);
                            }else{
                                wb = util.exportLargeExcel(sName, headers, type, tempList, fileds,null,comboxColumn, wb , j, isTime);
                            }
                        }
                        break;  
                    }
                    logger.debug("....等待查询结果....");
                    Thread.sleep(5000);  
                }
                //清空列表
                mapList = new HashMap<Integer, List>();
                watch.stop();
                logger.debug("数据处理完成,所耗时间："+watch.getTime());
                /***** 多线程处理结束 ****/
                
                
                //1.3 判断是写入文件，还是直接放入response
                if(totalRows > maxRowsNum){
                    logger.debug("数据过大，写入文件：" + exportExcelFile);
                    //写入文件
                    FileOutputStream out = new FileOutputStream(exportExcelFile);
                    wb.write(out);
                    out.close();
                    wb.dispose();
                    logger.debug("读取Excel文件放入response");
                    //1.3.1 读取文件到response中
                    downloadFile(new File(exportExcelFile), response);
                }else{
                    logger.debug("把excel对象放入response");
                    //1.3.2 excel对象放入reponse
                    write(wb,fileName,response);
                }
                
            }else{
                logger.debug("excel导出当前页，共："+ pageSize + " 条数据");
                watch.start();
                //2.1 读取当前页数据
                responseMsg = postForm(getPostUrlOfExportAllData(currentPage,pageSize,url), new TypeReference<ResponseMsg<QueryRespBean>>() {} , request.getHeader("Cookie"),  params);
                
                //2.2 写入excel对象
                list = resultJsonToListMap(responseMsg,comboxColumn);
                if (isExportCombox(isCombox)) {
                    wb = util.exportLargeExcel(sName, headers, type, list, fileds,attributesOfDateType,comboxColumn, wb , 1, isTime); //页面传递为1
                }else{
                    wb = util.exportLargeExcel(sName, headers, type, list, fileds,attributesOfDateType,null, wb , 1, isTime); //页面传递为1
                }
                watch.stop();
                logger.debug("本次次处理" + pageSize + "条，查询所耗时间："+watch.getTime());
                watch.reset();
                logger.debug("把excel对象放入response");
                
                //2.3 excel对象放入reponse
                write(wb,fileName,response);
            }
			
		} catch (Exception e) {
			logger.error("导出EXCEL异常", e);
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * 构建查询参数
	 * @param request
	 * @param dataMsg
	 * @return
	 */
	private QueryReqBean buildQueryParams(HttpServletRequest request,DataMsg dataMsg){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Map<String, Object> dto = new HashMap<String, Object>();
		Enumeration parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			if ("json".equals(name)||"pageSize".equals(name) ||"pageIndex".equals(name) || "url".equals(name) || "sName".equals(name) || "fileName".equals(name)) continue;
			
			//tomcat需配置useBodyEncodingForURI，否则可能参数是乱码
			String value = request.getParameter(name);
			dto.put(name, value);
		}
		searchParams.put("dto", dto);
		QueryReqBean params = new QueryReqBean();
		params.setSearchParams(searchParams);
		PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		return params;
	}
	
	
	/**
	 * 构建查询参数
	 * @param request
	 * @param dataMsg
	 * @return
	 */
	private String buildQueryParamsToString(HttpServletRequest request,DataMsg dataMsg){
		Map<String, Object> searchParams = new HashMap<String, Object>();
		Map<String, Object> dto = new HashMap<String, Object>();
		Enumeration parameterNames = request.getParameterNames();
		StringBuffer buffer = new StringBuffer();
		while (parameterNames.hasMoreElements()) {
			String name = (String) parameterNames.nextElement();
			if ("json".equals(name) || "url".equals(name)||"pageSize".equals(name) ||"pageIndex".equals(name) || "sName".equals(name) || "fileName".equals(name)) continue;
			String value = request.getParameter(name);
			buffer.append(name+"="+value+"&");
		}
		
		return buffer.toString();
	}
	
	
	/**
	 * 解析页面封装的JSON参数
	 * @param json
	 * @return
	 */
	private List<ExportExcelDto> parseToExcelDtos(String json){
		if (StringUtils.isEmpty(json)) {
			throw new IllegalArgumentException("解析页面参数[json]异常...");
		}
		JSONObject obj = JSONObject.parseObject(json);
		JSONArray arr = obj.getJSONArray("statuses");
		List<ExportExcelDto> result = JSON.parseArray(arr.toString(),ExportExcelDto.class);
		return result;
	}
	
	
	
	/**
	 * 是否是时间字段
	 * @param column
	 * @return
	 */
	private boolean isDate(String column){
		return "date".equalsIgnoreCase(column);
	}
	
	/**
	 * 是否导出下拉列表
	 * @param isCombox
	 * @return
	 */
	private boolean isExportCombox(String isCombox){
		return (isCombox == null || isCombox.length()== 0 ) ? false:Boolean.valueOf(isCombox).booleanValue();
	}
	
	/**
	 * 验证是否导出所有数据
	 * @param isAll 默认true
	 * @param url 导出数据请求的URL
	 * @return
	 */
	private  String  getUrlOfExportAllData(String isAll,String currentPage,String pageSize,String url,String queryParam){
		if ("true".equalsIgnoreCase(isAll)) {
			if (url.indexOf("?")!=-1) {
				url = url+"&pageSize="+Integer.MAX_VALUE+"&"+queryParam;//因URL中的control方法，已设置分页参数，只能临时改变pageSize以实现导出所有
			}else{
				url = url+"?pageSize="+Integer.MAX_VALUE+"&"+queryParam;
			}
		}else{
			if (url.indexOf("?")!=-1) {
				url = url+"&pageIndex="+currentPage+"&pageSize="+pageSize+"&"+queryParam;//因URL中的control方法，已设置分页参数，只能临时改变pageSize以实现导出默认10条
			}else{
				url = url+"?pageSize="+pageSize+"&pageIndex="+currentPage+"&"+queryParam;
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("导出EXCEL操作请求的URL:"+url);
		}
		return url ;
	}
	
	
	/**
	 * 验证是否导出所有数据（获取post方式传递的url，查询参数单独传递）
		* @title: getPostUrlOfExportAllData
		* ljw
		* @description:
		* @date 2015年4月23日 上午10:00:21
		* @param currentPage   当前页码
		* @param pageSize      每页的数据量
		* @param url           请求数据地址
		* @return
		* @throws
	 */
    private  String  getPostUrlOfExportAllData(int currentPage,int pageSize,String url){

        if (url.indexOf("?")!=-1) {
            url = url+"&pageIndex="+currentPage+"&pageSize="+pageSize;//因URL中的control方法，已设置分页参数，只能临时改变pageSize以实现导出默认10条
        }else{
            url = url+"?pageSize="+pageSize+"&pageIndex="+currentPage;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("导出EXCEL操作请求的URL:"+url);
        }
        return url ;
    }
	
	
	/**
	 * 页面输出
	 * @param workbook
	 * @param fileName
	 * @param response
	 */
	private  void write(HSSFWorkbook workbook,String fileName,HttpServletResponse response){
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1")
					+ ".xls");
			workbook.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (UnsupportedEncodingException e) {
			logger.error("导出EXCEL异常", e);
		} catch (IOException e) {
			logger.error("导出EXCEL异常", e);
		}
	}
	
	/**
	 * 
		* @title: 把excel文件放入response
		* ljw
		* @description:
		* @date 2015年4月23日 上午10:23:15
		* @param xworkbook
		* @param fileName
		* @param response
		* @throws
	 */
	private void write(SXSSFWorkbook xworkbook,String fileName, HttpServletResponse response){
		try {
			response.setContentType("application/vnd.ms-excel");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("content-disposition", "attachment;filename="
					+ new String(fileName.getBytes("gb2312"), "ISO8859-1")
					+ ".xlsx");
			xworkbook.write(response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (UnsupportedEncodingException e) {
			logger.error("导出EXCEL异常", e);
		} catch (IOException e) {
			logger.error("导出EXCEL异常", e);
		}
	}


	/**
	 * 用于下拉列表值映射
	 * @param result
	 * @param columnNameOfSelectType
	 * @return
	 */
	private List<Map> resultJsonToListMap(String result,Map<String, SelectValue[]> columnNameOfSelectType) throws Exception{
		JSONObject dataObj = JSONObject.parseObject(result);
		JSONArray dataArr = dataObj.getJSONArray("data");
		List<Map> list = new ArrayList<Map>();
		for (int i = 0; i < dataArr.size(); i++) {
			JSONObject jsonobject = dataArr.getJSONObject(i);
			for(String dataKey : columnNameOfSelectType.keySet()){  
				SelectValue [] selectValue = columnNameOfSelectType.get(dataKey);
				if(selectValue != null && selectValue.length >0){
					for (int j = 0; j < selectValue.length; j++) {
						if (selectValue[j].getValue().equalsIgnoreCase(jsonobject.getString(dataKey))) {
							jsonobject.put(dataKey, selectValue[j].getText());
						}
					}
				}else{
					jsonobject.put(dataKey, "");
				}
				
			}
			Map<String, Object> addMap = new HashMap<String, Object>();
			addMap.putAll(jsonobject);
			list.add(addMap);
		}
		return list;
	}

	private <T, D> String askUrlByClient(String url,
			TypeReference<ResponseMsg<T>> typeReference, D data, String cookie)
			throws Exception {

		
		HttpUriRequest httpUriRequest = ExcelHttpFactory.getInstance(200).buildRequest(
				jyptAppId, url, "POST", data);
		CloseableHttpClient httpClient = ExcelHttpFactory.getInstance(200)
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
			}else{
				throw new Exception("导出excel请求异常，异常代码为："+statusCode+",请尝试直连URL是否成功:"+url);
			}
		} catch (RuntimeException ex) {
			logger.error("导出excel请求运行时异常！", ex);
			throw new Exception(ex.getMessage());
		}catch (Exception e) {
			logger.error("导出excel请求异常", e);
			throw new Exception(e.getMessage());
		} finally {
		    try {
                if(httpResponse != null)
                    httpResponse.close();
            } catch (IOException e) {
                logger.error("导出excel请求异常 response", e);
            }
            try {
                if(httpClient != null)
                    httpClient.close();
            } catch (Exception e) {
                logger.error("导出excel请求异常 httpclient", e);
            }
		}
		return result;
	}
	
	/** 
     * post方式提交请求
     */  
    public <T, D> String postForm(String url, TypeReference<ResponseMsg<T>> typeReference, String cookie, QueryReqBean params) throws Exception {  
    	String result = null;
    	
        // 创建默认的httpClient实例.    
        CloseableHttpClient httpclient = ExcelHttpFactory.getInstance(500).getHttpClient();
        // 创建httppost    
        HttpPost httppost = new HttpPost(url);  
    	Header header = new BasicHeader("Cookie", cookie);
    	httppost.setHeader(header);
    	
          // 创建参数队列    
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();  
        Map<String, Object> parasMap = params.getSearchParams();
        formparams =  converParameter(parasMap);
        UrlEncodedFormEntity uefEntity;  
        uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");  
        httppost.setEntity(uefEntity);  
        
        CloseableHttpResponse response = null;
        
        try {
            response = httpclient.execute(httppost);  
            int statusCode = 200;
			statusCode = response.getStatusLine().getStatusCode();

			if (statusCode == 200) {
				result = EntityUtils.toString(response.getEntity());
			}else{
				throw new Exception("导出excel请求异常，异常代码为："+statusCode+",请尝试直连URL是否成功:"+url);
			}
    		
    		
        } catch (RuntimeException ex) {
			logger.error("导出excel请求运行时异常！", ex);
			throw new Exception(ex.getMessage());
		}catch (Exception e) {
			logger.error("导出excel请求异常", e);
			throw new Exception(e.getMessage());
		} finally {
			try {
			    if(response != null)
				response.close();
			} catch (IOException e) {
				logger.error("导出excel请求异常 response", e);
			}
			try {
                if(httpclient != null)
                httpclient.close();
            } catch (Exception e) {
                logger.error("导出excel请求异常 httpclient", e);
            }
			
		}
        return result; 
    }
    
    /**
     * 查询参数转换
     * @param pMap
     * @return
     */
    public List<NameValuePair> converParameter(Map<String, Object> pMap){
    	List<NameValuePair> formparams = new ArrayList<NameValuePair>(); 
    	
    	Map<String, String> dtoMap = (HashMap<String, String>)pMap.get("dto");
    	Iterator itor = dtoMap.keySet().iterator();
    	while(itor.hasNext()){
	    	String key = (String) itor.next();
	    	formparams.add(new BasicNameValuePair(key, dtoMap.get(key)));
    	}
    	
    	return formparams;
    }
    
    /**
     * 把下载文件以流的方式放入response
     * @param file
     * @param response
     * @return
     */
    public HttpServletResponse downloadFile(File file, HttpServletResponse response) {
        InputStream fis       = null;
        OutputStream toClient = null;
        try {
            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(
                    file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            
            // 清空response
            response.reset();

            toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/vnd.ms-excel");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-disposition", "attachment;filename="
                    + new String(file.getName().getBytes("gb2312"), "ISO8859-1"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            
            
            
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (toClient != null) {
                try {
                    toClient.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            //删除文件
            file.delete();
        }
        return response;
    }

    //多线程查询
    @Override
    public void run() {
        try {
            String responseMsg = postForm(getPostUrlOfExportAllData(requestIndex,requestPageSize,requestUrl), new TypeReference<ResponseMsg<QueryRespBean>>() {} , requestCookie,  requestParams);
            List<Map> list = resultJsonToListMap(responseMsg,requestComboxColumn);
            fairLock.lock();
            mapList.put(requestIndex, list);
        }catch (Exception e) {
            e.printStackTrace();
            logger.error("excel导出：执行多线程查询时失败！");
        }finally{
            fairLock.unlock();
        }
    }
    
}
