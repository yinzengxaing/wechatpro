package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatProductPackageChooseService {

	/**
	 * 添加多选套餐
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	public void addPackage(InputObject inputObject, OutputObject outputObject) throws Exception;
	/**
	 * 显示所有套餐
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	public void selectAllPackage(InputObject inputObject, OutputObject outputObject) throws Exception;
	/**
	 * 查看详情
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	public void selectOne(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 删除活动套餐
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	public void deleteOne(InputObject inputObject, OutputObject outputObject) throws Exception;
	/**
	 * 修改活动套餐
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	public void updateOne(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 获取待审核状态的套餐信息 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void getCheckPendingListupdateOne(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	public void updatePackageStatePass(InputObject inputObject, OutputObject outputObject) throws Exception;
	public void updatePackageStateNotPass(InputObject inputObject, OutputObject outputObject) throws Exception;
	
	/**
	 * 提交审核
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateChoosePackageState(InputObject inputObject, OutputObject outputObject) throws Exception;
}
