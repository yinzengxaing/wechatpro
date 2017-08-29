package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductRestaurantService;

@Controller
public class WechatProductRestaurantController {

	@Resource 
	private WechatProductRestaurantService wechatProductRestaurantService;
	
	/**
	 * 对每个餐厅分发产品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductRestaurantController/insertProduct")
	@ResponseBody
	public void insertProduct(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductRestaurantService.insertProduct(inputObject, outputObject);
	}
	
	/**
	 * 获得对应餐厅下的产品
	 * @param inputObject
	 * @param outputObject
	 */
	@RequestMapping("/post/WechatProductRestaurantController/getProduct")
	@ResponseBody
	public void getProduct(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProductRestaurantService.getProduct(inputObject, outputObject);
	}
	
	/**
	 * 获得对应餐厅下的产品
	 * @param inputObject
	 * @param outputObject
	 
	@RequestMapping("/post/WechatProductRestaurantController/updateStoreState")
	@ResponseBody
	public void updateStoreState(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProductRestaurantService.updateStoreState(inputObject, outputObject);
	}
	*/
}
