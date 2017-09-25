package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.beanmapper.WechatActivityProductService;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

@Controller
public class WechatActivityProductController {

	@Resource
	private WechatActivityProductService wechatActivityProductService;
	
	
	/**
	 * 添加一个商品特价活动
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatActivityProductController/addProductActivity")
	@ResponseBody
	public void addProductActivity(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatActivityProductService.addProductActivity(inputObject, outputObject);
	}
	
	/**
	 * 获取商品列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatActivityProductController/getProductList")
	@ResponseBody
	public void getProductList(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatActivityProductService.getProductList(inputObject, outputObject);
	}
	
}
