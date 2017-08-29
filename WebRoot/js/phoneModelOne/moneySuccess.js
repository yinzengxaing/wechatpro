
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