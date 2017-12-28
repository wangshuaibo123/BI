package com.jy.modules.genCode.repository;

import java.util.List;
import java.util.Map;

import com.jy.platform.core.mybatis.MyBatisRepository;

@MyBatisRepository
public interface GenCodeMapper {

    public List<Map> queryTableNameOfDataBase(Map<String, Object> param) throws Exception;

    public List<Map> queryColumnListOfGenerateCode(Map<String, Object> param) throws Exception;

    public int queryTableNameCountOfDataBase(Map<String, Object> param) throws Exception;
}
