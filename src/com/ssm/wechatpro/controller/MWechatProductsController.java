package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatProductsService;

@Controller
/**
 * 手机端获取商品的controller
 * @author yinzengxiang
 *
 */
public class MWechatProductsController {
	@Resource
	private MWechatProductsService mWechatProductsService;

	/**
	 * 获取该地区的所有的商品分类
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatProductsController/getProductTypeList")
	@ResponseBody
	public void getProductTypeList(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatProductsService.getTypeList(inputObject, outputObject);
	}


	/**
	 * 手机端获取该地区商店的产品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatProductsController/getProductList")
	@ResponseBody
	public void getProductList(InputObject inputObject ,OutputObject outputObject)throws Exception{
		mWechatProductsService.getProductList(inputObject, outputObject);
	}
	
	/**
	 * 手机端获取该地区商店的套餐
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatProductsController/getPackageList")
	@ResponseBody
	public void getPackageList(InputObject inputObject,OutputObject outputObject) throws Exception{
		mWechatProductsService.getPackageList(inputObject, outputObject);
	}
	
	/**
	 * 手机主页商品的展示
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatProductsController/getProductListForIndex")
	@ResponseBody
	public void getProductListForIndex (InputObject inputObject,OutputObject outputObject)throws Exception{
		mWechatProductsService.getProductListForIndex(inputObject, outputObject);
	}
	
}
