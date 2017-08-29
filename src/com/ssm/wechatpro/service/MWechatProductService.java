package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

/**
 * 手机端显示商品的service
 *
 * @author 殷曾祥
 * 2017-7-30  下午4:49:09
 */
public interface MWechatProductService {
	//获取当前地区的所有餐厅
	public void getAllRestaurant(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	//获取该餐厅中的所有的商品分类
	public void getAllType(InputObject inputObject ,OutputObject outputObject) throws Exception;
	
	//根据分类获取该分类下的所有商品
	public void getProductListByType (InputObject inputObject ,OutputObject outputObject) throws Exception;
	
	//获取该商店下的所有的套餐
	public void getAllPackageList(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	//获取该商店下的素偶有的可选套餐
	public void getAllChoosePackageList (InputObject inputObject, OutputObject outputObject) throws Exception;
	
	//获取首页显示的餐厅信息
	public void getIndexProductList(InputObject inputObject ,OutputObject outputObject)throws Exception;
	
	//获取一个商品的详情
	public void getProductDetail(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	//获取当前登录人购物车中当前商店的详情
	public void getCartDetail(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	
	
}
