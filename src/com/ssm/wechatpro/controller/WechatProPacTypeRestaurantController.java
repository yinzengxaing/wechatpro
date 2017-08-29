package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProPacTypeRestaurantService;
/**
 * 编辑分类信息的controller
 * @author yinzengxiang
 *
 */
@Controller
public class WechatProPacTypeRestaurantController {
	@Resource
	private WechatProPacTypeRestaurantService wechatProPacTypeRestaurantService;
	
	/**
	 * 获取所有的分类列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProPacTypeRestaurantController/getRestaurantList")
	@ResponseBody
	public void getRestaurantList(InputObject inputObject,OutputObject outputObject)throws Exception{
		wechatProPacTypeRestaurantService.getRestaurantList(inputObject, outputObject);
	}
	/**
	 * 获取所有的商品的列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProPacTypeRestaurantController/getProductList")
	@ResponseBody
	public void getProductList(InputObject inputObject,OutputObject outputObject)throws Exception{
		wechatProPacTypeRestaurantService.getProductList(inputObject, outputObject);
	}
	/**
	 * 修改一个商品分类信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProPacTypeRestaurantController/updateRestaurant")
	@ResponseBody
	public void updateRestaurant(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProPacTypeRestaurantService.updateRestaurant(inputObject, outputObject);
	}
	
	/**
	 * 获当前分类信息的创建信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProPacTypeRestaurantController/getInformationById")
	@ResponseBody
	public void getInformationById(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProPacTypeRestaurantService.getRestaurantById(inputObject, outputObject);
	}
	
}
