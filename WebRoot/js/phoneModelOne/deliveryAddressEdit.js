var id = null;
$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	_init_area();
	id = $.req("id");
	var params = {
		id:id,
	};
		AjaxPostUtil.request({url:path+"/gateway/MWechatDeliveryAddressController/selectById",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				$("#realname").val(json.bean.deliveryConsignee);
				$("#mobile").val(json.bean.deliveryPhone);
			    $("#s_province").val(json.bean.deliveryAddressProvince);
			    change(1);
			    $("#s_city").val(json.bean.deliveryAddressCity);
			    change(2);
			    $("#s_county").val(json.bean.deliveryAddressCount);
			    $("#address").val(json.bean.deliveryAddressSpecific);
				eventInit();
			}else{
				location.href = 'sessionNull.html';
			}
	    }});
}

function eventInit(){
	$('body').on('click','#sureAddress',function(e){
		var params = {
			id:id,
			deliveryAddressProvince:$('#s_province').val(),
			deliveryAddressCity:$('#s_city').val(),
			deliveryAddressCount:$('#s_county').val(),
			deliveryAddressSpecific:$('#address').val(),
		}
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
			AjaxPostUtil.request({url:path+"/gateway/MWechatDeliveryAddressController/updateAddress",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
			        qiao.bs.msg({msg:"修改成功",type:'success'});
			        location.href="deliveryAddress.html";
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}
	});
	
	$('body').on('click', '#cancel', function(e){
		location.href="deliveryAddress.html";
	});
}