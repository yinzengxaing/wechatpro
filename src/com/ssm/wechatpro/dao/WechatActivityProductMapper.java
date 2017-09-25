package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

/**
 * 处理商品特价的mapper
 *
 * @author 殷曾祥
 * 2017-9-24  下午5:32:15
 */
public interface WechatActivityProductMapper {

	//创建一个商品特价的活动 返回主键id
	public void addProductActivity(Map<String, Object> map) throws Exception;
	
	//创建一个商品特价活动详情 返回主键id
	public void addProductActivityDetail(Map<String, Object> map) throws Exception;
	
	//添加商品特价活动中所属的商品
	public void addProductInActivity(Map<String, Object> map) throws Exception;
	
	//绑定活动与商店之间的关系
	public void addActivityRestaurant(Map<String, Object> map) throws Exception;
	
	//获取所有的商品列表 若优惠为特价优惠,找出所有高于该特价的商品
	public List<Map<String, Object>> getProductList(Map<String, Object> map) throws Exception;
	
	
}
