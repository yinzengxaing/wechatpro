package com.ssm.wechatpro.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatMapService;

/**
 * 微信端有关城市的所有操作
 * @author mps
 *
 */
@Controller
public class WechatMapController {

	@Autowired
	private WechatMapService wechatMapService;
	
	/**
	 * 根据字典排序获取所有的城市列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/gateway/WechatMapController/queryAllCityByABC")
	@ResponseBody
	public void queryAllCityByABC(InputObject inputObject, OutputObject outputObject)throws Exception{
		wechatMapService.queryAllCityByABC(inputObject, outputObject);
	}
}
