package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

/**
 *封装关于订单的操作
 * @author yinzengxiang
 *
 */
public interface MWechatCustomerOrderMapper {
	//批量添加商品数据 (wechat_customer_order_shopping_log 中添加数据)
	public void addOrderItem(Map<String, Object> map) throws Exception;
	//添加一个订单 (wechat_customer_order_log 中添加数据)
	public void addOrder(Map<String, Object> map) throws Exception;
	//更改订单支付状态 0 未支付 1 已支付 默认为0 
	public void updatePayState(Map<String, Object> map) throws Exception;
	//更改是否给顾客做完并给顾客 0 ：未给  1 已给 默认 为0
	public void updateMakeState(Map<String, Object> map) throws Exception;
	//查看该用户下的所有订单（可查询 已支付（待接收） 未支付 所有订单）
	public List<Map<String, Object>> getAllOrder(Map<String, Object> map) throws Exception;
	//查询一个订单的详情
	public Map<String, Object> getOrderDetailByOrderId(Map<String, Object> map) throws Exception;
	//根据一个订单号查询一个订单的所有商品的信息
	public List<Map<String, Object>> getProductDetailByOrderId(Map<String, Object> map) throws Exception;
	//根据商店id查找到最新的一条数据获取其订单编号
	public Map<String, Object> selsctByShoppindId (Map<String, Object> map) throws Exception;
	// 通过商店id和当前日期查询最大日流水号
	public Map<String, Object> selectMaxDayNo(Map<String, Object> map) throws Exception;
	//查询一个商品的信息
	public Map<String, Object> getProductInfo(Map<String, Object> map) throws Exception;
	//查询商店的信息
	public Map<String, Object> getRestaurantInfo(Map<String, Object> map) throws Exception;
	//获取当前用户的默认地址
	public Map<String, Object> getAddress(Map<String, Object> map) throws Exception;
	//根据id 查询一个用户的地址
	public Map<String,Object> getAddressById(Map<String,Object> map) throws Exception;
	//根据订单id 删除该订单的所有订单项订单项
	public void deleteOrderItems(Map<String, Object> map) throws Exception;
	//根据底订单id 删除订单信息
	public void deleteOrder(Map<String, Object> map) throws Exception;
	
	// 删除当前用户在该商店中购物车中的商品
	public void deleteShopCartProduct(Map<String, Object> map ) throws Exception;
	
	//根据订单编号查询信息
	public Map<String, Object> checkOrderByNo(Map<String, Object> resultMap)throws Exception;
	
}
