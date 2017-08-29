
var code = "";

var base = null;

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	code = $.req("code");
	base = new Base64();
	AjaxPostUtil.request({url:path+"/gateway/WechatUserController/selectLatitudeAndLongtitude",params:{},type:'json',callback:function(json){
		if(json.returnCode==0){
			if(isNull(json.bean)){
				AjaxPostUtil.request({url:path+"/gateway/EmpowerWebpageController/getOpenidBycode",params:{code:code},type:'json',callback:function(jsonall){
					if(jsonall.returnCode==0){
						if(isNull(jsonall.bean.Location)){
							$("#city").html("郑州市");
						}else{
							$("#city").html(jsonall.bean.Location);
						}
						$("#headimgurl").attr("src",jsonall.bean.headimgurl);
						$("#wechatIntegral").html(jsonall.bean.wechatIntegral);
						$("#accountLogin").html(base.decode(jsonall.bean.nickname));
					}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
				}});
			}else{
				if(isNull(json.bean.Location)){
					$("#city").html("郑州市");
				}else{
					$("#city").html(json.bean.Location);
				}
				$("#wechatIntegral").html(json.bean.wechatIntegral);
				$("#headimgurl").attr("src",json.bean.headimgurl);
				$("#accountLogin").html(base.decode(json.bean.nickname));
			}
			eventInit();
		}else{
			location.href = 'sessionNull.html';
		}
	}});
}

function eventInit(){
	$('body').on('click',"#moreCity",function(e){
		location.href="allCity.html";
	});
	
	$('body').on('click',"#myAddress",function(e){
		location.href="deliveryAddress.html";
	});
	
	$('body').on('click', "#myBalance", function(e){
		qiao.bs.msg({msg:"还未开发，不能使用，请您稍后···",type:'danger'});
	});
	
	$('body').on('click', "#myCard", function(e){
		qiao.bs.msg({msg:"还未开发，不能使用，请您稍后···",type:'danger'});
	});
	
	$('body').on('click', "#scoreShop", function(e){
		qiao.bs.msg({msg:"还未开发，不能使用，请您稍后···",type:'danger'});
	});
}