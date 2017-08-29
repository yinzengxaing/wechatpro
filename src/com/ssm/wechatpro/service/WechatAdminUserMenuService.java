package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatAdminUserMenuService {

	public void addUserMenu(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void selectMenuByUserId(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void deleteMenuByUserId(InputObject inputObject,OutputObject outputObject) throws Exception;
	
}
