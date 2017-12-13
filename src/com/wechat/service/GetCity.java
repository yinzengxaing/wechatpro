package com.wechat.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;



public class GetCity {

	public static Map<String,Object> getUserCity(String latitude, String longtitude) {
		String result = null;
		String url = "http://gc.ditu.aliyun.com/regeocoding";
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("l", latitude+","+longtitude);// 
		params.put("type", "010");
		Map<String,Object> bean = new HashMap<String,Object>();
		try {
			result = UtilService.net(url, params, "GET");
			 JSONObject jsonObject = JSONObject.fromObject(result);  
		        JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("addrList"));  
		        JSONObject j_2 = JSONObject.fromObject(jsonArray.get(0));  
		        String allAdd = j_2.getString("admName");  
		        String arr[] = allAdd.split(",");
		        bean.put("city", arr[1]);
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
