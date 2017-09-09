package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatOrderPrintMaper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatOrderPrintService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;

@Service
public class WechatOrderPrintServiceImpl implements WechatOrderPrintService{

	@Autowired
	WechatOrderPrintMaper wechatOrderPrintMaper;
	
	/**
	 * 返回订单中包含的信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void selectOrder(InputObject inputObject, OutputObject outputObject) throws Exception {
		// 获取该登陆人的信息
		// Map<String, Object> map = inputObject.getLogParams();
		// 获取订单的单号和id
		Map<String, Object> mapParam = inputObject.getParams();
		// 添加订单表名称
		mapParam.put("tableName", Constants.ORDER_TABLE + DateUtil.getTimeSixAndToString());
		// 商店id
		//	mapParam.put("orderAdminId", map.get("id")+"");
		// 商品的基本信息
		List<Map<String, Object>> mapInfo = wechatOrderPrintMaper.selectOrderInfo(mapParam);
		// 判断查询结果是否为空
		if(mapInfo.size() == 0){
			return ;
		}
		// 拼接订单详情表中的表明
		mapParam.put("tableName", Constants.SHOP_TABLE + DateUtil.getTimeSixAndToString());
		// 订单中包含的商品
		List<Map<String, Object>> productList = wechatOrderPrintMaper.selectOrderPruduct(mapParam);
		mapInfo.get(0).put("productSort", productList.size());
		mapInfo.get(0).put("productDetail", productList);
		outputObject.setBeans(mapInfo);
		outputObject.settotal(mapInfo.size());
	}

	/**
	 * 查询指定时间到当前时间之间的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectTimeQuantumOrderInfo(InputObject inputObject, OutputObject outputObject) throws Exception {
		// 获取当前登录人的信息
		// Map<String, Object> map = inputObject.getLogParams();
		// 获取传入参数信息
		Map<String, Object> mapParam = inputObject.getParams();
		// nowDay 20170827
		mapParam.put("nowDay", DateUtil.getTimeToString());
		// 获取当前日期(2016-12-29 11:23:09)
		mapParam.put("nowTime", DateUtil.getTimeAndToString());
		// 添加订单表名称
		mapParam.put("tableName", Constants.ORDER_TABLE + DateUtil.getTimeSixAndToString());
		// 商店id
		// mapParam.put("orderAdminId", map.get("id")+"");
		List<Map<String, Object>> listProductByTime = wechatOrderPrintMaper.selectTimeQuantumOrderInfo(mapParam);
		String tableName = Constants.SHOP_TABLE + DateUtil.getTimeSixAndToString();// 订单详情表的名称
		for(Map<String, Object> mapp: listProductByTime){
			Map<String, Object> mapDetailInfo = new HashMap<>(); // 
			mapDetailInfo.put("id", mapp.get("id"));
			mapDetailInfo.put("tableName", tableName);
			List<Map<String,Object>> productDetailInfo = wechatOrderPrintMaper.selectOrderPruduct(mapDetailInfo);
			mapp.put("productDetail", productDetailInfo);
			mapp.put("productSort", productDetailInfo.size());// 商品的种类数
		}
		outputObject.setBeans(listProductByTime);
		outputObject.settotal(listProductByTime.size());
		
	}

}
