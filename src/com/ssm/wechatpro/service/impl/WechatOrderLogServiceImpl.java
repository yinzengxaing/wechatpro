package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatOrderLogMapper;
import com.ssm.wechatpro.dao.WechatOrderShoppingLogMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatOrderLogService;
import com.ssm.wechatpro.util.DateUtil;

@Service
public class WechatOrderLogServiceImpl implements WechatOrderLogService {

	@Resource
	private WechatOrderLogMapper wechatOrderLogMapper;
	
	@Resource
	private WechatOrderShoppingLogMapper wechatOrderShoppingLogMapper;
	
	String tableName = null; // 表示数据表名(订单表)
	String tableName1 = null;//表示产品信息表
	
	public WechatOrderLogServiceImpl() {
		tableName = "wechat_customer_order_log_" + DateUtil.getTimeSixAndToString();// 拼接数据表名
		tableName1 = "wechat_customer_order_shopping_log_"+DateUtil.getTimeSixAndToString();//拼接数据表
	}
	/**
	 * 当前用户创建订单(单个商品生成的订单)
	 * @param map
	 * @throws Exception
	 */
	public void insertOrderLog(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();// 获取参数	
		
		Map<String, Object> orderAdminIdMap = new HashMap<String, Object>();
		orderAdminIdMap.put("tableName", tableName);
		orderAdminIdMap.put("orderAdminId", map.get("orderAdminId"));
		Map<String, Object> orderNumber = wechatOrderLogMapper.selsctByShoppindId(orderAdminIdMap); // 通过商店id获得最新订单编号
		String goodsNum = null; // 表示每天接的第几单
		if(orderNumber == null) 
		{
			goodsNum = "0000"; // 表示某天还没有接单
		}else{ // 表示不是某一天的第一单
			goodsNum = (String) orderNumber.get("orderNumber"); // 得到的商品编号的后几位
			goodsNum = goodsNum.substring(goodsNum.length()-4, goodsNum.length());// 默认获取后四位
		}
		String nextgoodsNum = "0000" + (Integer.parseInt(goodsNum) + 1); // 自增1后的订单号
		String newNextGoodsNum = nextgoodsNum.subSequence(nextgoodsNum.length() - 4, nextgoodsNum.length()) + "";// 进行前导补0后的订单号
		int orderAdminId =  Integer.parseInt((map.get("orderAdminId").toString())); // 从map中获取餐厅id
		String preGoodsNum = orderAdminId + "0000" + DateUtil.getTimeSixAndToString(); // 获取订单前边的id
		
		map.put("tableName", tableName);
		map.put("orderNumber",preGoodsNum + newNextGoodsNum); // 新的商品编号
		map.put("createId", inputObject.getWechatLogParams().get("id"));  // 将创建人id传入map中
		map.put("createTime", DateUtil.getTimeAndToString());// 创建该条记录的时间
		wechatOrderLogMapper.insertOrderLog(map);// 将新数据插入到数据库中		
		
		//将订单中的产品信息存到数据库中
		map.put("tableName1", tableName1);
		map.put("orderId", map.get("id").toString());
		map.put("wetherDiscount", 0);
		map.put("wetherActivity", 0);
		wechatOrderShoppingLogMapper.insertProductInfo(map);
	}
	
	

	/**
	 * 显示当前用户的全部订单（按照未付款、已付款，订单编号递降），即查看历史记录
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void selectByUserId(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map.put("tableName",tableName);
		
		List<Map<String, Object>> list = wechatOrderLogMapper.selectByUserId(map);
		outputObject.setBeans(list);
	}

	/**
	 * 显示当前用户未付款的订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public void selectByWetherPayment(InputObject inputObject, OutputObject outputObject) throws Exception {
		
		Map<String, Object> map = inputObject.getParams();
//		Map<String, Object> map = new HashMap<String, Object>();
		
//		HttpSession session = InputObject.getRequest().getSession(); // 从session中获取用户的id
//		Object createId =  session.getAttribute("createId"); // 表示创建人 的id

//		map.put("createId", createId); // 将用户的id放在map集合中
		map.put("tableName", tableName);
		
		List<Map<String, Object>> nonPayment = wechatOrderLogMapper.selectByWetherPayment(map); // 获取未付款的订单，（根据订单编号递减输出）
		outputObject.setBeans(nonPayment);
	}	


	/**
	 * 支付订单（支付状态更改为1，更新支付时间）（通过创建人id和餐厅id）
	 * @param map
	 * @throws Exception
	 */
	public void modifyOrderLog(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map.put("tableName", tableName);		
		map.put("wetherPaymentTime", DateUtil.getTimeAndToString());
		wechatOrderLogMapper.modifyOrderLog(map); // 修改订单信息（添加付款时间、更改付款单状态）
	}

	/**
	 * 逻辑删除该订单(已经做完给顾客)（通过订单号）
	 * @param map
	 * @throws Exception
	 */
	public void deleteOrderLog(InputObject inputObject,OutputObject outputObject) throws Exception {
		
		// 测试
		Map<String, Object> map = inputObject.getParams(); // 获取订单编号
//		HttpSession session = InputObject.getRequest().getSession(); // 从session中获取用户的id
//		Object createId =  session.getAttribute("createId"); // 表示创建人 的id

//		int orderAdminId = 0 ;/*********如果此处商店id已经获取*************/
		
		Map<String, Object> orderAdminIdMap = new HashMap<String, Object>();
		
		orderAdminIdMap.put("tableName", tableName);
		// 测试
		orderAdminIdMap.put("orderNumber", map.get("orderNumber"));
		wechatOrderLogMapper.deleteOrderLog(orderAdminIdMap);
		System.out.println(map.get("orderNumber"));
		
	}
	
	/**
	 * 回显商品的名称和价格
	 * @param map
	 * @throws Exception
	 */
	@Override
	public void selectProduct(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = wechatOrderLogMapper.selectProduct(map);
		outputObject.setBean(bean);
	}
	
	/**
	 * 回显餐厅的名称
	 * @param map
	 * @throws Exception
	 */
	@Override
	public void selectShopName(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = wechatOrderLogMapper.selectShopName(map);
		outputObject.setBean(bean);
	}

}
