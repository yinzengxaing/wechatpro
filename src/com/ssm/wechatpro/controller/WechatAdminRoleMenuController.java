package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAdminRoleMenuService;

@Controller
public class WechatAdminRoleMenuController {

	@Resource
	private WechatAdminRoleMenuService wechatAdminRoleMenuService;
	
	@RequestMapping("post/WechatAdminRoleMenuController/selectRoleByMenuId")
	@ResponseBody
	public void selectRoleByMenuId(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminRoleMenuService.selectRoleByMenuId(inputObject, outputObject);
	}
	
}
