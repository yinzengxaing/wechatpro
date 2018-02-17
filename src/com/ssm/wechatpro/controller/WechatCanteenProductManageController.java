package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatCanteenProductManageService;

@Controller
public class WechatCanteenProductManageController {
	
	@Resource
	private WechatCanteenProductManageService wechatCanteenProductManageService;
	

	/**
	 * 显示所选产品的种类信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatCanteenProductManageController/selectProductTypeByChoose")
	@ResponseBody
	public void selectProductTypeByChoose(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatCanteenProductManageService.selectProductTypeByChoose(inputObject, outputObject);
	}
	/**
	 * 显示所选择的产品信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatCanteenProductManageController/selectProductByChoose")
	@ResponseBody
	public void selectProductByChoose(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatCanteenProductManageService.selectProductByChoose(inputObject, outputObject);
	}
	
	/**
	 * 显示所选择的常规套餐的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatCanteenProductManageController/selectPackageProductByChoose")
	@ResponseBody
	public void selectPackageProductByChoose(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatCanteenProductManageService.selectPackageProductByChoose(inputObject, outputObject);
	}
	/**
	 * 显示所选择的可选套餐的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatCanteenProductManageController/selectChoosePackageByChoose")
	@ResponseBody
	public void selectChoosePackageByChoose(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatCanteenProductManageService.selectChoosePackageByChoose(inputObject, outputObject);
	}
	
	/**
	 * 显示商品的数量
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatCanteenProductManageController/selectNum")
	@ResponseBody
	public void selectNum (InputObject inputObject, OutputObject outputObject)throws Exception{
		wechatCanteenProductManageService.selectNum(inputObject, outputObject);
	}
	/**
	 * 更新商品的总数量
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatCanteenProductManageController/updateProductNum")
	@ResponseBody
	public void updateProductNum(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatCanteenProductManageService.updateProductNum(inputObject, outputObject);
	}
	
	/**
	 *  重置卖出数量
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatCanteenProductManageController/updateProductNowNum")
	@ResponseBody
	public void updateProductNowNum(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatCanteenProductManageService.updateProductNowNum(inputObject, outputObject);
	}
	
	/**
	 * 商品上下线
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatCanteenProductManageController/updateProductState")
	@ResponseBody
	public void updateProductState(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatCanteenProductManageService.updateProductState(inputObject, outputObject);
	}
	
	/**
	 * 表示用现金支付的用户，在指定商品上加一
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatCanteenProductManageController/updateCashProple")
	@ResponseBody
	public void updateCashProple(InputObject inputObject,OutputObject outputObject) throws Exception {
		wechatCanteenProductManageService.updateCashProple(inputObject, outputObject);
	}
	
	/**
	 * 查看商店的信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatCanteenProductManageController/selectForShopInfo")
	@ResponseBody
	public void selectForShopInfo(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatCanteenProductManageService.selectForShopInfo(inputObject, outputObject);
	}
	
	/**
	 * 各个门店修改地址，营业时间等
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatCanteenProductManageController/updateProductForShop")
	@ResponseBody
	public void updateProductForShop(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatCanteenProductManageService.updateProductForShop(inputObject, outputObject);
	}
}
