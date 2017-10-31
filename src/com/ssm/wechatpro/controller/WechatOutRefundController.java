package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatOutRefundService;

@Controller
public class WechatOutRefundController {

	@Resource
	private WechatOutRefundService wechatOutRefundService;
	
	
	/**
	 * 用户申请退单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/WechatOutRefundController/applyForRefund")
	@ResponseBody
	public void applyForRefund(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOutRefundService.applyForRefund(inputObject, outputObject);
	}
	
	/**
	 * 商家处理退单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatOutRefundController/dealWithRefund")
	@ResponseBody
	public void dealWithRefund(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOutRefundService.dealWithRefund(inputObject, outputObject);
	}
	
	/**
	 * 获取退单列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatOutRefundController/getRefundList")
	@ResponseBody
	public void getRefundList(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOutRefundService.getRefundList(inputObject, outputObject);
	}
}
