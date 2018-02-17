package com.ssm.wechatpro.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.wechatpro.dao.WechatMapMapper;
import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.service.WechatMapService;

@Service
public class WechatMapServiceImpl implements WechatMapService {

	@Autowired
	private WechatMapMapper wechatMapMapper;

	/**
	 * 根据字典排序获取所有的城市列表
	 */
	@Override
	public void queryAllCityByABC(InputObject inputObject,OutputObject outputObject) throws Exception {
		Map<String, Object> map = inputObject.getParams();
		List<Map<String, Object>> beans = wechatMapMapper.queryAllCityByABC(map);
		String arr[] = null;
		String wordArr = "A,B,C,D,E,F,J,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,X,Y,Z";
		arr = wordArr.split(",");
		
//		for(int i=0;i<arr.length;i++){
//			for(int j=0;j<beans.size();j++){
//				if(arr[i].equals(beans.get(j).get("wordSort"))){
//					System.out.println(arr[i]+","+beans.get(j).get("wordSort").toString());
//				}
//			}
//		}
 		outputObject.setBeans(beans);
	}

	
}
