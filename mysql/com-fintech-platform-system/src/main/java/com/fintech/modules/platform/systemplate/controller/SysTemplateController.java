package com.fintech.modules.platform.systemplate.controller;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import oracle.sql.BLOB;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.modules.platform.systemplate.dto.SysTemplateDTO;
import com.fintech.modules.platform.systemplate.service.SysTemplateService;
import com.fintech.platform.api.systemplate.TemplateAPI;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: SysTemplateController
 * @description: 定义  模板 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysTemplate")
public class SysTemplateController extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(SysTemplateController.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.systemplate.service.SysTemplateService")
    private SysTemplateService service;
    
    @Autowired
    private TemplateAPI templateAPI;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/systemplate/querySysTemplate");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/systemplate/addSysTemplate");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/systemplate/updateSysTemplate");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/systemplate/viewSysTemplate");
        }else if("toViewContent".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/systemplate/viewSysTemplateContent");
        }else if("testTemplateAPI".equals(operate)){//跳转至 查看页面
        	Map<String, Object> param = new HashMap<String, Object>();
        	param.put("userName", "于承洋");
        	templateAPI.getContent(this.getParameterString("templateNo"), param);
        }
        return model;
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-10-27 14:30:25
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysTemplate")
    @ResponseBody
    public DataMsg queryListSysTemplate(HttpServletRequest request, SysTemplateDTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<SysTemplateDTO> list = service.searchSysTemplateByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author
     * @description:新增
     * @date 2014-10-27 14:30:25
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
	@RequestMapping(value = "/insertSysTemplate")
    @ResponseBody
    public DataMsg insertSysTemplate(HttpServletRequest request, SysTemplateDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	Object templateContent = dto.getContent();
        	InputStream  fileIn = null;
        	if(templateContent!=null && templateContent instanceof CommonsMultipartFile){//判断是否有文件根据Spring上传组件实现文件内容读取
        		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)templateContent;
        		fileIn = commonsMultipartFile.getInputStream();
        	}
        	dto = (SysTemplateDTO)super.initDto(dto);
            service.insertSysTemplate(dto, fileIn);
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
        	logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:编辑
     * @date 2014-10-27 14:30:25
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysTemplate")
    @ResponseBody
    public DataMsg updateSysTemplate(HttpServletRequest request, SysTemplateDTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	
        	Object templateContent = dto.getContent();
        	InputStream  fileIn = null;
        	if(templateContent!=null && templateContent instanceof CommonsMultipartFile){
        		CommonsMultipartFile commonsMultipartFile = (CommonsMultipartFile)templateContent;
        		fileIn = commonsMultipartFile.getInputStream();
        	}
        	dto = (SysTemplateDTO)super.initDto(dto);
            service.updateSysTemplate(dto , fileIn);
            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }catch (Exception e) {
        	dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author
     * @description:删除
     * @date 2014-10-27 14:30:25
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysTemplate")
    @ResponseBody
    public DataMsg deleteSysTemplate(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteSysTemplateByPrimaryKey(dto,ids);
			 dataMsg.setMsg("删除成功");
		} catch (Exception e) {
			dataMsg.failed(e.getMessage());
			logger.error("执行方法deleteSysResource异常：", e);

		}
        
        return dataMsg;
    }
    /**
     * @author
     * @description:通过主键查询 其明细信息
     * @date 2014-10-27 14:30:25
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream  ins  =null;
        try{
        	SysTemplateDTO dto = service.querySysTemplateByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
            String contentStr =new String(dto.getTemplateContent(),"GBK");
        	contentStr = StringUtils.replace(contentStr, System.getProperty("line.separator"), "<br>");//处理页面换行问题
        	model.addObject("templateContent",contentStr);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    /**
     * @description:下载压缩包 sysTemplate/download?id=123
     * @author
     * @date: 2016年7月21日 上午10:06:14
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/download")
    public ModelAndView  download(HttpServletRequest request,HttpServletResponse response) throws Exception {
    	String id = request.getParameter("id");
    	if(!StringUtils.isNotEmpty(id)){
    		throw new Exception("参数不能为空！！！");
    	}
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String realName = sdf.format(new Date())+".zip";  
        
        BufferedOutputStream bos = null;
        InputStream  ins =null;
        try {
        	SysTemplateDTO dto = service.querySysTemplateByPrimaryKey(id);
            Object object = dto.getTemplateContent();
            if(object!=null && object instanceof Blob){
            	long lastModified = new Date().getTime();
                setLastModifiedHeader(response, lastModified);
                setEtag(response,"aaaaaaaaaa");
            	response.setContentType("application/octet-stream");
    			response.setHeader("Content-Disposition", "attachment; filename=\"" + new String(realName.getBytes("utf-8"), "ISO8859-1") + "\"");
    			ByteArrayOutputStream os = new ByteArrayOutputStream();
    			bos = new BufferedOutputStream(response.getOutputStream());
            	BLOB content = (BLOB)object;
            	ins = content.getBinaryStream();
            	byte[] buff = new byte[2048];
            	int bytesRead = -1;
            	while (-1 != (bytesRead = ins.read(buff, 0, buff.length))) {
            		bos.write(buff, 0, bytesRead);
            	}
        	}
		} catch (Exception e) {
			logger.error("===error===压缩包下载失败",e);
		}finally{
			if(ins != null){
				ins.close();  
			}
			if(bos != null){
				bos.flush();
				bos.close();
			}
		}
        
        return null;
    }
    
    private void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName){
      String encodedfileName = null;

      encodedfileName = fileName.trim().replaceAll(" ", "_");
      String agent = request.getHeader("User-Agent");
      boolean isMSIE = (agent != null) && (agent.toUpperCase().indexOf("MSIE") != -1);
      try {
        if (isMSIE)
          encodedfileName = URLEncoder.encode(fileName, "UTF-8");
        else
          encodedfileName = new String(fileName.getBytes(), "ISO-8859-1");
      }
      catch (UnsupportedEncodingException e) {
        logger.error("setFileDownloadHeader error", e);
      }
    }

    private  void setLastModifiedHeader(HttpServletResponse response, long lastModifiedDate){
      response.setDateHeader("Last-Modified", lastModifiedDate);
    }

    private  void setEtag(HttpServletResponse response, String etag){
      response.setHeader("ETag", etag);
    }
}
