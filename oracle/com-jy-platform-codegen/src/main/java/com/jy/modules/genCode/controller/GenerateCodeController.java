package com.jy.modules.genCode.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jy.modules.genCode.common.StringUtilTools;
import com.jy.modules.genCode.dto.ProJspDTO;
import com.jy.modules.genCode.dto.ProductCodeDTO;
import com.jy.modules.genCode.service.IGenCodeService;
import com.jy.modules.genCode.util.CodeGenPropertiesInfo;
import com.jy.modules.genCode.util.DynamicDataSourceUtil;
import com.jy.modules.genCode.util.ZipUtil;
import com.jy.platform.core.common.JYConstants;
import com.jy.platform.core.message.DataMsg;
import com.jy.platform.restservice.web.base.BaseController;

/**
 * @classname: GenerateCodeController
 * @description: TODO(这里用一句话描述这个类的作用)
 */
@Controller
@Scope("prototype")
@RequestMapping("/generate")
@SuppressWarnings("all")
public class GenerateCodeController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(GenerateCodeController.class);

    @Autowired
    @Qualifier("com.jy.modules.genCode.service.impl.GenCodeServiceImpl")
    private IGenCodeService genCodeService;

    /**
     * @title: generateCode
     * @author congfei
     * @description:生成代码
     * @date 2014-9-4 下午4:03:41
     * @param request
     * @param dto
     * @return
     * @throws Exception
     * @throws
     */
    @RequestMapping(value = "/generateCode")
    @ResponseBody
    public Map<String, Object> generateCode(HttpServletRequest request, HttpServletResponse response, ProductCodeDTO dto) throws Exception {
        System.out.println("--------Generate Code start---------");
    	String[] bArray =  {"001","002","003","004", "005", "006"}; 	//初始化字典项对应code
    	List  list = new ArrayList();
    	List  li = new ArrayList();
        String acceptStore =request.getParameter("acceptStore");
        String [] falg = null;
        if(StringUtils.isNotBlank(acceptStore)){
        	falg=acceptStore.split(",");
        }
        Map<String, Object> paramMap =  new HashMap<String, Object>();
        try {
            DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp = df.format(new java.util.Date());
            
        	paramMap = this.makeupParam(request, dto, timestamp);
        	paramMap.put("timestamp", timestamp);
            String validateResult = (String) paramMap.get("result");
            String path =null; 
            if( StringUtils.isNotEmpty(validateResult)) return paramMap;
             
            if(falg!=null&&falg.length>0){
            	this.start(paramMap, dto);//开始生成
            	path =dto.getTargetDir()+dto.getPackageNamePrefix().replace(".", "/")+"/"+dto.getModelName();
            	for (int i = 0; i < falg.length; i++) {
                	list.add(falg[i]);
     			}
            	
            	if(falg.length==1&&falg[0].equals("006")){//如果只生成js和jsp直接删除java代码com目录
            		deleteDir(new File("C:/generate/com"));
            	}else{
	            	for (int i = 0; i < bArray.length; i++) {
	            		if(!list.contains(bArray[i])) {//如果数组 list 不包含当前项，则增加该项到数组中
	            				switch (Integer.valueOf(bArray[i])) {
	             				case 001:
	                 				deleteDir(new File(path+"/Dto"));
	             					break;
	             				case 002:
	                 				deleteDir(new File(path+"/service"));
	             					break;
	             				case 003:
	                 				deleteDir(new File(path+"/controller"));
	             					break;
	             				case 004:
	                 				deleteDir(new File(path+"/rest"));
	             					break;
	             				case 005:
	                 				deleteDir(new File(path+"/dao"));
	             					break;
	             				default:
	                 				deleteDir(new File(dto.getTargetDir()+"jsp"));
	                 				deleteDir(new File(dto.getTargetDir()+"js"));
	             					break;
	             				}
	            			}
	        	    	}
	            	}
            	
            }
            else{
                String dburl = request.getParameter("dburl");
                String dbusername = request.getParameter("dbusername");
                String dbpassword = request.getParameter("dbpassword");
                
                BasicDataSource dataSource = new BasicDataSource();
                dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
                dataSource.setUrl(dburl);
                dataSource.setUsername(dbusername);
                dataSource.setPassword(dbpassword);
                
                //尝试从数据源获取连接
                Connection conn = null;
                try{
                    conn = dataSource.getConnection();
                }
                catch(Exception e){
                    logger.error("url="+dburl+",username="+dbusername+"获取数据库连接报错", e);
                    paramMap.put("result", JYConstants.FAILED);
                    paramMap.put("message", "获取数据库连接报错，请检查数据库相关参数");
                    return paramMap;
                }
                finally{
                   if(conn != null){
                       try{conn.close();}catch(Exception e){logger.error("", e);}
                   } 
                }
                
                DynamicDataSourceUtil.setDataSource(dataSource);
                
                this.start(paramMap, dto);//开始生成
                
                //打zip包
                String codeGenDir = CodeGenPropertiesInfo.getValByKey("codegen.dir");
                String filePath = codeGenDir + timestamp + "/";
                String fileName = dto.getTableName()+ "_" + timestamp + ".zip";
                String zipPath = codeGenDir + fileName;
//                ClassLoader cl = GenerateCodeController.class.getClassLoader();
//                URL url = cl.getResource("genCodeTemplate");
//                String urlPath = url.getFile();
//                String filePath = urlPath.substring(0, (urlPath.indexOf("WEB-INF/"))+"WEB-INF/".length())+ "/classes/gencode/"+timestamp+"/";
//                String fileName = timestamp + ".zip";
//                String zipPath = urlPath.substring(0, (urlPath.indexOf("WEB-INF/"))+"WEB-INF/".length())+ "/classes/gencode/"+fileName;
                ZipUtil.zipMultiFile(filePath, zipPath, false);
                
                //下载zip
                BufferedOutputStream bos = null;
                File file = null;
                FileInputStream fis = null;
                
                try{
                    file = new File(zipPath);
                    if(file.exists()){
                        fis = new FileInputStream(file);
                        
                        response.setContentType("application/octet-stream");
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(fileName.getBytes("gb2312"), "ISO8859-1")  + "\"");
                        bos = new BufferedOutputStream(response.getOutputStream());
                        byte[] buff = new byte[2048];
                        int bytesRead = -1;
                        while (-1 != (bytesRead = fis.read(buff, 0, buff.length))) {
                            bos.write(buff, 0, bytesRead);
                        }
                    } 
                }
                catch(Exception e){
                    logger.error("", e);
                }
                finally{
                    if(fis != null){
                        try{
                            fis.close();
                        }catch(Exception e){e.printStackTrace();}
                    }
                    if(bos != null){
                        try{
                            bos.flush();
                            bos.close();
                        }catch(Exception e){e.printStackTrace();}
                    }
                }
                
            }
//        	paramMap.put("message", "自动生成代码成功，请查看目录C:/generate/");
        	paramMap.put("message", "自动生成代码成功");
        	paramMap.put("result", JYConstants.SUCCESS);
        	
        	//删除目录及zip
        	String codeGenDir = CodeGenPropertiesInfo.getValByKey("codegen.dir");
            String filePath = codeGenDir + timestamp + "/";
            String fileName = dto.getTableName()+ "_" + timestamp + ".zip";
            String zipPath = codeGenDir + fileName;
            
            File dir = new File(filePath);
            if(dir.exists()){
                deleteDir(dir);
            }
            File zipFile = new File(zipPath);
            if(zipFile.exists()){
                zipFile.delete();
            }
        	
        }catch (Exception e) {
            e.printStackTrace();
            paramMap.put("result", JYConstants.FAILED);
            paramMap.put("message", e.toString());
        }
        System.out.println("--------Generate Code end---------");
        
        return paramMap;
    }
    

    private Map<String, Object> makeupParam(HttpServletRequest request, ProductCodeDTO dto, String timestamp) {
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	 
    	ClassLoader cl = GenerateCodeController.class.getClassLoader();
		URL url = cl.getResource("genCodeTemplate");
		String urlPath = url.getFile();
        String templatePath = urlPath.substring(0, (urlPath.indexOf("WEB-INF/"))+"WEB-INF/".length())+ "/classes/genCodeTemplate/";
        
//        String jsp_path = urlPath.substring(0, (urlPath.indexOf("WEB-INF/"))+"WEB-INF/".length())+ "/classes/gencode/"+timestamp+"/jsp/";
//        String js_path = urlPath.substring(0, (urlPath.indexOf("WEB-INF/"))+"WEB-INF/".length())+ "/classes/gencode/"+timestamp+"/js/";
        
        String codeGenDir = CodeGenPropertiesInfo.getValByKey("codegen.dir");
        String jsp_path = codeGenDir + timestamp + "/jsp/";
        String js_path = codeGenDir + timestamp + "/js/";
        
        File jsp_dir = new File(jsp_path); 
        jsp_dir.mkdirs();
        File js_dir = new File(js_path); 
        js_dir.mkdir();
        
        System.out.println("-------------templatePath:"+templatePath);
		String tableName = dto.getTableName();//quartz_task_group_def
		//tableName = this.getParameterString("tableName");
		//设置模板路径	
		dto.setTemplatePath(templatePath);
//		dto.setJspTargetDir(GenCodeServiceImpl.JSP_PATH);
		dto.setJspTargetDir(jsp_path);
		dto.setModelName(StringUtilTools.convertName(tableName, true));
//		dto.setJsTargetDir(GenCodeServiceImpl.JS_PATH);
		dto.setJsTargetDir(js_path);
		
		
        if (StringUtils.isEmpty(dto.getTableName()) || StringUtils.isEmpty(dto.getPackageNamePrefix())
        		|| StringUtils.isEmpty(dto.getJspPrefix())) {
        	paramMap.put("result", JYConstants.FAILED);
            paramMap.put("message", "表名称、java代码包前缀、jsp目录前缀不可以为空！");
            return paramMap;
        }
        
        //组装使用 页面传递 的值，没有信息则使用数据库的信息
        // 字段英文描述
        String[] columnName = request.getParameterValues("columnName");
        String[] columnComments = request.getParameterValues("columnComments");
        String[] dataType = request.getParameterValues("dataType");
        String[] dataLength = request.getParameterValues("dataLength");
        // 页面显示
        String[] myDisplay = request.getParameterValues("myDisplay");
        // 页面展示方式
        String[] myView = request.getParameterValues("myView");
        List jspParamList = new ArrayList();
        if (columnName != null && columnName.length > 0) {
            for (int i = 0; i < columnName.length; i++) {
                ProJspDTO jspDTO = new ProJspDTO();
                jspDTO.setColumnName(String.valueOf(columnName[i]));
                if (StringUtils.isNotEmpty(columnComments[i])) {
                    jspDTO.setColumnComments(columnComments[i]);
                } else {
                    jspDTO.setColumnComments(columnName[i]);
                }

                jspDTO.setDataLength(dataLength[i]);
                jspDTO.setDataType(dataType[i]);
                jspDTO.setMyDisplay("显示");
                // jspDTO.setMyView(myView[i]);
                jspParamList.add(jspDTO);
            }
        }
        // 将 页面设置的 columnOfSubTable 转换成dto 存放入proOfSubTable
        String columnOfSub = dto.getColumnOfSubTable();
        //是否是主子表生成
        if (StringUtils.isNotEmpty(columnOfSub)) {
            dto.setProOfSubTable(StringUtilTools.convertName(columnOfSub, true));
        }
        
       
		paramMap.put("productCodeDTO", dto);
		paramMap.put("jspParamList", jspParamList);
		
		return paramMap;
	}

	/**
     * @title: parpareQuery
     * @author congfei
     * @description:
     * @date 2014-9-9 上午10:22:36
     * @param request
     * @param model
     * @return
     * @throws
     */
    @RequestMapping("/parpareQuery")
    public String parpareQuery(HttpServletRequest request, ModelMap model) {
        return "platform/genCode/queryGenerateCode";
    }


    /**
     * @title: queryColumnsOfTable
     * @author congfei
     * @description:
     * @date 2014-9-9 下午6:10:45
     * @param dto
     * @param request
     * @param pageData
     * @return
     * @throws Exception
     * @throws
     */
    @RequestMapping(value = "/queryColumnsOfTable")
    @ResponseBody
    public DataMsg queryColumnsOfTable(ProductCodeDTO dto, HttpServletRequest request,
            @ModelAttribute DataMsg pageData) throws Exception {
        List<Map> list = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("productCodeDTO", dto);
            paramMap.put("tableName", dto.getTableName());

            list = genCodeService.queryColumnOfTable(paramMap);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
        pageData.setData(list);
        return pageData;
    }

    /**
     * @title: queryTablesOfDatabase
     * @author congfei
     * @description:
     * @date 2014-9-9 下午6:10:40
     * @param dto
     * @param request
     * @param pageData
     * @return
     * @throws Exception
     * @throws
     */
    @RequestMapping(value = "/queryTablesOfDatabase")
    @ResponseBody
    public DataMsg queryTablesOfDatabase(ProductCodeDTO dto, HttpServletRequest request,
            @ModelAttribute DataMsg pageData) throws Exception {

        if (pageData == null) {
            pageData = new DataMsg();
        }
        List<Map> list = null;
        try {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            paramMap.put("startIndex", (pageData.getPageIndex() - 1) * pageData.getPageSize() + 1);
            paramMap.put("endIndex",
                    (pageData.getPageIndex() - 1) * pageData.getPageSize() + 1 + pageData.getPageSize());
            paramMap.put("productCodeDTO", dto);
            if (StringUtils.isNotEmpty(dto.getTableName())) {
                paramMap.put("tableName", dto.getTableName().toUpperCase());
            }
            list = genCodeService.queryListOfTable(paramMap);
            int count = genCodeService.queryListOfTableCount(paramMap);
            pageData.setData(list);
            pageData.setTotalRows(count);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return pageData;
    }
  
    
    /**
     * 删除空目录
     * @param dir 将要删除的目录路径
     */
    private static void doDeleteEmptyDir(String dir) {
        boolean success = (new File(dir)).delete();
        if (success) {
            logger.info("Successfully deleted empty directory: " + dir);
        } else {
            logger.info("Failed to delete empty directory: " + dir);

        }
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
    /**
     * 开始生成代码
     * 
     */
    private  void start(Map<String, Object> paramMap,ProductCodeDTO dto) throws Exception{
    	dto = genCodeService.proDTOCode(paramMap);
    	genCodeService.proServiceCode(paramMap);
    	genCodeService.proActionCode(paramMap);
    	genCodeService.proRestCode(paramMap);
    	genCodeService.proMapperCode(paramMap);
    	genCodeService.proSQLCode(paramMap);
    	genCodeService.proJSPCode(paramMap);
    	genCodeService.proJSCode(paramMap);
    }
}
