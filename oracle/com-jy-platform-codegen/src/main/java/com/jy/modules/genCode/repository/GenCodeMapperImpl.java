package com.jy.modules.genCode.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jy.modules.genCode.util.DynamicDataSourceUtil;

@Service("com.jy.modules.genCode.repository.GenCodeMapperImpl")
public class GenCodeMapperImpl implements GenCodeMapper {
    private static final Logger logger = LoggerFactory.getLogger(GenCodeMapperImpl.class);
    
    /**
     * 获取SqlSession
     * @return
     * @throws Exception
     */
    private SqlSession getSqlSession() throws Exception{
        DataSource dataSource = DynamicDataSourceUtil.getDataSource();
        if(dataSource == null) return null;
        
        Environment environment = new Environment("development",  new JdbcTransactionFactory(), dataSource); 
        Configuration configuration = new Configuration(environment); 
        configuration.addMapper(GenCodeMapper.class); 

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration); 
        return sqlSessionFactory.openSession(); 
    }
    

    @Override
    public List<Map> queryTableNameOfDataBase(Map<String, Object> param) throws Exception {
        SqlSession session = getSqlSession();
        if(session == null) return null;
        List<Map> list = new ArrayList<Map>();
        
        try{
            list = session.selectList("queryTableNameOfDataBase", param);
        }
        catch(Exception e){
            logger.error("GenCodeMapperImpl.queryTableNameOfDataBase() error: ", e); 
        }
        finally{
            if(session != null){
                try{session.close();}catch(Exception e1){e1.printStackTrace();}
            }
            
            BasicDataSource dataSource = (BasicDataSource)DynamicDataSourceUtil.getDataSource();
            if(dataSource != null){
                try{dataSource.close();}catch(Exception e1){e1.printStackTrace();}
            }
        }
        
        return list;
    }

    @Override
    public List<Map> queryColumnListOfGenerateCode(Map<String, Object> param) throws Exception {
        SqlSession session = getSqlSession();
        if(session == null) return null;
        List<Map> list = new ArrayList<Map>();
        
        try{
            list = session.selectList("queryColumnListOfGenerateCode", param);
        }
        catch(Exception e){
            logger.error("GenCodeMapperImpl.queryColumnListOfGenerateCode() error: ", e); 
        }
        finally{
            if(session != null){
                try{session.close();}catch(Exception e1){e1.printStackTrace();}
            }
            
            BasicDataSource dataSource = (BasicDataSource)DynamicDataSourceUtil.getDataSource();
            if(dataSource != null){
                try{dataSource.close();}catch(Exception e1){e1.printStackTrace();}
            }
        }
        
        return list;
    }

    @Override
    public int queryTableNameCountOfDataBase(Map<String, Object> param) throws Exception {
        SqlSession session = getSqlSession();
        if(session == null) return 0;
        Integer i = 0;
        
        try{
            i = session.selectOne("queryTableNameCountOfDataBase", param);
        }
        catch(Exception e){
            logger.error("GenCodeMapperImpl.queryTableNameCountOfDataBase() error: ", e); 
        }
        finally{
            if(session != null){
                try{session.close();}catch(Exception e1){e1.printStackTrace();}
            }
            
            BasicDataSource dataSource = (BasicDataSource)DynamicDataSourceUtil.getDataSource();
            if(dataSource != null){
                try{dataSource.close();}catch(Exception e1){e1.printStackTrace();}
            }
        }
        
        return i;
    }

}
