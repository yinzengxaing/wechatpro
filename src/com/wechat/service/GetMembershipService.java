package com.wechat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.ssm.util.TokenThread;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.WeixinUtil;

public class GetMembershipService {
	
	@Resource
	public static OutputObject outputObject = new OutputObject();
	
	
	public static Map<String,Object> getusedMembership(String cardId) throws Exception{
		String result = null;
		String url = "https://api.weixin.qq.com/card/get?access_token="+TokenThread.accessToken.getToken();
		Map<String,Object> bean = new HashMap<>();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("card_id",cardId);
		result = UtilService.net(url, params, "POST");
		JSONObject object = JSONObject.fromObject(result);
		while (!object.getString("errcode").equals("0")) {
			if (object.getString("errcode").equals(Constants.TOKEINVALID)) {
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid, TokenThread.appsecret);
				url = "https://api.weixin.qq.com/card/get?access_token="+TokenThread.accessToken.getToken();
				result = UtilService.net(url, params, "POST");
				object = JSONObject.fromObject(result);
			} else {
				String returnMessage = object.getString("errmsg");
				String returnCode = object.getString("errcode");
				outputObject.setreturnMessage(returnMessage, returnCode);
			}
		}
		//获取card中的信息
		JSONObject card = object.getJSONObject("card");
		bean.put("card_type", card.getString("card_type"));
		//获取card中的member_card
		JSONObject member_card = card.getJSONObject(card.getString("card_type").toLowerCase());
		if(member_card.containsKey("prerogative")){
			bean.put("prerogative", member_card.getString("prerogative"));
		}
		if(member_card.containsKey("discount")){
			bean.put("discount", member_card.getString("discount"));
		}
		if(member_card.containsKey("background_pic_url")){
			bean.put("background_pic_url", member_card.getString("background_pic_url"));
		}
		//获取card中的 member_card  base_info中的信息
		JSONObject base_info = member_card.getJSONObject("base_info");
		bean.put("logo_url", base_info.getString("logo_url"));
		bean.put("code_type", base_info.getString("code_type"));
		bean.put("brand_name", base_info.getString("brand_name"));
		bean.put("title", base_info.getString("title"));
		bean.put("color", base_info.getString("color"));
		bean.put("notice", base_info.getString("notice"));
		if(member_card.containsKey("service_phone")){
			bean.put("service_phone", base_info.getString("service_phone"));
		}
		bean.put("description", base_info.getString("description"));
		bean.put("get_limit", base_info.getString("get_limit"));
		bean.put("can_share", true);
		bean.put("can_give_friend", base_info.getString("can_give_friend"));
		bean.put("createTime", base_info.getString("create_time"));
		if(base_info.containsKey("need_push_on_view")){
			bean.put("need_push_on_view", base_info.getString("need_push_on_view"));
		}
		bean.put("use_all_locations", base_info.getString("use_all_locations"));
		//获取获取card中的  member_card  base_info  date_info
		JSONObject date_info = base_info.getJSONObject("date_info");
		bean.put("date_info", date_info.toString());
		bean.put("type", date_info.getString("type"));
		//获取获取card中的 member_card  base_info  sku
		JSONObject sku = base_info.getJSONObject("sku");
		bean.put("sku", sku.toString());
		bean.put("quantity", sku.getString("total_quantity"));
		//获取card中的 member_card  advanced_info中的信息
		JSONObject advanced_info = member_card.getJSONObject("advanced_info");
		//获取card中的 member_card  advanced_info text_image_list
		JSONArray text_image_list = advanced_info.getJSONArray("text_image_list");
		if(!text_image_list.isEmpty()){
			@SuppressWarnings("unchecked")
			Map<String,Object> textimagelist = (Map<String,	Object>) text_image_list.get(0);
			bean.put("image_url", textimagelist.get("image_url").toString());
			bean.put("text", textimagelist.get("text").toString());
		}
		//获取card中的 member_card  advanced_info business_service
		JSONArray businessservice = advanced_info.getJSONArray("business_service");
		if(!businessservice.isEmpty()){
			String business_service = businessservice.get(0).toString();
			for(int i = 1 ;i < businessservice.size();i++){
				business_service = business_service+"," + businessservice.get(i).toString();
			}
			bean.put("business_service", business_service);
		}
		//获取card中的 member_card  bonus_rule
		if(member_card.containsKey("bonus_rule")){
			JSONObject bonus_rule = member_card.getJSONObject("bonus_rule");
			bean.put("cost_money_unit", bonus_rule.getString("cost_money_unit"));
			bean.put("increase_bonus", bonus_rule.getString("increase_bonus"));
			bean.put("cost_bonus_unit", bonus_rule.getString("cost_bonus_unit"));
			bean.put("reduce_money", bonus_rule.getString("reduce_money"));
			bean.put("least_money_to_use_bonus", bonus_rule.getString("least_money_to_use_bonus"));
			bean.put("max_reduce_bonus", bonus_rule.getString("max_reduce_bonus"));
		}
		bean.put("card_id", cardId);
		return bean;
	}
	
	
	/**
	 * 修改会员卡信息
	 * @param map
	 * @throws Exception
	 */
	public static void updateMembership(Map<String,Object> map) throws Exception{
		String result = null;
		String url = "https://api.weixin.qq.com/card/update?access_token="+TokenThread.accessToken.getToken();
		Map<String,Object> params = new HashMap<String, Object>();
		//member_card 
		Map<String, Object> member_card = new HashMap<String,Object>();
		//background_pic_url 
		member_card.put("background_pic_url", map.get("background_pic_url"));
		//base_info 
		Map<String, Object> base_info = new HashMap<String,Object>();
		base_info.put("logo_url", map.get("logo_url"));
		base_info.put("code_type", map.get("code_type"));
		base_info.put("title", map.get("title"));
		base_info.put("color", map.get("color"));
		base_info.put("notice", map.get("notice"));
		base_info.put("logo_url", map.get("logo_url"));
		base_info.put("service_phone", map.get("service_phone"));
		base_info.put("description", map.get("description"));
		member_card.put("base_info", base_info);
		//直接获取
		member_card.put("prerogative", map.get("prerogative"));
		Map<String, Object> bonus_rule = new HashMap<String,Object>();
		bonus_rule.put("cost_money_unit", map.get("cost_money_unit"));
		bonus_rule.put("increase_bonus", map.get("increase_bonus"));
		bonus_rule.put("cost_bonus_unit", map.get("cost_bonus_unit"));
		bonus_rule.put("reduce_money", map.get("reduce_money"));
		bonus_rule.put("least_money_to_use_bonus", map.get("least_money_to_use_bonus"));
		bonus_rule.put("max_reduce_bonus", map.get("max_reduce_bonus"));
		member_card.put("bonus_rule", bonus_rule);
		member_card.put("discount", map.get("discount"));
		params.put("member_card", member_card);
		params.put("card_id", map.get("card_id"));
		result = UtilService.net(url, params, "POST");
		JSONObject object = JSONObject.fromObject(result);
		while(!object.getString("errcode").equals("0")){
			if(object.getString("errcode").equals(Constants.TOKEINVALID)){
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid,TokenThread.appsecret);
				url = "https://api.weixin.qq.com/card/update?access_token="+TokenThread.accessToken.getToken();
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
	 * 删除会员卡
	 * @param cardId
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteMembership(String cardId) throws Exception{
		String result = null;
		boolean errcode = false;
		String url = "https://api.weixin.qq.com/card/delete?access_token="+TokenThread.accessToken.getToken();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("card_id",cardId);
		result = UtilService.net(url, params, "POST");
		JSONObject object = JSONObject.fromObject(result);
		while (!object.getString("errcode").equals("0")) {
			if (object.getString("errcode").equals(Constants.TOKEINVALID)) {
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid, TokenThread.appsecret);
				url = "https://api.weixin.qq.com/card/delete?access_token="+ TokenThread.accessToken.getToken();
				result = UtilService.net(url, params, "POST");
				object = JSONObject.fromObject(result);
			} else {
				String returnMessage = object.getString("errmsg");
				String returnCode = object.getString("errcode");
				outputObject.setreturnMessage(returnMessage, returnCode);
			}
		}
		if (object.getString("errcode").equals("0")) {
			errcode = true;
		}
		return errcode;
	}
	/**
	 * 获取已投放卡卷的id
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getAllMembership() throws Exception{
		String result = null;
		String url = "https://api.weixin.qq.com/card/batchget?access_token="+TokenThread.accessToken.getToken();
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("offset","0");
		params.put("count","50");
		List<String> statusList = new ArrayList<String>();
		statusList.add("CARD_STATUS_VERIFY_OK");
		statusList.add("CARD_STATUS_DISPATCH");
		params.put("status_list",statusList);
		result = UtilService.net(url, params, "POST");
		JSONObject object = JSONObject.fromObject(result);
		while (!object.getString("errcode").equals("0")) {
			if (object.getString("errcode").equals(Constants.TOKEINVALID)) {
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid, TokenThread.appsecret);
				url = "https://api.weixin.qq.com/card/batchget?access_token="+ TokenThread.accessToken.getToken();
				result = UtilService.net(url, params, "POST");
				object = JSONObject.fromObject(result);
			} else {
				String returnMessage = object.getString("errmsg");
				String returnCode = object.getString("errcode");
				outputObject.setreturnMessage(returnMessage, returnCode);
			}
		}
		int total_num = object.getInt("total_num");
		JSONArray card_id_list =  object.getJSONArray("card_id_list");
		List<String> cardidlist = (List<String>)card_id_list;
		for(int i = 50;i<total_num;i+=50){
			int count = 50 ;
			int offset = i;
			params.clear();
			params.put("offset",offset);
			params.put("count",count);
			params.put("status_list","CARD_STATUS_DISPATCH");
			result = UtilService.net(url, params, "POST");
		    object = JSONObject.fromObject(result);
			while (!object.getString("errcode").equals("0")) {
				if (object.getString("errcode").equals(Constants.TOKEINVALID)) {
					TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid, TokenThread.appsecret);
					url = "https://api.weixin.qq.com/card/batchget?access_token="+ TokenThread.accessToken.getToken();
					result = UtilService.net(url, params, "POST");
					object = JSONObject.fromObject(result);
				} else {
					String returnMessage = object.getString("errmsg");
					String returnCode = object.getString("errcode");
					outputObject.setreturnMessage(returnMessage, returnCode);
				}
			}
			card_id_list =  object.getJSONArray("card_id_list");
			cardidlist.addAll((List<String>)card_id_list);
			
		}
		return cardidlist;
		 
	}
	
	/**
	 * 创建会员卡
	 * @throws Exception
	 */
	public static String creatMembership(Map<String,Object> map) throws Exception {
		String result = null;
		String cardId = null;
		String url = "https://api.weixin.qq.com/card/create?access_token="+TokenThread.accessToken.getToken();
		//params
		Map<String, Object> params = new HashMap<String, Object>();
		
		//card
		Map<String, Object>  card = new HashMap<String,Object>();
		
		card.put("card_type", map.get("card_type"));
		
		//member_card 
		Map<String, Object> member_card = new HashMap<String,Object>();
		
		//background_pic_url 
		member_card.put("background_pic_url", map.get("background_pic_url"));
		
		//base_info 
		Map<String, Object> base_info = new HashMap<String,Object>();
		base_info.put("logo_url", map.get("logo_url"));
		base_info.put("brand_name", map.get("brand_name"));
		base_info.put("code_type", map.get("code_type"));
		base_info.put("title", map.get("title"));
		base_info.put("color", map.get("color"));
		base_info.put("notice", map.get("notice"));
		base_info.put("logo_url", map.get("logo_url"));
		base_info.put("service_phone", map.get("service_phone"));
		base_info.put("use_all_locations", map.get("use_all_locations"));
		base_info.put("description", map.get("description"));

		base_info.put("get_limit", map.get("get_limit"));
		base_info.put("can_give_friend", map.get("can_give_friend"));
		base_info.put("need_push_on_view", map.get("need_push_on_view"));
		
		base_info.put("date_info", map.get("date_info"));
       	base_info.put("sku", map.get("sku"));
	
     	member_card.put("base_info", base_info);
		
		//advanced_info
		Map<String, Object> advanced_info = new HashMap<String,Object>();
		
		List<Object> text_image_list = new ArrayList<Object>();
		Map<String, Object> map1 = new HashMap<String,Object>();
		map1.put("image_url", map.get("image_url"));
		map1.put("text", map.get("text"));
		text_image_list.add(map1);
		advanced_info.put("text_image_list", text_image_list);
		
		List<Object> time_limit = new ArrayList<Object>();
		Map<String, Object> timelimit = new HashMap<String,Object>();
		timelimit.put("type", map.get("time_limit"));
		time_limit.add(timelimit);
		advanced_info.put("time_limit", time_limit);
		advanced_info.put("business_service", map.get("business_service"));
		
		member_card.put("advanced_info", advanced_info);
		
		//直接获取
		member_card.put("supply_bonus", map.get("supply_bonus"));
		member_card.put("supply_balance", map.get("supply_balance"));
		member_card.put("prerogative", map.get("prerogative"));
		member_card.put("wx_activate", map.get("wx_activate"));
		Map<String, Object> bonus_rule = new HashMap<String,Object>();
		bonus_rule.put("cost_money_unit", map.get("cost_money_unit"));
		bonus_rule.put("increase_bonus", map.get("increase_bonus"));
		bonus_rule.put("cost_bonus_unit", map.get("cost_bonus_unit"));
		bonus_rule.put("reduce_money", map.get("reduce_money"));
		bonus_rule.put("least_money_to_use_bonus", map.get("least_money_to_use_bonus"));
		bonus_rule.put("max_reduce_bonus", map.get("max_reduce_bonus"));
		member_card.put("bonus_rule", bonus_rule);
		
		member_card.put("discount", map.get("discount"));
		
		card.put("member_card", member_card);
		
		params.put("card", card);
		result = UtilService.net(url, params, "POST");
		JSONObject object = JSONObject.fromObject(result);
		while (!object.getString("errcode").equals("0")) {
			if (object.getString("errcode").equals(Constants.TOKEINVALID)) {
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid, TokenThread.appsecret);
				url = "https://api.weixin.qq.com/card/create?access_token="+ TokenThread.accessToken.getToken();
				result = UtilService.net(url, params, "POST");
				object = JSONObject.fromObject(result);
			} else {
				String returnMessage = object.getString("errmsg");
				String returnCode = object.getString("errcode");
				outputObject.setreturnMessage(returnMessage, returnCode);
			}
		}
		cardId = object.getString("card_id");
		return cardId;
	}
	
	public static void main(String[] agrs) throws Exception{
		System.out.println(getusedMembership("pSU_mtwB0QX7Wc4U4fu_8Vdg_u4M"));
	}
	
}


