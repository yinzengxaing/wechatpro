package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

public interface WechatAdminRoleMenuMapper {

	public int addUserMenu(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectMenuByRole(Map<String,Object> map) throws Exception;
	
	public int deleteMenu(Map<String,Object> map) throws Exception;
	
	public int deleteMenuByRoleId(Map<String,Object> map) throws Exception;
	
	public List<Map<String, Object>> selectRoleByMenuId(Map<String,Object> map) throws Exception;
} 
