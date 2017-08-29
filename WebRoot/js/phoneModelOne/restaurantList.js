var code = "";
var latitude = "";
var longitude = "";
var city = "";

$(function(e){
    	receiveData();
    	dataInit();
});


function dataInit(){
	//获取选择的城市
	if (city == ""){
		city = $.req("city");
	}
	code = $.req("code");
	AjaxPostUtil.request({url:path+"/gateway/WechatUserController/selectLatitudeAndLongtitude",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			if(isNull(json.bean)){
				AjaxPostUtil.request({url:path+"/gateway/EmpowerWebpageController/getOpenidBycode",params:{code:code},type:'json',callback:function(jsonall){
					if(jsonall.returnCode==0){
						if(isNull(jsonall.bean.Location)){
							$("#city").html("郑州市");
							eventInit();
						}else{
							$("#city").html(jsonall.bean.Location);
							eventInit();
						}
					}else{
						location.href = 'sessionNull.html';
					}
				}});
			}else{
				if(isNull(json.bean.Location)){
					$("#city").html("郑州市");
					eventInit();
				}else{
					$("#city").html(json.bean.Location);
					eventInit();
				}
			}
		}else{
			location.href = 'sessionNull.html';
		}
	}});
}

function eventInit(){
	getLocation();
	//获取城市列表
	$('body').on('click',"#moreCity",function(e){
		location.href="allCityList.html";
	});
	//确认餐厅按钮点击事件
	$('#restaurantSelect').on('click',".chooseShop",function(e){
		var adminId = $(this).find("font[name=shopName]").attr("shop");
		location.href="commodity.html?adminId="+adminId;
	});
}

function getLocation(){
	var geolocation = new BMap.Geolocation();
    geolocation.getCurrentPosition(function (r) {  
        if (this.getStatus() == BMAP_STATUS_SUCCESS) {  
            var mk = new BMap.Marker(r.point);  
            latitude = r.point.lat;  //维度
            longitude = r.point.lng;  //经度
        	var params = {
        			city : city
        	}
        	AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getAllRestaurant",params:params,type:'json',callback:function(json){
        		if(json.returnCode==0){
        			$("#city").html(json.bean.city);
        			Handlebars.registerHelper("compare1", function(v1,v2,v3,options){
        				var s = GetDistance(latitude,longitude,v3,v2);
        					return v1+"("+s+"Km)";
        			});
        			//填充数据
        			var source = $("#restaurantBean").html();  
        		    var template = Handlebars.compile(source);
        		    $("#restaurantSelect").html(template(json));
        		}else{
          			$("#city").html(json.bean.city);
          			$("#resDiv").empty();
        			$("#resDiv").html("当前地区没有商店，请更换地区后再试~");
        		}
        	}});
        }else{
        	alert("位置获取失败")
        }
    });
}
