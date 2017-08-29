package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

/**
 * 处理商品类型的service接口
 * @author administrator
 *
 */
public interface WechatProductTypeService {
	
	public void getProductTypeList(InputObject inputObject, OutputObject outputObject) throws Exception;  
	
	public void getProductTypeById(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void addProductType(InputObject inputObject , OutputObject outputObject) throws Exception;
	
	public void updateProductType(InputObject inputObject, OutputObject outputObject) throws Exception;
	//修改状态为提审
	public void updateStateSubmit(InputObject inputObject, OutputObject outputObject) throws Exception ;
	//删除一个商品类型
	public void deleteProductType(InputObject inputObject, OutputObject outputObject) throws Exception ;
	//获取所有已经上线的商品类型
	public void getTypeOnline(InputObject inputObject, OutputObject outputObject) throws Exception;
	//查询所有商品的种类，并按照id递增顺序排列
	public void selectProductType(InputObject inputObject, OutputObject outputObject) throws Exception ;
	//获取所有待审核状态的产品类别
	public void getCheckPendingList(InputObject inputObject ,OutputObject outputObject) throws Exception;
	//审核通过一个产品类别
	public void updateStatePass(InputObject inputObject ,OutputObject outputObject) throws Exception;
	//审核不通过一个产品类别
	public void updateStateNoPass(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	
	
}
