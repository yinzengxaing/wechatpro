package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatUrlService;


@Controller
public class WechatUrlController {
	@Resource
	private WechatUrlService wechatUrlService;
	
	/**
	 * 获取WeChat的url
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatUrlController/getWechatUrl")
	@ResponseBody
	public void getWechatUrl(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatUrlService.getUrlList(inputObject, outputObject);
	}
}
