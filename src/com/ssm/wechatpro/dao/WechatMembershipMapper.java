package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

public interface WechatMembershipMapper {
	
	//查询所有会员卡
	public List<Map<String,Object>> selectMemberships() throws Exception;
	
	//查看会员卡详情
	public Map<String,Object> selectMembership(Map<String,Object> map) throws Exception;
	
	//添加会员卡
	public void insertMembership(Map<String,Object> map) throws Exception;
	
	//修改会员卡
	public void updateMembership(Map<String,Object> map) throws Exception;
	
	//删除会员卡
	public void deleteMembership(Map<String,Object> map) throws Exception;
}
