package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductPackageDiscountService;

@Controller
public class WechatProductPackageDiscountController {

	@Resource
	private WechatProductPackageDiscountService wechatProductPackageDiscountService;

	/**
	 * 显示所有套餐
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageDiscountController/selsctAllPackageDiscount")
	@ResponseBody
	public void selectAllPackageDiscount(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageDiscountService.selectAllPackageDiscount(inputObject, outputObject);
	}


	/**
	 * 添加新的打折表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageDiscountController/insertPackageDiscount")
	@ResponseBody
	public void insertPackageDiscount(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageDiscountService.insertPackageDiscount(inputObject, outputObject);
	}

	/**
	 * 修改打折表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageDiscountController/modifyPackageDiscount")
	@ResponseBody
	public void modifyPackageDiscount(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageDiscountService.modifyPackageDiscount(inputObject, outputObject);
	}

	/**
	 * 删除打折表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatProductPackageDiscountController/deletePackageDiscount")
	@ResponseBody
	public void deletePackageDiscount(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductPackageDiscountService.deletePackageDiscount(inputObject, outputObject);
	}
}
