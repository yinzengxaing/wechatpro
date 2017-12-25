package com.ssm.interceptor;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import com.ssm.util.CustomException;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.object.PutObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.JudgeUtil;

public class HandlerInterceptorGateway implements HandlerInterceptor{
	
	
	private static final String[] URL = {"/wechatpro/gateway/MWechatDeliveryAddressController/insertDeliveryAddress","/wechatpro/gateway/MWechatDeliveryAddressController/selectByDeliveryUserId","/wechatpro/gateway/MWechatDeliveryAddressController/deleteById",
		"/wechatpro/gateway/MWechatDeliveryAddressController/updateAddress","/wechatpro/gateway/MWechatDeliveryAddressController/selectById",
		"/wechatpro/gateway/MWechatDeliveryAddressController/updateUse","/wechatpro/gateway/MWechatCustomerOrderController/updatePayState"};
	
	private static final String[] LOGIN_URL = {"/wechatpro/gateway/WechatUserController/selectLatitudeAndLongtitude",
		"/wechatpro/gateway/EmpowerWebpageController/getOpenidBycode"};

	//在进入Handler方法之前执行  
	//用于身份认证、身份授权、  
	//比如身份认证，如果认证不通过表示当前用户没有登录，需要此方法拦截不再向下执行
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	HttpServletRequest servletRequest = (HttpServletRequest) request;
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		new PutObject(request,response);
		String url = servletRequest.getContextPath() + servletRequest.getServletPath();		
		Map<String,Object> wechatUser = InputObject.getWechatLogInParams();
		if(wechatUser == null ){//判断session是否为空
			if(!JudgeUtil.contains(LOGIN_URL,url)){
				if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
					//如果是ajax请求响应头会有x-requested-with
					return true;
				}else{
					//非ajax请求时
					return true;
				}
			}else if(JudgeUtil.contains(URL,url)){
				throw new CustomException("错误提示:您还未登录,请先登录<br/>错误类型:Session为空");
			}else{
				return true;
			}
		}else{
			if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
				//如果是ajax请求响应头会有x-requested-with
				return true;
			}else{
				//非ajax请求时
				return true;
			}
		}
	}

    //进入Handler方法之后，在返回ModelAndView之前执行  
    //应用场景从modelAndView对象出发：将公用的模型数据(比如菜单的导航)在这里传到视图，也可以在这里面来统一指定视图
    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
    	request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
        if (request.getHeader("x-requested-with") != null && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
        	//如果是ajax请求响应头会有x-requested-with
        	if(OutputObject.getCode()==Constants.WRONG){
        		OutputObject.setCode(0);
        		if(OutputObject.getMessage().equals(Constants.WRONG_MESSAGE)){
        			OutputObject.setMessage("成功");
        		}else{
        			OutputObject.setCode(Constants.WRONG);
        		}
        	}
        	OutputObject.put();
        }else{
        	//非ajax请求时
        	if(OutputObject.getCode()==Constants.WRONG){
        		OutputObject.setCode(0);
        		if(OutputObject.getMessage().equals(Constants.WRONG_MESSAGE)){
        			OutputObject.setMessage("成功");
        		}else{
        			OutputObject.setCode(Constants.WRONG);
        		}
        	}
        	OutputObject.put();
        }
    }

    //执行Handler完成后执行此方法  
  	//应用场景：统一的异常处理，统一的日志处理  
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
