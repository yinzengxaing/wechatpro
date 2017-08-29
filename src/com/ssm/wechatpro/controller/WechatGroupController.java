package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatGroupService;

@Controller
public class WechatGroupController {

	@Resource
	private WechatGroupService wechatGroupService;
	
	/**
	 * 添加分组
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatGroupController/insertWeChatGroup")
	@ResponseBody
	public void insertWeChatGroup(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatGroupService.insertWechatGroup(inputObject, outputObject);
	}
	
	/**
	 * 查询所有分组信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatGroupController/selectAll")
	@ResponseBody
	public void selectAll(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatGroupService.selectWechatGroup(inputObject, outputObject);
	}
	
	/**
	 * 根据ID查询一个分组信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatGroupController/selectById")
	@ResponseBody
	public void selectById(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatGroupService.selectOne(inputObject, outputObject);
	}
	
	/**
	 * 删除分组
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatGroupController/delete")
	@ResponseBody
	public void delete(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatGroupService.delete(inputObject, outputObject);
	}
	
	/**
	 * 修改分组信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatGroupController/update")
	@ResponseBody
	public void update(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatGroupService.updateById(inputObject, outputObject);
	}
}
