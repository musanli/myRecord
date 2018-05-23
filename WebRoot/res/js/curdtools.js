/**
 * 增删改工具栏
 */
window.onerror = function() {
	return true;
};
var iframe;// iframe操作对象
var win;//窗口对象
var gridname="";//操作datagrid对象名称
var windowapi = frameElement.api, W = windowapi.opener;//内容页中调用窗口实例对象接口
function upload(curform) {
	upload();
}

/**
 * 更新事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 * @param id//主键字段
 */
function update(title,url, id,width,height) {
	gridname=id;
	var rowsData = $('#'+id).datagrid('getSelections');
	if (!rowsData || rowsData.length==0) {
		tip('请选择编辑项目');
		return;
	}
	
	if (rowsData.length>1) {
		tip('请选择一条记录再编辑');
		return;
	}
	createwindow(title,url,width,height);
}


/**
 * 如果页面是详细查看页面，无效化所有表单元素，只能进行查看
 * $(function(){
	if(location.href.indexOf("load=detail")!=-1){
		$(":input").attr("disabled","true");
		$(":input").attr("style","border:0;border-bottom:1 solid black;background:white;");
	}
});
 */


/**
 * 多记录刪除請求
 * @param title
 * @param url
 * @param gname
 * @return
 */
function deleteALLSelect(title,url,gname) {
	gridname=gname;
    var ids = [];
    var rows = $("#"+gname).datagrid('getSelections');
    if (rows.length > 0) {
    	$.dialog.confirm('你确定永久删除该数据吗?', function(r) {
		   if (r) {
				for ( var i = 0; i < rows.length; i++) {
					ids.push(rows[i].id);
				}
				$.ajax({
					url : url,
					type : 'post',
					data : {
						ids : ids.join(',')
					},
					cache : false,
					success : function(data) {
						var d = eval('(' + data + ')'); 
						if (d.success) {
							var msg = d.msg;
							tip(msg);
							reloadTable();
							$("#"+gname).datagrid('unselectAll');
							ids='';
						}else{
							var msg = d.msg;
							tip(msg);
						}
					}
				});
			}
		});
	} else {
		tip("请选择需要删除的数据");
	}
}


/**
 * 查看时的弹出窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createdetailwindow(title, addurl,width,height) {
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = document.body.offsetWidth;
		height =document.body.offsetHeight-100;
	}
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height: height,
			title:title,
			opacity : 0.3,
			cache:false, 
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		});
	}else{
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height: height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false, 
		    cancelVal: '关闭',
		    cancel: true /*为true等价于function(){}*/
		});
	}
	
}

// 删除调用函数
function delObj(url,name) {
	gridname=name;
	createdialog('删除确认 ', '确定删除该记录吗 ?', url,name);
}

// 普通询问操作调用函数
function confirm(url, content,name) {
	createdialog('提示信息 ', content, url,name);
}
/**
 * 提示信息
 */
function tip_old(msg) {
	$.dialog.setting.zIndex = 1980;
	$.dialog.tips(msg, 1);
}
/**
 * 提示信息
 */
function tip(msg) {
	$.dialog.setting.zIndex = 1980;
	$.messager.show({
		title : '提示信息',
		msg : msg,
		timeout : 1000 * 1.5
	});
}
/**
 * 提示异常
 */
function tiperror(msg) {
	$.dialog.setting.zIndex = 1980;
	$.messager.show({
		title : '系统发生错误',
		msg : msg,
		timeout : 1000 * 1.5
	});
}
/**
 * 创建添加或编辑窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function createwindow(title, addurl,width,height) {
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = document.body.offsetWidth;
		height =document.body.offsetHeight-100;
	}
	if(typeof(windowapi) == 'undefined'){
		$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height:height,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){
		    	iframe = this.iframe.contentWindow;
		  	saveObj();
		 	return false;
		    },
		    cancelVal: '关闭',
		   cancel: true /*为true等价于function(){}*/
		});
	}else{
		
		W.$.dialog({
			content: 'url:'+addurl,
			lock : true,
			width:width,
			height:height,
			parent:windowapi,
			title:title,
			opacity : 0.3,
			cache:false,
		    ok: function(){iframe = this.iframe.contentWindow;
				saveObj();
				return false;
		    },
		    cancelVal: '关闭',
		   cancel: true /*为true等价于function(){}*/
		});
	}
	
}
/**
 * 创建上传页面窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function openuploadwin(title, url,name,width, height) {
	gridname=name;
	$.dialog({
	    content: 'url:'+url,
	    cache:false,
	    button: [
	        {
	            name: '开始上传',
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
					iframe.upload();
					return false;
	            },
	            focus: true
	        },
	        {
	            name: '取消上传',
	            callback: function(){
	            	iframe = this.iframe.contentWindow;
					iframe.cancel();
	            }
	        }
	    ]
	});
}

/**
 * 创建不带按钮的窗口
 * 
 * @param title
 * @param addurl
 * @param saveurl
 */
function openwindow(title, url,name, width, height) {
	width = width?width:700;
	height = height?height:400;
	if(width=="100%" || height=="100%"){
		width = document.body.offsetWidth;
		height =document.body.offsetHeight-100;
	}
	if (typeof (width) == 'undefined'&&typeof (height) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				cache:false,
				lock : true,
				width: 'auto',
			    height: height
			});
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				cache:false,
				parent:windowapi,
				lock : true,
				width: 'auto',
			    height: height
			});
		}
	}
	if (typeof (height) == 'undefined'&&typeof (width) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				width: width,
				cache:false,
			    height: 'auto'
			});
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				parent:windowapi,
				width: width,
				cache:false,
			    height: 'auto'
			});
		}
	}
	if (typeof (width) == 'undefined'&&typeof (height) == 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				width: 'auto',
				cache:false,
			    height: 'auto'
			});
		}else{
			$.dialog({
				content: 'url:'+url,
				title : title,
				lock : true,
				parent:windowapi,
				width: 'auto',
				cache:false,
			    height: 'auto'
			});
		}
	}
	
	if (typeof (width) != 'undefined'&&typeof (height) != 'undefined')
	{
		if(typeof(windowapi) == 'undefined'){
			$.dialog({
				width: width,
			    height:height,
				content: 'url:'+url,
				title : title,
				cache:false,
				lock : true
			});
		}else{
			$.dialog({
				width: width,
			    height:height,
				content: 'url:'+url,
				parent:windowapi,
				title : title,
				cache:false,
				lock : true
			});
		}
	}
}

/**
 * 创建询问窗口
 * 
 * @param title
 * @param content
 * @param url
 */
function createdialog(title, content, url,name) {
	$.dialog.confirm(content, function(){
		doPost(url,name);
		rowid = '';
	}, function(){
	});
}
/**
 * 执行保存
 * 
 * @param url
 * @param gridname
 */
function saveObj() {
	$('#btn_sub', iframe.document).click();
}


/**
 * 退出确认框
 * 
 * @param url
 * @param content
 * @param index
 */
function exit(url, content) {
	$.dialog.confirm(content, function(){
		window.location = url;
	}, function(){
	});
}

function opensubwin(title, url, okbutton, closebutton) {
	$.dialog({
		content: 'url:'+url,
		title : title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : okbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				$('#btn_sub', iframe.document).click();
				return false;
			}
		}, {
			name : closebutton,
			callback : function() {
			}
		} ]

	});
}

function openauditwin(title, url, saveurl, okbutton, backbutton, closebutton) {
	$.dialog({
		content: 'url:'+url,
		title : title,
		lock : true,
		opacity : 0.3,
		button : [ {
			name : okbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = $.dialog.open.origin;// 来源页面
				$('#btn_sub', iframe.document).click();
				return false;
			}
		}, {
			name : backbutton,
			callback : function() {
				iframe = this.iframe.contentWindow;
				win = frameElement.api.opener;// 来源页面
				$('#formobj', iframe.document).form('submit', {
					url : saveurl + "&code=exit",
					onSubmit : function() {
						$('#code').val('exit');
					},
					success : function(r) {
						$.dialog.tips('操作成功', 2);
						win.location.reload();
					}
				});

			}
		}, {
			name : closebutton,
			callback : function() {
			}
		} ]

	});
}

/**
 * 添加事件打开窗口
 * @param title 编辑框标题
 * @param addurl//目标页面地址
 */
function add(title,addurl,gname,width,height) {
	gridname=gname;
	createwindow(title, addurl,width,height);
}

/**
 * 将选中的数据删除
 * @param {} title
 * @param {} url
 * @param {} gname
 */
function deleteSelect(title,url,gname) {
	gridname=gname;
    var row = $("#"+gname).datagrid('getSelected');
    if (row) {
    	$.dialog.confirm('你确定永久删除该数据吗?', function(r) {
		   if (r) {
				$.ajax({
					url : url,
					type : 'post',
					data :row,
					dataType:'json',
					cache : false,
					success : function(data) {
						if (data.success) {
							tip(data.msg);
							reloadTable(gname);
							$("#"+gname).datagrid('unselectAll');
						}else{
							var msg = data.msg;
							tip(msg);
						}
					}
				});
			}
		});
	} else {
		tip("请选择需要删除的数据");
	}
}
/**
 * 刷新表格
 * @param {} tableid
 */
function reloadTable(tableid){
	try{
		$('#'+tableid).datagrid('reload');
	}catch(ex){}
}
/**
 * 隐藏gride中的 pageList显示
 */
function hidePageList(){
	$('.pagination-page-list').hide();
}
/**
 * ajax提交FORM --过时...
 * 
 * @param url 提交的URL
 * @param formname fromid
 * @param gridname 需要刷新的gride
 */
function ajaxdoForm(url,formname,gridid) {
	$('#' + formname).form('submit', {
		url : url,
		onSubmit : function() {
			 return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			var api = frameElement.api;
        	var win = frameElement.api.opener;
	        var d =eval('(' + data + ')'); 
	        if(d.success==true){
				api.close();
				win.tip(d.msg);
				if(typeof(gridid) != 'undefined'){
					win.reloadTable(gridid);
				}
			}else{
				tip(d.msg);
			}
		}
	});
}

/**
 * 执行操作
 * 
 * @param url
 * @param index
 */
function doPost(url,name,queryParam) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		data:queryParam,
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = eval('(' + data + ')'); 
			var msg = d.msg;
			tip(msg);
			if(typeof(name) != 'undefined'){
					reloadTable(name);
			}
		}
	});
}
/**
 * 执行操作
 * 
 * @param url
 * @param index
 */
function doPostBack(url,backFunc,queryParam) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		data:queryParam,
		error : function() {// 请求失败处理函数
		},
		success : backFunc
	});
}
function doGet(url,name,queryParam) {
	$.ajax({
		async : false,
		cache : false,
		type : 'GET',
		url : url,// 请求的action路径
		data:queryParam,
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			var d = eval('(' + data + ')'); 
			if (d.success) {
				var msg = d.msg;
				tip(msg);
				if(typeof(name) != 'undefined'){
					reloadTable(name);
				}
				
			}
		}
	});
}
function doGetBack(url,backFunc,queryParam) {
	$.ajax({
		async : false,
		cache : false,
		type : 'GET',
		url : url,// 请求的action路径
		data:queryParam,
		error : function() {// 请求失败处理函数
		},
		success : backFunc
	});
}
function _toExcel(gridid,mbiztype){
	var row = $('#'+gridid).datagrid('options');
	var fieldAry = new Array();
	var titleAry = new Array();
	$.each(row.columns[0], function(i,val){      
		fieldAry.push(val.field);
		titleAry.push(val.title);
	});   
    var projectName=_BasePath+"/admin";
    var pm = JSON.stringify(row.queryParams);
	$.ajax({
		cache : false,
		type : 'POST',
		url : projectName+'/commonToExcel.do',// 请求的action路径
		data:{URL:row.url,SQLCODE:row.queryParams.sqlCode,COLFIELD:fieldAry,TITLE:titleAry,BIZTYPE:mbiztype,PARAM:pm},
		error : function(msg) {// 请求失败处理函数
			tip("请求失败!");
		},
		success : function(data) {
			var d = eval('(' + data + ')'); 
			if (d.success) {
				tip(d.msg);
				parent.say();
			}else{
				tip(d.msg);
			}

		}
	});
	}

function doSelectPost(pid,mid){
	var a = $('#'+pid).val();
	var b = $('#'+mid).attr('sqlcode');
	var p = $('#'+mid).attr('param');
    var projectName=_BasePath+"/admin";
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : projectName+"/commonSelect.do",// 请求的action路径
		data:{sqlcode:b,parent:a,param:p},  
		error : function() {// 请求失败处理函数
		},
		success : function(data) {
			//var d = $.parseJSON(data);
			var d = eval('(' + data + ')');  
			$("#"+mid).empty();
			$.each(d,function(n,value) {   
				 $("#"+mid).append("<option value='"+value.CODE+"'>"+value.CODENAME+"</option>");  
			});  
			$("#"+mid).change();
		}
	});
}
/**通过url加载from 不会使用缓存**/
function loadFrom(fromid,url){
	$("#"+fromid).form("load", url+"&_="+new Date());
}
/**跳转到登录页面**/
function goToLogin(){
    var projectName=_BasePath+"/admin";
    window.top.location.href=projectName+"/login.do";
}
/**
*ajax方式提交from 
*参数说明： 
*fromid 需要提交的from
*url 目标url
*backFun 回调函数
**/
function submitFrom(fromid,url,backFun){
	$('#'+fromid).form('submit',{
            	url:url,
                onSubmit:function(){
               		 $.messager.progress({
		                msg: '正在处理，请稍后...',
		            });
                	var isValid = $(this).form('enableValidation').form('validate');
                	if (!isValid){
						$.messager.progress('close');	// hide progress bar while the form is invalid
					}
                    return isValid;
                },
                success:function(data){
                	$.messager.progress('close');
                	backFun(data);
                }
            });
}

function submitFromWithTable(fromid,url,tableid){
	$('#'+fromid).form('submit',{
            	url:url,
                onSubmit:function(){
               		 $.messager.progress({
		                msg: '正在处理，请稍后...',
		            });
                	var isValid = $(this).form('enableValidation').form('validate');
                	if (!isValid){
						$.messager.progress('close');	// hide progress bar while the form is invalid
					}
                    return isValid;
                },
                success:function(data){
                	$.messager.progress('close');
			        var data = eval('(' + data + ')');
			        if (data.success) {
			       		var api = frameElement.api;
                		var win = frameElement.api.opener;
				        api.close();
						win.tip(data.msg);
						win.reloadTable(tableid);
			        }else{
						tip(data.msg);
			        } 
					
                }
            });
}


/**
 * 计算两个日期的差,返回差的月数(M)或天数(D)(其中天数除2.29这一天)
 * Example: dateDiff("2002-10-1", "2002-10-3", "D") returns "2";dateDiff("2002-1-1", "2002-10-3", "M") returns "9"
 * @param dateStart 减日期
 * @param dateEnd 被减日期
 * @param MD 标记，“M”为要求返回差的月数；“D”为要求返回差的天数
 * @return 返回两个日期差的月数(M)或天数(D)
 */
function dateDiff(dateStart,dateEnd,MD)
{
	if(dateStart==""||dateEnd=="")
	{
		return false;
	}
	var strSeparator = "-"; 
	
	var strDateArrayStart ;

    var strDateArrayEnd ;
    
	if(typeof(dateStart) == "string")
	{
		strDateArrayStart = dateStart.split(strSeparator);
		dateStart = new Date(strDateArrayStart[0] + "/" + strDateArrayStart[1] + "/" + strDateArrayStart[2]);
	}
	if(typeof(dateEnd) == "string")
	{
		strDateArrayEnd = dateEnd.split(strSeparator);
		dateEnd = new Date(strDateArrayEnd[0] + "/" + strDateArrayEnd[1] + "/" + strDateArrayEnd[2]);
	}
	
	var i;
	if(MD=="D")
	{
		//按天计算差
		var endD = dateEnd.getDate();
		var endM = dateEnd.getMonth();
		var endY = dateEnd.getFullYear();
		var startD = dateStart.getDate();
		var startM = dateStart.getMonth();
		var startY = dateStart.getFullYear();
		//modify by liping 081014 月份传入修改
		endM=endM+1;
		startM=startM+1;
		//end by liping 081014
		var startT=new Date(startY,startM-1,startD);
		var endT=new Date(endY,endM-1,endD);
		var diffDay=(endT.valueOf()-startT.valueOf())/86400000;
		return diffDay;
	}
	else
	{
		//按月计算差
		var endD = dateEnd.getDate();
		var endM = dateEnd.getMonth();
		var endY = dateEnd.getFullYear();
		var startD = dateStart.getDate();
		var startM = dateStart.getMonth();
		var startY = dateStart.getFullYear();
		if(endD>=startD)
		{
			return(endY-startY)*12 +(endM-startM) + 1;
		}
		else
		{
			return(endY-startY)*12 +(endM-startM);
		}
	}
}

  function dateDiff2(strDateStart,strDateEnd){
   var strSeparator = "-"; //日期分隔符
   var oDate1;
   var oDate2;
   var iDays;
   oDate1= strDateStart.split(strSeparator);
   oDate2= strDateEnd.split(strSeparator);
   var strDateS = new Date(oDate1[0], oDate1[1]-1, oDate1[2]);
   var strDateE = new Date(oDate2[0], oDate2[1]-1, oDate2[2]);
   iDays = parseInt(Math.abs(strDateS - strDateE ) / 1000 / 60 / 60 /24)//把相差的毫秒数转换为天数 
   return iDays ;
}
