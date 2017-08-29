package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

/**
 * 购物车所有相关的dao操作
 * @author yinzengxiang
 *
 */
public interface MWechatShoppingCartMapper {
	//向购物车中添加一个商品
	public void addProduct(Map<String, Object> map) throws Exception;
	//删除购物车中的一种商品
	public void deleteProduct(Map<String, Object> map ) throws Exception;
	//更改购物车中一个商品的数量
	public void updateProductCount(Map<String, Object> map) throws Exception;
	//获取该用户购物车中的商品信息（商品id  商品类型， 商品所在商店， 商品数量）
	public List<Map<String, Object>> getCartProductInfo(Map<String, Object> map) throws Exception;
	//根据用户id清除该 用户购物车中的所有信息
	public void deleteCartAllInfo (Map<String, Object> map) throws Exception;
	//获取该购物车中的商店
	public List<Map<String, Object>> getShopIds(Map<String, Object> map) throws Exception; 
	//商品数量增加一个
	public void productCountUp (Map<String, Object> map) throws Exception;
	//商品数量减少一个
	public void productCOuntDown (Map<String,Object> map) throws Exception;
	//根据商店id 获取商店的名称 和id
	public Map<String, Object> getShopName(Map<String, Object> map) throws Exception;
	/**
	 *获取商品的详细信息 
	 */
	//产品的详细信息
	public Map<String, Object> getProductByCartInfo(Map<String, Object> map) throws Exception;
	//套餐的详细信息
	public Map<String, Object> getPackageByCartInfo(Map<String, Object> map) throws Exception;
	//获取可选套餐的详细信息
	public Map<String, Object> getChooPackageByCartInfo(Map<String, Object> map) throws Exception;
	//查询当前登录人购物车中所有商品
	public List<Map<String,Object>> selectAllProduct(Map<String,Object> map) throws Exception;
 	
}
