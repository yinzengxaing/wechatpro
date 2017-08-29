package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatShoppingCartService;
/**
 * 购物车操作相关接口
 * @author yinzengxiang
 *
 */

@Controller
public class MWechatShoppingCartController {
	@Resource
	private MWechatShoppingCartService mWechatShoppingCartService;
	
	/**
	 * 向购物车中添加一个商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatShoppingCartController/addProduct")
	@ResponseBody
	public void addProduct(InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatShoppingCartService.addProduct(inputObject, outputObject);
	}
	
	/**
	 * 向购物车中减少一个商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatShoppingCartController/deleteProductCount")
	@ResponseBody
	public void deleteProductCount(InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatShoppingCartService.deleteProductCount(inputObject, outputObject);
	}
	
	
	/**
	 * 删除购物车中的一个商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatShoppingCartController/deleteProduct")
	@ResponseBody
	public void deleteProduct(InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatShoppingCartService.deleteProduct(inputObject, outputObject);
	}
	
	/**
	 * 修改购物车中的一个商品的数量
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatShoppingCartController/updateProductCount")
	@ResponseBody
	public void updateProductCount (InputObject inputObject, OutputObject outputObject)throws Exception{
		mWechatShoppingCartService.updateProductCount(inputObject, outputObject);
	}
	
	/**
	 * 查看购物车信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatShoppingCartController/checkCart")
	@ResponseBody
	public void checkCart (InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatShoppingCartService.checkCart(inputObject, outputObject);
	}
	
	/**
	 *删除购物车中的所有商品
	 * @param inputObject
	 * @param outputObject
	 */
	@RequestMapping("/gateway/MWechatShoppingCartController/deleteCart")
	@ResponseBody
	public void deleteCart(InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatShoppingCartService.deleteCart(inputObject, outputObject);
	}
}
