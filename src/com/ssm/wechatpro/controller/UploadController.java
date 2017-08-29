package com.ssm.wechatpro.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.UploadService;


@Controller
public class UploadController {
	
	@Resource
	private UploadService uploadService;
	
	/**
	 * 上传图片
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/UploadController/insertImgFile")
	@ResponseBody
	public void insertImgFile(InputObject inputObject,OutputObject outputObject,@RequestParam("imgFiles") CommonsMultipartFile files) throws Exception {
		uploadService.insertImgFile(inputObject, outputObject, files);
	}
	
	/**
	 * 上传图片
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/UploadController/insertImgFileOne")
	@ResponseBody
	public void insertImgFileOne(InputObject inputObject,OutputObject outputObject,@RequestParam("imgFilesOne") CommonsMultipartFile files) throws Exception {
		uploadService.insertImgFile(inputObject, outputObject, files);
	}
	
	/**
	 * 上传图片
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/UploadController/insertImgFileTwo")
	@ResponseBody
	public void insertImgFileTwo(InputObject inputObject,OutputObject outputObject,@RequestParam("imgFilesTwo") CommonsMultipartFile files) throws Exception {
		uploadService.insertImgFile(inputObject, outputObject, files);
	}
	
	/**
	 * 上传图片
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/UploadController/insertImgFileThree")
	@ResponseBody
	public void insertImgFileThree(InputObject inputObject,OutputObject outputObject,@RequestParam("imgFilesThree") CommonsMultipartFile files) throws Exception {
		uploadService.insertImgFile(inputObject, outputObject, files);
	}
	
	/**
	 * 上传图片
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("post/UploadController/insertImgFileFour")
	@ResponseBody
	public void insertImgFileFour(InputObject inputObject,OutputObject outputObject,@RequestParam("imgFilesFour") CommonsMultipartFile files) throws Exception {
		uploadService.insertImgFile(inputObject, outputObject, files);
	}
	
	/**
	 * 上传图片
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("/post/UploadController/insertImgFileFive")
	@ResponseBody
	public void insertImgFileFive(InputObject inputObject,OutputObject outputObject,@RequestParam("imgFilesFive") CommonsMultipartFile files) throws Exception {
		uploadService.insertImgFile(inputObject, outputObject, files);
	}
	
	/**
	 * 微信上传会员卡背景图片
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("/post/UploadController/insertImgFileWechatOne")
	@ResponseBody
	public void insertImgFileWechatOne(InputObject inputObject,OutputObject outputObject,@RequestParam("imgFilesFivebackground") CommonsMultipartFile files) throws Exception {
		uploadService.insertImgFileWechat(inputObject, outputObject, files);
	}
	/**
	 * 微信上传会员卡logo图片
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("/post/UploadController/insertImgFileWechatTwo")
	@ResponseBody
	public void insertImgFileWechatTwo(InputObject inputObject,OutputObject outputObject,@RequestParam("imgFilesFivelogo") CommonsMultipartFile files) throws Exception {
		uploadService.insertImgFileWechat(inputObject, outputObject, files);
	}
	
	/**
	 * 微信上传会员卡简介图片
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping("/post/UploadController/insertImgFileWechatThree")
	@ResponseBody
	public void insertImgFileWechatThree(InputObject inputObject,OutputObject outputObject,@RequestParam("imgFilesFiveimage") CommonsMultipartFile files) throws Exception {
		uploadService.insertImgFileWechat(inputObject, outputObject, files);
	}
	
}
