package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
/**
 * app用户的service接口
 * @author administrator
 *
 */
public interface WechatAppService {
     public void addWechatApp(InputObject inputObject, OutputObject outputObject) throws Exception;
    
     public void updateApp(InputObject inputObject, OutputObject outputObject) throws Exception;
     
     public void selectApp(InputObject inputObject, OutputObject outputObject) throws Exception;
}
