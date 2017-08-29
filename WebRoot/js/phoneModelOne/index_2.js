var code = "";

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	var swiper = new Swiper('.swiper-container2', {
		autoplay: 5000,//可选选项，自动滑动
		pagination : '.swiper-pagination2'
	});
	code = $.req("code");
	AjaxPostUtil.request({url:path+"/gateway/WechatUserController/selectLatitudeAndLongtitude",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			if(isNull(json.bean)){
				AjaxPostUtil.request({url:path+"/gateway/EmpowerWebpageController/getOpenidBycode",params:{code:code},type:'json',callback:function(jsonall){
					if(jsonall.returnCode==0){
						if(isNull(jsonall.bean.Location)){
							$("#city").html("郑州市");
						}else{
							$("#city").html(jsonall.bean.Location);
						}
						initPicture();
					}else{
						location.href = 'sessionNull.html';
					}
				}});
			}else{
				if(isNull(json.bean.Location)){
					$("#city").html("郑州市");
				}else{
					$("#city").html(json.bean.Location);
				}
				initPicture();
			}
		}else{
			location.href = 'sessionNull.html';
		}
	}});
}

function initPicture(){
	AjaxPostUtil.request({url:path+"/gateway/WechatScollorPicController/selectFiveScollor",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			Handlebars.registerHelper("optionPath",function(v1,options){
				 return path+"/"+v1;
			});
			Handlebars.registerHelper("addOneShow",function(index,options){
				if(index=="0"){
					return "active";
				}else{
					return "";
				}
			});
			//填充数据
			var source = $("#lunbopicture").html();
			var template = Handlebars.compile(source);
			$("#lunbopictureDiv").html(template(json));
			
			Handlebars.registerHelper("addOne",function(index,options){
				if(index=="0"){
					return "active";
				}else{
					return "";
				}
			});
			var source = $("#lunbopicturePoint").html();
			var template = Handlebars.compile(source);
			$("#lunbopicturePointShow").html(template(json));
			$(".picLunbo").each(function(){
		    	var myThis = $(this);
		    	var thisUrl = myThis.attr("src");
		    	$.ajax(thisUrl, {
		            type: 'get',
		            timeout: 1000,
		            success: function() {
		            },
		            error: function() {
		            	myThis.attr("src",path + "/assest/icon/scollorPic.jpg");
		            }
		        });
		    });
			initShopMessage();
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function initShopMessage(){
	//获取首页展示的商品
	AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getIndexProductList",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			if (!isNull(json.bean)){
			//对logo进行修饰
			Handlebars.registerHelper("chooLogo",function(v1,options){
				 return path+"/"+v1;
			});
			
			Handlebars.registerHelper("packLogo",function(v1,options){
				 return path+"/"+v1;
			});
			Handlebars.registerHelper("chooLogo",function(v1,options){
				 return path+"/"+v1;
			});
			
			//填充数据
			var source = $("#chooByPriceBena").html();
			var template = Handlebars.compile(source);
			$("#chooByPriceDiv").html(template(json));
			
			var source = $("#newPackageBean").html();
			var template = Handlebars.compile(source);
			$("#newPackageDiv").html(template(json));
			
			//若是套餐图片不存在显示max图标
			$(".img-thumbnail").each(function(){
		    	var myThis = $(this);
		    	var thisUrl = myThis.attr("src");
		    	$.ajax(thisUrl, {
		            type: 'get',
		            timeout: 1000,
		            success: function() {
		            },
		            error: function() {
		            	myThis.attr("src",path + "/assest/icon/maxLogo.jpg");
		            }
		        });
		    });
			eventInit();
			}else{
			eventInit();
			}
		}else{
			location.href = 'sessionNull.html';
		}
	}});
}

function eventInit(){
	$('body').on('click','#moreALLCity',function(e){
		location.href="allCity.html";
	});
	
	$('body').on('click','.picLunbo',function(e){
		var rowId = $(this).parent().parent().find('div[class="item"]').attr("rowId");
		location.href="slideShow.html?id="+rowId;
	});
	
	$('body').on('click', '.icon', function(e){
		qiao.bs.msg({msg:"还未开发，不能使用，请您稍后···",type:'danger'});
	})
}