package com.ssm.wechatpro.service;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

@Service
public interface WechatAdminMenuService {
   public void insertAdminMenu(InputObject inputObject,OutputObject outputObject) throws Exception;
   
   public void deleteMenuById(InputObject inputObject,OutputObject outputObject) throws Exception;
   
   public void deleteById(InputObject inputObject,OutputObject outputObject) throws Exception;
   
   public void updateById(InputObject inputObject,OutputObject outputObject) throws Exception;
   
   public void selectAll(InputObject inputObject,OutputObject outputObject) throws Exception;
   
   public void selectAllMenuList(InputObject inputObject, OutputObject outputObject) throws Exception;
   
   public void selectById(InputObject inputObject,OutputObject outputObject) throws Exception;
   
   public void selectFirst(InputObject inputObject,OutputObject outputObject) throws Exception;

   public void selectSecondMenu(InputObject inputObject,OutputObject outputObject) throws Exception;
   
   public void insertPower(InputObject inputObject,OutputObject outputObject) throws Exception;
   
   public void selectPower(InputObject inputObject,OutputObject outputObject) throws Exception;
   
   public void updatePower(InputObject inputObject,OutputObject outputObject) throws Exception;

   public void selectFirstAndSecond(InputObject inputObject,OutputObject outputObject) throws Exception;
}
