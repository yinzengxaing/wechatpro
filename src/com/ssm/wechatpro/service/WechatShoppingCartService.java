package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatShoppingCartService {

	/**
	 * 根据购物车所属人id显示该用户购物车中的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectAllShoppingCart(InputObject inputObject,OutputObject outputObject) throws Exception;

	/***
	 * 向购物车中添加商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void insertShoppingCart(InputObject inputObject,OutputObject outputObject) throws Exception;

	/**
	 * 删除购物车中的信息(通过所属人id和商品id)
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void deleteShopptinCartByCustomerLoginIdAndCommodity(InputObject inputObject, OutputObject outputObject)throws Exception;
}
