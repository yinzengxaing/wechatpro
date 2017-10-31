var id = ""; 
var orderNumber = "";
var orderType="";
var count = 0;
$(function(e){
    dataInit();
});
function dataInit(){
	var oTable = new TableInit();
	oTable.Init();
	eventInit();
}

function eventInit(){
	// 按照条件来查找退单类型
	$("body").on("click", "#seachBtn", function(e){
		$("#message").bootstrapTable("refresh", {url : path + "/post/WechatOutRefundController/getRefundList"});
	});
}


var TableInit =  function (){
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.Init = function(){
		$("#message").bootstrapTable({
			url: path+'/post/WechatOutRefundController/getRefundList', // 请求后套的URL
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
            	field: 'Number',
            	title: '序号',
            	width: '50',
            	align: 'center',
            	formatter: function (value, row, index) {
            		return index+1;
            	}
            },{
            	field: "orderNumber",
            	title: "订单号",
            	width: "80",
            	align: "center",
            },{
            	field: "refundTime",
            	title: "申请时间",
            	width: 50,
            	align: "center",
            },{
            	field: "refundDesc",
            	title: "申请原因",
            	width: "100",
            	align: "center",
            },{
            	field: "orderPrice",
            	title: "订单价格",
            	width: "100",
            	align: "center",
            },{
            	field: "phoneNumber",
            	title: "客户联系方式",
            	width: "100",
            	align: "center",
            },{
            	field:"processState",
            	title:"处理状态",
            	width: "50",
            	align:"center",
            	formatter: function (value, row, index) {
            		if(value == 0)
            			return "<font>未处理</font>";
            		else if(value == 1)
            			return "<font>同意退款</font>";
            		else if(value == 2){
            			return "<font>拒绝退款</blue>";
            		}
                }
            },{
            	field: 'operate',
	            title: '操作',
	            width: '150',
	            align: "center",
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
	// 得到查询的参数
	oTableInit.queryParams = function(params){
		var temp = {
			limit : params.limit,
			offset : params.offset,
			processState : $("#selectOrderType").val()
		}
		return temp;
	};
	return oTableInit;
};

window.EvenInit = {
	// 查看订单的详情
	"click .RoleOfA":function (e, value, row, index){
		id = row.orderId; // 表示订单的id
		orderNumber = row.orderNumber; // 通过获取的订单id来进行查询订单的详情
		$("#myModal3").modal("show");
		var param = {
				orderId : id,
				orderNumber : orderNumber,
		}
		AjaxPostUtil.request({url: path + "/post/wechatOrderManagerController/selectOrderFormInfo", params:param, type:"json", callback:function(json){
			if(json.returnCode == 0){
				$("#orderNumber").html(json.bean.orderNumber);
				$("#wetherPaymentTime").html(json.bean.wetherPaymentTime);
				var wetherPaymen = json.bean.wetherPayment;// 是否支付
				if(wetherPaymen == 0 ){
					$("#wetherPayment").html("未支付");
				}else{
					$("#wetherPayment").html("已支付");
				}
				$("#orderType").html(json.bean.orderType); // 表订单类型
				$("#orderEatTime").html(json.bean.orderEatTime);
				$("#orderPrice").html(json.bean.orderPrice + "元"); // 订单价格
				$("#orderDesc").html(json.bean.orderDesc);
				// 遍历订单中的信息
				// 判断logo
				Handlebars.registerHelper("productlogo", function(v1, options){
					if(!isNull(v1)){
						return path + "/" + v1;
					}else{
						return path + "/assest/img/roleNoPic.png";
					}
				});
				var source = $("#productsBean").html();
				var template = Handlebars.compile(source);
				$("#productsList").html(template(json));
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	},
	// 同意退款
	"click .RoleOfB":function (e, value, row, index){
		var orderNumber = row.orderNumber; 
		var refundDesc = row.refundDesc;
		qiao.bs.confirm("确定同意退款？",function(){
			var params = {
				orderNumber :orderNumber,
				processState:1,
				refundDesc:refundDesc
			};
			AjaxPostUtil.request({url:path+"/post/WechatOutRefundController/dealWithRefund",params:params,type:'json',callback:function(json){
				if (json.returnCode == 0){
					qiao.bs.msg({msg:"退款申请成功",type:'success'});
					$("#message").bootstrapTable("refresh", {url : path + "/post/WechatOutRefundController/getRefundList"});
				}
			}
			});	
		},function(){});
	},
	
	// 拒绝退款
	"click .RoleOfC":function (e, value, row, index){
		var orderNumber = row.orderNumber; // 通过获取的订单id来进行查询订单的详情
		qiao.bs.confirm("确定拒绝退款？",function(){
			var params = {
				orderNumber :orderNumber,
				processState:2
			};
			AjaxPostUtil.request({url:path+"/post/WechatOutRefundController/dealWithRefund",params:params,type:'json',callback:function(json){
				if (json.returnCode == 0){
					qiao.bs.msg({msg:"退款申请已被拒绝",type:'success'});
					$("#message").bootstrapTable("refresh", {url : path + "/post/WechatOutRefundController/getRefundList"});
				}
			}
			});	
		},function(){});
	}
}

function operateFormatter(value, row, index) {
	if(row.processState == 0){
		return [
		        '<button type="button" class="RoleOfA btn btn-primary selectMaton">订单详情</button>',
		        '<button type="button" class="RoleOfB btn btn-success">同意退款</button>',
		        '<button type="button" class="RoleOfC btn btn-error">拒绝退款</button>',
		        ].join('');
	}else{
		return [
		        '<button type="button" class="RoleOfA btn btn-primary selectMaton">订单详情</button>',
		        ].join('');
	}
	
}