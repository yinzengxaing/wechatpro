package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatScollorPicService;

@Controller
public class WechatScollorPicController {

	@Resource
	private WechatScollorPicService wechatScollorPicService;
	
	/**
	 * 添加广告
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatScollorPicController/insertScoller")
	@ResponseBody
	public void insertScoller(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.insertScoller(inputObject, outputObject);
	}
	
	
	/**
	 * 查询所有广告
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatScollorPicController/selectAllScollor")
	@ResponseBody
	public void selectAllScollor(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.selectAllScollor(inputObject, outputObject);
	}
	
	/**
	 * 删除一条广告
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatScollorPicController/deleteScollor")
	@ResponseBody
	public void deleteScollor(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.deleteScollor(inputObject, outputObject);
	}
	
	/**
	 * 回显
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/WechatScollorPicController/selectById")
	@ResponseBody
	public void selectById(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.selectById(inputObject, outputObject);
	}
	
	/**
	 * 编辑一条广告
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	
	@RequestMapping("post/WechatScollorPicController/updateScoller")
	@ResponseBody
	public void updateScoller(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.updateScoller(inputObject, outputObject);
	} */
	
	
	/**
	 * 发布一条通知
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 
	@RequestMapping("post/WechatScollorPicController/updateFbScollor")
	@ResponseBody
	public void updateFbScollor(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.updateFbScollor(inputObject, outputObject);
	}*/
	
	/**
	 * 取消发布通知
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 
	@RequestMapping("post/WechatScollorPicController/updateQxFbScollor")
	@ResponseBody
	public void updateQxFbScollor(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.updateQxFbScollor(inputObject, outputObject);
	}*/
	
	/**
	 * 上线
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	
	@RequestMapping("post/WechatScollorPicController/updateSxScollor")
	@ResponseBody
	public void updateSxScollor(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.updateSxScollor(inputObject, outputObject);
	} */
	
	/**
	 * 下线
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 
	@RequestMapping("post/WechatScollorPicController/updateXxScollor")
	@ResponseBody
	public void updateXxScollor(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.updateXxScollor(inputObject, outputObject);
	}*/
	
	/**
	 * 编辑展示顺序
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 
	@RequestMapping("post/WechatScollorPicController/updateScollorNum")
	@ResponseBody
	public void updateScollorNum(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.updateScollorNum(inputObject, outputObject);
	}*/
	
	/**
	 * 查询前五条已发布并且上线的通知
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 
	@RequestMapping("gateway/WechatScollorPicController/selectFiveScollor")
	@ResponseBody
	public void selectFiveScollor(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.selectFiveScollor(inputObject, outputObject);
	}
	*/
	
	/**
	 * 手机端查看通知详情
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("gateway/WechatScollorPicController/selectOneScollor")
	@ResponseBody
	public void selectOneScollor(InputObject inputObject, OutputObject outputObject) throws Exception {
		wechatScollorPicService.selectById(inputObject, outputObject);
	}
	
	
}
