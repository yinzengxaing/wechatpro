var productTypeId = "";
var id1 = ""; // 表示商品的id
var productName = "";

$(function(e){
	dataInit();
});

function dataInit(){
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
				$('#typeTitle').html(json.rows[0].typeName);
				var source = $("#myTabBean").html();
				var template = Handlebars.compile(source);
				$("#myTabList").html(template(json));
				
				// 将第一个li标签上加上Class属性
				$('#hideDiv ul li:first-child').attr("class", "active");
				if (productTypeId == ""){
					productTypeId = json.rows[0].id;
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
	
	// 确认删除
	$("body").on("click", "#debeteOkBtn", function(e){
		var param = {
			id : id1,	
		};
		// 删除选中
		AjaxPostUtil.request({url:path+"/post/WechatProductController/deleteProduct",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				$('#myModalDelete').modal('hide');
				$('#message').bootstrapTable('refresh', {url: path+'/post/WechatProductMenuController/getProductListByTypeId'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	// 取消删除
	$("body").on("click", "#deleteCancelBtn", function(e){
		$('#myModalDelete').modal('hide');
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
    		  index = index * 1 + 1
    		  typeStr = typeStr + index + "-" +typeId +",";
    	  });
    	  var param = {
    			  typeStr :typeStr  
    	  }
    	  
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
	    		  index = index * 1 + 1
	    		  productStr = productStr + index + "-" +productId +",";
	    	  });
	    	  var param = {
	    			  productStr :productStr  
	    	  }
	  		AjaxPostUtil.request({url:path+"/post/WechatProductMenuController/updateProductMenu",params:param,type:'json',callback:function(json){
				if(json.returnCode == 0){
					qiao.bs.msg({msg:"商品位置修改成功！",type:'success'});
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
	  });
}