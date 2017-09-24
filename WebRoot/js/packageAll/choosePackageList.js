
var packageStatusCode = "";
var packageName = "";

$(function(e) {
	dataInit();
	eventInit();
});

function dataInit(){
	packageDivInit(); // 套餐信息
}

// 套餐信息
function packageDivInit() {
	var param = {
		packageState:packageStatusCode,
		key:packageName,
	};
	
	AjaxPostUtil.request({url:path+"/post/WechatProductPackageChooseController/selectAllPackage",params:param,type:'json',callback:function(json){
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
					if(v1.length>10)
						return v1.substring(0,10);
					else
						return v1;
				});
				
				Handlebars.registerHelper("desc", function(v1, options){
					
					if(v1 != null && v1 != '' && typeof(v1) != "undefined"){
						if(v1.length>30)
							return v1.substring(0,30)+"...";
						else
							return v1;
					}else{
						return '无';
					}
						
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
		
		location.href = "choosePackageAdd.html";
	});
	
	// 查看套餐详情
	$("body").on("click", ".selectPackage",function(e){
		var rowId = $(this).parent().parent().find('div[class="packageMation"]').attr("rowId");
		$("#myModal2").modal('show');
		var params = {
			id : rowId,
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
				if(!isNull(json.bean.packageLogoPath)){
					$("#packageLogo").attr('src',path+"/"+json.bean.packageLogoPath);
				}
				$("#packageIntegral").html(json.bean.packageIntegral);
				var count = json.bean.packagePrice;
				var disCounnt = new Array("一折", "二折", "三折", "四折", "五折", "六折", "七折", "八折", "九折", "原价");
				$("#choosePackageDiscount").html(disCounnt[count-1] + "出售");
				
				if(json.bean.packageDesc != null && json.bean.packageDesc != '' && typeof(json.bean.packageDesc) != "undefined"){
					$("#packageDesc").html(json.bean.packageDesc);
				}else{
					$("#packageDesc").html("无");
				}
				
				$("#packagePrice").html(json.bean.packagePrice);
				$("#packageIntegral").html(json.bean.packageIntegral);
				$("#packageWetherDiscount").html(json.bean.packageWetherDiscount);
				
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
			AjaxPostUtil.request({url:path+"/post/WechatProductPackageChooseController/updateChoosePackageState",params:params,type:'json',callback:function(json){
				if(json.returnCode == 0){
					qiao.bs.msg({msg:"正在进行提审，请耐心等待···",type:'success'});
					setTimeout(packageDivInit,1000);// 表示一秒后进行刷新
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}})
			
		}, function(){
			
		});
	});
	
	$("body").on("click", ".deleteMation", function(e){
		var rowId = $(this).parent().parent().find('div[class="packageMation"]').attr("rowId");
		qiao.bs.confirm("确定删除该套餐吗？", function(){
			var params={
				id:rowId,	
			};
			AjaxPostUtil.request({url:path+"/post/WechatProductPackageChooseController/delectOne",params:params,type:'json',callback:function(json){
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
		location.href="choosePackageEdit.html?id="+rowId;
	});
	
}