package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatProductPackageDiscountService {

	
	/**
	 * 显示所有的打折套餐
	 * @return
	 * @throws Exception
	 */
	public void selectAllPackageDiscount (InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 添加新的打折表
	 * @param map
	 * @throws Exception
	 */
	public void insertPackageDiscount(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 修改打折表
	 * @param map
	 * @throws Exception
	 */
	public void modifyPackageDiscount(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 删除打折表
	 * @param map
	 * @throws Exception
	 */
	public void deletePackageDiscount(InputObject inputObject, OutputObject outputObject) throws Exception;
}
