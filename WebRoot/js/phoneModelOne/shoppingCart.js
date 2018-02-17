var code = "";
$(function(e){
	dataInit();
});


function dataInit(){
	code = $.req("code");
	AjaxPostUtil.request({url:path+"/gateway/WechatUserController/selectLatitudeAndLongtitude",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			if(isNull(json.bean)){
				AjaxPostUtil.request({url:path+"/gateway/EmpowerWebpageController/getOpenidBycode",params:{code:code},type:'json',callback:function(jsonall){
					if(jsonall.returnCode==0){
						setCartInfo();
					}else{
						location.href = 'sessionNull.html';
					}
				}});
			}else{
				setCartInfo();
			}
		}else{
			location.href = 'sessionNull.html';
		}
	}});
	eventInit();
}

function eventInit(){
	
	//--
	$('body').on('click', '.minus', function(e){
		var t = $(this).parent().find('.num');
		t.text(parseInt(t.text()) - 1);
		if (t.text() <= 1) {
			t.text(1);
		}
		TotalPrice();
	});
	
	//++
	$('body').on('click', '.plus', function(e){
		var t = $(this).parent().find('.num');
		t.text(parseInt(t.text()) + 1);
		if (t.text() <= 1) {
			t.text(1);
		}
		TotalPrice();
	});
	
	// 点击商品按钮
	$('body').on('click','.goodsCheck',function(e) {
		var goods = $(this).closest(".shop-group-item").find(".goodsCheck"); // 获取本店铺的所有商品
		var goodsC = $(this).closest(".shop-group-item").find(".goodsCheck:checked"); // 获取本店铺所有被选中的商品
		var Shops = $(this).closest(".shop-group-item").find(".shopCheck"); // 获取本店铺的全选按钮
		if (goods.length == goodsC.length) { // 如果选中的商品等于所有商品
			Shops.prop('checked', true); // 店铺全选按钮被选中
			if ($(".shopCheck").length == $(".shopCheck:checked").length) { // 如果店铺被选中的数量等于所有店铺的数量
				$("#AllCheck").prop('checked', true); // 全选按钮被选中
				TotalPrice();
			} else {
				$("#AllCheck").prop('checked', false); // else全选按钮不被选中
				TotalPrice();
			}
		} else { // 如果选中的商品不等于所有商品
			Shops.prop('checked', false); // 店铺全选按钮不被选中
			$("#AllCheck").prop('checked', false); // 全选按钮也不被选中
			// 计算
			TotalPrice();
		}
	});
	
	// 点击店铺按钮
	$('body').on('click','.shopCheck',function(e) {
		if ($(this).prop("checked") == true) { // 如果店铺按钮被选中
			$(this).parents(".shop-group-item").find(
					".goods-check").prop('checked', true); // 店铺内的所有商品按钮也被选中
			if ($(".shopCheck").length == $(".shopCheck:checked").length) { // 如果店铺被选中的数量等于所有店铺的数量
				$("#AllCheck").prop('checked', true); // 全选按钮被选中
				TotalPrice();
			} else {
				$("#AllCheck").prop('checked', false); // else全选按钮不被选中
				//$("#productDiv").empty();
				TotalPrice();
			}
		} else { // 如果店铺按钮不被选中
			$(this).parents(".shop-group-item").find(
					".goods-check").prop('checked', false); // 店铺内的所有商品也不被全选
			$("#AllCheck").prop('checked', false); // 全选按钮也不被选中
			TotalPrice();
		}
	});
	
	// 点击店铺按钮
	$('body').on('click','#AllCheck',function(e) {
		if ($(this).prop("checked") == true) { // 如果全选按钮被选中
			$(".goods-check").prop('checked', true); // 所有按钮都被选中
			TotalPrice();
		} else {
			$(".goods-check").prop('checked', false); //else所有按钮不全选
			TotalPrice();
		}
		$(".shopCheck").change(); //执行店铺全选的操作
	});
	
	//结算按钮点击事件
	$('body').on('click','#Clearing',function(e) {
		//获取被选择的CheckBox集合
		var orderItems = ""  ;
		var adminId = "";
		var flag = 1;
		var isDouble = 0;
		$(".productItem").each(function(){
			var myThis = $(this);
			var isSelect = myThis.prop("checked");
			if (isSelect == true){
				var selectAdminId = myThis.attr("adminId");
				if (flag == 1){
					adminId = selectAdminId;
					flag = 0;
				}
				if (adminId != selectAdminId){
					isDouble = 1;
					return;
				}
				var count ="#count"+myThis.attr("productId");
				var  items = myThis.attr("adminId")+"-"+myThis.attr("productType")+"-"+myThis.attr("productId")+"-"+$(count).html()+"-"+myThis.attr("cartId");
				orderItems += items+",";
			}
		});
		//若用户没用选取商品
		if (orderItems == ''){
			qiao.bs.msg({msg:"请勾选您要购买的商品,亲~",type:'danger'});
		}else if (isDouble == 1){
			qiao.bs.msg({msg:"您不能同时购买不同商店的商品，请重试~",type:'danger'});
		}else{
			location.href="orderList.html?orderItems="+orderItems;
		}
	});
	
	$('body').on('click', ".editShoplist", function(e){
		qiao.bs.msg({msg:"还未开发，不能使用，请您稍后···",type:'danger'});
	});
	
	//删除按钮点击事件
	$('body').on('click', ".deleteProduct", function(e){
		var cartId = $(this).attr("cartId");
		qiao.bs.confirm("确定删除该商品？",function(){
			var params = {
				id:cartId,
			};			
			AjaxPostUtil.request({url:path+"/gateway/MWechatShoppingCartController/deleteProduct",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					$("#productDiv").empty();
					setCartInfo();
				}else{
					qiao.bs.msg({msg:"删除失败",type:"danger"});
				}
			}});
		},function(){});
		
	});
}

// 计算
function TotalPrice() {
	var allprice = 0; // 总价
	$(".shop-group-item").each(function() { // 循环每个店铺
		var oprice = 0; // 店铺总价
		$(this).find(".goodsCheck").each(function() { // 循环店铺里面的商品
			if ($(this).is(":checked")) { // 如果该商品被选中
				var num = parseInt($(this).parents(".shop-info").find(".num").text()); // 得到商品的数量
				var price = parseFloat($(this).parents(".shop-info").find(".price").text()); // 得到商品的单价
				var total = price * num; // 计算单个商品的总价
				oprice += total; // 计算该店铺的总价
			}
			$(this).closest(".shop-group-item").find(".ShopTotal").text(oprice.toFixed(2)); // 显示被选中商品的店铺总价
		});
		var oneprice = parseFloat($(this).find(".ShopTotal").text()); // 得到每个店铺的总价
		allprice += oneprice; // 计算所有店铺的总价
	});
	$("#AllTotal").text(allprice.toFixed(2)); //输出全部总价
}

//初始化购物车信息
function setCartInfo(){
	//获取当前用户购物车中的商品
	AjaxPostUtil.request({url:path+"/gateway/MWechatShoppingCartController/checkCart",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			//对产品productLogo进行修饰
			Handlebars.registerHelper("productLogo",function(v1,options){
				return path+"/"+v1;
			});
			//填充数据
			var source = $("#productBean").html();
			var template = Handlebars.compile(source);
			$("#productDiv").html($("#productDiv").html() + template(json));
			//若是套餐图片不存在显示max图标
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
			qiao.bs.msg({msg:"您的购物车现在还没有商品哦",type:'danger'});
		}
	}});
}
