package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

public interface WechatAdminUserMenuMapper {

	public void addUserMenu(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectMenuByUserId(Map<String,Object> map) throws Exception;
	
	public void deleteMenuByUserId(Map<String,Object> map) throws Exception;
	
	public int selectUserByMenuId(Map<String,Object> map) throws Exception;
	
	
	
}
