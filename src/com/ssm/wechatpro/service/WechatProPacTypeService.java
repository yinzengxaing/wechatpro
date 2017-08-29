package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatProPacTypeService {
	public void getPacTypeList(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void getPacTypeById(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void addPacType(InputObject inputObject,OutputObject outputObject)  throws Exception;
	
	public void deletePacType(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void updatePacType(InputObject inputObject,OutputObject outputObject) throws Exception;
}
