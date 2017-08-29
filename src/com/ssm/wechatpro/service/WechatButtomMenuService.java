package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
/**
 * 自定义菜单的service接口
 * @author administrator
 *
 */
public interface WechatButtomMenuService {
	public void deleteMenus(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectAllMenus(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectMenuByVersion(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updateMenuPublish(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void selectMenuById(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updatemenuById(InputObject inputObject, OutputObject outputObject) throws Exception;

	public void addMenu(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void getmenuVersion(InputObject inputObject, OutputObject outputObject) throws Exception;
}
