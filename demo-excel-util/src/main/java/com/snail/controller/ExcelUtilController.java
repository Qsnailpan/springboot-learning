package com.snail.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.snail.util.excel.CommonHandleExcel;
import com.snail.util.excel.ExcelUtil;

/**
 * Util excel
 * 
 * @author snail 2017年11月11日
 *
 */
@RestController
@RequestMapping("/util")
public class ExcelUtilController {

	private List<Map<String, Object>> initData() {
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < 5; i++) {
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("no", i + 1);
			data.put("name", "张" + i);
			data.put("age", 20 + i);
			data.put("address", "名人小区" + i + "栋");
			data.put("time", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
			datas.add(data);
		}
		return datas;
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void exportBase(HttpServletResponse response, HttpServletRequest request,
			@RequestParam(value = "title", defaultValue = "员工信息表") String title) {
		try {
			List<Map<String, Object>> datas = initData();
			String[] celTitle = new String[] { "序号", "姓名", "年齡", "地址", "时间" };
			String[] celVal = new String[] { "no", "name", "age", "address", "time" };
			ExcelUtil.createWorkBook(title, celTitle, celVal, datas, response, new CommonHandleExcel(celTitle.length, datas.size()), null);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}