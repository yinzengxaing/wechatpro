package com.ssm.wechatpro.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.UploadMapper;
import com.ssm.wechatpro.dao.WechatScollorPicMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatScollorPicService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
import com.ssm.wechatpro.util.ToolUtil;

@Service
public class WechatScollorPicServiceImpl implements WechatScollorPicService{

	@Resource
	private WechatScollorPicMapper wechatScollorPicMapper;
	@Resource
	private UploadMapper uploadMapper;
	
	/**
	 * 添加广告
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void insertScoller(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC_TEST, Constants.SCOLLOR_PIC_TEST_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		map.put("scollor_pic_data", DateUtil.getTime());//添加日期
		wechatScollorPicMapper.insertScoller(map);
	}

	/**
	 * 查询所有通知
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAllScollor(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String,Object>> beans = wechatScollorPicMapper.selectAllScollor(map,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)beans;
		for (Map<String, Object> bean : beans) {//遍历每一条通知，获取每一条图片的路径
			if(bean.containsKey("scollor_pic_path")){
				if(!JudgeUtil.isNull(bean.get("scollor_pic_path").toString())){
					Map<String,Object> upload = new HashMap<>();
					upload.put("id", bean.get("scollor_pic_path").toString());
					Map<String,Object> tupian = uploadMapper.selectById(upload);
					if(tupian!=null){
						bean.put("optionPath", tupian.get("optionPath").toString());
					}
				}
			}
			//Map<String,Object> param = new HashMap<>();
			//param.put("id", bean.get("scollor_pic_userid").toString());
			//Map<String,Object> map2 = wechatAdminLoginMapper.selectById(param);
			//bean.put("adminNo", map2.get("adminNo").toString());//创建人手机号
			//拼接url
			//bean.put("url", "http://"+Constants.YUMING+"/wechatpro/html/phoneModelOne/slideShow.html?id="+bean.get("id").toString());
		}
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
	}

	/**
	 * 删除一条通知
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteScollor(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC, Constants.SCOLLOR_PIC_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		wechatScollorPicMapper.deleteScollor(map);
	}

	/**
	 * 回显
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectById(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String,Object> map = inputObject.getParams();
		if(!ToolUtil.contains(map, Constants.SCOLLOR_PIC, Constants.SCOLLOR_PIC_RETURNMESSAGE, inputObject, outputObject)){
			return;
		}
		Map<String,Object> bean = wechatScollorPicMapper.selectById(map);
		//回显图片
		if(bean.containsKey("scollor_pic_path")){
			if(!JudgeUtil.isNull(bean.get("scollor_pic_path").toString())){
				Map<String,Object> upload = new HashMap<>();
				upload.put("id", bean.get("scollor_pic_path").toString());
				Map<String,Object> tupian = uploadMapper.selectById(upload);
				if(tupian!=null){
					bean.put("optionPath", tupian.get("optionPath").toString());
				}
			}
		}
		outputObject.setBean(bean);
	}
	
	
	/**
	 * 手机端显示广告图片
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAllScollorList(InputObject inputObject,OutputObject outputObject) throws Exception {
		List<Map<String,Object>> selectAllScollorList = wechatScollorPicMapper.selectAllScollorList();
		outputObject.setBeans(selectAllScollorList);
		outputObject.settotal(selectAllScollorList.size());
	}
	
}
