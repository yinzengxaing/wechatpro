var id1 = "";
var productState = "";
$(function(e){
	reviewInfo();
});
function reviewInfo(){
	var param = {
		productType : 3,
	};
	AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/selectNum",params:param,type:'json',callback:function(json){
		if(json.returnCode == 0){
			if(json.bean.productNumber != 0){
				$("#hideDiv").attr("style", "display:block");
			}else{
				$("#whenUnInvable").append("<img src='../../assest/img/not-available.png'>");
			}
			dataInit();
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
};
function dataInit(){
	var oTable = new TableInit();
	oTable.Init();
	eventTini();
}

function eventTini(){
	// 进行查询
	$("body").on("click", '#seachBtn', function(e){
		$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectChoosePackageByChoose'});
	});
	
	// 重置卖出数量
	$("body").on("click", "#enterNowNumBut", function (e){
		alert(id1);
		var param = {
			productId: id1,
			productType:3
		};
		AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductNowNum",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
    			$('#myModal1').modal('hide');
    			$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectChoosePackageByChoose'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	
	$("body").on("click", "#modifyNumCancelbtn", function(e){
		$('#myModal1').modal('hide');
	});
	// 修改总数量
	$("body").on("click", "#modifyNumbtn", function (e){
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
				productType:3,
				productNum:$("#productNumberId").val(),
		};
		
		AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductNum",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
    			$('#myModal1').modal('hide');
    			$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectChoosePackageByChoose'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	
	$("body").on("click", "#upOrDownCancelbtn",function(e){
		$('#myModal4').modal('hide');
	});
	// 进行上下线操作
	$("body").on("click", "#upOrDownbtn",function(e){
		var param = {
				productId: id1,
				productType:3,
				productState:productState,
			}
			id1 = "";
			productState = "";
			AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductState",params:param,type:'json',callback:function(json){
				if(json.returnCode == 0){
					qiao.bs.msg({msg:"修改成功",type:'success'});
	    			$('#myModal1').modal('hide');
	    			$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectChoosePackageByChoose'});
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
			$("#upLineOk").show();
			$("#downLineOk").show();
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
				productType:3,
			};
		id1 = "";
		AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductNowNum",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
	    		$('#myModal11').modal('hide');
	    		$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectChoosePackageByChoose'});
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
			productType:3,
			productState:productState,
		}
		id1 = "";
		productState = "";
		AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductState",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
    			$('#myModal44').modal('hide');
    			$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectChoosePackageByChoose'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				$('#myModal44').modal('hide');
				$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectChoosePackageByChoose'});
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
				productType:3,
				productNum: $("#productNumberIdMany").val(),
			};
		id1 = "";
		AjaxPostUtil.request({url:path+"/post/wechatCanteenProductManageController/updateProductNum",params:param,type:'json',callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
	    		$('#myModal22').modal('hide');
	    		$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectChoosePackageByChoose'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	
	// 取消现金支付
	$("body").on("click", "#cancelCashBut", function(e){
		$("#myModal5").modal("hide");
	});
	// 进行现金支付
	$("body").on("click", "#enterCashBut",function (e){
		var param = {
			productType : 3,
			productId : id1,
		};
		 id1 = "";
		AjaxPostUtil.request({url: path + "/post/wechatCanteenProductManageController/updateCashProple", params:param, type:'json', callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"付款成功",type:'success'});
	    		$('#myModal5').modal('hide');
	    		$('#message').bootstrapTable('refresh', {url: path+'/post/wechatCanteenProductManageController/selectChoosePackageByChoose'});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
}

var TableInit =  function (){
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.Init = function(){
		$("#message").bootstrapTable({
			url: path+'/post/wechatCanteenProductManageController/selectChoosePackageByChoose', // 请求后套的URL
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
            columns: [{
                checkbox: true
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
            	title:'套餐Logo',
            	width:'50',
            	align:'center',
            	formatter: function (value, row, index) {
            		if (isNull(value)){
            			return '<img alt="" src="../../assest/img/roleNoPic.png" class="dialogUserImg img-thumbnail" id = "packageLogo"/>';
            		}
            		return '<img alt="" src="'+ path + '/' + value+'" class="dialogUserImg img-thumbnail" id = "packageLogo"/>';
            	}
            },{
            	field:'packageName',
            	title:'套餐名称',
            	width:'50',
            	align:'center',
            },{
            	field:'packageDesc',
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
	//得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {
        	limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            packageName: $('#search').val(),
        };
        return temp;
    };
	return oTableInit;
};
window.EvenInit = {
	// 查看套餐 详情
	'click .RoleOfA': function (e, value, row, index) {
    	var rowId = row.id;
    	$('#myModal3').modal('show');
		var params = {
			id:rowId,
		};
		AjaxPostUtil.request({url:path+"/post/WechatProductPackageChooseController/selectOne",params:params,type:'json',callback:function(json){
			if(json.returnCode == 0){
				var states = json.bean.packageState;
				var packageType = json.bean.packageType;
				var productId = json.bean.productId;
				$("#packageName").html(json.bean.packageName);
				$("#createName").html(json.bean.adminNo);
				$("#packageCreateTime").html(json.bean.createTime);
				$("#choosePackagePrice").html(json.bean.packageGroupProductMinPrice + "元");
				// 套餐是否打折
				var count = json.bean.packagePrice;
				var disCounnt = new Array("一折", "二折", "三折", "四折", "五折", "六折", "七折", "八折", "九折", "原价");
				$("#choosePackageDiscount").html(disCounnt[count-1] + "出售");
				if(!isNull(json.bean.packageLogoPath)){
					$("#packageLogo").attr('src',path+"/"+json.bean.packageLogoPath);
				}
				if(json.bean.packageDesc != null && json.bean.packageDesc != '' && typeof(json.bean.packageDesc) != "undefined"){
					$("#packageDesc").html(json.bean.packageDesc);
				}else{
					$("#packageDesc").html("无");
				}
				$("#packagePrice").html(json.bean.packagePrice);
				$("#packageIntegral").html(json.bean.packageIntegral);
				if(json.bean.packageWetherDiscount == "N"){
					$("#packageWetherDiscount").html("不优惠");
				}else{
					$("#packageWetherDiscount").html("优惠");
				}
					
				if(json.bean.packageOpinion != null && json.bean.packageOpinion != '' && typeof(json.bean.packageOpinion) != "undefined"){
					$("#packageOpinion").html(json.bean.packageOpinion);
				}else{
					$("#packageOpinion").html("无");
				}
				
				// 套餐状态的判断
				if(states == 0){
					$("#packageState").html("创建");
				}else if(states == 1){
					$("#packageState").html("正在审核");
				}else if(states == 2){
					$("#packageState").html("审核通过");
				}else if(states == 3){
					$("#packageState").html("审核不通过");
				}else{
					
				}
				// 套餐类型的判断(是否优惠)
				if(packageType == 1){
					$("#packageType").html("永久有效");
				}else if(packageType == 2){
					$("#packageType").html(json.bean.packageStartTime + "~" +json.bean.packageEndTime+"有效");
				}else{
					
				}
				Handlebars.registerHelper("onePackageGroupProductMinPrice", function(v1, v2, option){
					if(v1 == 1)
						return '多选一';
					if(v1 == 2)
						return '多选	可选数量：'+ v2;
					if(v1 == 3)
						return '全选';
				});
				
				Handlebars.registerHelper("productStatus", function(v1, option){
					if(v1 == 4 || v1 == 3)
						return "下线";
					else if (v1 == 1)
						return "正在审核";
					else
						return "上线 ";
				});
				
				Handlebars.registerHelper("oneCreateTime", function(v1, option){
					if(v1.length>10)
						return v1.substring(0,10);
					else
						return v1;
				});
				Handlebars.registerHelper("oneOptionPath", function(v1, option){
					if(!isNull(v1))
						return path + "/" + v1;
					else
						return path + "/assest/img/roleNoPic.png";
				});
				var source = $("#productsBean").html();
				var template = Handlebars.compile(source);
				$("#productsList").html(template(json));
				
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
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	},
	// 上下线操作
	'click .RoleOfB':function(e, value, row, index) {
		id1 = row.id;
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
		$('#myModal1').modal('show');
		id1 = row.id;
	},
	// 重置可卖数量
	'click .RoleOfD':function(e, value, row, index) {
		// 显示总数量的模态框
		$("#myModal2").modal("show");
		$("#productNumberId").attr("value", row.productNum);
		id1 = row.id;
	},
	// 表示用现金进行支付
	'click .RoleOfE' : function (e, value, row , index){
		$("#myModal5").modal("show");
		// 获得对应商品的id
		id1 = row.id;
	}
}
function operateFormatter(value, row, index) {
	return [
			'<button type="button" class="RoleOfA btn btn-primary selectMaton" id="detail">详情</button>',
			'<button type="button" class="RoleOfB btn btn-info">上/下线</button>',
			'<button type="button" class="RoleOfC btn btn-success" id="sell">重置卖出数量</button>',
			'<button type="button" class="RoleOfD btn btn-default" id="buy">修改总数量</button>',
			'<button type="button" class="RoleOfE btn btn-danger">现金支付</button>'
	    ].join('');
}