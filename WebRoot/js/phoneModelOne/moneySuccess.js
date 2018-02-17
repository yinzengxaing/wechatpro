
var orderId="";
var orderNumber="";

$(function(e){
	dataInit();
	receiveData();
});

function dataInit(){
	if (orderId == "" || orderNumber == "" ){
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
			$("#phoneNumber").html(json.bean.phoneNumber);
			$("#wetherPaymentTime").html(json.bean.wetherPaymentTime);
			$("#orderDesc").html(json.bean.orderDesc);
		}});
	eventInit();
}

//事件
function eventInit(){
	
	//返回首页
	$('body').on('click', "#returnIndex", function(e){
		location.href ="index.html";
	});
	
	//查看订单详情
	$('body').on('click', "#orderDetails", function(e){
		location.href = "orderDetails.html?orderId="+orderId+"&orderNumber="+orderNumber;
	});
}