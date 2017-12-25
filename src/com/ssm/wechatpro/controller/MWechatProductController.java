package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatProductService;
/**
 * 处理手机端商品的展示
 *
 * @author 殷曾祥
 * 2017-7-31  上午9:15:56
 */
@Controller
public class MWechatProductController {
	@Resource
	private MWechatProductService mWechatProductService;
	
	/**
	 * 获取该地区的所有的商店
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatProductController/getAllRestaurant")
	@ResponseBody
	public void getAllRestaurant (InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatProductService.getAllRestaurant(inputObject, outputObject);
	}
	
	/**
	 * 获取该商店的所有的商品类型
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatProductController/getAllType")
	@ResponseBody
	public void getAllType (InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatProductService.getAllType(inputObject, outputObject);
	}
	
	/**
	 * 根据类型 获取该商店下的商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatProductController/getProductListByType")
	@ResponseBody
	public void getProductListByType (InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatProductService.getProductListByType(inputObject, outputObject);
	}
	
	/**
	 *	获取商品详情
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatProductController/getProductDetail")
	@ResponseBody
	public void getProductDetail (InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatProductService.getProductDetail(inputObject, outputObject);
	}
	
	/**
	 *	获取当前登录人的购物车在当前商店中的详情
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/MWechatProductController/getCartDetail")
	@ResponseBody
	public void getCartDetail (InputObject inputObject, OutputObject outputObject) throws Exception{
		mWechatProductService.getCartDetail(inputObject, outputObject);
	}
}
