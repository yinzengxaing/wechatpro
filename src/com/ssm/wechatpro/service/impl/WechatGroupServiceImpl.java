package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.util.CustomException;
import com.ssm.wechatpro.dao.WechatGroupMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatGroupService;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;

@Service
public class WechatGroupServiceImpl implements WechatGroupService{

	@Resource
	private WechatGroupMapper wechatGroupMapper;
	
	/**
	 * 添加分组
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertWechatGroup(InputObject inputObject,OutputObject outputObject) throws Exception {
		 Map<String,Object> map = inputObject.getParams();//获取传入的参数
		 map.put("creatTime", DateUtil.getTimeAndToString());
		 map.put("creatUserId", "1001");
		 int count = wechatGroupMapper.selectByName(map);//查询与此分组名称相同的分组信息
		 if(count>0){//若有重名的，则添加分组失败
			 outputObject.setreturnMessage("添加分组失败，该分组名称已存在！");
		 }else{
			 wechatGroupMapper.insertGroup(map);//反之，添加分组成功
		 }
	}
	
	/**
	 * 查询所有分组信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectWechatGroup(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();//获取参数
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String,Object>> beans = wechatGroupMapper.selectAll(map,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
	}
	
	/**
	 * 根据ID查询一个分组信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectOne(InputObject inputObject, OutputObject outputObject) throws Exception {
       Map<String,Object> map = inputObject.getParams();
       Map<String,Object> bean = wechatGroupMapper.selectById(map);
       outputObject.setBean(bean);
	}
	
	/**
	 * 删除分组
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void delete(InputObject inputObject, OutputObject outputObject) throws Exception {
       Map<String,Object> map = inputObject.getParams();
       wechatGroupMapper.deleteById(map);
	}
	
	/**
	 * 修改分组信息
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateById(InputObject inputObject, OutputObject outputObject) throws Exception {
         Map<String,Object> map = inputObject.getParams();
         if(JudgeUtil.isNull(map.get("id").toString())){
        	 throw new CustomException("分组ID不能为空");
         }
         int count = wechatGroupMapper.selectByName(map);//查询与此分组名称相同的分组信息
		 if(count>0){//若此分组名已存在，则不能修改
			 outputObject.setreturnMessage("修改分组失败，该分组名称已存在！");
		 }else{
			  wechatGroupMapper.update(map);//更新用户
		 }		
	}
	
}
