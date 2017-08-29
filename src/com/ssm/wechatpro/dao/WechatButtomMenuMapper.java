package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatButtomMenuMapper {
	
	//根据id删除该菜单
	public void deleteButtomById(Map<String,Object> map) throws Exception;
	
	//根据菜单父ID删除该菜单
	public void deleteButtomBymenuBelong(Map<String,Object> map) throws Exception;
	
	//查询所有的自定义菜单
	public List<Map<String,Object>> selectAllMenuList(Map<String,Object> map, PageBounds pageBounds) throws Exception;
	
	//根据Version查询菜单项
	public List<Map<String, Object>> selectMenuByVersion(Map<String,Object> map) throws Exception;
	
	//修改Publish
	public void updatewetherPublish() throws Exception;
	//根据id查询菜单项 
	public Map<String, Object> selectMenuById(Map<String, Object> map) throws Exception;
	
	//查询一级菜单的子菜单 
	public List<Map<String,Object>> selectMenuByBelong(Map<String,Object> map) throws Exception;
	
	//添加自定义菜单
	public void addMenu(Map<String, Object> map) throws Exception;
	
	//根据id修改菜单项 
	public void updateMenu(Map<String, Object> map) throws Exception;
	
	//修改发布状态
	public  void updatePublish(Map<String, Object> map) throws Exception;

	public List<Map<String, Object>> SelectAllByLevel(Map<String, Object> map) throws Exception;
	
}
