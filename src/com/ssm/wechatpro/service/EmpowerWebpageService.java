package com.ssm.wechatpro.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface EmpowerWebpageService {
	
	public void getOpenidBycode(InputObject inputObject,OutputObject outputObject,HttpServletRequest request,HttpServletResponse response) throws Exception;
		
	public void updateSession(InputObject inputObject,OutputObject outputObject) throws Exception;
}
