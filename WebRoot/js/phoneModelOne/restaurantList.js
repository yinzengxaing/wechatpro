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
		location.href="commodity.html?adminId="+adminId;
	});
}

function dataInit(){
	if (city == ""){
		city = $.req("city");
	}
        	var params = {
        			city : city
        	}
        	AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getAllRestaurant",params:params,type:'json',callback:function(json){
        		if(json.returnCode==0){
        			$("#city").html(json.bean.city);
        			latitude = json.bean.latitude;
        			longitude = json.bean.longitude;
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