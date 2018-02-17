package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

public interface WechatCityMapper {

	List<Map<String, Object>> queryCityAllByList(Map<String, Object> map,PageBounds pageBounds)throws Exception;

	List<Map<String, Object>> queryCityOneBySelect(Map<String, Object> map)throws Exception;

	List<Map<String, Object>> queryCityTwoByList(Map<String, Object> map)throws Exception;

	Map<String, Object> queryCityById(Map<String, Object> map)throws Exception;

	Map<String, Object> queryCityByType(Map<String, Object> map)throws Exception;

	void addCity(Map<String, Object> map)throws Exception;

	void deleteCityByParentId(Map<String, Object> map)throws Exception;

	void deleteCityById(Map<String, Object> map)throws Exception;

	void updateCityById(Map<String, Object> map)throws Exception;

	List<Map<String, Object>> queryAllCity()throws Exception;

}
