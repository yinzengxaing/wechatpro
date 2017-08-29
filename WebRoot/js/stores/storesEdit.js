var adminNo = null;
var adminWorkXCoordinate = "";
var adminWorkYCoordinate = "";
var map = null;

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	if(adminNo==null){
		adminNo = $.req("adminNo");
	}
	var params = {
		adminNo:adminNo,
    };
	AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/selectShopXQ",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
		    $("#adminNo").val(json.bean.adminNo);
		    $("#adminShopName").val(json.bean.adminShopName);
		    $("#adminWorkPlace").val(json.bean.adminWorkPlace);
		    $("#adminKfPhone").val(json.bean.adminKfPhone);
		    $("#adminCharacteristic").val(json.bean.adminCharacteristic);
		    $("#adminKfBusinessHours").val(json.bean.adminKfBusinessHours);
		    $("#adminRecommend").val(json.bean.adminRecommend);
		    $("#adminWorkXCoordinate").val(json.bean.adminWorkXCoordinate);
		    $("#adminWorkYCoordinate").val(json.bean.adminWorkYCoordinate);
		    $("#adminShopCard").val(json.bean.adminShopCard);
			$("#adminShopKey").val(json.bean.adminShopKey);
		    map = new AMap.Map('container', {
				 resizeEnable: true,
		         zoom: 11,
		         center: [json.bean.adminWorkXCoordinate, json.bean.adminWorkYCoordinate]
		    });
			//加载插件
			AMap.service('AMap.Geocoder',function(){
				geocoder = new AMap.Geocoder({});
			});
			//在地图上进行标记
			var marker = new AMap.Marker({map:map,bubble:true});
			//通过地址获取经纬度
			var input = document.getElementById('adminWorkPlace');
			map.on('click',function(e){
	           marker.setPosition(e.lnglat);
	           geocoder.getAddress(e.lnglat,function(status,result){
					if(status=='complete'){
						input.value = result.regeocode.formattedAddress;
						var address = input.value;
						geocoder.getLocation(address,function(status,result){
							if(status=='complete'&&result.geocodes.length){
								marker.setPosition(result.geocodes[0].location);
								map.setCenter(marker.getPosition());
								$("#adminWorkXCoordinate").val(result.geocodes[0].location.lng);
								$("#adminWorkYCoordinate").val(result.geocodes[0].location.lat);
							}else{
							}
			            })
					}else{
					}
	           });
	       });
	       eventInit();
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function eventInit(){
	
	$('body').on('click', '#returnBack', function(e){
		location.href="stores.html";
	});
	
	$('body').on('click', '#saveMenu', function(e){
		 if(isNull($("#adminShopName").val())){
			qiao.bs.msg({msg:"门店名不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminWorkPlace").val())){
			qiao.bs.msg({msg:"门店地址不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminKfPhone").val())){
			qiao.bs.msg({msg:"门店电话不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminCharacteristic").val())){
			qiao.bs.msg({msg:"门店特征不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminKfBusinessHours").val())){
			qiao.bs.msg({msg:"门店营业时间不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminRecommend").val())){
			qiao.bs.msg({msg:"门店推荐不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminWorkXCoordinate").val())){
			qiao.bs.msg({msg:"门店经度不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminWorkYCoordinate").val())){
			qiao.bs.msg({msg:"门店纬度不能为空",type:'danger'});
			return;
		}else if(!checkMobile($("#adminKfPhone").val()) && !checkPhone($("#adminKfPhone").val()) && !checknum($("#adminKfPhone").val())){
			qiao.bs.msg({msg:"门店客服电话（手机号）不合格",type:'danger'});
			return;
		}
		var params = {
			adminNo:adminNo,
			adminShopName:$("#adminShopName").val(),
			adminWorkPlace:$("#adminWorkPlace").val(),
			adminKfPhone:$("#adminKfPhone").val(),
			adminShopCard:$("#adminShopCard").val(),
			adminShopKey:$("#adminShopKey").val(),
			adminCharacteristic:$("#adminCharacteristic").val(),
			adminKfBusinessHours:$("#adminKfBusinessHours").val(),
			adminRecommend:$("#adminRecommend").val(),
			adminWorkXCoordinate:$("#adminWorkXCoordinate").val(),
			adminWorkYCoordinate:$("#adminWorkYCoordinate").val(),
		}
		AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/updateShopXQ",params:params,type:'json',callback:function(json){
			if(json.returnCode==0){
		        qiao.bs.msg({msg:"修改成功",type:'success'});
		        setTimeout(function(e){
		        	location.href="stores.html";
		        },500);
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
}