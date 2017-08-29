/**
 * 
 */
package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatCustomerOrderService;

/**
 * 关于订单的操作的接口
 * @author 殷曾祥
 * 2017-7-22 下午9:28:16
 */
@Controller
public class MWechatCustomerOrderController {
	@Resource
	private MWechatCustomerOrderService mWechatCustomerOrderService;

	/**
	 * 添加一个订单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/addOrder")
	@ResponseBody
	public void addOrder(InputObject inputObject ,OutputObject outputObject) throws Exception{
		mWechatCustomerOrderService.addOrder(inputObject, outputObject);
	}
	
	/**
	 * 更改一个订单为支付状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/updatePayState")
	@ResponseBody
	public void updatePayState (InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatCustomerOrderService.updatePayState(inputObject, outputObject);
	}
	
	/**
	 * 更爱一个订单为已做完状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/updateMakeState")
	@ResponseBody
	public void updateMakeState (InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatCustomerOrderService.updateMakeState(inputObject, outputObject);
	}
	
	/**
	 * 查询该用户下的所有的订单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/getAllOrder")
	@ResponseBody
	public void getAllOrder(InputObject inputObject ,OutputObject outputObject) throws Exception{
		mWechatCustomerOrderService.getAllOrder(inputObject, outputObject);
	}
	
	/**
	 * 查看该用户下所有未支付状态的订单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/getAllOrderNotPaid")
	@ResponseBody
	public void getAllOrderNotPaid(InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatCustomerOrderService.getAllOrderNotPaid(inputObject, outputObject);
	}
	
	/**
	 * 根据订单编号显示该订单下的所有商品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/getProductDetailByOrderId")
	@ResponseBody
	public void getProductDetailByOrderId(InputObject inputObject, OutputObject outputObject)throws Exception{
		mWechatCustomerOrderService.getProductDetailByOrderId(inputObject, outputObject);
	}
	
	/**
	 * 获取一件商品的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/getProductInfo")
	@ResponseBody
	public void getProductInfo(InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatCustomerOrderService.getProductInfo(inputObject, outputObject);
	}
	
	/**
	 * 获得用户的默认地址
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/getAddress")
	@ResponseBody
	public void getAddress(InputObject inputObject ,OutputObject outputObject) throws Exception{
		mWechatCustomerOrderService.getAddress(inputObject, outputObject);
	}
	
	/**
	 *根据id获取一个收货地址 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/getAddressById")
	@ResponseBody
	public void getAddressById(InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatCustomerOrderService.getAddressById(inputObject, outputObject);
	}
	/**
	 * 删除一个订单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/deleteOrder")
	@ResponseBody
	public void deleteOrder(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatCustomerOrderService.deleteOrder(inputObject, outputObject);
	}
	
	/**
	 * 
	 * 查看订单详情
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/getOrderDetails")
	@ResponseBody
	public void getOrderDetails(InputObject inputObject, OutputObject outputObject)throws Exception{
		mWechatCustomerOrderService.getOrderDetails(inputObject, outputObject);
	}
	
	/**
	 * 
	 * 支付未完成的订单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatCustomerOrderController/payOrder")
	@ResponseBody
	public void payOrder(InputObject inputObject, OutputObject outputObject)throws Exception{
		mWechatCustomerOrderService.payOrder(inputObject, outputObject);
	}
}
