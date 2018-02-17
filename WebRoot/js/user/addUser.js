var map = null;

$(function(e){
	dataInit();
});

function dataInit(){
	
	 //创建地图，实例化地图
    var map = new AMap.Map('container', {
    	resizeEnable: true,
        zoom:11,
        center: [116.397428, 39.90923]   
	});
    //异步加载地图
    map.plugin(["AMap.ToolBar"], function() {
        map.addControl(new AMap.ToolBar());
    });
    
    setTimeout(function(){
		//延迟，重新设置地图比例（如果不设置，当网页加载出来的时候，地图不能显示）
        map.setZoom(13);
	}, 2000);
	//加载插件
	AMap.service('AMap.Geocoder',function(){//回调函数
	    //实例化Geocoder
	    geocoder = new AMap.Geocoder({
	    });
	});
	//在地图上进行标记
	var marker = new AMap.Marker({
	     map:map,
	     bubble:true
	});
	
	/*
	* 输入提示
	*/
	var autoOptions = {
	    input: "adminWorkPlace",
	};
	
	var auto;
	AMap.plugin('AMap.Autocomplete',function(){//回调函数
		auto = new AMap.Autocomplete(autoOptions);
	});
	
	var placeSearch;
	AMap.service('AMap.PlaceSearch',function(){//回调函数
		 placeSearch = new AMap.PlaceSearch({
		     map: map
		 });  //构造地点查询类
	});
	
	AMap.event.addListener(auto, "select", select);//注册监听，当选中某条记录时会触发
	
	function select(e) {
			placeSearch.setCity(e.poi.adcode);
			placeSearch.search(e.poi.name,function(status, result){
			});
	}
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
							alert("获取位置失败,请重试");
						}
		            });
				}else{
					alert("获取位置失败,请重试");
				};
	     });
	 });
	  input.onchange = function(e){
	      var address = input.value;
	      geocoder.getLocation(address,function(status,result){
				if(status=='complete'&&result.geocodes.length){
					marker.setPosition(result.geocodes[0].location);
					map.setCenter(marker.getPosition());
					$("#adminWorkXCoordinate").val(result.geocodes[0].location.lng);
					$("#adminWorkYCoordinate").val(result.geocodes[0].location.lat);
				}else{
					alert("获取位置失败,请重试");
				}
	      });
	  };
	
	eventInit();
}

function eventInit(){

	$('body').on('change', '#adminIdentity', function(e){
		var adminIdentity = $(this).val();
		if(adminIdentity==6){
			$(".secondMenu").hide();
			$(".thirtMenu").hide();
			$(".fourMenu").hide();
			$(".fiveMenu").hide();
			$(".sixMenu").hide();
			$(".sevenMenu").hide();
			$(".eightMenu").hide();
			$(".shopCard").hide();
			$(".shopKey").hide();
			//$("#adminWorkPlace").removeAttr("readonly");
		}else if(adminIdentity==5){
			$(".secondMenu").show();
			$(".thirtMenu").show();
			$(".fourMenu").show();
			$(".fiveMenu").show();
			$(".sixMenu").show();
			$(".sevenMenu").show();
			$(".eightMenu").show();
			$(".shopCard").show();
			$(".shopKey").show();
			//$("#adminWorkPlace").removeAttr("readonly");
			//$("#adminWorkPlace").prop('readonly','readonly');
		}
	});
	
	
	
	$('body').on('click', '#saveUser', function(e){
		if(isNull($("#uNumber").val())){
			qiao.bs.msg({msg:"用户账号不能为空",type:'danger'});
			return;
		}else if(isNull($("#uPassword").val())){
			qiao.bs.msg({msg:"用户密码不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminWorkPlace").val())){
			qiao.bs.msg({msg:"地址信息不能为空",type:'danger'});
			return;
		}else if(isNull($("#searchShopKey").val())){
			qiao.bs.msg({msg:"门店搜索关键字不能为空",type:'danger'});
			return;
		}else if($("#adminIdentity").val()==5){
			if(isNull($("#adminKfPhone").val())){
				qiao.bs.msg({msg:"门店客服电话不能为空",type:'danger'});
				return;
			}else if(isNull($("#adminShopName").val())){
				qiao.bs.msg({msg:"商店名称不能为空",type:'danger'});
				return;
			}else if(isNull($("#adminShopCard").val())){
				qiao.bs.msg({msg:"商户号不能为空",type:'danger'});
				return;
			}else if(isNull($("#adminShopKey").val())){
				qiao.bs.msg({msg:"商户号对应的key不能为空",type:'danger'});
				return;
			}else if(!checkMobile($("#adminKfPhone").val()) && !checkPhone($("#adminKfPhone").val()) && !checknum($("#adminKfPhone").val())){
				qiao.bs.msg({msg:"门店客服电话（手机号）不合格",type:'danger'});
				return;
			}
			var params = {
					adminNo:$("#uNumber").val(),
					password:$("#uPassword").val(),
					adminWorkPlace:$("#adminWorkPlace").val(),
					adminIdentity:$("#adminIdentity").val(),	
					adminShopName:$("#adminShopName").val(),
					adminShopCard:$("#adminShopCard").val(),
					adminShopKey:$("#adminShopKey").val(),
					adminKfPhone:$("#adminKfPhone").val(),
					adminKfBusinessHours:$("#adminKfBusinessHours").val(),
					adminRecommend:$("#adminRecommend").val(),
					adminCharacteristic:$("#adminCharacteristic").val(),
					adminWorkXCoordinate:$("#adminWorkXCoordinate").val(),
					adminWorkYCoordinate:$("#adminWorkYCoordinate").val(),
					searchShopKey: $("#searchShopKey").val(),
			};
			AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/insertUser",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
			        qiao.bs.msg({msg:"添加成功",type:'success'});
			        setTimeout(function(e){
			        	location.href = 'userList.html';
			        },500);
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}else if($("#adminIdentity").val()==6){
			var params = {
					adminNo:$("#uNumber").val(),
					password:$("#uPassword").val(),
					adminWorkPlace:$("#adminWorkPlace").val(),
					adminIdentity:$("#adminIdentity").val(),	
					adminShopName:$("#adminShopName").val(),
					adminShopCard:$("#adminShopCard").val(),
					adminShopKey:$("#adminShopKey").val(),
					adminKfPhone:$("#adminKfPhone").val(),
					adminKfBusinessHours:$("#adminKfBusinessHours").val(),
					adminRecommend:$("#adminRecommend").val(),
					adminCharacteristic:$("#adminCharacteristic").val(),
					adminWorkXCoordinate:$("#adminWorkXCoordinate").val(),
					adminWorkYCoordinate:$("#adminWorkYCoordinate").val(),
					searchShopKey: $("#searchShopKey").val(),
			};
			AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/insertUser",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
			        qiao.bs.msg({msg:"添加成功",type:'success'});
			        setTimeout(function(e){
			        	location.href = 'userList.html';
			        },500);
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}
		
	});
	
	$('body').on('click', '#qvxiao', function(e){
		location.href="userList.html";
	});
}