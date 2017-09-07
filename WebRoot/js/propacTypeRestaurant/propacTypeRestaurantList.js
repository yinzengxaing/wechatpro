var selectName = "";
$(function(e){
	dataInit();
});

function dataInit(){
	setData(); //初始化分类列表
	eventInit();
}

function eventInit(){
	//查询按钮监听器
	$('body').on('click', '#selectBtn', function(e){
		  selectName = $('#selectName').val();
			setData(); //初始化分类列表
	});
	//详情按钮点击事件
	$('body').on('click', '#detailBtn', function(e){
		var rowId = $(this).parent().attr("rowId");
		var proPacTypeName = $(this).parent().attr("proPacTypeName");
		$("#myModalLabel2").html(proPacTypeName);
		var params = {
				proPacTypeId :rowId
		};
		AjaxPostUtil.request({url:path+"/post/WechatProPacTypeRestaurantController/getInformationById",params:params,type:'json',callback:function(json){
			if (json.returnCode == 0){
				//对商品logo路径进行修饰
				Handlebars.registerHelper("proLogo", function(v1,options){
					 return path + "/"+v1;
				});
				//对商品创建时间进行修饰
				Handlebars.registerHelper("proCreatTime", function(v1,options){
					return v1.substring(0,10);
				});
				//对套餐logo路径进行修饰
				Handlebars.registerHelper("pacLogo", function(v1,options){
					 return path + "/"+v1;
				});
				//对套餐创建时间进行修饰
				Handlebars.registerHelper("pacCreatTime", function(v1,options){
					return v1.substring(0,10);
				});
				//对可选套餐logo路径进行修饰
				Handlebars.registerHelper("chooLogo", function(v1,options){
					 return path + "/"+v1;
				});
				//对可选套餐创建时间进行修饰
				Handlebars.registerHelper("chooCreatTime", function(v1,options){
					return v1.substring(0,10);
				});
				//商品的列表
				var source = $("#selectProductListBean").html();  
			    var template = Handlebars.compile(source);
			    $("#selectProDiv").html(template(json));
				//套餐的列表
				var source = $("#selectPackageListBean").html();  
			    var template = Handlebars.compile(source);
			    $("#selectPacDiv").html(template(json));
			    //可选套餐的列表
				var source = $("#selectChoosePackageListBean").html();  
			    var template = Handlebars.compile(source);
			    $("#selectChooDiv").html(template(json));
			    //若图片找不到  则显示max图标
/*				$(".img-thumbnail").each(function(){
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
				$("#createTime").html(json.bean.createTime.substring(0,10));
				$("#createId").html(json.bean.createId);
				//显示查看页面
				$('#myModal2').modal('show');
			}else{
				$("#createId").html("无");
				$("#createTime").html("无");
			     $(".clearDiv").html("");
				$('#myModal2').modal('show');
			}
		}});
	});
	
	//编辑按钮点击事件
	$('body').on('click', '#editBtn', function(e){
		var rowId = $(this).parent().attr("rowId");
		var proPacTypeName = $(this).parent().attr("proPacTypeName");
		$("#myModalLabel1").html(proPacTypeName);
		$("#saveBtn").attr("serveId",rowId);
		var params = {
				proPacTypeId :rowId
		};
		//获得所有的商品并勾选已经被选择的商品
		AjaxPostUtil.request({url:path+"/post/WechatProPacTypeRestaurantController/getProductList",params:params,type:'json',callback:function(json){
			//对商品logo路径进行修饰
			Handlebars.registerHelper("proLogo", function(v1,options){
				 return path + "/"+v1;
			});
			//对商品创建时间进行修饰
			Handlebars.registerHelper("proCreatTime", function(v1,options){
				return v1.substring(0,10);
			});
			//对套餐logo路径进行修饰
			Handlebars.registerHelper("pacLogo", function(v1,options){
				 return path + "/"+v1;
			});
			//对套餐创建时间进行修饰
			Handlebars.registerHelper("pacCreatTime", function(v1,options){
				return v1.substring(0,10);
			});
			//对可选套餐logo路径进行修饰
			Handlebars.registerHelper("chooLogo", function(v1,options){
				 return path + "/"+v1;
			});
			//对可选套餐创建时间进行修饰
			Handlebars.registerHelper("chooCreatTime", function(v1,options){
				return v1.substring(0,10);
			});
			//商品的列表
			var source = $("#productListBean").html();  
		    var template = Handlebars.compile(source);
		    $("#productListDiv").html(template(json));
			//套餐的列表
			var source = $("#packageListBean").html();  
		    var template = Handlebars.compile(source);
		    $("#packageListDiv").html(template(json));
		    //可选套餐的列表
			var source = $("#choosePackageListBean").html();  
		    var template = Handlebars.compile(source);
		    $("#choosePackageListDiv").html(template(json));
		    //若图片找不到  则显示max图标
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
			//将所有选项置为未选择状态
			  $("[name = chkItem]:checkbox").attr("checked", false);
			 //回显商品
				//对选择的商品进行回显
				var list = json.bean.productSelect;
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
				var list = json.bean.packageListSelect;
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
				var list = json.bean.choosePackageSelect;
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
		$('#myModal1').modal('show');
	});
	//保存按钮点击事件
	$('body').on('click', '#saveBtn', function(e){
		var proPacTypeId =$(this).attr("serveId");
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
				proPacTypeId : proPacTypeId,
				selectProductList : selectProductList,
				selectPackageList : selectPackageList,
				selectChoosePackageList : selectChoosePackageList
		};
		//发送请求
		AjaxPostUtil.request({url:path+"/post/WechatProPacTypeRestaurantController/updateRestaurant",params:params,type:'json',callback:function(json){
			if(json.returnCode == 0){
				$("#proCount"+proPacTypeId).html(json.bean.proCount)
				$("#packCount"+proPacTypeId).html(json.bean.packCount);
				$("#chooCount"+proPacTypeId).html(json.bean.chooCount);
				qiao.bs.msg({msg:"保存成功",type:'success'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
}
//数据的初始化
function setData(){
	var  params = {
		proPacTypeName:selectName
	}
	//初始化分类列表
	AjaxPostUtil.request({url:path+"/post/WechatProPacTypeRestaurantController/getRestaurantList",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
			//对分类的详情进行修饰
			Handlebars.registerHelper("proPacTypeDesc", function(v1,options){
				if(v1.length>90){
					return v1.substring(0,90)+"...";
				}else{
					return v1;
				}
			});
			//添加数据
			var source = $("#restaurantListBean").html();  
		    var template = Handlebars.compile(source);
		    $("#restaurantListDiv").html(template(json));
			
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}
