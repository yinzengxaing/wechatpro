var adminId = ""

var totalCount = 0;
var catState = 0; // 购物车显示状态 默认为0 不显示
var totalPrice = 0.00;

$(function() {

	receiveData();
	dataInit();
})

/*
  商品信息初始化
 */
function dataInit() {
	if (adminId == "") {
		adminId = $.req("adminId");
	}
	var params = {
		adminId : adminId
	}
	// 获取美食类别列表
	AjaxPostUtil.request({
		url : path + "/gateway/MWechatProductController/getAllType",params : params,type : 'json',callback : function(json) {
			if (json.returnCode == 0) {
				// 对类型log进行修饰
				Handlebars.registerHelper("typeLogo", function(v1, options) {
					return path + "/" + v1;
				});
				var source = $("#typeListBean").html();
				var template = Handlebars.compile(source);
				$("#typeListUl").html(template(json));
				// 默认显示第一个商品类别的商品
				$("#typeListUl li:first").addClass("commodity-activity");
				// 对产品productLogo进行修饰
				Handlebars.registerHelper("productLogo", function(v1, options) {
					return path + "/" + v1;
				});
				Handlebars.registerHelper("productCount", function(v1, options) {
					if (v1 > 0){
						return v1;
					}else{
						return 0;
					}
				});
				var source2 = $("#productListBean").html();
				var template = Handlebars.compile(source2);
				$("#productList-div").html($("#productList-div").html() + template(json));
				setCountState();
				getCartInfo();
				if (totalCount != 0){
			 		$('#clearDiv').show();
			 		$('#getCartProduct').show();
				}
			} else {
				location.href = 'sessionNull.html';
			}

		}
	});
	eventInit();
}

function eventInit() {
	// 锚点跳转滑动效果
	$('body').on('click','a',function(e) {
		if (location.pathname.replace(/^\//, '') == this.pathname.replace(/^\//, '')&& location.hostname == this.hostname) {
			var $target = $(this.hash);
			$target = $target.length && $target
					|| $('[name=' + this.hash.slice(1) + ']');
			if ($target.length) {
				var targetOffset = $target.offset().top;
				targetOffset = targetOffset - 50;
				$('html,body').animate({
					scrollTop : targetOffset
				}, 500);
				return false;
			}
		}
	});
	// 滚动变化选择效果
	$(window).scroll(function() {// 开始监听滚动条
		// 获取当前滚动条高度
		var topp = $(document).scrollTop();
		$(".food-div").each(function() {
			var s = $(this).offset().top - 50;
			if (topp == 0) {
				$(".innerbox li a").each(function() {
					$($(this).parent()).removeClass('commodity-activity');
				});
				$("#typeListUl li:first").addClass("commodity-activity");
			} else if (topp >= s - 400) {
				var id = "#" + $(this).attr("id");
				$(".innerbox li a").each(function() {
					$($(this).parent()).removeClass('commodity-activity');
					var href = $(this).attr("href");
					if (href == id) {
						$($(this).parent()).addClass('commodity-activity');
					}
				});
			}
		});

	});
	
	//页面中+号事件
	$('body').on('click', '.toCart', function(e){
		showMask();
		var s = $(this).parent();
		var wechatCommodity = $(this).attr("buyProductId");//获取商品id
		var wechatCommodityType = $(this).attr("productType");//获取商品的类型
		var wechatCommodityAdminId = $(this).attr("adminId");//获取商店的id
		var productPrice = $(this).attr("productPrice");
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
		 		totalPrice = accAdd(totalPrice, productPrice);
				$('#AllTotal').html(totalPrice);
			 }else{
				 qiao.bs.msg({msg:"添加失败",type:'danger'});
			 }
			 hideMask();
		 }});
	});
	
	// 页面中-号事件
	$('body').on('click', '.redCart', function(e){
		showMask();
		var s = $(this).parent();
		var wechatCommodity = $(this).attr("buyProductId");//获取商品id
		var wechatCommodityType = $(this).attr("productType");//获取商品的类型
		var wechatCommodityAdminId = $(this).attr("adminId");//获取商店的id
		var productPrice = $(this).attr("productPrice"); //商品的价格
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
				 totalPrice=accSub(totalPrice, productPrice);
				 if (totalPrice <= 0){
					totalCount = 0;
					totalPrice = 0.00;
					$('#clearDiv').hide();
			 		$('#getCartProduct').hide();
				 }else{
					 $('#AllTotal').html(totalPrice);
				 }
			 }else{
				 qiao.bs.msg({msg:"删除失败",type:'danger'});
			 }
			 hideMask();
		 }});
	});
	
	//购物车详情事件
	$('body').on('click', '#getCartProduct', function(e){
		if(catState == 0){
			catState = 1 ;
			getProductInfo();
			$('#catProductDiv').show();
			$('#wrap').show();
			hideMask();
		}else if (catState == 1 ){
			catState = 0;
			$('#catProductDiv').hide();
			$('#wrap').hide();
		}
	});
	
	//购物车内＋号 事件
	$('body').on('click', '.addInCart', function(e){
		showMask();
		var s = $(this).parent();
		var wechatCommodity = $(this).attr("buyProductId");//获取商品id
		var wechatCommodityType = $(this).attr("productType");//获取商品的类型
		var wechatCommodityAdminId = $(this).attr("adminId");//获取商店的id
		var productPrice = $(this).attr("productPrice");
		
		 var params = {
				 wechatCommodity:wechatCommodity,
				 wechatCommodityType:wechatCommodityType,
				 wechatCommodityAdminId:wechatCommodityAdminId
		 }
		 AjaxPostUtil.request({url:path+"/gateway/MWechatShoppingCartController/addProduct",params:params,type:'json',callback:function(json){
			 if(json.returnCode==0){
					var proCount = $(".proCount");
					proCount.each(function() {
					    var thisWechatCommodity = $(this).attr("buyProductId");//获取商品id
						var thisWechatCommodityType = $(this).attr("productType");//获取商品的类型
						if (thisWechatCommodity == wechatCommodity  &&   thisWechatCommodityType == wechatCommodityType){
							count = $(this).html();
							$(this).html(accAdd(count, 1));
						}
					});
					var count = s.find('.count').html();
					 s.find('.count').html(count*1+1);
					 totalCount = totalCount*1+1;
					 s.find('.count').show();
					s.find('.redCart').show();
			 		$('#clearDiv').show();
			 		$('#getCartProduct').show();
			 		totalPrice = accAdd(totalPrice, productPrice);
					$('#AllTotal').html(totalPrice);
				 }else{
					 qiao.bs.msg({msg:"添加失败",type:'danger'});
				 }
			 hideMask();
		 }});
	});
	//购物车内 －号 事件
	$('body').on('click', '.redInCart', function(e){
		showMask();
		var s = $(this).parent();
		var wechatCommodity = $(this).attr("buyProductId");//获取商品id
		var wechatCommodityType = $(this).attr("productType");//获取商品的类型
		var wechatCommodityAdminId = $(this).attr("adminId");//获取商店的id
		var productPrice = $(this).attr("productPrice");
		var proCount = $(".proCount");
		var params = {
				 wechatCommodity:wechatCommodity,
				 wechatCommodityType:wechatCommodityType,
				 wechatCommodityAdminId:wechatCommodityAdminId
		 }
		 AjaxPostUtil.request({url:path+"/gateway/MWechatShoppingCartController/deleteProductCount",params:params,type:'json',callback:function(json){
			 if(json.returnCode==0){
					c = s.find(".count").html();
					if (c <= 1){
						s.parent().remove();
					}else{
						s.find(".count").html(accSub(c, 1));
					}
					
					proCount.each(function() {
					    var thisWechatCommodity = $(this).attr("buyProductId");//获取商品id
						var thisWechatCommodityType = $(this).attr("productType");//获取商品的类型
						if (thisWechatCommodity == wechatCommodity  &&   thisWechatCommodityType == wechatCommodityType){
							count = $(this).html();
							 newCount=accSub(count, 1);
							 if (newCount <=0){
								 mys = $(this).parent();
								 mys.find('.count').hide(); 
								 mys.find('.redCart').hide();
								 mys.find('.count').html("0");
								 
							 }else{
								 $(this).html(newCount);
							 }
						}
					});
				 totalCount = totalCount*1-1;
				 if (totalCount == 0){
				 		$('#clearDiv').hide();
				 		$('#getCartProduct').hide();
				 }
				 totalPrice=accSub(totalPrice, productPrice);
				 if (totalPrice <= 0){
					totalCount = 0;
					totalPrice = 0.00;
					$('#clearDiv').hide();
			 		$('#getCartProduct').hide();
			 		catState = 0;
					$('#catProductDiv').hide();
					$('#wrap').hide();
				 }else{
					 $('#AllTotal').html(totalPrice);
				 }
			 }else{
				 qiao.bs.msg({msg:"删除失败",type:'danger'});
			 }
			 hideMask();
		 }});
		
	});

	//选好了按钮点击事件
	$('body').on('click', '#selectOver', function(e){
		location.href ="orderList.html?adminId="+adminId;
	});
	
}



/*
   设置+ 和 - 的隐藏和消失
*/
function setCountState() { 
	// 遍历当前页面所有的product-count
	$(".product-count").each(function() {
		var s = $(this);
		var count = s.find('.count').html()
		if (count == 0) {
			s.find('.count').hide();
			s.find('.redCart').hide();
		}
	});
}

/*
  获取购物车信息
 */
function getCartInfo() {
	showMask();
	var params = {
		adminId : adminId
	}
	AjaxPostUtil.request({
		url : path + "/gateway/MWechatProductController/getCartDetail",params : params,type : 'json',callback : function(json) {
			if (json.returnCode == 0) {
				if (json.bean.totalCount == 0) { // 当前登录人购物车无商品
					totalCount = 0;
					totalPrice = 0.00;
					$('#clearDiv').hide();
					$('#getCartProduct').hide();
				} else { 
					// 当前登录人购物车有商品
					totalCount =json.bean.totalCount;
					totalPrice = json.bean.totalPrice;
					$('#AllTotal').html(totalPrice);
					$('#clearDiv').show();
					$('#getCartProduct').show();
				}
			} else {
				alert("未查询到数据");
			}
			hideMask();
		}
	});
}


/**
 * 获取购物车内商品信息
 */
function getProductInfo(){
	var params = {
			adminId : adminId
	}
	showMask();
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
			}else{
				totalCount = 0;
				totalPrice = 0.00;
				$("#catProductDiv").empty();
				$('#clearDiv').hide();
		 		$('#getCartProduct').hide();
				$('#wrap').hide();
			}
		}else{
			qiao.bs.msg({msg:"查询失败哦~",type:'danger'});
		}
		hideMask();
	}});
}

/*
  显示遮罩层
 */
function showMask() {
	$("#mask").css("height", $(document).height());
	$("#mask").css("width", $(document).width());
	$("#mask").show();
}
/*
  隐藏遮罩层
 */
function hideMask() {
	$("#mask").hide();
}
