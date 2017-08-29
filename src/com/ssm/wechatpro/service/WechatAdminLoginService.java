package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatAdminLoginService {
 
	public void insertAdminLogin(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void deleteById(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updatePassword(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateById(InputObject inputObject, OutputObject outputObject) throws Exception; 
	
	public void selectAll(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectByAdminIdentity(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectById(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectAdminNo(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void insertLogin(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void selectSession(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void clearSession(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectRz(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateUserNoPass(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateCtUserPass(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateGlUserPass(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectByUser(InputObject inputObject, OutputObject outputObject) throws Exception;
   
	public void insertUser(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void selectShop(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void selectShopXQ(InputObject inputObject,OutputObject outputObject) throws Exception;
	
	public void updateShopXQ(InputObject inputObject,OutputObject outputObject) throws Exception;
	
}
