package com.ssm.wechatpro.service;

import javax.servlet.ServletRequest;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface EmpowerWebpageService {
	
	public void getOpenidBycode(InputObject inputObject,OutputObject outputObject, ServletRequest request) throws Exception;
		
	public void updateSession(InputObject inputObject,OutputObject outputObject) throws Exception;
}
