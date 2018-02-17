var code = "";
var latitude = "";
var longitude = "";
var city = "";

$(function(e){
    	receiveData();
    	dataInit();
    	eventInit();
});

function eventInit(){
	//获取城市列表
	$('body').on('click',"#moreCity",function(e){
		location.href="allCityList.html";
	});
	//确认餐厅按钮点击事件
	$('#restaurantSelect').on('click',".chooseShop",function(e){
		var adminId = $(this).find("font[name=shopName]").attr("shop");
		var time = $(this).find("font[name=shopName]").attr("data-time");
		location.href="commodity.html?adminId="+adminId+"&time="+time;
	});
}

function dataInit(){
	//判断是否从城市列表中传过来的值
	if (city == ""){
		city = $.req("city");
	}
	var params = {
			city : city
	};   	
	AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getAllRestaurant",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
			$("#city").html(json.bean.city);
//			latitude = json.bean.latitude;
//			longitude = json.bean.longitude;
			//第一个变色
			Handlebars.registerHelper("queryIndex", function(v1){
				if(v1 == 0){
					var str = "background-color:rgb(199, 0, 11);color: white";  //第一个li
					return str;
				}
			});
			Handlebars.registerHelper("queryClass", function(v1){
				if(v1 == 0){
					var str = "color: white";  //第一个li
					return str;
				}
			});
			//第一个变色
			Handlebars.registerHelper("queryDistance", function(v1){ //km
				if(v1 < 1){//若是小于1km，则换算成以米为单位的距离
					return v1*1000+"米";
				}else{
					return v1+"公里";
				}
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
}

function showMask(){     
    $("#mask").css("height",$(document).height());     
    $("#mask").css("width",$(document).width());     
    $("#mask").show();     
}  
/**
 * 隐藏遮罩层
 */
function hideMask(){     
    $("#mask").hide();     
}  