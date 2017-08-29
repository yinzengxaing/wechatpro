package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

public interface WechatShoppingCartMapper {

	// 根据购物车所属人id显示该用户购物车中的信息
	public List<Map<String, Object>> selectAllShoppingCart(Map<String, Object> map);

	// 向购物车中添加商品
	public void insertShoppingCart(Map<String, Object> map);

	// 删除购物车中的信息(通过所属人id和商品id)
	public void deleteShopptinCartByCustomerLoginIdAndCommodity(Map<String, Object> map);
}
