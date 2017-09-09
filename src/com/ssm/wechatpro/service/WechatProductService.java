package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

/**
 * 处理商品的service
 * @author administrator
 *
 */
public interface WechatProductService {

	public void getProductList(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void getProductById(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void selectProductType(InputObject inputObject , OutputObject outputObject) throws Exception;
	
	public void addProduct(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateProduct(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void deleteProduct(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void getPackageByProductId(InputObject inputObject, OutputObject outputObject) throws Exception;
	//修改状态为提审
	public void updateStateSubmit(InputObject inputObject, OutputObject outputObject) throws Exception;
	//获取所有待审你和的商品
	public void getCheckPendingList(InputObject inputObject ,OutputObject outputObject) throws Exception;
	//审核通过一个商品
	public void updateStatePass(InputObject inputObject ,OutputObject outputObject) throws Exception;
	//审核不通过一个商品
	public void updateStateNoPass(InputObject inputObject, OutputObject outputObject) throws Exception;
	//获取商品的分类
	public void getProductTypeList(InputObject inputObject, OutputObject outputObject) throws Exception;
	
}
