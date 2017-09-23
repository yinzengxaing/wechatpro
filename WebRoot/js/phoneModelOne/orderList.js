//支付使用的常量
var code = "";
var appId="";  
var timeStamp="";  
var nonceStr="";  
var prepay_id="";//之前参数名叫package,对应api接口，因为package是关键字，被坑了一次  
var paySign="";
var ip = "";
var signature = "";
var paypackage = "";

//订单类型状态
var liji = "1";
var shaowan = "0";
var tangshi = "1";
var waidai = "0";

//初始化数据所用的参数
var adminId = "";
var totalCount="";
var totalPrice ="";
var catState = 0;

//支付后返回的参数
var orderNumber =""; //订单编号
var orderId =""; // 订单id

//生成订单必须的参数
var orderType = "";
var eatTime = "";
var phoneNumber = "";

//时间参数
var hh  = "";
var ss = "";

$(function(e){
	receiveData();
	dataInit();
	eventInit();
	//getTime();
});

//数据的初始化
function dataInit(){
	var calendartime = new lCalendar();
	calendartime.init({
		'trigger': '#geteateTime',
		'type': 'time'
	});
	if (adminId == ""){
		adminId = $.req("adminId");
	}
	getCartInfo();
}

//事件的触发
function eventInit(){
	//时间选择下拉框选择变化事件
	$('body').on('change', '#h', function(e){
		var h = $('#h').val();
		var d = new Date();
		 hh = d.getHours();
		 mm = d.getMinutes();
		 if (h != hh){
			 $('#min').empty();
			for(var i=0;i<=59;i=i*1+5){
				if (i>=0 && i<=9)
					i="0"+i;
				$('#min').append("<option value='"+i+"'>"+i+"</option>");
			}
		 }else{
			 $('#min').empty();
			 setMin();
		 }
	});
	
	//订单类型点击事件
	$('body').on('click', '#liji', function(e){
		$('#eatTimeDiv').hide();
		if(liji == 1){
			return;
		}else{
			liji = 1;
			shaowan = 0;
			$("#li").attr("class","triangle-bottomright");
			$("#shao").attr("class"," ");
		}
	});
	$('body').on('click', '#shaowan', function(e){
		/*setNowTime();*/
		$('#eatTimeDiv').show();
		if(shaowan == 1){
			return;
		}else{
			liji = 0;
			shaowan = 1;
			$("#li").attr("class","");
			$("#shao").attr("class","triangle-bottomright");
		}
	});
	$('body').on('click', '#tangshi', function(e){
		if(tangshi == 1){
			return;
		}else{
			tangshi = 1;
			waidai = 0;
			$("#tang").attr("class","triangle-bottomright");
			$("#wai").attr("class","");
		}
	});
	$('body').on('click', '#waidai', function(e){
		if(waidai == 1){
			return;
		}else{
			tangshi = 0;
			waidai = 1;
			$("#tang").attr("class","");
			$("#wai").attr("class","triangle-bottomright");
		}
	});
	
	//结算按钮点击事件
	$('body').on('click', '#clearing', function(e){
		//判断订单类型
		if(liji == 1 && tangshi == 1){
			orderType = 1;//立即堂食
			eatTime="";
		}else if(liji == 1 && waidai == 1){
			eatTime="";
			orderType = 3;//立即外带
		}else if(shaowan == 1 && tangshi == 1){
			orderType = 2;//稍晚堂食
			//eatTime = $('#h').val()+"点"+$('#min').val()+"分";
			var t = $('#geteateTime').val();
			if (t == ""){
				qiao.bs.msg({msg:"取餐时间不能为空",type:'danger'});
				return ;
			}else {
				eatTime = t.substring(7,14);
			}
		}else if(shaowan == 1 && waidai == 1){
			orderType = 4;//稍晚外带
			var t = $('#geteateTime').val();
			if (t == ""){
				qiao.bs.msg({msg:"取餐时间不能为空",type:'danger'});
				return ;
			}else {
				eatTime = t.substring(7,14);
			}
			//eatTime = $('#h').val()+"点"+$('#min').val()+"分";
		}
		//验证手机号
		if(isNull($("#phoneNumber").val())){
			qiao.bs.msg({msg:"联系方式不能为空",type:'danger'});	
			return;
		}else if(!checkMobile( $("#phoneNumber").val() ) && !checknum($("#phoneNumber").val()) && !checkPhone($("#phoneNumber").val()) ){
			qiao.bs.msg({msg:"联系方式不合格",type:'danger'});	
			return;
		}
		//获取取餐时间
		if(shaowan == 1 && eatTime == ""){
			qiao.bs.msg({msg:"请选择取餐时间",type:'danger'});	
			return;
		}
		var params = {
				orderType:orderType,
				phoneNumber:$("#phoneNumber").val(),
				eatTime:eatTime,
				adminId:adminId,
				remark:$("#remark").val()
		}
		AjaxPostUtil.request({url:path+"/gateway/MWechatCustomerOrderController/addOrder",params:params,type:'json',callback:function(jsonPa){
			if (jsonPa.returnCode == 0){
				if(jsonPa.bean!=null){
					paySign = jsonPa.bean.paySign;
					appId = jsonPa.bean.appid;
					timeStamp = jsonPa.bean.timestamp;
					nonceStr = jsonPa.bean.noncestr;
					paypackage = jsonPa.bean.paypackage;
					signature = jsonPa.bean.signature;
					
					orderNumber = jsonPa.bean.orderNumber;
					adminId = jsonPa.bean.adminId;
					orderId = jsonPa.bean.orderId;
					
					payEvent();
				} else{
					qiao.bs.msg({msg:"支付过程出现错误！",type:'danger'});	
				}
				
			}else{
				qiao.bs.msg({msg:jsonPa.returnMessage,type:'danger'});
			}
			
		}});
		
	});
	
	//购物车详情事件
	$('body').on('click', '#getCartProduct', function(e){
		if(catState == 0){
			catState = 1 ;
			getCartInfo();
			$('#catProductDiv').show();
			$('#wrap').show();
		}else if (catState == 1 ){
			catState = 0;
			$('#catProductDiv').hide();
			$('#wrap').hide();
		}
	});
	
	//购物车内＋号 事件
	$('body').on('click', '.addInCart', function(e){
		showMask();
		var s = $(this).parent();
		var wechatCommodity = $(this).attr("buyProductId");//获取商品id
		var wechatCommodityType = $(this).attr("productType");//获取商品的类型
		var wechatCommodityAdminId = $(this).attr("adminId");//获取商店的id
		 var params = {
				 wechatCommodity:wechatCommodity,
				 wechatCommodityType:wechatCommodityType,
				 wechatCommodityAdminId:wechatCommodityAdminId
		 }
		 AjaxPostUtil.request({url:path+"/gateway/MWechatShoppingCartController/addProduct",params:params,type:'json',callback:function(json){
			 if(json.returnCode==0){
				 getCartInfo();
			 }
			 hideMask();
		 }});
	});
	//购物车内 －号 事件
	$('body').on('click', '.redInCart', function(e){
		showMask();
		var s = $(this).parent();
		var wechatCommodity = $(this).attr("buyProductId");//获取商品id
		var wechatCommodityType = $(this).attr("productType");//获取商品的类型
		var wechatCommodityAdminId = $(this).attr("adminId");//获取商店的id
		 var params = {
				 wechatCommodity:wechatCommodity,
				 wechatCommodityType:wechatCommodityType,
				 wechatCommodityAdminId:wechatCommodityAdminId
		 }
		 AjaxPostUtil.request({url:path+"/gateway/MWechatShoppingCartController/deleteProductCount",params:params,type:'json',callback:function(json){
			 if(json.returnCode==0){
				 getCartInfo();
			 }
			hideMask();
		 }});
	});
	
}

//自定义时间显示
/*function getTime(){
	//添加时
	for(var i=9;i<=22;i++){
		if (i==9)
			i="0"+i;
		$('#h').append("<option value='"+i+"'>"+i+"</option>");
	}
	//添加分
	for(var i=0;i<=59;i=i*1+5){
		if (i>=0 && i<=9)
			i="0"+i;
		$('#min').append("<option value='"+i+"'>"+i+"</option>");
	}
}*/
//初始化时间
function setNowTime(){
	var d = new Date();
	 hh = d.getHours();
	 mm = d.getMinutes();
	 setH();
	 setMin();
}
//设置小时
function setH(){
	if(mm >=50){
		hh = hh +1;
	}
	//添加时
	if (hh >=9 && hh<= 22){
		for(var i=hh;i<=22;i++){
			if (i==9)
				i="0"+i;
			$('#h').append("<option value='"+i+"'>"+i+"</option>");
		}
	}else{
		alert("改时间段无法进行点餐")
	}
}
//设置分钟
function setMin(){
	if (mm < 50){
		for(var i=mm*1+10;i<=59;i=i*1+5){
			if (i>=0 && i<=9)
				i="0"+i;
			$('#min').append("<option value='"+i+"'>"+i+"</option>");
		}
	}else{
		for(var i=0;i<=59;i=i*1+5){
			if (i>=0 && i<=9)
				i="0"+i;
			$('#min').append("<option value='"+i+"'>"+i+"</option>");
		}
	}
}

//获取当前登录人购物车中的信息
function getCartInfo(){
	showMask();
	var params = {
			adminId : adminId
	}
	AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getCartDetail",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
			if (json.bean.totalCount != 0){ 
			//填充数据
			totalCount = json.bean.totalCount;
			totalPrice = json.bean.totalPrice;
			$('#AllTotal').html(totalPrice);	
			var source = $("#cartBenas").html();
			var template = Handlebars.compile(source);
			$("#catProductDiv").html(template(json));
			}else{
				totalCount = 0;
				totalPrice = 0.00;
				$("#catProductDiv").empty();
				$('#clearDiv').hide();
		 		$('#getCartProduct').hide();
				$('#wrap').hide();
			}
			//判断总金额，超过100给出温馨提示
			if(totalPrice >= 100){
				$(".wrap_phone_tel_bt").show();
			}else{
				$(".wrap_phone_tel_bt").hide();
			}
		}else{
			qiao.bs.msg({msg:"查询失败哦~",type:'danger'});
		}
		hideMask();
	}});
}


//支付事件
function payEvent(){
	 wx.config({  
	    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。  
	    appId: appId, // 必填，公众号的唯一标识  
	    timestamp: timeStamp, // 必填，生成签名的时间戳  
	    nonceStr: nonceStr, // 必填，生成签名的随机串  
	    signature: signature,// 必填，签名，见附录1  
	    jsApiList: ['chooseWXPay','onMenuShareTimeline','onMenuShareAppMessage','showOptionMenu'] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2  
	}); 
	 wx.ready(function(){
		 wx.chooseWXPay({  
			timestamp: timeStamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符  
			nonceStr: nonceStr, // 支付签名随机串，不长于 32 位  
			package: paypackage, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）  
			signType: 'MD5', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'  
			paySign: paySign, // 支付签名  
			success:function(res){
				var myParams = {
						adminId:adminId,
						orderNumber:orderNumber
				}
				AjaxPostUtil.request({url:path+"/gateway/MWechatCustomerOrderController/updatePayState",params:myParams,type:'json',callback:function(json){
					if(json.returnCode == 0){
						location.href="moneySuccess.html?orderNumber="+orderNumber+"&orderId="+orderId;
					}else{
						alert("支付失败");
					}
				}});
			},
			error:function(res){
				alert(res);
			},
			cencel:function(res){
	            alert('cencel pay');
	        },
		});  
	 });
}

function showMask(){     
    $("#mask").css("height",$(document).height());     
    $("#mask").css("width",$(document).width());     
    $("#mask").show();     
}  
/**
 * 隐藏遮罩层
 */
function hideMask(){     
    $("#mask").hide();     
}  