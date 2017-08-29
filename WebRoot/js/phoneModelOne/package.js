$(function(e){
	dataInit();
});

function dataInit(){
	//获取常规套餐列表
	AjaxPostUtil.request({url:path+"/gateway/MWechatProductsController/getPackageList",params:{},type:'json',callback:function(json){
		//对产品productLogo进行修饰
		Handlebars.registerHelper("packageLogo",function(v1,options){
			 return path+"/"+v1;
		});
		//填充数据
		var source = $("#packageListBean").html();
		var template = Handlebars.compile(source);
		$("#packageListUl").html(template(json));
/*		//若是套餐图片不存在显示max图标
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
	    });*/
	}});
	
	eventInit();
}

function eventInit(){
	//立即购买按钮点击事件
	$('body').on('click','#buyNow', function(e){
		var productId = $(this).attr("buyProductId");
		var orderAdminId = $(this).attr("adminid");
		var productType = $(this).attr("productType");
		var orderItems = orderAdminId+"-"+productType+"-"+productId+"-1"+"-0";
		location.href="orderList.html?orderItems="+orderItems;
	});
	
	//添加购物车点击事件
	$('body').on('click', '#toCart', function(e){
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
				 qiao.bs.msg({msg:"添加成功,在购物车等亲~",type:'success'});
			 }else{
				 qiao.bs.msg({msg:"添加失败,请重试亲~",type:'danger'});
			 }
		 }});
	});
}
