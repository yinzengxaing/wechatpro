package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

/**
 * 购物车操作的service
 * @author yinzengxiang
 *
 */
public interface MWechatShoppingCartService {
	//添加一个商品到购物车中
	public void addProduct(InputObject inputObject,OutputObject outputObject) throws Exception;
	//删除一个在购物车中存在的商品
	public void deleteProductCount(InputObject inputObject, OutputObject outputObject) throws Exception;
	//删除一种商品到购物车中
	public void deleteProduct(InputObject inputObject,OutputObject outputObject) throws Exception;
	//更改商品的数量
	public void updateProductCount(InputObject inputObject, OutputObject outputObject) throws Exception;
	//查看购物车
	public void checkCart(InputObject inputObject,OutputObject outputObject) throws Exception;
	//删除购物车中的所有商品
	public void deleteCart(InputObject inputObject ,OutputObject outputObject) throws Exception;
}
