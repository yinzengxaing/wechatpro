package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductService;

@Controller
public class WechatProductController {

	@Resource
	private WechatProductService wechatProductService ;
	
	/**
	 * 查询所有产品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/getProductList")
	@ResponseBody
	public void getProductList(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductService.getProductList(inputObject, outputObject);
	}
	
	/**
	 * 查询一个产品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/getPrductById")
	@ResponseBody
	public void getPrductById(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductService.getProductById(inputObject, outputObject);
	}
	
	/**
	 * 按照类别查询产品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/selectProductType")
	@ResponseBody
	public void selectProductType(InputObject inputObject , OutputObject outputObject) throws Exception{
		wechatProductService.selectProductType(inputObject, outputObject);
	}
	/**
	 * 添加一个产品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/addProduct")
	@ResponseBody
	public void addProduct(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductService.addProduct(inputObject, outputObject);
	}

	/**
	 * 修改一个产品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/updateProduct")
	@ResponseBody
	public void updateProduct(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductService.updateProduct(inputObject, outputObject);
	}
	
	/**
	 * 删除一个商品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/deleteProduct")
	@ResponseBody
	public void deleteProduct(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductService.deleteProduct(inputObject, outputObject);
	}
	
	/**
	 * 商品所在的查询套餐信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/getPackageByProductId")
	@ResponseBody
	public void getPackageByProductId(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductService.getPackageByProductId(inputObject, outputObject);
	}
	
	/**
	 * 修改状态为提审
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/updateStateSubmit")
	@ResponseBody
	public void updateStateSubmit(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductService.updateStateSubmit(inputObject, outputObject);
	}
	/**
	 * 获取所有待审核的商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/getCheckPendingList")
	@ResponseBody
	public void getCheckPendingList(InputObject inputObject ,OutputObject outputObject)throws Exception{
		wechatProductService.getCheckPendingList(inputObject, outputObject);
	}
	
	/**
	 * 审核通过一个商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/updateStatePass")
	@ResponseBody
	public void updateStatePass(InputObject inputObject ,OutputObject outputObject)throws Exception{
		wechatProductService.updateStatePass(inputObject, outputObject);
	}
	
	/**
	 * 审核通过一个商品
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/updateStateNoPass")
	@ResponseBody
	public void updateStateNoPass(InputObject inputObject ,OutputObject outputObject)throws Exception{
		wechatProductService.updateStateNoPass(inputObject, outputObject);
	}
	/**
	 * 获取类别list
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductController/getProductTypeList")
	@ResponseBody
	public void getProductTypeList(InputObject inputObject,OutputObject outputObject)throws Exception{
		wechatProductService.getProductTypeList(inputObject, outputObject);
	}
	
	
}
