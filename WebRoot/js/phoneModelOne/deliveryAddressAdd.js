var orderItems = "";
var returnOrder = null;
$(function(e){
	receiveData();
	dataInit();
});


function dataInit(){
	orderItems = $.req("orderItems");
	returnOrder = $.req("returnOrder");
	_init_area();
	eventInit();
}

function eventInit(){
	$('body').on('click', '#sureAddress', function(e){
		var params = {
				deliveryPhone:$("#mobile").val(),	
				deliveryConsignee:$("#realname").val(),
				deliveryAddressProvince:$("#s_province").val(),
				deliveryAddressCity:$("#s_city").val(),
				deliveryAddressCount:$("#s_county").val(),
				deliveryAddressSpecific:$("#address").val(),
		};
		if(isNull($("#realname").val())){
			qiao.bs.msg({msg:"收件人不能为空!",type:'danger'});
			return false;
		}else if(isNull($("#mobile").val())){
			qiao.bs.msg({msg:"手机号不能为空!",type:'danger'});
			return false;
		}else if(checkMobile($("#mobile").val()) == false){
			qiao.bs.msg({msg:"手机号码不正确!",type:'danger'});
			return false;
		}else if($("#mobile").val().length != 11){
			qiao.bs.msg({msg:"手机号长度为11位!",type:'danger'});
			return false;
		}else{
			AjaxPostUtil.request({url:path+"/gateway/MWechatDeliveryAddressController/insertDeliveryAddress",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					if (returnOrder == 1){
						qiao.bs.msg({msg:"添加成功",type:'success'});
						location.href = 'deliveryAddress.html';
					}else{
						location.href = 'orderList.html?orderItems='+orderItems;
					}
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}
	});
	
	$('body').on('click', '#cancel', function(e){
		if (returnOrder == 1){
		location.href = 'deliveryAddress.html';
		}else{
			location.href = 'orderList.html?orderItems='+orderItems;	
		}
	});
}