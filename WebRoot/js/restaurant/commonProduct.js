var productTypeId = ''; // 表示商品分类的id
var id1 = ""; // 存放返回的数组
var productState = "";
var productNumber = '';// 记录商品的
$(function(e){
	reviewSort();
});
function dataInit(){
	var oTable = new TableInit();
	oTable.Init();
	eventInit();
}

function reviewSort(){
	// 显示种类信息
	AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/selectProductTypeByChoose",params:{},type:'json',callback:function(json){
		if(json.returnCode == 0){
			if(json.total != 0){
				// 将标签显示
				$("#myTabContent").attr("style", "display:block");
				$("#myTabList").attr("style", "display:block");

				var source = $("#myTabBean").html();
				var template = Handlebars.compile(source);
				$("#myTabList").html(template(json));
				
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
				
				// 将第一个li标签上加上Class属性
				$('ul li:first-child').attr("class", "active");
				// 获得第一种类型的商品
				productTypeId = json.rows[0].productTYpeId;
			dataInit();
			}else{
				$("#whenUnInvable").append("<img src='../../assest/img/not-available.png'>");
			}
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}
function eventInit(){
	// 进行查询
	$("body").on("click", '#seachBtn', function(e){
		$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectProductByChoose'});
	});
	
	// 重置卖出数量
	$("body").on("click", "#enterNowNumBut", function (e){
		var param = {
			productId: id1,
			productType:1
		};

		id1 = "";
		AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductNowNum",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
    			$('#myModal1').modal('hide');
    			$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectProductByChoose'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	
	$("body").on("click", "#modifyNumCancelbtn", function(e){
		$('#myModal1').modal('hide');
	});
	// 修改总数量（可进行多条数据更新）
	$("body").on("click", "#modifyNumbtn", function (e){
		if(isNaN($("#productNumberId").val())){
			qiao.bs.msg({msg:"请正确填写数字",type:'danger'});
			return false;
		}
		if(isNull($("#productNumberId").val())){
			qiao.bs.msg({msg:"请填写总数量",type:'danger'});
			return false;
		}
		if($("#productNumberId").val() > 1000){
			qiao.bs.msg({msg:"总数量在0~1000范围内",type:'danger'});
			return false;
		}
		var param = {
				productId: id1,
				productType:1,
				productNum:$("#productNumberId").val(),
		};

		id1 = "";
		AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductNum",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
    			$('#myModal1').modal('hide');
    			$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectProductByChoose'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	// 上下线取消
	$("body").on("click", "#upOrDownCancelbtn",function(e){
		$('#myModal4').modal('hide');
	});
	// 进行上下线操作
	$("body").on("click", "#upOrDownbtn",function(e){
		var param = {
			productId: id1,
			productType:1,
			productState:productState,
		}
		id1 = "";
		productState = "";
		AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductState",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
    			$('#myModal1').modal('hide');
    			$("#message").bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectProductByChoose'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
		$("#upLineOk").show();
		$("#downLineOk").show();
	});
	// 点击切换商品种类
	$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        // 获取已激活的标签页的名称
		productTypeId = $(e.target).attr("productTypeId");
		$("#message").bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectProductByChoose'});
	});
	
	// 显示批量重置卖出数量的模态框
	$("body").on("click", "#enterNowNumButMany", function (e){
		$('#myModal11').modal('show');
	});
	$("body").on("click", "#enterNowNumModelCancelButMany", function(e){
		$('#myModal11').modal('hide');
	});
	// 批量重置卖出数量
	$("body").on("click", "#enterNowNumModelButMany", function (e){
		// 获得表格中选中的数量
		var manyUpOrDown = $("#message").bootstrapTable('getSelections');
		for(var i = 0 ; i < manyUpOrDown.length; i ++)
			id1 += manyUpOrDown[i].id + ",";
		var param = {
				productId: id1,
				productType:1
			};
		id1 = "";
		AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductNowNum",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
	    		$('#myModal11').modal('hide');
	    		$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectProductByChoose'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	
	// 显示批量上下操作的模态框
	$("body").on("click", "#upOrDownbtnMany", function (e){
		$('#myModal44').modal('show');
	});
	$("body").on("click", "#upOrDownModelCancelbtnMany", function (e){
		$('#myModal44').modal('hide');
	});
	// 批量处理上下线（都选择上线或者都选择下线进行处理，不能同时选择上线或者下线）
	$("body").on("click", "#upOrDownModelbtnMany", function(e){
		// 获得表格中选中的数量
		var manyUpOrDown = $("#message").bootstrapTable('getSelections');
		// 上下线状态
		for(var i = 0 ; i < manyUpOrDown.length; i ++){
			id1 += manyUpOrDown[i].id + ",";
			// 遍历状态进项反选
			if(manyUpOrDown[i].productState == 1)
				productState += "0" + ",";
			else
				productState += "1" + ",";
		}
		var param = {
			productId: id1,
			productType:1,
			productState:productState,
		}
		id1 = "";
		productState = "";
		AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductState",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
    			$('#myModal44').modal('hide');
    			$("#message").bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectProductByChoose'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				$('#myModal44').modal('hide');
				$("#message").bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectProductByChoose'});
			}
		}});
	});
	
	// 批量修改总数量
	$("body").on("click", "#modifyNumbtnMany", function (e){
		$('#myModal22').modal('show');
		// 最大值为1000
		$("#productNumberIdMany").attr("value", 1000);
	});
	$("body").on("click", "#modifyNumModelCancelbtnMany", function(e){
		$('#myModal22').modal('hide');
	});
	// 批量修改总数量
	$("body").on("click", "#modifyNumModelbtnMany", function (e){
		// 获得表格中选中的数量
		var manyUpOrDown = $("#message").bootstrapTable('getSelections');
		
		// 对输入的数字进行判断
		if(isNaN($("#productNumberIdMany").val())){
			qiao.bs.msg({msg:"请正确填写数字",type:'danger'});
			return false;
		}
		if(isNull($("#productNumberIdMany").val())){
			qiao.bs.msg({msg:"请填写总数量",type:'danger'});
			return false;
		}
		if($("#productNumberIdMany").val() > 1000){
			qiao.bs.msg({msg:"总数量在0~1000范围内",type:'danger'});
			return false;
		}
		
		for(var i = 0 ; i < manyUpOrDown.length; i ++)
			id1 += manyUpOrDown[i].id + ",";
		var param = {
				productId: id1,
				productType:1,
				productNum: $("#productNumberIdMany").val(),
			};
		id1 = "";
		AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductNum",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
	    		$('#myModal22').modal('hide');
	    		$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectProductByChoose'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
};

var TableInit =  function (){
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.Init = function(){
		$("#message").bootstrapTable({
			url: path+'/post/wechatCanteenProductManageController/selectProductByChoose', // 请求后套的URL
			method:"post", 						// 请求方式（*）
			toolbar: '#toolbar',                //工具按钮用哪个容器
			striped: true,                      //是否显示行间隔色
			cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
			pagination: true,                   //是否显示分页（*）
			sortable: false,                    //是否启用排序
			sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            contentType: "application/x-www-form-urlencoded",         //发送到服务器的数据编码类型
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [10],						//可供选择的每页的行数（*）
            height:700,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,
            columns:[{
            	checkbox :true
            },{
            	field: 'Number',
            	title: '序号',
            	width: '50',
            	align: 'center',
            	formatter: function (value, row, index) {
            		return index+1;
            	}
            },{
            	field:'optionPath',
        		title:'商品Logo',
        		width:'50',
        		align:'center',
        		formatter: function (value, row, index) {
        			if (isNull(value)){
        				return '<img alt="" src="../../assest/img/roleNoPic.png" class="dialogUserImg img-thumbnail" id = "packageLogo"/>';
        			}
        			return '<img alt="" src="'+ path + '/' + value+'" class="dialogUserImg img-thumbnail" id = "packageLogo"/>';
        		}
            },{
            	field:'productName',
            	title:'商品名称',
            	width:'50',
            	align:'center',
            },{
            	field:'productDesc',
            	title:'描述',
            	width:'100',
            	align:'center',
            	formatter: function (value, row, index) {
            		if (isNull(value)){
                		return "无";
                	}else if(value.length > 20){
    					return value.substring(0,20)+"...";
    				}else{
    					return value;
    				}
                }
            },{
            	field:'createTime',
            	title:'创建时间',
            	width:'50',
            	align:'center',
            	formatter: function (value, row, index) {
            		if(isNull(value)){
            			return "无";
            		}else if(value.length > 10){
            			return value.substring(0, 10);
            		}else{
            			return value;
            		}
            	}
            },{
            	field:'productNowNum',
            	title:'卖出数量',
            	width:'50',
            	align:'center',
            },{
            	field:'productNum',
            	title:'总数量',
            	width:'50',
            	align:'center',
            },{
            	field:'productState',
            	title:'当前状态',
            	width:'50',
            	align:'center',
            	formatter:function (value, row, index) {
            		if(value == 1)
            			return "上线";
            		else
            			return "<font color='red' >下线</font>";
            	}
            },{
            	field: 'operate',
	            title: '操作',
	            width: '300',
	            align: 'center',
	            events: EvenInit,
	            formatter: operateFormatter
            }],
            onLoadSuccess: function(){  //加载成功时执行
            	console.log("加载成功");
            },
            onLoadError: function(){  //加载失败时执行
            	console.log("加载数据失败", {time : 1500, icon : 2});
            }
		});
	};
	oTableInit.queryParams = function (params) {
        var temp = {
        	limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            productName: $('#search').val(),
            productTypeId:productTypeId,
        };
        return temp;
    };
    return oTableInit;
}

window.EvenInit = {
	// 查看商品详情
	'click .RoleOfA': function (e, value, row, index) {
	    var rowId = row.id;
    	$('#myModal3').modal('show');
		var params = {
			id :rowId,
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
/*						$(".img-thumbnail").each(function(){
						   	var myThis = $(this);
						    var thisUrl = myThis.attr("src");
						    $.ajax(thisUrl, {
						    	type: 'get',
						    	timeout: 1000,
						    	success: function() {
						    	},
						    	error: function() {
						    		myThis.attr("src",path + "/assest/icon/maxLogo.jpg");
						    	}});
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
	},
	// 上下线操作
	'click .RoleOfB':function(e, value, row, index) {
		id1 = row.id; // 将获取到的数据存放在数组中
		
		// 如果是上线状态则更改为下线状态，如果为下线状态则更改为上线状态
		productState = row.productState;
		if(productState == 1){
			$("#upLineOk").hide();
			productState = 0;
		}else{
			$("#downLineOk").hide();
			productState = 1;
		}
		$('#myModal4').modal('show');
	},
	// 重置卖出数量
	'click .RoleOfC':function(e, value, row, index) {
		id1 = row.id;
		$('#myModal1').modal('show');
	},
	// 重置可卖数量
	'click .RoleOfD':function(e, value, row, index) {
		// 显示总数量的模态框
		$("#myModal2").modal("show");
		$("#productNumberId").attr("value", row.productNum);
		id1 = row.id // 将获取到的数据存放在数组中
	}
}
function operateFormatter(value, row, index) {
	return [
			'<button type="button" class="RoleOfA btn btn-primary selectMaton">详情</button>',
			'<button type="button" class="RoleOfB btn btn-info">上/下线</button>',
			'<button type="button" class="RoleOfC btn btn-success">重置卖出数量</button>',
			'<button type="button" class="RoleOfD btn btn-default">修改总数量</button>'
	    ].join('');
}