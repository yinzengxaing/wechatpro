package com.wechat.service;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.ssm.util.TokenThread;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.WeixinUtil;

/**
 * 获取会员信息
 * @author ZXR
 *
 */
@SuppressWarnings("unchecked")
public class GetMemberMassage {
	
	@Resource
	public static OutputObject outputObject =  new OutputObject();
	
	public static Map<String,Object> pullMemberInfo(String cardId,String code) throws Exception{
		String result = null;
		Map<String,Object> bean = new HashMap<String, Object>();
		String url = "https://api.weixin.qq.com/card/membercard/userinfo/get?access_token="+TokenThread.accessToken.getToken();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("card_id",cardId);
		params.put("code",code);
		result = UtilService.net(url, params, "POST");
		JSONObject object = JSONObject.fromObject(result);
		while(!object.getString("errcode").equals("0")){
    		if(object.getString("errcode").equals(Constants.TOKEINVALID)){
    			TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid,TokenThread.appsecret);
    			url = "https://api.weixin.qq.com/card/membercard/userinfo/get?access_token="+TokenThread.accessToken.getToken();
    	    	result=UtilService.net(url, params, "POST");
    	        object = JSONObject.fromObject(result);
    		}else{
    			String returnMessage = object.getString("errmsg");
    			String returnCode = object.getString("errcode");
    			outputObject.setreturnMessage(returnMessage, returnCode);
    		}
    	}
		GetActivationCard.getCrad(object.getString("membership_number"), code);
		if(object.get("has_active").toString().equals("true")){//卡如果被激活获取用户信息
			if(object.containsKey("user_info")){
				JSONObject user_info = JSONObject.fromObject(object.getString("user_info"));
				JSONArray common_field_list =  user_info.getJSONArray("common_field_list");
				Map<String, Object> map = (Map<String,Object>) common_field_list.get(0);
				bean.put("wechatNowUser", map.get("value"));
				bean.put("wechatIntegral", object.get("bonus"));
				bean.put("openid", object.getString("openid"));
				bean.put("membershipCardId", cardId);
				bean.put("has_active", true);
			}
		}else{
			bean.put("has_active", false);
		}
		return bean;
	}

}
