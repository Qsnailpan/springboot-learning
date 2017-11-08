package com.snail.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snail.util.ExcelUtils;

@RestController
@RequestMapping("w/exportUtil")
public class ExportExcelUtilController {

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

		try {
			List<Map<String, Object>> datas = initData();
			List<String> titles = Arrays.asList(new String[] { "序号", "姓名", "年齡", "地址", "时间" });
			List<String> items = Arrays.asList(new String[] { "no", "name", "age", "address", "time" });
			
			ExcelUtils.epsCreateWorkBook(response, request, datas, titles, items, "员工信息表");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}