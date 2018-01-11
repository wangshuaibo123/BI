package com.fintech.platform.jbpm4.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.cmd.Environment;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.repository.RepositoryCache;
import org.jbpm.pvm.internal.stream.FileStreamInput;
import org.jbpm.pvm.internal.stream.StreamInput;
import org.jbpm.pvm.internal.util.IoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fintech.platform.api.sysdict.SysDictAPI;
import com.fintech.platform.core.common.JYLoggerUtil;
import com.fintech.platform.jbpm4.drawJbpmPng.JpdlModel;
import com.fintech.platform.jbpm4.drawJbpmPng.JpdlModelDrawer;
import com.fintech.platform.jbpm4.dto.JbpmBlobDTO;
import com.fintech.platform.jbpm4.repository.JbpmMapper;
import com.fintech.platform.jbpm4.service.IModifyProInfoService;
import com.fintech.platform.jbpm4.temporaryJbpm4Info.dto.TemporaryJbpm4InfoDTO;
import com.fintech.platform.jbpm4.temporaryJbpm4Info.service.ITemporaryJbpm4InfoService;
import com.fintech.platform.jbpm4.tool.MyJBPMTool;
import com.fintech.platform.jbpm4.tool.RestUtil;
/**
 * 
 * @Description: jbpm4.4 工作流的service 实现类 
 * @author
 * @version 1.0, 
 * @date 2013-8-29 下午01:38:38
 */
@Service("com.fintech.platform.jbpm4.service.impl.ModifyProInfoServiceImpl")
public class ModifyProInfoServiceImpl implements IModifyProInfoService,Serializable {
		
	String apiUrl = "/api/jbpm/repositoryCache/delete/v1/{ID}";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private JbpmMapper mapper;
	//获取 访问业务层的对象
	@Autowired
	@Qualifier("MyJBPMTool")
	private MyJBPMTool myJBPMTool;
	
	@Autowired
	@Qualifier("com.fintech.platform.jbpm4.temporaryJbpm4Info.service.impl.TemporaryJbpm4InfoServiceImpl")
	private ITemporaryJbpm4InfoService tempService;
	
	@Autowired
	private SysDictAPI sysDictAPI;
	
	
	/*@Autowired
	@Qualifier("com.platform.components.jbpm.jbpm4BusinessTab.service.impl.Jbpm4BusinessTabServiceImpl")
	private IJbpm4BusinessTabService bizService;
	*/
	

	public String productXmlByXmlContent(String xmlContent) {
		ClassLoader cl= ModifyProInfoServiceImpl.class.getClassLoader();
		URL url=cl.getResource("proxml");
		String urlPath = url.getFile();
		String templatePath = urlPath.substring(0, (urlPath.indexOf("WEB-INF/"))+"WEB-INF/".length())+ "/classes/proxml/";
        
		String xmlPath = templatePath;//path +"/";
		//用它可以产生一个号称全球唯一的ID
		UUID uuid = UUID.randomUUID();
		String realPath = "";
		String tempPath =  xmlPath + uuid+".xml";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddmmss");
		
		realPath = xmlPath + dateFormat.format(new Date())  + ".jpdl.xml";
		
		//向文件写xml内容信息
		Writer writer = null;
		try {
            File file = new File(realPath);
            writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");

            writer.write(xmlContent);
            
            writer.flush();
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally{
        	if(writer != null ){
        		try {
        			writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
				
        }
        
        
		return realPath;
	}

	
	@Transactional(rollbackFor=Exception.class)
	public String updateJbpm4LobTableImmediately(Map<String,Object> paramMap) throws Exception {
		//现 将立即生效的流程信息保存
		String lastId = "";
		String deploymentId ="";
		lastId = tempService.addTemporaryJbpm4Info(paramMap);
		
		//2.将数据库中存储的该流程信息读取,生成待发布的xml文件。
		String lastXmlContent = tempService.getOneProcessXMLContent(lastId);
		
		//if(StringUtils.isEmpty(lastXmlContent)) throw new Exception("-事务不在同一会话中---");
		
		String lastXmlFile = this.productXmlByXmlContent(lastXmlContent);
		
		//根据 最新的流程文件xml 生成流程图片
		this.updateProPngByLastId(lastXmlFile,lastId);
		
		//开始 及时生效 更新已经部署的流程信息
		JbpmBlobDTO dto = new JbpmBlobDTO();
	    byte[] contents = null;
	   
        File file = new File(lastXmlFile);
		StreamInput streamInput = new FileStreamInput(file);
		InputStream inputStream = streamInput.openStream();
        try {
        	byte[] bytes = IoUtil.readBytes(inputStream);
        	contents = bytes;
        }catch (Exception e) {
        	e.printStackTrace();
        }finally {
          IoUtil.close(inputStream);
        }
        dto.setContents(contents);
        //查询到其部署时 的流程定义id
        TemporaryJbpm4InfoDTO tempDTO = (TemporaryJbpm4InfoDTO) paramMap.get("dto");
        Map<String,Object> queryMap = new HashMap<String,Object>();
        queryMap.put("processName", tempDTO.getProcName());
        queryMap.put("proVersion", tempDTO.getProcVersion());
        
        
        //deploymentId= String.valueOf(dao.queryObjectBySql("query_max_deploymnet_id", queryMap));
        
        deploymentId= mapper.queryMaxDeploymnetId(queryMap);
        //通过sql 更新数据
        dto.setId(Long.parseLong(deploymentId));
        
        
		//dao.updateDataBySql("update_jbpm4_lob", dto);
		
		mapper.updateJbpm4Lob(dto);
		
		//删除本机缓存
		deleteRepositoryCache(deploymentId);
	    /**
	     * 删除集群缓存
	     */
		
		//项目集群地址
		List<Map> dictList = sysDictAPI.getDictByKey("PROJECT_CLUSTER_URL");
		if(null!=dictList){
			for(Map dictMap:dictList){
				String dictValue = (String)dictMap.get("DICVALUE");
				String url = dictValue+apiUrl;
				try{
					RestUtil.doGet(url, deploymentId);
				}catch(Exception e){
					JYLoggerUtil.error(ModifyProInfoServiceImpl.class, "删除集群流程定义缓存失败。", e);
				}
			}
		}
		return deploymentId;
	}
	
	/**
	 * 删除流程定义缓存
	 * @param deploymentId
	 */
	public void deleteRepositoryCache(String deploymentId){
		Environment evn = ((EnvironmentFactory)myJBPMTool.getProcessEngine()).openEnvironment();
	    RepositoryCache repositoryCache = ((EnvironmentImpl) evn).getFromCurrent(RepositoryCache.class);
	    repositoryCache.remove(deploymentId);
	}
	
	
	@Transactional
	public String deployProcess(TemporaryJbpm4InfoDTO dto,String lastId) throws Exception {
		String deployId = "";
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("dto", dto);
		//如果lastId 为null 说明 不是通过流程web设计器发布的流程，
		if(!StringUtils.isNotEmpty(lastId)){
			//通过流程web设计器发布流程要先保存 再发布
			lastId = tempService.addTemporaryJbpm4Info(paramMap);
			/*
			//1.先将该流程旧信息进行假删除操作。
			//删掉流程名称相同的旧信息
			//dao.updateDataBySql("delete_old_process_info_by_sql", paramMap);
			
			mapper.deleteOldProcessInfoBySql(paramMap);
			//2.再新增该流程信息
			//dto.setXmlContent(errors.getRequest().getParameter("xml"));
			TemporaryJbpm4InfoDTO dtoTemp = (TemporaryJbpm4InfoDTO) paramMap.get("dto");
			
			if(!dtoTemp.getXmlContent().contains("<?xml version='1.0' encoding='UTF-8' ?>")){
				dtoTemp.setXmlContent("<?xml version='1.0' encoding='UTF-8' ?>"+dtoTemp.getXmlContent());
			}
			
			paramMap.put("dto", dtoTemp);
			
			//Object obj = (Object)dao.addObject("add_temporary_jbpm4_info", paramMap);
			mapper.addTemporaryJbpm4Info(paramMap);
			//TemporaryJbpm4InfoDTO resulstDto = (TemporaryJbpm4InfoDTO) paramMap.get("dto");
			
			lastId = String.valueOf(paramMap.get("id"));*/
		}
		
	
		if(StringUtils.isNotEmpty(lastId)){
			//2.将数据库中存储的该流程信息读取,生成待发布的xml文件。
			//TemporaryJbpm4InfoDTO dtoJbpm4 = (TemporaryJbpm4InfoDTO) paramMap.get("dto");
			String lastXmlContent = tempService.getOneProcessXMLContent(lastId);
			
			//String lastXmlContent = dtoJbpm4.getXmlContent();
			
			String lastXmlFile = this.productXmlByXmlContent(lastXmlContent);
			//通过xml文件生成流程图片
			this.updateProPngByLastId(lastXmlFile,lastId);
			
			lastXmlFile = lastXmlFile.substring(lastXmlFile.indexOf("classes/")+"classes/".length(),lastXmlFile.length());
			JYLoggerUtil.info(this.getClass(), "--------"+lastXmlFile);
			
			//发布流程
			//lastXmlFile = "uploadProcessXml/test.jpdl.xml";
			deployId = myJBPMTool.publishProcessByXML(lastXmlFile);
		}
		
		return deployId;
	}
	
	@Transactional
	public void cancelPublishProcess(String deploymentId) throws Exception {

		if(StringUtils.isNotEmpty(deploymentId)){
			myJBPMTool.cancelPublishProcess(deploymentId);
		}
		
	}
	/**
	 * 通过 流程图的xml文件 绘制流程图片 返回流程图片名称
	 * @param xmlPath
	 * @return
	 * @throws Exception 
	 */
	private String drawProPng(String xmlPath) throws Exception {
		ClassLoader cl= ModifyProInfoServiceImpl.class.getClassLoader();
		URL url=cl.getResource("proxml");
        
		String urlPath = url.getFile();
		String templatePath = urlPath.substring(0, (urlPath.indexOf("WEB-INF/"))+"WEB-INF/".length())+ "/classes/proxml/";
        
        
		String pngPath = templatePath;
		String pngName = null;
		//String pngPath =  ConstantJBPM.JbpmXmlfilePath;//BaseConfiguration.getString("jbpm.xmlfile.path");
		//用它可以产生一个号称全球唯一的ID
		UUID uuid = UUID.randomUUID();
		String realPath = "";
		String tempPath =  pngPath + uuid+".png";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddmmss");
		
		pngName = dateFormat.format(new Date())  + ".png";
		
		realPath = pngPath + "/"+pngName;
		
		StreamInput streamInput = new FileStreamInput( new File(xmlPath));
		InputStream is = streamInput.openStream();
			
		//InputStream is = this.getClass().getResourceAsStream(xmlPath);
		
		JpdlModel jpdlModel = new JpdlModel(is);
		ImageIO.write(JpdlModelDrawer.getInstance().draw(jpdlModel), "png",new File(realPath));
	

		JYLoggerUtil.info(this.getClass(), "成功生成流程图的图片.....");
		return realPath;
	}
	
	
	/**
	 * 通过 最新的xml文件生成流程图片 保存至最新的 tempor jbpm4 info 表中
	 * @param lastXmlFile
	 * @param lastId
	 * @throws Exception
	 */
	private void updateProPngByLastId(String lastXmlFile,String lastId) throws Exception{
		//通过xml文件生成流程图片
		String pngRealPath = this.drawProPng(lastXmlFile);
		 
		JbpmBlobDTO pngDTO = new JbpmBlobDTO();
		pngDTO.setId(Long.parseLong(lastId));
		byte[] proPng = null;
		
		File file = new File(pngRealPath);
		StreamInput streamInput = new FileStreamInput(file);
		InputStream inputStream = streamInput.openStream();
        try {
        	byte[] bytes = IoUtil.readBytes(inputStream);
        	proPng = bytes;
        }catch (Exception e) {
        	//e.printStackTrace();
        	throw new Exception ();
        }finally {
          IoUtil.close(inputStream);
        }
        
		pngDTO.setContents(proPng );
		//将图片保存至 temporary_jbpm4_info 表中 id 为lastId
		tempService.updateProPngOfTempJbpm4Info(pngDTO);

	}
	
	
}

