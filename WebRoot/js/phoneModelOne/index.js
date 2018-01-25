var code = "";
var base = null;
var latitude = 34.775803 ;
var longitude = 113.630876; 

$(function(e){
//	dataInit();
	getImage();
	eventInit();
	code = $.req("code");
	base = new Base64();
});

function dataInit(){
	
	AjaxPostUtil.request({url:path+"/gateway/WechatUserController/selectLatitudeAndLongtitude",params:{},type:'json',callback:function(jsonall){
		if(jsonall.returnCode==0){
			if (!isNull(jsonall.bean)){
				$("#city").html(jsonall.bean.Location);
				$("#username").html("欢迎您:"+base.decode(jsonall.bean.nickname));
			}else{
				var geolocation = new BMap.Geolocation();
			    geolocation.getCurrentPosition(function (r) {  
			    	if (this.getStatus() == BMAP_STATUS_SUCCESS) { 
			    		
			    		var mk = new BMap.Marker(r.point);  
				        if(r.accuracy == null){
				        	
				            latitude = 34.75661;
					        longitude= 113.649644;
				        }else{
				            latitude = r.point.lat;  //维度
					        longitude = r.point.lng;  //经度
				        }
				    }else{
				        alert("地理位置获取失败");
				    }
			    	var params={
			    			longitude:longitude,
			    			latitude:latitude,
			    			code:code	
			    	};
			    	AjaxPostUtil.request({url:path+"/gateway/EmpowerWebpageController/getOpenidBycode",params:params,type:'json',callback:function(jsonall){
			    		if(jsonall.returnCode==0){
			    			if(isNull(jsonall.bean)){
			    				$("#city").html("未获取到用户位置信息 ，请重新登录~");
			    				$("#username").html("欢迎您:"+base.decode(jsonall.bean.nickname));
			    			}else{
			    				$("#city").html(jsonall.bean.Location);
			    				$("#username").html("欢迎您:"+base.decode(jsonall.bean.nickname));
			    			}
			    		}else{
			    			qiao.bs.msg({msg:jsonall.returnMessage,type:'danger'});	
			    			location.href = 'sessionNull.html';
			    		}
			    	}});
			        });
			}
		}
	}});
}

function getImage(){
	
	//轮播图自动播放
	 $('#carousel-ad').carousel({
	        interval: 2500
	 });
	 //加载轮播图图片
	 AjaxPostUtil.request({url:path+"/gateway/WechatScollorPicController/selectAllScollorList",params:{},type:'json',callback:function(json){
		 if(json.returnCode==0){
			 if(!isNull(json.bean)){
				 $("#city").html(json.bean.Location);
				 $("#username").html("欢迎您:"+base.decode(json.bean.nickname));
			 }else{
				 $("#indexDiv").removeClass("show");
				 $("#indexDiv").addClass("hide");
				 var geolocation = new BMap.Geolocation();
				    geolocation.getCurrentPosition(function (r) {  
				    	
				    	if (this.getStatus() == BMAP_STATUS_SUCCESS) { 
				    		
				    		var mk = new BMap.Marker(r.point);  
					        if(r.accuracy == null){
					        	
					            latitude = 34.75661;
						        longitude= 113.649644;
					        }else{
					            latitude = r.point.lat;  //维度
						        longitude = r.point.lng;  //经度
					        }
					    }else{
					        alert("地理位置获取失败");
					    }
				    	var params={
				    			longitude:longitude,
				    			latitude:latitude,
				    			code:code	
				    	};
				    	
				    	
				    	showMask();
				    	AjaxPostUtil.request({url:path+"/gateway/EmpowerWebpageController/getOpenidBycode",params:params,type:'json',callback:function(jsonall){
				    		if(jsonall.returnCode==0){
				    			if(isNull(jsonall.bean)){
				    				$("#city").html("未获取到用户位置信息 ，请重新登录~");
				    				$("#username").html("欢迎您:"+base.decode(jsonall.bean.nickname));
				    				
				    			}else{
				    				$("#city").html(jsonall.bean.Location);
				    				$("#username").html("欢迎您:"+base.decode(jsonall.bean.nickname));
				    			}
				    			
				    		}else{
				    			qiao.bs.msg({msg:jsonall.returnMessage,type:'danger'});	
				    			location.href = 'sessionNull.html';
				    		}
				    		 hideMask();
				    	}});
				    	
				        });
				   
				   
			 }
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
			
		  }
		 }
//		 dataInit();
		}});
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

function showMask(){  
	
    $("#mask").css("height",$(document).height()); 
    $("#mask").css("width",$(document).width());  
    $("#mask").show();     
}  
/**
 * 隐藏遮罩层
 */
function hideMask(){
	$("#indexDiv").removeClass("hide");
	$("#indexDiv").addClass("show");
    $("#mask").hide();     
}  
