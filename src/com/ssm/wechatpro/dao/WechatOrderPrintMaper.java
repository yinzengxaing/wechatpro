package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;


public interface WechatOrderPrintMaper {

	/**
	 * 返回订单中的商品的基本信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectOrderInfo(Map<String, Object> map)throws Exception;
	
	/**
	 * 订单中含有的商品的信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> selectOrderPruduct(Map<String, Object> map) throws Exception;
	
	/**
	 * 查询指定时间到当前时间之间的信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectTimeQuantumOrderInfo(Map<String, Object> map) throws Exception; 
}
