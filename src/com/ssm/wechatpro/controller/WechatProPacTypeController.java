package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProPacTypeService;

@Controller
public class WechatProPacTypeController {
	@Resource
	private WechatProPacTypeService wechatProPacTypeService;
	
	/**
	 * 获取分页的类别列表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProPacTypeController/getPacTypeList")
	@ResponseBody
	public void getPacTypeList(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProPacTypeService.getPacTypeList(inputObject, outputObject);
	}
	
	/**
	 * 通过id获取一个分类信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProPacTypeController/getPacTypeById")
	@ResponseBody
	public void getPacTypeById(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProPacTypeService.getPacTypeById(inputObject, outputObject);
	}
	
	/**
	 * 添加一分类信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProPacTypeController/addPacType")
	@ResponseBody
	public void addPacType(InputObject inputObject,OutputObject outputObject)throws Exception{
		wechatProPacTypeService.addPacType(inputObject, outputObject);
	}
	
	/**
	 * 删除一个分类
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProPacTypeController/deletePacType")
	@ResponseBody
	public void deletePacType(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProPacTypeService.deletePacType(inputObject, outputObject);
	}
	
	/**
	 * 修改一个分类
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatProPacTypeController/updatePacType")
	@ResponseBody
	public void updatePacType(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatProPacTypeService.updatePacType(inputObject, outputObject);
	}
	
	
}
