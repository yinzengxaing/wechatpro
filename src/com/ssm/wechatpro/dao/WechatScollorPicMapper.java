package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatScollorPicMapper {

	public int insertScoller(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectAllScollor(Map<String,Object> map, PageBounds pageBounds) throws Exception;
	
	public int deleteScollor(Map<String,Object> map) throws Exception;

	public Map<String,Object> selectById(Map<String,Object> map) throws Exception;
	
	public int updateScoller(Map<String,Object> map) throws Exception;
	
	/*
	public int updateFbScollor(Map<String,Object> map) throws Exception;
	
	public int updateQxFbScollor(Map<String,Object> map) throws Exception;
	
	public int updateSxScollor(Map<String,Object> map) throws Exception;
	
	public int updateXxScollor(Map<String,Object> map) throws Exception;
	
	public List<Map<String,Object>> selectFiveScollor(Map<String,Object> map) throws Exception;
	
	public Map<String,Object> selectScollorNum(Map<String,Object> map) throws Exception;
	
	public int updateScollorNum(Map<String,Object> map) throws Exception;
	*/
}
