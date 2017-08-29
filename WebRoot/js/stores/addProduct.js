var id = null;

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	if(id == null){
		id = $.req("id");
	}
	setData(); //初始化分类列表
	eventInit();
}

function eventInit(){
	//保存按钮点击事件
	$('body').on('click', '#saveProduct', function(e){
		//获取选择的商品
		var selectProductList = ""  ;
		$(".productItem").each(function(){
			var myThis = $(this);
			var isSelect = myThis.attr("checked");
			if (isSelect == "checked"){
				var  id = myThis.attr("productId");
				selectProductList += id+","
			}
		});
		//获取选择的套餐
		var selectPackageList = "" ;
		$(".packageItem").each(function(){
			var myThis = $(this);
			var isSelect = myThis.attr("checked");
			if (isSelect == "checked"){
				var  id = myThis.attr("packageId");
				selectPackageList += id+","
			}
		});
		//获得选择的可选套餐
		var selectChoosePackageList = "" ;
		$(".choosePacItem").each(function(){
			var myThis = $(this);
			var isSelect = myThis.attr("checked");
			if (isSelect == "checked"){
				var  id = myThis.attr("choosePackageId");
				selectChoosePackageList += id+","
			}
		});
		var params = {
				adminRestaurantId:id,
				selectProductList : selectProductList,
				selectPackageList : selectPackageList,
				selectChoosePackageList : selectChoosePackageList
		};
		//发送请求
		AjaxPostUtil.request({url:path+"/post/WechatProductRestaurantController/insertProduct",params:params,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"保存成功",type:'success'});
				location.href="stores.html";
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	
	//取消
	$('body').on('click', '#qvxiao', function(e){
		location.href="stores.html";
	});
	
}

function setData(){
	var  params  = {
			adminRestaurantId : id
	}
	//初始化商品列表
	AjaxPostUtil.request({url:path+"/post/WechatProPacTypeRestaurantController/getProductList",params:{},type:'json',callback:function(json){
		//对商品logo路径进行修饰
		Handlebars.registerHelper("proLogo", function(v1,options){
			 return path + "/"+v1;
		});
		//对商品创建时间进行修饰
		Handlebars.registerHelper("proCreatTime", function(v1,options){
			return v1.substring(0,10);
		});
		var source = $("#productListBean").html();  
	    var template = Handlebars.compile(source);
	    $("#myModal1").html($("#myModal1").html() + template(json));
	    
	    //若图片找不到  则显示max图标
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
		//将所有选项置为未选择状态
		$("[name = chkItem]:checkbox").attr("checked", false);
		//获取已经选择的商品id 并且对其回显
		AjaxPostUtil.request({url:path+"/post/WechatProductRestaurantController/getProduct",params:params,type:'json',callback:function(json){
			//对选择的商品进行回显
			var list = json.bean.productList;
			$.each(list, function (index,value) {
				var selectId =  value.productId;
				$(".productItem").each(function(){
					var myThis = $(this);
					var thisId = myThis.attr("productId");
					if (selectId == thisId){
						myThis.attr("checked", true);
					}
				});
			});
			//对选择的套餐进行回显
			var list = json.bean.packageList;
			$.each(list, function (index,value) {
				var selectId =  value.packageId;
				$(".packageItem").each(function(){
					var myThis = $(this);
					var thisId = myThis.attr("packageId");
					if (selectId == thisId){
						myThis.attr("checked", true);
					}
				});
			});
	        //对选择的可选套餐进行回显
			var list = json.bean.chooPackList;
			$.each(list, function (index,value) {
				var selectId =  value.choosePackageId;
				$(".choosePacItem").each(function(){
					var myThis = $(this);
					var thisId = myThis.attr("choosePackageId");
					if (selectId == thisId){
						myThis.attr("checked",true);
					}
				});
			});	  
		}});
	}});
}	