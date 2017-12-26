package com.fintech.modules.platform.sysorg.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fintech.modules.platform.sysorg.dto.SysOrgDTO;

@SuppressWarnings("all")
public class ExportExcel<T> {

	
	public String exportExcel(String sheetTitle,Collection<T> dataset, OutputStream out) {
		return exportExcel(sheetTitle, null, dataset, out, "yyyy-MM-dd");
	}

	public String exportExcel(String sheetTitle,String[] headers, Collection<T> dataset,
			OutputStream out) {
		return exportExcel(sheetTitle, headers, dataset, out, "yyyy-MM-dd");
	}

	@SuppressWarnings("unchecked")
	public String exportExcel(String title, String[] headers,
			Collection<T> dataset, OutputStream out, String pattern) {
		String state = "fail";
		// 声明一个工作薄
		XSSFWorkbook workbook = new XSSFWorkbook();
		// 生成一个表格
		XSSFSheet sheet = workbook.createSheet(title);
		// 设置表格默认列宽度为15个字节
		sheet.setDefaultColumnWidth((short) 15);
		// 生成一个样式
		XSSFCellStyle style = workbook.createCellStyle();
		// 设置这些样式
		style.setFillForegroundColor(HSSFColor.GREEN.index);
		style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 生成一个字体
		XSSFFont font = workbook.createFont();
		font.setFontName(HSSFFont.FONT_ARIAL);
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		// 生成并设置另一个样式
		XSSFCellStyle style2 = workbook.createCellStyle();
		style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		// 生成另一个字体
		XSSFFont font2 = workbook.createFont();
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
		// 把字体应用到当前的样式
		style2.setFont(font2);

		// 声明一个画图的顶级管理器
		XSSFDrawing patriarch = sheet.createDrawingPatriarch();
		// 定义注释的大小和位置,详见文档
		/*XSSFComment comment = patriarch.createCellComment(new XSSFClientAnchor(0,
				0, 0, 0, (short) 4, 2, (short) 6, 5));
		// 设置注释内容
		comment.setString(new XSSFRichTextString("可以在POI中添加注释！"));
		// 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
		comment.setAuthor("leno");*/

		// 产生表格标题行
		XSSFRow row;
		int index = 0;
		if(headers!=null){
			row = sheet.createRow(0);
			index++;
			for (short i = 0; i < headers.length; i++) {
				XSSFCell cell = row.createCell(i);
				cell.setCellStyle(style);
				XSSFRichTextString text = new XSSFRichTextString(headers[i]);
				cell.setCellValue(text);
			}
		}
		

		Iterator<T> itt = dataset.iterator();
		boolean columnTitle =false;
		while (itt.hasNext()) {
			row = sheet.createRow(index);
			index++;
			T t = (T) itt.next();
			Field[] fields = t.getClass().getDeclaredFields();
			int titleIndex = 0;
			for (short i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if("serialVersionUID".equals(fieldName)){
					continue;
				}else{
					XSSFCell cell = row.createCell(titleIndex);
					titleIndex++;
					cell.setCellStyle(style);
					cell.setCellValue(fieldName);
				}
			}	
			break;
		}
		itt = null;
		Iterator<T> it = dataset.iterator();
		// 遍历集合数据，产生数据行
		while (it.hasNext()) {
			row = sheet.createRow(index);
			index++;
			T t = (T) it.next();
			// 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
			Field[] fields = t.getClass().getDeclaredFields();
			int cellIndex = 0;
			for (short i = 0; i < fields.length; i++) {
				Field field = fields[i];
				String fieldName = field.getName();
				if("serialVersionUID".equals(fieldName)){
					continue;
				}
				XSSFCell cell = row.createCell(cellIndex);
				cellIndex++;
				cell.setCellStyle(style2);
				String getMethodName = "get"
						+ fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1);
				try {
					Class tCls = t.getClass();
					Method getMethod = tCls.getMethod(getMethodName,
							new Class[] {});
					Object value = getMethod.invoke(t, new Object[] {});
					if(value == null){
						value = "";
					}
					// 判断值的类型后进行强制类型转换
					String textValue = null;
					if (value instanceof Date) {
						Date date = (Date) value;
						SimpleDateFormat sdf = new SimpleDateFormat(pattern);
						textValue = sdf.format(date);
					} else if (value instanceof byte[]) {
						// 有图片时，设置行高为60px;
						row.setHeightInPoints(60);
						// 设置图片所在列宽度为80px,注意这里单位的一个换算
						sheet.setColumnWidth(i, (short) (35.7 * 80));
						// sheet.autoSizeColumn(i);
						byte[] bsValue = (byte[]) value;
						HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
								1023, 255, (short) 6, index, (short) 6, index);
						anchor.setAnchorType(2);
						patriarch.createPicture(anchor, workbook.addPicture(
								bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
					} else {
						// 其它数据类型都当作字符串简单处理
						textValue = value.toString();
					}
					// 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
					if (textValue != null) {
						Pattern p = Pattern.compile("^//d+(//.//d+)?$");
						Matcher matcher = p.matcher(textValue);
						if (matcher.matches()) {
							// 是数字当作double处理
							cell.setCellValue(Double.parseDouble(textValue));
						} else {
							XSSFRichTextString richString = new XSSFRichTextString(
									textValue);
							XSSFFont font3 = workbook.createFont();
							font3.setColor(HSSFColor.BLUE.index);
							richString.applyFont(font3);
							cell.setCellValue(richString);
						}
					}
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				} 
			}
		}
		try {
			workbook.write(out);
			state = "succes";
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return state;
		}
	}

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		ExportExcel<SysOrgDTO> ex = new ExportExcel<SysOrgDTO>();
		List<SysOrgDTO> dataset = new ArrayList<SysOrgDTO>();
		for(int i =0;i<8;i++){
			SysOrgDTO dto = new SysOrgDTO();
			dto.setId((long)(i+1));
			dto.setOrgCode(dto.getId()+"");
			dto.setOrgName("ceshi"+dto.getId());
			dto.setOrgType("org");
			dto.setIsLeef("0");
			dto.setIsVirtual("0");
			dto.setValidateState("1");
			dto.setParentId("6");
			dto.setParentIds("/6/");
			dto.setVersion(123456789L);
			dataset.add(dto);
		}
		OutputStream out = new FileOutputStream("E://a.xlsx");
		//ex.exportExcel("机构导出",dataset,out);
		String header[] = new String[]{"ID","机构编码","机构名称","机构类型","机构类型中文","上级机构ID","所有上级机构ID",
				"排序","状态","状态中文","是否虚拟机构","是否虚拟机构中文","版本","系统标识","系统表示中文","是否是叶子结点","区域码","区域地址","扩展","扩展"};
		ex.exportExcel("机构导出",header,dataset,out);
	}

}
