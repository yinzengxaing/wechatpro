var orderId="";
var orderNumber="";

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	if (orderId == "" || orderNumber == ""){
		orderId = $.req("orderId");
		orderNumber = $.req("orderNumber");
	}
	var params = {
			orderId:orderId,				
			orderNumber:orderNumber
		};			
		AjaxPostUtil.request({url:path+"/gateway/MWechatCustomerOrderController/getOrderDetails",params:params,type:'json',callback:function(json){
			if (isNull(json.bean.username) && isNull(json.bean.phone) && isNull(json.bean.addressInfo)){
				$(".orderingAdress").hide();
			}else{
				$("#username").html(json.bean.username + "      "+json.bean.phone);
				$("#addressInfo").html(json.bean.addressInfo);
			}
			//填充数据
			$("#dayNo").html(json.bean.dayNo);
			var orderType = json.bean.orderType;
			if (orderType == 1){
				$("#orderType").html("立即取餐");
			}else if (orderType == 2){
				$("#orderType").html("稍后取餐("+json.bean.orderEatTime+")");
			}else if (orderType == 3){
				$("#orderType").html("立即外带");
			}else if (orderType == 4){
				$("#orderType").html("稍后外带("+json.bean.orderEatTime+")");
			}else if (orderType == 5){
				$("#orderType").html("外卖");
			}
			$("#orderNumber").html(json.bean.orderNumber);
			$("#creatTime").html(json.bean.createTime);
			$("#shopName").html(json.bean.adminShopName);
			$("#phoneNumber").html(json.bean.phoneNumber);
			$("#wetherPaymentTime").html(json.bean.wetherPaymentTime);
			$("#orderDesc").html(json.bean.orderDesc);
			
			$("#price").html(json.bean.orderPrice+"元");
			$("#reailPrice").html(json.bean.orderPrice+"元");
			$("#totalPrice").html(json.bean.orderPrice+"元");
			$("#orderNumber").html(json.bean.orderNumber);
			$("#creatTime").html(json.bean.createTime);
			$("#shopName").html(json.bean.adminShopName);
			$("#shopName").attr("adminId",json.bean.adminId);
			var s =  json.bean.orderNumber;
			$("#dayNumber").html(s.substring(20,26));
			if(json.returnCode==0){
				//对图片进行修饰
				Handlebars.registerHelper("productLogo",function(v1,options){
					 return path+"/"+v1;
				});
				var source = $("#productBean").html();
				var template = Handlebars.compile(source);
				$("#orderDetailUl").html(template(json));
			}else{
				
			}
		}});
	eventInit();
}

function eventInit(){
	
}