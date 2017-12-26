package com.fintech.modules.logmonitor.sysappftpinfo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fintech.modules.logmonitor.sysappftpinfo.dto.SysAppFtpInfoDTO;
import com.fintech.modules.logmonitor.sysappftpinfo.service.SysAppFtpInfoService;
import com.fintech.platform.core.common.BaseDTO;
import com.fintech.platform.core.message.DataMsg;
import com.fintech.platform.core.message.PageParameter;
import com.fintech.platform.core.message.PageUtil;
import com.fintech.platform.core.message.QueryReqBean;
import com.fintech.platform.restservice.exception.AbaboonException;
import com.fintech.platform.restservice.web.base.BaseController;
import com.fintech.platform.tools.common.ZipUtil;
import com.fintech.platform.tools.ftp.FTPUtils;

/**
 * @classname: SysAppFtpInfoController
 * @description: 定义 业务系统节点FTP配置表 控制层
 * @author: lei
 */
@Controller
@Scope("prototype")
@RequestMapping("/sysAppFtpInfo")
public class SysAppFtpInfoController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SysAppFtpInfoController.class);

    @Autowired
    @Qualifier("com.fintech.modules.logmonitor.sysappftpinfo.service.SysAppFtpInfoService")
    private SysAppFtpInfoService service;

    /**
     * 通过 Controller 请求 跳转 进入至相关 页面
     */
    @RequestMapping(value = "/prepareExecute/{operate}")
    public ModelAndView execute(@PathVariable("operate") String operate) throws AbaboonException {
        ModelAndView model = new ModelAndView();

        if ("toQueryPage".equals(operate)) {// 跳转至 查询页面
            model.setViewName("platform/sysappftpinfo/querySysAppFtpInfo");
        } else if ("toAdd".equals(operate)) { // 跳转至 新增页面
            model.setViewName("platform/sysappftpinfo/addSysAppFtpInfo");
        } else if ("toUpdate".equals(operate)) {// 跳转至 修改页面
            String id = this.getParameterString("id");
            model = this.queryOneDTO(id);
            model.setViewName("platform/sysappftpinfo/updateSysAppFtpInfo");
        } else if ("toView".equals(operate)) {// 跳转至 查看页面
            String id = this.getParameterString("id");
            model = this.queryOneDTO(id);
            model.setViewName("platform/sysappftpinfo/viewSysAppFtpInfo");
        }

        return model;
    }

    /**
     * @author lei
     * @description:查询分页列表
     * @date 2015-04-03 10:06:16
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/queryListSysAppFtpInfo")
    @ResponseBody
    public DataMsg queryListSysAppFtpInfo(HttpServletRequest request, SysAppFtpInfoDTO dto,
            @ModelAttribute DataMsg dataMsg) throws Exception {

        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("dto", dto);
        QueryReqBean params = new QueryReqBean();
        params.setSearchParams(searchParams);
        PageParameter pageInfo = PageUtil.toPageParameter(dataMsg);
        params.setPageParameter(pageInfo);

        List<SysAppFtpInfoDTO> list = service.searchSysAppFtpInfoByPaging(params.getSearchParams());

        dataMsg.setData(list);
        dataMsg.setTotalRows(pageInfo.getTotalCount());
        return dataMsg;
    }

    /**
     * @author lei
     * @description:新增
     * @date 2015-04-03 10:06:16
     * @param request
     * @param dto
     * @param dataMsg
     * @return
     * @throws
     */
    @RequestMapping(value = "/insertSysAppFtpInfo")
    @ResponseBody
    public DataMsg insertSysAppFtpInfo(HttpServletRequest request, SysAppFtpInfoDTO dto, @ModelAttribute DataMsg dataMsg) {
        try {
            dto = (SysAppFtpInfoDTO) super.initDto(dto);

            service.insertSysAppFtpInfo(dto);

            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("新增成功");
            dataMsg.setStatus("ok");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            logger.error("执行方法insertSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author lei
     * @description:编辑
     * @date 2015-04-03 10:06:16
     * @param request
     * @param dto
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/updateSysAppFtpInfo")
    @ResponseBody
    public DataMsg updateSysAppFtpInfo(HttpServletRequest request, SysAppFtpInfoDTO dto, @ModelAttribute DataMsg dataMsg) {
        try {
            dto = (SysAppFtpInfoDTO) super.initDto(dto);

            service.updateSysAppFtpInfo(dto);

            dataMsg = super.initDataMsg(dataMsg);
            dataMsg.setMsg("修改成功");
            dataMsg.setStatus("ok");
            dataMsg.setData(this.makeJSONData(dto.getId()));
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            logger.error("执行方法updateSysResource异常：", e);
        }
        return dataMsg;
    }

    /**
     * @author lei
     * @description:删除
     * @date 2015-04-03 10:06:16
     * @param request
     * @param pageData
     * @return
     * @throws
     */
    @RequestMapping(value = "/deleteSysAppFtpInfo")
    @ResponseBody
    public DataMsg deleteSysAppFtpInfo(HttpServletRequest request, @ModelAttribute DataMsg dataMsg) {

        BaseDTO dto = super.initDto(null);
        String ids = (String) this.getParameter("ids");// 格式: 1,2,3
        dataMsg = super.initDataMsg(dataMsg);
        try {
            service.deleteSysAppFtpInfoByPrimaryKey(dto, ids);
            dataMsg.setMsg("删除成功");
            dataMsg.setStatus("ok");
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            logger.error("执行方法deleteSysResource异常：", e);

        }

        return dataMsg;
    }

    /**
     * @author lei
     * @description:通过主键查询 其明细信息
     * @date 2015-04-03 10:06:16
     * @param id
     * @return
     */
    private ModelAndView queryOneDTO(String id) throws AbaboonException {
        ModelAndView model = new ModelAndView();
        try {
            SysAppFtpInfoDTO dto = service.querySysAppFtpInfoByPrimaryKey(id);
            // 将信息放入 model 以便于传至页面 使用
            model.addObject("dto", dto);
        }
        catch (Exception e) {
            throw new AbaboonException("执行queryOneDTO异常：", e);
        }
        return model;
    }

    /**
     * 打包下载所有的错误日志
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downloadErrorLog")
    @ResponseBody
    public void downloadErrorLog(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute DataMsg dataMsg) throws Exception {

        try {
            Properties prop = new Properties();
            ClassPathResource cp = new ClassPathResource("log-ftp.properties");
            prop.load(cp.getInputStream());
            String localPath = prop.getProperty("errorLog_backPath").trim();

            // 获取本地的日志文件路径
            File file = new File(localPath);
            File zipFile = new File(localPath + ".zip");

            // 压缩文件
            List<File> filesDir = new ArrayList<File>();
            filesDir.add(file);
            ZipUtil.compressFiles(filesDir, zipFile);

            // 下载文件
            downloadZip(zipFile, response);
        }
        catch (Exception e) {
            dataMsg.failed(e.getMessage());
            logger.error("执行方法downloadErrorLog异常：", e);
        }

    }

    /**
     * 打包下载所有的错误日志
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/downloadAppLog")
    @ResponseBody
    public void downloadAppLog(HttpServletRequest request, HttpServletResponse response, @ModelAttribute DataMsg dataMsg)
            throws Exception {

        SysAppFtpInfoDTO dto = null;
        try {
            String id = (String) this.getParameter("v_id");

            // 下载文件
            Properties prop = new Properties();
            ClassPathResource cp = new ClassPathResource("log-ftp.properties");
            prop.load(cp.getInputStream());
            
            logger.debug("获取应用日志下载路径");
            String appLogPath = prop.getProperty("appLog_LocalPath").trim();
            String localAppPath = "";
            
            logger.debug("查询数据库中配置的ftp连接信息");
            dto = service.querySysAppFtpInfoByPrimaryKey(id);
            
            if (dto != null) {
                localAppPath = appLogPath + "/" + dto.getIp().replaceAll("[.]", "_");

                logger.debug("建立本地日志目录" + localAppPath );
                File localAppFile = new File(localAppPath);
                if (!localAppFile.exists()) {
                    localAppFile.mkdirs();
                }

                // 从远程目录下载日志文件
                logger.debug("连接远程ftp" + dto.getIp() + ":"+ dto.getPort());
                FTPUtils ftp = new FTPUtils(dto.getIp(), Integer.valueOf(dto.getPort()), dto.getUsername(),
                        dto.getPassword(), dto.getApplogDic());
                ftp.login();
                /*  下载目录下所有文件  
                for(String f:ftp.listFiles("*log.log")){
                    ftp.download( localAppPath + "/" +f, dto.getApplogDic() + "/" + f);
                }*/

                // 下载单个日志
                logger.debug("下载应用日志文件catalina.out");
                ftp.download(localAppPath + "/catalina.out", dto.getApplogDic() + "/catalina.out");

                ftp.closeServer();

                // 获取本地的日志文件路径
                File file = new File(localAppPath);
                File zipFile = new File(localAppPath + ".zip");

                // 压缩文件
                logger.debug("下载完毕，开始压缩日志文件：" + localAppPath + ".zip");
                List<File> filesDir = new ArrayList<File>();
                filesDir.add(file);
                ZipUtil.compressFiles(filesDir, zipFile);
                // 删除下载的文件
                logger.debug("删除下载的应用日志文件夹:" + localAppPath);
                deleteDirectory(localAppPath);

                // 下载文件并删除压缩文件
                logger.debug("下载并删除压缩后的日志文件" + localAppPath + ".zip");
                downloadZip(zipFile, response);

            }

        }
        catch (Exception e) {
            
            if (e instanceof ConnectException){
                logger.error("连接ftp：" + dto.getIp() + ":" + dto.getPort() + "失败", e);
                responseMsg("连接ftp失败" , response);
            }
            
            logger.error("执行方法downloadAppLog异常：", e);
            responseMsg("执行方法downloadAppLog异常:" + e.getMessage(), response);

        }

    }

    /**
     * 把下载文件以流的方式放入response
     * 
     * @param file
     * @param response
     * @return
     */
    public static HttpServletResponse downloadZip(File file, HttpServletResponse response) {
        InputStream fis = null;
        OutputStream toClient = null;
        try {
            // 以流的形式下载文件。
            fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);

            // 清空response
            response.reset();

            toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (toClient != null) {
                try {
                    toClient.close();
                }
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            // 删除文件
            file.delete();
        }
        return response;
    }

    /**
     * @Title: deleteDirectory
     * @Description: 删除文件夹及文件夹下内容
     * @param @param sPath
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     */
    public boolean deleteDirectory(String sPath) {
        // 如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        boolean flag = true;
        // 删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = files[i].delete();
                if (!flag)
                    break;
                // 删除子目录
            } else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag)
            return false;
        // 删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @Title: responseMsg 
     * @Description: 把响应信息放入response并返回
     * @param @param msgStr
     * @param @param response
     * @param @return    设定文件 
     * @return HttpServletResponse    返回类型 
     * @throws
     */
    public HttpServletResponse responseMsg(String msgStr, HttpServletResponse response) {
        OutputStream toClient = null;
        try {
            toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(msgStr.getBytes());
            toClient.flush();
            toClient.close();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {
            if (toClient != null) {
                try {
                    toClient.close();
                }catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return response;
    }
}
