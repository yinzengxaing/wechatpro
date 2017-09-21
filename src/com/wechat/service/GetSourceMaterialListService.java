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
 * 获取素材列表
 * @author 卫志强
 *
 */
public class GetSourceMaterialListService {
	
	@Resource
	public static OutputObject outputObject = new OutputObject();
	
	/**
	 * 获取素材列表
	 * @param type 素材的类型，图片（image）、视频（video）、语音 （voice）、图文（news）
	 * @param offset 从全部素材的该偏移位置开始返回，0表示从第一个素材 返回
	 * @param count 返回素材的数量，取值在1到20之间
	 * @throws Exception
	 */
	public static void getSourceMaterial(String type, int offset, int count) throws Exception {
		String result = null;
		//TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid,TokenThread.appsecret);
		String url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+TokenThread.accessToken.getToken();// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("type", type);
		params.put("offset", offset);
		params.put("count", count);
		result = UtilService.net(url, params, "POST");
		JSONObject object = JSONObject.fromObject(result);
		while (!object.getString("errcode").equals("0")) {
			if (object.getString("errcode").equals(Constants.TOKEINVALID)) {
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid, TokenThread.appsecret);
				url = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token="+ TokenThread.accessToken.getToken();// 请求接口地址
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
		getSourceMaterial("image",0,10);
	}
}
