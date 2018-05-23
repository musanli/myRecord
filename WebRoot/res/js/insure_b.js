 $('#document').ready( function(){
  
   $('#sub4_s').click( function(){
   var queryParam = $("#fm").serialize();
    doPost('insure_sue_v.htm',queryParam);
 });
 });
 
 
 function selectInput(obj){
 
		var relid=obj.attr("rel");
		var ipt = obj.parent().parent().parent().find('input');
		ipt.val(relid);
		prtSameIn();
}

function  SelectInput3(falg){
  if(falg==1){
      $("#city_S").html('');
      $("#city").val('');
  }else{
      $("#bcity").val('');
      $("#bcity_S").html('');
  }
}

  //axjaxs 提交
 //校验失败提示 错误
 //校验成功后台提交到 下一个页面
 function doPost(url,queryParam) {
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url  : url,// 请求的action路径
		data : queryParam,
		success : function(data) {
		 var data = eval('(' + data + ')');  
	      if(data.success==true){
	         insured_login();
	      }else{
	         var tempdate = data.msg;
	         $.jBox.error(data.msg, '提示信息');
	      }
		}
	});
}

 function insured_login(){

       var pathName=window.document.location.pathname;
       var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
   	$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url :  projectName+'/insureProcess/insure_userLoginCheck.htm',// 请求的action路径
			data:$("#surform").serialize(),
			error : function() {
			
			},
			success : function(data) {
					var d = eval('(' + data + ')');  
					if (!d.success) {
						$.jBox("iframe:"+projectName+'/insureProcess/insure_login.htm?phone='+$("#phone").val(),  { 
								title: '登陆提示',
								width: 800,
								border:0,
								height: 350,
								buttons: {"下一步":'ok',"关闭":'no'} ,
								submit:function(v,h,f){
						　　　　　　　　　if(v == "ok"){  
						                if(f.islogin=='0'){
						                  insured_login();
						                }
						　　　　　　　　　}
						              if(v == "no"){  
						　　　　　　　　　}
						　　　　　　　}
							});
					}else{
					  fm.submit();
					}
				}
		});
}



function prtSameIn(){
  
  if($("#fsex").attr("checked")){
    $("#bfsex").attr("checked", true);
  }
  if($("#msex").attr("checked")){
    $("#bmsex").attr("checked", true);
  }
  
  if($("#bname").val()==''){
   $("#bname").val($("#name").val());
  }
  if($("#bidno").val()==''){
   $("#bidno").val($("#idno").val());
  }
  if($("#bphone").val()==''){
   $("#bphone").val($("#phone").val());
  }
  if($("#baddress").val()==''){
   $("#baddress").val($("#address").val());
  }
  if($("#bbirthday").val()==''){
   $("#bbirthday").val($("#birthday").val());
  }
   
   $("#bidtype_S").html($("#idtype_S").html());
   $("#bidtype").val($("#idtype").val());
 
   $("#bcity_S").html($("#city_S").html());
   $("#bcity").val($("#city").val());
    
    if($("#province").val()!=''){
       $("#bprovince").val($("#province").val());
       $("#bprovince_S").html($("#province_S").html());
    }
}

 function bt_city(){
 var tempdate = $("#province").val();
  bt_jiliang( tempdate ,'ces_bt' )
}

 function bt_bcity(){
 var tempdate = $("#bprovince").val();
  bt_jiliang( tempdate ,'bces_bt' )
}

 function bt_jiliang( tempdate,showId ){

 var pathName=window.document.location.pathname;
 var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
 var queryParam = {"sprovince":""+tempdate+""};
  if(tempdate!=''){
	$.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : projectName+'/insureProcess/insure_jiliang.htm',// 请求的action路径
		data:queryParam,
		error : function() {// 请求失败处理函数
		},
		success :function(data) {
	    $("#"+showId).html(data);
		}
	});   
  }
}

 function selectInput2(obj){
	
	var relid=obj.attr("rel");
		var ipt = obj.parent().parent().parent().find('input');
		ipt.val(relid);
		$("#city").val(relid);
		 
   	 var queryParam = $("#fm").serialize();
 	 var pathName=window.document.location.pathname;
     var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);
	   $.ajax({
		async : false,
		cache : false,
		type : 'POST',
		url : projectName+'/insureProcess/insure_jiliangName.htm',// 请求的action路径
		data:queryParam,
		error : function() {// 请求失败处理函数
		},
		success :function(data) {
		
	    $("#city_S").html(data);
		}
	}); 
	prtSameIn();
}



