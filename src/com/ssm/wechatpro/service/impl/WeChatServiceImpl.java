package com.ssm.wechatpro.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import com.ssm.util.TokenThread;
import com.ssm.wechatpro.bean.wechat.ResponseTextMessage;
import com.ssm.wechatpro.dao.WechatKeysMapper;
import com.ssm.wechatpro.service.WeChatService;
import com.ssm.wechatpro.service.WechatMembershipService;
import com.ssm.wechatpro.service.WechatUserService;
import com.ssm.wechatpro.util.Constants;
import com.ssm.wechatpro.util.MessageSendUtil;
import com.ssm.wechatpro.util.MessageUtil;
import com.wechat.service.CreateLandingpage;
import com.wechat.service.GetActivationCard;
import com.wechat.service.GetCity;
import com.wechat.service.GetMemberMassage;
import com.wechat.service.GetUserMationService;

@Service
public class WeChatServiceImpl implements WeChatService {

	@Resource
	private WechatUserService wechatUserService;
	@Resource
	private WechatMembershipService wechatMembershipService;
	@Resource
	private WechatKeysMapper wechatKeysMapper;
	/**
	 * 微信消息处理
	 * @param request
	 */
	@Override
	public synchronized String WechatReply(HttpServletRequest request) {
		String respMessage = null;
		try {
			String respContent = "欢迎使用麦克思小麦机器人";// 默认返回的文本消息内容
			Map<String, String> requestMap = MessageUtil.parseXml(request);// xml请求解析
			String fromUserName = requestMap.get("FromUserName");// 发送方帐号（open_id）
			String toUserName = requestMap.get("ToUserName");// 公众帐号
			String msgType = requestMap.get("MsgType");// 消息类型
            
			// 回复文本消息
			ResponseTextMessage textMessage = new ResponseTextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			
			if(!msgType.equals(Constants.REQ_MESSAGE_TYPE_TEXT)&&!msgType.equals(Constants.REQ_MESSAGE_TYPE_EVENT)){
				return MessageSendUtil.Reback(fromUserName, toUserName, "", request);
			}
			
			if (msgType.equals(Constants.REQ_MESSAGE_TYPE_TEXT)) {//文本消息
				String content = requestMap.get("Content");
		        Map<String,Object> key = new HashMap<>();
		        key.put("wechatKey", content);
		        key = wechatKeysMapper.selectKeybyWechatKey(key);
		        if(key==null){
		        	return MessageSendUtil.Reback(fromUserName, toUserName, content, request);
		        }else{
		        	respContent = key.get("context").toString();
		        }
			} else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_IMAGE)) {// 图片消息
				// 取得图片地址
				String picUrl = requestMap.get("PicUrl"); 
				respContent = "您好，该公众号目前作为开发使用，请微信搜索max-burger关注正在运营的公众号\n很抱歉给您带来不便";
			} else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_LOCATION)) {//地理位置消息
				respContent = "您好，该公众号目前作为开发使用，请微信搜索max-burger关注正在运营的公众号\n很抱歉给您带来不便";
				String access_token = TokenThread.accessToken.getToken();// 获取access_token
				GetUserMationService.getRequest1(fromUserName);// 查看用户信息
			} else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_LINK)) {// 链接消息
				respContent = "您好，该公众号目前作为开发使用，请微信搜索max-burger关注正在运营的公众号\n很抱歉给您带来不便";
			} else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_VOICE)) {// 音频消息
				respContent = "您好，该公众号目前作为开发使用，请微信搜索max-burger关注正在运营的公众号\n很抱歉给您带来不便";
			}
			if (msgType.equals(Constants.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
				String eventType = requestMap.get("Event");// 事件类型
/*				if (eventType.equals(Constants.EVENT_TYPE_LOCATION)){//获取地理位置
					Map<String,Object> map = new HashMap<String, Object>();
					Map<String,Object> city = GetCity.getUserCity(requestMap.get("Latitude"), requestMap.get("Longitude"));
					map.put("openid", fromUserName);
					map.put("Latitude", requestMap.get("Latitude"));
					map.put("Longitude", requestMap.get("Longitude"));
					map.put("Precision", requestMap.get("Precision"));
					map.put("Location", city.get("city"));
					wechatUserService.updateWechatUserLocation(map);
				}else */if (eventType.equals(Constants.EVENT_TYPE_SUBSCRIBE)) {// 订阅
					wechatUserService.subscribe(fromUserName);
				} else if (eventType.equals(Constants.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅------取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
					wechatUserService.unsubscribe(fromUserName);
				} else if (eventType.equals(Constants.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
					
					
				}else if(eventType.equals(Constants.EVENT_TYPE_CARD_PASS_CHECK)){//卡卷审核通过
					String CardId = requestMap.get("CardId");
					String card_url = CreateLandingpage.CarLandingpage(CardId);
					Map<String, Object> map = new HashMap<>();
					map.put("card_url", card_url);
					map.put("card_id", CardId);
					wechatMembershipService.updateWechatMembershipMassage(map);
					GetActivationCard.setCard(CardId);
				}else if(eventType.equals(Constants.EVENT_TYPE_CARD_NOT_PASS_CHECK)){//卡卷审核不通过
					String RefuseReason = requestMap.get("RefuseReason");
				}else if(eventType.equals(Constants.EVENT_TYPE_USER_GET_CARD)){//用户领取卡券
				}else if(eventType.equals(Constants.EVENT_TYPE_SUBMIT_MEMBERCARD_USER_INFO)){//接收会员信息事件通知
					String CardId = requestMap.get("CardId");
					String UserCardCode = requestMap.get("UserCardCode");
					if(CardId!=null&&UserCardCode!=null){
						Map<String, Object> map =GetMemberMassage.pullMemberInfo(CardId, UserCardCode);
						if(map.get("has_active").toString().equals(Constants.STATE)){//会员卡已被激活
							wechatUserService.updateWechatUserMassage(map);
						}
					}
				}
				return null;
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}

}
