package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductBrandTagService;

@Controller
public class WechatProductBrandTagController {
	@Resource 
	private WechatProductBrandTagService wechatProductBrandTagService;
	
	/**
	 * 获取全部的产品品牌列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductBrandTagController/getProductBrandTagList")
	@ResponseBody
	public void getProductBrandTagList(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductBrandTagService.getProductBrandTagList(inputObject, outputObject);
	}
	
	/**
	 * 根据ID获取一个产品品牌信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductBrandTagController/getProductBrandTagById")
	@ResponseBody
	public void getProductBrandTagById(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductBrandTagService.getProductBrandTagById(inputObject, outputObject);
	}
	
	/**
	 * 添加一个产品品牌信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductBrandTagController/addProductBrandTag")
	@ResponseBody
	public void addProductBrandTag(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProductBrandTagService.addProductBrandTag(inputObject, outputObject);
	}
	
	/**
	 * 修改一个产品品牌信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductBrandTagController/updateProductBrandTag")
	@ResponseBody
	public void updateProductBrandTag(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatProductBrandTagService.updateProductBrandTag(inputObject, outputObject);
	}
	
	/**
	 * 获取所有已经上线的产品品牌
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductBrandTagController/getBrandTagOnline")
	@ResponseBody
	public void getBrandTagOnline(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductBrandTagService.getBrandTagOnline(inputObject, outputObject);
	}
	
	/**
	 * 获取所有待审核的产品品牌
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductBrandTagController/getCheckPendingList")
	@ResponseBody
	public void getCheckPendingList(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductBrandTagService.getCheckPendingList(inputObject, outputObject);
	}
	
	/**
	 * 审核通过一个商品品牌
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductBrandTagController/updateStatePass")
	@ResponseBody
	public void updateStatePass(InputObject inputObject ,OutputObject outputObject)throws Exception{
		wechatProductBrandTagService.updateStatePass(inputObject, outputObject);
	}
	
	/**
	 * 审核通过一个商品品牌
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductBrandTagController/updateStateNoPass")
	@ResponseBody
	public void updateStateNoPass(InputObject inputObject ,OutputObject outputObject)throws Exception{
		wechatProductBrandTagService.updateStateNoPass(inputObject, outputObject);
	}
}
	
