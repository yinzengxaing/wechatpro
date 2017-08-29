
var packageStatusCode = ""; // 套餐的状态
var packageName = "";	// 套餐的名称

$(function(e) {
	dataInit();
});

function dataInit(){
	packageDivInit(); // 套餐信息
	eventInit();
}

// 套餐信息
function packageDivInit() {
	var param = {
		packageState:packageStatusCode,
		packageName:packageName,
	};
	AjaxPostUtil.request({url:path+"/post/wechatProductPackageController/selectAllPackage",params:param,type:'json',callback:function(json){
		if(json.returnCode == 0){
			if(json.total != 0){
				Handlebars.registerHelper("packagestate", function(v1, options){
					if(v1 == 0)
						return "创建";
					else if(v1 == 1)
						return "正在审核";
					else if(v1 == 2)
						return "上线";
					else if(v1 == 3)
						return "审核不通过";
					else 
						return "失效";
				});
				
				Handlebars.registerHelper("creattime", function(v1, options){
					if(isNull(v1))
						return "无";
					else if(v1.length>10)
						return v1.substring(0,10);
					else
						return v1;
				});
				
				Handlebars.registerHelper("desc", function(v1, options){
					 if(isNull(v1))
						return "无";
					 else if(v1.length>30)
						return v1.substring(0,30)+"...";
					else
						return v1;
				});
				Handlebars.registerHelper("packageTp",function(v1, v2, v3, options){
					if(v1 == 1)
						return "永久有效";
					else if(v1 == 2){
						return  v2 + "~" + v3 + "有效";
					}
				});
				Handlebars.registerHelper("packageButtton", function(v1, option){
					if(v1 == 0){
						return '<button type="button" class="btn btn-primary selectPackage">详情</button>'
						+'<button type="button" class="btn btn-default editPackage">编辑</button>'
						+'<button type="button" class="btn btn-info arraignment">提审</button>'
						+'<button type="button" class="btn btn-danger deleteMation">删除</button>';
					}else if(v1 == 1){
						return '<button type="button" class="btn btn-primary selectPackage">详情</button>';
					}else if(v1 == 2){
						return '<button type="button" class="btn btn-primary selectPackage">详情</button>'
						+'<button type="button" class="btn btn-default editPackage">编辑</button>'
						+'<button type="button" class="btn btn-danger deleteMation">删除</button>';
					}else if(v1 == 3){
						return '<button type="button" class="btn btn-primary selectPackage">详情</button>'
						+'<button type="button" class="btn btn-default editPackage">编辑</button>'
						+'<button type="button" class="btn btn-danger deleteMation">删除</button>';
					}else{
						
					}
				});
				
				// 套餐的logo
				Handlebars.registerHelper("packagelogo", function(v1, option){
					if(v1!=null)
						return path + "/" + v1;
					else
						return path + "/assest/img/roleNoPic.png";
				});
				// 初始化 
				packageStatusCode = "";
				packageName = "";
				var source = $("#packageListBean").html();
				var template = Handlebars.compile(source);
				$("#packageListDiv").html(template(json));
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
				// 查询结果为空的时候，将div设置为空，并显示不存在内容的图片
				$("#packageListDiv").empty();
				$("#packageListDiv").append("<img src='../../assest/img/not-available.png'>");
			}
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function eventInit() {
	
	$("body").on('click', '#addPackage', function(e) {
		location.href = "addPackage.html";
	});
	
	// 查看套餐详情
	$("body").on("click", ".selectPackage",function(e){
		var rowId = $(this).parent().parent().find('div[class="packageMation"]').attr("rowId");
		$("#myModal2").modal('show');
		var params = {
			id : rowId,
		};
		AjaxPostUtil.request({url:path+"/post/wechatProductPackageController/selectPackageById",params:params,type:'json',callback:function(json){
			if(json.returnCode == 0){
				var states = json.bean.packageState;
				var packageType = json.bean.packageType;
				var productId = json.bean.productId;
				$("#packageName").html(json.bean.packageName);
				$("#packagePrice").html(json.bean.packagePrice + "元");
				$("#packageIntegral").html(json.bean.packageIntegral);
				// Logo
				if(!isNull(json.bean.optionPath)){
					$("#packageLogo").attr('src',path+"/"+json.bean.optionPath);
				}
				// 显示是否优惠
				if(json.bean.packageWetherDiscount == "N")
					$("#packageWetherDiscount").html("不优惠");
				else
					$("#packageWetherDiscount").html("优惠");
				// 套餐描述
				if(isNull(json.bean.packageDesc))
					$("#packageDesc").html("无");
				else
					$("#packageDesc").html(json.bean.packageDesc);
				$("#packageIntegral").html(json.bean.packageIntegral);
				$("#packageCreateTime").html(json.bean.packageCreateTime);
				if(isNull(json.bean.packageOpinion))
					$("#packageOpinion").html("无");
				else
					$("#packageOpinion").html(json.bean.packageOpinion);
				$("#createName").html(json.bean.createName);
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
				// 不为空的话设置为查询到的地址
				if(!isNull(json.bean.optionPath))
					$("#optionPath").attr("src", path + "/" +json.bean.optionPath);
				// 套餐类型的判断(是否优惠)
				if(packageType == 1){
					$("#packageType").html("永久有效");
				}else if(packageType == 2){
					$("#packageType").html(json.bean.packageStartTime + "~" +json.bean.packageEndTime+"有效");
				}else{
					
				}
				var productBindId = {
					productId:productId,
				};
				AjaxPostUtil.request({url:path+"/post/wechatProductPackageController/selectPackageDetailInfoById",params:productBindId,type:'json',callback:function(json){
					if(json.returnCode == 0){
						Handlebars.registerHelper("packagelogo", function(v1, option){
							if(isNull(v1))
								return path + "/assest/img/roleNoPic.png";
							else
								return path + "/" + v1;
						});
						Handlebars.registerHelper("productStatus", function(v1, option){
							if(v1 == 4 || v1 == 3)
								return "下线";
							else if (v1 == 1)
								return "正在审核";
							else
								return "上线 ";
						});
						var source=$("#productsBean").html();
						var template = Handlebars.compile(source);
						$("#productsList").html(template(json));
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
					            }
					        });
					    });*/
					}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
				}});
			}else{
				qiao.bs.msg({msg:json.returnMessage,type:'danger'});
			}
		}});
	});
	// 通过商品名称进行模糊查询
	$("body").on("click", "#selectPackageList",function(e){
		packageStatusCode = $("#NameStatus").val();
		packageName = $("#inPackageName").val();
		packageDivInit();
	});
	// 进行提审操作
	$("body").on("click", '.arraignment',function(e){
		var rowId = $(this).parent().parent().find('div[class="packageMation"]').attr("rowId");
		qiao.bs.confirm("确定要进行提审吗？", function(){
			var params = {
				id:rowId,
			};
			AjaxPostUtil.request({url:path+"/post/wechatProductPackageController/updatePackageState",params:params,type:'json',callback:function(json){
				if(json.returnCode == 0){
					qiao.bs.msg({msg:"正在进行提审，请耐心等待···",type:'success'});
					setTimeout(packageDivInit,1000);// 表示一秒后进行刷新
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
			
		}, function(){
			
		});
	});
	
	// 删除操作
	$("body").on("click", ".deleteMation", function(e){
		var rowId = $(this).parent().parent().find('div[class="packageMation"]').attr("rowId");
		qiao.bs.confirm("确定要删除此项吗？", function(){
			var params={
				id:rowId,	
			};
			AjaxPostUtil.request({url:path+"/post/wechatProductPackageController/deletePackageById",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					qiao.bs.msg({msg:"删除成功",type:"success"});
					setTimeout(packageDivInit,1000);// 一秒后刷新
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}, function(){
			
		});
	});
	
	// 编辑操作
	$("body").on("click",".editPackage",function(e){
		var rowId = $(this).parent().parent().find('div[class="packageMation"]').attr("rowId");
		location.href="packageEdit.html?id="+rowId;
	});
}