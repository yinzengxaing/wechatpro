var productTypeId="";
var productName ="";

$(function(e){
	dataInit();
});

function dataInit(){
	AjaxPostUtil.request({url:path+"/post/WechatProductController/getProductTypeList",params:{},type:'json',callback:function(json){
		if (json.returnCode == 0){
			//填充类别数据
			var source = $("#productTypeBean").html();
			var template = Handlebars.compile(source);
			$("#productTypeId").html(template(json));
			productDivInit();
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:"danger"});
		}
	}});
	eventInit();
}
//商品列表的初始化
function productDivInit(){
	var params ={
			productName:productName,
			productTypeId:productTypeId
	}	
	AjaxPostUtil.request({url:path+"/post/WechatProductController/getProductList",params:params,type:'json',callback:function(json){
		if (json.returnCode == 0){
			if(json.total != 0){
				//对产品productLogo进行修饰
				Handlebars.registerHelper("productLogo",function(v1,options){
					 return path+"/"+v1;
				});
				//对productState进行修饰
				Handlebars.registerHelper("productState",function(v1,options){
					if (v1 == 0)
						return "创建";
					else if(v1 == 1)
						return "审核中";
					else if (v1 == 2)
						return "上线";
					else if (v1 == 3)
						return "审核不通过";
				});
				//对productDesc进行修饰
				Handlebars.registerHelper("productDesc",function(v1,options){
					if (isNull(v1))
						return "无";
					else{
						if (v1.length >10)
							return	v1.substring(0,10)+"...";
						else
						 return v1;
					}
				});
				//对createTime进行修饰
				Handlebars.registerHelper("createTime",function(v1,createTime){
					return v1.substring(0,10);
				});
				//对productBtn进行修饰
				Handlebars.registerHelper("productBtn",function(v1,options){
					return '<button type="button" class="btn btn-primary selectPackage" id="selectBtn">详情</button>'
					+'<button type="button" class="btn btn-default editPackage" id="editBtn">编辑</button>'
					+'<button type="button" class="btn btn-danger deleteMation" id="delBtn">删除</button>';
				});
				//填充数据
				var source = $("#productListBean").html();
				var template = Handlebars.compile(source);
				$("#productListDiv").html(template(json));
				
			}else{
				// 查询结果为空的时候，将div设置为空，并显示不存在内容的图片
				$("#productListDiv").empty();
				$("#productListDiv").append("<img src='../../assest/img/not-available.png'>");
			}
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:"danger"});
		}
	}
	});
}
//事件的处理
function eventInit(){
	//添加按钮处理事件
	$('body').on('click', '#addProduct', function(e){
		location.href="addProduct.html";
	});
	
	//查询按钮点击事件
	$('body').on('click','#searchBtn',function(e){
		productTypeId=$('#productTypeId').val();
		 productName = $('#searchProductName').val();
		 productDivInit();
		 
	});
	
	//删除按钮处理事件
	$('body').on('click', '#delBtn', function(e){
		var rowId = $(this).parent().parent().find('div[class="ProductMation"]').attr("rowId");
		qiao.bs.confirm("确定删除该商品吗？",function(){
			var params = {
				id :rowId
			};
			AjaxPostUtil.request({url:path+"/post/WechatProductController/deleteProduct",params:params,type:'json',callback:function(json){
				if (json.returnCode == 0){
					qiao.bs.msg({msg:"删除成功！",type:'success'});
					setTimeout(productDivInit,1000);//一秒后刷新页面
				}
			}
			});	
		},function(){});
	});
	
	//编辑按钮处理事件
	$('body').on('click', '#editBtn', function(e){
		var rowId = $(this).parent().parent().find('div[class="ProductMation"]').attr("rowId");
		location.href="updateProduct.html?id="+rowId;
	});
	
	//查看按钮处理事件
	$('body').on('click', '#selectBtn', function(e){
		$('#myModal2').modal('show');	
		var rowId = $(this).parent().parent().find('div[class="ProductMation"]').attr("rowId");
		var params = {
				id :rowId
		};
		//查询商品详情
		AjaxPostUtil.request({url:path+"/post/WechatProductController/getPrductById",params:params,type:'json',callback:function(json){
			if (json.returnCode == 0){
				//将查询的数据进行显示
				$('#productName').html(json.bean.productName);
				$('#productPrice').html(json.bean.productPrice);
				var productDesc = json.bean.productDesc;
				if (productDesc == null || productDesc == "")
					$('#productDesc').html("无");
				else 
					$('#productDesc').html(productDesc);
				$('#productPrice').html(json.bean.productPrice);
				$("#imgPath").attr('src',path+"/"+json.bean.productLogo); 	
				var productState = json.bean.productState;
				if (productState == 0)
					$('#productState').html("创建"); 
				else if (productState == 1)
					$('#productState').html("审核中"); 
				else if (productState == 2)
					$('#productState').html("上线"); 
				else if (productState == 3)
					$('#productState').html("审核不通过"); 
				var productWetherBreakfast = json.bean.productWetherBreakfast;
				if (productWetherBreakfast == "N")
					$('#productWetherBreakfast').html("否");
				else
					$('#productWetherBreakfast').html("是");
				var productWetherLunch = json.bean.productWetherLunch;
				if (productWetherLunch == "N")
					$('#productWetherLunch').html("否");
				else
					$('#productWetherLunch').html("是");
				var productWetherDinner = json.bean.productWetherDinner;
				if (productWetherDinner == "N")
					$('#productWetherDinner').html("否");
				else
					$('#productWetherDinner').html("是");		
				var productWetherDiscount = json.bean.productWetherDiscount;
				if (productWetherDiscount == "N")
					$('#productWetherDiscount').html("否");
				else
					$('#productWetherDiscount').html("是");	
				$('#productIntegral').html(json.bean.productIntegral);
				var productOpinion = json.bean.productOpinion;
				if (isNull(productOpinion))
					$('#productOpinion').html("无");
				else
					$('#productOpinion').html(productOpinion);
				var createTime = json.bean.createTime;
				$('#createTime').html(createTime.substring(0,10));
				$('#brandTagName').html(json.bean.brandTagName);
				$('#typeName').html(json.bean.typeName);
				$('#adminNo').html(json.bean.adminNo);
				$('#productKeyStr').html(json.bean.productKeyStr);	
				//查询商品所在的套餐详情
				AjaxPostUtil.request({url:path+"/post/WechatProductController/getPackageByProductId",params:params,type:'json',callback:function(json){
						if(json.total == 0){
							$('#packageFont').html("无");
							//对套餐图片进行修饰
							Handlebars.registerHelper("packageLogo",function(v1,options){
									return path+"/"+v1;
							});
							//对商品所在套餐信息进行修饰
							Handlebars.registerHelper("packageDesc",function(v1,options){
								if (isNull(v1)){
									return "无";
								}else{
									return v1;
								}
							});
							var source = $("#packageListBean").html();
							var template = Handlebars.compile(source);
							$("#packageListDiv").html(template(json));	
							//若是套餐图片不存在显示max图标
/*							$(".img-thumbnail").each(function(){
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
							$('#packageFont').html("");
							//对商品所在套餐信息进行修饰
							Handlebars.registerHelper("packageDesc",function(v1,options){
								if (isNull(v1)){
									return "无";
								}else{
									return v1 ;
								}
							});
							//对套餐图片进行修饰
							Handlebars.registerHelper("packageLogo",function(v1,options){
								return path+"/"+v1;
							});
							var source = $("#packageListBean").html();
							var template = Handlebars.compile(source);
							$("#packageListDiv").html(template(json));		
/*							$(".img-thumbnail").each(function(){
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
						}
				}});
			}
		}});
	});
}
	
