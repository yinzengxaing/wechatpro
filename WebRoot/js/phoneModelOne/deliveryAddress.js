$(function(e){
	dataInit();
});

function dataInit(){
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
	$('body').on('click','.editAd',function(e){
		var rowId = $(this).attr("rowId");
		location.href="deliveryAddressEdit.html?id="+rowId;
	});
	$('body').on('click','.delAd',function(e){
		var rowId = $(this).attr("rowId");
		qiao.bs.confirm("确定删除该地址吗？",function(){
			var params = {
				id:rowId,
			};			
			AjaxPostUtil.request({url:path+"/gateway/MWechatDeliveryAddressController/deleteById",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:"删除成功",type:'success'});
					setTimeout(addressList,1000);//一秒后刷新页面
				}else{
					qiao.bs.msg({msg:"删除失败",type:"danger"});
				}
			}});
		},function(){});
	});
	$('body').on('click',".morenAddress",function(e){
		var rowId = $(this).attr("rowId");
		var _this = $(this);
		var params = {
				id:rowId,
		};
		AjaxPostUtil.request({url:path+"/gateway/MWechatDeliveryAddressController/updateUse",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
				$(".morenAddress").removeClass("choossAeddressColor_RED");
				_this.addClass("choossAeddressColor_RED");
				qiao.bs.msg({msg:"设置默认地址成功",type:'success'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
}