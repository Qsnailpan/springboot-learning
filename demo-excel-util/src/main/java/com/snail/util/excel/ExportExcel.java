package com.snail.util.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExportExcel {

	private static final Logger log = LoggerFactory.getLogger(ExportExcel.class);

	private HSSFWorkbook wb = null;
	private HSSFSheet sheet = null;
	private Font headFont = null;

	private static final String SONG_TI_FONT = "宋体";

	public ExportExcel(HSSFWorkbook wb, HSSFSheet sheet) {
		super();
		this.wb = wb;
		this.sheet = sheet;
		headFont = wb.createFont();
	}

	
	/**
	 * 创建表头
	 * 
	 * @param row
	 * @param name
	 * @param wb
	 */
	@SuppressWarnings({"deprecation", "static-access" })
	public  void createHeadName(String headName , int row) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(cellStyle.ALIGN_CENTER); // 居中

		Font font = wb.createFont();
		font.setFontName(SONG_TI_FONT);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		font.setFontHeightInPoints((short) 20); // 高度 - 字号大小

		cellStyle.setFont(font);

		Cell cell = sheet.createRow(row).createCell(0);
		cell.setCellValue(headName);
		cell.setCellStyle(cellStyle);
	}


	/**
	 * 设置标题行
	 * 
	 * @param headString
	 *            标题名称
	 * @param lineNumber
	 *            在哪一行设置标题
	 */
	@SuppressWarnings("deprecation")
	public void setHeadLine(String[] headString, int lineNumber) {
		headFont.setFontName(SONG_TI_FONT);
		headFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		CellStyle style3 = createTrueBorderCellStyle(wb,
				HSSFColor.GREY_25_PERCENT.index,
				HSSFColor.GREY_25_PERCENT.index, HSSFCellStyle.ALIGN_CENTER,
				headFont);
		HSSFRow row = sheet.createRow(lineNumber);
		row.setHeight((short) 800);
		for (int i = 0; i < headString.length; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(style3);
			HSSFRichTextString text2 = new HSSFRichTextString(headString[i]);
			cell.setCellValue(text2);
		}
	}
	/**
	 * 输出EXCEL文件
	 * 
	 * @param fileName
	 *            文件名
	 */
	public void outputExcel(String fileName) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(fileName));
			wb.write(fos);
			fos.close();
		} catch (FileNotFoundException e) {
			log.error(new StringBuffer("[").append(e.getMessage()).append("]")
					.append(e.getCause()).toString());
			e.printStackTrace();
		} catch (IOException e) {
			log.error(new StringBuffer("[").append(e.getMessage()).append("]")
					.append(e.getCause()).toString());
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				log.error(new StringBuffer("[").append(e.getMessage()).append(
						"]").append(e.getCause()).toString());
			}
		}
	}

	/**
	 * 功能：创建带边框的CellStyle样式（实线框）
	 * 
	 * @param wb
	 *            HSSFWorkbook
	 * @param backgroundColor
	 *            背景色
	 * @param foregroundColor
	 *            前置色
	 * @param borderFont
	 *            字体
	 * @return CellStyle
	 */

	@SuppressWarnings("deprecation")
	public CellStyle createTrueBorderCellStyle(HSSFWorkbook wb,
                                               short backgroundColor, short foregroundColor, short halign,
                                               Font borderFont) {
		CellStyle cs = wb.createCellStyle();
		cs.setAlignment(halign);
		cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
//		cs.setFillBackgroundColor(backgroundColor);
//		cs.setFillForegroundColor(foregroundColor);
		cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cs.setFont(borderFont);
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cs.setBorderTop(HSSFCellStyle.BORDER_THIN);
		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		return cs;
	}

	/**
	 * 设置列宽（每列统一宽度）
	 * 
	 * @param sheet
	 * @param width
	 * @return
	 */
	public HSSFSheet setColumnWidthStyle(HSSFSheet sheet, int width) {
		sheet.setDefaultColumnWidth(width);
		return sheet;
	}

	/**
	 * 设置某一列的宽度
	 * 
	 * @param sheet
	 * @param width
	 * @return
	 */
	public HSSFSheet setColumnsWidthStyle(HSSFSheet sheet, int column, int width) {
		sheet.setColumnWidth(column, width);
		return sheet;
	}

	/**
	 * 设置某一列的宽度
	 * 
	 * @param sheet
	 * @param width
	 * @return HSSFSheet
	 */
	public HSSFSheet setColumnsWidthsStyle(HSSFWorkbook wb, HSSFSheet sheet,
                                           List<String> columns, List<String> widths) {
		for (int i = 0; i < columns.size(); i++) {
			wb.createCellStyle();
			sheet.setColumnWidth(Integer.parseInt(columns.get(i)), Integer
					.parseInt(widths.get(i)));
		}
		return sheet;
	}

	public HSSFSheet getSheet() {
		return sheet;
	}

	public void setSheet(HSSFSheet sheet) {
		this.sheet = sheet;
	}

	public HSSFWorkbook getWb() {
		return wb;
	}

	public void setWb(HSSFWorkbook wb) {
		this.wb = wb;
	}

}
