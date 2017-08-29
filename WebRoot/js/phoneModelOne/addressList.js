var orderItems = "";
$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	if (orderItems == ""){
		orderItems = $.req("orderItems");
	}
	addressList();
	eventInit();
}

function addressList(){
	AjaxPostUtil.request({url:path+"/gateway/MWechatDeliveryAddressController/selectByDeliveryUserId",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			Handlebars.registerHelper("compare1", function(v1,v2,v3,v4,options){
				if(v1 != null){
					return v1+v2+v3+v4;
				}else{
					return ;
				}
			});
			Handlebars.registerHelper("compare2", function(v1,options){
				if (v1==1){
					return "choossAeddressColor_RED";
				}else{
					return "";
				}
			});
			var source = $("#roleListBean").html();  
		    var template = Handlebars.compile(source);
		    $("#roleListDiv").html(template(json));
		}else{
			location.href = 'sessionNull.html';
		}
	}});
	
}

function eventInit(){
	$('body').on('click',".addressItems",function(e){
		var rowId = $(this).attr("rowId");
		location.href = 'orderList.html?orderItems='+orderItems+"&addressId="+rowId;
	});
}