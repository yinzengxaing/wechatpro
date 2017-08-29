package com.ssm.wechatpro.service.impl;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssm.wechatpro.service.WechatProductRestaurantService;

@Component
public class Timer {

	@Resource
	private WechatProductRestaurantService wechatProductRestaurantService;
	
	/**
	 * 每天凌晨修改商品状态
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception 0/5 * * * * ?
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void updateState() throws Exception {
		wechatProductRestaurantService.updateState();
	}
	
	/**
	 * 每月15号创建月表
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception 0 0 0 15 * ?
	 */
	@Scheduled(cron = "0 0 0 15 * ?")
	public void insertTable() throws Exception {
		wechatProductRestaurantService.insertTable();
	}
	
	/**
	 * 每天凌晨清空购物车
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception 0/5 * * * * ?
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void deleteAllCart() throws Exception {
		wechatProductRestaurantService.deleteAllCart();
	}
	
}
