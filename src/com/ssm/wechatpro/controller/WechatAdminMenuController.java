package com.ssm.wechatpro.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatAdminMenuService;

@Controller
public class WechatAdminMenuController {
	
	@Resource
	private WechatAdminMenuService wechatAdminMenuService;

	/**
	 * 添加菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/insertAdminMenu")
	@ResponseBody
	public void insertAdminMenu(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.insertAdminMenu(inputObject, outputObject);
	}
	
	/**
	 * 根据id删除一个菜单（角色使用）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/deleteMenuById")
	@ResponseBody
	public void deleteMenuById(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.deleteMenuById(inputObject, outputObject);
	}
	
	/**
	 * 根据id删除一个菜单（没有角色管理）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/deleteById")
	@ResponseBody
	public void deleteById(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.deleteById(inputObject, outputObject);
	}
	
	/**
	 * 修改菜单信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/update")
	@ResponseBody
	public void update(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.updateById(inputObject, outputObject);
	}
	
	/**
	 * 查询全部菜单信息（也可以根据菜单级别查询）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/selectAll")
	@ResponseBody
	public void selectAll(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.selectAll(inputObject, outputObject);
	}
	
	/**
	 * 查询全部菜单信息（用于权限列表查询）
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/selectAllMenuList")
	@ResponseBody
	public void selectAllMenuList(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.selectAllMenuList(inputObject, outputObject);
	}
	
	/**
	 * 根据id查询一条菜单信息，用于数据回显
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/selectById")
	@ResponseBody
	public void selectById(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.selectById(inputObject, outputObject);
	}
	
	/**
	 * 查询一级菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/selectFirst")
	@ResponseBody
	public void selectFirst(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.selectFirst(inputObject, outputObject);
	}
	
	/**
	 * 查询二级菜单
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/selectSecondMenu")
	@ResponseBody
	public void selectSecondMenu(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.selectSecondMenu(inputObject, outputObject);
	}
	
	/**
	 * 添加菜单所拥有的权限
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/insertPower")
	@ResponseBody
	public void insertPower(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.insertPower(inputObject, outputObject);
	}
	
	/**
	 * 查询二级菜单所拥有的权限
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/selectPower")
	@ResponseBody
	public void selectPower(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.selectPower(inputObject, outputObject);
	}
	
	/**
	 * 修改二级菜单所拥有的权限
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/updatePower")
	@ResponseBody
	public void updatePower(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.updatePower(inputObject, outputObject);
	}
	
	/**
	 * 查询权限的二级菜单id和一级菜单id
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatAdminMenuController/selectFirstAndSecond")
	@ResponseBody
	public void selectFirstAndSecond(InputObject inputObject,OutputObject outputObject) throws Exception{
		wechatAdminMenuService.selectFirstAndSecond(inputObject, outputObject);
	}
}
