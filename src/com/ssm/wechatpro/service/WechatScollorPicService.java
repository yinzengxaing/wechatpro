package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatScollorPicService {

	public void insertScoller(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectAllScollor(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void deleteScollor(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void selectById(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectAllScollorList(InputObject inputObject, OutputObject outputObject) throws Exception;
}
