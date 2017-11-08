package com.snail.util;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExcelUtils {

	public static Workbook epsCreateWorkBook(HttpServletResponse response, HttpServletRequest request,
			List<Map<String, Object>> datas, List<String> titles, List<String> items, String titleName) {
		OutputStream os = null;
		Workbook wb = null;
		try {
			
			wb = new SXSSFWorkbook();//new一个工作薄
			Sheet sheet = wb.createSheet();
			CellRangeAddress cra = new CellRangeAddress(0, 0, 0, titles.size() - 1);//合并单元格
			sheet.addMergedRegion(cra);
			
			sheet.setColumnWidth(3, 5000);// 设置第（3,4）四列和第五列的宽度
			sheet.setColumnWidth(4, 5000);
		
			// 创建表头
			Row row = sheet.createRow(0);
			createName(row, titleName, wb);
			row = sheet.createRow(1);
			
			createTitle(row, titles);// 创建标题行
			// 填充数据
			if (datas != null && datas.size() > 0) {
				for (int i = 0; i < datas.size(); i++) {
					row = sheet.createRow(i + 2);
					setDataRow(row, null, datas.get(i), items);
				}
			}
			
			String agent = request.getHeader("USER-AGENT");
			String fileName_;
			if (StringUtils.isNotBlank(agent) && agent.indexOf("Firefox") > -1) {
				fileName_ = new String(titleName.getBytes(), "ISO8859-1");
			} else {
				fileName_ = URLEncoder.encode(titleName, "UTF-8");
			}

			response.setHeader("Content-Disposition", "attachment; filename=" + fileName_ + ".xls");
			response.setContentType("application/msexcel");
			os = response.getOutputStream();
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(wb);
		}
		return wb;
	}
	/**
	 * 创建表头
	 * 
	 * @param row
	 * @param name
	 * @param wb
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	private static void createName(Row row, String name, Workbook wb) {
		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(cellStyle.ALIGN_CENTER); // 居中

		Font font = wb.createFont();
		font.setFontName("仿宋_GB2312");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
		font.setFontHeightInPoints((short) 20); // 高度 - 字号大小

		cellStyle.setFont(font);

		Cell cell = row.createCell(0);
		cell.setCellValue(name);
		cell.setCellStyle(cellStyle);
	}

	/**
	 * 设置标题行的值
	 * 
	 * @param row
	 * @param titles
	 */
	private static void createTitle(Row row, List<String> titles) {
		for (int i = 0; i < titles.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(titles.get(i));
		}
	}

	/**
	 * 设置数据行的值
	 * 
	 * @param row
	 * @param project
	 * @param items
	 */
	private static void setDataRow(Row row, CellStyle style, Map<String, Object> map, List<String> items) {
		int size = items.size();
		for (int i = 0; i < size; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(map.get(items.get(i)).toString());
		}

	}
}
