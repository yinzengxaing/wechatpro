package com.wechat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import net.sf.json.JSONObject;
import com.ssm.util.TokenThread;
import com.ssm.wechatpro.object.OutputObject;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.WeixinUtil;

public class CreateLandingpage {
	
	@Resource
	public static OutputObject outputObject = new OutputObject();
	/**
	 * 通过卡券货架投放会员卡
	 * @param card_id
	 * @throws Exception
	 */
	public static String  CarLandingpage(String card_id) throws Exception{
		String result = null;
		String card_url = null;
		String url = "https://api.weixin.qq.com/card/landingpage/create?access_token="+TokenThread.accessToken.getToken();// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("banner", "http://pic2.ooopic.com/11/34/39/00b2OOOPICee.jpg");
		params.put("page_title", "麦克思");
		params.put("can_share",true);
		params.put("scene", "SCENE_MENU");
		Map<String,Object> cardlist = new HashMap<>();
		cardlist.put("card_id", card_id);
		cardlist.put("thumb_url", "http://imgqn.koudaitong.com/upload_files/2015/04/13/FvgNUJUCpKoZmSIJWsjbNN36408O.jpg");
		List<Object> card_list = new ArrayList<>();
		card_list.add(cardlist);
		params.put("card_list", card_list);
		result = UtilService.net(url, params,"POST");
		JSONObject object = JSONObject.fromObject(result);
		while(!object.getString("errcode").equals("0")){
			if(object.getString("errcode").equals(Constants.TOKEINVALID)){
				TokenThread.accessToken = WeixinUtil.getAccessToken(TokenThread.appid,TokenThread.appsecret);
				url = "https://api.weixin.qq.com/card/landingpage/create?access_token="+TokenThread.accessToken.getToken();
				result=UtilService.net(url, params, "POST");
				object = JSONObject.fromObject(result);
				}else{
					String returnMessage = object.getString("errmsg");
					String returnCode = object.getString("errcode");
					outputObject.setreturnMessage(returnMessage, returnCode);
					}
			}
		card_url = object.get("url").toString();
		return card_url;
		}
	
	public static void main(String[] agrs) throws Exception{
		CarLandingpage("pSU_mt4ZKnrDcBRgD_sxiJiG2eDM");
	}
	

}
