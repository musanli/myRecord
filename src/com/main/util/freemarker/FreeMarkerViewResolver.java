package com.main.util.freemarker;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import com.main.util.SSILogger;
import com.main.util.SSIUtils;


public class FreeMarkerViewResolver extends AbstractTemplateViewResolver{
	/**
	 * Set default viewClass
	 */
	public FreeMarkerViewResolver() {
		setViewClass(MyFreeMarkerView.class);
	}

	/**
	 * if viewName start with / , then ignore prefix.
	 */
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		AbstractUrlBasedView view = super.buildView(viewName);
		// start with / ignore prefix
		if (viewName.startsWith("/")) {
			view.setUrl(viewName + getSuffix());
		}
		return view;
	}
}


/**
 * 扩展spring的FreemarkerView，加上base属性。
 * 
 * 支持jsp标签，Application、Session、Request、RequestParameters属性
 * 
 * 
 */
class MyFreeMarkerView extends FreeMarkerView {

	
	/**
	 * 部署路径属性名称
	 */
	public static final String CONTEXT_PATH = "base";
	/**
	 * 版本号
	 */
	public static final String CONTEXT_VERSION = "version";
	/**
	 * 在model中增加部署路径base，方便处理部署路径问题。
	 */
	@SuppressWarnings("unchecked")
	protected void exposeHelpers(Map model, HttpServletRequest request)
			throws Exception {
		super.exposeHelpers(model, request);
		model.put(CONTEXT_PATH, request.getContextPath());
		
		
//		String version = PayConfig.getInstance().getValue("version");
//		if(version==null){
//			version = "1.0.0";
//		}
//		model.put(CONTEXT_VERSION, version);
	}

}
