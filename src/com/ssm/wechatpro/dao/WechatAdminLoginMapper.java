package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatAdminLoginMapper {

	public int insertAdminLogin(Map<String,Object> map) throws Exception;
	
	public void deleteById(Map<String,Object> map) throws Exception;
	
	public int updatePassword(Map<String,Object> map) throws Exception;
	
	public int updateById(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectAll(Map<String,Object> map,PageBounds pageBounds) throws Exception;
	
	public Map<String,Object> selectById(Map<String,Object> map) throws Exception;
	
	public int selectByNo(Map<String,Object> map) throws Exception;
	
	public Map<String,Object> selectAdminNo(Map<String,Object> map) throws Exception;
	
	public Map<String, Object> selectUserPassword(Map<String,Object> map) throws Exception;
	
	public int selectByRole(Map<String,Object> map) throws Exception;

	public List<Map<String,Object>> selectByAdminIdentity(Map<String,Object> map,PageBounds pageBounds) throws Exception;
	
	public int updateUserNoPass(Map<String,Object> map) throws Exception;
	
	public int updateCtUserPass(Map<String,Object> map) throws Exception;
	
	public int updateGlUserPass(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectByUser(Map<String,Object> map) throws Exception;
	
	//显示餐厅和餐厅人员信息
	public List<Map<String,Object>> selectShop(Map<String,Object> map, PageBounds pageBounds) throws Exception;
	
	//查看餐厅详情
	public Map<String,Object> selectShopXQ(Map<String,Object> map) throws Exception;
	
	//编辑餐厅详情信息
	public int updateShopXQ(Map<String,Object> map) throws Exception;
	
	//查询当前登录用户所属角色下的权限
	public List<Map<String,Object>> selectPowerByRole(Map<String,Object> map) throws Exception;
}
