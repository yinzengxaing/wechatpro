package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatProductRestaurantService {

	public void insertProduct(InputObject inputObject,OutputObject outputObject) throws Exception;
	//获取该门店下的商品
	public void getProduct(InputObject inputObject, OutputObject outputObject) throws Exception;
	//系统定时修改商品状态
	public void updateState() throws Exception;
	//系统定时创建月表
	public void insertTable() throws Exception;
	//系统定时清空数据库
	public void deleteAllCart() throws Exception;
}
