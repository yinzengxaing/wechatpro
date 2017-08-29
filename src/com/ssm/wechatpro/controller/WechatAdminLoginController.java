package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAdminLoginService;

@Controller
public class WechatAdminLoginController {

	@Resource
	private WechatAdminLoginService wechatAdminLoginService;
	
	
	
	/**
	 * 注册
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/insertAdminLogin")
	@ResponseBody
	public void insertAdminLogin(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.insertAdminLogin(inputObject, outputObject);
	}
	
	/**
	 * 添加用户
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/insertUser")
	@ResponseBody
	public void insertUser(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.insertUser(inputObject, outputObject);
	}
	
	/**
	 * 删除用户
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/deleteById")
	@ResponseBody
	public void deleteById(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.deleteById(inputObject, outputObject);
	}
	
	/**
	 * 更新用户密码
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/updatePassword")
	@ResponseBody
	public void updatePassword(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.updatePassword(inputObject, outputObject);
	}
	
	/**
	 * 身份认证
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/updateById")
	@ResponseBody
	public void updateById(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.updateById(inputObject, outputObject);
	}
	
	/**
	 * 查询所有用户信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/selectAll")
	@ResponseBody
	public void selectAll(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.selectAll(inputObject, outputObject);
	}
	
	/**
	 * 用于数据回显
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/selectById")
	@ResponseBody
	public void selectById(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.selectById(inputObject, outputObject);
	}
	
	/**
	 * 用于身份认证回显
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/selectRz")
	@ResponseBody
	public void selectRz(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.selectRz(inputObject, outputObject);
	}
	/**
	 * 登录
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/insertLogin")
	@ResponseBody
	public void insertLogin(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.insertLogin(inputObject, outputObject);
	}
	
	/**
	 * 获取登录信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/selectSession")
	@ResponseBody
	public void selectSession(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.selectSession(inputObject, outputObject);
	}
	
	/**
	 * 清空session
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/clearSession")
	@ResponseBody
	public void clearSession(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.clearSession(inputObject, outputObject);
	}
	
	/**
	 * 查看手机号是否被注册过
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/selectAdminNo")
	@ResponseBody
	public void selectAdminNo(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.selectAdminNo(inputObject, outputObject);
	}
	
	/**
	 * 所有正在认证中的用户
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/selectByAdminIdentity")
	@ResponseBody
	public void selectByAdminIdentity(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.selectByAdminIdentity(inputObject, outputObject);
	}
	
	/**
	 * 认证未通过
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/updateUserNoPass")
	@ResponseBody
	public void updateUserNoPass(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.updateUserNoPass(inputObject, outputObject);
	}
	
	/**
	 * 认证通过（餐厅人员认证）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/updateCtUserPass")
	@ResponseBody
	public void updateCtUserPass(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.updateCtUserPass(inputObject, outputObject);
	}
	
	/**
	 * 认证通过（管理人员认证）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/updateGlUserPass")
	@ResponseBody
	public void updateGlUserPass(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.updateGlUserPass(inputObject, outputObject);
	}
	
	/**
	 * 查询用户所属角色下的所有菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/selectByUser")
	@ResponseBody
	public void selectByUser(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.selectByUser(inputObject, outputObject);
	}
	
	/**
	 * 显示所有餐厅信息和餐厅人员信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/selectShop")
	@ResponseBody
	public void selectShop(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.selectShop(inputObject, outputObject);
	}
	
	/**
	 *显示餐厅详情
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/selectShopXQ")
	@ResponseBody
	public void selectShopXQ(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.selectShopXQ(inputObject, outputObject);
	}
	
	/**
	 *编辑餐厅详情信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/wechatAdminLoginController/updateShopXQ")
	@ResponseBody
	public void updateShopXQ(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminLoginService.updateShopXQ(inputObject, outputObject);
	}
}
