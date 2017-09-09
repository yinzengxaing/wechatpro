var path = getRootPath();


var mainContentShowId = null;
var mainContentText = null;

//base64加密开始  
var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"  
        + "wxyz0123456789+/" + "=";  

//计算距离，参数分别为第一点的纬度，经度；第二点的纬度，经度
function GetDistance(lat1,lng1,lat2,lng2){
    var radLat1 = Rad(lat1);
    var radLat2 = Rad(lat2);
    var a = radLat1 - radLat2;
    var  b = Rad(lng1) - Rad(lng2);
    var s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
    Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
    s = s *6378.137 ;// EARTH_RADIUS;
    s = Math.round(s * 10000) / 10000; //输出为公里
    //s=s.toFixed(4);
    return s;
}

function Rad(d){
	return d * Math.PI / 180.0;
}

function isUndefineReturnNull(str){
	if(str=="undefine"||str==""||str==null){
		return "";
	}else{
		return str;
	}
}

function isContains(str, substr) {
    return new RegExp(substr).test(str);
}

function setIframeHeight(iframe) {
	if (iframe) {
		var iframeWin = iframe.contentWindow || iframe.contentDocument.parentWindow;
		if (iframeWin.document.body) {
			if((iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight)<900){
				iframe.height = 900;
			}else{
				iframe.height = iframeWin.document.documentElement.scrollHeight || iframeWin.document.body.scrollHeight;
			}
		}
	}
}

window.onload = function () {
	setIframeHeight(document.getElementById('external-frame'));
};

function encode64(input) {  
    var output = "";  
    var chr1, chr2, chr3 = "";  
    var enc1, enc2, enc3, enc4 = "";  
    var i = 0;  
    do {  
        chr1 = input.charCodeAt(i++);  
        chr2 = input.charCodeAt(i++);  
        chr3 = input.charCodeAt(i++);  
        enc1 = chr1 >> 2;  
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);  
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);  
        enc4 = chr3 & 63;  
        if (isNaN(chr2)) {  
            enc3 = enc4 = 64;  
        } else if (isNaN(chr3)) {  
            enc4 = 64;  
        }  
        output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)  
                + keyStr.charAt(enc3) + keyStr.charAt(enc4);  
        chr1 = chr2 = chr3 = "";  
        enc1 = enc2 = enc3 = enc4 = "";  
    } while (i < input.length);  

    return output;  
}  
// base64加密结束  

function Base64() {  
	   
    // private property  
    _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";  
   
    // public method for encoding  
    this.encode = function (input) {  
        var output = "";  
        var chr1, chr2, chr3, enc1, enc2, enc3, enc4;  
        var i = 0;  
        input = _utf8_encode(input);  
        while (i < input.length) {  
            chr1 = input.charCodeAt(i++);  
            chr2 = input.charCodeAt(i++);  
            chr3 = input.charCodeAt(i++);  
            enc1 = chr1 >> 2;  
            enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);  
            enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);  
            enc4 = chr3 & 63;  
            if (isNaN(chr2)) {  
                enc3 = enc4 = 64;  
            } else if (isNaN(chr3)) {  
                enc4 = 64;  
            }  
            output = output +  
            _keyStr.charAt(enc1) + _keyStr.charAt(enc2) +  
            _keyStr.charAt(enc3) + _keyStr.charAt(enc4);  
        }  
        return output;  
    }  
   
    // public method for decoding  
    this.decode = function (input) {  
        var output = "";  
        var chr1, chr2, chr3;  
        var enc1, enc2, enc3, enc4;  
        var i = 0;  
        input = input.replace(/[^A-Za-z0-9\+\/\=]/g, "");  
        while (i < input.length) {  
            enc1 = _keyStr.indexOf(input.charAt(i++));  
            enc2 = _keyStr.indexOf(input.charAt(i++));  
            enc3 = _keyStr.indexOf(input.charAt(i++));  
            enc4 = _keyStr.indexOf(input.charAt(i++));  
            chr1 = (enc1 << 2) | (enc2 >> 4);  
            chr2 = ((enc2 & 15) << 4) | (enc3 >> 2);  
            chr3 = ((enc3 & 3) << 6) | enc4;  
            output = output + String.fromCharCode(chr1);  
            if (enc3 != 64) {  
                output = output + String.fromCharCode(chr2);  
            }  
            if (enc4 != 64) {  
                output = output + String.fromCharCode(chr3);  
            }  
        }  
        output = _utf8_decode(output);  
        return output;  
    }  
   
    // private method for UTF-8 encoding  
    _utf8_encode = function (string) {  
        string = string.replace(/\r\n/g,"\n");  
        var utftext = "";  
        for (var n = 0; n < string.length; n++) {  
            var c = string.charCodeAt(n);  
            if (c < 128) {  
                utftext += String.fromCharCode(c);  
            } else if((c > 127) && (c < 2048)) {  
                utftext += String.fromCharCode((c >> 6) | 192);  
                utftext += String.fromCharCode((c & 63) | 128);  
            } else {  
                utftext += String.fromCharCode((c >> 12) | 224);  
                utftext += String.fromCharCode(((c >> 6) & 63) | 128);  
                utftext += String.fromCharCode((c & 63) | 128);  
            }  
   
        }  
        return utftext;  
    }  
   
    // private method for UTF-8 decoding  
    _utf8_decode = function (utftext) {  
        var string = "";  
        var i = 0;  
        var c = c1 = c2 = 0;  
        while ( i < utftext.length ) {  
            c = utftext.charCodeAt(i);  
            if (c < 128) {  
                string += String.fromCharCode(c);  
                i++;  
            } else if((c > 191) && (c < 224)) {  
                c2 = utftext.charCodeAt(i+1);  
                string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));  
                i += 2;  
            } else {  
                c2 = utftext.charCodeAt(i+1);  
                c3 = utftext.charCodeAt(i+2);  
                string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));  
                i += 3;  
            }  
        }  
        return string;  
    }  
}

function getRootPath(){  
    var curWwwPath=window.document.location.href;  
    var pathName=window.document.location.pathname;  
    var pos=curWwwPath.indexOf(pathName);  
    var localhostPaht=curWwwPath.substring(0,pos);  
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
    return(localhostPaht+projectName);//http://127.0.0.1:8080/tsy
}  

function LoadNewTpl(id,tpl){
	if($("#"+id).html==""||$("#"+id).html==null){
	}else{
		$("#"+id).html("");
	}
//	$.ajaxSetup({
//		cache:false;
//	});
	$("#"+id).load(tpl,function(responseTxt,statusTxt,xhr){
	});
}

/**
 * 
 * @param total 数据总条数
 * @param pageCount 展示多少个分页
 * @param limit 每页多少条数据
 * @param pageShowId 分页展示位置的ID
 * @param contentShowId 正文文本展示的位置的ID
 * @param contentText 正文文本
 */
function callBackPagination(total,pageCount,limit,pageShowId,contentShowId,contentText) {
    var totalCount = total, showCount = pageCount, limit = limit;
    mainContentShowId = contentShowId;
    mainContentText = contentText;
    createTable(1, limit, totalCount);
    $('#'+pageShowId).extendPagination({
        totalCount: totalCount,
        showCount: showCount,
        limit: limit,
        callback: function (curr, limit, totalCount) {
            createTable(curr, limit, totalCount);
        }
    });
}

function createTable(currPage, limit, total) {
    var html = [];
    html.push(mainContentText);
    var mainObj = $('#'+mainContentShowId);
    mainObj.empty();
    mainObj.html(html.join(''));
}

//根据内容获取数组下表
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val) return i;
	}
	return -1;
};
//数组转化成字符串,中间用val这个对象的内容隔开
Array.prototype.toString = function(val){
	var str = "";
	for (var i = 0; i < this.length; i++) {
		if (this[i] != null && this[i] != "") 
			str = str + this[i] + val;
	}
	return str;
}
//根据内容移除数组指定项
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};
//打开新的界面
var creatNewPage = function(href,params){
	if($.isEmptyObject(params)){//为空
		window.location.href = encodeURI(href);
	}else{//不为空
		var entrys = [];  
        for(var i in params) {  
            entrys.push(i + '=' + params[i]);  
        }
		window.location.href = encodeURI(href + '?' + entrys.join('&'));
	}
}
//接收参数
var receiveData = function(e){
	var url = decodeURI(window.location.href);  
	var index = url.indexOf('?');  
	if (index < 0) {  
	    return;  
	}  
	var parameters = {};  
	var entrys = url.substring(++index, url.length).split('&');  
	for (var i = 0, len = entrys.length; i < len; i++) {  
	    var entry = entrys[i].split('=');  
	    parameters[entry[0]] = entry[1];  
	}  
	$.data(document, 'parameters', parameters);
}
//获取值
$.req = function(key) {  
    var parameters = $.data(document, 'parameters');  
    return parameters === undefined ? undefined : parameters[key];  
} 

/**
 * 判断是否是汉字
 * @param str
 * @returns {Boolean}
 */
function isChn(str){
	var reg = new RegExp("[\\u4E00-\\u9FFF]+","g");
	if(reg.test(str)){     
		return true;
	}else{
		return false;
	}
}

//手机号码正则验证
function checkMobile(obj){
	var partten = /^1[34578]\d{9}$/;
	if(partten.test(obj)){
		return true;
	}else{
		return false;
	}
}

//400电话号码验证
function checknum(obj){
	var partten = /^(4|8)00-[0-9]{3}-[0-9]{4}$/;
	if(partten.test(obj)){
		return true;
	}else{
		return false;
	}
}

//电话号码正则验证
function checkPhone(str){
   var re = /^0\d{2,3}-?\d{7,8}$/;
   if(re.test(str)){
       return true;
   }else{
       return false;
   }
}

//判断对象是否为空
function isEmptyObject(e) {  
    var t;  
    for (t in e)  
        return !1;  
    return !0  
}  

//判断是否是数字
function isNumber(str){
	if(!isNaN(str)){
		return true;
	}else{
		return false;
	}
}

function checkURL(str){
	return !!str.match(/^(http|https):\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/);
}

//判断是否为链接
function isURL(str_url) {
	if(str_url==""||str_url==null){
		return true; 
	}else{
		var strRegex = '^((https|http|ftp|rtsp|mms)?://)'
			+ '?(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?' //ftp的user@ 
			+ '(([0-9]{1,3}.){3}[0-9]{1,3}' // IP形式的URL- 199.194.52.184 
			+ '|' // 允许IP和DOMAIN（域名） 
			+ '([0-9a-z_!~*\'()-]+.)*' // 域名- www. 
			+ '([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].' // 二级域名 
			+ '[a-z]{2,6})' // first level domain- .com or .museum 
			+ '(:[0-9]{1,4})?' // 端口- :80 
			+ '((/?)|' // a slash isn't required if there is no file name 
			+ '(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$'; 
		var re=new RegExp(strRegex); 
		//re.test() 
		if (re.test(str_url)) { 
			return true; 
		} else { 
			return false; 
		} 
	}
}

//判断内容是否为空
function isNull(str){
	if(str==null||str==""||str==''){
		return true;
	}else{
		return false;
	}
}

//ajax请求
var AjaxPostUtil = {
	// 基础选项  
	options: {
		method: "post", // 默认提交的方法,get post  
		url: "", // 请求的路径 required  
		params: {}, // 请求的参数  
		type: 'text', // 返回的内容的类型,text,xml,json  
		callback: function() {} // 回调函数 required  
	},

	// 创建XMLHttpRequest对象  
	createRequest: function() {
		var xmlhttp;
		try {
			xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); // IE6以上版本  
		} catch(e) {
			try {
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); // IE6以下版本  
			} catch(e) {
				try {
					xmlhttp = new XMLHttpRequest();
					if(xmlhttp.overrideMimeType) {
						xmlhttp.overrideMimeType("text/xml");
					}
				} catch(e) {
					alert("您的浏览器不支持Ajax");
				}
			}
		}
		return xmlhttp;
	},
	// 设置基础选项  
	setOptions: function(newOptions) {
		for(var pro in newOptions) {
			this.options[pro] = newOptions[pro];
		}
	},
	// 格式化请求参数  
	formateParameters: function() {
		var paramsArray = [];
		var params = this.options.params;
		for(var pro in params) {
			var paramValue = params[pro];
			/*if(this.options.method.toUpperCase() === "GET")  
			{  
			    paramValue = encodeURIComponent(params[pro]);  
			}*/
			paramsArray.push(pro + "=" + paramValue);
		}
		return paramsArray.join("&");
	},

	// 状态改变的处理  
	readystatechange: function(xmlhttp) {
		// 获取返回值  
		var returnValue;
		if(xmlhttp.readyState == 4 && (xmlhttp.status == 200 || xmlhttp.status == 0)) {
			switch(this.options.type) {
				case "xml":
					returnValue = xmlhttp.responseXML;
					break;
				case "json":
					var jsonText = xmlhttp.responseText;
					if(jsonText) {
						if(isContains(jsonText,"404错误页面")&&isContains(jsonText,"哎哟喂！页面让狗狗叼走了！")){
							var retMesc = jsonText.substring(jsonText.indexOf("错误提示:"),jsonText.indexOf("错误类型:"));
							if(retMesc=="错误提示:您还未登录,请先登录<br/>"){
								qiao.bs.msg({msg:jsonText.substring(jsonText.indexOf("错误提示:"),jsonText.indexOf("错误类型:")),type:'error'});
								location.href = 'login.html';
								return;
							}else{
								qiao.bs.msg({msg:jsonText.substring(jsonText.indexOf("错误提示:"),jsonText.indexOf("错误类型:")),type:'error'});
								return;
							}
						}else{
							returnValue = eval("(" + jsonText + ")");
						}
					}
					break;
				default:
					returnValue = xmlhttp.responseText;
					break;
			}
			if(returnValue) {
				this.options.callback.call(this, returnValue);
			} else {
				this.options.callback.call(this);
			}
		}
	},

	// 发送Ajax请求  
	request: function(options) {
		var ajaxObj = this;
		// 设置参数  
		ajaxObj.setOptions.call(ajaxObj, options);
		// 创建XMLHttpRequest对象  
		var xmlhttp = ajaxObj.createRequest.call(ajaxObj);
		// 设置回调函数  
		xmlhttp.onreadystatechange = function() {
			ajaxObj.readystatechange.call(ajaxObj, xmlhttp);
		};
		// 格式化参数  
		var formateParams = ajaxObj.formateParameters.call(ajaxObj);
		// 请求的方式  
		var method = ajaxObj.options.method;
		var url = ajaxObj.options.url;
		if("GET" === method.toUpperCase()) {
			url += "?" + formateParams;
		}
		// 建立连接  
		xmlhttp.open(method, url, true);
		if("GET" === method.toUpperCase()) {
			xmlhttp.send(null);
		} else if("POST" === method.toUpperCase()) {
			// 如果是POST提交，设置请求头信息  
			xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlhttp.send(formateParams);
		}
	}
};

var AjaxGetUtil = {
		// 基础选项  
		options: {
			method: "get", // 默认提交的方法,get post  
			url: "", // 请求的路径 required  
			params: {}, // 请求的参数  
			type: 'text', // 返回的内容的类型,text,xml,json  
			callback: function() {} // 回调函数 required  
		},

		// 创建XMLHttpRequest对象  
		createRequest: function() {
			var xmlhttp;
			try {
				xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); // IE6以上版本  
			} catch(e) {
				try {
					xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); // IE6以下版本  
				} catch(e) {
					try {
						xmlhttp = new XMLHttpRequest();
						if(xmlhttp.overrideMimeType) {
							xmlhttp.overrideMimeType("text/xml");
						}
					} catch(e) {
						alert("您的浏览器不支持Ajax");
					}
				}
			}
			return xmlhttp;
		},
		// 设置基础选项  
		setOptions: function(newOptions) {
			for(var pro in newOptions) {
				this.options[pro] = newOptions[pro];
			}
		},
		// 格式化请求参数  
		formateParameters: function() {
			var paramsArray = [];
			var params = this.options.params;
			for(var pro in params) {
				var paramValue = params[pro];
				/*if(this.options.method.toUpperCase() === "GET")  
				{  
				    paramValue = encodeURIComponent(params[pro]);  
				}*/
				paramsArray.push(pro + "=" + paramValue);
			}
			return paramsArray.join("&");
		},

		// 状态改变的处理  
		readystatechange: function(xmlhttp) {
			// 获取返回值  
			var returnValue;
			if(xmlhttp.readyState == 4 && (xmlhttp.status == 200 || xmlhttp.status == 0)) {
				switch(this.options.type) {
					case "xml":
						returnValue = xmlhttp.responseXML;
						break;
					case "json":
						var jsonText = xmlhttp.responseText;
						if(isContains(jsonText,"404错误页面")&&isContains(jsonText,"哎哟喂！页面让狗狗叼走了！")){
							var retMesc = jsonText.substring(jsonText.indexOf("错误提示:"),jsonText.indexOf("错误类型:"));
							if(retMesc=="错误提示:您还未登录,请先登录<br/>"){
								qiao.bs.msg({msg:jsonText.substring(jsonText.indexOf("错误提示:"),jsonText.indexOf("错误类型:")),type:'error'});
								location.href = 'login.html';
								return;
							}else{
								qiao.bs.msg({msg:jsonText.substring(jsonText.indexOf("错误提示:"),jsonText.indexOf("错误类型:")),type:'error'});
								return;
							}
						}else{
							returnValue = eval("(" + jsonText + ")");
						}
						break;
					default:
						returnValue = xmlhttp.responseText;
						break;
				}
				if(returnValue) {
					this.options.callback.call(this, returnValue);
				} else {
					this.options.callback.call(this);
				}
			}
		},

		// 发送Ajax请求  
		request: function(options) {
			var ajaxObj = this;
			// 设置参数  
			ajaxObj.setOptions.call(ajaxObj, options);
			// 创建XMLHttpRequest对象  
			var xmlhttp = ajaxObj.createRequest.call(ajaxObj);
			// 设置回调函数  
			xmlhttp.onreadystatechange = function() {
				ajaxObj.readystatechange.call(ajaxObj, xmlhttp);
			};
			// 格式化参数  
			var formateParams = ajaxObj.formateParameters.call(ajaxObj);
			// 请求的方式  
			var method = ajaxObj.options.method;
			var url = ajaxObj.options.url;
			if("GET" === method.toUpperCase()) {
				url += "?" + formateParams;
			}
			// 建立连接  
			xmlhttp.open(method, url, true);
			if("GET" === method.toUpperCase()) {
				xmlhttp.send(null);
			} else if("POST" === method.toUpperCase()) {
				// 如果是POST提交，设置请求头信息  
				xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
				xmlhttp.send(formateParams);
			}
		}
	};

//是0~10整数的正则验证
function isdiscount(obj){
	var partten = /^[1-9]\d{0,10}$/;
	if(partten.test(obj)){
		return true;
	}else{
		return false;
	}
}


/****************************************解决JS浮点数(小数)计算加减乘除的BUG    Start****************************************/

/**
** 加法函数，用来得到精确的加法结果
** 说明：javascript的加法结果会有误差，在两个浮点数相加的时候会比较明显。这个函数返回较为精确的加法结果。
** 调用：accAdd(arg1,arg2)
** 返回值：arg1加上arg2的精确结果
**/
function accAdd(arg1, arg2) {
    if (isNaN(arg1)) {
        arg1 = 0;
    }
    if (isNaN(arg2)) {
        arg2 = 0;
    }
    arg1 = Number(arg1);
    arg2 = Number(arg2);
    var r1, r2, m, c;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    c = Math.abs(r1 - r2);
    m = Math.pow(10, Math.max(r1, r2));
    if (c > 0) {
        var cm = Math.pow(10, c);
        if (r1 > r2) {
            arg1 = Number(arg1.toString().replace(".", ""));
            arg2 = Number(arg2.toString().replace(".", "")) * cm;
        } else {
            arg1 = Number(arg1.toString().replace(".", "")) * cm;
            arg2 = Number(arg2.toString().replace(".", ""));
        }
    } else {
        arg1 = Number(arg1.toString().replace(".", ""));
        arg2 = Number(arg2.toString().replace(".", ""));
    }
    return (arg1 + arg2) / m;
}

//给Number类型增加一个add方法，调用起来更加方便。
Number.prototype.add = function(arg) {
    return accAdd(this, arg);
};

/**
** 减法函数，用来得到精确的减法结果
** 说明：javascript的减法结果会有误差，在两个浮点数相减的时候会比较明显。这个函数返回较为精确的减法结果。
** 调用：accSub(arg1,arg2)
** 返回值：arg1加上arg2的精确结果
**/
function accSub(arg1, arg2) {
    if (isNaN(arg1)) {
        arg1 = 0;
    }
    if (isNaN(arg2)) {
        arg2 = 0;
    }
    arg1 = Number(arg1);
    arg2 = Number(arg2);

    var r1, r2, m, n;
    try {
        r1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
        r1 = 0;
    }
    try {
        r2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
        r2 = 0;
    }
    m = Math.pow(10, Math.max(r1, r2)); //last modify by deeka //动态控制精度长度
    n = (r1 >= r2) ? r1 : r2;
    return ((arg1 * m - arg2 * m) / m).toFixed(n);
}

// 给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.sub = function(arg) {
    return accSub(this, arg);
};

/**
** 乘法函数，用来得到精确的乘法结果
** 说明：javascript的乘法结果会有误差，在两个浮点数相乘的时候会比较明显。这个函数返回较为精确的乘法结果。
** 调用：accMul(arg1,arg2)
** 返回值：arg1乘以 arg2的精确结果
**/
function accMul(arg1, arg2) {
    if (isNaN(arg1)) {
        arg1 = 0;
    }
    if (isNaN(arg2)) {
        arg2 = 0;
    }
    arg1 = Number(arg1);
    arg2 = Number(arg2);
    
    var m = 0, s1 = arg1.toString(), s2 = arg2.toString();
    try {
        m += s1.split(".")[1].length;
    }
    catch (e) {
    }
    try {
        m += s2.split(".")[1].length;
    }
    catch (e) {
    }
    return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
}

// 给Number类型增加一个mul方法，调用起来更加方便。
Number.prototype.mul = function(arg) {
    return accMul(this, arg);
};

/** 
** 除法函数，用来得到精确的除法结果
** 说明：javascript的除法结果会有误差，在两个浮点数相除的时候会比较明显。这个函数返回较为精确的除法结果。
** 调用：accDiv(arg1,arg2)
** 返回值：arg1除以arg2的精确结果
**/
function accDiv(arg1, arg2) {
    if (isNaN(arg1)) {
        arg1 = 0;
    }
    if (isNaN(arg2)) {
        arg2 = 0;
    }
    arg1 = Number(arg1);
    arg2 = Number(arg2);
    
    var t1 = 0, t2 = 0, r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length;
    }
    catch (e) {
    }
    try {
        t2 = arg2.toString().split(".")[1].length;
    }
    catch (e) {
    }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""));
        r2 = Number(arg2.toString().replace(".", ""));
        return (r1 / r2) * pow(10, t2 - t1);
    }
}

//给Number类型增加一个div方法，调用起来更加方便。
Number.prototype.div = function(arg) {
    return accDiv(this, arg);
};
/****************************************解决JS浮点数(小数)计算加减乘除的BUG  End****************************************/
