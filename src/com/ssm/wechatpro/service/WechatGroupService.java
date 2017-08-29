package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatGroupService {

	public void insertWechatGroup(InputObject inputObject, OutputObject outputObject)throws Exception;
	
	public void selectWechatGroup(InputObject inputObject, OutputObject outputObject)throws Exception;
	
	public void selectOne(InputObject inputObject, OutputObject outputObject)throws Exception;
		
	public void delete(InputObject inputObject, OutputObject outputObject)throws Exception;
	
	public void updateById(InputObject inputObject, OutputObject outputObject)throws Exception;
}
