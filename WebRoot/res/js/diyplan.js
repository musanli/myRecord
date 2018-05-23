$(function(){
  clearAll();
});
function clearAll(){
$("#diyForm input[type='hidden']").each(function(){
	$(this).val("");
});
$("#main_box .select_box span").each(function(){
	$(this).html("选择保额");
});
$("#perSpan").html("选择保障期限");
$("#arrivedate").val("请输入生效日期");
$("#planList").html("");
$("#planList").append("<li id='plan_period'><span class='wd1 bold'>保障期限　</span><span class='wd3 bold'>0天</span></li>");
$("#totlePrem").html("0");
}
function calPream(obj){
		var relid=obj.attr("rel");
		var ipt = obj.parent().parent().parent().find('input');
		ipt.val(relid);
		
		var pathName=window.document.location.pathname;
    	var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    	var url = projectName+"/cal/calPrem.htm"
    	var queryParam = $("#diyForm").serialize();
		doPost(url,queryParam,calPremByPeriodBack);
}
function calPremByPeriodBack(data){
 	var d = eval('(' + data + ')');
	 if(d.success) {
	 	var listData = d.msg;
	 	var totlePrem = 0;
		$.each(listData,function(n,value){
			var perLi = $("#plan_period")
			totlePrem = value.totlePrem;
		 	if(perLi.length>0){
		 		var li = "<span class='wd1 bold'>保障期限　</span><span class='wd3 bold'>"+value.period+"天</span>";
				perLi.html(li);
		 	}
			var riskLi = $("#plan_"+value.riskcode);
			var riskName = $("#"+value.riskcode+"_name").html();
			if(riskLi.length>0){
				var li = "<span class='wd1'>"+riskName+"：</span><span class='wd2'>"+value.amnt+"元</span><span class='wd3'><span class='prem'>"+value.prem+"</span>元</span>";
				riskLi.html(li);
			}else{
				var li = "<li id='plan_"+value.riskcode+"'><span class='wd1'>"+riskName+"：</span><span class='wd2'>"+value.amnt+"元</span><span class='wd3'><span class='prem'>"+value.prem+"</span>元</span></li>";
				$("#planList").append(li);
			}
        }
        );
        $("#totlePrem").html(totlePrem);
	 }else{
	 	jBox.tip(d.msg, 'info');
	 }
}

function doPost(url,queryParam,calback) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : url,// 请求的action路径
		data:queryParam,
		error : function(data) {// 请求失败处理函数
			jBox.tip("抱歉，数据请求错误！", 'info');
		},
		success :calback
	});
}
function gotoNext(){
    var queryParam = $("#diyForm").serialize();
    var pathName=window.document.location.pathname;
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
    var url = projectName+'/insureProcess/insure_b_v.htm';
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url  : url,// 请求的action路径
		data : queryParam,
		success : function(data) {
		 var data = eval('(' + data + ')');  
	      if(data.success==true){
	         diyForm.submit();
	      }else{
	      $.jBox.error(data.msg, '');
	      }
		  
		}
	});
}