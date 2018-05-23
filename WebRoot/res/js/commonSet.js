$(function(){
	$.ajaxSetup({
	   
　　　　error: function (xhr, status, e) { 
		$.messager.progress('close');
		
		switch (xhr.status){  
                case(500):  
                    tiperror("服务器系统内部错误");  
                    break;  
                case(999):  
                   $.dialog({
						lock : true,
						title:'登录超时',
						content:'由于长时间没有登录，您被强制跳转到登录页面。',
						opacity : 0.3,
						cache:false,
					    ok: function(){
					    	 goToLogin();
						    }
						}); 
                    break;  
                case(404):  
                    tiperror('您访问的页面不存在！');  
                    break;  
                default:  
                   tiperror('系统发生错误请联系管理员！');
            }  
		
		},
　　　　complete: function (xhr, status) {
 		$.messager.progress('close');
 		switch (xhr.status){  
                case(500):  
                    tiperror("服务器系统内部错误");  
                    break;  
                case(999):  
                   $.dialog({
						lock : true,
						title:'登录超时',
						content:'由于长时间没有登录，您被强制跳转到登录页面。',
						opacity : 0.3,
						cache:false,
					    ok: function(){
					    	 goToLogin();
						    }
						}); 
                    break;  
                case(404):  
                    tiperror('您访问的页面不存在！');  
                    break;
            }  
		},
　　　　beforeSend: function (xhr) {
 			$.messager.progress({
                msg:'正在处理，请稍后...(全局)'
            });
		}
	});
});
