package com.ssm.wechatpro.service;

import com.ssm.wechatpro.object.InputObject;
import com.ssm.wechatpro.object.OutputObject;

public interface WechatCityService {

	void queryCityAllByList(InputObject inputObject, OutputObject outputObject)throws Exception;

	void queryCityOneBySelect(InputObject inputObject, OutputObject outputObject)throws Exception;

	void queryCityTwoByList(InputObject inputObject, OutputObject outputObject)throws Exception;

	void queryCityById(InputObject inputObject, OutputObject outputObject)throws Exception;

	void addCity(InputObject inputObject, OutputObject outputObject)throws Exception;

	void deleteCityById(InputObject inputObject, OutputObject outputObject)throws Exception;

	void updateCityById(InputObject inputObject, OutputObject outputObject)throws Exception;

	void queryAllCity(InputObject inputObject, OutputObject outputObject)throws Exception;

}
