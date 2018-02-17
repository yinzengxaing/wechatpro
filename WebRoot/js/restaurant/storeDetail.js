var map = null;
var rowId = "";
$(function(e){
    dataInit();
});

// 查看商店的详细信息
function dataInit(){
	eventInit();
	AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/selectForShopInfo",params:{},type:'json',callback:function(json){
		if(json.returnCode == 0){
			rowId = json.bean.id;
			$("#ifCoincidence").attr("style", "display:block");
			$("#adminNo").html(json.bean.adminNo);
			$("#adminShopName").html(json.bean.adminShopName);
			$("#adminKfPhone").val(json.bean.adminKfPhone);
			$("#adminCharacteristic").html(json.bean.adminCharacteristic);
			$("#adminKfBusinessHours").val(json.bean.adminKfBusinessHours);
			$("#adminRecommend").html(json.bean.adminRecommend);
			$("#adminWorkPlace").val(json.bean.adminWorkPlace);
			$("#adminWorkXCoordinate").val(json.bean.adminWorkXCoordinate);
			$("#adminWorkYCoordinate").val(json.bean.adminWorkYCoordinate);
			$("#searchShopKey").val(json.bean.searchShopKey);
			map = new AMap.Map("container", {
				resizeEnable: true,
				zoom : 11,
				center : [json.bean.adminWorkXCoordinate, json.bean.adminWorkYCoordinate]
			});
			// 加载插件
			AMap.service("AMap.Geocoder", function(){ // 回调函数
				// 实例化Geocoder
				geocoder = new AMap.Geocoder({});
				// TODO: 使用geocoder对象完成 相关功能
			});
			//在地图上进行标记
			var marker = new AMap.Marker({
	            map:map,
	            bubble:true
	        });
			setTimeout(function(){
				//延迟，重新设置地图比例（如果不设置，当网页加载出来的时候，地图不能显示）
		        map.setZoom(13);
			}, 2000);
			map.plugin(["AMap.ToolBar"], function() {
			    map.addControl(new AMap.ToolBar());
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
		}else{
			$("#showByMyself").html("<img src='../../assest/img/not-available.png' style=' width:100%; height:100%;'/>");
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function eventInit(){
	$('body').on('click', '#updateBtn', function(){
		$('.exitDiv').attr("readonly",false);
		$('input').css('border', '1px solid #04ae00');
		$("#updateBtn").removeClass("btn-primary");
		$("#saveBtn").addClass("btn-primary");
		$("#saveBtn").attr("disabled",false);
	});
	
	$('body').on('click', '#cancleBtn', function(){
		$('.exitDiv').attr("readonly",true);
		$('input').css('border', '0px');
		$("#saveBtn").removeClass("btn-primary");
		$("#updateBtn").addClass("btn-primary");
		$("#saveBtn").attr("disabled",true);
	});
	
	$('body').on('click', '#saveBtn', function(){
		if(isNull($("#adminKfPhone").val())){
			qiao.bs.msg({msg:"门店电话不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminKfBusinessHours").val())){
			qiao.bs.msg({msg:"营业时间不能为空",type:'danger'});
			return;
		}else if(isNull($("#searchShopKey").val())){
			qiao.bs.msg({msg:"门店搜索关键字不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminWorkPlace").val())){
			qiao.bs.msg({msg:"门店地址不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminWorkXCoordinate").val())){
			qiao.bs.msg({msg:"门店经度不能为空",type:'danger'});
			return;
		}else if(isNull($("#adminWorkYCoordinate").val())){
			qiao.bs.msg({msg:"门店纬度不能为空",type:'danger'});
			return;
		}else{
			var params = {
					adminKfPhone : 	$("#adminKfPhone").val(),
					adminKfBusinessHours : $("#adminKfBusinessHours").val(),
					adminWorkPlace : $("#adminWorkPlace").val(),
					adminWorkXCoordinate : $("#adminWorkXCoordinate").val(),
					adminWorkYCoordinate : $("#adminWorkYCoordinate").val(),
					searchShopKey : $("#searchShopKey").val(),
					rowId : rowId
			};
			AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductForShop",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
			        qiao.bs.msg({msg:"修改成功",type:'success'});
			        setTimeout(function(e){
			        	location.href = 'storeDetail.html';
			        },500);
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}
	});
}