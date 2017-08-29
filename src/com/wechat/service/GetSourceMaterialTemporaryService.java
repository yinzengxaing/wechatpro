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
 * 获取临时素材-----成功
 * @author 卫志强
 *
 */
public class GetSourceMaterialTemporaryService {
	
	@Resource
	public static OutputObject outputObject = new OutputObject();
	
	/**
	 * 获取临时素材
	 * @param mediaId 要获取的素材的media_id
	 * @throws Exception
	 */
	public static void getSourceMaterialTemporary(String mediaId) throws Exception {
		String result = null;
		String url = "https://api.weixin.qq.com/cgi-bin/media/get";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("access_token", TokenThread.accessToken.getToken());// 调用接口凭证
		params.put("media_id", mediaId);
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
		getSourceMaterialTemporary("image");
	}
}
