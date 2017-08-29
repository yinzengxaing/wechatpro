var map = null;

$(function(e){
    dataInit();
});

// 查看商店的详细信息
function dataInit(){
	AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/selectForShopInfo",params:{},type:'json',callback:function(json){
		if(json.returnCode == 0){
			$("#ifCoincidence").attr("style", "display:block");
			$("#adminNo").html(json.bean.adminNo);
			$("#adminShopName").html(json.bean.adminShopName);
			$("#adminKfPhone").html(json.bean.adminKfPhone);
			$("#adminCharacteristic").html(json.bean.adminCharacteristic);
			$("#adminKfBusinessHours").html(json.bean.adminKfBusinessHours);
			$("#adminRecommend").html(json.bean.adminRecommend);
			$("#adminWorkPlace").html(json.bean.adminWorkPlace);
			$("#adminWorkXCoordinate").html(json.bean.adminWorkXCoordinate);
			$("#adminWorkYCoordinate").html(json.bean.adminWorkYCoordinate);
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
		}else{
			$("#showByMyself").html("<img src='../../assest/img/not-available.png' style=' width:100%; height:100%;'/>");
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}