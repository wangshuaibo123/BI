package com.easub.clip.service;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.easub.clip.dao.ClipVideoCopyrightSummaryStatDao;
import com.easub.clip.dto.ClipVideoCopyrightSummaryStatDTO;
import com.fintech.platform.core.common.BaseDTO;

/**
 * @classname: ClipVideoCopyrightSummaryStatService
 * @description: 定义  clip_video_copyright_summary_stat 实现类
 * @author:  jiangshuncheng
 */
@Service("com.easub.clip.service.ClipVideoCopyrightSummaryStatService")
public class ClipVideoCopyrightSummaryStatService implements Serializable {

    private static final long serialVersionUID = 1L;
    
	@Autowired
	private ClipVideoCopyrightSummaryStatDao dao;

	/**
     * @author jiangshuncheng
     * @description: 分页查询 clip_video_copyright_summary_stat列表
     * @date 2018-01-25 10:20:36
     * @param searchParams 条件
     * @return
     * @throws
     */ 
	public List<ClipVideoCopyrightSummaryStatDTO> searchClipVideoCopyrightSummaryStatByPaging(Map<String,Object> searchParams) throws Exception {
		List<ClipVideoCopyrightSummaryStatDTO> dataList =  dao.searchClipVideoCopyrightSummaryStatByPaging(searchParams);
		return dataList;
	}
	/**
     * @author jiangshuncheng
     * @description: 按条件查询clip_video_copyright_summary_stat列表
     * @date 2018-01-25 10:20:36
     * @param searchParams 条件
     * @return
     * @throws
     */
	public List<ClipVideoCopyrightSummaryStatDTO> searchClipVideoCopyrightSummaryStat(Map<String,Object> searchParams) throws Exception {
	    List<ClipVideoCopyrightSummaryStatDTO> dataList = dao.searchClipVideoCopyrightSummaryStat(searchParams);
        return dataList;
    }
	/**
     * @author jiangshuncheng
     * @description: 查询clip_video_copyright_summary_stat对象
     * @date 2018-01-25 10:20:36
     * @param id
     * @return
     * @throws
     */ 
	public ClipVideoCopyrightSummaryStatDTO queryClipVideoCopyrightSummaryStatByPrimaryKey(String id) throws Exception {
		
		ClipVideoCopyrightSummaryStatDTO dto = dao.findClipVideoCopyrightSummaryStatByPrimaryKey(id);
		
		if(dto == null) dto = new ClipVideoCopyrightSummaryStatDTO();
		
		return dto;
		
	}

	/**
     * @title: insertClipVideoCopyrightSummaryStat
     * @author jiangshuncheng
     * @description: 新增 clip_video_copyright_summary_stat对象
     * @date 2018-01-25 10:20:36
     * @param dto
     * @return
     * @throws
     */
	@SuppressWarnings("all")
	public Long insertClipVideoCopyrightSummaryStat(ClipVideoCopyrightSummaryStatDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		int count = dao.insertClipVideoCopyrightSummaryStat(paramMap);
		
		ClipVideoCopyrightSummaryStatDTO resultDto = (ClipVideoCopyrightSummaryStatDTO) paramMap.get("dto");
		Long keyId = resultDto.getId();
		return keyId;
	}
	/**
     * @title: updateClipVideoCopyrightSummaryStat
     * @author jiangshuncheng
     * @description: 修改 clip_video_copyright_summary_stat对象
     * @date 2018-01-25 10:20:36
     * @param paramMap
     * @return
     * @throws
     */
	public void updateClipVideoCopyrightSummaryStat(ClipVideoCopyrightSummaryStatDTO dto) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", dto);
		
		dao.updateClipVideoCopyrightSummaryStat(paramMap);
	}
	/**
     * @title: deleteClipVideoCopyrightSummaryStatByPrimaryKey
     * @author jiangshuncheng
     * @description: 删除 clip_video_copyright_summary_stat,按主键
     * @date 2018-01-25 10:20:36
     * @param paramMap
     * @throws
     */ 
	public void deleteClipVideoCopyrightSummaryStatByPrimaryKey(BaseDTO baseDto,String ids) throws Exception {
		if(StringUtils.isEmpty(ids)) throw new Exception("删除失败！传入的参数主键为null");
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dto", baseDto);
		paramMap.put("ids", ids);
		dao.deleteClipVideoCopyrightSummaryStatByPrimaryKey(paramMap);
	}
	
	public void deleteClipVideoCopyrightSummaryStat(Map<String,Object> map) throws Exception{
		this.dao.deleteClipVideoCopyrightSummaryStat(map);
	}

}

