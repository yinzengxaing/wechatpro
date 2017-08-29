
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

var orderNumber =""; //订单编号
var adminId = ""; //商家id
var orderId =""; // orderId;

var orderItems = null;
var addressId = null; 
var isAddress = 0;


$(function(e){
	receiveData();
	dataInit();
	getTime();
});

//回显数据
function dataInit(){
	productInit();
/*	if (addressId == null){
		addressId = $.req("addressId");
	}
	//判断使用默认地址还是使用用户选择的地址
	if (addressId == null){
		address();
	}else {
		addressById();
	}*/
}
//默认地址的回显
function address(){
	AjaxPostUtil.request({url:path+"/gateway/MWechatCustomerOrderController/getAddress",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){ //获取得到用户的默认收货地址
			addressId = json.bean.addressId;
			$("#username").html("收货人:"+json.bean.username);
			$("#phone").html(json.bean.phone);
			$("#addressInfo").html("收货地址:"+json.bean.addressInfo);
			isAddress=0;
			productInit();
		}else{//当前用户没有默认的收货地址 跳转到添加收货地址的页面
			 $("#addressInof").html("您当前还没有默认的收货地址~");
			 productInit();
			 isAddress=1;
		}
	}});
}

//根据地址id获取地址
function addressById (){
	var params = {
			id : addressId
	}
	AjaxPostUtil.request({url:path+"/gateway/MWechatCustomerOrderController/getAddressById",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){ //获取得到用户的默认收货地址
			$("#username").html("收货人:"+json.bean.username);
			$("#phone").html(json.bean.phone);
			$("#addressInfo").html("收货地址:"+json.bean.addressInfo);
			isAddress=0;
			productInit();
		}else{//当前用户没有默认的收货地址 跳转到添加收货地址的页面
			 $("#addressInof").html("您当前还没有默认的收货地址~");
			 productInit();
			 isAddress=1;
		}
	}});
}

//商品信息的回显
function productInit(){
	if(orderItems == null ){
		orderItems = $.req("orderItems");
	}
	var params= {
		orderItems:orderItems
	}
	//显示订单中的商品信息
	AjaxPostUtil.request({url:path+"/gateway/MWechatCustomerOrderController/getProductInfo",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
			var source = $("#orderBean").html();
			var template = Handlebars.compile(source);
			$("#orderDiv").html($("#orderDiv").html() + template(json));
			TotalPrice();
		}else{
			 qiao.bs.msg({msg:"数据请求失败,请重试~",type:'danger'});
		}
	}});
	eventInit();
}

function eventInit(){
	$('body').on('click', '.aui-tab>ul>li', function(e){
		$(this).addClass('aui-hit').siblings().removeClass('aui-hit');
		$('.aui-panes>div:eq('+$(this).index()+')').show().siblings().hide();
	});
	//--
	$('body').on('click', '.minus', function(e){
		var t = $(this).parent().find('.num');
		t.text(parseInt(t.text()) - 1);
		if (t.text() <= 1) {
			t.text(1);
		}
		TotalPrice();
	});
	
	//++
	$('body').on('click', '.plus', function(e){
		var t = $(this).parent().find('.num');
		t.text(parseInt(t.text()) + 1);
		if (t.text() <= 1) {
			t.text(1);
		}
		TotalPrice();
	});
	
	//用餐类型下拉框变化事件
	$('body').on('change', '#selectItems', function(e){
		if ($("#selectItems").val() == 1){
			$("#addressDiv").show();
			$("#eatTimeDiv").hide();
		}else if ($("#selectItems").val() == 2){
			$("#addressDiv").hide();
			$("#eatTimeDiv").hide();
		}else if ($("#selectItems").val() == 3){
			$("#addressDiv").hide();
			$("#eatTimeDiv").show();
		}
	});
	
	//结算按钮点击事件
	$('body').on('click', '#buyNow', function(e){
		//点击一次使按钮失效
		//$("#buyNow").attr("disabled", true); 
		if (!isNull(orderId)){
			location.href="wait.html?orderType=3";
		}else{
		var orderItems = "";
		var orderType = "";
		var eatTime="";
		$(".shop-group-item").each(function() { // 循环每个店铺
			var items = ""; // 店铺总价
			$(".productLi").each(function(){//循环每个产品
				var adminId = $(this).find(".shop-info-text").attr("adminId"); //商店id
				var productType = $(this).find(".shop-info-text").attr("wechatCommodityType"); //产品类型
				var productId = $(this).find(".shop-info-text").attr("productID"); //产品id
				var cardId = $(this).find(".shop-info-text").attr("cardId"); //产品id
				var count = $(this).find(".num").html() // 得到商品的数量
				items=adminId+"-"+productType+"-"+productId+"-"+count+"-"+cardId;
				orderItems+=items+",";
			});
		});
		if ($("#selectItems").val() == 1){
			orderType=$("#selectItems").val();
			eatTime=addressId;
		}else if ($("#selectItems").val() == 2){
			eatTime="";
			orderType=$("#selectItems").val();
		}else if ($("#selectItems").val() == 3){
			orderType=$("#selectItems").val();
			var timeDay = $('#timeDay').val();
			var h = $('#h').val();
			var min = $('#min').val();
			eatTime=timeDay+": "+h+" 点 "+min+" 分"
		}
		var params = {
				orderItems:orderItems,
				orderType:orderType,
				eatTime:eatTime
		}
		AjaxPostUtil.request({url:path+"/gateway/MWechatCustomerOrderController/addOrder",params:params,type:'json',callback:function(jsonPa){
			if (jsonPa.returnCode == 0){
				if(jsonPa.bean!=null){
					paySign = jsonPa.bean.paySign;
					appId = jsonPa.bean.appid;
					timeStamp = jsonPa.bean.timestamp;
					nonceStr = jsonPa.bean.nonceStr;
					paypackage = jsonPa.bean.paypackage;
					signature = jsonPa.bean.signature;
					
					orderNumber = jsonPa.bean.orderNumber;
					adminId = jsonPa.bean.adminId;
					orderId = jsonPa.bean.orderId;
					
					payEvent(orderNumber,adminId,orderId);
				} else{
					qiao.bs.msg({msg:"支付过程出现错误！",type:'danger'});	
				}
				
			}else{
				qiao.bs.msg({msg:jsonPa.returnMessage,type:'danger'});
			}
			
		}});
	}
	});
	
	//选择收货的地址按钮点击事件
	$('body').on('click', '#moreAddr', function(e){
		if (isAddress == 0){
			location.href="addressList.html?orderItems="+orderItems;
		}else{
			location.href="deliveryAddressAdd.html?orderItems="+orderItems+"&returnOrder=0";
		}
	});
}
//计算
function TotalPrice() {
	var allprice = 0.00; // 总价
	$(".shop-group-item").each(function() { // 循环每个店铺
		var oprice = 0.00; // 店铺总价
		$(".productLi").each(function(){//循环每个产品
			var num = 	$(this).find(".num").html() // 得到商品的数量
			var price = $(this).find(".price").html(); // 得到单价
			var total = price * num; // 计算单个商品的总价
			oprice += total; // 计算该店铺的总价
		});
		allprice += oprice; // 计算所有店铺的总价
		$(this).find(".ShopTotal").text(oprice.toFixed(2));
	});
	$("#AllTotal").text(allprice.toFixed(2)); //输出全部总价
}
//自定义时间显示
function getTime(){
	//添加时
	for(var i=1;i<=12;i++){
		if (i>=1 && i<=9)
			i="0"+i;
		$('#h').append("<option value='"+i+"'>"+i+"</option>");
	}
	//添加分
	for(var i=0;i<=59;i++){
		if (i>=1 && i<=9)
			i="0"+i;
		$('#min').append("<option value='"+i+"'>"+i+"</option>");
	}
}
//支付事件
function payEvent(orderNumber,adminId,orderId){
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