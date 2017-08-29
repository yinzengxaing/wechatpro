package com.ssm.wechatpro.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatButtomMenuService;

@Controller
public class WechatButtomMenuController {
	
	@Resource
	private WechatButtomMenuService wechatButtomMenuService;
	
	
	/***
	 * 删除指定菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatButtomMenuController/deleteMenus")
	@ResponseBody
	public void deleteMenus(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatButtomMenuService.deleteMenus(inputObject, outputObject);
	}
	
	/***
	 * 查找所有菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatButtomMenuController/selectAllMenus")
	@ResponseBody
	public void selectAllMenus(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatButtomMenuService.selectAllMenus(inputObject, outputObject);
	}
	
	/***
	 * 查看Version相同的菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatButtomMenuController/selectMenuByVersion")
	@ResponseBody
	public void selectMenuByVersion(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatButtomMenuService.selectMenuByVersion(inputObject, outputObject);
	}
	
	/***
	 *发布菜单
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatButtomMenuController/updateMenuPublish")
	@ResponseBody
	public void updateMenuPublish(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatButtomMenuService.updateMenuPublish(inputObject, outputObject);
	}
	/***
	 *根据id查询菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatButtomMenuController/selectMenuById")
	@ResponseBody
	public void selectMenuById(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatButtomMenuService.selectMenuById(inputObject, outputObject);
	}
	
	/***
	 *修改菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatButtomMenuController/updateMenuById")
	@ResponseBody
	public void updateMenuById(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatButtomMenuService.updatemenuById(inputObject, outputObject);
	}
	/***
	 *添加菜单项
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatButtomMenuController/addMenu")
	@ResponseBody
	public void addMenu(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatButtomMenuService.addMenu(inputObject, outputObject);
	}
	/***
	 *生成版本号
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatButtomMenuController/getmenuVersion")
	@ResponseBody
	public void getmenuVersion(InputObject inputObject, OutputObject outputObject) throws Exception{
		wechatButtomMenuService.getmenuVersion(inputObject, outputObject);
	}

}
