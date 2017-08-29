package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatShoppingCartService;

@Controller
public class WechatShoppingCartController {
	
	@Resource
	private WechatShoppingCartService wechatShoppingCartService;

	/**
	 * 根据购物车所属人id显示该用户购物车中的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatShoppingCartController/selectAllShoppingCart")
	@ResponseBody
	public void selectAllShoppingCart(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatShoppingCartService.selectAllShoppingCart(inputObject,outputObject);
	}

	/**
	 * 向购物车中添加商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatShoppingCartController/insertShoppingCart")
	@ResponseBody
	public void insertShoppingCart(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatShoppingCartService.insertShoppingCart(inputObject, outputObject);
	}

	/**
	 * 删除购物车中的信息(通过所属人id和商品id)
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatShoppingCartController/deleteShopptinCartByCustomerLoginIdAndCommodity")
	@ResponseBody
	public void deleteShopptinCartByCustomerLoginIdAndCommodity(InputObject inputObject, OutputObject outputObject)throws Exception {
		wechatShoppingCartService.deleteShopptinCartByCustomerLoginIdAndCommodity(inputObject,outputObject);
	}
}
