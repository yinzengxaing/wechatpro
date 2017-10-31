var orderType = "";

var wetherPayment = "";
var wetherMake = "";

$(function(e){
	dataInit();
	receiveData();
});

function dataInit(){
	//判断进入时用户需要查看的订单类型
	if (orderType == ""){
		orderType = $.req("orderType");
	}
	//显示全部的订单
	if(orderType == 1){ 
	$("#allOrder").addClass('aui-hit').siblings().removeClass('aui-hit');
	$('.aui-panes>div:eq('+$("#allOrder").index()+')').show().siblings().hide();
	wetherMake="";
	wetherPayment="";
	allOrder();
	}//显示待发货
	else if (orderType == 2){
	$("#sendOrder").addClass('aui-hit').siblings().removeClass('aui-hit');
	$('.aui-panes>div:eq('+$("#sendOrder").index()+')').show().siblings().hide();
	wetherMake=0;
	wetherPayment=1;
	allOrder();
	}//显示已完成
	else if (orderType == 3){
	$("#getOrder").addClass('aui-hit').siblings().removeClass('aui-hit');
	$('.aui-panes>div:eq('+$("#getOrder").index()+')').show().siblings().hide();
	wetherMake=1;
	wetherPayment=1;
	allOrder();
	}
	eventInit();
}

function eventInit(){
	//订单按钮点击
	$('body').on('click',".aui-tab ul li",function(e){
		orderType = $(this).attr("orderType");
		if (orderType == 1){ //全部订单
			wetherMake="";
			wetherPayment="";
			allOrder();
		}else if (orderType == 2){ //待发货订单
			wetherMake=0;
			wetherPayment=1;
			allOrder();
		}else if (orderType == 3){ //已完成订单
			wetherMake=1;
			wetherPayment=1;
			allOrder();
		}
		$(this).addClass('aui-hit').siblings().removeClass('aui-hit');
		$('.aui-panes>div:eq('+$(this).index()+')').show().siblings().hide();
	});
	
	//查看按钮点击事件
	$('body').on('click',".lookOrder",function(e){
		var orderId = $(this).parent().attr("orderId");
		var orderNumber =  $(this).parent().attr("orderNumber");
		location.href = "orderDetails.html?orderId="+orderId+"&orderNumber="+orderNumber;
	});
	
	//删除按钮点击事件
	$('body').on('click',".deleteOrder",function(e){
		var orderId = $(this).parent().attr("orderId");
		var orderNumber =  $(this).parent().attr("orderNumber");
		qiao.bs.confirm("确定删除该订单吗？",function(){
			var params = {
				orderId:orderId,				
				orderNumber:orderNumber
			};			
			AjaxPostUtil.request({url:path+"/gateway/MWechatCustomerOrderController/deleteOrder",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:"删除成功",type:'success'});
					setTimeout(allOrder,1000);//一秒后刷新页面
				}else{
					qiao.bs.msg({msg:"删除失败",type:"danger"});
				}
			}});
		},function(){});
	});
	
	//退单按钮点击事件
	$('body').on('click',".outRefund",function(e){
		$("#refundDesc").val("");
		$('#myModal4').modal('show');
		var orderNumber =  $(this).parent().attr("orderNumber");
		$('body').on('click',"#sure",function(e){
			if(isNull($("#refundDesc").val())){
				qiao.bs.msg({msg:"请填写您的退单原因",type:'danger'});
				return false;
			}
	    	var params = {
    			refundDesc:$("#refundDesc").val(),
	    		orderNumber:orderNumber
	    	};
	    	AjaxPostUtil.request({url:path+"/gateway/WechatOutRefundController/applyForRefund",params:params,type:'json',callback:function(json){
	    		if(json.returnCode==0){
	    			$('#myModal4').modal('hide');
	    			qiao.bs.msg({msg:"您的退单请求已经发送给商家，请耐心等待处理结果",type:'success'});
	    			if (orderType == 1){ //全部订单
	    				wetherMake="";
	    				wetherPayment="";
	    				allOrder();
	    			}else if (orderType == 2){ //待发货订单
	    				wetherMake=0;
	    				wetherPayment=1;
	    				allOrder();
	    			}else if (orderType == 3){ //已完成订单
	    				wetherMake=1;
	    				wetherPayment=1;
	    				allOrder();
	    			}
	    		}else{
	    			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
	    		}
	    	}});
		});
	});
}

//查看全部订单
function allOrder(){
	var params = {
			wetherPayment:wetherPayment,
			wetherMake:wetherMake
	}
	
	AjaxPostUtil.request({url:path+"/gateway/MWechatCustomerOrderController/getAllOrder",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
			//对图片进行修饰
			Handlebars.registerHelper("orderLogo",function(v1,options){
				 return path+"/"+v1;
			});
			//对订单状态进行修饰
			Handlebars.registerHelper("compare1",function(v1,v2,options){
				 if (v1 == 0 && v2 == 0 ){
					 return "待付款";
				 }else if (v1==1 && v2==0) {
					 return "待发货";
				 }else if (v1==1 && v2==1){
					 return "已完成";
				 }else if (v1 ==2){
					 return "退款中";
				 }else if (v1== 3){
					 return "退款成功";
				 }else if (v1==4){
					 return "拒绝退款";
				 }
			});
			//对订单那类型进行修饰
			Handlebars.registerHelper("orderType",function(v1,options){
				 if (v1 == 1){
					 return "立即堂食";
				 }else if (v1 == 2){
					 return "稍后堂食";
				 }else if(v1 == 3){
					 return "立即外带";
				 }else if(v1 == 4){
					 return "稍后外带";
				 }else if (v1 == 5){
					 return "外卖";
				 }
			});
			//对订单按钮的显示状态进行修饰
			Handlebars.registerHelper("compare2",function(v1,v2,options){
				 if (v1 == 0 && v2==0 ){
					 return " <div class='sureshop2 deleteOrder' style='cursor:pointer;'><h5>删除订单</h5></div>"+
			 		 "<div class='sureshop2 payOrder' style='cursor:pointer;'><h5>支付订单</h5></div>"+
			 		"<div class='sureshop2 outRefund' style='cursor:pointer;'><h5>申请退单</h5></div>"+
			 		 "<div class='sureshop2 lookOrder' style='cursor:pointer;'><h5>查看订单</h5></div>";
				 }else if (v1==1 && v2==0) {
					 return "<div class='sureshop2 lookOrder' style='cursor:pointer;'><h5>查看订单</h5></div>"+
					 		"<div class='sureshop2 outRefund' style='cursor:pointer;'><h5>申请退单</h5></div>";
				 }else if (v1==1 && v2 ==1){
					 return  "<div class='sureshop2 lookOrder' style='cursor:pointer;'><h5>查看订单</h5></div>"+
					 "<div class='sureshop2 outRefund' style='cursor:pointer;'><h5>申请退单</h5></div>";
				 }else if (v1==2){	
					 return  "<div class='sureshop2 lookOrder' style='cursor:pointer;'><h5>查看订单</h5></div>";
				 }else if (v1==3){
					 return  "<div class='sureshop2 lookOrder' style='cursor:pointer;'><h5>查看订单</h5></div>";
					
				 }else if (v1==4){
					 return  "<div class='sureshop2 lookOrder' style='cursor:pointer;'><h5>查看订单</h5></div>";
					
				 }
			});
			
			var source = $("#allOrderBean").html();
			var template = Handlebars.compile(source);
			$("#orderDiv").html(template(json));
		}else{
			$("#orderDiv").html("您当前没有该类订单~");
		}
	}});
	
}
