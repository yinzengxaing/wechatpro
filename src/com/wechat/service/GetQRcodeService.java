package com.wechat.service;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import com.ssm.util.TokenThread;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.WeixinUtil;

public class GetQRcodeService {
	
	@Resource
	public static OutputObject outputObject = new OutputObject();
	
	/**
	 * 获取二维码ticket
	 * @param card_id
	 * @return
	 * @throws Exception
	 */
	
	public static String createQRcode(String card_id) throws Exception {
		String result = null;
		String ticket = null;
		String url = "https://api.weixin.qq.com/card/qrcode/create?access_token="+TokenThread.accessToken.getToken();
		Map<String, Object> params = new HashMap<String, Object>();
		Map<String, Object> action_info = new HashMap<String, Object>();
		Map<String,Object> card = new HashMap<>();
		card.put("card_id", card_id);
		action_info.put("card", card);
		params.put("action_name", "QR_CARD");
		params.put("expire_seconds", 1800);
		params.put("action_info", action_info);
		result = UtilService.net(url, params, "POST");
		JSONObject object = JSONObject.fromObject(result);
		while (!object.getString("errcode").equals("0")) {
			if (object.getString("errcode").equals(Constants.TOKEINVALID)) {
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid, TokenThread.appsecret);
				url = "https://api.weixin.qq.com/card/qrcode/create?access_token="+ TokenThread.accessToken.getToken();
				result = UtilService.net(url, params, "POST");
				object = JSONObject.fromObject(result);
			} else {
				String returnMessage = object.getString("errmsg");
				String returnCode = object.getString("errcode");
				outputObject.setreturnMessage(returnMessage, returnCode);
			}
		}
		ticket = object.getString("ticket");
		return ticket;
	}
	
	/**
	 * 通过ticket换取二维码
	 * @param ticket
	 */
	public static void getQRcodeByticket(String ticket){
		String result = null;
		String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;//>>TICKET记得进行UrlEncode返回说明
		Map<String ,Object> params = new HashMap<>();
		try {
			result = UtilService.net(url, params,"GET");
			JSONObject object = JSONObject.fromObject(result);
			System.out.println(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println(createQRcode("pSU_mt95VrSl9LvbQ6NO9sad1jw0"));
		//getQRcodeByticket("gQHB8DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0JIV3lhX3psZmlvSDZmWGVMMTZvAAIEsNnKVQMEIAMAAA==");
	}

}
