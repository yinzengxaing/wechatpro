package com.wechat.service;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.codec.binary.Base64;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.ssm.util.TokenThread;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.TransUtil;

/**
 * 获取用户信息----成功
 * @author 卫志强
 *
 */
public class GetUserMationService {
	
	@Resource
	public static OutputObject outputObject = new OutputObject();

	/**
	 * 查看用户信息
	 **/
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";
	
	@SuppressWarnings("unchecked")
	public static List<Map<String,Object>> getRequest(List<Map<String,Object>> user) throws Exception {
		List<Map<String,Object>> beans = new ArrayList<Map<String,Object>>();
		String result = null;
		String url = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token="+TokenThread.accessToken.getToken();// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("user_list", user);
		result = UtilService.net(url, params, "POST");
		JSONObject object = JSONObject.fromObject(result);
		if(object.containsKey("user_info_list")){
			String userinfolist = object.getString("user_info_list");
			JSONArray array = JSONArray.fromObject(userinfolist);
			List<Map<String, Object>> object2 = (List<Map<String, Object>>) array;
			for (int i = 0; i < object2.size(); i++) {
				if(object2.get(i).get("subscribe").toString().equals(Constants.SUBSCRIBE)){
					Map<String, Object> bean = new HashMap<String, Object>();
					String openid = object2.get(i).get("openid").toString();
					bean.put("openid", openid);
					if(object2.get(i).containsKey("nickname")){
						bean.put("nickname", Base64.encodeBase64String(object2.get(i).get("nickname").toString().getBytes("utf-8")));
					}
					if(object2.get(i).containsKey("city")){
						bean.put("city", object2.get(i).get("city").toString().replaceAll("[\\x{10000}-\\x{10FFFF}]", ""));
					}
					if(object2.get(i).containsKey("country")){
						bean.put("country", object2.get(i).get("country").toString().replaceAll("[\\x{10000}-\\x{10FFFF}]", ""));
					}
					if(object2.get(i).containsKey("subscribeTime")){
						bean.put("subscribeTime", TransUtil.WeChatData(object2.get(i).get("subscribe_time").toString()));
					}
					if(object2.get(i).containsKey("sex")){
						bean.put("sex", object2.get(i).get("sex").toString());
					}
					if(object2.get(i).containsKey("province")){
						bean.put("province", object2.get(i).get("province").toString().replaceAll("[\\x{10000}-\\x{10FFFF}]", ""));
					}
					if(object2.get(i).containsKey("groupid")){
						bean.put("groupid", object2.get(i).get("groupid").toString());
					}
					if(object2.get(i).containsKey("headimgurl")){
						bean.put("headimgurl", object2.get(i).get("headimgurl").toString());
					}
					if(object2.get(i).containsKey("subscribe")){
						bean.put("subscribe", object2.get(i).get("subscribe").toString());
					}
					bean.put("remark", 0);
					bean.put("user_id", 0);
					bean.put("frozen", 1);
					bean.put("appId", Constants.APPID);
					bean.put("wechatIntegral", 0);
					beans.add(bean);
				}
			}
		}
		return beans;
	}
	
	public static Map<String,Object> getRequest1(String openid) throws Exception {
		Map<String,Object> bean = new HashMap<String, Object>();
		String result = null;
		String url = "https://api.weixin.qq.com/cgi-bin/user/info";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("access_token", TokenThread.accessToken.getToken());// 调用接口凭证
		params.put("lang", "zh_CN");
		params.put("openid", openid);
		result = UtilService.net(url, params, "GET");
		JSONObject object = JSONObject.fromObject(result);
		bean.put("openid", openid);
		if(object.containsKey("nickname")){
			bean.put("nickname", Base64.encodeBase64String(object.getString("nickname").getBytes("utf-8")));
		}
		if(object.containsKey("city")){
			bean.put("city", object.getString("city").replaceAll("[\\x{10000}-\\x{10FFFF}]", ""));
		}
		if(object.containsKey("country")){
			bean.put("country", object.getString("country").replaceAll("[\\x{10000}-\\x{10FFFF}]", ""));
		}
		if(object.containsKey("subscribeTime")){
			bean.put("subscribeTime", TransUtil.WeChatData(object.getString("subscribe_time")));
		}
		if(object.containsKey("sex")){
			bean.put("sex", object.getString("sex"));
		}
		if(object.containsKey("province")){
			bean.put("province", object.getString("province").replaceAll("[\\x{10000}-\\x{10FFFF}]", ""));
		}
		if(object.containsKey("groupid")){
			bean.put("groupid", object.getString("groupid"));
		}
		if(object.containsKey("headimgurl")){
			bean.put("headimgurl", object.getString("headimgurl"));
		}
		if(object.containsKey("subscribe")){
			bean.put("subscribe", object.getString("subscribe"));
		}
		bean.put("remark", 0);
		bean.put("user_id", 0);
		bean.put("frozen", 1);
		bean.put("appId", Constants.APPID);
		bean.put("wechatIntegral", 0);
		return bean;
	}
	
	public static void main(String[] args) throws Exception{
		List<Map<String,Object>> beans = new ArrayList<Map<String,Object>>();
		Map<String,Object> bean = new HashMap<String, Object>();
		bean.put("openid", "oSU_mt59Lj4_6P5K269wAeXThJLE");
		bean.put("lang", "zh_CN");
		Map<String,Object> bean1 = new HashMap<String, Object>();
		bean1.put("openid", "oSU_mt1Tjc1dCBvPd0YXJA_ICN9M");
		bean1.put("lang", "zh_CN");
		Map<String,Object> bean2 = new HashMap<String, Object>();
		bean2.put("openid", "oSU_mt_3ELsuZUFUAg3mAOzOomEs");
		bean2.put("lang", "zh_CN");
		beans.add(bean);
		beans.add(bean1);
		beans.add(bean2);
	}
	
	

}
