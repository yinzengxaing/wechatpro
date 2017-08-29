package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

/**
 * 手机获取所有的商品的信息
 * @author yinzengxiang
 *
 */
public interface MWechatProductsMapper {
	
	/**
	 *以方法是若是所查询的地区拥有商店
	 */
	//根据商店位置 获取该位置下的商店id以及名称
	public List<Map<String, Object>> getRestaurantInfo(Map<String, Object> map) throws Exception;
	//根据商店id获取该商店的所有--下所有的产品（按照分类排）
	public List<Map<String, Object>> getProductByResId(Map<String, Object> map) throws Exception;
	//根据商店id 获取该商店下所有的套餐 (按照时间排序)
	public List<Map<String, Object>> getPackByResId(List<Map<String, Object>> lsit) throws Exception;
	//根据商店id 获取该商店下的所有可选套餐
	public List<Map<String, Object>> getChooByResId(List<Map<String, Object>> list) throws Exception;
	//获取该商店下的所有商品的分类
	public List<Map<String, Object>> getTypeList(List<Map<String, Object>> lsit) throws Exception ;
	//获取4个价格最低的可选套餐作为新品必选
	public List<Map<String, Object>> getChooListByPrice(List<Map<String, Object>> list) throws Exception;
	
	/**
	 * 以下方法为所查询的地区没有商店
	 */
	//获取所有的商品类型
	public List<Map<String, Object>> getAllType(Map<String, Object> map) throws Exception;
	//根据商品类型获取商品
	public List<Map<String, Object>> getAllProductByType(Map<String, Object> map) throws Exception;
	//获取所有的套餐
	public List<Map<String, Object>> getAllPackage(Map<String, Object> map) throws Exception;
	//获取所有的可选套餐
	public List<Map<String, Object>> getAllChooPackage(Map<String, Object> map) throws Exception;
	//根据时间获取价格最新的四个可选套餐
	public List<Map<String, Object>> getAllChooByPrice(Map<String, Object> map) throws Exception;

	
	}
