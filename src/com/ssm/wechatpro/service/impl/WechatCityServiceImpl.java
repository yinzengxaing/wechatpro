package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatCityMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatCityService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.ToolUtil;

@Service
public class WechatCityServiceImpl implements WechatCityService {

	@Autowired
	private WechatCityMapper wechatCityMapper;
	
	/**
	 * 查询一级列表
	 */
	@Override
	public void queryCityAllByList(InputObject inputObject,
			OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		//获取分页信息
		int page = Integer.parseInt(map.get("offset").toString())/Integer.parseInt(map.get("limit").toString());
		page++;
		int limit = Integer.parseInt(map.get("limit").toString());
		List<Map<String, Object>> beans = wechatCityMapper.queryCityAllByList(map, new PageBounds(page, limit));
		PageList<Map<String, Object>> beansPageList = (PageList<Map<String, Object>>)beans;
		//获取当前页数的总数
		int total = beansPageList.getPaginator().getTotalCount();
		outputObject.setBeans(beans);
		outputObject.settotal(total);
	}

	/**
	 * 查询一级列表，用作select查询
	 */
	@Override
	public void queryCityOneBySelect(InputObject inputObject,
			OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map.put("cityType", Constants.CITY_First);//城市级别为1
		List<Map<String, Object>> beans = wechatCityMapper.queryCityOneBySelect(map);
		outputObject.setBeans(beans);
	}

	/**
	 * 根据parentId查询二级列表
	 */
	@Override
	public void queryCityTwoByList(InputObject inputObject,
			OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		List<Map<String, Object>> beans = wechatCityMapper.queryCityTwoByList(map);
		outputObject.setBeans(beans);
	}

	/**
	 * 根据ID查询一条记录信息
	 */
	@Override
	public void queryCityById(InputObject inputObject, OutputObject outputObject)
			throws Exception {
		Map<String, Object> map = inputObject.getParams();
		Map<String, Object> bean = wechatCityMapper.queryCityById(map);
		outputObject.setBean(bean);
	}

	/**
	 * 添加城市
	 */
	@Override
	public void addCity(InputObject inputObject, OutputObject outputObject)
			throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map.put("createTime", ToolUtil.getTimeAndToString());
		map.put("createId", inputObject.getLogParams().get("id"));
		Map<String, Object> bean = wechatCityMapper.queryCityByType(map);//根据类别和名称查询信息
		if(bean ==  null){
			if(map.get("cityType").toString().equals(Constants.CITY_First)){//一级类别
				map.put("parentId", Constants.CITY_PARENT_ID_ZERO);
				wechatCityMapper.addCity(map);
			}else if(map.get("cityType").toString().equals(Constants.CITY_SECOND)){//二级类别
				wechatCityMapper.addCity(map);
			}else{
				outputObject.setreturnMessage("非法的城市级别");
			}
		}else{
			outputObject.setreturnMessage("该城市已存在，请修改后保存.");
		}
	}

	/**
	 * 根据ID删除一条记录信息
	 */
	@Override
	public void deleteCityById(InputObject inputObject,
			OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		wechatCityMapper.deleteCityById(map);//删除一级类别
	}

	/**
	 * 根据ID修改一条记录信息
	 */
	@Override
	public void updateCityById(InputObject inputObject,
			OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		map.put("createTime", ToolUtil.getTimeAndToString());//修改时间
		map.put("createId", inputObject.getLogParams().get("id"));
		wechatCityMapper.updateCityById(map);
	}

	/**
	 * 微信端查询所有城市
	 */
	@Override
	public void queryAllCity(InputObject inputObject, OutputObject outputObject)
			throws Exception {
		Map<String, Object> map = inputObject.getParams();
		List<Map<String, Object>> beans = wechatCityMapper.queryAllCity();
		beans = ToolUtil.createDataToMenu(beans);
		outputObject.setBeans(beans);
		
	}

}
