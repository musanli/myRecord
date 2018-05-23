$(function(){
  $(".s_pic2 dl").hover(function(){
	$(this).find(".more1").slideDown();
   $(this).find(".tit1").slideUp();
  },function(){
   $(this).find(".more1").slideUp();
	$(this).find(".tit1").slideDown();
  });
  $(".pay2 li").click(function(){
	  $(this).addClass('on').siblings("li").removeClass('on');
  });
  $(".h_order li").click(function(){
	  $(this).addClass('hover').siblings("li").removeClass('hover');
	  $(this).children('.h_dd2').show().siblings("li").children('.h_dd2').hide();
  });
//  $(".h_order li").click(function(){
//	  $(this).addClass('hover').siblings("li").removeClass('hover');
//	  $(this).children('.h_dd2').toggle();
//  });
	dingwei();
});
function dingwei(){
	var webAddress = location.href;
	webAddress = webAddress.split("?")[0];
	webAddress = webAddress.split("/");
	webAddress = webAddress[webAddress.length-1]
	webAddress = webAddress.split(".")[0];
	webAddress = webAddress.split("_")[0];
	$('.nav li').find("a").each(function() {
		var Address = $(this).attr('href');	
			Address = Address.split("?")[0];
			Address = Address.split("/");
			Address = Address[Address.length-1]
			Address = Address.split(".")[0];
			Address = Address.split("_")[0];
		if (webAddress == Address)
			$(this).parents("li").addClass('hover');
	});
}
// JavaScript Document
jQuery(function ($) {
    if ($(".slide-pic").length > 0) {
        var defaultOpts = { interval: 5000, fadeInTime:800, fadeOutTime: 400 };
        var _titles = $("ul.slide-txt li");
        var _titles_bg = $("ul.op li");
        var _bodies = $("ul.slide-pic li");
        var _count = _titles.length;
        var _current = 0;
        var _intervalID = null;
        var stop = function () { window.clearInterval(_intervalID); };
        var slide = function (opts) {
            if (opts) {
                _current = opts.current || 0;
            } else {
                _current = (_current >= (_count - 1)) ? 0 : (++_current);
            };
            _bodies.filter(":visible").fadeOut(defaultOpts.fadeOutTime, function () {
                _bodies.eq(_current).fadeIn(defaultOpts.fadeInTime);
                _bodies.removeClass("cur").eq(_current).addClass("cur");
            });
            _titles.removeClass("cur").eq(_current).addClass("cur");
            _titles_bg.removeClass("cur").eq(_current).addClass("cur");
        };
        var go = function () {
            stop();
            _intervalID = window.setInterval(function () { slide(); }, defaultOpts.interval);
        };
        var itemMouseOver = function (target, items) {
            stop();
            var i = $.inArray(target, items);
            slide({ current: i });
        };
        _titles.hover(function () { if ($(this).attr('class') != 'cur') { itemMouseOver(this, _titles); } else { stop(); } }, go);
        _bodies.hover(stop, go);
        go();
    }
});
$(function(){
	$(".select_box span").click( function () { 
		$(this).parent().find('ul.son_ul').slideDown("fast");
		$(this).parent().find('li').hover(function(){$(this).addClass('select_box_hover')},function(){$(this).removeClass('select_box_hover')}); //li的hover效果
		$(this).parent().hover(function(){},
			function(){
				$(this).parent().find("ul.son_ul").slideUp("fast"); 
			}
		);
	 })
	$('ul.son_ul li').click(function(){
		var relid=$(this).attr("rel");
		//reliid=relid.split(",")[0];
		//var ulid=relid.split(",")[1];
		$(this).parent().parent().find('span').html($(this).html());
		$(this).parent().parent().find('ul').slideUp("fast");
		
		});
	}
	
);

//function topxiala(obj){
//		var relid=obj.attr("rel");
//		reliid=relid.split(",")[0];
//		var ulid=relid.split(",")[1];

//		obj.parent().parent().parent().find('input').val(reliid);
//		obj.parent().parent().find('span').html(obj.html());
//		obj.parent().parent().find('ul').slideUp("fast");
//}



//$(function(){
//	  
//  $("div.i_news1 dl").hover(function(){
//	$(this).addClass("hover");
//	$(this).find("dd").show();
//	$(this).siblings("dl").find("dd").hide();
//	$(this).siblings("dl").removeClass("hover");
//  },function(){
//  })
//  $("#gun li:odd").addClass("bg1");
//  $(".n_xx1 li:odd").addClass("bg1");
//});
//function abc(){
//  xialadiv=$(".top_nav");
//  dis=xialadiv.css("display");
//  if(dis=="none"){
//	  $("#topnav").click(function(){
//	  $(".top_nav").show();
//	  //alert("00");
//  })
//	  }
//  else{
//	  $("#topnav").click(function(){
//		  $(".top_nav").hide();
//		  //alert("00");
//	  })
//  }
//}
//
//	
//function setTab(name,cursel,n){
// for(i=1;i<=n;i++){
//  var menu=document.getElementById(name+i);
//  var con=document.getElementById("con_"+name+"_"+i);
//  menu.className=i==cursel?"hover":"";
//  con.style.display=i==cursel?"block":"none";
// }
//}
