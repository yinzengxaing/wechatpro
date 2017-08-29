package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatOrderLogService;

@Controller
public class WechatOrderLogController {

	@Resource
	private WechatOrderLogService wechatOrderLogService;
	
	/**
	 * 当前用户创建订单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/wechatOrderLogController/insertOrderLog")
	@ResponseBody
	public void insertOrderLog(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOrderLogService.insertOrderLog(inputObject, outputObject);
	}
	/**
	 * 显示当前用户的全部订单（按照未付款、已付款，订单编号递降），即查看历史记录
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/wechatOrderLogController/selectByUserId")
	@ResponseBody
	public void selectByUserId(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOrderLogService.selectByUserId(inputObject, outputObject);
	}

	/**
	 * 显示当前用户未付款的订单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/wechatOrderLogController/selectByWetherPayment")
	@ResponseBody
	public void selectByWetherPayment(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOrderLogService.selectByWetherPayment(inputObject, outputObject);
	}
	/**
	 * 支付订单（支付状态更改为1，更新支付时间）（通过创建人id和餐厅id）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/wechatOrderLogController/modifyOrderLog")
	@ResponseBody
	public void modifyOrderLog(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOrderLogService.modifyOrderLog(inputObject, outputObject);
	}
	/**
	 * 逻辑删除该订单(已经做完给顾客)（通过订单号）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/wechatOrderLogController/deleteOrderLog")
	@ResponseBody
	public void deleteOrderLog(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOrderLogService.deleteOrderLog(inputObject, outputObject);
	}
	
	/**
	 * 回显订单中商品的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/wechatOrderLogController/selectProduct")
	@ResponseBody
	public void selectProduct(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOrderLogService.selectProduct(inputObject, outputObject);
	}
	
	/**
	 * 查询餐厅的名称
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/wechatOrderLogController/selectShopName")
	@ResponseBody
	public void selectShopName(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOrderLogService.selectShopName(inputObject, outputObject);
	}
}
