var id = ""; 
var orderNumber = "";
var orderType="";
var count = 0;
$(function(e){
    dataInit();
});
function dataInit(){
	AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/selectSession",params:{},type:'json',callback:function(jsonSession){
		if(jsonSession.returnCode==0){
			var goEasy = new GoEasy({
			    appkey: 'BC-c5e986fba5d14d38b2b2c5b4b072fc8c'
			});
			goEasy.subscribe({
				channel: jsonSession.bean.id+"",
				onMessage: function (message) {
					count = count * 1 + 1;
					$("#countNumber").html("您有" +  count + "份新订单");
					$("#countNumber").attr("style", "display:block");
					qiao.bs.msg({msg:message.content,type:'success'});
				},
				onSuccess: function () {
					var oTable = new TableInit();
					oTable.Init();
				    eventInit();
					qiao.bs.msg({msg:"消息接收机制已启动",type:'success'});
				},
				onFailed: function (error) {
					alert("Channel订阅失败, 错误编码：" + error.code + " 错误信息：" + error.content)
				}
			});

		}else{
			qiao.bs.msg({msg:jsonSession.returnMessage,type:'danger'});
		}
	}});
}

function eventInit(){
	
	// 点击提示后隐藏
	$("body").on("click", "#countNumber",  function(e){
		$("#countNumber").attr("style", "display:none");
		$("#message").bootstrapTable("refresh", {url : path + "/post/wechatOrderManagerController/selectPaiedOrderForm"});
		count = 0;
		$("#selectOrderType").attr("value", "");
	});
	
	// 表示取消送给顾客
	$("body").on("click", "#concellMaked", function(e){
		$('#myModal1').modal('hide');
	});
	// 表示确定送给顾客
	$("body").on("click", "#enterMaked", function (e){
		var param = {
				orderId:id,
				orderNumber : orderNumber,
		};
		AjaxPostUtil.request({url:path+"/post/wechatOrderManagerController/updateMake", params:param, type:"json", callback:function(json){
			if(json.returnCode == 0){
				qiao.bs.msg({msg:"修改成功",type:'success'});
				$("#message").bootstrapTable("refresh", {url : path + "/post/wechatOrderManagerController/selectPaiedOrderForm"});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
			$('#myModal1').modal('hide');
		}});
	});
	
	// 按照条件来进行查找套餐
	$("body").on("click", "#seachBtn", function(e){
		$("#message").bootstrapTable("refresh", {url : path + "/post/wechatOrderManagerController/selectPaiedOrderForm"});
	});
}


var TableInit =  function (){
	var oTableInit = new Object();
	// 初始化Table
	oTableInit.Init = function(){
		$("#message").bootstrapTable({
			url: path+'/post/wechatOrderManagerController/selectPaiedOrderForm', // 请求后套的URL
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
            	field: "dayNo",
            	title: "取餐号",
            	width: 50,
            	align: "center",
            },{
            	field: "wetherPaymentTime",
            	title: "下单时间",
            	width: "100",
            	align: "center",
            },{
            	field:"orderType",
            	title:"订单类型",
            	width: "50",
            	align:"center",
            	formatter: function (value, row, index) {
            		if(value == 1)
            			return "<font>立即堂食</font>";
            		else if(value == 2)
            			return "<font>稍后堂食</font>";
            		else if(value == 3){
            			return "<font>立即外带</blue>";
            		}else if(value == 4 ){
            			return "<font>稍后外带</blue>";
            		}else{
            			return "<font>外卖</blue>";
            		}
                }
            },{
            	field:"orderEatTime",
            	title:"就餐时间",
            	width:"200",
            	align:"center",
            	formatter: function (value, row, index) {
            		if(value=="" || value == null){
            			return "立即就餐";
            		}else{
            			return value;
            		}	
            	}
            },{
            	field: 'phoneNumber',
	            title: '手机号',
	            width: '150',
	            align: "center",
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
			orderType : $("#selectOrderType").val(),
		}
		return temp;
	};
	return oTableInit;
};

window.EvenInit = {
	// 查看订单的详情
	"click .RoleOfA":function (e, value, row, index){
		id = row.id; // 表示订单的id
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
	// 表示做给顾客
	"click .RoleOfB":function (e, value, row, index){
		id = row.id; // 表示订单的
		orderNumber = row.orderNumber; // 通过获取的订单id来进行查询订单的详情
		$('#myModal1').modal('show');
	}
}

function operateFormatter(value, row, index) {
	return [
			'<button type="button" class="RoleOfA btn btn-primary selectMaton">订单详情</button>',
			'<button type="button" class="RoleOfB btn btn-info">已做好</button>',
	    ].join('');
}