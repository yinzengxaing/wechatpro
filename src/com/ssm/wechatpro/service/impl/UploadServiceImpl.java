package com.ssm.wechatpro.service.impl;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssm.wechatpro.dao.UploadMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.UploadService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.UploadUtil;
import com.wechat.service.WeChatApiUtil;

@Service
public class UploadServiceImpl implements UploadService{
	
	@Resource
	private UploadMapper uploadMapper;

	/**
	 * 上传图片
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@Override
	public void insertImgFile(InputObject inputObject, OutputObject outputObject, CommonsMultipartFile files) throws Exception {
		Map<String,Object> map = UploadUtil.imgFileUpload(inputObject, outputObject, files);
		if(map!=null){
			uploadMapper.insertOption(map);
			outputObject.setBean(map);
		}else{
			outputObject.setreturnMessage("文件类型不正确，只允许上传jpg,png,jpeg,svg格式的图片");
		}
	}
	
	/**
	 * 上传图片
	 * @param inputObject
	 * @param outputObject
	 * @param files
	 * @throws Exception
	 */
	@Override
	public void insertImgFileWechat(InputObject inputObject, OutputObject outputObject, CommonsMultipartFile files) throws Exception {
		Map<String,Object> map = UploadUtil.imgFileUpload(inputObject, outputObject, files);
		if(map!=null){
			HttpServletRequest request = InputObject.getRequest();
			String fil = "";
			String path = request.getSession().getServletContext().getRealPath(fil);
			uploadMapper.insertOption(map);
			String s = path+"\\"+map.get("optionPath");
			File f = new File(s);
			String token = WeChatApiUtil.getToken(Constants.APPID, Constants.APPSECRET);
			JSONObject o = WeChatApiUtil.uploadMedia(f, token, "image");
			map.put("url", o.getString("url"));
			outputObject.setBean(map);
		}else{
			outputObject.setreturnMessage("文件类型不正确，只允许上传jpg,png,jpeg,svg格式的图片");
		}
	}
	
	
	
}
