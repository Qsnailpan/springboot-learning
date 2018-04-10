package com.snail.util.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil {
	/**
	 * 生成Excel,并以输出流返回到前端
	 *
	 * @param fileName 文件名
	 * @param displayNames  Excel字段,与keys按顺序一一对应
	 * @param keys 对应数据源对象的属性
	 * @param datas 数据源
	 * @param response
	 * @param callBack回调，对excel进一步处理,可为null
	 * @return
	 */
	public static HSSFWorkbook createWorkBook(String fileName, String[] headString, String[] keys,
			List<Map<String, Object>> datas, HttpServletResponse response, IExcelCallBack callBack,
			IDataCallBack dataCallback) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet(fileName);
		Font borderFont = workbook.createFont();
		ExportExcel exportExcel = new ExportExcel(workbook, sheet);

		CellRangeAddress cra = new CellRangeAddress(0, 0, 0, headString.length - 1);// 合并单元格
		sheet.addMergedRegion(cra);
		// 创建表头
		exportExcel.createHeadName(fileName,0);
		// 创建标题行
		exportExcel.setHeadLine(headString, 1);
		
		OutputStream os = null;
		// 创建单元格样式
		@SuppressWarnings("deprecation")
		CellStyle style = exportExcel.createTrueBorderCellStyle(workbook, HSSFColor.WHITE.index, HSSFColor.WHITE.index,
				HSSFCellStyle.ALIGN_CENTER, borderFont);
		// 自动换行
		style.setWrapText(true);
		try {
			// 填充单元格数据
			setData(keys, datas, dataCallback, sheet, borderFont, style);
			
			// 表末另起一行添加数据附加信息
			if (callBack != null) {
				callBack.run(exportExcel);
			}
			response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Type", "application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition",
					"attachment; filename=" + (new String(fileName.getBytes(), "iso-8859-1")) + ".xls");
			os = response.getOutputStream();
			workbook.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			e.getMessage();
		}finally {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(workbook);
		}
		return workbook;
	}

	/**
	 * 生成Excel,并以输出流返回到前端 支持表头多行
	 *
	 * @param fileName
	 *            文件名
	 * @param titles
	 *            表头 可有多行
	 * @param keys
	 *            对应数据源对象的属性
	 * @param datas
	 *            数据源
	 * @param response
	 *            返回处理
	 * @param callBack，对excel进一步处理,可为null
	 * @param dataCallback，数据进行处理,可为null
	 * @return
	 */
	public static HSSFWorkbook createWorkBook(String fileName, List<String[]> titles, String[] keys,
			List<Map<String, Object>> datas, HttpServletResponse response, IExcelCallBack callBack,
			IDataCallBack dataCallback) {

		return null;
	}

	@SuppressWarnings("deprecation")
	private static void setData(String[] keys, List<Map<String, Object>> datas, IDataCallBack dataCallback,
			HSSFSheet sheet, Font borderFont, CellStyle style) {
		short rowNumber = (short) sheet.getLastRowNum();
		for (Map<String, Object> map : datas) {
			rowNumber++;;
			HSSFRow row = sheet.createRow((short) rowNumber);
			row.setHeight((short) 800);
			borderFont.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
			for (int i = 0; i < keys.length; i++) {
				HSSFCell cell = row.createCell(i);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String key = keys[i].toString();
				String val = map.get(key) == null ? "" : map.get(key).toString();
				val = dataCallback == null ? val : dataCallback.run(key, val);
				cell.setCellValue(new HSSFRichTextString(val));
				cell.setCellStyle(style);
			}
		}
	}


	/**
	 *  解析上传表格
	 * @param is
	 * @param startRow  从第几行开始解析
	 * @return  解析之后的单元数据<行号，[data,data,data...]>
	 * @throws IOException
	 */
	@SuppressWarnings({ "deprecation", "resource" })
	public static Map<Integer, List<String>> parseExcel(InputStream is , int startRow) throws IOException {
		Map<Integer, List<String>> content = new HashMap<Integer, List<String>>();
		Workbook wb = null;
		// 默认从第一行开始解析
		try {
			wb = new HSSFWorkbook(is);
		} catch (OfficeXmlFileException e) {
//			e.printStackTrace();
			wb = new XSSFWorkbook(is);
		}

		Sheet sheet = wb.getSheetAt(0);
		// 得到总行数
		int rowNum = sheet.getLastRowNum();
		// 开始
		Row row = sheet.getRow(startRow);
		int colNum = row.getPhysicalNumberOfCells();
		// 数据从第四行开始,前面都是列头
		for (int i = startRow; i < rowNum; i++) {
			row = sheet.getRow(i);
			if (row == null) {
				break;
			}
			List<String> list = new ArrayList<String>();
			for (int j = 0; j < colNum; j++) {
				Cell cell = row.getCell(j);
				if (cell != null) {
					switch (cell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字
						if (HSSFDateUtil.isCellDateFormatted(cell)) {
							// 如果是date类型则 ，获取该cell的date值
							list.add(DateFormatUtils.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()),
									"yyyy/MM/dd"));
						} else { // 纯数字
							double d = cell.getNumericCellValue();
							if (d - (int) d < Double.MIN_VALUE) {
								// 是否为int型
								list.add(Integer.toString((int) d));
							} else {
								// 是否为double型
								list.add(Double.toString(cell.getNumericCellValue()));
							}
						}
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符串
						list.add(cell.getStringCellValue() + "");
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN: // Boolean
						list.add(cell.getBooleanCellValue() + "");
						break;
					case HSSFCell.CELL_TYPE_FORMULA: // 公式
						list.add(cell.getCellFormula() + "");
						break;
					case HSSFCell.CELL_TYPE_BLANK: // 空值
						list.add("");
						break;
					case HSSFCell.CELL_TYPE_ERROR: // 故障
						list.add("");
						break;
					default:
						list.add("");
						break;
					}
				} else {
					list.add("");
				}
			}
			content.put(i, list);
		}
		return content;
	}

	
	/**
	 * 上传excel文件到临时目录后并开始解析
	 * @param fileName
	 * @param file
	 * @param userName
	 * @return
	 */
	public static String batchImport(String fileName,MultipartFile mfile,String userName){
		
		   File uploadDir = new  File("E:\\fileupload");
	       //创建一个目录 （它的路径名由当前 File 对象指定，包括任一必须的父路径。）
	       if (!uploadDir.exists()) uploadDir.mkdirs();
	       //新建一个文件
	       File tempFile = new File("E:\\fileupload\\" + new Date().getTime() + ".xlsx"); 
	       //初始化输入流
	       InputStream is = null;  
	       try{
	    	   //将上传的文件写入新建的文件中
	    	   mfile.transferTo(tempFile);
	    	   
	    	   //根据新建的文件实例化输入流
	           is = new FileInputStream(tempFile);
	    	   
	    	   //根据版本选择创建Workbook的方式
	           Workbook wb = null;
	           //根据文件名判断文件是2003版本还是2007版本
//	           wb = new XSSFWorkbook(is);
	           wb = new HSSFWorkbook(is);   
	           //根据excel里面的内容读取知识库信息
	           return readExcelValue(wb,userName,tempFile,1);
	      }catch(Exception e){
	          e.printStackTrace();
	      } finally{
	          if(is !=null)
	          {
	              try{
	                  is.close();
	              }catch(IOException e){
	                  is = null;    
	                  e.printStackTrace();  
	              }
	          }
	      }
        return "导入出错！请检查数据格式！";
    }
	
	
	/**
	   * 解析Excel里面的数据
	   * @param wb
	   * @return
	   */
	  private static String readExcelValue(Workbook wb,String userName,File tempFile,int startRow ){
		  
		   //错误信息接收器
		   String errorMsg = "";
	       //得到第一个shell  
	       Sheet sheet=wb.getSheetAt(0);
	       //得到Excel的行数
	       int totalRows=sheet.getPhysicalNumberOfRows();
	       //总列数
		   int totalCells = 0; 
	       //得到Excel的列数(前提是有行数)，从第二行算起
	       if(totalRows>=2 && sheet.getRow(1) != null){
	            totalCells=sheet.getRow(1).getPhysicalNumberOfCells();
	       }
	       
	       
//	       List<User> UserList=new ArrayList<User>();
//	       User user;    
//	       
	       
	       String br = "<br/>";
	       
	       //循环Excel行数,从第二行开始。标题不入库
	       for(int r=startRow;r<totalRows;r++){
	    	   String rowMessage = "";
	           Row row = sheet.getRow(r);
	           if (row == null){
	        	   errorMsg += br+"第"+(r+1)+"行数据有问题，请仔细检查！";
	        	   continue;
	           }
//	           user = new User();
	           
	           String question = "";
	           String answer = "";
	           
	           //循环Excel的列
	           for(int c = 0; c <totalCells; c++){
	               Cell cell = row.getCell(c);
	               if (null != cell){
	                   if(c==0){
	                	   question = cell.getStringCellValue();
	                	   if(StringUtils.isEmpty(question)){
	                		   rowMessage += "不能为空；";
	                	   }else if(question.length()>60){
	                		   rowMessage += "字数不能超过60；";
	                	   }
//	                	   user.setQuestion(question);
	                   }else if(c==1){
	                	   answer = cell.getStringCellValue();
	                	   if(StringUtils.isEmpty(answer)){
	                		   rowMessage += "不能为空；";
	                	   }else if(answer.length()>1000){
	                		   rowMessage += "字数不能超过1000；";
	                	   }
//	                	   user.setAnswer(answer);
	                   }
	               }else{
	            	   rowMessage += "第"+(c+1)+"列数据有问题，请仔细检查；";
	               }
	               
	               System.out.println(r +"行"+c+"列数据：" +cell.getStringCellValue() );
	           }
	           //拼接每行的错误提示
	           if(!StringUtils.isEmpty(rowMessage)){
	        	   errorMsg += br+"第"+(r+1)+"行，"+rowMessage;
	           }else{
//	        	   UserList.add(user);
	           }
	           
	       }
	       
	       //删除上传的临时文件
	       if(tempFile.exists()){
	    	   tempFile.delete();
	       }
	       
	       //全部验证通过才导入到数据库
	       if(StringUtils.isEmpty(errorMsg)){
//	    	   save() ..
	       }
	       
	       return errorMsg;
	  }
}
