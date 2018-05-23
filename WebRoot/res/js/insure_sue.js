 $('#document').ready( function(){
   $('#sub4_s').click( function(){
     $("#nopay").val("0");
	   goNext();
     });
   
    $('#save_s').click( function(){
      $("#nopay").val("1");
	 goNext();
   });
  
 });
 
 function goNext(){
 
       var pathName=window.document.location.pathname;
       var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);

   	$.ajax({
			async : false,
			cache : false,
			type : 'POST',
			url :  projectName+'/insureProcess/insure_insuredTo.htm',// 请求的action路径
			data:$("#surform").serialize(),
			error : function() {
			
			},
			success : function(data) {
					var d = eval('(' + data + ')');  
					if (d.success==false) {
					  $.jBox.error(d.msg, '提示信息');
					}else{
					     $("#orderno").val(d.msg);
					     surform.submit();
					}
				}
		});
	
 }
 
 
 

