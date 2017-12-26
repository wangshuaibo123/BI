package com.fintech.modules.platform.systemplate.service;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.fintech.modules.platform.systemplate.dao.SysTemplateDao;
import com.fintech.modules.platform.systemplate.dto.SysTemplateDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: SysTemplateService
 * @description: 定义  模板 实现类
 * @author
 */
@Service("com.fintech.modules.platform.systemplate.service.SysTemplateService")
public class SysTemplateService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private SysTemplateDao dao;

	/**
     * @author
     * @description: 分页查询 模板列表
     * @date 2014-10-27 14:30:25
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<SysTemplateDTO> searchSysTemplateByPaging(Map<String,Object> searchParams) throws Exception {
		List<SysTemplateDTO> dataList =  dao.searchSysTemplateByPaging(searchParams);
		return dataList;
	}
	/**
     * @author
     * @description: 按条件查询模板列表
     * @date 2014-10-27 14:30:25
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<SysTemplateDTO> searchSysTemplate(Map<String,Object> searchParams) throws Exception {
	    List<SysTemplateDTO> dataList = dao.searchSysTemplate(searchParams);
        return dataList;
    }
	/**
     * @author
     * @description: 查询模板对象
     * @date 2014-10-27 14:30:25
     * @param id
     * @return
     * @throws
     */ 
	public SysTemplateDTO querySysTemplateByPrimaryKey(String id) throws Exception {
		
		SysTemplateDTO dto = dao.findSysTemplateByPrimaryKey(id);
		
		if(dto == null) dto = new SysTemplateDTO();
		
		return dto;
		
	}

	/**
     * @title: insertSysTemplate
     * @author
     * @description: 新增 模板对象
     * @date 2014-10-27 14:30:25
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertSysTemplate(SysTemplateDTO dto , InputStream fileIn) throws Exception {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		//oracle

//		int count = dao.insertSysTemplate(paramMap);
//		SysTemplateDTO resultDto = (SysTemplateDTO) paramMap.get("dto");
//		Long keyId = resultDto.getId();
//		if(fileIn!=null ){
//			//锁定一行数据
//			SysTemplateDTO prsitDto = dao.findSysTemplateByPrimaryKeyForUpdate(resultDto.getId().toString());
//			BLOB content = (BLOB)prsitDto.getTemplateContent();
//			OutputStream ops = null;  
//			try {  
////	        ops = content.getBinaryOutputStream();//暂时使用这个废弃的方法  
//				ops = content.setBinaryStream(0);//ojdbc14支持,ojdbc6,5都不支持  
//				byte[] data = null;  
//				data = FileCopyUtils.copyToByteArray(fileIn);  
//				ops.write(data);  
//			} catch (Exception e) {  
//				e.printStackTrace();  
//			} finally {  
//				try {  
//					if(fileIn!=null){  
//						fileIn.close();  
//					}  
//					if(ops!=null){  
//						ops.close();  
//					}  
//				} catch (IOException e) {  
//					e.printStackTrace();  
//				}  
//			} 
//		}
		//mysql
			if(fileIn!=null ){
				byte[] data = null;  
				data = FileCopyUtils.copyToByteArray(fileIn); 
				dto.setTemplateContent(data);
			}
			int count = dao.insertSysTemplate(paramMap);
			//SysTemplateDTO resultDto = (SysTemplateDTO) paramMap.get("dto");
		return dto.getId();
	}
	/**
     * @title: updateSysTemplate
     * @author
     * @description: 修改 模板对象
     * @date 2014-10-27 14:30:25
     * @param paramMap
     * @return
     * @throws
     */
	public void updateSysTemplate(SysTemplateDTO dto , InputStream fileIn ) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		/*dao.updateSysTemplate(paramMap);
		if(fileIn!=null){
			//锁定一行数据
			SysTemplateDTO prsitDto = dao.findSysTemplateByPrimaryKeyForUpdate(dto.getId().toString());
			Long keyId = dto.getId();
			BLOB content = (BLOB)prsitDto.getTemplateContent();
			OutputStream ops = null;  
			try {  
//			        ops = content.getBinaryOutputStream();//暂时使用这个废弃的方法  
				ops = content.setBinaryStream(0);//ojdbc14支持,ojdbc6,5都不支持  
				byte[] data = null;  
				data = FileCopyUtils.copyToByteArray(fileIn);  
				ops.write(data);  
			} catch (Exception e) {  
				e.printStackTrace();  
			} finally {  
				try {  
					if(fileIn!=null){  
						fileIn.close();  
					}  
					if(ops!=null){  
						ops.close();  
					}  
				} catch (IOException e) {  
					e.printStackTrace();  
				}  
			} 
		}*/
		//mysql
		if(fileIn!=null ){
			byte[] data = null;  
			data = FileCopyUtils.copyToByteArray(fileIn); 
			dto.setTemplateContent(data);
			//dao.updateSysTemplateContent(dto);
		}
		dao.updateSysTemplate(paramMap);
	}
	/**
     * @title: deleteSysTemplateByPrimaryKey
     * @author
     * @description: 删除 模板,按主键
     * @date 2014-10-27 14:30:25
     * @param paramMap
     * @throws
     */ 
	public void deleteSysTemplateByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteSysTemplateByPrimaryKey(paramMap);
	}

}

