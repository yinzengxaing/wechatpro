
var adminId = ""
var totalCount=0;
var catState = 0;  //购物车显示状态 默认为0 不显示
var totalPrice=0.00;
var typeId = "";
	
$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	if (adminId == ""){
		adminId = $.req("adminId");
	}
	var params = {
			adminId:adminId
	}
	//获取美食类别列表
	AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getAllType",params:params,type:'json',callback:function(json){
		if (json.returnCode == 0){
			//填充数据
			var source = $("#typeListBean").html();
			var template = Handlebars.compile(source);
			$("#typeListUl").html(template(json));
			//默认显示第一个商品类别的商品
			$("#typeListUl li:first").addClass("active");
			//获取第一个商品类别下的所有商品并将其作为参数传递到后台查询数据
			typeId = $("#typeListUl li:first").attr("id");
			var myparams = {
					typeId : typeId,
					adminId:adminId
			}
			AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getProductListByType",params:myparams,type:'json',callback:function(json){
				//对产品productLogo进行修饰
				Handlebars.registerHelper("productLogo",function(v1,options){
					 return path+"/"+v1;
				});
				//填充数据
				var source = $("#productListBean").html();
				var template = Handlebars.compile(source);
				$("#productListUl").html(template(json));
				$('#AllTotal').html(totalPrice);
				setCountState();
				getCartInfo();
				if (totalCount != 0){
			 		$('#clearDiv').show();
			 		$('#getCartProduct').show();
				}
				eventInit();
			}});
		}else{
			location.href = 'sessionNull.html';
		}
	}});
}

function eventInit(){
	//点击美食类别事件
	$('body').on('click', '#sidebar ul li', function(e){
		$(this).addClass('active').siblings('li').removeClass('active');
		var index = $(this).index();
		$('.j-content').eq(index).show().siblings('.j-content').hide();
		typeId = $(this).attr("id");
			 var params = {
						typeId : $(this).attr("id"),
						adminId:adminId
			 }
				AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getProductListByType",params:params,type:'json',callback:function(json){
					//对产品productLogo进行修饰
					Handlebars.registerHelper("productLogo",function(v1,options){
						 return path+"/"+v1;
					});
					//填充数据
					var source = $("#productListBean").html();
					var template = Handlebars.compile(source);
					$("#productListUl").html(template(json));
					setCountState();
					$('#AllTotal').html(totalPrice);
					if (totalCount != 0){
				 		$('#clearDiv').show();
				 		$('#getCartProduct').show();
					}
					 getCartInfo();
					//getImg();
				}});
		});
	//页面中+号事件
	$('body').on('click', '.toCart', function(e){
		var s = $(this).parent();
		var wechatCommodity = $(this).attr("buyProductId");//获取商品id
		var wechatCommodityType = $(this).attr("productType");//获取商品的类型
		var wechatCommodityAdminId = $(this).attr("adminId");//获取商店的id
		 var params = {
				 wechatCommodity:wechatCommodity,
				 wechatCommodityType:wechatCommodityType,
				 wechatCommodityAdminId:wechatCommodityAdminId
		 }
		 AjaxPostUtil.request({url:path+"/gateway/MWechatShoppingCartController/addProduct",params:params,type:'json',callback:function(json){
			 if(json.returnCode==0){
				var count = s.find('.count').html();
				 s.find('.count').html(count*1+1);
				 totalCount = totalCount*1+1;
				 s.find('.count').show();
				s.find('.redCart').show();
		 		$('#clearDiv').show();
		 		$('#getCartProduct').show();
			 }else{
				 qiao.bs.msg({msg:"添加失败",type:'danger'});
			 }
			 getCartInfo();
		 }});
	});
	
	// 页面中-号事件
	$('body').on('click', '.redCart', function(e){
		var s = $(this).parent();
		var wechatCommodity = $(this).attr("buyProductId");//获取商品id
		var wechatCommodityType = $(this).attr("productType");//获取商品的类型
		var wechatCommodityAdminId = $(this).attr("adminId");//获取商店的id
		 var params = {
				 wechatCommodity:wechatCommodity,
				 wechatCommodityType:wechatCommodityType,
				 wechatCommodityAdminId:wechatCommodityAdminId
		 }
		 AjaxPostUtil.request({url:path+"/gateway/MWechatShoppingCartController/deleteProductCount",params:params,type:'json',callback:function(json){
			 if(json.returnCode==0){
				 var count = s.find('.count').html();
				 if (count == 1){
					 s.find('.count').hide(); 
					 s.find('.redCart').hide();
					 s.find('.count').html("0");
				 }else{
					 s.find('.count').html(count*1-1);
				 }
				 totalCount = totalCount*1-1;
				 if (totalCount == 0){
				 		$('#clearDiv').hide();
				 		$('#getCartProduct').hide();
				 }
				 getCartInfo();
			 }else{
				 qiao.bs.msg({msg:"删除失败",type:'danger'});
			 }
		 }});
	});
	
	//购物车详情事件
	$('body').on('click', '#getCartProduct', function(e){
		if(catState == 0){
			catState = 1 ;
			getProductInfo();
			$('#catProductDiv').show();
			$('#wrap').show();
		}else if (catState == 1 ){
			catState = 0;
			$('#catProductDiv').hide();
			$('#wrap').hide();
		}
	});
	
	//购物车内＋号 事件
	$('body').on('click', '.addInCart', function(e){
		var s = $(this).parent();
		var wechatCommodity = $(this).attr("buyProductId");//获取商品id
		var wechatCommodityType = $(this).attr("productType");//获取商品的类型
		var wechatCommodityAdminId = $(this).attr("adminId");//获取商店的id
		 var params = {
				 wechatCommodity:wechatCommodity,
				 wechatCommodityType:wechatCommodityType,
				 wechatCommodityAdminId:wechatCommodityAdminId
		 }
		 AjaxPostUtil.request({url:path+"/gateway/MWechatShoppingCartController/addProduct",params:params,type:'json',callback:function(json){
			 if(json.returnCode==0){
				 getProductInfo();
			 }
		 }});
	});
	//购物车内 －号 事件
	$('body').on('click', '.redInCart', function(e){
		var s = $(this).parent();
		var wechatCommodity = $(this).attr("buyProductId");//获取商品id
		var wechatCommodityType = $(this).attr("productType");//获取商品的类型
		var wechatCommodityAdminId = $(this).attr("adminId");//获取商店的id
		 var params = {
				 wechatCommodity:wechatCommodity,
				 wechatCommodityType:wechatCommodityType,
				 wechatCommodityAdminId:wechatCommodityAdminId
		 }
		 AjaxPostUtil.request({url:path+"/gateway/MWechatShoppingCartController/deleteProductCount",params:params,type:'json',callback:function(json){
			 if(json.returnCode==0){
					 getProductInfo();
			 }
		 }});
		
	});
	
	//选好了按钮点击事件
	$('body').on('click', '#selectOver', function(e){
		location.href ="orderList.html?adminId="+adminId;
	});
}

function getImg(){
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
}

function setCountState(){
	//遍历当前页面所有的product-count
	$(".product-count").each(function(){
		var s = $(this);
		var count = s.find('.count').html()
		if (count == 0 ){
			 s.find('.count').hide(); 
			 s.find('.redCart').hide();
		}
	  });
}

function getCartInfo(){
	var params = {
			adminId : adminId
	}
	AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getCartDetail",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
			if (json.bean.totalCount == 0){ //当前登录人购物车无商品
				totalCount = 0;
				totalPrice = 0.00;
				$('#clearDiv').hide();
		 		$('#getCartProduct').hide();
			}else{ //当前登录人购物车有商品
				totalCount = json.bean.totalCount;
				totalPrice = json.bean.totalPrice;
				$('#AllTotal').html(totalPrice);
		 		$('#clearDiv').show();
		 		$('#getCartProduct').show();
			}
		}else{
			
		}
	}});
}

function getProductInfo(){
	var params = {
			adminId : adminId
	}
	AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getCartDetail",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
			if (json.bean.totalCount != 0){ 
			//填充数据
			totalCount = json.bean.totalCount;
			totalPrice = json.bean.totalPrice;
			$('#AllTotal').html(totalPrice);	
			var source = $("#cartBenas").html();
			var template = Handlebars.compile(source);
			$("#catProductDiv").html(template(json));
			refishProduct();
			}else{
				totalCount = 0;
				totalPrice = 0.00;
				$("#catProductDiv").empty();
				$('#clearDiv').hide();
		 		$('#getCartProduct').hide();
				$('#wrap').hide();
				refishProduct();
			}
		}else{
			qiao.bs.msg({msg:"查询失败哦~",type:'danger'});
		}
	}});
}

function refishProduct(){
	var params = {
			typeId : typeId,
			adminId:adminId
	}
	AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getProductListByType",params:params,type:'json',callback:function(json){
		 if(json.returnCode==0){
			Handlebars.registerHelper("productLogo",function(v1,options){
				 return path+"/"+v1;
			});
			//填充数据
			var source = $("#productListBean").html();
			var template = Handlebars.compile(source);
			$("#productListUl").html(template(json));
			setCountState();
		 }else{
			 qiao.bs.msg({msg:"刷新失败哦~",type:'danger'}); 
		 }
	}});
}
	
	
	