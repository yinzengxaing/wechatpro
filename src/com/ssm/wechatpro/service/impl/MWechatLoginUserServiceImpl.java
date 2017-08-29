package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.MWechatLoginUserMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.MWechatLoginUserService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
import com.ssm.wechatpro.util.MessageSendUtil;
import com.ssm.wechatpro.util.ToolUtil;
import com.wechat.service.PhoneMessageService;

@Service
public class MWechatLoginUserServiceImpl implements MWechatLoginUserService{

	@Resource
	private MWechatLoginUserMapper mWechatLoginUserMapper;
	
	/**
	 * 手机端获取验证码
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getIdetifyCode(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		String idetifyCode = MessageSendUtil.getWord();//获取验证码
		String number = map.get("loginPhone").toString();//获取手机号
		if(JudgeUtil.isPhoneNO(number)){//判断手机号是否合格
			Map<String, Object> bean = PhoneMessageService.sendPhoneMessage(number, idetifyCode);// 发送短信
			if (bean.get("error_code").toString().equals("0")) {
			}else{
				outputObject.setreturnMessage(bean.get("reason").toString());
			}
		}else if(JudgeUtil.isNull(number)){
			outputObject.setreturnMessage("手机号不能为空");
		}else{
			outputObject.setreturnMessage("手机号不合格");
		}
		Map<String,Object> bean = new HashMap<>();
		bean.put("phoneMessage", idetifyCode);
		outputObject.setBean(bean);//将验证码显示在页面
	}
	
	/**
	 * 手机端注册
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertPhoneLogin(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("createTime",DateUtil.getTimeAndToString());//获取当前注册时间
		map.put("loginIdentity", Constants.USER_IDENTITY);//默认状态下用户为普通身份
		map.put("loginState", Constants.USER_STATE);//默认状态下用户状态为正常
		
		if( JudgeUtil.isNull(map.get("passeordFirst").toString()) && JudgeUtil.isNull(map.get("passeordSecond").toString()) ){//如果密码为空，则用户类型为免密登录（1）
			outputObject.setreturnMessage("密码不能为空！");
			return;
		}else{//如果密码不为空，则用户类型为账号密码登录（2）
			map.put("loginType", Constants.USER_STATEBYPASSWORD);
			if(map.get("passeordFirst").toString().equals(map.get("passeordSecond").toString())){
				if(map.get("passeordFirst").toString().length()>5 && map.get("passeordFirst").toString().length()<19 ){
					map.put("loginPassword", ToolUtil.MD5(map.get("passeordFirst").toString()));
				}else{
					outputObject.setreturnMessage("密码长度必须为6-18位!!");
					return;
				}
			}else{
				outputObject.setreturnMessage("两次密码输入不相同，请输入密码!!");
				return;
			}
		}
		
		//判断获得的验证码和手机上的验证码是否一样
		if(map.get("phoneMessage").toString().equals(map.get("sendPhoneMessage").toString())){
		}else{
			outputObject.setreturnMessage("验证码不正确！");
			return;
		}		
		
		//验证手机号是否合格
		if(JudgeUtil.isPhoneNO(map.get("loginPhone").toString())){//手机号合格
			Map<String,Object> loginPhone = mWechatLoginUserMapper.selectByLoginPhone(map);//判断手机号是否被注册过，而且是否为免密登录
			if(loginPhone != null){
				if(loginPhone.get("loginType").toString().equals(Constants.USER_STATE)){//免密登录
					//修改密码和用户类型为账号密码登录
					map.put("loginPasswordAfter", ToolUtil.MD5(map.get("passeordFirst").toString()));
					map.put("loginType", Constants.USER_STATEBYPASSWORD);
					mWechatLoginUserMapper.updatePasswordAndType(map);
				}else{
					outputObject.setreturnMessage("该手机号为账号密码登录类型，且已被注册!!");
				}
			}else{
				mWechatLoginUserMapper.insertPhoneLogin(map);//添加用户
			}
		}else if(JudgeUtil.isNull(map.get("loginPhone").toString())){
			outputObject.setreturnMessage("手机号不能为空!!");
		}else{
			outputObject.setreturnMessage("手机号不合格!!");
		}	
		
	}
	
	/**
	 * 登录(免密登录)
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void LoginNoPassword(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		String phoneNumber = map.get("loginPhone").toString();
		if(JudgeUtil.isNull(phoneNumber)){
			outputObject.setreturnMessage("该手机号不能为空！");
			return;
		}else if(!JudgeUtil.isPhoneNO(phoneNumber)){
			outputObject.setreturnMessage("该手机号不合格");
			return;
		} 
		
		//判断获得的验证码和手机上的验证码是否一样
		if(map.get("phoneMessage").toString().equals(map.get("sendPhoneMessage").toString())){
		}else{
			outputObject.setreturnMessage("验证码不正确！");
			return;
		}
		
		Map<String,Object> bean = mWechatLoginUserMapper.selectByLoginPhone(map);
		if(bean == null){//该手机号没有免密登录过
			map.put("createTime",DateUtil.getTimeAndToString());//获取当前注册时间
			map.put("loginIdentity", Constants.USER_IDENTITY);//默认状态下用户为普通身份
			map.put("loginState", Constants.USER_STATE);//默认状态下用户状态为正常
			map.put("loginType", Constants.USER_STATE);
			mWechatLoginUserMapper.insertPhoneLogin(map);
		}else{
			outputObject.setLogParams(bean);
		}	
	}

	/**
	 * 登录(账号密码登录)
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectLoginPassword(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean =  mWechatLoginUserMapper.selectLoginPassword(map);
		if(JudgeUtil.isNull(map.get("loginPhone").toString())){
			outputObject.setreturnMessage("手机号不能为空");
			return;
		}else if(!JudgeUtil.isPhoneNO(map.get("loginPhone").toString())){
			outputObject.setreturnMessage("该手机号不合格！");
			return;
		}else if(mWechatLoginUserMapper.selectByLoginPhone(map) == null){
			outputObject.setreturnMessage("该手机号未被注册过，请先注册！");
			return;
		}
		
	    if(JudgeUtil.isNull(bean.get("loginPassword").toString())){
			outputObject.setreturnMessage("请选择免密登录！");
		}else if(JudgeUtil.isNull(map.get("passwordbefore").toString())){
			outputObject.setreturnMessage("密码不能为空");
		}else if(!ToolUtil.MD5(map.get("passwordbefore").toString()).equals(bean.get("loginPassword").toString())){//如果从页面获取的密码与相对应的密码不相同
		    outputObject.setreturnMessage("密码错误！");
		}else{
			outputObject.setLogParams(bean);
		}
	}
	
	/**
	 * 忘记密码
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updatePassword(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		Map<String,Object> bean = mWechatLoginUserMapper.selectByLoginPhone(map);
		if(JudgeUtil.isNull(map.get("loginPhone").toString())){
			outputObject.setreturnMessage("手机号不能为空！");
			return;
		}else if(!JudgeUtil.isPhoneNO(map.get("loginPhone").toString())){
			outputObject.setreturnMessage("该手机号不合格");
			return;
		}
		
		//判断获得的验证码和手机上的验证码是否一样
		if(map.get("phoneMessage").toString().equals(map.get("sendPhoneMessage").toString())){
		}else{
			outputObject.setreturnMessage("验证码不正确！");
			return;
		}
		
		if( JudgeUtil.isNull(map.get("passeordFirst").toString()) && JudgeUtil.isNull(map.get("passeordSecond").toString()) ){//如果密码为空，则用户类型为免密登录（1）
			outputObject.setreturnMessage("密码不能为空！");
			return;
		}else{//如果密码不为空，则用户类型为账号密码登录（2）
			if(map.get("passeordFirst").toString().equals(map.get("passeordSecond").toString())){
				if(map.get("passeordFirst").toString().length()>5 && map.get("passeordFirst").toString().length()<19 ){
					map.put("loginPassword", ToolUtil.MD5(map.get("passeordFirst").toString()));
				}else{
					outputObject.setreturnMessage("密码长度必须为6-18位!!");
					return;
				}
			}else{
				outputObject.setreturnMessage("两次密码输入不相同，请输入密码!!");
				return;
			}
		}
		
		if(bean != null){//被注册过，判断为免密登录类型还是账号密码登录类型
			if(bean.get("loginType").toString().equals(Constants.USER_STATE)){//如果是免密登录
				map.put("loginPasswordAfter", ToolUtil.MD5(map.get("passeordFirst").toString()));
				map.put("loginType", Constants.USER_STATEBYPASSWORD);
				mWechatLoginUserMapper.updatePasswordAndType(map);
			}else{//账号密码登录
				mWechatLoginUserMapper.updatePassword(map);
			}
		}else if(bean == null){
			outputObject.setreturnMessage("该手机号未被注册过，请先注册！");
		}
	}
	
	/**
	 * 获取登录信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectSession(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getLogParams();
		if(map==null){
			outputObject.setreturnMessage("Session为空,获取信息失败...");
		}else{
			outputObject.setBean(map);
		}
	}

	/**
	 * 查询所有客户
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAll(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		List<Map<String,Object>> beans = mWechatLoginUserMapper.selectAll(map);
		outputObject.setBeans(beans);
	}

	/**
	 * 回显登录页面
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectPhoneAndScore(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		map.put("id", inputObject.getLogParams().get("id"));
		Map<String,Object> bean = mWechatLoginUserMapper.selectPhoneAndScore(map);//根据当前登录人的id回显其账号和积分
		outputObject.setBean(bean);
	}
	
	
}
