var adminId= "";
var productType="";
var productId="";

$(function(e){
	dataInit();
	receiveData();
});

function dataInit(){
	//初始化参数
	if (productType == "" || productId=="" || adminId==""){
		adminId = $.req("adminId");
		productType = $.req("productType");
		productId = $.req("productId");
	}
	var params ={
			adminId:adminId,
			productType:productType,
			productId : productId
	}
	//获取商品信息
	AjaxPostUtil.request({url:path+"/gateway/MWechatProductController/getProductDetail",params:params,type:'json',callback:function(json){
		if(json.returnCode == 0){
			$("#productLogo").attr("src",path+"/"+json.bean.productLogo);
			$("#productName").html(json.bean.productName);
			$("#productPrice").html("￥"+json.bean.productPrice);
			$("#monthNumber").html(json.bean.monthNumber);
			if (isNull(json.bean.productDesc)){
				$("#productDesc").html("该商品暂无描述~");
			}else{
				
				$("#productDesc").html(json.bean.productDesc);
			}
/*			$("#productIntegral").html(json.bean.productIntegral);*/
			
/*			$(".img-thumbnail").each(function(){
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
		}else{
			 qiao.bs.msg({msg:"显示错误~",type:'danger'});
		}
	}});
	
	eventInit();
}

//按钮事件点击事件
function eventInit(){
	//	//立即购买按钮点击事件
	$('body').on('click', '#buyNow', function(e){
		var orderItems = adminId+"-"+productType+"-"+productId+"-1"+"-0";
		location.href="orderList.html?orderItems="+orderItems;
	});
	
	//添加购物车点击事件
	$('body').on('click', '#toCart', function(e){
		var wechatCommodity =productId;
		var wechatCommodityType = productType;
		var wechatCommodityAdminId = adminId;
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