package com.ssm.wechatpro.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatUserService;

@Controller
public class WechatUserController {
	@Resource
	private WechatUserService wechatUserService;
	
	/**
	 * 查找所有用户信息
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatUserController/selectAllWechatUser")
	@ResponseBody
	public void selectAllWechatUser(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatUserService.selectAllWechatUser(inputObject, outputObject);
	}
	
	/**
	 * 检查所有用户信息
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatUserController/checkWechatUser")
	@ResponseBody
	public void checkWechatUser(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatUserService.insertWechatUser(inputObject, outputObject);
	}
	
	/**
	 * 获取微信用户信息
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/WechatUserController/selectLatitudeAndLongtitude")
	@ResponseBody
	public void selectLatitudeAndLongtitude(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatUserService.selectLatitudeAndLongtitude(inputObject, outputObject);
	}
	
	
}
