var code = "";
var base = null;

$(function(e){
	showMask();
	 //加载轮播图图片
	 AjaxPostUtil.request({url:path+"/gateway/WechatScollorPicController/selectAllScollorList",params:{},type:'json',callback:function(json){
		 if(json.returnCode==0){
			 if (json.total > 0){
				 //填充数据
				 for (var i=0;i<json.total;i=i*1+1){
					 if (i==0){
						 $("#olList").append("<li data-target='#carousel-ad' data-slide-to="+i+" class='active'></li>");
					 }else{
						 $("#olList").append("<li data-target='#carousel-ad' data-slide-to="+i+"></li>");
					 }
				 }
			 //对产品productLogo进行修饰
				 Handlebars.registerHelper("optionPath",function(v1,options){
					 return path+"/"+v1;
			});
			var source = $("#adBean").html();
			var template = Handlebars.compile(source);
			$("#adListDiv").html(template(json));
			
			$(".img").each(function(index){
		    if (index == 0){
		    	$(this).addClass("active");
		    }
		    });
				//轮播图自动播放
				 $('#carousel-ad').carousel({
			         interval: 2500
			       });
				 dataInit();
			}
		 }
		 }});
});

function dataInit(){
	code = $.req("code");
	base = new Base64();
	AjaxPostUtil.request({url:path+"/gateway/EmpowerWebpageController/getOpenidBycode",params:{code:code},type:'json',callback:function(jsonall){
		if(jsonall.returnCode==0){
			if(isNull(jsonall.bean)){
				$("#city").html("未获取到位置信息");
				$("#username").html("欢迎您:"+base.decode(jsonall.bean.nickname));
			}else{
				$("#city").html(jsonall.bean.Location);
				$("#username").html("欢迎您:"+base.decode(jsonall.bean.nickname));
			}
		}else{
			location.href = 'sessionNull.html';
		}
	}});
	eventInit();
}


//事件
function eventInit(){
	 hideMask();
	//开始点餐按钮点击事件
	$('body').on('click','#begin_eat_btn',function(e){
		location.href="restaurantList.html";
	});
	
	//个人中心按钮点击事件
	$('body').on('click','#userDetail',function(e){
		location.href="myMation.html?typeId=3";
	});
	
	//收货地址详情按钮点击事件
	$('body').on('click','#addressDeatil',function(e){
		location.href="deliveryAddress.html";
	});
	
	//订单按钮点击事件
	$('body').on('click','#waitDetail',function(e){
		location.href="wait.html?orderType=1";
	});
	
}

/**
 * 显示遮罩层
 */
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
