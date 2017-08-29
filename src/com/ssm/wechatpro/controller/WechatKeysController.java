package com.ssm.wechatpro.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatKeysService;

@Controller
public class WechatKeysController {

	@Resource
	private WechatKeysService wechatKeysService;

	/**
	 * 查询所有的关键字
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatKeysController/selectAllKeys")
	@ResponseBody
	public void selectAllKeys(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatKeysService.selectAllKeys(inputObject, outputObject);
	}

	/**
	 * 添加关键字
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatKeysController/addWechatKey")
	@ResponseBody
	public void addWechatKey(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatKeysService.addWechatKey(inputObject, outputObject);
	}

	/**
	 * 修改关键字
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatKeysController/updateWechatKey")
	@ResponseBody
	public void updateWechatKey(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatKeysService.updateWechatKey(inputObject, outputObject);
	}

	/**
	 * 删除关键字
	 * 
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatKeysController/deleteWechatKey")
	@ResponseBody
	public void deleteWechatKey(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatKeysService.deleteWechatKey(inputObject, outputObject);
	}

	@RequestMapping("/post/WechatKeysController/selectKey")
	@ResponseBody
	public void selectKey(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatKeysService.selectKey(inputObject, outputObject);
	}

}
