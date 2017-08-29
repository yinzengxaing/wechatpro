var background_pic_url = null;
var logo_url = null;
var image_url = null;
var blogo_url = null;
var bimage_url = null;
var bbackground_pic_url = null;

$(function(e){
	dataInit();
});

function dataInit(){
	eventInit();
}
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
		var businessservice = ""  ;
		$(".serviceItem").each(function(){
			var myThis = $(this);
			var isSelect = myThis.attr("checked");
			if (isSelect == "checked"){
				var  id = myThis.attr("id");
				businessservice += id+","
			}
			
		});
		var use_all_locations  = $('input[name="optionsRadiosinline"]:checked').val();
		
		var color  = $('input[name="optionsRadiosinline1"]:checked').val();
		
		if(isNull($("#title").val())){
			qiao.bs.msg({msg:"卡券名称不能为空!",type:'danger'});
			return;
		}else if($("#title").val().length>9){
			qiao.bs.msg({msg:"卡券名称长度必须小于9个字！",type:'danger'});
			return;
		}else if(isNull($("#brand_name").val())){
			qiao.bs.msg({msg:"商户名称不能为空！",type:'danger'});
			return;
		}else if($("#brand_name").val().length>12){
			qiao.bs.msg({msg:"商户名称长度必须小于12个字！",type:'danger'});
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
		}else if(isNull($("#quantity").val())){
			qiao.bs.msg({msg:"卡卷数量不能为空！",type:'danger'});
			return;
		}else if((parseInt($("#quantity").val())>100000000)||(parseInt($("#quantity").val())<0)){
			qiao.bs.msg({msg:"卡卷数量最多不能超过100000000张且不能为0张！",type:'danger'});
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
		}else if(isNull($("#text").val())){
			qiao.bs.msg({msg:"简介说明不能为空！！",type:'danger'});
			return;
		}else if($("#text").val().length>256){
			qiao.bs.msg({msg:"简介说明不能超过256个字",type:'danger'});
			return;
		}else if(isNull($("#prerogative").val())){
			qiao.bs.msg({msg:"特权说明不能为空！！",type:'danger'});
			return;
		}else if($("#prerogative").val().length>1024){
			qiao.bs.msg({msg:"特权说明不能超过1024个字",type:'danger'});
			return;
		}else if(isNull(businessservice)){
			qiao.bs.msg({msg:"商家服务未选择!",type:'danger'});
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
		}else if(isNull(image_url)){
			qiao.bs.msg({msg:"未上传简介图片!",type:'danger'});
			return;
		}else{
			var params = {
					title:$("#title").val(),
					brand_name:$("#brand_name").val(),
					service_phone:$("#service_phone").val(),
					discount:$("#discount").val(),
					quantity:$("#quantity").val(),
					cost_money_unit:$("#cost_money_unit").val(),
					cost_bonus_unit:$("#cost_bonus_unit").val(),
					increase_bonus:$("#increase_bonus").val(),
					least_money_to_use_bonus:$("#least_money_to_use_bonus").val(),
					max_reduce_bonus:$("#max_reduce_bonus").val(),
					reduce_money:$("#reduce_money").val(),
					description:$("#description").val(),
					notice:$("#notice").val(),
					text:$("#text").val(),
					prerogative:$("#prerogative").val(),
					business_service:businessservice,
					use_all_locations:use_all_locations,
					color:color,
					background_pic_url:background_pic_url,
					logo_url:logo_url,
					image_url:image_url,
					blogo_url:blogo_url,
					bimage_url:bimage_url,
					bbackground_pic_url:bbackground_pic_url
			};
			AjaxPostUtil.request({url:path+"/post/WechatMembershipController/addWechatMembership",params:params,type:'json',callback:function(json){
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
	
	//上传会员卡简介图片
	$("#imgFilesFiveimage").uploadPreview({ Img: "image_url"});
	$("#imgFilesFiveimage").fileupload({
		url : path + "/post/UploadController/insertImgFileWechatThree?imgType=6",
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
			$.staticPic('image_url','logoDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			qiao.bs.msg({msg:"文件必须为jpg/jpeg/png格式",type:'danger'});
			$.staticPic('image_url','logoDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			qiao.bs.msg({msg:"文件不能大于2M",type:'danger'})
			$.staticPic('image_url','logoDiv');
			return false;
		}
	}).on('fileuploaddone', function (e, data) {
		if(isNull(data.result.bean)){
			$.staticPic('image_url','logoDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			image_url = data.result.bean.url;
			bimage_url = data.result.bean.optionPath;
		}
	}).on('fileuploadfail', function (e, data) {
		$.staticPic('image_url','logoDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
	
}


