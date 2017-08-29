var id = null;

$(function(e){
	receiveData();
	dataInit();
});

function dataInit(){
	if(id==null){
		id = $.req("id");
	}
	var params = {
		id:id,
	};
	AjaxPostUtil.request({url:path+"/gateway/WechatScollorPicController/selectOneScollor",params:params,type:'json',callback:function(json){
		if(json.returnCode==0){
			$("#scollorName").html(json.bean.scollor_pic_name);
			$("#scollorTime").html(json.bean.scollor_pic_data);
			$("#scollorContent").html(json.bean.scollor_pic_content);
			eventInit();
		}else{
			qiao.bs.msg({msg:json.returnMessage,type:'danger'});
		}
    }});
}

function eventInit(){
	
}

