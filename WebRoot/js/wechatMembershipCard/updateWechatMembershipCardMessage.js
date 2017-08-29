var background_pic_url = null;
var logo_url = null;
var card_id = null;
var blogo_url = null;
var bimage_url = null;
var bbackground_pic_url = null;

$(function(e){
	dataInit();
	eventInit();
});
function Interaction(){
	$('body').on('change', '#discount', function(e){
		$("#vdiscount").text($(this).val());
	});
	$('body').on('change', '#service_phone', function(e){
		$("#vservice_phone").text($(this).val());
	});
	$('body').on('change', '#cost_money_unit', function(e){
		$("#vcost_money_unit").text($(this).val());
	});
	$('body').on('change', '#cost_bonus_unit', function(e){
		$("#vcost_bonus_unit").text($(this).val());
	});
	$('body').on('change', '#increase_bonus', function(e){
		$("#vincrease_bonus").text($(this).val());
	});
	$('body').on('change', '#least_money_to_use_bonus', function(e){
		$("#vleast_money_to_use_bonus").text($(this).val());
	});
	$('body').on('change', '#max_reduce_bonus', function(e){
		$("#vmax_reduce_bonus").text($(this).val());
	});
	$('body').on('change', '#reduce_money', function(e){
		$("#vreduce_money").text($(this).val());
	});
	$('body').on('change', '#description', function(e){
		$("#vdescription").text($(this).val());
	});
	$('body').on('change', '#prerogative', function(e){
		$("#vprerogative").text($(this).val());
	});
}

function dataInit(){
	receiveData();
	card_id = $.req("card_id");
	AjaxPostUtil.request({url:path+"/post/WechatMembershipController/selectWechatMembership",params:{card_id:card_id},type:'json',callback:function(json){
		if(json.returnCode==0){
			$("#title").val(json.bean.title);
			$("#brand_name").val(json.bean.brand_name);
			$("#service_phone").val(json.bean.service_phone);
			$("#vservice_phone").text(json.bean.service_phone);
			$("#discount").val(json.bean.discount);
			$("#vdiscount").text(json.bean.discount);
			$("#quantity").val(json.bean.quantity);
			$("#cost_money_unit").val(json.bean.cost_money_unit);
			$("#vcost_money_unit").text(json.bean.cost_money_unit);
			$("#cost_bonus_unit").val(json.bean.cost_bonus_unit);
			$("#vcost_bonus_unit").text(json.bean.cost_bonus_unit);
			$("#increase_bonus").val(json.bean.increase_bonus);
			$("#vincrease_bonus").text(json.bean.increase_bonus);
			$("#least_money_to_use_bonus").val(json.bean.least_money_to_use_bonus);
			$("#vleast_money_to_use_bonus").text(json.bean.least_money_to_use_bonus);
			$("#max_reduce_bonus").val(json.bean.max_reduce_bonus);
			$("#vmax_reduce_bonus").text(json.bean.max_reduce_bonus);
			$("#reduce_money").val(json.bean.reduce_money);
			$("#vreduce_money").text(json.bean.reduce_money);
			$("#description").val(json.bean.description);
			$("#vdescription").html(json.bean.description);
			$("#notice").val(json.bean.notice);
			$("#text").val(json.bean.text);
			$("#prerogative").val(json.bean.prerogative);
			$("#vprerogative").text(json.bean.prerogative);
			background_pic_url = json.bean.background_pic_url;
			logo_url = json.bean.logo_url;
			var business_service = "";
			if(json.bean.BIZ_SERVICE_DELIVER == true){
				$('#BIZ_SERVICE_DELIVER').attr("checked",'checked');
				business_service += "外卖服务    ";
				}
			if(json.bean.BIZ_SERVICE_FREE_PARK == true){
				$('#BIZ_SERVICE_FREE_PARK').attr("checked",'checked');
				business_service += "停车位    ";
				}
			if(json.bean.BIZ_SERVICE_WITH_PET == true){
				$('#BIZ_SERVICE_WITH_PET').attr("checked",'checked');
				business_service += "可带宠物    ";
			}
			if(json.bean.BIZ_SERVICE_FREE_WIFI == true){
				$('#BIZ_SERVICE_FREE_WIFI').attr("checked",'checked');
				business_service += "免费wifi  ";
			}
			$("#vbusiness_service").text(business_service);
			if(json.bean.color=="Color010"){
				$("input[name='optionsRadiosinline1']:eq(0)").attr("checked",'checked');
			}else if(json.bean.color=="Color020"){
				$("input[name='optionsRadiosinline1']:eq(1)").attr("checked",'checked');
			}else if(json.bean.color=="Color030"){
				$("input[name='optionsRadiosinline1']:eq(2)").attr("checked",'checked');
			}else if(json.bean.color=="Color040"){
				$("input[name='optionsRadiosinline1']:eq(3)").attr("checked",'checked');
			}else if(json.bean.color=="Color050"){
				$("input[name='optionsRadiosinline1']:eq(4)").attr("checked",'checked');
			}else if(json.bean.color=="Color060"){
				$("input[name='optionsRadiosinline1']:eq(5)").attr("checked",'checked');
			}else if(json.bean.color=="Color070"){
				$("input[name='optionsRadiosinline1']:eq(6)").attr("checked",'checked');
			}else if(json.bean.color=="Color080"){
				$("input[name='optionsRadiosinline1']:eq(7)").attr("checked",'checked');
			}else if(json.bean.color=="Color090"){
				$("input[name='optionsRadiosinline1']:eq(8)").attr("checked",'checked');
			}else if(json.bean.color=="Color100"){
				$("input[name='optionsRadiosinline1']:eq(9)").attr("checked",'checked');
			}
			
			if(json.bean.use_all_locations=="是"){
				$("input[name='optionsRadiosinline']:eq(0)").attr("checked",'checked');
			}else{
				$("input[name='optionsRadiosinline']:eq(1)").attr("checked",'checked');
			}
			$('#background_pic_url').attr("src",path+"/"+json.bean.bbackground_pic_url);
			$('#logo_url').attr("src",path+"/"+json.bean.blogo_url);
			$('#image_url').attr("src",path+"/"+json.bean.bimage_url);
			$('#quantity').attr("disabled",true);
			$('#brand_name').attr("disabled",true);
			$('#text').attr("disabled",true);
			$('.serviceItem').attr("disabled",true);
			$('image_url').attr("disabled",true);
			
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
	}});
}

function eventInit(){
	Interaction();
	$(".form_datetime").datetimepicker({
        format: "dd MM yyyy - hh:ii",
        autoclose: true,
        todayBtn: true,
        startDate: "2013-02-14 10:00",
        minuteStep: 10
    });
	$('body').on('click', '#saveBean', function(e){
		var use_all_locations  = $('input[name="optionsRadiosinline"]:checked').val();
		
		var color  = $('input[name="optionsRadiosinline1"]:checked').val();
		
		if(isNull($("#title").val())){
			qiao.bs.msg({msg:"卡券名称不能为空!",type:'danger'});
			return;
		}else if($("#title").val().length>9){
			qiao.bs.msg({msg:"卡券名称长度必须小于9个字！",type:'danger'});
			return;
		}else if(isNull($("#service_phone").val())){
			qiao.bs.msg({msg:"手机号名称不能为空！",type:'danger'});
			return;
		}else if(isNull($("#discount").val())){
			qiao.bs.msg({msg:"卡券折扣不能为空！",type:'danger'});
			return;
		}else if(!isdiscount($("#discount").val())){
			qiao.bs.msg({msg:"卡券折扣应是0~10的整数！",type:'danger'});
			return;
		}else if(isNull($("#cost_money_unit").val())){
			qiao.bs.msg({msg:"消费金额不能为空！",type:'danger'});
			return;
		}else if(!isNumber($("#cost_money_unit").val())){
			qiao.bs.msg({msg:"消费金额不合法",type:'danger'});
			return;
		}else if(isNull($("#increase_bonus").val())){
			qiao.bs.msg({msg:"增加积分不能为空！",type:'danger'});
			return;
		}else if(!isNumber($("#increase_bonus").val())){
			qiao.bs.msg({msg:"增加积分不合法",type:'danger'});
			return;
		}else if(isNull($("#cost_bonus_unit").val())){
			qiao.bs.msg({msg:"积分抵扣不能为空！！",type:'danger'});
			return;
		}else if(!isNumber($("#cost_bonus_unit").val())){
			qiao.bs.msg({msg:"积分抵扣不合法",type:'danger'});
			return;
		}else if(isNull($("#reduce_money").val())){
			qiao.bs.msg({msg:"抵扣金额不能为空！！",type:'danger'});
			return;
		}else if(!isNumber($("#reduce_money").val())){
			qiao.bs.msg({msg:"抵扣金额不合法",type:'danger'});
			return;
		}else if(isNull($("#least_money_to_use_bonus").val())){
			qiao.bs.msg({msg:"抵扣条件不能为空！！",type:'danger'});
			return;
		}else if(!isNumber($("#least_money_to_use_bonus").val())){
			qiao.bs.msg({msg:"抵扣条件不合法",type:'danger'});
			return;
		}else if(isNull($("#max_reduce_bonus").val())){
			qiao.bs.msg({msg:"使用上限不能为空！！",type:'danger'});
			return;
		}else if(!isNumber($("#max_reduce_bonus").val())){
			qiao.bs.msg({msg:"使用上限不合法",type:'danger'});
			return;
		}else if(isNull($("#description").val())){
			qiao.bs.msg({msg:"使用说明不能为空！！",type:'danger'});
			return;
		}else if($("#description").val().length>1024){
			qiao.bs.msg({msg:"使用说明不能超过1024个字",type:'danger'});
			return;
		}else if(isNull($("#notice").val())){
			qiao.bs.msg({msg:"使用提醒不能为空！！",type:'danger'});
			return;
		}else if($("#notice").val().length>16){
			qiao.bs.msg({msg:"使用提醒不能超过16个字",type:'danger'});
			return;
		}else if(isNull($("#prerogative").val())){
			qiao.bs.msg({msg:"特权说明不能为空！！",type:'danger'});
			return;
		}else if($("#prerogative").val().length>1024){
			qiao.bs.msg({msg:"特权说明不能超过1024个字",type:'danger'});
			return;
		}else if(isNull(use_all_locations)){
			qiao.bs.msg({msg:"未选择是否支持全部门店!",type:'danger'});
			return;
		}else if(isNull(color)){
			qiao.bs.msg({msg:"卡券颜色未选择!",type:'danger'});
			return;
		}else if(isNull(background_pic_url)){
			qiao.bs.msg({msg:"未上传卡券背景图片!",type:'danger'});
			return;
		}else if(isNull(logo_url)){
			qiao.bs.msg({msg:"未上传商户logo图片!",type:'danger'});
			return;
		}else{
			var params = {
					title:$("#title").val(),
					service_phone:$("#service_phone").val(),
					discount:$("#discount").val(),
					cost_money_unit:$("#cost_money_unit").val(),
					cost_bonus_unit:$("#cost_bonus_unit").val(),
					increase_bonus:$("#increase_bonus").val(),
					least_money_to_use_bonus:$("#least_money_to_use_bonus").val(),
					max_reduce_bonus:$("#max_reduce_bonus").val(),
					reduce_money:$("#reduce_money").val(),
					description:$("#description").val(),
					notice:$("#notice").val(),
					prerogative:$("#prerogative").val(),
					use_all_locations:use_all_locations,
					color:color,
					background_pic_url:background_pic_url,
					logo_url:logo_url,
					blogo_url:blogo_url,
					bbackground_pic_url:bbackground_pic_url,
					card_id:card_id
			};
			AjaxPostUtil.request({url:path+"/post/WechatMembershipController/updateWechatMembership",params:params,type:'json',callback:function(json){
				if(json.returnCode==0){
					$('#saveBean').css({"display":"none"});
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
					
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'danger'});
				}
			}});
		}
		
	});

	$('body').on('click', '#returnBack', function(e){
		location.href="wechatMembershipCard.html";
	});
	
	//上传会员卡背景图
	$("#imgFilesFivebackground").uploadPreview({ Img: "background_pic_url"});
	$("#imgFilesFivebackground").fileupload({
		url : path + "/post/UploadController/insertImgFileWechatOne?imgType=6",
		disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
        autoUpload: true,//是否自动上传
        maxFileSize: 999000,
        imageMaxWidth: 1000,
        imageMaxHeight: 800,
        imageQuality:0.6,
        dataType:'json',
        acceptFileTypes: /(\.|\/)(jpe?g|png|svg)$/i
	}).on('fileuploadadd', function (e, data) {
		var uploadFile = data.files[0];
		fileName = uploadFile.name;
		var reg = ".(jpg|png|jpeg|svg)$";
		var re = new RegExp(reg);
		if (fileName.length > 50) {
			qiao.bs.msg({msg:"文件名长度不能大于50",type:'danger'});
			$.staticPic('background_pic_url','logoDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			qiao.bs.msg({msg:"文件必须为jpg/jpeg/png格式",type:'danger'});
			$.staticPic('background_pic_url','logoDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			qiao.bs.msg({msg:"文件不能大于2M",type:'danger'})
			$.staticPic('background_pic_url','logoDiv');
			return false;
		}
	}).on('fileuploaddone', function (e, data) {
		if(isNull(data.result.bean)){
			$.staticPic('background_pic_url','logoDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			background_pic_url = data.result.bean.url;
			bbackground_pic_url = data.result.bean.optionPath;
		}
	}).on('fileuploadfail', function (e, data) {
		$.staticPic('background_pic_url','logoDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
	
	//上传会员卡logo
	$("#imgFilesFivelogo").uploadPreview({ Img: "logo_url"});
	$("#imgFilesFivelogo").fileupload({
		url : path + "/post/UploadController/insertImgFileWechatTwo?imgType=6",
		disableImageResize: /Android(?!.*Chrome)|Opera/.test(window.navigator.userAgent),
        autoUpload: true,//是否自动上传
        maxFileSize: 999000,
        imageMaxWidth: 1000,
        imageMaxHeight: 800,
        imageQuality:0.6,
        dataType:'json',
        acceptFileTypes: /(\.|\/)(jpe?g|png|svg)$/i
	}).on('fileuploadadd', function (e, data) {
		var uploadFile = data.files[0];
		fileName = uploadFile.name;
		var reg = ".(jpg|png|jpeg|svg)$";
		var re = new RegExp(reg);
		if (fileName.length > 50) {
			qiao.bs.msg({msg:"文件名长度不能大于50",type:'danger'});
			$.staticPic('logo_url','logoDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			qiao.bs.msg({msg:"文件必须为jpg/jpeg/png格式",type:'danger'});
			$.staticPic('logo_url','logoDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			qiao.bs.msg({msg:"文件不能大于2M",type:'danger'})
			$.staticPic('logo_url','logoDiv');
			return false;
		}
	}).on('fileuploaddone', function (e, data) {
		if(isNull(data.result.bean)){
			$.staticPic('logo_url','logoDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			logo_url = data.result.bean.url;
			blogo_url = data.result.bean.optionPath;
		}
	}).on('fileuploadfail', function (e, data) {
		$.staticPic('logo_url','logoDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
}


