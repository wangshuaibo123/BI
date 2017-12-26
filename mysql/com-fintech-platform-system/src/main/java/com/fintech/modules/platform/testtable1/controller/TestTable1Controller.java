package com.fintech.modules.platform.testtable1.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.fintech.modules.platform.testtable1.dto.SysDataPrvDTO;
import com.fintech.modules.platform.testtable1.dto.TestTable1DTO;
import com.fintech.modules.platform.testtable1.service.TestTable1Service;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;

/**
 * @classname: TestTable1Controller
 * @description: 定义  测试表 控制层
 * @author
 */
@Controller
@Scope("prototype")
@RequestMapping("/testTable1")
public class TestTable1Controller extends BaseController{
    private static final Logger logger =  LoggerFactory.getLogger(TestTable1Controller.class);

    @Autowired
    @Qualifier("com.fintech.modules.platform.testtable1.service.TestTable1Service")
    private TestTable1Service service;
    
    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        
        if("toQueryPage".equals(operate)){//跳转至 查询页面
        	model.setViewName("platform/testtable1/queryTestTable1");
        }else if("toAdd".equals(operate)){ //跳转至 新增页面
        	model.setViewName("platform/testtable1/addTestTable1");
        }else if("toUpdate".equals(operate)){//跳转至 修改页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/testtable1/updateTestTable1");
        }else if("toView".equals(operate)){//跳转至 查看页面
        	String id = this.getParameterString("id");
        	model = this.queryOneDTO(id);
        	model.setViewName("platform/testtable1/viewTestTable1");
        }else if("toUserPrvs".equals(operate)){//跳转至查看用户权限集合——测试用
            String id = this.getParameterString("id");
            //调用service
            model = this.queryUserPrvs(id);
            model.setViewName("platform/testtable1/viewSysPrvBizUserALLResult");
        }else if("toQueryUserPrvPage".equals(operate)){// 调转至用户查询所求权限查询页面——暂时测试用
            model.setViewName("platform/testtable1/queryPrvUserAllResult");  
        }
        
        return model;
    }
    
    @RequestMapping(value = "/queryUserPrvPage")
    public ModelAndView queryUserPrvPage(HttpServletRequest request) throws AbaboonException{
        
        String id = request.getParameter("id");
        ModelAndView model = this.queryUserPrvs(id);
        model.setViewName("platform/testtable1/viewSysPrvBizUserALLResult");
        
        return model;
        
    }
    
    /**
     * @author
     * @description:查询分页列表
     * @date 2014-10-25 15:06:44
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListTestTable1")
    @ResponseBody
    public DataMsg queryListTestTable1(HttpServletRequest request, TestTable1DTO dto, @ModelAttribute DataMsg dataMsg) throws Exception {
        
    	Map<String, Object> searchParams = new HashMap<String, Object>();
    	dto.setOpUserId(2L);
    	dto.setUserOrgId(2L);
    	
    	searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
    	PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
		params.setPageParameter(pageInfo);
		
        List<TestTable1DTO> list = service.searchTestTable1ByPaging(params.getSearchParams());
        
        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }
    

    /**
     * @author
     * @description:新增
     * @date 2014-10-25 15:06:44
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertTestTable1")
    @ResponseBody
    public DataMsg insertTestTable1(HttpServletRequest request, TestTable1DTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (TestTable1DTO)super.initDto(dto);

            service.insertTestTable1(dto);
            
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
     * @date 2014-10-25 15:06:44
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateTestTable1")
    @ResponseBody
    public DataMsg updateTestTable1(HttpServletRequest request, TestTable1DTO dto, @ModelAttribute DataMsg dataMsg){
        try {
        	dto = (TestTable1DTO)super.initDto(dto);
           
            service.updateTestTable1(dto);
            
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
     * @date 2014-10-25 15:06:44
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteTestTable1")
    @ResponseBody
    public DataMsg deleteTestTable1(HttpServletRequest request, @ModelAttribute DataMsg dataMsg){
    	
    	BaseDTO dto = super.initDto(null);
   	 	String ids = (String) this.getParameter("ids");//格式: 1,2,3
   	 	dataMsg = super.initDataMsg(dataMsg);
		try {
			 service.deleteTestTable1ByPrimaryKey(dto,ids);
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
     * @date 2014-10-25 15:06:44
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try{
        	TestTable1DTO dto = service.queryTestTable1ByPrimaryKey(id);
            //将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }catch(Exception e){
        	throw new AbaboonException("执行queryOneDTO异常：",e);
        }
        return model;
    }
    
    /**
     * 
     * @param id
     * @return
     * @throws AbaboonException
     * @Description:通过用户ID查询用户权限集合——测试使用
     */
    private ModelAndView queryUserPrvs(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try {
            List<SysDataPrvDTO> dtos = service.searchUserPrvs(id);
          //将信息放入 model 以便于传至页面 使用
            model.addObject("dtos", dtos);
        } catch (Exception e) {
            throw new AbaboonException("执行queryUserPrvs异常：",e);
        }
        return model;
    }
    
}
