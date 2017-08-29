package com.wechat.service;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 根据ID获取地址----成功
 * @author 卫志强
 *
 */
public class IPService {

	// 配置您申请的KEY
	public static final String APPKEY = "5ebb13fd0a2e0e2d3ed7d18b518b85cd";

	// 1.IP归属地查询
	public static String getAddressByIp(String ip) {
		String return_data = null;
		String result = null;
		String url = "http://apis.juhe.cn/ip/ip2addr";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("ip", ip);// 需要查询的ip地址
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("dtype", "json");// 返回数据的格式,xml或json，默认json
		try {
			result = UtilService.net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				return_data = object.get("result").toString();
			} else {
				return_data = object.get("error_code") + ":" + object.get("reason");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return return_data;
	}

	/**
	 * IP归属地
	 * @param phone
	 * @return
	 */
	public static String getAddressByLoginIp(String ip) {
		String ip_data = null;
		String result = getAddressByIp(ip);
		if (result.startsWith("201102")) {
			ip_data = result;
		} else {
			JSONObject data = JSONObject.fromObject(result);
			ip_data = "地区:" + data.getString("area") + "\n位置" + data.getString("location");
		}
		return ip_data;
	}

}
