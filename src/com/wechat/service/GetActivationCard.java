package com.wechat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import com.ssm.util.TokenThread;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.WeixinUtil;


public class GetActivationCard {

	@Resource
	public static OutputObject outputObject = new OutputObject();
	/**
	 * 激活会员卡接口
	 * @param membership_number
	 * @param code
	 * @throws Exception
	 */
	public static void getCrad(String membership_number,String code) throws Exception{
		String result = null;
		String url = "https://api.weixin.qq.com/card/membercard/activate?access_token="+TokenThread.accessToken.getToken();// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("membership_number", membership_number);// 会员卡编号
		params.put("code", code);// 领取会员卡用户获得的code             
		result = UtilService.net(url, params, "POST");
		JSONObject object = JSONObject.fromObject(result);
		while(!object.getString("errcode").equals("0")){
			if(object.getString("errcode").equals(Constants.TOKEINVALID)){
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid,TokenThread.appsecret);
				url = "https://api.weixin.qq.com/card/membercard/activate?access_token="+TokenThread.accessToken.getToken();
				result=UtilService.net(url, params, "POST");
				object = JSONObject.fromObject(result);
				}else{
					String returnMessage = object.getString("errmsg");
					String returnCode = object.getString("errcode");
					outputObject.setreturnMessage(returnMessage, returnCode);
					}
			}
		}
	
	/**
	 * 设置开卡字段
	 * @param cardId
	 * @throws Exception
	 */
	public static void setCard(String cardId) throws Exception{
		String result = null;
		String url = "https://api.weixin.qq.com/card/membercard/activateuserform/set?access_token="+TokenThread.accessToken.getToken();
		Map<String,Object> params = new HashMap<String, Object>();
		Map<String,Object> required_form = new HashMap<>();
		required_form.put("can_modify", false);
		List<String> common_field_id_list = new ArrayList<>();
		common_field_id_list.add("USER_FORM_INFO_FLAG_MOBILE");
		required_form.put("common_field_id_list", common_field_id_list);
		Map<String,Object> optional_form = new HashMap<>();
		optional_form.put("can_modify", false);
		List<String> common_field_id_list1 = new ArrayList<>();
		common_field_id_list1.add("USER_FORM_INFO_FLAG_NAME");
		common_field_id_list1.add("USER_FORM_INFO_FLAG_SEX");
		optional_form.put("common_field_id_list", common_field_id_list1);
	    params.put("card_id", cardId);
	    params.put("required_form", required_form);
	    params.put("optional_form", optional_form);
	    result = UtilService.net(url, params, "POST");
	    JSONObject object = JSONObject.fromObject(result);
	    while(!object.getString("errcode").equals("0")){
	    	if(object.getString("errcode").equals(Constants.TOKEINVALID)){
	    		TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid,TokenThread.appsecret);
	    		url = "https://api.weixin.qq.com/card/membercard/activateuserform/set?access_token="+TokenThread.accessToken.getToken();
	    		result=UtilService.net(url, params, "POST");
	    		object = JSONObject.fromObject(result);
	    		}else{
	    			String returnMessage = object.getString("errmsg");
	    			String returnCode = object.getString("errcode");
	    			outputObject.setreturnMessage(returnMessage, returnCode);
	    			}
	    	}
	    }
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		//getCrad("AAA00000001","12312313");
		setCard("pSU_mt4ZKnrDcBRgD_sxiJiG2eDM");
	}
	
	
	

}
