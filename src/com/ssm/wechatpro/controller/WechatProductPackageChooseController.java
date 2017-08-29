package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductPackageChooseService;

@Controller
public class WechatProductPackageChooseController {

	@Resource
	private WechatProductPackageChooseService wechatProductPackageChooseServiceImpl;

	/**
	 * 添加可选套餐
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductPackageChooseController/addPackage")
	@ResponseBody
	public void addPackage(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageChooseServiceImpl.addPackage(inputObject, outputObject);
	}
	/**
	 * 显示所有套餐
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductPackageChooseController/selectAllPackage")
	@ResponseBody
	public void selectAllPackage(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageChooseServiceImpl.selectAllPackage(inputObject, outputObject);
	}
	/**
	 * 查看详情
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductPackageChooseController/selectOne")
	@ResponseBody
	public void selectOne(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageChooseServiceImpl.selectOne(inputObject, outputObject);
	}
	
	/**
	 * 删除活动套餐
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductPackageChooseController/delectOne")
	@ResponseBody
	public void deleteOne(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageChooseServiceImpl.deleteOne(inputObject, outputObject);
	}
	
	/**
	 * 修改活动套餐
	 * @param inputObject
	 * @param outputObject
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductPackageChooseController/updateOne")
	@ResponseBody
	public void updateOne(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageChooseServiceImpl.updateOne(inputObject, outputObject);
	}
	
	/**
	 * 查看待审核状态的可选套餐信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductPackageChooseController/getCheckPendingList")
	@ResponseBody
	public void getCheckPendingList(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageChooseServiceImpl.getCheckPendingListupdateOne(inputObject, outputObject);
	}
	
	/**
	 * 通过审核
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductPackageChooseController/updatePackageStatePass")
	@ResponseBody
	public void updatePackageStatePass(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductPackageChooseServiceImpl.updatePackageStatePass(inputObject, outputObject);
	}
	/**
	 * 未通过审核
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductPackageChooseController/updatePackageStateNotPass")
	@ResponseBody
	public void updatePackageStateNotPass(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductPackageChooseServiceImpl.updatePackageStateNotPass(inputObject, outputObject);
	}
	
	/**
	 * 提交审核
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductPackageChooseController/updateChoosePackageState")
	@ResponseBody
	public void updateChoosePackageState(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductPackageChooseServiceImpl.updateChoosePackageState(inputObject, outputObject);
	}
}
