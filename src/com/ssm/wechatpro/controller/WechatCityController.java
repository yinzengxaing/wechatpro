package com.ssm.wechatpro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatCityService;

/**
 * 商店的有关操作
 * @author mps
 *
 */
@Controller
public class WechatCityController {

	@Autowired
	private WechatCityService wechatCityService;
	
	/**
	 * 查询一、二级列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatCityController/queryCityAllByList")
	@ResponseBody
	public void queryCityAllByList(InputObject inputObject, OutputObject outputObject)throws Exception{
		wechatCityService.queryCityAllByList(inputObject, outputObject);
	}
	
	/**
	 * 查询一级列表，用作select查询
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatCityController/queryCityOneBySelect")
	@ResponseBody
	public void queryCityOneBySelect(InputObject inputObject, OutputObject outputObject)throws Exception{
		wechatCityService.queryCityOneBySelect(inputObject, outputObject);
	}
	
	/**
	 * 根据parentId查询二级列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatCityController/queryCityTwoByList")
	@ResponseBody
	public void queryCityTwoByList(InputObject inputObject, OutputObject outputObject)throws Exception{
		wechatCityService.queryCityTwoByList(inputObject, outputObject);
	}
	
	/**
	 * 根据ID查询一条记录信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatCityController/queryCityById")
	@ResponseBody
	public void queryCityById(InputObject inputObject, OutputObject outputObject)throws Exception{
		wechatCityService.queryCityById(inputObject, outputObject);
	}
	
	/**
	 * 添加城市
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatCityController/addCity")
	@ResponseBody
	public void addCity(InputObject inputObject, OutputObject outputObject)throws Exception{
		wechatCityService.addCity(inputObject, outputObject);
	}
	
	/**
	 * 根据ID删除一条记录信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatCityController/deleteCityById")
	@ResponseBody
	public void deleteCityById(InputObject inputObject, OutputObject outputObject)throws Exception{
		wechatCityService.deleteCityById(inputObject, outputObject);
	}
	
	/**
	 * 根据ID修改一条记录信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatCityController/updateCityById")
	@ResponseBody
	public void updateCityById(InputObject inputObject, OutputObject outputObject)throws Exception{
		wechatCityService.updateCityById(inputObject, outputObject);
	}
	
	/**
	 * 微信端查询所有城市
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/WechatCityController/queryAllCity")
	@ResponseBody
	public void queryAllCity(InputObject inputObject, OutputObject outputObject)throws Exception{
		wechatCityService.queryAllCity(inputObject, outputObject);
	}
}
