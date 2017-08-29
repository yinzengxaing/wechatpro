var id1 = null;

$(function(e){
	dataInit();
});

function dataInit(){
	//1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
	eventInit();
}

function eventInit(){
	//查询按钮点击事件
	$('body').on('click', '#sub', function(e){
	    $('#massage').bootstrapTable('refresh', {url: path+'/post/WechatProductController/getCheckPendingList'});  
    });
	//审核通过按钮点击事件
	$('body').on('click', '#button1', function(e){
		if(isNull($("#productPass").val())){
			qiao.bs.msg({msg:"请填写审核意见",type:'danger'});
			return false;
		}
    	var params = {
    		id:id1,	
    		productOpinion:$("#productPass").val(),
    	};
    	AjaxPostUtil.request({url:path+"/post/WechatProductController/updateStatePass",params:params,type:'json',callback:function(json){
    		if(json.returnCode==0){
    			qiao.bs.msg({msg:"审核通过",type:'success'});
    			$('#myModal3').modal('hide');
    			$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatProductController/getCheckPendingList'});
    		}else{
    			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
    		}
    	}});
    });
	//审核不通过按钮点击事件
	$('body').on('click', '#button2', function(e){
		if(isNull($("#productNoPass").val())){
			qiao.bs.msg({msg:"请填写审核意见",type:'danger'});
			return false;
		}
    	var params = {
    		id:id1,	
    		productOpinion:$("#productNoPass").val()
    	};
    	AjaxPostUtil.request({url:path+"/post/WechatProductController/updateStateNoPass",params:params,type:'json',callback:function(json){
    		if(json.returnCode==0){
    			qiao.bs.msg({msg:"审核不通过",type:'success'});
    			$('#myModal4').modal('hide');
    			$('#massage').bootstrapTable('refresh', {url: path+'/post/WechatProductController/getCheckPendingList'});
    		}else{
    			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
    		}
    	}});
    });
}

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/WechatProductController/getCheckPendingList',             //请求后台的URL（*）
            method: 'post',                     //请求方式（*）
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
            pageList: [10],        //可供选择的每页的行数（*）
            height:700,
            showColumns: true,                  //是否显示所有的列
            showRefresh: true,                  //是否显示刷新按钮
            minimumCountColumns: 2,             //最少允许的列数
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            showToggle:true,                    //是否显示详细视图和列表视图的切换按钮
            cardView: false,                    //是否显示详细视图
            detailView: false,                  //是否显示父子表
            columns: [{
            	field: 'Number',
            	title: '序号',
            	width: '50',
            	align: 'center',
            	formatter: function (value, row, index) {
            		return index+1;
            	}
            }, {
                field: 'productName',
                title: '产品名称',
                align: 'center',
                width: '150',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'productState',
                title: '类别状态',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if(value==0){
    					return "创建";
    				}else if(value==1){
    					return "待审核";
    				}else if(value==2){
    					return "上线";
    				}else if(value==3){
    					return "审核不通过";
    				}else{
    					return "";
    				}
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'productDesc',
                title: '产品描述',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	if (isNull(value)){
                		return "无";
                	}else if(value.length > 20){
    					return value.substring(0,20)+"...";
    				}else{
    					return value;
    				}
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'adminNo',
                title: '创建人',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value+'</a>';
                }
            }, {
                field: 'createTime',
                title: '创建时间',
                align: 'center',
                width: '100',
                formatter: function (value, row, index) {
                	return '<a style="word-wrap:break-word;">'+value.substring(0,10)+'</a>';
                }
            }, {
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
            productName: $('#search').val(),
        };
        return temp;
    };
    
    return oTableInit;
}

window.EvenInit = {
    'click .RoleOfA': function (e, value, row, index) {
    	var rowId = row.id;
    	$('#myModal2').modal('show');
		var params = {
			id:rowId
			}
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
				var productState = json.bean.productState;
				if (productState == 0)
					$('#productState').html("创建"); 
				else if (productState == 1)
					$('#productState').html("提审"); 
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
				if (productOpinion == null || productOpinion == "")
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
							//对商品所在套餐logo进行修饰
							Handlebars.registerHelper("packageLogo",function(v1,options){
									return path+"/"+v1;
							});
							//对套餐描述进行修饰
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
							//对套餐图片进行修饰
							Handlebars.registerHelper("packageLogo",function(v1,options){
								return path+"/"+v1;
						});
							//对套餐描述进行修饰
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
		}
		});
    },
    'click .RoleOfB': function (e, value, row, index) {
    	$("#productPass").val("");//清空上次审核意见的内容
    	$('#myModal3').modal('show');
    	id1=row.id;
    },
    'click .RoleOfC': function (e, value, row, index) {
    	$("#productNoPass").val("");//清空上次审核意见的内容
    	$('#myModal4').modal('show');
    	id1=row.id
    }
};
function operateFormatter(value, row, index) {
    return [
        '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">详情</button>',
        '<button type="button" class="RoleOfB btn btn-default  btn-sm" style="margin-right:15px;">审核通过</button>',
        '<button type="button" class="RoleOfC btn btn-default  btn-sm" style="margin-right:15px;">审核不通过</button>',
    ].join('');
}
