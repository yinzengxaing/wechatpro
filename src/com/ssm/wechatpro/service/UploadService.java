package com.ssm.wechatpro.service;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface UploadService {

	public void insertImgFile(InputObject inputObject, OutputObject outputObject, CommonsMultipartFile files) throws Exception;

	public void insertImgFileWechat(InputObject inputObject, OutputObject outputObject, CommonsMultipartFile files) throws Exception;

}
