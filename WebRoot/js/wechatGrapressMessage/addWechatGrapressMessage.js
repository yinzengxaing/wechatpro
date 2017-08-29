
var arr = [ 'div1', 'div2', 'div3', 'div4', 'div5', 'div6', 'div7' ];
var arr2 = new Array();
var showDiv = "div0";
var editor = null;

$(function(e){
	dataInit();
});

function dataInit(){
	var option = {
	    initialContent : '在编辑器中默认显示的内容',//初始化编辑器的内容
	    initialFrameHeight : 340
	};
	editor = new UE.ui.Editor(option);
	editor.render("myEditor");
	eventInit();
}

function eventInit(){
	$('body').on('click','#add',function(e){
		var msgDiv;
        if (arr.length == 7) {
            $("#" + showDiv).hide();
            msgDiv = arr.pop();
            arr2.push(msgDiv);
            showDiv = msgDiv;
        } else if (arr.length == 0) {
            alert('最多添加8个图文信息');
            return;
        } else {
            msgDiv = arr.pop();
            $("#" + showDiv).hide();
            arr2.push(msgDiv);
            showDiv = msgDiv;
        }
        $(".previewBox").append(
                "<div class='cover' id='s" + msgDiv + "' class='childCover' onmouseout='removeCover(this);'"
                + " onmouseover='showCover(this);'><div> <div class='childCoverTitle' id='title2"+msgDiv+"'>标题</div><div style='float:right;'>"
                + "<img src='../../assest/icon/1.png' class='childCoverImg'/></div></div><ul class='abs tc sub-msg-opr firstYcm'><li><div class='childMenuDiv'><a href='javascript:;' class='fitstEdit' onclick='editDiv(\""
                + msgDiv + "\");return false;' class='fitstEdit'>编辑</a>" + "<a href='javascript:;'"
                + " onclick='removeDiv(\"" + msgDiv + "\");return false;'>删除 </a></div></li></ul></div>");
        $("#main").append(
                " <div class='msg-editer' id='"+msgDiv+"'> "
                + "<form method='POST' enctype='multipart/form-data' action=''><label class='block inputlabel' for=''>标题</label>"
                + "<input type='text' name='Title' value='标题' id='title" + msgDiv + "' onchange='setTitle(\"" + msgDiv + "\")' class='msg-input'>"
                + "<label class='block inputlabel' for=''>作者<em class='mp_desc'>（选填）</em></label> <input type='text' name='Author' value='' id='author' class='msg-input' />"
                + "<label class='block inputlabel' for=''><span class='upload-tip r'id='upload-tip'>大图片建议尺寸：720像素 * 400像素</span>封面</label>"
                + "<div class='cover-area' style='vertical-align: bottom;margin-bottom: 10px;'><input type='file'name='file"+msgDiv+"'/>"
                + "<input type='button' value='上传' onclick='ajaxFileUpload(\"" + msgDiv + "\")'/>"
                + "<img src='' id='img"+msgDiv+"' style='width: 100px;vertical-align: bottom;border: 1px solid gray'/>" + "<a id='rm"
                + msgDiv + "' href='#' onclick='removeImage(\"" + msgDiv + "\")' style='vertical-align: bottom;'>删除</a></div>" +
                "<label class='block inputlabel' for=''>正文</label><textarea name='Content' id='rich"+msgDiv+"'></textarea>"
                + "<div class='none' id='url-block' style='margin-top: 14px;'>"
                + "<label class='block inputlabel' for=''>原文链接<em class='mp_desc'>（选填）</em></label><input type='text' name='Content_Link' value='' id='surl' class='msg-input' />"
                + "<br/></div></form></div>");
        editor.render("rich" + msgDiv);
	});
}

function removeImage(id) {
    $("#img" + id).hide();
    $("#rm" + id).hide();
}

function showCover(obj) {
    $(obj).addClass("sub-msg-opr-show");
}
function removeCover(obj) {
    $(obj).removeClass("sub-msg-opr-show");
}

function editDiv(obj) {
    if (showDiv != obj) {
        $("#" + showDiv).hide();
        $("#" + obj).show();
        showDiv = obj;
    }
}

function removeDiv(obj) {
    $("#s" + obj).remove();
    $("#" + obj).remove();
    $("#rich" + obj).remove();
    arr.push(obj);
    arr2.splice($.inArray(obj, arr2), 1);
    if (arr2.length == 0) {
        showDiv = "div0";
        $("#" + showDiv).show();
    } else {
        if (obj == showDiv) {
            showDiv = arr2.pop();
            arr2.push(showDiv);
            $("#" + showDiv).show();
        } else {
            $("#" + showDiv).show();
            showDiv = arr2.pop();
            arr2.push(showDiv);
        }
    }
}

function setTitle(obj) {
    $("#title2" + obj).text($("#title" + obj).val());
}

function ajaxFileUpload(id) {
    var filename = $("#file" + id).val();
    var suffix;
    if (filename != "") {
        suffix = filename.substr(filename.indexOf(".") + 1, filename.length);
    }
    if (filename == "") {
        alert("请选择要上传的图片");
    } else if (suffix != "jpg" && suffix != "png") {
        alert("文件格式有无");
    } else {
        $.ajaxFileUpload({
            url : 'fileUpload', //用于文件上传的服务器端请求地址
            type: 'post',
            fileElementId : 'file' + id, //文件上传域的ID
            dataType : 'json', //返回值类型 一般设置为json
            success : function(data, status){
                alert("成功");
            },
            error : function(data, status, e){
                alert(e);
            }
        })
    }
}
function publishTemplate() {
    if ("@ViewBag.Template.Row_ID") {
        var result = window.confirm("确定发布这条图文？");
        if (result) {
            window.location = "@PublishUrl";
        }
    }
}
function removeTemplate() {
    if ("@ViewBag.Template.Row_ID") {
        var result = window.confirm("确定删除这条图文？");
        if (result) {
            window.location = "@RemoveUrl";
        }
    }
}