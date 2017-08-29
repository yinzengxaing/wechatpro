package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatOrderService;

@Controller
public class WechatOrderController {
	
	@Resource
	WechatOrderService wechatOrderService;
	
	/**
	 * 获取下单参数下单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/WechatOrderController/getOrderParameter")
	@ResponseBody
	public void getOrderParameter(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatOrderService.getOrderParameter(inputObject, outputObject);
	}
	
}
