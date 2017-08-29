package com.wechat.service;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class GetCity {

	public static Map<String,Object> getUserCity(String latitude, String longtitude) {
		String result = null;
		String url = "http://api.map.baidu.com/geocoder";
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("location", latitude+","+longtitude);// 
		params.put("output", "json");
		Map<String,Object> bean = new HashMap<String,Object>();
		JsonParser parse = new JsonParser();//创建json解析器
		try {
			result = UtilService.net(url, params, "GET");
			JsonObject json=(JsonObject) parse.parse(result);  //创建jsonObject对象			
			JsonObject js = json.get("result").getAsJsonObject();
			JsonObject addressComponent = js.get("addressComponent").getAsJsonObject();
			String city = addressComponent.get("city").getAsString();
			bean.put("city", city);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(getUserCity("36.347084", "115.004372"));
	}

}
