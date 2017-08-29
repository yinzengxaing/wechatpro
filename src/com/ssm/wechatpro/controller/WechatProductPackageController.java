package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductPackageService;

@Controller
public class WechatProductPackageController {

	@Resource
	private WechatProductPackageService wechatProductPackageService;

	/**
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */

	@RequestMapping("/post/wechatProductPackageController/selectAllPackage")
	@ResponseBody
	public void selectAllPackage(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageService.selectAllPackage(inputObject, outputObject);
	}

	/**
	 * 通过id查询套餐中的商品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageController/selectPackageById")
	@ResponseBody
	public void selectPackageById(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageService.selectPackageById(inputObject, outputObject);
	}
	/**
	 * 添加一条套餐表单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageController/insertPackage")
	@ResponseBody
	public void insertPackage(InputObject inputObject, OutputObject outputObject)throws Exception {
		wechatProductPackageService.insertPackage(inputObject, outputObject);
	}
	
	/**
	 * 查看所有提审状态的套餐
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageController/selectProductByState")
	@ResponseBody
	public void selectProductByState(InputObject inputObject, OutputObject outputObject)throws Exception {
		wechatProductPackageService.selectProductByState(inputObject, outputObject);
	}
	
	/**
	 * 修改套餐信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageController/updatePackageState")
	@ResponseBody
	public void updatePackageState(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductPackageService.updatePackageState(inputObject, outputObject);
	}
	
	@RequestMapping("/post/wechatProductPackageController/updatePackageStatePass")
	@ResponseBody
	public void updatePackageStatePass(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductPackageService.updatePackageStatePass(inputObject, outputObject);
	}
	
	@RequestMapping("/post/wechatProductPackageController/updatePackageStateNotPass")
	@ResponseBody
	public void updatePackageStateNotPass(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductPackageService.updatePackageStateNotPass(inputObject, outputObject);
	}
	
	
	
	/**
	 * 删除套餐信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageController/deletePackageById")
	@ResponseBody
	public void deletePackageById(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageService.deletePackageById(inputObject, outputObject);
	}
	
	/**
	 * 通过套餐中查询出套餐绑定产品ID,查看套餐的详细信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageController/selectPackageDetailInfoById")
	@ResponseBody
	public void selectPackageDetailInfoById(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductPackageService.selectPackageDetailInfoById(inputObject, outputObject);
	}
	
	
	/**
	 * 根据商品类别的id查询商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageController/selectProductByProductTypeId")
	@ResponseBody
	public void selectProductByProductTypeId(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductPackageService.selectProductByProductTypeId(inputObject, outputObject);
	}
	
	/**
	 * 更新套餐的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageController/updatePackageInfo")
	@ResponseBody
	public void updatePackageInfo(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductPackageService.updatePackageInfo(inputObject, outputObject);
	}
}
