package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatOrderManagerService;

@Controller
public class WechatOrderManagerController {

	@Resource
	WechatOrderManagerService wechatOrderManagerService;
	/**
	 * 查询该商店当天所有已经付款的但是没有给顾客的订单（按照流水号排序）
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatOrderManagerController/selectPaiedOrderForm")
	@ResponseBody
	public void selectPaiedOrderForm(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOrderManagerService.selectPaiedOrderForm(inputObject, outputObject);
	}
	 
	/**
	 * 根据订单的id和订单的单号查询订单详情
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatOrderManagerController/selectOrderFormInfo")
	@ResponseBody
	public void selectOrderFormInfo (InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOrderManagerService.selectOrderFormInfo(inputObject, outputObject);
	}
	
	/**
	 * 表示做好了给顾客
	 * @param map
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatOrderManagerController/updateMake")
	@ResponseBody
	public void updateMake(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOrderManagerService.updateMake(inputObject, outputObject);
	}
	
	/**
	 * 根据搜索的日期进行查询,订单统计
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/wechatOrderManagerController/selectAllOrderByDate")
	@ResponseBody
	public void selectAllOrderByDate(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatOrderManagerService.selectAllOrderByDate(inputObject, outputObject);
	}
}
