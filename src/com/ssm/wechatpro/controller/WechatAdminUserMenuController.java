package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAdminUserMenuService;


@Controller
public class WechatAdminUserMenuController {
	
	@Resource
	private WechatAdminUserMenuService wechatAdminUserMenuService;
	
	/**
	 * 添加用户&菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminUserMenuController/addUserMenu")
	@ResponseBody
	public void addUserMenu(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatAdminUserMenuService.addUserMenu(inputObject, outputObject);
	}
	
	/**
	 * 根据用户id查询他所使用的菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminUserMenuController/selectMenuByUserId")
	@ResponseBody
	public void selectMenuByUserId(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatAdminUserMenuService.selectMenuByUserId(inputObject, outputObject);
	}
	
	/**
	 * 删除用户使用的菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminUserMenuController/deleteMenuByUserId")
	@ResponseBody
	public void deleteMenuByUserId(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatAdminUserMenuService.deleteMenuByUserId(inputObject, outputObject);
	}
	
	
	
	
	
}
