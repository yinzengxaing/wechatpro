package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatProPacTypeRestaurantService {
	//获取所有的分类列表
	public void getRestaurantList(InputObject inputObject, OutputObject outputObject) throws Exception;
	//获取所有的商品
	public void getProductList(InputObject inputObject,OutputObject outputObject) throws Exception;
	//修改一个分类下的商品
	public void updateRestaurant(InputObject inputObject,OutputObject outputObject)throws Exception;
	//查看一个商品分类信息
	public void  getRestaurantById(InputObject inputObject,OutputObject outputObject)throws Exception;
}
