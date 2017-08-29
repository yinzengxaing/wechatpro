package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatPhoneMessageLogService;

@Controller
public class WechatPhoneMessageLogController {

	@Resource
	private WechatPhoneMessageLogService wechatPhoneMessageLogService;
    
    /**
     * 添加手机短信验证信息
     * @param inputObject
     * @param outputObject
     * @throws Exception
     */
    @RequestMapping("post/WechatPhoneMessageLogController/insertPhoneMessage")
    @ResponseBody
	public void getWord(InputObject inputObject,OutputObject outputObject) throws Exception{
    	wechatPhoneMessageLogService.insertPhoneMessage(inputObject, outputObject);
	}
    
    
}
