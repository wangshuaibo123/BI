package com.fintech.modules.platform.common.controller;

import java.io.File;
import java.io.FilenameFilter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.alibaba.fastjson.JSON;
import com.fintech.modules.platform.common.service.CommonService;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: StaticFilesUpdateController
 */
@Controller
@Scope("prototype")
@RequestMapping("/api/backBusiness")
public class StaticFilesUpdateController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(StaticFilesUpdateController.class);

	@Autowired
	private CommonService service;
    /**
     *文件替换
     *
     * @param response
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/updateFile", method = RequestMethod.POST)
    public void importFile(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        String status="ok";
        String msg="";
        try{
        	 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
             CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
             String replacePath=this.getParameterString("filePath");
             if(StringUtils.isEmpty(replacePath)){
                 status= "error";
                 msg="替换路径为空";
             }else {
                 URL url = this.getClass().getClassLoader().getResource("/");
                 String basePath = url.getPath();

                 //业务处理
                 String targetPath = getParentPath(basePath, 2);
                 targetPath=targetPath.substring(1);
                 Path src = Paths.get(targetPath + replacePath);
                 String lasxFix=getListFileNum(targetPath + replacePath);
                 Path to = Paths.get(targetPath + replacePath + ".bak"+lasxFix);
                 if (Files.exists(src)) {
                     //备份源文件
                     Files.move(src, to, StandardCopyOption.REPLACE_EXISTING);
                 }
                 try{
                	 Files.copy(file.getInputStream(), src);
                 }catch(Exception e){
                	 status= "error";
                     msg=e.getMessage();
                     //替换文件异常后需要还原原文件
                     Files.move(to, src, StandardCopyOption.REPLACE_EXISTING);
                 }
             }
        }catch(Exception e){
        	status= "error";
            msg=e.getMessage();
        }
        result.put("status", status);
        result.put("msg", msg);
        Writer out = response.getWriter();
        out.write(JSON.toJSONString(result));
    }

    @RequestMapping(value = "/executSql", method = RequestMethod.POST)
    @ResponseBody
    public DataMsg executSql(String sqlStr,  @ModelAttribute DataMsg dataMsg)
            throws Exception {
        List<Map<String,String>> results=new ArrayList<Map<String,String>>();
        String[] sqls=sqlStr.split(";");
        for(String sql:sqls){
            Map<String,String> result=new HashMap<String,String>();
            List<Map<String,Object>>list=new ArrayList<Map<String,Object>>();
            String errorMsg="execute success";
            try{
                list=service.executeSql(sql);
            }catch(Exception e){
                logger.error(e.getMessage());
                errorMsg="execute fail";
            }
            result.put("key",sql);
            result.put("result",(list==null)?errorMsg:JSON.toJSONString(list));
            results.add(result);
        }
        dataMsg.setData(results);
        return dataMsg;
    }

    private  String getParentPath(String basePath,int level){
        for(int i=0;i<=level;i++){
            basePath=basePath.substring(0,basePath.lastIndexOf("/"));
        }
        return basePath+"/";
    }

    /**
     * 获取目标文件夹下文件数量
     * @param basepath
     * @return
     * @throws Exception
     */
    private String getListFileNum(String basepath)throws Exception{
        String path=getParentPath(basepath,0);
        final String filename=basepath.substring(basepath.lastIndexOf("/")+1);
        String regex="^"+filename;
        final Pattern pattern = Pattern.compile(regex);
        File file=new File(path);
        if(file.isDirectory()){
            File[] list= file.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return  pattern.matcher(filename).matches();
                }
            });
            return (list==null || list.length==0)?"":list.length+"";
        }
        return "";
    }
}
