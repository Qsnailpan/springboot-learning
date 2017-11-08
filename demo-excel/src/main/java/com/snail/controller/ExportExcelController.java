package com.snail.controller;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("w/expor")
public class ExportExcelController {

	private List<Map<String, Object>> initData() {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 5; i++) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("no", i + 1);
			data.put("name", "张三" + i);
			data.put("age", 20 + i);
			data.put("address", "名流花园" + i + "栋");
			data.put("time", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			datas.add(data);
		}
		return datas;
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public void exportBase(HttpServletResponse response, HttpServletRequest request) {
		OutputStream os = null;
		Workbook wb = null;
		try {

			List<Map<String, Object>> list2 = initData();
			;
			List<String> titles = Arrays.asList(new String[] { "序号", "姓名", "年齡", "地址", "时间" });
			List<String> items = Arrays.asList(new String[] { "no", "name", "age", "address", "time" });

			wb = new SXSSFWorkbook();
			Sheet sheet = wb.createSheet();

			// 合并单元格
			CellRangeAddress cra = new CellRangeAddress(0, 0, 0, titles.size() - 1);
			sheet.addMergedRegion(cra);
			sheet.setColumnWidth(3, 5000);
			sheet.setColumnWidth(4, 5000);
			String name1 = "信息表";
			// 创建表头
			Row row = sheet.createRow(0);
			createName(row, name1, wb);
			// 创建标题行
			row = sheet.createRow(1);
			createTitle(row, titles);
			CellStyle style = wb.createCellStyle();
			//填充数据
			if (list2 != null && list2.size() > 0) {
				for (int i = 0; i < list2.size(); i++) {
					row = sheet.createRow(i + 2);
					setDataRow(row, style, list2.get(i), items);
				}
			}
			String agent = request.getHeader("USER-AGENT");
			String fileName_;
			if (StringUtils.isNotBlank(agent) && agent.indexOf("Firefox") > -1) {
				fileName_ = new String(name1.getBytes(), "ISO8859-1");
			} else {
				fileName_ = URLEncoder.encode(name1, "UTF-8");
			}

			response.setHeader("Content-Disposition", "attachment; filename=" + fileName_ + ".xls");
			response.setContentType("application/msexcel");
			os = response.getOutputStream();
			wb.write(response.getOutputStream());
		} catch (Exception e) {
			IOUtils.closeQuietly(os);
			IOUtils.closeQuietly(wb);
		}
	}

	/**
	 * 创建表头
	 * 
	 * @param row
	 * @param name
	 * @param wb
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	private void createName(Row row, String name, Workbook wb) {
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
	private void createTitle(Row row, List<String> titles) {
		for (int i = 0; i < titles.size(); i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(titles.get(i));
		}
	}

	/**
	 * 设置数据行的值
	 * 
	 * @param row
	 * @param map
	 * @param items
	 */
	private void setDataRow(Row row, CellStyle style, Map<String, Object> map, List<String> items) {
		int size = items.size();
		for (int i = 0; i < size; i++) {
			Cell cell = row.createCell(i);
			if (map.get(items.get(i)) != null) {
				cell.setCellValue(map.get(items.get(i)).toString());
			}
		}

	}
}
