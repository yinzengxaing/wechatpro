package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAdminRoleService;

@Controller
public class WechatAdminRoleController {

	@Resource
	private WechatAdminRoleService wechatAdminRoleService;
	
	/**
	 * 添加角色
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminRoleController/addRole")
	@ResponseBody
	public void addRole(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminRoleService.addRole(inputObject, outputObject);
	}
	
	/**
	 * 删除角色
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminRoleController/deleteById")
	@ResponseBody
	public void deleteById(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminRoleService.deleteById(inputObject, outputObject);
	}
	
	/**
	 * 查询所有角色
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminRoleController/selectAll")
	@ResponseBody
	public void selectAll(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminRoleService.selectAll(inputObject, outputObject);
	}
	
	/**
	 * 查询所有上线角色
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminRoleController/selectAllSx")
	@ResponseBody
	public void selectAllSx(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminRoleService.selectAllSx(inputObject, outputObject);
	}
	
	/**
	 * 根据id查询一个角色
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminRoleController/selectById")
	@ResponseBody
	public void selectById(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminRoleService.selectById(inputObject, outputObject);
	}
	
	/**
	 * 修改
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminRoleController/updateById")
	@ResponseBody
	public void updateById(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminRoleService.updateById(inputObject, outputObject);
	}
	
	/**
	 * 修改为审核中状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminRoleController/updateState")
	@ResponseBody
	public void updateState(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminRoleService.updateState(inputObject, outputObject);
	}
	
	/**
	 * 修改审核通过状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminRoleController/updateStatePass")
	@ResponseBody
	public void updateStatePass(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminRoleService.updateStatePass(inputObject, outputObject);
	}
	
	/**
	 * 修改审核不通过状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminRoleController/updateStateNoPass")
	@ResponseBody
	public void updateStateNoPass(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminRoleService.updateStateNoPass(inputObject, outputObject);
	}
	
	
	/**
	 * 查询角色上线审核状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminRoleController/selectByState")
	@ResponseBody
	public void selectByState(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminRoleService.selectByState(inputObject, outputObject);
	}
	
}
