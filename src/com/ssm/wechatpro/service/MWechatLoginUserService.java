package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface MWechatLoginUserService {

	public void insertPhoneLogin(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void getIdetifyCode(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void selectLoginPassword(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void LoginNoPassword(InputObject inputObject,OutputObject outputObject) throws Exception;
	
    public void updatePassword(InputObject inputObject,OutputObject outputObject) throws Exception;
    
    public void selectSession(InputObject inputObject, OutputObject outputObject) throws Exception;
    
    public void selectAll(InputObject inputObject, OutputObject outputObject) throws Exception;
    
    public void selectPhoneAndScore(InputObject inputObject, OutputObject outputObject) throws Exception;
}
