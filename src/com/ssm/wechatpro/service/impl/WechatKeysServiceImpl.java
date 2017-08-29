package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.ssm.wechatpro.dao.WechatKeysMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatKeysService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.DateUtil;
import com.ssm.wechatpro.util.JudgeUtil;
import com.ssm.wechatpro.util.ToolUtil;

@Service
public class WechatKeysServiceImpl implements WechatKeysService {

	@Resource
	WechatKeysMapper wechatKeysMapper;
	
	/***
	 * 添加关键字
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void addWechatKey(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		if(!ToolUtil.contains(params,Constants.WECHATKEY_KEY,Constants.WECHATKEY_RETURNMESSAGE, inputObject, outputObject)){
			return ;
		}
		Map<String, Object> key = wechatKeysMapper.selectKeybyWechatKey(params);
		if (JudgeUtil.isNull(params.get("context").toString()))
			outputObject.setreturnMessage("回复内容不能为空");
		else if (JudgeUtil.isNull(params.get("wechatKey").toString()))
			outputObject.setreturnMessage("关键字不能为空");
		else if (key != null)
			outputObject.setreturnMessage("该关键字已存在");
		else {
			params.put("wetherClose", 1);
			params.put("createId", inputObject.getLogParams().get("id"));
			params.put("createTime", DateUtil.getTimeAndToString());
			wechatKeysMapper.addKey(params);
		}
	}

	/***
	 * 查询所有关键字
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void selectAllKeys(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		params.put("wechatKey", "%" + params.get("wechatKey") + "%");
		int page = Integer.parseInt(params.get("offset").toString())
				/ Integer.parseInt(params.get("limit").toString());
		page++;
		int limit = Integer.parseInt(params.get("limit").toString());
		List<Map<String, Object>> keys = wechatKeysMapper.selectAllKeys(params,
				new PageBounds(page, limit));
		PageList<Map<String, Object>> abilityInfoPageList = (PageList<Map<String, Object>>) keys;
		int total = abilityInfoPageList.getPaginator().getTotalCount();
		outputObject.setBeans(keys);
		outputObject.settotal(total);
	}

	/***
	 * 删除关键字
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void deleteWechatKey(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		wechatKeysMapper.deleteKey(params);
	}

	/***
	 * 修改关键字
	 * @amparam inputObject
	 * @par outputObject
	 * @throws Exception
	 */
	@Override
	public void updateWechatKey(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		if(!ToolUtil.contains(params,Constants.WECHATKEY_KEY,Constants.WECHATKEY_RETURNMESSAGE, inputObject, outputObject)){
			return ;
		}
		Map<String, Object> keyid = wechatKeysMapper.selectKeybyId(params);
		Map<String, Object> keykey = wechatKeysMapper
				.selectKeybyWechatKey(params);
		if (JudgeUtil.isNull(params.get("context").toString()))
			outputObject.setreturnMessage("回复内容不能为空");
		else if (!JudgeUtil.isNull(params.get("wechatKey").toString())) {
			if (JudgeUtil.isNull(params.get("wetherClose").toString())) {
				if (keykey != null) {
					if (keyid.get("id") == keykey.get("id"))
						wechatKeysMapper.updateKey(params);
					else
						outputObject.setreturnMessage("该关键字已存在");
				} else
					wechatKeysMapper.updateKey(params);
			} else {
				wechatKeysMapper.updateKey(params);
			}

		} else
			outputObject.setreturnMessage("关键字不能为空");
	}

	@Override
	public void selectKey(InputObject inputObject, OutputObject outputObject) throws Exception {
		Map<String, Object> params = inputObject.getParams();
		Map<String, Object> key = wechatKeysMapper.selectKeybyId(params);
		outputObject.setBean(key);
		
	}
}
