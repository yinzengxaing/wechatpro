package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

/**
 * 订单列表
 * */

public interface WechatOrderLogMapper {

	/**
	 * 当前用户创建订单
	 * @param map
	 * @throws Exception
	 */
	public void insertOrderLog(Map<String, Object> map) throws Exception;

	/**
	 * 显示当前用户的全部订单（按照未付款、已付款，订单编号递降）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectByUserId(Map<String, Object> map)throws Exception;

	/**
	 * 显示当前用户未付款的订单
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectByWetherPayment(Map<String, Object> map) throws Exception;

	/**
	 * 根据商店id查找到最新的一条数据获取其订单编号
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selsctByShoppindId(Map<String, Object> map)throws Exception;
	
	/**
	 * 支付订单（支付状态更改为1，更新支付时间）（通过创建人id和餐厅id）
	 * 
	 * @param map
	 * @throws Exception
	 */
	public void modifyOrderLog(Map<String, Object> map) throws Exception;

	/**
	 * 逻辑删除该订单(已经做完给顾客)（通过创建人id和餐厅id）
	 * @param map
	 * @throws Exception
	 */
	public void deleteOrderLog(Map<String, Object> map) throws Exception;

	//查询商品的名称和价格
	public Map<String,Object> selectProduct(Map<String,Object> map) throws Exception;
	//回显餐厅名称
	public Map<String,Object> selectShopName(Map<String,Object> map) throws Exception;
}
