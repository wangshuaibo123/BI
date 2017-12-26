package com.fintech.platform.tools.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fintech.platform.tools.excel.SelectValue;

/**
 * <p>
 * Excel导出工具类
 * </p>
 * 
 * @author
 * @2014年11月25日 下午3:34:22
 */
public class ExcelExport {

	private static final Logger logger = LoggerFactory.getLogger(ExcelExport.class);

	public ExcelExport() {
	}

	/**
	 * 定义sheet页面
	 * @param sheetName
	 * @param headers
	 * @return
	 */
	protected  Sheet createSheet(Workbook workbook,String sheetName,String[] headers){
		Sheet sheet = workbook.createSheet(sheetName);
		sheet.createFreezePane(0, 1);
		for (int i = 0; i < headers.length; i++) {
			sheet.setColumnWidth(i, 5000);
		}
		return sheet;
	}
	
	   /**
     * 定义sheet页面
     * @param sheetName
     * @param headers
     * @return
     */
    protected  Sheet createSheet(SXSSFWorkbook workbook,String sheetName,String[] headers){
        Sheet sheet = workbook.createSheet(sheetName);
        sheet.createFreezePane(0, 1);
        for (int i = 0; i < headers.length; i++) {
            sheet.setColumnWidth(i, 5000);
        }
        return sheet;
    }
	

	/**
	 * 定义表头样式
	 * @param workbook
	 * @return
	 */
	public CellStyle createHeadStyle(Workbook workbook){
		Font headfont = workbook.createFont();
		headfont.setFontName("宋体");
		headfont.setFontHeightInPoints((short) 12);// 字体大小
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗
		CellStyle headstyle = workbook.createCellStyle();
		headstyle.setFont(headfont);
		headstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		return headstyle;
	}
	
	/**
	 * 定义单元格样式
	 * @param workbook
	 * @return
	 */
	public CellStyle createCellStyle(Workbook workbook){
		Font font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);
		CellStyle style = workbook.createCellStyle();
		style.setFont(font);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		return style;
	}
	
	private CellStyle createDateCellStyle(Workbook workbook){
		Font font = workbook.createFont();
		font.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);
		CellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setFont(font);
		dateStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		dateStyle.setDataFormat(workbook.createDataFormat().getFormat("yyyy-MM-dd"));
		return dateStyle;
	}
	
	private CellStyle createMoneyCellStyle(Workbook workbook){
	    CellStyle moneyStyle = workbook.createCellStyle();
	    moneyStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
	    moneyStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        return moneyStyle;
	}
	
	
	
	/**
	 * 导出
	 * @param sheetName sheet页名称
	 * @param headers 表头
	 * @param dataset 数据集合
	 * @param propertiesName 列名集合
	 * @param datePropertiesName 日期列集合 对应页面表格中type=date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public  HSSFWorkbook export(String sheetName, String[] headers,
			List dataset, String[] propertiesName,List attributesOfDateType,Map comboxColumn) throws Exception {
		
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = (HSSFSheet)createSheet(workbook, sheetName, headers);
		HSSFRow row = sheet.createRow(0);
		HSSFCellStyle hssfCellStyle =  (HSSFCellStyle)createHeadStyle(workbook);
		for (int i = 0; i < headers.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new HSSFRichTextString(headers[i]));
			cell.setCellStyle(hssfCellStyle);
		}
		int index = 1;
		Iterator it = dataset.iterator();
		HSSFCellStyle cellStyle = (HSSFCellStyle)createCellStyle(workbook);
		HSSFCellStyle cellDateStyle = (HSSFCellStyle)createDateCellStyle(workbook);
		while (it.hasNext()) {
			row = sheet.createRow(index);
			Map t = (HashMap) it.next();
			for (int j = 0; j < propertiesName.length; j++) {
				String fieldName = propertiesName[j];
				HSSFCell cell = row.createCell(j);
				cell.setCellStyle(cellStyle);//普通格式
				for (int m = 0; m < attributesOfDateType.size(); m++) {
					if(fieldName!=null && attributesOfDateType.get(m)!=null && fieldName.equalsIgnoreCase(attributesOfDateType.get(m).toString())){
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(cellDateStyle);
					}
				}
				if ("rowNum".equals(fieldName)) {
					cell.setCellValue(index);
				} else {
					
					if (comboxColumn != null &&comboxColumn.containsKey(fieldName)) {
						SelectValue[] selectValue = (SelectValue[])comboxColumn.get(fieldName);
						String [] combox = new String[selectValue.length];
						for (int i = 0; i < selectValue.length; i++) {
							if (selectValue[i].getValue() == null || selectValue[i].getValue().length()==0) continue;
							combox[i]=selectValue[i].getText();
						}
						StringBuilder builder = new StringBuilder();
						for (int i = 0; i < combox.length; i++) {
							if(combox[i]==null){
								continue;
							}
							builder.append(combox[i]);
							if (i !=combox.length-1) {
								builder.append(";");
							}
						}
						combox = builder.toString().split(";");
						DVConstraint constraint = DVConstraint.createExplicitListConstraint(combox);
						CellRangeAddressList regions = new CellRangeAddressList(cell.getRowIndex(), sheet.getLastRowNum(), cell.getColumnIndex(), cell.getColumnIndex());
						HSSFDataValidation data_validation = new  HSSFDataValidation(regions,constraint);
						sheet.addValidationData(data_validation);
					}
					
					Object objValue = t.get(fieldName);
					if (objValue != null) {
						 if (HSSFDateUtil.isCellDateFormatted(cell)) {  
						        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						        try {
						        	 Date dat=new Date(Long.parseLong(objValue.toString()));  
						             GregorianCalendar gc = new GregorianCalendar();   
						             gc.setTime(dat);  
						        	cell.setCellValue(format.format(gc.getTime()));
								} catch (Exception e) {
									cell.setCellType(HSSFCell.CELL_TYPE_STRING);
									cell.setCellValue(objValue.toString());
								}
						 } else {
							 cell.setCellType(HSSFCell.CELL_TYPE_STRING);
							 cell.setCellValue(objValue.toString());
						}
						
					} else {
						cell.setCellValue("");
					}
				}
			}
			index++;
		}
		return workbook;
	}
	
	
	/**
	 * 导出
	 * @param sheetName sheet页名称
	 * @param headers 表头
	 * @param dataset 数据集合
	 * @param propertiesName 列名集合
	 * @param datePropertiesName 日期列集合 对应页面表格中type=date
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public  XSSFWorkbook export2007(String sheetName, String[] headers,
			List dataset, String[] propertiesName,List attributesOfDateType,Map comboxColumn) throws Exception {
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = (XSSFSheet)createSheet(workbook, sheetName, headers);
		XSSFRow row = (XSSFRow)sheet.createRow(0);
		XSSFCellStyle XSSFCellStyle =  (XSSFCellStyle)createHeadStyle(workbook);
		for (int i = 0; i < headers.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			cell.setCellValue(new XSSFRichTextString(headers[i]));
			cell.setCellStyle(XSSFCellStyle);
		}
		int index = 1;
		Iterator it = dataset.iterator();
		XSSFCellStyle cellStyle = (XSSFCellStyle)createCellStyle(workbook);
		XSSFCellStyle cellDateStyle = (XSSFCellStyle)createDateCellStyle(workbook);
		while (it.hasNext()) {
			row = sheet.createRow(index);
			Map t = (HashMap) it.next();
			for (int j = 0; j < propertiesName.length; j++) {
				String fieldName = propertiesName[j];
				XSSFCell cell = row.createCell(j);
				cell.setCellStyle(cellStyle);//普通格式
				for (int m = 0; m < attributesOfDateType.size(); m++) {
					if(fieldName!=null && attributesOfDateType.get(m)!=null && fieldName.equalsIgnoreCase(attributesOfDateType.get(m).toString())){
						cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(cellDateStyle);
					}
				}
				if ("rowNum".equals(fieldName)) {
					cell.setCellValue(index);
				} else {
					
					if (comboxColumn != null &&comboxColumn.containsKey(fieldName)) {
						SelectValue[] selectValue = (SelectValue[])comboxColumn.get(fieldName);
						String [] combox = new String[selectValue.length];
						for (int i = 0; i < selectValue.length; i++) {
							if (selectValue[i].getValue() == null || selectValue[i].getValue().length()==0) continue;
							combox[i]=selectValue[i].getText();
						}
						StringBuilder builder = new StringBuilder();
						for (int i = 0; i < combox.length; i++) {
							if(combox[i]==null){
								continue;
							}
							builder.append(combox[i]);
							if (i !=combox.length-1) {
								builder.append(";");
							}
						}
						combox = builder.toString().split(";");
						DataValidationHelper helper = sheet.getDataValidationHelper();
						XSSFDataValidationConstraint constraint=new XSSFDataValidationConstraint(combox);
						CellRangeAddressList regions = new CellRangeAddressList(cell.getRowIndex(), sheet.getLastRowNum(), cell.getColumnIndex(), cell.getColumnIndex());
						 DataValidation dataValidation  = helper.createValidation(constraint, regions);
						 if(dataValidation instanceof XSSFDataValidation) {
						      dataValidation.setSuppressDropDownArrow(true);
						      dataValidation.setShowErrorBox(true);
						    }else {
						      dataValidation.setSuppressDropDownArrow(false);
						    }
						sheet.addValidationData(dataValidation);
					}
					
					Object objValue = t.get(fieldName);
					if (objValue != null) {
						 if (DateUtil.isCellDateFormatted(cell)) {  
						        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
						        try {
						        	 Date dat=new Date(Long.parseLong(objValue.toString()));  
						             GregorianCalendar gc = new GregorianCalendar();   
						             gc.setTime(dat);  
						        	cell.setCellValue(format.format(gc.getTime()));
								} catch (Exception e) {
									cell.setCellType(XSSFCell.CELL_TYPE_STRING);
									cell.setCellValue(objValue.toString());
								}
						 } else {
							 cell.setCellType(XSSFCell.CELL_TYPE_STRING);
							 cell.setCellValue(objValue.toString());
						}
						
					} else {
						cell.setCellValue("");
					}
				}
			}
			index++;
		}
		return workbook;
	}
	/**
	 * 
		* @title: 分页导出excel文件
		* ljw
		* @description:
		* @date 2015年4月23日 上午10:10:45
        * @param sheetName sheet页名称
        * @param headers 表头
        * @param dataset 数据集合
        * @param propertiesName 列名集合
		* @param attributesOfDateType  日期属性处理
		* @param comboxColumn 下拉框数据处理
		* @param workbook 存放的数据对象
		* @param pageNum  页码，页码为1需要插入头部信息
		* @return
		* @throws Exception
		* @throws
	 */
    @SuppressWarnings("rawtypes")
    public SXSSFWorkbook exportLargeExcel(String sheetName, String[] headers, String[] types,List dataset, String[] propertiesName,
            List attributesOfDateType, Map comboxColumn, SXSSFWorkbook workbook, int pageNum, String isTime) throws Exception {
        try {
			SXSSFSheet sheet = null;
			SXSSFRow row = null;
			//数据开始插入位置
			int index = 0; 
			
			//定义单元格样式
			XSSFCellStyle XSSFCellStyle = (XSSFCellStyle) createHeadStyle(workbook);
			XSSFCellStyle cellStyle = (XSSFCellStyle) createCellStyle(workbook);
			XSSFCellStyle cellDateStyle = (XSSFCellStyle) createDateCellStyle(workbook);
			XSSFCellStyle cellMoneyStyle = (XSSFCellStyle) createMoneyCellStyle(workbook);
			if(pageNum == 1){
			    //第一页需要加头部
			    sheet = (SXSSFSheet)createSheet(workbook, sheetName, headers);
			    /**
			     * 设置隐藏列
			     */
			    for(int t=0;t<types.length;t++){
			    	if("hidden".equals(types[t])){
			    		sheet.setColumnHidden(t, true);
			    	}
			    }
			    
			    row = (SXSSFRow)sheet.createRow(0);
			    for (int i = 0; i < headers.length; i++) {
			        Cell cell = row.createCell(i);
			        cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			        cell.setCellValue(new XSSFRichTextString(headers[i]));
			        cell.setCellStyle(XSSFCellStyle);
			        //设置下拉框(只在头部设置下拉框)
			        if (comboxColumn != null &&comboxColumn.containsKey(propertiesName[i])) {
			            SelectValue[] selectValue = (SelectValue[])comboxColumn.get(propertiesName[i]);
			            String [] combox = new String[selectValue.length];
			            StringBuilder builder = new StringBuilder();
			            builder.append(headers[i]+ ";");
			            for (int j = 0; j < selectValue.length; j++) {
			                if (selectValue[j].getValue() == null || selectValue[j].getValue().length()==0) continue;
			                builder.append(selectValue[j].getText()+ ";");
			            }
			            String bldStr = builder.toString();
			            combox = bldStr.substring(0, bldStr.length()-1).split(";");
			            DataValidationHelper helper = sheet.getDataValidationHelper();
			            XSSFDataValidationConstraint constraint=new XSSFDataValidationConstraint(combox);
			            CellRangeAddressList regions = new CellRangeAddressList(cell.getRowIndex(), sheet.getLastRowNum(), cell.getColumnIndex(), cell.getColumnIndex());
			            DataValidation dataValidation  = helper.createValidation(constraint, regions);
			            if(dataValidation instanceof XSSFDataValidation) {
			              dataValidation.setSuppressDropDownArrow(true);
			              dataValidation.setShowErrorBox(true);
			            }else {
			              dataValidation.setSuppressDropDownArrow(false);
			            }
			            sheet.addValidationData(dataValidation);
			        }
			    } 
			    index = 1;
			}else{
			    sheet = (SXSSFSheet)workbook.getSheet(sheetName);
			    /**
			     * 设置隐藏列
			     */
			    for(int t=0;t<types.length;t++){
			    	if("hidden".equals(types[t])){
			    		sheet.setColumnHidden(t, true);
			    	}
			    }
			    index = sheet.getLastRowNum() + 1;
			}
			
			Iterator it = dataset.iterator();
			while (it.hasNext()) {
			    row = (SXSSFRow)sheet.createRow(index);
			    Map t = (HashMap) it.next();
			    for (int j = 0; j < propertiesName.length; j++) {
			        String fieldName = propertiesName[j];
			        SXSSFCell cell = (SXSSFCell)row.createCell(j);
			        cell.setCellStyle(cellStyle);//普通格式
			        for (int m = 0; m < attributesOfDateType.size(); m++) {
			            if(fieldName!=null && attributesOfDateType.get(m)!=null && fieldName.equalsIgnoreCase(attributesOfDateType.get(m).toString())){
			                cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);                        
			                cell.setCellStyle(cellDateStyle);
			            }
			        }
			        if ("rowNum".equals(fieldName)) {
			            cell.setCellValue(index);
			        } else {
			            
			            Object objValue = t.get(fieldName);
			            if (objValue != null) {
			                 if (DateUtil.isCellDateFormatted(cell)) {  
			                        SimpleDateFormat format = null;
			                        if("true".equals(isTime)){
			                            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			                        }else{
			                            format = new SimpleDateFormat("yyyy-MM-dd");
			                        }
			                        try {
			                             Date dat=new Date(Long.parseLong(objValue.toString()));  
			                             GregorianCalendar gc = new GregorianCalendar();   
			                             gc.setTime(dat);  
			                            cell.setCellValue(format.format(gc.getTime()));
			                        } catch (Exception e) {
			                            cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			                            cell.setCellValue(objValue.toString());
			                        }
			                 } else {
			                     cell.setCellType(XSSFCell.CELL_TYPE_STRING);
			                     cell.setCellValue(objValue.toString());
			                }
			                
			            } 
			            //如果value为空，且为money类型，默认值为0
			            else if(objValue==null && "money".equalsIgnoreCase(types[j])){
			            	 cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
			            	 cell.setCellStyle(cellMoneyStyle);
		                     cell.setCellValue(0);
			            }
			            else {
			                cell.setCellValue("");
			            }
			        }
			    }
			    index++;
			}
		} catch (Exception e) {
			String msg = e.getMessage();
			logger.error("===exportLargeExcel==error="+msg,e);
			if(msg == null) msg = "null";
			throw new Exception(msg);
		}
       return workbook;
    }
}
