package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatUserMapper {
	//查询所有用户信息
	public List<Map<String,Object>> selectWechatUser(Map<String,Object> map, PageBounds pageBounds) throws Exception;

	//查询用户订阅情况
	public Map<String,Object> selectWechatSubscribe(Map<String,Object> map) throws Exception;
	
	//添加用户信息
	public int insertWechatUser(Map<String,Object> map) throws Exception;
	
	//修改用户关注情况
	public int updateWechatSubscribe(Map<String, Object> map) throws Exception;
	
	//修改wechatUser地理位置信息
	public int updateWechatUserLoaction(Map<String,Object> map) throws Exception;
	
	//更新用户
	public int insertAllWechatUser(List<Map<String,Object>> beans) throws Exception;
	
	//查询所有已存储用户的openid
	public List<Map<String,Object>> selectWechatOpenid() throws Exception;
	
	//根据openid获取经纬度
	public Map<String,Object> selectLatitudeAndLongtitude(Map<String,Object> map) throws Exception;

	//根据opendid获取用户信息
	public Map<String, Object> selectWechatUserByOpenId(Map<String, Object> bean) throws Exception;
	
	//修改用户信息
	public void updateWechatUser(Map<String,Object> map) throws Exception;
	
	// 添加新的登陆用户
	public void insertMWechatLoginUser(Map<String,Object> map) throws Exception;

	public Map<String, Object> selectLation(Map<String, Object> map)throws Exception;
	
}
