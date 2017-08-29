package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatProductPackageService {

	/**
	 * 显示所有套餐
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	public void selectAllPackage(InputObject inputObject, OutputObject outputObject) throws Exception;
	/**
	 * 根据id查找套餐中的商品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectPackageById(InputObject inputObject,	OutputObject outputObject) throws Exception;
	
	/**
	 * 添加一个套餐
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void insertPackage(InputObject inputObject, OutputObject outputObject) throws Exception;

	/**
	 * 查看提审状态的套餐
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectProductByState(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 *  更新套餐状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updatePackageState(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updatePackageStatePass(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updatePackageStateNotPass(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	
	/**
	 * 通过id删除套餐
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void deletePackageById(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 通过套餐ID查询套餐中所含有商品的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectPackageDetailInfoById(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 根据商品类别ID查询商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void selectProductByProductTypeId(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 更新套餐信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updatePackageInfo(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	
}
