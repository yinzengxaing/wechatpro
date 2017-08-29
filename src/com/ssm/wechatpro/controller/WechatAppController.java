package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAppService;

@Controller
public class WechatAppController {
	
	@Resource
	private WechatAppService wechatAppService;

	/**
	 * 添加app信息
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatAppController/addWechatApp")
	@ResponseBody
	public void addWechatApp(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatAppService.addWechatApp(inputObject, outputObject);
	}

	/**
	 * 修改app信息
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatAppController/updateApp")
	@ResponseBody
	public void updateApp(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatAppService.updateApp(inputObject, outputObject);
	}

	/**
	 * 查询app信息
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatAppController/selectApp")
	@ResponseBody
	public void selectApp(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatAppService.selectApp(inputObject, outputObject);
	}

}
