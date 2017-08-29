package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

/**
 * 手机端商品展示的功能
 *
 * @author 殷曾祥
 * 2017-7-30  下午2:58:43
 */
public interface MWechatProductMapper {
	//获取该地区下的所有商店
	public List<Map<String, Object>> getAllRestaurant(Map<String, Object> map) throws Exception ;
	
	//获取该商店下的所有商品类型
	public List<Map<String, Object>> getAllType(Map<String, Object> map) throws Exception;
	
	//根据获得的类型 获取该类型中的商品的信息
	public List<Map<String, Object>> getProductListByType(Map<String,Object> map) throws Exception;
	
	//获取该商店下的所有的套餐信息
	public List<Map<String, Object>> getAllPackageList(Map<String, Object> map) throws Exception;
	
	//获取该商店下的所有的可选套餐的信息
	public List<Map<String, Object>> getAllChoosePackageList(Map<String, Object> map) throws Exception;
	
	//获取四个价格低的套餐
	public List<Map<String, Object>> getPackageByPrice(Map<String, Object> map) throws Exception;
	
	//获取商品在该月买的个数
	public Map<String, Object> getCountByMonth(Map<String, Object> map) throws Exception;
	
	//获取一个商品的详细信息
	public Map<String, Object> getProductDetail(Map<String, Object> map) throws Exception;
}
