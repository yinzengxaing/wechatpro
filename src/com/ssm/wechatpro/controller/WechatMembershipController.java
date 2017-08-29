package com.ssm.wechatpro.controller;

import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatMembershipService;

@Controller
public class WechatMembershipController {
	
	@Resource
	private WechatMembershipService wechatMembershipService;
	
	/**
	 * 添加会员卡
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatMembershipController/addWechatMembership")
	@ResponseBody
	public void addWechatMembership(InputObject inputObject , OutputObject outputObject) throws Exception {
		wechatMembershipService.createWechatMembership(inputObject, outputObject);
	}
	
	
	/**
	 * 修改会员卡
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatMembershipController/updateWechatMembership")
	@ResponseBody
	public void updateWechatMembership(InputObject inputObject , OutputObject outputObject) throws Exception {
		wechatMembershipService.updateWechatMembership(inputObject, outputObject);
	}
	
	/**
	 * 删除会员卡
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatMembershipController/deleteWechatMembership")
	@ResponseBody
	public void deleteWechatMembership(InputObject inputObject , OutputObject outputObject) throws Exception {
		wechatMembershipService.deleteWechatMembership(inputObject, outputObject);
	}
	
	/**
	 * 查看会员卡详情
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatMembershipController/selectWechatMembership")
	@ResponseBody
	public void selectWechatMembership(InputObject inputObject , OutputObject outputObject) throws Exception {
		wechatMembershipService.selectWechatMembership(inputObject, outputObject);
	}
	
	
	/**
	 * 查看会员卡
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatMembershipController/selectWechatMemberships")
	@ResponseBody
	public void selectWechatMemberships(InputObject inputObject , OutputObject outputObject) throws Exception {
		wechatMembershipService.selectWechatMemberships(inputObject, outputObject);
	}
	
	/**
	 * 检索已投在公众平台投放过的会员卡
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("/post/WechatMembershipController/checkWechatMemberships")
	@ResponseBody
	public void checkWechatMemberships(InputObject inputObject , OutputObject outputObject) throws Exception {
		wechatMembershipService.checkWechatMemberships(inputObject, outputObject);
	}

}
