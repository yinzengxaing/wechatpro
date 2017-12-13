package com.wechat.service;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;

import org.apache.http.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ssm.util.TokenThread;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.impl.EmpowerWebpageServiceImpl;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.WeixinUtil;
import net.sf.json.JSONObject;

/**
 * 根据Code获取OPENID-----成功
 * @author 卫志强
 *
 */
public class GetOpenIdByCode {
	
	@Resource
	public static OutputObject outputObject = new OutputObject();
	
	private static Logger logger = LoggerFactory.getLogger(GetOpenIdByCode.class);  
	
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static final String OAUTHUSERINFO = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	/**
	 * 根据Code获取OPENID
	 * @param appId
	 * @param secret
	 * @param code
	 * @return
	 */
	public static Map<String,Object> getRequest1(String appId, String secret, String code) {
		String result = null;
		Map<String,Object> bean = new HashMap<String, Object>();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("appid", appId);
		params.put("secret", secret);
		params.put("code", code);
		params.put("grant_type", "authorization_code");
		try {
			result = UtilService.net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if(object.containsKey("errcode")) {
				return null;
			}
			bean.put("openid", object.getString("openid"));
			bean.put("access_token", object.getString("access_token"));
			bean.put("refresh_token",  object.getString("refresh_token"));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("getRequest1-error={}",e);
		}
		return bean;
//			return bean;
	}
	/**
	 * 获取用户信息
	 * @param access_token
	 * @param openid
	 * @return
	 * @throws Exception 
	 */
	public static Map<String,Object> getUserMation(String access_token, String openid) throws Exception {
		Map<String,Object> bean = new HashMap<String, Object>();
		String result = null;
		String url = "https://api.weixin.qq.com/sns/userinfo";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("access_token", access_token);// 调用接口凭证
		params.put("openid", openid);// 普通用户的标识，对当前公众号唯一
		params.put("lang", "zh_CN");// 返回国家地区言版本，zh_CN 简体，zh_TW 繁体，en 英语
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
		bean.put("subscribe", object.getString("subscribe"));
		bean.put("openid", object.getString("openid"));
		bean.put("headImgUrl", object.getString("headimgurl"));
		bean.put("nickname", object.getString("nickname"));
		bean.put("sex", object.getString("sex"));
		bean.put("city", object.getString("city"));
		bean.put("country", object.getString("country"));
		bean.put("province", object.getString("province"));
		bean.put("headimgurl", object.getString("headimgurl"));
		bean.put("subscribeTime", object.getString("subscribe_time"));
		bean.put("remark", object.getString("remark"));
		bean.put("groupid", object.getString("groupid"));
		bean.put("unionid", object.getString("unionid"));
		return bean;
	}
	
}
