package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

public interface WechatAppMapper {
	// 查询公众号信息
	public List<Map<String, Object>> getApp() throws Exception;

	// 添加公众号信息
	public void addApp(Map<String, Object> app) throws Exception;

	// 修改公众号信息
	public void updateApp(Map<String, Object> app) throws Exception;

}
