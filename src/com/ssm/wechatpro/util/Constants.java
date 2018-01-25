package com.ssm.wechatpro.util;

/**
 * 常量类
 *
 */
public class Constants {
	
	public static final String WECHAT_PAY_FOR_PREPAY_ID_URL = "https://api.mch.wexin.qq.com/pay/unifiedorder";//微信统一支付获取prepay_id接口
	
	public static final String WECHAT_FOR_SIGNATURE = "http://z1714z2699.imwork.net/wechatpro/html/phoneModelOne/orderList.html";//测试使用
	public static final String KEY  = "276ef476761549c3bd9853a2ceba5eb7";//商户平台的key值//测试使用
	public static final String APPSECRET = "946bd10ae09c79733e7def85ff0a3f62";//测试使用
	public static final String APPID = "wxf5179dd713bc6e36";//测试使用
	public static final String MCH_ID ="1364180602";//商户号ID 测试使用
	public static final String PATH = "http://z1714z2699.imwork.net/wechatpro";//测试使用
	public static final String SSL_PATH="D:/apiclientCert/apiclient_cert.p12"; //退款使用的证书地址
	public static final String IP="192.168.0.106"; //本地ip
	
//max大号	
//	public static final String WECHAT_FOR_SIGNATURE = "http://weixinorder.maxburger.com.cn/wechatpro/html/phoneModelOne/orderList.html?adminId=";//主账号
//	public static final String KEY  = "5EC4C1E34DF34589826C49375EF14FB3";//商户平台的key值//主账号
//	public static final String APPSECRET = "de3ff5c9eaf5859d0d6d5250d39eed9d";//主账号
//	public static final String APPID = "wx95cdc7bd59759712";//主账号
//	public static final String MCH_ID ="1444842502";//商户号ID 主账号
//	public static final String PATH = "http://weixinorder.maxburger.com.cn/wechatpro";//主账号
//	public static final String SSL_PATH="D:/apiclientCert/apiclient_cert.p12"; //退款使用的证书地址
//	public static final String IP="116.255.224.3"; //本地ip
	
// 牛肉饭
//	public static final String WECHAT_FOR_SIGNATURE = "http://niuroufanorder.maxburger.com.cn/wechatpro/html/phoneModelOne/orderList.html?adminId=";//主账号
//	public static final String KEY  = "06020B52B1BA4AE4AF2A9838463A51CE";//商户平台的key值//主账号
//	public static final String APPSECRET = "e18463ffacc776c1163d77e33f6b202b";//主账号
//	public static final String APPID = "wx4885931d45bc30e2";//主账号
//	public static final String MCH_ID ="1493476902";//商户号ID 主账号
//	public static final String PATH = "http://niuroufanorder.maxburger.com.cn/wechatpro";//主账号
//	public static final String SSL_PATH="D:/apiclientCert_niuroufan/apiclient_cert.p12"; //退款使用的证书地址
//	public static final String IP="116.255.224.3"; //本地ip
	
	public static final String TOKEINVALID ="40001";
	public static final String STATE ="true";
	public static final String SUBSCRIBE = "1";//关注订阅号
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";//返回消息类型：文本
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";//返回消息类型：音乐
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";//返回消息类型：图文
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";//请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";//请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_LINK = "link";//请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";//请求消息类型：地理位置
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";//请求消息类型：音频
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";//请求消息类型：推送
	public static final String EVENT_TYPE_LOCATION = "LOCATION";//事件类型：地理位置
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";//事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";//事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_CLICK = "CLICK";//事件类型：CLICK(自定义菜单点击事件)
	public static final String EVENT_TYPE_CARD_PASS_CHECK = "card_pass_check";//事件类型：卡券通过审核
	public static final String EVENT_TYPE_CARD_NOT_PASS_CHECK = "card_not_pass_check";//事件类型：卡券未通过审核
	public static final String EVENT_TYPE_USER_GET_CARD = "user_get_card";//事件类型：卡券未通过审核用户领取卡券
	public static final String EVENT_TYPE_SUBMIT_MEMBERCARD_USER_INFO = "submit_membercard_user_info";//事件类型： 接收会员信息事件通知
	public static final String ADD_WRONG_MESSAGE = "该分组已存在，请更改用户名";
	public static final String UPDATE_WRONG_MESSAGE="该分组名称已存在，无法更新";
	
	public static final int WRONG = -9999;
	
	public static final String MONTH_TABLE="wechat_phone_message_log_";
	public static final String LOGIN_TABLE="wechat_admin_login_log_";
	public static final String SCORE_TABLE="m_wechat_login_score_log_";
	public static final String ORDER_TABLE="wechat_customer_order_log_";
	public static final String SHOP_TABLE="wechat_customer_order_shopping_log_";
	
	public static final String USER_IDENTITY = "0";//用户身份、未认证角色
	public static final String USER_STATE = "1";//用户类型为免密登录
	public static final String USER_STATEBYPASSWORD = "2";//用户类型为账号密码登录、角色为上线状态
	
	public static final String ADDRESS = "1";//地址默认为使用中
	
	public static final String JIBIE = "3";//菜单级别是3
	
	public static final String WRONG_MESSAGE = "失败";
	
	public static final String WECHAT_BUTTOM_MENU_SUCCESS = "0";
	public static final String FIRST_MENU = "0";//一级菜单
	public static final String ROLE_STATE = "0";//角色初始状态为0
	public static final String SCOLLOR_NUM = "0";//通知顺序为0
	public static final String PRODUCT = "0";//分发产品时，产品状态。产品数量。产品当前数量
	public static final String WECHAT_OPTION_STATE_NORMARL = "1";
	
	public static final String SHOP = "1";
	public static final String PACKAGE = "2";
	public static final String CHOSEPACKAGE = "3";
	
	public static final String[] IMG_TYPE={"jpg","png","jpeg","svg"};
	
	public static final String WECHAT_ROLE_STATE_CREATE = "0";
	public static final String ZERO = "0";
	public static final String YUMING = "z1714z2699.imwork.net";//域名
	
	public static final String[] TEST_KEY={"age","name"};
	public static final String[] TEST_RETURNMESSAGE={"年龄不能为空","姓名不能为空"};
	
	public static final String WECHAT_ERRORCODE_INVALID_CODE = "40029";//invalid code Code无效错误
	
	//添加收获地址
	public static final String[] ADD_ADDRESS={"deliveryConsignee","deliveryPhone"};
	public static final String[] ADD_ADDRESS_RETURNMESSAGE={"收件人姓名不能为空","收件人联系方式不能为空"};
	//删除收获地址、编辑收获地址、回显收货地址、设置默认地址
	public static final String[] ALL_ADDRESS={"id"};
	public static final String[] ALL_ADDRESS_RETURNMESSAGE={"id不能为空"};
	
	//添加通知不为空
	public static final String[] SCOLLOR_PIC_TEST={"scollor_pic_name","scollor_pic_path"};
	public static final String[] SCOLLOR_PIC_TEST_RETURNMESSAGE={"通告名称不能为空","图片ID不能为空"};
	//删除通知、编辑通知、通知详情、上线、下线、发布、取消发布、编辑展示顺序
	public static final String[] SCOLLOR_PIC={"id"};
	public static final String[] SCOLLOR_PIC_RETURNMESSAGE={"id不能为空"};
	
	
	//添加角色不能为空
	public static final String[] ROLE_TEST={"wechatRoleName","wechatRoleDesc","wechatRoleHeadPortrait"};
	public static final String[] ROLE_RETURNMESSAGE={"角色名称不能为空","角色描述不能为空","图片ID不能为空"};
	//删除角色、角色详情、提审、编辑角色
	public static final String[] ALL_ROLE={"id"};
	public static final String[] ALL_ROLE_RETURNMESSAGE={"id不能为空"};
	
	//添加菜单不能为空
	public static final String[] MENU_TEST={"name"};
	public static final String[] MENU_RETURNMESSAGE={"菜单名称不能为空"};
	//删除菜单、编辑菜单、菜单详情（回显）、查询二级菜单
	public static final String[] ALL_MENU={"id"};
	public static final String[] ALL_MENU_RETURNMESSAGE={"id不能为空"};
	
	//注册
	public static final String[] REGIST={"adminNo","password1","password2","sendPhoneMessage"};
	public static final String[] REGIST_RETURNMESSAGE={"手机号不能为空","密码不能为空","密码不能为空","验证码不能为空"};
	//登录
	public static final String[] LOGIN={"adminNo","passwordbefore"};
	public static final String[] LOGIN_RETURNMESSAGE={"手机号不能为空","密码不能为空"};
	//添加用户
	public static final String[] INSERT_USER={"adminNo","password"};
	public static final String[] INSERT_USER_RETURNMESSAGE={"账号不能为空","密码不能为空"};
	//删除用户、回显用户、用户详情（id不能为空）
	public static final String[] USER={"id"};
	public static final String[] USER_RETURNMESSAGE={"id不能为空"};
	//修改密码、忘记密码
	public static final String[] PASSWORD={"id","newPassword1","newPassword2"};
	public static final String[] PASSWORD_RETURNMESSAGE={"id不能为空","密码不能为空","密码不能为空"};
	//用户认证不通过、通过
	public static final String[] USERONLINE={"id","adminReason"};
	public static final String[] USERONLINE_RETURNMESSAGE={"id不能为空","审核原因不能为空"};
	//编辑餐厅信息、餐厅详情
	public static final String[] STORE={"adminNo"};
	public static final String[] STORE_RETURNMESSAGE={"账号不能为空"};
	
	//添加权限
	public static final String[] ADD_POWER={"name","url"};
	public static final String[] ADD_POWER_RETURNMESSAGE={"权限名称不能为空","权限链接不能为空"};
	
	//APP基本信息
	public static final String[] WECHATAPP_KEY = {"appId","appSecret"};
	public static final String[] WECHATAPP_RETURNMESSAGE= {"appId的值不能为空","appSecret的值不能为空"};
	
	//自定义菜单
	public static final String[] BUTTOMMENU_KEY = {"menuName","rebackContext"};
	public static final String[] BUTTOMMENU_RETURNMESSAGE = {"菜单名称不能为空","回复内容不能为空"};
	
	//关键字
	public static final String[] WECHATKEY_KEY = {"wechatKey","context"};
	public static final String[] WECHATKEY_RETURNMESSAGE = {"关键字不能为空","回复内容不能为空"};
	
	//创建会员卡
	public static final String[] MEMBERSHIP_KEY = {"background_pic_url","prerogative","discount","logo_url","brand_name","title","color","notice","service_phone","use_all_locations","description","quantity","image_url","text","business_service","cost_money_unit","increase_bonus","cost_bonus_unit","reduce_money","least_money_to_use_bonus","max_reduce_bonus"};
	public static final String[] MEMBERSHIP_RETURNMESSAGE = {"卡卷背景不能为空","特权说明不能为空","卡券折扣不能为空","商户logo不能为空","商户名称不能为空","卡券名称不能为空","卡券颜色不能为空","使用提醒不能为空","客服电话不能为空","是否支持全部门店不能为空","使用说明不能为空","卡券数量不能为空","简介图片不能为空","简介说明不能为空","商家服务类型不能为空","消费金额不能为空","增加积分不能为空","积分抵扣不能为空","抵扣金额不能为空","抵扣条件不能为空","使用上限不能为空"};
	
	//会员卡修改
	public static final String[] UPDATEMEMBERSHIP_KEY = {"background_pic_url","prerogative","discount","logo_url","title","color","notice","service_phone","use_all_locations","description","cost_money_unit","increase_bonus","cost_bonus_unit","reduce_money","least_money_to_use_bonus","max_reduce_bonus","card_id"};
	public static final String[] UPDATEMEMBERSHIP_RETURNMESSAGE = {"卡卷背景不能为空","特权说明不能为空","卡券折扣不能为空","商户logo不能为空","商户名称不能为空","卡券名称不能为空","卡券颜色不能为空","使用提醒不能为空","客服电话不能为空","是否支持全部门店不能为空","使用说明不能为空","消费金额不能为空","增加积分不能为空","积分抵扣不能为空","抵扣金额不能为空","抵扣条件不能为空","使用上限不能为空","会员卡编号不能为空"};

    //会员卡
	public static final String[] AMEMBERSHIP_KEY = {"card_id"};
	public static final String[] AMEMBERSHIP_RETURNMESSAGE = {"会员卡编号不能为空"};
}

