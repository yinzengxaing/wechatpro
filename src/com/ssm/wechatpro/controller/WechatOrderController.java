package com.ssm.wechatpro.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	
	/**
	 * 接收退款的参数
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/WechatOrderController/orderRefund")
	@ResponseBody
	public void orderRefund(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatOrderService.orderRefund(inputObject, outputObject);
	}
	
	/**
	 * 异步回调改变订单状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/WechatOrderController/notifyPay")
	@ResponseBody
	public void notifyPay(HttpServletRequest request, HttpServletResponse response) throws Exception{
		wechatOrderService.notifyPay(request, response);
	}
	
	
}
