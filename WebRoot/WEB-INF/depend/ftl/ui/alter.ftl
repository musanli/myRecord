<#macro alter title="" content="" other="" confirm="" cancel="" >
<div class="m-layer m-layer-show">
    <div class="lymask"></div>
    <table class="lytable"><tbody><tr><td class="lytd">
    <div class="lywrap">
	    <div class="lytt"><h2 class="u-tt">${title}</h2><span class="lyclose" onclike="">×</span></div>
	    <div class="lyct">
	        ${content}
	    </div>
	    <div class="lybt">
	        <div class="lyother">
	            <p>${other}</p>
	        </div>
	        <div class="lybtns">
	            <button type="button" class="u-btn" <#if confirm!=""> onclick="${confirm}"</#if><#rt/>>确定</button>
	            <button type="button" class="u-btn u-btn-c4" <#if cancel!=""> onclick="${cancel}"</#if><#rt/>>取消</button>
	        </div>
	    </div>
    </div></td></tr></tbody></table>
</div>
</#macro>