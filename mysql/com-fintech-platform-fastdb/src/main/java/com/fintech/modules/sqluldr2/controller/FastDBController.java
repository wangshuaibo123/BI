package com.fintech.modules.sqluldr2.controller;


import com.fintech.modules.sqluldr2.TaskInfo;
import com.fintech.modules.sqluldr2.source.DefaultOracleSourceDataGetter;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

/**
 * @classname: FastDBController
 * @description: 快速导入导出数据库单表内容
 */
@Controller
@Scope("prototype")
@RequestMapping("/fastdb")
public class FastDBController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(FastDBController.class);

    @Autowired
    @Qualifier("com.fintech.modules.sqluldr2.source.DefaultOracleSourceDataGetter")
    private DefaultOracleSourceDataGetter biz;

    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        //跳转至 查询页面
        //model.setViewName("platform/bizauth/vmauthregisterinfo/"+operate);

        return model;
    }
    /**
     *获取 oracle转mysql的平面文件
     * @param taskInfo
     * @param pageData
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/obtainOracleToMysqlSQL")
    @ResponseBody
    public DataMsg obtainOracleToMysqlSQL(TaskInfo taskInfo, @ModelAttribute DataMsg pageData) throws Exception {
        List<Map> list = null;
        try {
            taskInfo.setId("1");

            //taskInfo.setSrcdbconn("user=ptuser/ptuser@172.18.100.86:1521/testdb");
            //taskInfo.setExpdb("sys_app_error_info.sql");
            //taskInfo.setTagtable("sys_app_error_info");
            //taskInfo.setToolsPath("F:/cgJYwork/fintech/src/main/webapp/component/tools/");
            //taskInfo.setLogFile(new File("F:/cgJYwork/fintech/src/main/webapp/component/tools/sqlunloader_logs.log"));
            //taskInfo.setLogCmdFile(new File("F:/cgJYwork/fintech/src/main/webapp/component/tools/log/exe_log.log"));


            taskInfo.setExecmode("APPEND");
            //taskInfo.setTagdbcols("ID ,NODE_NAME,APP_FLAG ,CREATE_TIME,CONCENT,FILE_NAME,LOG_LEVEL,LEVEL_SETUP_ID ,MATCHED_FLAG ");
            //taskInfo.setSrcSql("select ID ,NODE_NAME,APP_FLAG ,CREATE_TIME,CONCENT,FILE_NAME,LOG_LEVEL,LEVEL_SETUP_ID ,MATCHED_FLAG  from sys_app_error_info");

            biz.collectionValueToMysql(taskInfo);
            pageData.setMsg("操作成功");
        }catch (Exception e) {
            logger.error("=obtainOracleToMysqlSQL error",e);
            pageData.setMsg("操作失败"+e.getMessage());
        }

        return pageData;
    }

}
