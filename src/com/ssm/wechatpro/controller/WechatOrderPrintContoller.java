package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatOrderPrintService;

@Controller
public class WechatOrderPrintContoller {
	
	@Resource
	WechatOrderPrintService wechatOrderPrintService;
	
	@RequestMapping("/post/wechatOrderPrintContoller/selectOrder")
	@ResponseBody
	public void selectOrder(InputObject inputObject, OutputObject outputObject)throws Exception{
		wechatOrderPrintService.selectOrder(inputObject, outputObject);
	}
	
	/**
	 * 查询指定时间到当前时间的订单信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatOrderPrintContoller/selectTimeQuantumOrderInfo")
	@ResponseBody
	public void selectTimeQuantumOrderInfo(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatOrderPrintService.selectTimeQuantumOrderInfo(inputObject, outputObject);
	}
}
