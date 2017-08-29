package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatAdminRoleService {

	public void addRole(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void deleteById(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void selectAll(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void selectAllSx(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void selectById(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void updateById(InputObject inputObject,OutputObject outputObject) throws Exception;
   
	public void updateState(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void updateStatePass(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void updateStateNoPass(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void selectByState(InputObject inputObject,OutputObject outputObject) throws Exception;
}
