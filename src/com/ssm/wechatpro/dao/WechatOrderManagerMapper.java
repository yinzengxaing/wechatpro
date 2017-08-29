package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatOrderManagerMapper {

	/**
	 * 查询该商店当天所有已经付款的但是没有给顾客的订单（按照流水号排序）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectPaiedOrderForm(Map<String, Object> map) throws Exception;
	public List<Map<String, Object>> selectPaiedOrderForm(Map<String, Object> map, PageBounds pageBounds) throws Exception;
	/**
	 * 根据订单的id和订单的单号查询订单的基本信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> selectOrderFormBasic (Map<String, Object> map) throws Exception;
	
	/**
	 * 查询该订单中包含的商品信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectOrderFormDetail(Map<String,Object> map) throws Exception;
	
	/**
	 * 将做好订单中的商品数量相加到已做好的表中
	 * @param map
	 * @throws Exception
	 */
	public void updateMakeAddNum(Map<String ,Object> map) throws Exception;
	
	/**
	 * 表示做好了给顾客
	 * @param map
	 * @throws Exception
	 */
	public void updateMake(Map<String, Object> map) throws Exception;
	
	/**
	 * 查看商品的数量和id 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selectProductTotal (Map<String, Object> map) throws Exception;
}
