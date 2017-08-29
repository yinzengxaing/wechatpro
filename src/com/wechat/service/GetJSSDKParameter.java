package com.wechat.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.ssm.util.TokenThread;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.OrderUtil;
import com.ssm.wechatpro.util.WeixinUtil;

public class GetJSSDKParameter {
	
	@Resource
	public static OutputObject outputObject = new OutputObject();
	
	/**
	 * 获取jsapi_ticket
	 * @return
	 * @throws Exception
	 */
	public static String getJsapiTicket() throws Exception{
		String result = null;
		String ticket = null;
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("access_token",TokenThread.accessToken.getToken());
		params.put("type", "jsapi");
		result = UtilService.net(url, params, "GET");
		JSONObject object = JSONObject.fromObject(result);
		while(!object.getString("errcode").equals("0")){
			if(object.getString("errcode").equals(Constants.TOKEINVALID)){
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid,TokenThread.appsecret);
				params.put("access_token",TokenThread.accessToken.getToken());
				result=UtilService.net(url, params, "GET");
				object = JSONObject.fromObject(result);
			}else{
				String returnMessage = object.getString("errmsg");
    			String returnCode = object.getString("errcode");
    			outputObject.setreturnMessage(returnMessage, returnCode);
			}
		}
		return ticket;
	}
	
	public static Map<String,Object> getWechatJsParameter(Map<String,Object> param) throws Exception{
		String jsapi_ticket = getJsapiTicket();
		Map<String,Object> map = new HashMap<>();
		map.put("jsapi_ticket", jsapi_ticket);
		map.put("timestamp", param.get("timestamp"));
		map.put("url", Constants.WECHAT_FOR_SIGNATURE);
		map.put("noncestr", param.get("nonceStr"));
		//排序  
        String string1 = OrderUtil.sortParameters(map);  
        //拼接API秘钥  
		String signature = OrderUtil.sha1(string1); 
		map.put("signature", signature);
		map.put("appId", Constants.APPID);
		return map;
	}
	
	public static void main(String[] args) throws Exception{
		getJsapiTicket();
	}

}
