package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface EmpowerWebpageService {
	
	public void getOpenidBycode(InputObject inputObject,OutputObject outputObject) throws Exception;
		
	public void updateSession(InputObject inputObject,OutputObject outputObject) throws Exception;
}
