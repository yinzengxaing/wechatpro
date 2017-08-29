var controlImgId1 = null;
var controlImgId2 = null;
var controlImgId3 = null;
var adminIdentity = null;
var ShopName = "";
var adminWorkXCoordinate = "";
var adminWorkYCoordinate = "";
var adminKfPhone = "";
var adminKfBusinessHours = "";
var adminRecommend = "";
var adminCharacteristic = "";
var adminShopCard = "";
var adminShopKey = "";
var map = null;
$(function(e){
	dataInit();
});

function dataInit(){
	//数据回显
	AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/selectRz",params:{},type:'json',callback:function(json){
		adminIdentity=json.bean.adminIdentity;
		if(json.returnCode==0){
			if(adminIdentity!=0){
				$("#realName").val(json.bean.realName);
				if(json.bean.adminSex==1){
					$("#optionsRadios1").attr("checked",true);
				}else if(json.bean.adminSex==2){
					$("#optionsRadios2").attr("checked",true);
				}
				$("#shzt").html(json.bean.adminIdentityShow);
				$("#adminAge").val(json.bean.adminAge);
				$("#adminEducation").val(json.bean.adminEducation);
				$("#adminNation").val(json.bean.adminNation);
				$("#adminHomePlace").val(json.bean.adminHomePlace);
				$("#adminPoliticalLandscape").val(json.bean.adminPoliticalLandscape);
				$("#adminQq").val(json.bean.adminQq);
				$("#shyjms").html(json.bean.adminReason);
				if(json.bean.adminIdentity==2 || json.bean.adminIdentity==3 || json.bean.adminIdentity==6){//管理人员
					$("#adminWorkPlace").val(json.bean.adminWorkPlace);
					$("#contolLogo1").attr("src",path + "/" + json.bean.optionPath);
					$("#contolLogo2").attr("src",path + "/" + json.bean.optionPath2);
					$("#contolLogo3").attr("src",path + "/" + json.bean.optionPath3);
					controlImgId1 = json.bean.adminIDCardPositive;
					controlImgId2 = json.bean.adminIDCardOtherPositive;
					controlImgId3 = json.bean.adminIDCardPositive;
				}else if(json.bean.adminIdentity==1 || json.bean.adminIdentity==5){
					$("#shopPlace").val(json.bean.adminWorkPlace);
					$("#adminShopName").val(json.bean.adminShopName);//商店名称
					$("#adminShopCard").val(json.bean.adminShopCard);//商店商户号
					$("#adminShopKey").val(json.bean.adminShopKey);//商户号对应的key
					$("#adminKfBusinessHours").val(json.bean.adminKfBusinessHours);//营业时间
					$("#adminKfPhone").val(json.bean.adminKfPhone);
					$("#adminRecommend").val(json.bean.adminRecommend);
					$("#adminCharacteristic").val(json.bean.adminCharacteristic);
					$("#adminWorkXCoordinate").val(json.bean.adminWorkXCoordinate);
					$("#adminWorkYCoordinate").val(json.bean.adminWorkYCoordinate);
					$("#contolLogo4").attr("src",path + "/" + json.bean.optionPath);
					$("#contolLogo5").attr("src",path + "/" + json.bean.optionPath2);
					$("#contolLogo6").attr("src",path + "/" + json.bean.optionPath3);
					controlImgId1 = json.bean.adminIDCardPositive;
					controlImgId2 = json.bean.adminIDCardOtherPositive;
					controlImgId3 = json.bean.adminIDCardPositive;
				}
			}else{
				$("#shopName").hide();
				$("#businesstime").hide();
				$("#kfPhone").hide();
				$("#recommd").hide();
				$("#character").hide();
				$("#xcoordinate").hide();
				$("#ycoordinate").hide();
				$("#ditu").hide();
				$("#shopAdress").hide();
				$("#shopCard").hide();
				$("#shopKey").hide();
			}
			map = new AMap.Map('container', {
				 resizeEnable: true,
		         zoom: 11,
		         center: [116.397428, 39.90923]
		    });
			//加载插件
			AMap.service('AMap.Geocoder',function(){//回调函数
			    //实例化Geocoder
			    geocoder = new AMap.Geocoder({});
			});
			//在地图上进行标记
			var marker = new AMap.Marker({
	            map:map,
	            bubble:true
	        });
			//通过地址获取经纬度
			var input = document.getElementById('shopPlace');
	        map.on('click',function(e){
	            marker.setPosition(e.lnglat);
	            geocoder.getAddress(e.lnglat,function(status,result){
					if(status=='complete'){
						input.value = result.regeocode.formattedAddress;
						var address = input.value;
						geocoder.getLocation(address,function(status,result){
							if(status=='complete'&&result.geocodes.length){
								marker.setPosition(result.geocodes[0].location);
								map.setCenter(marker.getPosition());
								$("#adminWorkXCoordinate").val(result.geocodes[0].location.lng);
								$("#adminWorkYCoordinate").val(result.geocodes[0].location.lat);
							}else{
							}
			            })
					}else{
					}
	            });
	        });
	        input.onchange = function(e){
	            var address = input.value;
	            geocoder.getLocation(address,function(status,result){
					if(status=='complete'&&result.geocodes.length){
						marker.setPosition(result.geocodes[0].location);
						map.setCenter(marker.getPosition());
						$("#adminWorkXCoordinate").val(result.geocodes[0].location.lng);
						$("#adminWorkYCoordinate").val(result.geocodes[0].location.lat);
					}else{
					}
	            })
	        }
			eventInit();
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
    }});
}

function uploadImg(){
	$("#imgFiles").uploadPreview({ Img: "contolLogo1"});
	$("#imgFiles").fileupload({
		url : path + "/post/UploadController/insertImgFile?imgType=4",
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
			$.staticPic('contolLogo1','logoDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			qiao.bs.msg({msg:"文件必须为jpg/jpeg/png格式",type:'danger'});
			$.staticPic('contolLogo1','logoDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			qiao.bs.msg({msg:"文件不能大于2M",type:'danger'});
			$.staticPic('contolLogo1','logoDiv');
			return false;
		}
	}).on('fileuploaddone', function (e, data) {
		if(isNull(data.result.bean)){
			$.staticPic('contolLogo1','logoDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			controlImgId1 = data.result.bean.id;
		}
	}).on('fileuploadfail', function (e, data) {
		$.staticPic('contolLogo1','logoDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
	
	$("#imgFilesOne").uploadPreview({ Img: "contolLogo2"});
	$("#imgFilesOne").fileupload({
		url : path + "/post/UploadController/insertImgFileOne?imgType=4",
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
			$.staticPic('contolLogo2','logoDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			qiao.bs.msg({msg:"文件必须为jpg/jpeg/png格式",type:'danger'});
			$.staticPic('contolLogo2','logoDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			qiao.bs.msg({msg:"文件不能大于2M",type:'danger'});
			$.staticPic('contolLogo2','logoDiv');
			return false;
		}
	}).on('fileuploaddone', function (e, data) {
		if(isNull(data.result.bean)){
			$.staticPic('contolLogo2','logoDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			controlImgId2 = data.result.bean.id;
		}
	}).on('fileuploadfail', function (e, data) {
		$.staticPic('contolLogo2','logoDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
	
	$("#imgFilesTwo").uploadPreview({ Img: "contolLogo3"});
	$("#imgFilesTwo").fileupload({
		url : path + "/post/UploadController/insertImgFileTwo?imgType=4",
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
			$.staticPic('contolLogo3','logoDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			qiao.bs.msg({msg:"文件必须为jpg/jpeg/png格式",type:'danger'});
			$.staticPic('contolLogo3','logoDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			qiao.bs.msg({msg:"文件不能大于2M",type:'danger'});
			$.staticPic('contolLogo3','logoDiv');
			return false;
		}
	}).on('fileuploaddone', function (e, data) {
		if(isNull(data.result.bean)){
			$.staticPic('contolLogo3','logoDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			controlImgId3 = data.result.bean.id;
		}
	}).on('fileuploadfail', function (e, data) {
		$.staticPic('contolLogo3','logoDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
	
	$("#imgFilesThree").uploadPreview({ Img: "contolLogo4"});
	$("#imgFilesThree").fileupload({
		url : path + "/post/UploadController/insertImgFileThree?imgType=5",
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
			$.staticPic('contolLogo4','logoDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			qiao.bs.msg({msg:"文件必须为jpg/jpeg/png格式",type:'danger'});
			$.staticPic('contolLogo4','logoDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			qiao.bs.msg({msg:"文件不能大于2M",type:'danger'});
			$.staticPic('contolLogo4','logoDiv');
			return false;
		}
	}).on('fileuploaddone', function (e, data) {
		if(isNull(data.result.bean)){
			$.staticPic('contolLogo1','logoDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			controlImgId1 = data.result.bean.id;
		}
	}).on('fileuploadfail', function (e, data) {
		$.staticPic('contolLogo4','logoDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
	
	$("#imgFilesFour").uploadPreview({ Img: "contolLogo5"});
	$("#imgFilesFour").fileupload({
		url : path + "/post/UploadController/insertImgFileFour?imgType=5",
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
			$.staticPic('contolLogo5','logoDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			qiao.bs.msg({msg:"文件必须为jpg/jpeg/png格式",type:'danger'});
			$.staticPic('contolLogo5','logoDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			qiao.bs.msg({msg:"文件不能大于2M",type:'danger'});
			$.staticPic('contolLogo5','logoDiv');
			return false;
		}
	}).on('fileuploaddone', function (e, data) {
		if(isNull(data.result.bean)){
			$.staticPic('contolLogo5','logoDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			controlImgId2 = data.result.bean.id;
		}
	}).on('fileuploadfail', function (e, data) {
		$.staticPic('contolLogo5','logoDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
	
	$("#imgFilesFive").uploadPreview({ Img: "contolLogo6"});
	$("#imgFilesFive").fileupload({
		url : path + "/post/UploadController/insertImgFileFive?imgType=5",
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
			$.staticPic('contolLogo6','logoDiv');
			return false;
		} else if (!re.test(fileName.toLowerCase())) {
			qiao.bs.msg({msg:"文件必须为jpg/jpeg/png格式",type:'danger'});
			$.staticPic('contolLogo6','logoDiv');
			return false;
		} else if (uploadFile.size > 2000000) { // 2mb
			qiao.bs.msg({msg:"文件不能大于2M",type:'danger'});
			$.staticPic('contolLogo6','logoDiv');
			return false;
		}
	}).on('fileuploaddone', function (e, data) {
		if(isNull(data.result.bean)){
			$.staticPic('contolLogo6','logoDiv');
			qiao.bs.msg({msg:data.result.returnMessage,type:'danger'});
		}else{
			qiao.bs.msg({msg:"上传成功",type:'success'});
			controlImgId3 = data.result.bean.id;
		}
	}).on('fileuploadfail', function (e, data) {
		$.staticPic('contolLogo6','logoDiv');
		qiao.bs.msg({msg:"上传失败，图片格式不正确",type:'danger'});
	});
}
                    

function eventInit(){
	if(adminIdentity==1 || adminIdentity==2 || adminIdentity==3 || adminIdentity==5 || adminIdentity==6){
		$("input").attr("disabled",true);
		$("button").hide();
		$("input[type='file']").remove();
		$("#shjg").show();
		$("#shyj").show();
		if(adminIdentity==5 || adminIdentity==1){
			$("#home").removeClass("active");
			$("#ios").addClass("active");
			$("#home").removeClass("in");
			$("#ios").addClass("in");
			$("#gly").css("display","none");
			$("#ctry").addClass("active");
			$("#workadress").hide();
		}else if(adminIdentity==6 || adminIdentity==2 || adminIdentity==3){
			$("#shopName").hide();
			$("#shopCard").hide();
			$("#shopKey").hide();
			$("#businesstime").hide();
			$("#kfPhone").hide();
			$("#recommd").hide();
			$("#character").hide();
			$("#xcoordinate").hide();
			$("#ycoordinate").hide();
			$("#ditu").hide();
			$("#shopAdress").hide();
			$("#ios").removeClass("active");
			$("#home").addClass("active");
			$("#ios").removeClass("in");
			$("#home").addClass("in");
			$("#ctry").css("display","none");
			$("#gly").addClass("active");
		}
	}else{
		$("#shjg").show();
		$("#shyj").show();
		uploadImg();
		
		$('body').on('click', '#submitPassPeople', function(e){
			if(isNull(controlImgId1)){
				qiao.bs.msg({msg:"请上传身份证正面照!",type:'danger'});
			}else if(isNull(controlImgId2)){
				qiao.bs.msg({msg:"请上传身份证反面照!",type:'danger'});
			}else if(isNull(controlImgId3)){
				qiao.bs.msg({msg:"请上传认证合一证件!",type:'danger'});
			}else if(isNull($("#adminWorkPlace").val())){
				qiao.bs.msg({msg:"工作地址不能为空",type:'danger'});
			}else{
				var params = {
						realName:$("#realName").val(),
						adminSex:$("input[name='optionsRadios']:checked").val(),
						adminAge:$("#adminAge").val(),
						adminEducation:$("#adminEducation").val(),
						adminNation:$("#adminNation").val(),
						adminWorkPlace:$("#adminWorkPlace").val(),
						adminHomePlace:$("#adminHomePlace").val(),
						adminPoliticalLandscape:$("#adminPoliticalLandscape").val(),
						adminQq:$("#adminQq").val(),	
						adminShopName:ShopName,
						adminIDCardPositive:controlImgId1,
						adminIDCardOtherPositive:controlImgId2,
						adminIDCardPeoAndPosi:controlImgId3,
						adminIdentity:'6',
						adminWorkXCoordinate:adminWorkXCoordinate,
						adminWorkYCoordinate:adminWorkYCoordinate,
						adminKfPhone:adminKfPhone,
						adminShopCard:adminShopCard,
						adminShopKey:adminShopKey,
						adminKfBusinessHours:adminKfBusinessHours,
						adminRecommend:adminRecommend,
						adminCharacteristic:adminCharacteristic,
				};
				//认证
				AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/updateById",params:params,type:'json',callback:function(json){
					if(json.returnCode==0){
						qiao.bs.msg({msg:"提交成功!",type:'success'});
						$("input").attr("disabled",true);
						$("button").hide();
						$("input[type='file']").remove();
						$("#shjg").show();
						$("#shyj").show();
					}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
			    }});
			}
			
		});
		$('body').on('click', '#submitPassCons', function(e){
			if(isNull(controlImgId1)){
				qiao.bs.msg({msg:"请上传营业执照!",type:'danger'});
			}else if(isNull(controlImgId2)){
				qiao.bs.msg({msg:"请上传人与营业执照合一证件!",type:'danger'});
			}else if(isNull(controlImgId3)){
				qiao.bs.msg({msg:"请上传人与身份证合一证件!",type:'danger'});
			}else if(isNull($("#shopPlace").val())){
				qiao.bs.msg({msg:"门店地址不能为空!",type:'danger'});
			}else if(isNull($("#adminKfPhone").val())){
				qiao.bs.msg({msg:"客服电话不能为空!",type:'danger'});
			}else if(isNull($("#adminShopCard").val())){
				qiao.bs.msg({msg:"商户号不能为空!",type:'danger'});
			}else if(isNull($("#adminShopKey").val())){
				qiao.bs.msg({msg:"商户号对应的key不能为空!",type:'danger'});
			}else if(isNull($("#adminShopName").val())){
				qiao.bs.msg({msg:"商店名称不能为空!",type:'danger'});
			}else if(isNull($("#adminKfBusinessHours").val())){
				qiao.bs.msg({msg:"商店营业时间不能为空!",type:'danger'});
			}else if(checkPhone($("#adminKfPhone").val()) || checkMobile($("#adminKfPhone").val()) || checknum($("#adminKfPhone").val()) ){
				var params = {
						realName:$("#realName").val(),
						adminSex:$("input[name='optionsRadios']:checked").val(),
						adminAge:$("#adminAge").val(),
						adminEducation:$("#adminEducation").val(),
						adminNation:$("#adminNation").val(),
						adminWorkPlace:$("#shopPlace").val(),
						adminHomePlace:$("#adminHomePlace").val(),
						adminPoliticalLandscape:$("#adminPoliticalLandscape").val(),
						adminQq:$("#adminQq").val(),	
						adminShopName:$("#adminShopName").val(),
						adminIDCardPositive:controlImgId1,
						adminIDCardOtherPositive:controlImgId2,
						adminIDCardPeoAndPosi:controlImgId3,
						adminIdentity:'5',
						adminKfPhone:$("#adminKfPhone").val(),
						adminShopCard:$("#adminShopCard").val(),
						adminShopKey:$("#adminShopKey").val(),
						adminKfBusinessHours:$("#adminKfBusinessHours").val(),
						adminRecommend:$("#adminRecommend").val(),
						adminCharacteristic:$("#adminCharacteristic").val(),
						adminWorkXCoordinate:$("#adminWorkXCoordinate").val(),
						adminWorkYCoordinate:$("#adminWorkYCoordinate").val(),
				};
				//认证
				AjaxPostUtil.request({url:path+"/post/wechatAdminLoginController/updateById",params:params,type:'json',callback:function(json){
					if(json.returnCode==0){
						qiao.bs.msg({msg:"认证成功!",type:'danger'});
						$("input").attr("disabled",true);
						$("button").hide();
						$("input[type='file']").remove();
						$("#shjg").show();
						$("#shyj").show();
					}else{
						qiao.bs.msg({msg:json.returnMessage,type:'danger'});
					}
			    }});
			}else{
				qiao.bs.msg({msg:"门店客服电话或联系方式不合格!",type:'danger'});
			}
		});
		//点击管理a标签
		$('body').on('click', '#guanli', function(e){
			$("#shopName").hide();
			$("#shopCard").hide();
			$("#shopKey").hide();
			$("#businesstime").hide();
			$("#kfPhone").hide();
			$("#recommd").hide();
			$("#character").hide();
			$("#xcoordinate").hide();
			$("#ycoordinate").hide();
			$("#ditu").hide();
			$("#shopAdress").hide();
			$("#workadress").show();
		});
		
		$('body').on('click', '#canting', function(e){
			$("#shopName").show();
			$("#shopCard").show();
			$("#shopKey").show();
			$("#businesstime").show();
			$("#kfPhone").show();
			$("#recommd").show();
			$("#character").show();
			$("#xcoordinate").show();
			$("#ycoordinate").show();
			$("#ditu").show();
			$("#shopAdress").show();
			$("#workadress").hide();
		});
		
	}
}