package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatScollorPicService {

	public void insertScoller(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectAllScollor(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void deleteScollor(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void selectById(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	//public void updateScoller(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/*
	public void updateFbScollor(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateSxScollor(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateXxScollor(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateQxFbScollor(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectFiveScollor(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateScollorNum(InputObject inputObject, OutputObject outputObject) throws Exception;
	*/
}
