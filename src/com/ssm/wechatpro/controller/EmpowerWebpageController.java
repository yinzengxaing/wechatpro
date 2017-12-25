package com.ssm.wechatpro.controller;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.EmpowerWebpageService;

@Controller
public class EmpowerWebpageController {
	
	@Resource
	private EmpowerWebpageService empowerWebpageService;
	
	/**
	 * 通过code获取openid
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/EmpowerWebpageController/getOpenidBycode")
	@ResponseBody
	public void getOpenidBycode(InputObject inputObject,OutputObject outputObject, ServletRequest request) throws Exception{
		empowerWebpageService.getOpenidBycode(inputObject, outputObject,request);
	}
	
	/**
	 * 更新session中的城市
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/EmpowerWebpageController/updateSession")
	@ResponseBody
	public void updateSession(InputObject inputObject,OutputObject outputObject ) throws Exception{
		empowerWebpageService.updateSession(inputObject, outputObject);
	}

}
