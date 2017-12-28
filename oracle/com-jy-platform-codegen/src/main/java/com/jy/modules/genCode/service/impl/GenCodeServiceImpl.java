package com.jy.modules.genCode.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.jy.modules.genCode.common.StringUtilTools;
import com.jy.modules.genCode.dto.ProJspDTO;
import com.jy.modules.genCode.dto.ProductCodeDTO;
import com.jy.modules.genCode.repository.GenCodeMapperImpl;
import com.jy.modules.genCode.service.IGenCodeService;
import com.jy.modules.genCode.util.CodeGenPropertiesInfo;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * @description: 代码 生成
 * @author chen_gang
 * @date:2014年9月25日下午9:25:35
 */
@Service("com.jy.modules.genCode.service.impl.GenCodeServiceImpl")
public class GenCodeServiceImpl implements IGenCodeService, Serializable {

    private static final long serialVersionUID = 3053871414748192069L;
    //是否使用新模板
    private static final boolean IS_NEW_TEMP = true;
    //定义 控制层生成的包后缀
    private static final String CONTROLLER_PACKAGE = ".controller";
    
    public static final String WEB_CONTROLLER = "";//"web/";
    //定义 model dto 生成的包后缀
    private static final String DTO_PACKAGE = ".dto";
    //定义 dao层 接口 包后缀
    private static final String MAPPER_PACKAGE = ".dao";
    //定义 service 层 包后缀
    private static final String ISERVICE_PACKAGE = ".service";
    //定义 业务层 实现类包后缀
    private static final String SERVICE_IMPL_PACKAGE = ".service";
    
    private static final String CONTROLLER_POSTFIX = "Controller";

    private static final String DTO_POSTFIX = "DTO";

    private static final String MAPPER_POSTFIX = "DAO";

    private static final String SERVICE_POSTFIX = "Service";

    private static final String SERVICE_IMPL_POSTFIX = "Service";

    private static final String JAVAFILE_POSTFIX = ".java";

    private static final String XMLFILE_POSTFIX = ".xml";

    private static final String CODE_PATH = "C:/generate/";
    
    public static final String JSP_PATH = "C:/generate/jsp/";
    public static final String JS_PATH = "C:/generate/js/";

    private static final String BASEDTO = "import com.jy.platform.core.common.BaseDTO;";

    @Autowired
    @Qualifier("com.jy.modules.genCode.repository.GenCodeMapperImpl")
    private GenCodeMapperImpl genCodeMapper;

    public List<Map> queryColumnOfTable(Map<String, Object> paramMap) throws Exception {
        List<Map> resultListOfColumn = genCodeMapper.queryColumnListOfGenerateCode(paramMap);
        return resultListOfColumn;
    }

    public int queryListOfTableCount(Map<String, Object> paramMap) throws Exception {
        return genCodeMapper.queryTableNameCountOfDataBase(paramMap);
    }

    public List queryListOfTable(Map<String, Object> paramMap) throws Exception {
        List resultListOfTable = genCodeMapper.queryTableNameOfDataBase(paramMap);
        return resultListOfTable;
    }

    /**
     * 1.通过表的英文名称查询其相关字段属性信。。 2.生成DTO,其DTO名称为MenuInfoDTO
     */
    
    public ProductCodeDTO proDTOCode(Map<String, Object> paramMap) throws Exception {
    	
        // 组装生成DTO时所需要的参数信息
        paramMap = makeupDTOParams(paramMap);
        // 获取组装好的参数信息
        ProductCodeDTO productDTO = (ProductCodeDTO) paramMap.get("productCodeDTO");
        List columnsList = productDTO.getColumnsList();
        // 开始生成DTO 实体
        writeDTOToFile(productDTO, columnsList);

        return productDTO;
    }

    public ProductCodeDTO proMapperCode(Map<String, Object> paramMap) throws Exception {
        // TODO
        // 组装生成DTO时所需要的参数信息
        Map<String, Object> dtoParamMap = makeupDTOParams(paramMap);
        // 组装 service 时所需的参数
        Map<String, Object> mapperParamMap = makeupMapperParams(dtoParamMap);
        // 开始生成Mapper
        Map<String, Object> tempMap = productMapperByTemplate(mapperParamMap);

        return null;
    }

    public ProductCodeDTO proSQLCode(Map<String, Object> paramMap) throws Exception {
        // 组装生成SQL时所需要的参数信息
        Map<String, Object> dtoParamMap = makeupSQLParams(paramMap);
        // 开始自动生成 sql
        proSQLByTemplate(dtoParamMap);

        ProductCodeDTO proSQLDTO = (ProductCodeDTO) dtoParamMap.get("productCodeDTO");

        return proSQLDTO;
    }

    public ProductCodeDTO proServiceCode(Map<String, Object> paramMap) throws Exception {
        // 组装生成DTO时所需要的参数信息
        Map<String, Object> dtoParamMap = makeupDTOParams(paramMap);
        // 组装 service 时所需的参数
        Map<String, Object> serviceParamMap = makeupServiceParams(dtoParamMap);

        // 开始生成Iservice 和serviceImpl
        Map<String, Object> providerTempMap = productServiceByTemplate(serviceParamMap);

        return null;
    }
/*
 * (non-Javadoc)
 * @see com.jy.demo.dao.service.IGenCodeService#proActionCode(java.util.Map)
 */
    public ProductCodeDTO proActionCode(Map<String, Object> paramMap) throws Exception {

        Map<String, Object> actionMap = makeupActionParams(paramMap);

        Map<String, Object> resultMap = this.productActionByTemplate(actionMap);

        ProductCodeDTO productDTO = (ProductCodeDTO) resultMap.get("codeDTO");

        return productDTO;
    }

    private Map<String, Object> makeupMapperParams(Map<String, Object> paramMap) {
        // TODO Auto-generated method stub
        ProductCodeDTO productDTO = (ProductCodeDTO) paramMap.get("productCodeDTO");

        productDTO = this.makeupProductDtoParams(productDTO);

        paramMap.put("productCodeDTO", productDTO);
        return paramMap;
    }

    private Map<String, Object> productMapperByTemplate(Map<String, Object> data) throws Exception {
        // TODO Auto-generated method stub
        ProductCodeDTO productDTO = (ProductCodeDTO) data.get("productCodeDTO");

        Map<String, Object> dataOfTemplate = productTemplate(productDTO);

        String formatedTabName = productDTO.getDtoClassName().substring(0, productDTO.getDtoClassName().indexOf("DTO"));
        // 开始生成Mapper 接口
        String templateMapperName = "template_Mapper.java";
        String fileDir = productDTO.getTargetDir() + productDTO.getMapperPackageName().replaceAll("\\.", "/");
        String fileNamePath = productDTO.getTargetDir() + productDTO.getMapperPackageName().replaceAll("\\.", "/")
                + "/" + productDTO.getMapperClassName() + JAVAFILE_POSTFIX;

        // String my = "E:/template/" + templateMapperName;

        Template template = getConfiguration(productDTO.getTemplatePath()).getTemplate(templateMapperName);
        FileUtils.forceMkdir(new File(fileDir + "/"));

        Writer out = new OutputStreamWriter(new FileOutputStream(fileNamePath), "UTF-8");
        template.process(dataOfTemplate, out);
        out.flush();
        out.close();

        productDTO.setFormated_tab_name(formatedTabName);
        productDTO.setLower_tab_name(productDTO.getTableName().toLowerCase());
        data.put("productCodeDTO", productDTO);
        // 将data 数据全部装入 dataOfTemplate
        dataOfTemplate.putAll(data);
        return dataOfTemplate;
    }

    /**
     * 组装生成DTO的参数信息
     * 
     * @param paramMap
     * @return
     * @throws Exception
     */
    private Map<String, Object> makeupDTOParams(Map<String, Object> paramMap) throws Exception {
    	/*String templatePath = this.getClass().getClassLoader().getResource("/").getPath();
        if (StringUtils.isNotEmpty(templatePath)) {
            dto.setTemplatePath(templatePath + "/WEB-INF/classes/genCodeTemplate/");
        }*/
        
        // 返回的信息存放在 proDTO
        // 表名称
        ProductCodeDTO proDTO = (ProductCodeDTO) paramMap.get("productCodeDTO");
        // 实体所在包名 com.platform.um
        String packageNamePrefix = proDTO.getPackageNamePrefix();

        String tableName = proDTO.getTableName();

        // 生成的文件存储位置：
        // String targetDir = BaseConfiguration.getString("generate.code.path");
//        String targetDir = CODE_PATH;
        
        String timestamp = (String)paramMap.get("timestamp");
        
//        ClassLoader cl = GenerateCodeController.class.getClassLoader();
//        URL url = cl.getResource("genCodeTemplate");
//        String urlPath = url.getFile();
//        String targetDir = urlPath.substring(0, (urlPath.indexOf("WEB-INF/"))+"WEB-INF/".length())+ "/classes/gencode/"+timestamp+"/";
        
        String codeGenDir = CodeGenPropertiesInfo.getValByKey("codegen.dir");
        String targetDir = codeGenDir + timestamp + "/";
        
        // 作者
        String author = System.getProperty("user.name");
        if("chen_gng".equals(author)){
        	author = "chen_gang";
        }
        if (StringUtils.isNotEmpty(tableName) && StringUtils.isNotEmpty(targetDir)) {
            // 获取 package 后缀 如：menuInfo
            String packageNamePostfix = StringUtilTools.convertName(tableName, true);
            String dtoPackageName = packageNamePrefix ;
            
            if(IS_NEW_TEMP) dtoPackageName = dtoPackageName + "."+ packageNamePostfix;
            dtoPackageName +=  DTO_PACKAGE;
            
            // 获取 实体名称 前缀 如：MenuInfo
            String dtoNamePrefix = StringUtilTools.convertName(tableName, false);
            String dtoNamePostfix = DTO_POSTFIX;
            // DTO 类名
            String dtoClassName = dtoNamePrefix + dtoNamePostfix;

            // 表列名的列表信息
            paramMap.put("tableName", tableName);

            List columnsList = this.queryColumnListOfGenerateCode(paramMap);

            String tableComments = "";
            if (columnsList != null && columnsList.size() > 0)
                tableComments = (String) ((Map) columnsList.get(0)).get("TABLE_COMMENTS");
            if (!StringUtils.isNotEmpty(tableComments)) {
                tableComments = tableName;
            }
            // 需要返回的信息
            proDTO.setDtoClassName(dtoClassName);
            proDTO.setColumnsList(columnsList);
            proDTO.setDtoPackageName(dtoPackageName.toLowerCase());
            proDTO.setTableName(tableName);
            proDTO.setTargetDir(targetDir.toLowerCase());
            proDTO.setAuthor(author);
            proDTO.setTableComments(tableComments);
            proDTO.setModelName(StringUtilTools.convertName(tableName, true));

            paramMap.put("productCodeDTO", proDTO);
            paramMap.put("columnsList", columnsList);
        } else {
            throw new Exception("代码生成失败【表名称不能为空】");
        }
        return paramMap;
    }

    public List queryColumnListOfGenerateCode(Map<String, Object> paramMap) throws Exception {
        List resultList = new ArrayList();
        List list = genCodeMapper.queryColumnListOfGenerateCode(paramMap);
        List jspList = (List) paramMap.get("jspParamList");
        // 将返回的 list 中部门属性替换为 页面设置的属性信息
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                Map temp = (Map) list.get(i);
                if (jspList != null && jspList.size() > 0)
                    for (int k = 0; k < jspList.size(); k++) {
                        ProJspDTO jsp = (ProJspDTO) jspList.get(k);

                        if (String.valueOf(temp.get("COLUMN_NAME")).equals(jsp.getColumnName())) {
                            temp.put("COLUMN_NAME", jsp.getColumnName());
                            temp.put("DATA_TYPE", jsp.getDataType());
                            // temp.put("DATA_LENGTH", jsp.getDataLength());
                            temp.put("COLUMN_COMMENTS", jsp.getColumnComments().replaceAll(" ", "").replaceAll("\n", "").replaceAll("\r", ""));
                            temp.put("MY_DISPALY", jsp.getMyDisplay());
                            temp.put("MY_VIEW", jsp.getMyView());
                            break;
                        }
                    }
                resultList.add(temp);
            }
        }
        return resultList;
    }

    /**
     * 生成DTO 类
     * 
     * @param className
     * @param targetDir
     * @param packageName
     * @param author
     * @param tableName
     * @param fields
     * @param comments
     * @param dataTypes
     */
    private void writeDTOToFile(ProductCodeDTO proCodeDTO, List columnsList) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curTime = sdf.format(new Date());
        Writer writer = null;
        File file = null;
        String relativePath = proCodeDTO.getDtoPackageName().replaceAll("\\.", "/") + "/";
        try {
            String javaFileName = new StringBuilder(proCodeDTO.getTargetDir()).append(relativePath)
                    .append(proCodeDTO.getDtoClassName()).append(JAVAFILE_POSTFIX).toString();
            // 先创建目录
            File dir = new File(proCodeDTO.getTargetDir() + relativePath);
            if (!dir.exists())
                dir.mkdirs();
            // 创建文件
            file = new File(javaFileName);
            // if (!file.exists())
            file.createNewFile();
            // 向文件内写信息
            writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
            StringBuffer content = new StringBuffer();
            content.append((new StringBuilder("package ")).append(proCodeDTO.getDtoPackageName()).append(";\n\n")
                    .toString());
            content.append(BASEDTO).append("\n\n");
            // content.append("import javax.persistence.Column;\n");
            // content.append("import javax.persistence.Entity;\n");
            // content.append("import javax.persistence.Table;\n");
            content.append("/**\n");
            content.append((new StringBuilder(" *@Description:")).append(proCodeDTO.getTableComments()).append("\n")
                    .toString());
            content.append((new StringBuilder(" *@author ")).append(proCodeDTO.getAuthor()).append("\n").toString());
            content.append((new StringBuilder(" *@version 1.0")).append("\n").toString());
            content.append((new StringBuilder(" *@date ")).append(curTime).append("\n").toString());
            content.append(" */\n");
            // content.append("@Entity\n");
            // content.append((new
            // StringBuilder("@Table(name =\"")).append(tableName).append("\")\n").toString());
            content.append((new StringBuilder("public class ")).append(proCodeDTO.getDtoClassName())
                    .append(" extends BaseDTO{\n").toString());
            content.append("\t").append("private static final long serialVersionUID = 1L;").append("\n\n");
            // 开始生成 private String id;
            for (int i = 0; i < columnsList.size(); i++) {
                Map temMap = (Map) columnsList.get(i);
                String comment = (String) temMap.get("COLUMN_COMMENTS");
                String dataType = (String) temMap.get("DATA_TYPE");
                String field = (String) temMap.get("COLUMN_NAME");
                
                if(StringUtils.isEmpty(comment)) comment = field;
                content.append((new StringBuilder("\t/**")).append(comment).append("*/\n").toString());
                // content.append((new
                // StringBuilder("\t@Column(name =\"")).append(fields[i].toLowerCase()).append("\")\n").toString());
                content.append((new StringBuilder("\tprivate ")).append(StringUtilTools.formatDataType(dataType))
                        .append(" ").append(StringUtilTools.convertName(field, true)).append(";\n\n").toString());
            }

            // 开始生成 set get 方法
            for (int i = 0; i < columnsList.size(); i++) {
                Map temMap = (Map) columnsList.get(i);
                String comment = (String) temMap.get("COLUMN_COMMENTS");
                String dataType = (String) temMap.get("DATA_TYPE");
                String field = (String) temMap.get("COLUMN_NAME");

                // 组装 get 方法 注释
                String getComments = (new StringBuilder("\t/**\n\t *方法: 获得"))
                        .append(comment).append("\n\t *@return: ")
                        .append(StringUtilTools.formatDataType(dataType)).append("  ")
                        .append(StringUtilTools.convertName(field, true)).append("\n\t */\n").toString();
                content.append(getComments);

                content.append((new StringBuilder("\tpublic "))
                        .append(StringUtilTools.formatDataType(dataType))
                        .append(" get")
                        .append(StringUtilTools.convertName(field, true).substring(0, 1).toUpperCase())
                        .append(StringUtilTools.convertName(field, true).substring(1,
                                StringUtilTools.convertName(field, true).length())).append("(){\n").toString());
                content.append((new StringBuilder("\t\treturn this.")).append(StringUtilTools.convertName(field, true))
                        .append(";\n").toString());
                content.append("\t}\n\n");
                content.append("\t/**\n");
                content.append((new StringBuilder("\t *方法: 设置")).append(comment)
                        .append("\n").toString());
                content.append((new StringBuilder("\t *@param: ")).append(StringUtilTools.formatDataType(dataType))
                        .append("  ").append(StringUtilTools.convertName(field, true)).append("\n").toString());
                content.append("\t */\n");
                content.append((new StringBuilder("\tpublic void set"))
                        .append(StringUtilTools.convertName(field, true).substring(0, 1).toUpperCase())
                        .append(StringUtilTools.convertName(field, true).substring(1,
                                StringUtilTools.convertName(field, true).length())).append("(")
                        .append(StringUtilTools.formatDataType(dataType)).append(" ")
                        .append(StringUtilTools.convertName(field, true)).append("){\n").toString());
                content.append((new StringBuilder("\t\tthis.")).append(StringUtilTools.convertName(field, true))
                        .append(" = ").append(StringUtilTools.convertName(field, true)).append(";\n").toString());
                content.append("\t}\n\n");
            }

            content.append("}");
            writer.write(content.toString());
            writer.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
            file.delete();
        }
        finally {
            if (writer != null)
                try {
                    writer.flush();
                    writer.close();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    public ProductCodeDTO makeupProductDtoParams(ProductCodeDTO dto) {
        // 实体所在包名 com.jy.demo
        String packageNamePrefix = dto.getPackageNamePrefix();

        // 获取 模块名 如：ucTFunction
        String packageNamePostfix = dto.getModelName();
        packageNamePrefix = packageNamePrefix+ "."+packageNamePostfix;
        // IService包名
        String iServicePackageName = packageNamePrefix + ISERVICE_PACKAGE;// com.jy.demo.dao.service
        String dtoNamePrefix = "I" + StringUtilTools.convertName(dto.getTableName(), false);// IUcTFunction
        String dtoNamePostfix = SERVICE_POSTFIX;
        // IService 类名
        String iServiceClassName = dtoNamePrefix + dtoNamePostfix;// IUcTFunctionService

        // ServiceImpl包名
        String serviceImplPackageName = packageNamePrefix + SERVICE_IMPL_PACKAGE;// com.jy.demo.dao.service.impl
        String serviceImplPrefix = StringUtilTools.convertName(dto.getTableName(), false);
        String serviceImplPostfix = SERVICE_IMPL_POSTFIX;
        // ServiceImpl 类名
        String serviceImplClassName = serviceImplPrefix + serviceImplPostfix;// UcTFunctionServiceImpl

        // Mapper包名
        String mapperPackageName = packageNamePrefix + MAPPER_PACKAGE;// com.jy.demo.dao.repository
        String mapperNamePrefix = StringUtilTools.convertName(dto.getTableName(), false);
        String mapperNamePostfix = MAPPER_POSTFIX;
        // Mapper 类名
        String mapperClassName = mapperNamePrefix + mapperNamePostfix;// UcTFunctionMapper

        // Controller包名
        String actionPackageName = packageNamePrefix + CONTROLLER_PACKAGE;
        String actionNamePrefix = StringUtilTools.convertName(dto.getTableName(), false);
        String actionNamePostfix = CONTROLLER_POSTFIX;
        // action 类名
        String actionClassName = actionNamePrefix + actionNamePostfix;

        //rest
        String restPackageName = packageNamePrefix + ".rest";
        String restNamePrefix = StringUtilTools.convertName(dto.getTableName(), false);
        String restClassName = restNamePrefix +"Rest";
        		
        // 需要返回的Mapper信息
        dto.setMapperClassName(mapperClassName);
        dto.setMapperPackageName(mapperPackageName.toLowerCase());
        // 需要返回的IService信息
        dto.setIServiceClassName(iServiceClassName);
        dto.setIServicePackageName(iServicePackageName.toLowerCase());
        // 需要返回的ServiceImpl信息
        dto.setServiceClassName(serviceImplClassName);
        dto.setServicePackageName(serviceImplPackageName.toLowerCase());

        dto.setActionClassName(actionClassName);
        dto.setActionPackageName(actionPackageName.toLowerCase());
        
        dto.setRestClassName(restClassName);
        dto.setRestPackageName(restPackageName.toLowerCase());
        
        dto.setCurtDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        return dto;
    }

    /**
     * 组装生成Iservice 的参数信息
     * 
     * @param paramMap
     * @return
     * @throws Exception
     */
    private Map<String, Object> makeupServiceParams(Map<String, Object> paramMap) throws Exception {

        ProductCodeDTO productDTO = (ProductCodeDTO) paramMap.get("productCodeDTO");

        productDTO = this.makeupProductDtoParams(productDTO);

        paramMap.put("productCodeDTO", productDTO);

        return paramMap;
    }

    /**
     * 生成 sql
     * 
     * @param dtoParamMap
     * @return
     * @throws Exception
     */
    private void proSQLByTemplate(Map<String, Object> dtoParamMap) throws Exception {
        ProductCodeDTO productDTO = (ProductCodeDTO) dtoParamMap.get("productCodeDTO");

        // 生成 sql.xml
        String sqlTemplate = "template_sql.xml";
        String sqlFileDir = productDTO.getTargetDir() + productDTO.getMapperPackageName();
        String sqlFileNamePath = sqlFileDir + "."+ productDTO.getSqlFileName() ;
        
        sqlFileDir = sqlFileDir.replaceAll("\\.", "/");
        sqlFileNamePath = sqlFileNamePath.replaceAll("\\.", "/").replace("DAO", "Mapper") + XMLFILE_POSTFIX;
        
        Template templateSQL = getConfiguration(productDTO.getTemplatePath()).getTemplate(sqlTemplate);
        // 生成目录
        FileUtils.forceMkdir(new File(sqlFileDir + "/"));
        // 生成文件
        Writer outImpl = new OutputStreamWriter(new FileOutputStream(sqlFileNamePath), "UTF-8");
        templateSQL.process(dtoParamMap, outImpl);
        outImpl.flush();
        outImpl.close();

    }

    @SuppressWarnings("all")
    private Map<String, Object> makeupSQLParams(Map<String, Object> paramMap) {
        // 表名称
        ProductCodeDTO productDTO = (ProductCodeDTO) paramMap.get("productCodeDTO");
        List columnsList = productDTO.getColumnsList();
        List foramtColumns = new ArrayList();
        for (int i = 0; i < columnsList.size(); i++) {
            Map temp = (Map) columnsList.get(i);
            String column = (String) temp.get("COLUMN_NAME");
            String dataType = (String) temp.get("DATA_TYPE");
            dataType = StringUtilTools.formatDBDataType(dataType);
            // 如果当前字段是主键ID id的位置在第一位
            if ("ID".equals(column)) {
                temp.put("TEMP_ROWNUM", "IS_ID");
            } else {
                temp.put("TEMP_ROWNUM", "IS_NOT_ID");
            }
            temp.put("IS_LAST", "N");
            // #dto.id#
            String dtoColumnName = "dto." + StringUtilTools.convertName(column, true);
            // 如：pk_dsdoc ,
            String formatColumnName = StringUtilTools.formatSQLColumns(column.toLowerCase());
            // 如果是最后一个 字段则不需要有","
            if (i + 1 == columnsList.size()) {
                formatColumnName = formatColumnName.replace(",", "");
                dtoColumnName = dtoColumnName.replace(",", "");
                temp.put("IS_LAST", "Y");
            }

            temp.put("DTO_COLUMN_NAME", dtoColumnName);
            temp.put("FORMAT_COLUMN_NAME", formatColumnName);
            temp.put("DATA_TYPE", dataType);

            foramtColumns.add(temp);
        }

        String sqlFileName = productDTO.getFormated_tab_name() + MAPPER_POSTFIX;
        // String sqlFileName = productDTO.getLower_tab_name() + "_sql";
        // 需要返回的信息
        productDTO.setSqlFileName(sqlFileName);

        paramMap.put("productCodeDTO", productDTO);
        paramMap.put("dataInfoList", foramtColumns);
        
        paramMap.put("formated_tab_name", StringUtilTools.convertName( productDTO.getTableName(), false));
        paramMap.put("table_comment", productDTO.getTableName());
        
        return paramMap;
    }

    /**
     * 通过freemark生成标准service
     * 
     * @param data
     * @throws Exception
     */
    private Map<String, Object> productServiceByTemplate(Map<String, Object> data) throws Exception {

        ProductCodeDTO productDTO = (ProductCodeDTO) data.get("productCodeDTO");
        Map<String, Object> dataOfTemplate = productTemplate(productDTO);

        String formatedTabName = productDTO.getDtoClassName().substring(0, productDTO.getDtoClassName().indexOf("DTO"));

        // 开始生成Iservice 接口
        /*String templateIServiceName = "template_IService.java";
        String fileDir = productDTO.getTargetDir() + productDTO.getIServicePackageName().replaceAll("\\.", "/");
        String fileNamePath = productDTO.getTargetDir() + productDTO.getIServicePackageName().replaceAll("\\.", "/")
                + "/" + productDTO.getIServiceClassName() + JAVAFILE_POSTFIX;

        // String my = "E:/template/" + templateIServiceName;

        Template template = getConfiguration(productDTO.getTemplatePath()).getTemplate(templateIServiceName);
        FileUtils.forceMkdir(new File(fileDir + "/"));

        Writer out = new OutputStreamWriter(new FileOutputStream(fileNamePath), "UTF-8");
        template.process(dataOfTemplate, out);
        out.flush();
        out.close();*/

        // 生成serviceImpl 接口实现类
        String implTemplate = "template_ServiceImpl.java";
        String implFileDir = productDTO.getTargetDir() + productDTO.getServicePackageName().replaceAll("\\.", "/");
        String implFileNamePath = productDTO.getTargetDir() + productDTO.getServicePackageName().replaceAll("\\.", "/")
                + "/" + productDTO.getServiceClassName() + JAVAFILE_POSTFIX;

        System.err.println("-----------------:"+productDTO.getTemplatePath());
        Template templateSI = getConfiguration(productDTO.getTemplatePath()).getTemplate(implTemplate);
        // 生成目录
        FileUtils.forceMkdir(new File(implFileDir + "/"));
        // 生成文件
        Writer outImpl = new OutputStreamWriter(new FileOutputStream(implFileNamePath), "UTF-8");
        templateSI.process(dataOfTemplate, outImpl);
        outImpl.flush();
        outImpl.close();

        productDTO.setFormated_tab_name(formatedTabName);
        productDTO.setLower_tab_name(productDTO.getTableName().toLowerCase());
        data.put("productCodeDTO", productDTO);
        // 将data 数据全部装入 dataOfTemplate
        dataOfTemplate.putAll(data);
        return dataOfTemplate;
    }

    private Configuration getConfiguration(String path) throws IOException {
        Configuration cfg = new Configuration();
        // String path = BaseConfiguration.getString("generate.template.path");
        // String path = "E:/template/";
        cfg.setDirectoryForTemplateLoading(new File(path));
        cfg.setLocale(Locale.CHINA);
        cfg.setDefaultEncoding("UTF-8");
        return cfg;
    }

    /**
     * 组装生成 action 的参数信息
     * 
     * @param paramMap
     * @return
     * @throws Exception
     */
    private Map<String, Object> makeupActionParams(Map<String, Object> paramMap) throws Exception {

        ProductCodeDTO productDTO = (ProductCodeDTO) paramMap.get("productCodeDTO");

        productDTO = this.makeupProductDtoParams(productDTO);

        paramMap.put("productCodeDTO", productDTO);

        return paramMap;
    }

    public Map<String, Object> productTemplate(ProductCodeDTO productDTO) throws Exception {
        Map<String, Object> dataOfTemplate = new HashMap<String, Object>();
        dataOfTemplate.put("codeDTO", productDTO);
        dataOfTemplate.put("dtoPackageName", productDTO.getDtoPackageName());
        dataOfTemplate.put("dtoClassName", productDTO.getDtoClassName());
        dataOfTemplate.put("table_comment", productDTO.getTableComments());
        dataOfTemplate.put("author", productDTO.getAuthor());
        dataOfTemplate.put("curtDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String formatedTabName = productDTO.getDtoClassName().substring(0, productDTO.getDtoClassName().indexOf("DTO"));

        dataOfTemplate.put("formated_tab_name", formatedTabName);
        dataOfTemplate.put("iServicePackageName", productDTO.getIServicePackageName());
        dataOfTemplate.put("iServiceClassName", productDTO.getIServiceClassName());

        dataOfTemplate.put("serviceClassName", productDTO.getServiceClassName());
        dataOfTemplate.put("servicePackageName", productDTO.getServicePackageName());

        dataOfTemplate.put("mapperClassName", productDTO.getMapperClassName());
        dataOfTemplate.put("mapperPackageName", productDTO.getMapperPackageName());

        dataOfTemplate.put("lower_tab_name", productDTO.getTableName().toLowerCase());
        dataOfTemplate.put("formated_tab_name", formatedTabName);
        dataOfTemplate.put("transTableName", StringUtilTools.convertName( productDTO.getTableName(), true));
        dataOfTemplate.put("jspPrefix", productDTO.getJspPrefix());

        return dataOfTemplate;
    }

    /**
     * 具体生成 action
     * 
     * @param paramMap
     * @return
     * @throws Exception
     */
    private Map<String, Object> productActionByTemplate(Map<String, Object> paramMap) throws Exception {

        ProductCodeDTO productDTO = (ProductCodeDTO) paramMap.get("productCodeDTO");

        Map<String, Object> dataOfTemplate = productTemplate(productDTO);
        
        dataOfTemplate.put("jspPrefix", productDTO.getJspPrefix());
        String templateAction = "template_Action.java";
        // 获取表属性 Y:子表，N：为主表
        String tabProperty = productDTO.getTableIsSub();
        // 如果是主表的话，是否关联子表 Y：是，N：否
        String isRelationSub = productDTO.getRelationSub();

        // 如果主表 设置了关联子表，则按主子表关联 生成主表信息
        if ("N".equals(tabProperty) && "Y".equals(isRelationSub)) {
            // 取主子表关联关系的主表 action模板生成
            templateAction = "/oneToMore/tem_Action_1.java";
        }

        // 开始生成aciton
        String fileDir =  productDTO.getTargetDir() + WEB_CONTROLLER + productDTO.getActionPackageName().replaceAll("\\.", "/");
        
        String fileNamePath = productDTO.getTargetDir() + WEB_CONTROLLER+ productDTO.getActionPackageName().replaceAll("\\.", "/")
                + "/" + productDTO.getActionClassName() + JAVAFILE_POSTFIX;
        
        Template template = getConfiguration(productDTO.getTemplatePath()).getTemplate(templateAction);
        FileUtils.forceMkdir(new File(fileDir + "/"));

        Writer out = new OutputStreamWriter(new FileOutputStream(fileNamePath), "UTF-8");
        template.process(dataOfTemplate, out);
        out.flush();
        out.close();

        return dataOfTemplate;
    }
/*
 * (non-Javadoc)
 * @see com.jy.demo.dao.service.IGenCodeService#proJSPCode(java.util.Map)
 */
	public ProductCodeDTO proJSPCode(Map<String, Object> paramMap)
			throws Exception {
		Map<String,Object> jspParamMap  = makeupJSPParams(paramMap);
		//开始自动生成 jsp
		proJSPByTemplate(jspParamMap);
		
		ProductCodeDTO proJSPDTO  = (ProductCodeDTO) jspParamMap.get("productCodeDTO");
		
		return proJSPDTO;
	}
	/**
	 * 组装生成jsp需要的参数信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	private Map<String, Object> makeupJSPParams(Map<String, Object> paramMap)  throws Exception{
		//表名称
		ProductCodeDTO productDTO = (ProductCodeDTO) paramMap.get("productCodeDTO");
		//设置生成 jsp 的目录
		//productDTO.setJspTargetDir(GENERATE_JSP_PATH);
		//productDTO.setModelName(StringUtilTools.convertName(productDTO.getTableName(), true));
		
		List columnsList = productDTO.getColumnsList();
		//List foramtColumns = new ArrayList();
		List hideList = new ArrayList();
		List disList = new ArrayList();
		//将原始columnsList 分成显示 隐藏的两个list
		for(int i = 0;i < columnsList.size(); i ++){
			Map temp = (Map) columnsList.get(i);
			String columnName = (String) temp.get("COLUMN_NAME");
			String isDis = (String) temp.get("MY_DISPALY");
			if("隐藏".equals(isDis) || "ID".equalsIgnoreCase(columnName)
					|| "VALIDATE_STATE".equalsIgnoreCase(columnName)|| "CREATED_BY".equalsIgnoreCase(columnName)
					|| "LAST_UPD_BY".equalsIgnoreCase(columnName)|| "LAST_UPD".equalsIgnoreCase(columnName)
					|| "MODIFY_TIME".equalsIgnoreCase(columnName)|| "OWNER_ID".equalsIgnoreCase(columnName)
					|| "ORG_ID".equalsIgnoreCase(columnName)|| "MODIFY_BY".equalsIgnoreCase(columnName)
			){
				hideList.add(temp);
			}else{
				//显示
				disList.add(temp);
			}
			
		}
		disList = makeupFormatColumnLis(disList);
		
		hideList = makeupFormatColumnLis(hideList);
		
		
		String queryJspName = "query"+StringUtilTools.convertName(productDTO.getTableName(), false);
		String updateJspName = "update"+StringUtilTools.convertName(productDTO.getTableName(), false);
		String addJspName = "add"+StringUtilTools.convertName(productDTO.getTableName(), false);
		String viewJSPName = "view"+StringUtilTools.convertName(productDTO.getTableName(), false);
		//需要返回的信息
		productDTO.setQueryJspName(queryJspName);
		productDTO.setUpdateJspName(updateJspName);
		productDTO.setAddJspName(addJspName);
		productDTO.setViewJspName(viewJSPName);
		String dwrName = StringUtilTools.convertName(productDTO.getTableName(), true)+"_dwr";
		productDTO.setDwrName(dwrName);
		//组装 生成 query jsp的参数
		paramMap.put("jspDTO", productDTO);
		paramMap.put("dataInfoList", disList);
		//放入需要隐藏的list
		paramMap.put("dataHideList", hideList);
		
		//添加js 需要的参数信息
		
		paramMap.put("transTableName", StringUtilTools.convertName( productDTO.getTableName(), true));
		paramMap.put("jspPrefix", productDTO.getJspPrefix());
		return paramMap;
	}
	/**
	 * 组装 表字段 属性列表
	 * @param columnsList
	 * @return
	 */
	@SuppressWarnings("all")
	private List makeupFormatColumnLis(List columnsList) {
		List resultList = new ArrayList();
		for(int i = 0;i < columnsList.size();i ++){
			Map temp = (Map) columnsList.get(i);
			String column = (String) temp.get("COLUMN_NAME");
			//如果当前字段是主键ID id的位置在第一位
			if("ID".equals(column)){
				temp.put("TEMP_ROWNUM", "IS_ID");
			}else{
				temp.put("TEMP_ROWNUM", "IS_NOT_ID");
			}
			//是否是最后一个字段
			temp.put("IS_LAST", "N");
			//dto.id
			String dtoColumnName = "dto."+StringUtilTools.convertName(column, true);
			//如果是最后一个 字段则不需要有","
			if(i +1 == columnsList.size()){
				dtoColumnName = dtoColumnName.replace(",", "");
				temp.put("IS_LAST", "Y");
			}
			
			temp.put("DTO_COLUMN_NAME", dtoColumnName);
			
			resultList.add(temp);
		}
	
		return resultList;
	}
	/**
	 * 通过jsp 模板生成 页面
	 * @param jspParamMap
	 * @throws Exception
	 */
	@SuppressWarnings("all")
	private void proJSPByTemplate(Map<String, Object> jspParamMap) throws Exception{
		ProductCodeDTO productDTO = (ProductCodeDTO) jspParamMap.get("productCodeDTO");
		//获取表属性 Y:子表，N：为主表
		String tabProperty = productDTO.getTableIsSub();
		//如果是主表的话，是否关联子表 Y：是，N：否
		String isRelationSub = productDTO.getRelationSub();
		
		//String[] tempList= "template_query.jsp,template_add.jsp,template_update.jsp,template_dwr.xml".split(",");
		
		//append string 
		String st0 = "template_query.jsp";
		String st1 = "template_add.jsp";
		String st2 = "template_update.jsp";
		String st3 = "template_view.jsp";
		String st4 = "template_query_2.jsp";
		
		//如果主表 设置了关联子表，则按主子表关联 生成主表信息
		if("N".equals(tabProperty) && "Y".equals(isRelationSub)){
			st0 = "/oneToMore/tem_query_1.jsp";
			st1 = "/oneToMore/tem_add_1.jsp";
			st2 = "/oneToMore/tem_update_1.jsp";
		}
		//如果是子表生成代码
		if("Y".equals(tabProperty)){
			st0 = "/oneToMore/tem_query_2.jsp";
			st1 = "/oneToMore/tem_add_2.jsp";
		}
		String strs = st0 +","+st1+","+st2+","+st3+","+st4;
		
		//如果主表 设置了关联子表，则按主子表关联 生成查看预览页面
		if("N".equals(tabProperty) && "Y".equals(isRelationSub))
			strs = strs+","+"/oneToMore/tem_view_1.jsp";
		
		String[] tempList= strs.split(",");
		
		for(int i = 0;i < tempList.length;i ++){
			String tempName = tempList[i];
			
			
			String jspFileDir = productDTO.getJspTargetDir() +productDTO.getJspPrefix()+ productDTO.getModelName();
			jspFileDir = jspFileDir.toLowerCase();
			String jspFileNamePath = "";
			if(tempName.endsWith("_query.jsp") || tempName.contains("tem_query_") ){
				jspFileNamePath = jspFileDir +"/"+ productDTO.getQueryJspName() + ".jsp";
				
			}else if(tempName.endsWith("_add.jsp") || tempName.contains("tem_add_")){
				jspFileNamePath = jspFileDir +"/"+ productDTO.getAddJspName() + ".jsp";
				
			}else if(tempName.endsWith("_update.jsp") || tempName.contains("tem_update_")){
				
				jspFileNamePath = jspFileDir +"/"+ productDTO.getUpdateJspName() + ".jsp";
			}else if(tempName.endsWith("_view.jsp")){
				
				jspFileNamePath = jspFileDir+"/" + productDTO.getQueryJspName().replace("query", "view") + ".jsp";
				
			}else if(tempName.contains("tem_view_1")){
				//如果是主子表 查看阅览生成
				jspFileNamePath = jspFileDir +"/"+ productDTO.getQueryJspName().replace("query", "view") + ".jsp";
			}
			else if(tempName.endsWith("_query_2.jsp")){
                jspFileNamePath = jspFileDir +"/"+ productDTO.getQueryJspName()+ "_2" + ".jsp";
            }
			System.err.println("开始生成:"+jspFileNamePath);
			
			Template template = getConfiguration(productDTO.getTemplatePath()).getTemplate(tempName);
			//生成目录
			FileUtils.forceMkdir(new File(jspFileDir+"/"));
			//生成文件
			Writer outImpl = new OutputStreamWriter(new FileOutputStream(jspFileNamePath), "UTF-8");
			template.process(jspParamMap, outImpl);
			outImpl.flush();
			outImpl.close();
		}
	}
	/**
	 * 组装 js 所需 参数
	 * @param paraMap
	 * @return
	 */
	private Map<String, Object> makeupJSParams(Map<String, Object> paraMap) {
		//暂时可以使用jsp组装的参数信息
		
		//添加js 需要的参数信息
		ProductCodeDTO productDTO = (ProductCodeDTO) paraMap.get("productCodeDTO");
		//生成js的路径
		String jsTargetPath = productDTO.getJsTargetDir() +productDTO.getJspPrefix()+ productDTO.getModelName()+"/";
		//productDTO.getJspTargetDir()+productDTO.getModelName()+"/js";
		
		productDTO.setJsTargetPath(jsTargetPath);
		//组装 生成 query jsp的参数
		paraMap.put("jspDTO", productDTO);
		paraMap.put("transTableName", StringUtilTools.convertName( productDTO.getTableName(), true));
		paraMap.put("formated_tab_name", StringUtilTools.convertName( productDTO.getTableName(), false));
		return paraMap;

	}
	public ProductCodeDTO proJSCode(Map<String, Object> paraMap)
			throws Exception {
		Map<String,Object> jsParamMap  = makeupJSParams(paraMap);
		//开始自动生成 jsp
		proJSByTemplate(jsParamMap);
		
		ProductCodeDTO proJSPDTO  = (ProductCodeDTO) jsParamMap.get("productCodeDTO");
		
		return proJSPDTO;
	}
	/**
	 * 通过js 模板生成js 文件
	 * @param jspParamMap
	 * @throws IOException 
	 */
	private void proJSByTemplate(Map<String, Object> jspParamMap) throws Exception {
		ProductCodeDTO productDTO = (ProductCodeDTO) jspParamMap.get("productCodeDTO");
		//获取表属性 Y:子表，N：为主表
		String tabProperty = productDTO.getTableIsSub();
		//如果是主表的话，是否关联子表 Y：是，N：否
		String isRelationSub = productDTO.getRelationSub();
		//生成 query js
		String queryJSTemplate = "tem_query.js";
		
		//如果主表 设置了关联子表，则按主子表关联 生成主表信息
		if("N".equals(tabProperty) && "Y".equals(isRelationSub)){
			queryJSTemplate = "/oneToMore/tem_query_1.js";
		}
		//如果是子表生成代码
		if("Y".equals(tabProperty)){
			queryJSTemplate = "/oneToMore/tem_query_2.js";
		}
		String jsFileDir = productDTO.getJsTargetPath().toLowerCase();
		
		String jspFileNamePath = jsFileDir +"/"+ productDTO.getQueryJspName() + ".js";
		Template template = getConfiguration(productDTO.getTemplatePath()).getTemplate(queryJSTemplate);
		//生成目录
		FileUtils.forceMkdir(new File(jsFileDir+"/"));
		//生成文件
		Writer outImpl = new OutputStreamWriter(new FileOutputStream(jspFileNamePath), "UTF-8");
		template.process(jspParamMap, outImpl);
		outImpl.flush();
		outImpl.close();
		/*
		//生成 addJSP.jsp
		jspFileNamePath = jsFileDir +"/"+ productDTO.getAddJspName() + ".js";
		template = getConfiguration(productDTO.getTemplatePath()).getTemplate("tem_add.js");

		//如果主表 设置了关联子表，则按主子表关联 生成主表信息
		if("N".equals(tabProperty) && "Y".equals(isRelationSub)){
			queryJSTemplate = "/oneToMore/tem_query_1.js";
			template = getConfiguration(productDTO.getTemplatePath()).getTemplate("/oneToMore/tem_add_1.js");
		}
		
		//如果是子表生成代码
		if("Y".equals(tabProperty)){
			//queryJSTemplate = "/oneToMore/tem_query_2.js";
		}
		//生成目录
		FileUtils.forceMkdir(new File(jsFileDir+"/"));
		//生成文件
		outImpl = new OutputStreamWriter(new FileOutputStream(jspFileNamePath), "UTF-8");
		template.process(jspParamMap, outImpl);
		outImpl.flush();
		outImpl.close();
		
		//生成 updateJS 
		jspFileNamePath = jsFileDir +"/"+ productDTO.getUpdateJspName() + ".js";
		template = getConfiguration(productDTO.getTemplatePath()).getTemplate("tem_update.js");
		//如果主表 设置了关联子表，则按主子表关联 生成主表信息
		if("N".equals(tabProperty) && "Y".equals(isRelationSub)){
			queryJSTemplate = "/oneToMore/tem_query_1.js";
			template = getConfiguration(productDTO.getTemplatePath()).getTemplate("/oneToMore/tem_update_1.js");
		}
		//生成目录
		FileUtils.forceMkdir(new File(jsFileDir+"/"));
		//生成文件
		outImpl = new OutputStreamWriter(new FileOutputStream(jspFileNamePath), "UTF-8");
		template.process(jspParamMap, outImpl);
		outImpl.flush();
		outImpl.close();
		*/
		
	}

	public ProductCodeDTO proRestCode(Map<String, Object> paraMap)throws Exception {
		Map<String, Object> actionMap = makeupActionParams(paraMap);

        Map<String, Object> resultMap = this.producRestByTemplate(actionMap);

        ProductCodeDTO productDTO = (ProductCodeDTO) resultMap.get("codeDTO");

        return productDTO;
	}
	
	private Map<String, Object> producRestByTemplate(Map<String, Object> paramMap) throws Exception {

        ProductCodeDTO productDTO = (ProductCodeDTO) paramMap.get("productCodeDTO");

        Map<String, Object> dataOfTemplate = productTemplate(productDTO);
        
        dataOfTemplate.put("jspPrefix", productDTO.getJspPrefix());
        String templateAction = "template_rest.java";


        // 开始生成rest
        String fileDir =  productDTO.getTargetDir() + WEB_CONTROLLER + productDTO.getRestPackageName().replaceAll("\\.", "/");
        
        String fileNamePath = productDTO.getTargetDir() + WEB_CONTROLLER+ productDTO.getRestPackageName().replaceAll("\\.", "/")
                + "/" + productDTO.getRestClassName() + JAVAFILE_POSTFIX;
        
        Template template = getConfiguration(productDTO.getTemplatePath()).getTemplate(templateAction);
        FileUtils.forceMkdir(new File(fileDir + "/"));

        Writer out = new OutputStreamWriter(new FileOutputStream(fileNamePath), "UTF-8");
        template.process(dataOfTemplate, out);
        out.flush();
        out.close();

        return dataOfTemplate;
    }
}