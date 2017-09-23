package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductMenuService;

/**
 * 菜单的controller
 *
 * @author 殷曾祥
 * 2017-9-20  下午8:08:33
 */
@Controller
public class WechatProductMenuController {

	@Resource
	private WechatProductMenuService wechatProductMenuService;
	
	/**
	 * 获取所有的类别
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductMenuController/getTypeList")
	@ResponseBody
	public void getTypeList(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProductMenuService.getTypeList(inputObject, outputObject);
	}
	
	/**
	 * 根据类别获取该类别下的商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductMenuController/getProductListByTypeId")
	@ResponseBody
	public void getProductListByTypeId(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProductMenuService.getProductListByTypeId(inputObject, outputObject);
	}
	
	/**
	 * 修改类别菜单顺序的接口
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductMenuController/updateTypeMenu")
	@ResponseBody
	public void updateTypeMenu(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProductMenuService.updateTypeMenu(inputObject, outputObject);
	}
	
	/**
	 * 修改商品顺序的接口
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductMenuController/updateProductMenu")
	@ResponseBody
	public void updateProductMenu(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProductMenuService.updateProductMenu(inputObject, outputObject);
	}
	
	
	
}
