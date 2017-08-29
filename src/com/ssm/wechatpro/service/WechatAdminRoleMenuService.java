package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;


public interface WechatAdminRoleMenuService {
	
	public void selectRoleByMenuId(InputObject inputObject,OutputObject outputObject) throws Exception;
	
}
