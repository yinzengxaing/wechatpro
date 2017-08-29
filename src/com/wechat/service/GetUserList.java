package com.wechat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.ssm.util.TokenThread;
import com.ssm.wechatpro.object.OutputObject;

public class GetUserList {
	
	
	@Resource
	public static OutputObject outputObject = new OutputObject();
	/**
	 * 获取公众号用户OPENID集合
	 * @return
	 * @throws Exception
	 */
	public static List<String> getRequest() throws Exception{
		String result = null;
		String url="https://api.weixin.qq.com/cgi-bin/user/get";
		List<String> beans = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("access_token", TokenThread.accessToken.getToken());
		result = UtilService.net(url, params, "GET");
		JSONObject object = JSONObject.fromObject(result);
		JSONObject data = JSONObject.fromObject(object.get("data"));
		JSONArray openid = data.getJSONArray("openid");
		for (int i = 0; i < openid.size(); i++) {
			beans.add(openid.get(i).toString());
		}
		String next_openid = object.getString("next_openid");
		int total = Integer.parseInt(object.getString("total"));
		int count = Integer.parseInt(object.getString("count"));
		for (int i = count; i < total; i += count) {
			params.put("next_openid", next_openid);
			result = UtilService.net(url, params, "GET");
			JSONObject object1 = JSONObject.fromObject(result);
			JSONObject data1 = JSONObject.fromObject(object1.get("data"));
			JSONArray openid1 = data1.getJSONArray("openid");
			for (int j = 0; j < openid1.size(); j++) {
				beans.add(openid1.get(j).toString());
			}
			next_openid = object1.getString("next_openid");
		}
	    return beans;
	}
	
	public static void main(String[] args) throws Exception{
	 System.out.println(getRequest().toString());
	}
	

}
