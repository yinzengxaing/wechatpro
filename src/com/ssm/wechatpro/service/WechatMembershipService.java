package com.ssm.wechatpro.service;

import java.util.Map;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatMembershipService {
	
	public void createWechatMembership(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void deleteWechatMembership(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateWechatMembership(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateWechatMembershipMassage(Map<String,Object> map) throws Exception;
	
	public void selectWechatMembership(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void selectWechatMemberships(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void checkWechatMemberships(InputObject inputObject,OutputObject outputObject) throws Exception;
}
