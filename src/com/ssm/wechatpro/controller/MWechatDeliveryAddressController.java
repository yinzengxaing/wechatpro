package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatDeliveryAddressService;

@Controller
public class MWechatDeliveryAddressController {

	@Resource
	private MWechatDeliveryAddressService mWechatDeliveryAddressService;
	
	/**
	 * 添加地址
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatDeliveryAddressController/insertDeliveryAddress")
	@ResponseBody
	public void insertDeliveryAddress(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatDeliveryAddressService.insertDeliveryAddress(inputObject, outputObject);
	}
	
	/**
	 * 查询当前登录用户的所有收获地址信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatDeliveryAddressController/selectByDeliveryUserId")
	@ResponseBody
	public void selectByDeliveryUserId(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatDeliveryAddressService.selectByDeliveryUserId(inputObject, outputObject);
	}
	
	/**
	 * 根据id删除收货地址
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatDeliveryAddressController/deleteById")
	@ResponseBody
	public void deleteById(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatDeliveryAddressService.deleteById(inputObject, outputObject);
	}
	
	/**
	 * 编辑收货地址
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatDeliveryAddressController/updateAddress")
	@ResponseBody
	public void updateAddress(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatDeliveryAddressService.updateAddress(inputObject, outputObject);
	}
	
	/**
	 * 数据回显
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatDeliveryAddressController/selectById")
	@ResponseBody
	public void selectById(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatDeliveryAddressService.selectById(inputObject, outputObject);
	}
	
	/**
	 * 设置默认地址
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatDeliveryAddressController/updateUse")
	@ResponseBody
	public void updateUse(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatDeliveryAddressService.updateUse(inputObject, outputObject);
	}
	
	
	
}
