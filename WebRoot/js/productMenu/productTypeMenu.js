var productTypeId = "";
var id1 = ""; // 表示商品的id
var productName = "";

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	// 接收添加商品后订单类型的参数
	var returnParam= $.req("productTypeId");
	if (!isNull(returnParam)){
		if(returnParam != undefined){
			productTypeId = returnParam;
		}
	}
	reviewSort();
	eventInit();
}

//获取所有的商品类别
function reviewSort(){
	// 显示种类信息
	AjaxPostUtil.request({url:path+"/post/WechatProductMenuController/getTypeList",params:{},type:'json',callback:function(json){
		if(json.returnCode == 0){
			if(json.total != 0){
				// 将标签显示
				$("#myTabContent").attr("style", "display:block");
				$("#myTabList").attr("style", "display:block");
				var source = $("#myTabBean").html();
				var template = Handlebars.compile(source);
				$("#myTabList").html(template(json));
				
				// 如果是首先进来
				if (productTypeId == ""){
					$('#typeTitle').html(json.rows[0].typeName);
					$('#hideDiv ul li:first-child').attr("class", "active");
					productTypeId = json.rows[0].id;
				}else{
					// 判断如果是修改过之后返回该页面
					var $typeList = $("a[class='typeLi']");
					for(var i = 0 ; i < $typeList.length; i ++){
						if($($typeList[i]).attr("productTypeId") == productTypeId){
							$($typeList[i]).parent().attr("class", "active");
							$('#typeTitle').html(json.rows[i].typeName);
							break;
						}
					}
				}
			}else{
				$("#whenUnInvable").append("<img src='../../assest/img/not-available.png'>");
			}
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
		getProductList();
	}});
}
//事件
function eventInit(){
	// 进行查询
	$("body").on("click", '#seachBtn', function(e){
		productName = $('#search').val();
		getProductList();
	});
	
	// 点击切换商品种类
	$('body').on("click", ".typeLi", function (e){
        // 获取已激活的标签页的名称
		productTypeId = $(e.target).attr("productTypeId");
		var typeName =  $(e.target).attr("typeName");
		$('#typeTitle').html(typeName);
		getProductList();
	});
	
	//添加分类事件
	$('body').on('click','#addProductType',function(e){
		location.href="addProductType.html";
	});
	
	//添加商品事件
	$('body').on('click','#addProduct',function(e){
		location.href="addProduct.html?productTypeId="+productTypeId;
	});
	// 显示删除模态框
	$("body").on("click", ".product-delete", function(e){
		id1 = $(this).attr("productId");
		$('#myModalDelete').modal('show');
	});
	// 确认删除
	$("body").on("click", "#debeteOkBtn", function(e){
		var param = {
			id : id1,	
		};
		// 删除选中
		AjaxPostUtil.request({url:path+"/post/WechatProductController/deleteProduct",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				$('#myModalDelete').modal('hide');
				reviewSort();
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	// 取消删除
	$("body").on("click", "#deleteCancelBtn", function(e){
		$('#myModalDelete').modal('hide');
	});
	// 查看商品详情
	$("body").on("click", ".product-details",function(e){
		var productId = $(this).attr("productid");
		var params = {
				id :productId
		};
		AjaxPostUtil.request({url:path+"/post/WechatProductController/getPrductById",params:params,type:'json',callback:function(json){
			if (json.returnCode == 0){
				console.log(json);
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
				
				var productWetherDiscount = json.bean.productWetherDiscount;
				if (productWetherDiscount == "N")
					$('#productWetherDiscount').html("否");
				else
					$('#productWetherDiscount').html("是");	
				$('#productIntegral').html(json.bean.productIntegral);
				var createTime = json.bean.createTime;
				$('#createTime').html(createTime.substring(0,10));
				$('#brandTagName').html(json.bean.brandTagName);
				$('#typeName').html(json.bean.typeName);
				$('#adminNo').html(json.bean.adminNo);
				$('#productKeyStr').html(json.bean.productKeyStr);	
				var sellTime = json.bean.startTime + "~" + json.bean.endTime;
				$("#sellTime").html(sellTime);
				
				if(json.bean.productNature == 1){
					$("#productNature").html("现点现做");
					$("#productTime").html(json.bean.productTime+"分钟");
					if(json.bean.showStartTime1!=null && json.bean.showEndTime1 != null){
						var showTime1 = json.bean.showStartTime1+"~"+json.bean.showEndTime1;
						$("#showTime1").html(showTime1);
					}
					if(json.bean.showStartTime2!=null && json.bean.showEndTime2 != null){
						var showTime2 = json.bean.showStartTime2+"~"+json.bean.showEndTime2;
						$("#showTime2").html(showTime2);
					}
					if(json.bean.showStartTime3!=null && json.bean.showEndTime3 != null){
						var showTime3 = json.bean.showStartTime3+"~"+json.bean.showEndTime3;
						$("#showTime3").html(showTime3);
					}
					$("#productDiv").removeClass("hide");
					$("#productDiv").addClass("show");
				}else{
					$("#productNature").html("无");
				}
				
				if(json.bean.member_week != 'null'){
					$("#member_week").html(json.bean.member_week);
				}else{
					$("#member_week").html("");
				}
				if(json.bean.member_month != 'null'){
					$("#member_month").html(json.bean.member_month);
				}else{
					$("#member_week").val("");
				}
				// 显示查看商品的模态框
				$("#myModalShowProduct").modal("show");
			}
		}});
	});
	
	// 编辑商品
	$("body").on("click",".product-edit", function(e){
//		alert($(this).attr("productid"));
		// 跳转到编辑页面
		location.href="updateProduct.html?productTypeId="+productTypeId+"&id=" + $(this).attr("productid");
	});
	//确认删除
	
	$("body").on("mouseenter", ".typeLi", function(e){
		$(this).find("span").show();
	});
	
	$("body").on("mouseleave", ".typeLi", function(e){
		$(this).find("span").hide();
	});
	
	//编辑类别按钮点击事件
	$("body").on("click", ".type-edit", function(e){
		var typeId = $(this).attr("productTypeId");
		location.href="updateProductType.html?id="+typeId;
	});
	
	//删除类别按钮点击事件
	$("body").on("click", ".type-delete", function(e){
		var typeId = $(this).attr("productTypeId");
		qiao.bs.confirm("确定删除该商品类别以及该类别下的商品吗？",function(){
			var params = {
					id : typeId
			};
			AjaxPostUtil.request({url:path+"/post/WechatProductTypeController/deleteProductType",params:params,type:'json',callback:function(json){
			if (json.returnCode == 0){
				qiao.bs.msg({msg:"删除成功！",type:'success'});
				reviewSort();
			}else{
				qiao.bs.msg({msg:"删除失败！",type:'danger'});
			}
			}
			});
		},function(){});
	});
	
	//菜单排序事件
	menuSort();
	
	//商品排序事件
	productSort();
	
}

//根据类别获取商品列表
function getProductList(){
	var param = {
			productType : productTypeId,
			productName:productName
		};
	AjaxPostUtil.request({url:path+"/post/WechatProductMenuController/getProductListByTypeId",params:param,type:'json',callback:function(json){
		if(json.returnCode == 0){
			//清空ul
			if(json.total != 0){
				$('#myProductList').empty();
				//放置数据
				//对产品productLogo进行修饰
				Handlebars.registerHelper("productLogo",function(v1,options){
					 return path+"/"+v1;
				});
				//填充数据
				var source = $("#myProductBean").html();
				var template = Handlebars.compile(source);
				$("#myProductList").html(template(json));
				
			}else{
				$('#myProductList').html('<h3>暂时没有查询到商品...</h3>');
			}
			
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

//菜单排序事件
function menuSort(){
    $( "#myTabList" ).sortable();
    $( "#myTabList" ).disableSelection();
    //排序完成事件
    $("#myTabList").bind("sortupdate", function(event, ui) { 
    	//顺序获取每个类别的li标签 并获取其id
    	var typeStr = "";
    	  $(".typeLi").each(function(index){
    		  var typeId = $(this).attr("productTypeId");
    		  index = index * 1 + 1;
    		  typeStr = typeStr + index + "-" +typeId +",";
    	  });
    	  var param = {
    			  typeStr :typeStr  
    	  };
    	  
  		AjaxPostUtil.request({url:path+"/post/WechatProductMenuController/updateTypeMenu",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"菜单修改成功！",type:'success'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
    	 
    });   
}

//产品排序事件排序事件
function productSort(){
	 $( "#myProductList" ).sortable();
	 $( "#myProductList" ).disableSelection();
	  $("#myProductList").bind("sortupdate", function(event, ui){ 
	    	//顺序获取每个类别的li标签 并获取其id
	    	var productStr = "";
	    	  $(".productLi").each(function(index){
	    		  var productId = $(this).attr("productId");
	    		  index = index * 1 + 1;
	    		  productStr = productStr + index + "-" +productId +",";
	    	  });
	    	  var param = {
	    			  productStr :productStr  
	    	  };
	  		AjaxPostUtil.request({url:path+"/post/WechatProductMenuController/updateProductMenu",params:param,type:'json',callback:function(json){
				if(json.returnCode == 0){
					qiao.bs.msg({msg:"商品位置修改成功！",type:'success'});
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
	  });
}
//multiselect转换字符串
function returnSelStr(str){
	if(isNull(str)){
		alert("--"+str);
		return null;
	}else{
		return str.toString(",");
	}
}
