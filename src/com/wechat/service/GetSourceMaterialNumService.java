package com.wechat.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.ssm.util.TokenThread;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.WeixinUtil;


/**
 * 获取素材总数----成功
 * @author 卫志强
 * voice_count	语音总数量
 * video_count	视频总数量
 * image_count	图片总数量
 * news_count	图文总数量
 */
public class GetSourceMaterialNumService {
	
	@Resource
	public static OutputObject outputObject = new OutputObject();
	
	/**
	 * 获取素材总数
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	public static void getSourceMaterialNum() throws Exception {
		String result = null;
		String url = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("access_token", TokenThread.accessToken.getToken());// 调用接口凭证
		result = UtilService.net(url, params, "GET");
		JSONObject object = JSONObject.fromObject(result);
		while (!object.getString("errcode").equals("0")) {
			if (object.getString("errcode").equals(Constants.TOKEINVALID)) {
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid, TokenThread.appsecret);
				params.put("access_token", TokenThread.accessToken.getToken());
				result = UtilService.net(url, params, "GET");
				object = JSONObject.fromObject(result);
			} else {
				String returnMessage = object.getString("errmsg");
				String returnCode = object.getString("errcode");
				outputObject.setreturnMessage(returnMessage, returnCode);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		getSourceMaterialNum();
	}
}
