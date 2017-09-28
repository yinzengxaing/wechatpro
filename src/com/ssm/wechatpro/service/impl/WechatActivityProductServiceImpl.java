package com.ssm.wechatpro.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatActivityProductMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatActivityProductService;

@Service
public class WechatActivityProductServiceImpl implements WechatActivityProductService {

	@Resource
	private WechatActivityProductMapper wechatActivityProductMapper;
	
	/**
	 * 添加一个商品活动
	 */
	@Override
	public void addProductActivity(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		
		//map1: 用于存放生成活动的参数
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("activityName", params.get("activityName"));
		map1.put("activityType", params.get("activityType"));
		map1.put("activityTimeType", params.get("activityTimeType"));
		map1.put("activityDetail", params.get("activityDetail"));
		//判断该活动是否为时间段内限制
		if (params.get("activityTimeType").toString().equals("1")){
			map1.put("activityStart", params.get("activityStart"));
			map1.put("activityEnd", params.get("activityEnd"));
		}
		//生成一个活动 并获取活动的id 存入map1中
		wechatActivityProductMapper.addProductActivity(map1);
		
		//map2: 用于存放生成的特价商品活动的详情信息 需要用到map1中 返回的活动id
		Map<String, Object> map2 = new HashMap<String,Object>();
		map2.put("activityId", map1.get("id"));
		map2.put("cycleType", params.get("cycleType"));
		map2.put("dayTimeType", params.get("dayTimeType"));
		map2.put("isCardDay", params.get("isCardDay"));
		map2.put("isLimit", params.get("isLimit"));
		//判断单循环周期是否为自定义
		if (params.get("cycleType").toString().equals("1")){
			map2.put("cycleWeek", params.get("cycleWeek"));
		}
		//判断活动是否为自定义时间段开始
		if(params.get("dayTimeType").toString().equals("1")){
			map2.put("dayTimeStart", params.get("dayTimeStart"));
			map2.put("dayTimeEnd", params.get("dayTimeEnd"));
		}
		//判断活动是否为卡友日 每月的指定日期才能使用
		if (params.get("isCardDay").toString().equals("1")){
			map2.put("cardDay", params.get("cardDay"));
		}
		//判断活动是否为每单限购
		if (params.get("isLimit").toString().equals("1")){
			map2.put("limitCount", params.get("limitCount"));
		}
		//判断优惠方式 0: 特价优惠 1 ： 折扣优惠
		if(params.get("discountsType").toString().equals("0")){
			map2.put("specialOffer", params.get("specialOffer"));
		}else if (params.get("discountsType").toString().equals("1")){
			map2.put("specialOffer", params.get("discount"));
		}
		wechatActivityProductMapper.addProductActivityDetail(map2);
		
		//1.分解前台传入的productList 字符串、   例如 ：特价商品id-特价商品价格,特价商品id-特价商品价格,
		String[] productList = params.get("productList").toString().split(",");
		//2.循环遍历所有商品      map3: 添加活动的商品 从map2 中获取特价商品活动的详情，
		for (String product : productList) {
			String[] split = product.split("-");
			Map<String, Object> map3 = new HashMap<>();
			map3.put("productDetailId", map2.get("id"));
			map3.put("productId", split[0]);
			map3.put("productPrice", split[1]);
			wechatActivityProductMapper.addProductInActivity(map3);
		}
		
		//map4: 存放参与活动的商店id 以及从map1 中获取到的活动id
		//1. 分解前台传入的restaurantList字符串、 例如 ： 商店id,商店id,商店id
		String[] restaurantList = params.get("restaurantList").toString().split(",");
		for (String string : restaurantList) {
			//map4 :存放参与活动的商店id 和活动id
			Map<String, Object> map4 = new HashMap<>();
			map4.put("activityId", map1.get("id"));
			map4.put("restaurantId", string);
			wechatActivityProductMapper.addActivityRestaurant(map4);
		}	
	}

	/**
	 * 获取商品列表
	 */
	@Override
	public void getProductList(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		//判断需要获取商品列表的种类 若为优惠商品 那么 以获取优惠价格以上的所有商品 若以折扣为标准 则获取所有高于优惠价格的商品列表
		Map<String, Object> map = new HashMap<>();
		if (params.get("discountsType").toString().equals("0")){ //价格优惠
			map.put("specialOffer", params.get("specialOffer"));
		}
		//查询商品
		List<Map<String,Object>> productList = wechatActivityProductMapper.getProductList(map);
		
		//获取分类
		HashSet<Object> set = new HashSet<>();
		for (Map<String, Object> map2 : productList) {
			set.add(map2.get("typeName"));
		}
		
		//创建返回的list
		List<Map<String, Object>> returnList = new ArrayList<>();
		
		//获取分类中的商品
		for (Object object : set) {
			Map<String, Object> retrunMap = new HashMap<>();
			retrunMap.put("typrName", object);
			List<Map<String, Object>> list = new ArrayList<>();
			for (Map<String, Object> map2 : productList) {
				if (map2.get("typeName").equals(object)){
					list.add(map2);
				}
			}
			retrunMap.put("list", list);
			returnList.add(retrunMap);
		}
		outputObject.setBeans(returnList);
		outputObject.settotal(returnList.size());
	}
	
}
