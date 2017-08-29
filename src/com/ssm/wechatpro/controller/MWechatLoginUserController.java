package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatLoginUserService;

@Controller
public class MWechatLoginUserController {

	@Resource
	private MWechatLoginUserService mWechatLoginUserService;
	
	/**
	 * 手机端获取验证码
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatLoginUserController/getIdetifyCode")
	@ResponseBody
	public void getIdetifyCode(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatLoginUserService.getIdetifyCode(inputObject, outputObject);
	}
	
	/**
	 * 手机端注册
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatLoginUserController/insertPhoneLogin")
	@ResponseBody
	public void insertPhoneLogin(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatLoginUserService.insertPhoneLogin(inputObject, outputObject);
	}
	
	/**
	 * 登录（免密登录）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatLoginUserController/LoginNoPassword")
	@ResponseBody
	public void LoginNoPassword(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatLoginUserService.LoginNoPassword(inputObject, outputObject);
	}
	
	/**
	 * 登录（账号密码登录）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatLoginUserController/selectLoginPassword")
	@ResponseBody
	public void selectLoginPassword(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatLoginUserService.selectLoginPassword(inputObject, outputObject);
	}
	
	/**
	 * 忘记密码
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatLoginUserController/updatePassword")
	@ResponseBody
	public void updatePassword(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatLoginUserService.updatePassword(inputObject, outputObject);
	}
	
	/**
	 * 获取登录信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatLoginUserController/selectSession")
	@ResponseBody
	public void selectSession(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatLoginUserService.selectSession(inputObject, outputObject);
	}
	
	/**
	 * 查询所有客户
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatLoginUserController/selectAll")
	@ResponseBody
	public void selectAll(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatLoginUserService.selectAll(inputObject, outputObject);
	}
	
	/**
	 * 回显登录页面
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/MWechatLoginUserController/selectPhoneAndScore")
	@ResponseBody
	public void selectPhoneAndScore(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatLoginUserService.selectPhoneAndScore(inputObject, outputObject);
	}
	
}
