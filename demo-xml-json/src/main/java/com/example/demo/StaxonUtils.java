package com.example.demo;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;

import org.json.JSONObject;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLInputFactory;
import de.odysseus.staxon.json.JsonXMLOutputFactory;
import de.odysseus.staxon.xml.util.PrettyXMLEventWriter;

public class StaxonUtils {
	/**
	 * @Description: json string convert to xml string
	 * @author watermelon_code
	 * @date 2017年7月19日 上午10:50:32
	 */
	public static String json2xml(String json) {
		StringReader input = new StringReader(json);
		StringWriter output = new StringWriter();
		JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).build();
		try {
			XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
			XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
			writer = new PrettyXMLEventWriter(writer);
			writer.add(reader);
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output.toString();
	}

	/**
	 * @Description: json string convert to xml string ewidepay ues only
	 * @author watermelon_code
	 * @date 2017年7月19日 上午10:50:32
	 */
	public static String json2xmlPay(String json) {
		StringReader input = new StringReader(json);
		StringWriter output = new StringWriter();
		JsonXMLConfig config = new JsonXMLConfigBuilder().multiplePI(false).repairingNamespaces(false).build();
		try {
			XMLEventReader reader = new JsonXMLInputFactory(config).createXMLEventReader(input);
			XMLEventWriter writer = XMLOutputFactory.newInstance().createXMLEventWriter(output);
			writer = new PrettyXMLEventWriter(writer);
			writer.add(reader);
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (output.toString().length() >= 38) {// remove <?xml version="1.0" encoding="UTF-8"?>
			return "<xml>" + output.toString().substring(39) + "</xml>";
		}
		return output.toString();
	}

	/**
	 * @Description: xml string convert to json string
	 * @author watermelon_code
	 * @date 2017年7月19日 上午10:50:46
	 */
	public static String xml2json(String xml) {
		StringReader input = new StringReader(xml);
		StringWriter output = new StringWriter();
		JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true).autoPrimitive(true).prettyPrint(true).build();
		try {
			XMLEventReader reader = XMLInputFactory.newInstance().createXMLEventReader(input);
			XMLEventWriter writer = new JsonXMLOutputFactory(config).createXMLEventWriter(output);
			writer.add(reader);
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
				input.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return output.toString();
	}

	/**
	 * @Description: 去掉转换xml之后的换行和空格
	 * @author watermelon_code
	 * @date 2017年8月9日 下午4:05:44
	 */
	public static String json2xmlReplaceBlank(String json) {
		String str = StaxonUtils.json2xml(json);
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static void main(String[] args) {
		JSONObject json = new JSONObject();
		json.put("name", "jack");
		json.put("age", 25);
		String xmlstr = "<xml><ToUserName><![CDATA[toUser]]></ToUserName><FromUserName><![CDATA[fromUser]]></FromUserName><CreateTime>1348831860</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[thisisatest]]></Content><MsgId>1234567890123456</MsgId></xml>";
		System.out.println("JSON-->XML:");
		System.out.println("JSON:" + json.toString());
		System.out.println("---------------------------------------------------------------");
		System.out.println("普通转XML带格式：\n" + StaxonUtils.json2xml(json.toString()));
		System.out.println("---------------------------------------------------------------");
		System.out.println("转XML去掉头部、前后补充<XML>：\n" + StaxonUtils.json2xmlPay(json.toString()));
		System.out.println("---------------------------------------------------------------");
		System.out.println("普通转XML去掉空格换行：\n" + StaxonUtils.json2xmlReplaceBlank(json.toString()));
		System.out.println("---------------------------------------------------------------");
		System.out.println("XML转JSON：\n" + StaxonUtils.xml2json(xmlstr));
	}
}
