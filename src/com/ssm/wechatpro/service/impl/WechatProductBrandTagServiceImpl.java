package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatProductBrandTagMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatProductBrandTagService;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;


@Service
public class WechatProductBrandTagServiceImpl implements WechatProductBrandTagService {
	
	
	@Resource
	private WechatProductBrandTagMapper wechatProductBrandTagMapper;
	
	/**
	 * 查询所有的商品品牌
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getProductBrandTagList(InputObject inputObject,	OutputObject outputObject) throws Exception {
		Map<String, Object> params  = inputObject.getParams();		
		List<Map<String,Object>> productBrandTagList = wechatProductBrandTagMapper.getProductBrandTagList(params);
		if (productBrandTagList == null || productBrandTagList.size() <= 0 ){
			outputObject.setreturnMessage("现在还没有品牌，请添加!");
			return;
		}
		outputObject.setBeans(productBrandTagList);
		outputObject.settotal(productBrandTagList.size());
	}

	/**
	 * 查询一个商品品牌
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getProductBrandTagById(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> id = inputObject.getParams();
		Map<String, Object> productBrandTag = wechatProductBrandTagMapper.getProductBrandTagById(id);
		outputObject.setBean(productBrandTag);
	}

	/**
	 * 添加一个商品品牌
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void addProductBrandTag(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> user = inputObject.getLogParams();
		//判断添加的产品品牌名称是否已经存在
		Map<String, Object> productBrandTag = wechatProductBrandTagMapper.getProductBrandTagByName(params);
		if (productBrandTag != null){
			outputObject.setreturnMessage("该品牌名称已经存在，请重新输入");
			return;
		}
		String brandTagName = params.get("brandTagName").toString();
		//判断产品品牌名称是否为空
		if(JudgeUtil.isNull(brandTagName)){
			outputObject.setreturnMessage("品牌名称需要在30个字符以内");
			return;	
		}
		//判断产品品牌名称是否 >30个字符
		if (brandTagName.length() >30) {
			outputObject.setreturnMessage("品牌名称需要在30个字符以内");
			return;
		}
		//判断产品品牌名称描述点后大于300个字符
		String brandTagDesc = params.get("brandTagDesc").toString();
		if (brandTagDesc.length() > 200){
			outputObject.setreturnMessage("商品品牌描述不能大于200个字符");
		}
		
		params.put("createId", user.get("id"));
		//将新添加的商品品牌状态改变为提交审核
		params.put("brandTagState", 2);
		params.put("createTime", DateUtil.getTimeAndToString());
		wechatProductBrandTagMapper.addProductBrandTag(params);
	}

	/**
	 *	修改一个商品品牌
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateProductBrandTag(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams(); 
		String id = params.get("id").toString();
		//根据名称获取产品品牌
		Map<String, Object> productBrandTagByName = wechatProductBrandTagMapper.getProductBrandTagByName(params);
		String idByName =  null;
		//检查修改的名称是否存在
		if (productBrandTagByName != null ){
			idByName = productBrandTagByName.get("id").toString();
			if (!idByName.equals(id)){
				outputObject.setreturnMessage("该产品品牌名称已经存在,请重新输入");
				return;
			}
		}
		String brandTagName = params.get("brandTagName").toString();
		//判断产品品牌名称是否为空
		if(JudgeUtil.isNull(brandTagName)){
			outputObject.setreturnMessage("品牌名称需要在30个字符以内");
			return;	
		}
		//判断产品品牌名称是否 >30个字符
		if (brandTagName.length() >30) {
			outputObject.setreturnMessage("品牌名称需要在30个字符以内");
			return;
		}
		//判断产品品牌名称描述点后大于300个字符
		String brandTagDesc = params.get("brandTagDesc").toString();
		if (brandTagDesc.length() > 200){
			outputObject.setreturnMessage("商品品牌描述不能大于200个字符");
		}
		params.put("brandTagState", "2");
		params.put("brandTagOpinion", "");
		wechatProductBrandTagMapper.updateProductBrandTag(params);
	}
	/**
	 *	获取所有已经上线的产品品牌
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getBrandTagOnline(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("brandTagState", 2);
		List<Map<String,Object>> brandTagList = wechatProductBrandTagMapper.getProductBrandTagList(params);
		outputObject.setBeans(brandTagList);
		
	}
	/**
	 *	获取所有待审核的产品品牌
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void getCheckPendingList(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("brandTagState", 1);
		//获取分页信息
		int page = Integer.parseInt(params.get("offset").toString())/Integer.parseInt(params.get("limit").toString());
		page++;
		int limit = Integer.parseInt(params.get("limit").toString());
		 List<Map<String,Object>> productBrandTagList = wechatProductBrandTagMapper.getProductBrandTagList(params ,new PageBounds(page,limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>)productBrandTagList;
		//获取当前页数的总数
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(productBrandTagList);
		outputObject.settotal(total);
	}
	/**
	 *	审核通过
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	public void updateStatePass(InputObject inputObject, OutputObject outputObject) throws Exception{
		Map<String, Object> params = inputObject.getParams();
		params.put("brandTagState", 2);
		wechatProductBrandTagMapper.updateProductBrandTag(params);
	}
	/**
	 *	审核不通过
	 * @param inputObject
	 * @param outputObject
	 * @throws Exception
	 */
	@Override
	public void updateStateNoPass(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("brandTagState", 3);
		wechatProductBrandTagMapper.updateProductBrandTag(params);
	}
	
}
