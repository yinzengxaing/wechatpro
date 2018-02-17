package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

public interface WechatMapMapper {

	List<Map<String, Object>> queryAllCityByABC(Map<String, Object> map)throws Exception;

}
