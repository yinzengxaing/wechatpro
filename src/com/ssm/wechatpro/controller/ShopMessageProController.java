package com.ssm.wechatpro.controller;

import io.goeasy.GoEasy;
import io.goeasy.publish.GoEasyError;
import io.goeasy.publish.PublishListener;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;


@Controller
public class ShopMessageProController {
	/**
	 * 注册
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@RequestMapping("post/ShopMessageProController/test")
	@ResponseBody
	public void test(InputObject inputObject,final OutputObject outputObject) throws Exception{
		GoEasy goEasy = new GoEasy("BC-88866c36fae14e9f8037f59b8105d62e");
		goEasy.publish("18530289613","你好", new PublishListener(){
			@Override
			public void onSuccess() {
				System.out.print("消息发布成功。");
			}
			@Override
			public void onFailed(GoEasyError error) {
				outputObject.setreturnMessage("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent());
				System.out.print("消息发布失败, 错误编码：" + error.getCode() + " 错误信息： " + error.getContent());
			}
		});
	}
}
