
var code = "";
var base = null;

$(function(e){
	//轮播图自动播放
	 $('#carousel-ad').carousel({
         interval: 2500
       });
	receiveData();
	dataInit();
});

function dataInit(){
	code = $.req("code");
	base = new Base64();
	AjaxPostUtil.request({url:path+"/gateway/WechatUserController/selectLatitudeAndLongtitude",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			if(isNull(json.bean)){
				AjaxPostUtil.request({url:path+"/gateway/EmpowerWebpageController/getOpenidBycode",params:{code:code},type:'json',callback:function(jsonall){
					if(jsonall.returnCode==0){
						if(isNull(jsonall.bean.Location)){
							$("#city").html("郑州市");
							$("#username").html("欢迎登陆");
						}else{
							$("#city").html(jsonall.bean.Location);
							$("#username").html("欢迎您:"+base.decode(jsonall.bean.nickname));
						}
					}else{
						location.href = 'sessionNull.html';
					}
				}});
			}else{
				if(isNull(json.bean.Location)){
					$("#city").html("郑州市");
					$("#username").html("欢迎登陆");
				}else{
					$("#city").html(json.bean.Location);
					$("#username").html("欢迎您:"+base.decode(json.bean.nickname));
				}
			}
		}else{
			location.href = 'sessionNull.html';
		}
	}});
	eventInit();
}


//事件
function eventInit(){
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

