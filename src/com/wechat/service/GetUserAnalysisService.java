package com.wechat.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import com.ssm.util.TokenThread;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.WeixinUtil;

import net.sf.json.JSONObject;

/**
 * 用户分析数据接口
 * @author 卫志强
 *
 */
public class GetUserAnalysisService {
	
	@Resource
	public static OutputObject outputObject = new OutputObject();
	
	public static void getRequest(String startTime, String endTime) throws Exception {
		String result = null;
		String url = "https://api.weixin.qq.com/datacube/getusersummary?access_token="+TokenThread.accessToken.getToken();// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("begin_date", startTime);// 普通用户的标识，对当前公众号唯一
		params.put("end_date", endTime);// 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
		result = UtilService.net(url, params, "POST");
		JSONObject object = JSONObject.fromObject(result);
		while (!object.getString("errcode").equals("0")) {
			if (object.getString("errcode").equals(Constants.TOKEINVALID)) {
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid, TokenThread.appsecret);
				url = "https://api.weixin.qq.com/datacube/getusersummary?access_token="+ TokenThread.accessToken.getToken();
				result = UtilService.net(url, params, "POST");
				object = JSONObject.fromObject(result);
			} else {
				String returnMessage = object.getString("errmsg");
				String returnCode = object.getString("errcode");
				outputObject.setreturnMessage(returnMessage, returnCode);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		getRequest("2017-07-01","2017-07-04");
	}
}
