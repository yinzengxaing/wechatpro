
$(function(e){
	dataInit();
});

function dataInit(){
	$('#addCard').css({"display":"none"});
	getData();
	eventInit();
}

function eventInit(){

	//添加
	$('body').on('click', '#addCard', function(e){
		location.href="addWechatMembershipCard.html";
	});	
    
	//删除
	$('body').on('click', '#deleteBean', function(e){
		var card_id = $(this).attr("rowId");
		qiao.bs.confirm("是否删除该会员卡？",function(){
			AjaxPostUtil.request({url:path+"/post/WechatMembershipController/deleteWechatMembership",params:{card_id:card_id},type:'json',callback:function(json){
				if(json.returnCode==0){
					getData();
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
				}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
						}
				}});
			},function(){});
	});	
	
	//编辑
	$('body').on('click', '#editBean', function(e){
		var card_id = $(this).attr("rowId");
		location.href = "updateWechatMembershipCardMessage.html?card_id="+card_id;
	});
	
	$('body').on('click', '#updateBean', function(e){
		AjaxPostUtil.request({url:path+"/post/WechatMembershipController/checkWechatMemberships",params:{},type:'json',callback:function(jsons){
			if(jsons.returnCode==0){
				getData();
			}
		}});
		
	});
	
	//详情
	$('body').on('click', '#selectBean', function(e){
		$('#myModal2').modal('show');
		var card_id = $(this).attr("rowId");
		AjaxPostUtil.request({url:path+"/post/WechatMembershipController/selectWechatMembership",params:{card_id:card_id},type:'json',callback:function(json){
			if(json.returnCode==0){
				$("#title").html(json.bean.title);
				$("#card_id").html(json.bean.card_id);
				$("#card_url").html(json.bean.card_url);
				$("#brand_name").html(json.bean.brand_name);
				$("#cost_money_unit").html(json.bean.cost_money_unit);
				$("#increase_bonus").html(json.bean.increase_bonus);
				$("#least_money_to_use_bonus").html(json.bean.least_money_to_use_bonus);
				$("#cost_bonus_unit").html(json.bean.cost_bonus_unit);
				$("#reduce_money").html(json.bean.reduce_money);
				$("#type").html(json.bean.type);
				if(json.bean.type == "永久有效"){
					$("#begintimestamp").hide();
					$("#endtimestamp").hide();
				}else{
					$("#begintimestamp").show();
					$("#endtimestamp").show();
				}
				$("#use_all_locations").html(json.bean.use_all_locations);
				$("#service_phone").html(json.bean.service_phone);
				$("#text").html(json.bean.text);
				$("#notice").html(json.bean.notice);
				$("#prerogative").html(json.bean.prerogative);
				$("#description").html(json.bean.description);
				$("#quantity").html(json.bean.quantity);
				$("#business_service").html(json.bean.business_service);
				$("#discount").html(json.bean.discount);
				$("#image_url").attr("src",isUndefineReturnNull(json.bean.image_url));
				$("#background_pic_url").attr("src",isUndefineReturnNull(json.bean.background_pic_url));
				$("#logo_url").attr("src",isUndefineReturnNull(json.bean.logo_url));
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
			}});
		});
}

function getData(){
	AjaxPostUtil.request({url:path+"/post/WechatMembershipController/selectWechatMemberships",params:{},type:'json',callback:function(jsons){
		if(jsons.returnCode==0){
			if(isNull(jsons.rows)){
				$('#addCard').css({"display":"inline"});
			}else{
				Handlebars.registerHelper("compare", function(v1,options){
					if(v1 == "Color010"||v1 == "Color020"){
						return path+"/assest/img/greencard.png";
					}else if(v1 == "Color030"||v1 == "Color040"){
						return path+"/assest/img/bluecard.png";
					}else if(v1 == "Color050"||v1 == "Color060"){
						return path+"assest/img/pinkcard.png";
					}else{
						return path+"/assest/img/yellowcard.png";
					}
					});
				var source = $("#membersBean").html();  
				var template = Handlebars.compile(source);
				$("#membersFrom").html(template(jsons));
				}
			}
		}});
}

