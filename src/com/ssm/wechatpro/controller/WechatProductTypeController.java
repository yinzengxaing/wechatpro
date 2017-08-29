package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductTypeService;

@Controller
public class WechatProductTypeController {

	@Resource
	private WechatProductTypeService wechatProductTypeService;

	/**
	 * 获取全部的产品类型列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductTypeController/getProductTypeList")
	@ResponseBody
	public void getProductTypeList(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductTypeService.getProductTypeList(inputObject, outputObject);
	}
	
	/**
	 * 通过id获取产品类型信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductTypeController/getProductTypeById")
	@ResponseBody
	public void getProductType(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductTypeService.getProductTypeById(inputObject, outputObject);
	}
	
	/**
	 * 添加一个产品类型信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductTypeController/addProductType")
	@ResponseBody
	public void addProductType(InputObject inputObject , OutputObject outputObject) throws Exception{
		wechatProductTypeService.addProductType(inputObject, outputObject);
	}
	
	/**
	 * 修改一个产品类型信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductTypeController/updateProductType")
	@ResponseBody
	public void updateProductType(InputObject inputObject , OutputObject outputObject) throws Exception{
		wechatProductTypeService.updateProductType(inputObject, outputObject);
	}
	
	/**
	 * 提审一个产品类型信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductTypeController/updateStateSubmit")
	@ResponseBody
	public void updateStateSubmit(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductTypeService.updateStateSubmit(inputObject, outputObject);
	}
	
	/**
	 * 删除一个产品类型信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductTypeController/deleteProductType")
	@ResponseBody
	public void deleteProductType(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductTypeService.deleteProductType(inputObject, outputObject);
	}
	
	/**
	 * 获取所有已经上线的商品类型
	 * 查询所有商品的种类，并按照id递增顺序排列
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductTypeController/getTypeOnline")
	@ResponseBody
	public void getTypeOnline(InputObject inputObject ,OutputObject outputObject) throws Exception{
		wechatProductTypeService.getTypeOnline(inputObject, outputObject);
}
	
	/**
	 * 查询所有商品的种类，并按照id递增顺序排列
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductTypeController/selectProductType")
	@ResponseBody
	public void selectProductType(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatProductTypeService.selectProductType(inputObject, outputObject);
	}
	
	/**
	 * 获取所有的待审核状态的产品类别
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductTypeController/getCheckPendingList")
	@ResponseBody
	public void getCheckPendingList(InputObject inputObject ,OutputObject outputObject) throws Exception{
		wechatProductTypeService.getCheckPendingList(inputObject, outputObject);
	}
	
	/**
	 *审核通过一个商品类别 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductTypeController/updateStatePass")
	@ResponseBody
	public void updateStatePass(InputObject inputObject,OutputObject outputObject)throws Exception{
		wechatProductTypeService.updateStatePass(inputObject, outputObject);
	}
	
	/**
	 *审核不通过一个商品类别 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProductTypeController/updateStateNoPass")
	@ResponseBody
	public void updateStateNoPass(InputObject inputObject,OutputObject outputObject)throws Exception{
		wechatProductTypeService.updateStateNoPass(inputObject, outputObject);
	}
}
