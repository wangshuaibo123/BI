package com.fintech.modules.genCode.service;

import java.util.List;
import java.util.Map;

import com.fintech.modules.genCode.dto.ProductCodeDTO;

/**
 * 
	* @classname: IGenCodeService
	* @description: 
	* @author
 */
public interface IGenCodeService {

    /**
     * @title: queryListOfTable
     * @author
     * @description:查询数据库中的所有表
     * @date 2014-9-4 下午4:04:20
     * @param paramMap
     * @return
     * @throws Exception
     * @throws
     */
    public List<Map> queryListOfTable(Map<String, Object> paramMap) throws Exception;

    /**
     * @title: queryListOfTableCount
     * @author
     * @description: 查询数据库中表总数
     * @date 2014-9-9 下午2:42:31
     * @param paramMap
     * @return
     * @throws Exception
     * @throws
     */
    public int queryListOfTableCount(Map<String, Object> paramMap) throws Exception;

    /**
     * @title: queryColumnOfTable
     * @author
     * @description:查询指定表的所有字段信息
     * @date 2014-9-4 下午4:04:28
     * @param paramMap
     * @return
     * @throws Exception
     * @throws
     */
    public List<Map> queryColumnOfTable(Map<String, Object> paramMap) throws Exception;

    /**
     * @title: proDTOCode
     * @author
     * @description:生成 实体 Dto
     * @date 2014-9-4 下午4:04:35
     * @param paramMap
     * @return
     * @throws Exception
     * @throws
     */
    public ProductCodeDTO proDTOCode(Map<String, Object> paramMap) throws Exception;

    /**
     * @title: proActionCode
     * @author
     * @description:生成 Controller 层 
     * @date 2014-9-4 下午4:04:42
     * @param paramMap
     * @return
     * @throws Exception
     * @throws
     */
    public ProductCodeDTO proActionCode(Map<String, Object> paramMap) throws Exception;

    /**
     * @title: proMapperCode
     * @author
     * @description:生成 Mapper层
     * @date 2014-9-4 下午6:52:19
     * @param paramMap
     * @return
     * @throws Exception
     * @throws
     */
    public ProductCodeDTO proMapperCode(Map<String, Object> paramMap) throws Exception;

    /**
     * @title: proServiceCode
     * @author
     * @description:生成 service 层
     * @date 2014-9-4 下午4:04:37
     * @param paramMap
     * @return
     * @throws Exception
     * @throws
     */
    public ProductCodeDTO proServiceCode(Map<String, Object> paramMap) throws Exception;

    /**
     * @title: proSQLCode
     * @author
     * @description:生成 持久层 sql
     * @date 2014-9-4 下午4:04:39
     * @param paramMap
     * @return
     * @throws Exception
     * @throws
     */
    public ProductCodeDTO proSQLCode(Map<String, Object> paramMap) throws Exception;

	/**
	 * 生成展示层 view JSP
	 * @param paramMap
	 * @throws Exception
	 */
	public ProductCodeDTO proJSPCode(Map<String, Object> paramMap)throws Exception;
	
	/**
	 * 生成jsp 对应的 js 文件
	 * @param paraMap
	 * @return
	 * @throws Exception
	 */
	public ProductCodeDTO proJSCode(Map<String,Object> paraMap) throws Exception;
	/**
	 * 生成 rest java文件
	 * @param paraMap
	 * @return
	 * @throws Exception
	 */
	public ProductCodeDTO proRestCode(Map<String,Object> paraMap) throws Exception;

}
