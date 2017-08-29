package com.ssm.wechatpro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import com.ssm.wechatpro.dao.WechatMembershipMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatMembershipService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.ToolUtil;
import com.wechat.service.CreateLandingpage;
import com.wechat.service.GetMembershipService;

@Service
public class WechatMembershipServiceImpl implements WechatMembershipService {
	
	@Resource
	WechatMembershipMapper wechatMembershipMapper;

	/**
	 * 创建会员卡
	 */
	@Override
	public void createWechatMembership(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		if(!ToolUtil.contains(params,Constants.MEMBERSHIP_KEY,Constants.MEMBERSHIP_RETURNMESSAGE, inputObject, outputObject)){
			return ;
		}
		//前台获得的值
		Double disc = Double.parseDouble(params.get("discount").toString());
		int discount = (int)(disc/9*10);
		params.put("discount", discount);
		if(params.get("use_all_locations").toString().equals("true")){
			params.put("use_all_locations",true);
		}else{
			params.put("use_all_locations",false);
		}
		params.put("type", "DATE_TYPE_PERMANENT");//不可修改
		params.put("time_limit", "HOLIDAY");//不可修改
		
		String businessservice = params.get("business_service").toString();
		String[] businessSservice = businessservice.split(",");
		List<Object> business_service = new ArrayList<Object>();//不可修改
		for(int i =0 ; i<businessSservice.length ; i++){
			if(businessSservice[i].contains("BIZ_SERVICE_DELIVER")){
				business_service.add("BIZ_SERVICE_DELIVER");
			}else if(businessSservice[i].contains("BIZ_SERVICE_FREE_PARK")){
				business_service.add("BIZ_SERVICE_FREE_PARK");
			}else if(businessSservice[i].contains("BIZ_SERVICE_WITH_PET")){
				business_service.add("BIZ_SERVICE_WITH_PET");
			}else if(businessSservice[i].contains("BIZ_SERVICE_FREE_WIFI")){
				business_service.add("BIZ_SERVICE_FREE_WIFI");
			}
		}
		params.put("business_service", business_service);
		
		params.put("businessservice", businessservice);
		//后台设置默认值
		params.put("card_type", "MEMBER_CARD");
		params.put("get_limit", 1);
		params.put("can_give_friend", false);
		params.put("need_push_on_view", true);
		params.put("code_type", "CODE_TYPE_ONLY_QRCODE");
		params.put("supply_bonus", true);
		params.put("supply_balance",false);
		params.put("wx_activate", true);
		params.put("use_custom_code", false);
		params.put("bind_openid", false);
		params.put("can_share", true);
		Map<String, Object> date_info = new HashMap<String,Object>();
		date_info.put("type", params.get("type"));
		Map<String, Object> sku =new HashMap<String, Object>();
		sku.put("quantity", params.get("quantity"));
		params.put("date_info", JSONObject.fromObject(date_info));
		params.put("sku", JSONObject.fromObject(sku));
		params.put("createId", inputObject.getLogParams().get("id"));
		params.put("createTime", DateUtil.getTimeAndToString());
		String cartId = GetMembershipService.creatMembership(params);
		params.put("card_id", cartId);
		params.put("date_info", JSONObject.fromObject(date_info).toString());
		params.put("sku", JSONObject.fromObject(sku).toString());
		params.put("business_service", params.get("businessservice"));
		params.put("discount", disc.toString());
		wechatMembershipMapper.insertMembership(params);
		if(params.get("type").equals("DATE_TYPE_FIX_TIME_RANGE")){
			wechatMembershipMapper.updateMembership(params);
		}
	}

	/**
	 * 删除会员卡
	 */
	@Override
	public void deleteWechatMembership(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		if(!ToolUtil.contains(params,Constants.AMEMBERSHIP_KEY,Constants.AMEMBERSHIP_RETURNMESSAGE, inputObject, outputObject)){
			return ;
		}
		if(GetMembershipService.deleteMembership(params.get("card_id").toString())){
			wechatMembershipMapper.deleteMembership(params);
		}
	}

	/**
	 * 修改会员卡信息
	 */
	@Override
	public void updateWechatMembership(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		if(!ToolUtil.contains(params,Constants.UPDATEMEMBERSHIP_KEY,Constants.UPDATEMEMBERSHIP_RETURNMESSAGE, inputObject, outputObject)){
			return ;
		}
		//前台获得的值
		Double disc = Double.parseDouble(params.get("discount").toString());
		int discount = (int)(100-disc*10);
		params.put("discount", discount);
		if(params.get("use_all_locations").toString().equals("true")){
			params.put("use_all_locations",true);
		}else{
			params.put("use_all_locations",false);
		}
		if(params.containsKey("need_push_on_view")){
			if(params.get("need_push_on_view").toString().equals("true")){
				params.put("need_push_on_view", true);
			}else{
				params.put("need_push_on_view", false);
			}
		}
		GetMembershipService.updateMembership(params);
		params.put("discount", disc.toString());
		wechatMembershipMapper.updateMembership(params);
	}
    /**
     *创建会员卡通过审核
     *修改会员卡信息
     */
	@Override
	public void updateWechatMembershipMassage(Map<String, Object> map) throws Exception {
		wechatMembershipMapper.updateMembership(map);
	}

	/**
	 * 查看会员卡详情
	 */
	@Override
	public void selectWechatMembership(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> params = inputObject.getParams();
		if(!ToolUtil.contains(params,Constants.AMEMBERSHIP_KEY,Constants.AMEMBERSHIP_RETURNMESSAGE, inputObject, outputObject)){
			return ;
		}
		Map<String,Object> bean = wechatMembershipMapper.selectMembership(params);
		String businessservice = bean.get("business_service").toString();
		String[] business_service = businessservice.split(",");
		businessservice = "";
		bean.put("BIZ_SERVICE_DELIVER", false);
		bean.put("BIZ_SERVICE_FREE_PARK", false);
		bean.put("BIZ_SERVICE_WITH_PET", false);
		bean.put("BIZ_SERVICE_FREE_WIFI", false);
		for(int i =0 ; i<business_service.length ; i++){
			if(business_service[i].contains("BIZ_SERVICE_DELIVER")){
				businessservice += "外卖服务    ";
				bean.put("BIZ_SERVICE_DELIVER", true);
			}else if(business_service[i].contains("BIZ_SERVICE_FREE_PARK")){
				businessservice += "停车位    ";
				bean.put("BIZ_SERVICE_FREE_PARK", true);
			}else if(business_service[i].contains("BIZ_SERVICE_WITH_PET")){
				businessservice += "可带宠物    ";
				bean.put("BIZ_SERVICE_WITH_PET", true);
			}else if(business_service[i].contains("BIZ_SERVICE_FREE_WIFI")){
				businessservice += "免费wifi  ";
				bean.put("BIZ_SERVICE_FREE_WIFI", true);
			}
		}
		bean.put("business_service", businessservice);
		if(bean.get("type").toString().toString().equals("DATE_TYPE_PERMANENT")){
			bean.put("type", "永久有效");
		}
		if(bean.get("use_all_locations").toString().equals("1")){
			bean.put("use_all_locations", "是");
		}else{
			bean.put("use_all_locations", "否");
		}
		outputObject.setBean(bean);
	}

	/**
	 * 查看所有会员卡
	 */
	@Override
	public void selectWechatMemberships(InputObject inputObject, OutputObject outputObject) throws Exception {
		List<Map<String,Object>> beans = wechatMembershipMapper.selectMemberships();
		outputObject.setBeans(beans);
	}

	/**
	 * 检索所有在公众平台都放过的会员卡
	 */
	@Override
	public void checkWechatMemberships(InputObject inputObject,OutputObject outputObject) throws Exception {
		List<String> cardIdList = GetMembershipService.getAllMembership();
		for(String card : cardIdList){
			Map<String,Object> map = new HashMap<>();
			map.put("card_id", card);
			map = wechatMembershipMapper.selectMembership(map);
			if(map == null){//数据库中不存在
				Map<String,Object> bean = GetMembershipService.getusedMembership(card);
				String color = bean.get("color").toString();
				if(color.equals("#63B359")){
					color = "Color010";
				}else if(color.equals("#2C9f67")){
					color = "Color020";
				}else if(color.equals("#509FC9")){
					color = "Color030";
				}else if(color.equals("#5885CF")){
					color = "Color040";
				}else if(color.equals("#9062C0")){
					color = "Color050";
				}else if(color.equals("#D09A45")){
					color = "Color060";
				}else if(color.equals("#E4B138")){
					color = "Color070";
				}else if(color.equals("#EE903C")){
					color = "Color080";
				}else if(color.equals("#DD6549")){
					color = "Color090";
				}else{
					color = "Color100";
				}
				bean.put("color", color);
				if(bean.get("use_all_locations").toString().equals("true")){
					bean.put("use_all_locations", true);
				}else{
					bean.put("use_all_locations", false);
				}
				
				if(bean.get("can_give_friend").toString().equals("true")){
					bean.put("can_give_friend", true);
				}else{
					bean.put("can_give_friend", false);
				}
				
				if(bean.get("can_give_friend").toString().equals("true")){
					bean.put("can_give_friend", true);
				}else{
					bean.put("can_give_friend", false);
				}
				
				if(bean.get("card_type").toString().equals("MEMBER_CARD")){
					bean.put("createId", inputObject.getLogParams().get("id"));
					wechatMembershipMapper.insertMembership(bean);
					String card_url = CreateLandingpage.CarLandingpage(card);
					Map<String, Object> message = new HashMap<>();
					message.put("card_url", card_url);
					message.put("card_id", card);
					updateWechatMembershipMassage(message);
				}
				
			}
		}
		
	}

}
