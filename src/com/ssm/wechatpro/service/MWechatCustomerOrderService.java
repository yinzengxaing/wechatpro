package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

/**
 * 订单的相关操作的service
 * @author yinzengxiang
 *
 */
public interface MWechatCustomerOrderService {
	//新增一个订单
	public void addOrder(InputObject inputObject, OutputObject outputObject)throws Exception;
	//更改一个订单的支付状态
	public void updatePayState(InputObject inputObject, OutputObject outputObject) throws Exception;
	//更改一个订单是否做完并且给顾客的状态
	public void updateMakeState(InputObject inputObject,OutputObject outputObject)throws Exception;
	//插叙该用户下的所有订单
	public void getAllOrder(InputObject inputObject, OutputObject outputObject) throws Exception;
	//查询该用户下的待付款的订单
	public void getAllOrderNotPaid(InputObject inputObject, OutputObject outputObject) throws Exception;
	//根据订单编号查询该订单中商品的详细信息
	public void getProductDetailByOrderId(InputObject inputObject, OutputObject outputObject) throws Exception;
	//查询一个商品的信息
	public void getProductInfo (InputObject inputObject,OutputObject outputObject) throws Exception;
	//查询当前用户的默认地址
	public void getAddress(InputObject inputObject, OutputObject outputObject) throws Exception;
	//根据id查询一个地址
	public void getAddressById(InputObject inputObject,OutputObject outputObject) throws Exception;
	//删除一个订单
	public void deleteOrder(InputObject inputObject,OutputObject outputObject) throws Exception;
	//查看一个订单详情
	public void getOrderDetails(InputObject inputObject, OutputObject outputObject) throws Exception;
	//未付款订单支付
	public void payOrder(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	
}
