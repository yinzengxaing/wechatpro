package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatAdminMenuMapper {

	public int insertAdminMenu(Map<String,Object> map) throws Exception;

	public void deleteById(Map<String,Object> map) throws Exception;
	
	public void updateById(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectAll(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectAllMenuList(Map<String,Object> map, PageBounds pageBounds) throws Exception;
	
	public Map<String,Object> selectById(Map<String, Object> map) throws Exception;
	
	public List<Map<String,Object>> selectFirst() throws Exception;
	
	public List<Map<String,Object>> selectByBelongto(Map<String, Object> map) throws Exception;
	
	public List<Map<String,Object>> selectBybelong(Map<String, Object> map) throws Exception;
	//查询所有二级菜单的id和名称
	public List<Map<String,Object>> selectSecondMenu(Map<String,Object> map) throws Exception;
	
	//添加菜单所拥有的权限
	public int insertPower(Map<String,Object> map) throws Exception;
	
	//查询二级菜单所拥有的权限
	public List<Map<String,Object>> selectPower(Map<String,Object> map) throws Exception;
	
	//修改二级菜单所拥有的权限
	public int updatePower(Map<String,Object> map) throws Exception;
	
	public Map<String,Object> selectFirstAndSecond(Map<String,Object> map) throws Exception;
}
