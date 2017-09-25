package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.impl.WechatActivityProductServiceImpl;

@Controller
public class WechatActivityProductController {

	@Resource
	private WechatActivityProductServiceImpl wechatActivityProductServiceImpl;
	
	
	/**
	 * 添加一个商品特价活动
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatActivityProductController/addProductActivity")
	@ResponseBody
	public void addProductActivity(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatActivityProductServiceImpl.addProductActivity(inputObject, outputObject);
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
		wechatActivityProductServiceImpl.getProductList(inputObject, outputObject);
	}
	
}
