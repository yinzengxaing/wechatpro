package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

public interface WechatAdminRoleMapper {

	public int addRole(Map<String,Object> map) throws Exception;
	
	public int deleteById(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectAll(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectAllSx() throws Exception;
	
	public Map<String,Object> selectById(Map<String,Object> map) throws Exception;
	
	public int updateById(Map<String,Object> map) throws Exception;

	public int updateState(Map<String,Object> map) throws Exception;
	
	public int updateStatePass(Map<String,Object> map) throws Exception;
	
	public int updateStateNoPass(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectByState(Map<String,Object> map) throws Exception;
}