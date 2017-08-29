package com.ssm.wechatpro.dao;

import java.util.List;
import java.util.Map;

public interface WechatProductPackageDiscountMapper {
	// 打折表
	
	/**
	 * 显示所有套餐
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> selsctAllPackageDiscount () throws Exception;
	
	/**
	 * 添加新的打折表
	 * @param map
	 * @throws Exception
	 */
	public void insertPackageDiscount(Map<String, Object> map) throws Exception;
	
	/**
	 * 修改打折表
	 * @param map
	 * @throws Exception
	 */
	public void modifyPackageDiscount(Map<String, Object> map) throws Exception;
	
	/**
	 * 删除打折表
	 * @param map
	 * @throws Exception
	 */
	public void deletePackageDiscount(Map<String, Object> map) throws Exception;
}
