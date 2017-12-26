package com.fintech.modules.platform.common.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.fintech.modules.platform.sysconfig.dto.SysConfigDTO;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;
import com.fintech.platform.tools.excel.ExcelColumn;
import com.fintech.platform.tools.excel.ExcelHeader;
import com.fintech.platform.tools.excel.Template;
import com.fintech.platform.tools.excel.TemplateFactory;
import com.fintech.platform.tools.excel.TxtColumn;
import com.fintech.platform.tools.excel.TxtHeader;

/**
 * <p>导入DEMO</p>
 * 
 * @author
 * @2014年12月10日 上午10:28:49
 */
@Controller
@RequestMapping("/import")
public class ImportController extends BaseController {


	/**
	 * 通过 Controller 请求 跳转 进入至相关 页面
	 */
	@RequestMapping(value = "/prepareExecute/{operate}")
	public ModelAndView execute(@PathVariable("operate") String operate)
			throws AbaboonException {
		ModelAndView model = new ModelAndView();
		if ("toImportPage".equals(operate)) {// 跳转至 查询页面
			model.setViewName("platform/sysConfig/querySysConfig");
		}
		return model;
	}

	/**
	 * 导入Excel文件
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/importFile", method = RequestMethod.POST)
	public void excute(HttpServletResponse response,HttpServletRequest request)
			throws Exception {
		Writer out = response.getWriter();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
			FileItem fileItems = file.getFileItem();
			String fileName =  fileItems.getName();
			String extName = "xls";
			if (fileName.lastIndexOf(".") >= 0) {
				extName = fileName.substring(fileName.lastIndexOf(".")+1);
			}
			InputStream inputStream = file.getInputStream();
			Template template = TemplateFactory.getInstance(extName);
			// 1.构建头部,fieldName同DTO中属性名对应 且索引顺序同模板列顺序一致
			ExcelHeader excelHeader = new ExcelHeader();
			List<ExcelColumn> excelColumns = new ArrayList<ExcelColumn>();
			excelColumns.add(new ExcelColumn(1, "configName", "配置名称", true));
			excelColumns.add(new ExcelColumn(2, "configCode", "配置编码", true));
			excelColumns.add(new ExcelColumn(3, "configValue", "配置值", false));// 不进行导入的字段isImport为false即可
			excelHeader.setColumns(excelColumns);
			// 2.设置数据开始行数
			excelHeader.setRowCount(1);
			// 3.设置总的行数
			excelHeader.setTotalRows(51);
			// 4.设置需要进行数据转换的格式,map中的KEY值为fieldName
			Map<String, Map> excelColumnsConvertMap = new HashMap<String, Map>();
			Map<String, Integer> configCode = new HashMap<String, Integer>();
			configCode.put("是", 1);
			excelColumnsConvertMap.put("configCode", configCode);
			excelHeader.setColumnsConvertMap(excelColumnsConvertMap);
			// 5.导入数据到内存
			List<SysConfigDTO> dataOfimport = (List<SysConfigDTO>) template
					.importData(inputStream, SysConfigDTO.class, excelHeader);
			for (SysConfigDTO dto : dataOfimport) {
				logger.info("配置名称：" + dto.getConfigName() + "   配置编码："
						+ dto.getConfigCode()+ "    配置值：" + dto.getConfigValue());

			}
			// 6.入库操作，自行实现
			//TODO
			// 7.回显数据
			result.put("status", "ok");
			out.write(JSON.toJSONString(result));
		} catch (Exception e) {
			result.put("status", "error");
			throw new RuntimeException("导入失败");
		}				
	}
	
	
	/**
	 * 下载excel模板
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/download")   
    public ModelAndView download(HttpServletRequest request, HttpServletResponse response)   
            throws Exception {   
  
        response.setContentType("text/html;charset=utf-8");   
        request.setCharacterEncoding("UTF-8");   
        java.io.BufferedInputStream bis = null;   
        java.io.BufferedOutputStream bos = null;   
  
        String ctxPath = request.getSession().getServletContext().getRealPath("/")+ "component\\upload\\";   
        String downLoadPath = ctxPath + "EXCEL导入模板.xlsx";   
        try {   
            long fileLength = new File(downLoadPath).length();   
            response.setContentType("application/x-msdownload;");   
            String fileName = "模板.xls";
            fileName = new String(fileName.getBytes("UTF-8"), "ISO_8859_1"); 
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName+ "");
            response.setHeader("Content-Length", String.valueOf(fileLength));   
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));   
            bos = new BufferedOutputStream(response.getOutputStream());   
            byte[] buff = new byte[2048];   
            int bytesRead;   
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {   
                bos.write(buff, 0, bytesRead);   
            }   
        } catch (Exception e) {   
            throw new RuntimeException("模板下载失败");
        } finally {   
            if (bis != null)   
                bis.close();   
            if (bos != null)   
                bos.close();   
        }   
        return null;   
    }   
	
	/**
	 * 导入Excel文件
	 * @param response
	 * @param request
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/importTxt", method = RequestMethod.POST)
	public void importTxt(HttpServletResponse response,HttpServletRequest request)
			throws Exception {
		Writer out = response.getWriter();
		Map<String,Object> result = new HashMap<String,Object>();
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file");
			FileItem fileItems = file.getFileItem();
			String fileName =  fileItems.getName();
			String extName = "txt";
			if (fileName.lastIndexOf(".") >= 0) {
				extName = fileName.substring(fileName.lastIndexOf(".")+1);
			}
			InputStream inputStream = file.getInputStream();
			Template template = TemplateFactory.getInstance(extName);
			// 1.构建头部,fieldName同DTO中属性名对应，且索引顺序同模板列顺序一致
			TxtHeader header = new TxtHeader();
			List<TxtColumn> txtColumns = new ArrayList<TxtColumn>();
			txtColumns.add(new TxtColumn(0, "configName", "配置名称", true));
			txtColumns.add(new TxtColumn(1, "configCode", "配置编码", true));
			txtColumns.add(new TxtColumn(2, "configValue", "配置值", false));// 不进行导入的字段isImport为false即可
			header.setColumns(txtColumns);
			// 2.设置需要进行数据转换的格式,map中的KEY值为fieldName
			Map<String, Map> txtColumnsConvertMap = new HashMap<String, Map>();
			Map<String, Integer> configCode = new HashMap<String, Integer>();
			configCode.put("是", 1);
			txtColumnsConvertMap.put("configCode", configCode);
			header.setColumnsConvertMap(txtColumnsConvertMap);
			//3.导入数据到内存
			List<SysConfigDTO> dataOfimport = (List<SysConfigDTO>) template.importData(inputStream, SysConfigDTO.class, header);
			for (SysConfigDTO dto : dataOfimport) {
				logger.info("配置名称：" + dto.getConfigName() + "   配置编码："
						+ dto.getConfigCode() + "    配置值：" + dto.getConfigValue());
			}
			// 4.入库操作，自行实现
			//TODO
			// 5.回显数据
			result.put("status", "ok");
			out.write(JSON.toJSONString(result));
		} catch (Exception e) {
			result.put("status", "error");
			throw new RuntimeException("导入失败");
		}				
	}
	
	
	
	/**
	 * 下载txt模板
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/downloadTxt")   
    public ModelAndView downloadTxt(HttpServletRequest request, HttpServletResponse response)   
            throws Exception {   
  
        response.setContentType("text/html;charset=utf-8");   
        request.setCharacterEncoding("UTF-8");   
        java.io.BufferedInputStream bis = null;   
        java.io.BufferedOutputStream bos = null;   
  
        String ctxPath = request.getSession().getServletContext().getRealPath("/")+ "component\\upload\\";   
        String downLoadPath = ctxPath + "模板.txt";   
        try {   
            long fileLength = new File(downLoadPath).length();   
            String fileName = "模板.txt";
            fileName = new String(fileName.getBytes("UTF-8"), "ISO_8859_1"); 
            response.setContentType("text/plain");
            response.setHeader("Content-disposition", "attachment; filename=" + fileName+ "");
            response.setHeader("Content-Length", String.valueOf(fileLength));   
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));   
            bos = new BufferedOutputStream(response.getOutputStream());   
            byte[] buff = new byte[2048];   
            int bytesRead;   
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {   
                bos.write(buff, 0, bytesRead);   
            }   
        } catch (Exception e) {   
            throw new RuntimeException("模板下载失败");
        } finally {   
            if (bis != null)   
                bis.close();   
            if (bos != null)   
                bos.close();   
        }   
        return null;   
    }   
}
