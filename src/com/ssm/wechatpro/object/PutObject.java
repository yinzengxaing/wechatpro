package com.ssm.wechatpro.object;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class PutObject {
	
	public static HttpServletRequest request;
	public static HttpServletResponse response;
	
	public PutObject(){
		
	}
	
	public PutObject(HttpServletRequest request,HttpServletResponse response){
		PutObject.request = request;
		PutObject.response = response;
	}

	public static HttpServletRequest getRequest() {
		return PutObject.request;
	}
	public static HttpServletResponse getResponse() {
		return PutObject.response;
	}
}
