package com.main.util.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class SelectTag implements TemplateDirectiveModel{


	
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
			TemplateDirectiveBody body) throws TemplateException, IOException {
		
		SimpleScalar id = (SimpleScalar) params.get("id");
		SimpleScalar  name = (SimpleScalar) params.get("name");
		SimpleScalar type = (SimpleScalar) params.get("type");
		SimpleScalar classess = (SimpleScalar) params.get("class");
		SimpleScalar value = (SimpleScalar) params.get("value");
		SimpleScalar sqlcode = (SimpleScalar) params.get("sqlcode");
		SimpleScalar param = (SimpleScalar) params.get("pm");
		SimpleScalar parent = (SimpleScalar) params.get("parent");
		SimpleScalar style = (SimpleScalar) params.get("style");
		
		Writer out = env.getOut();
		String base = System.getProperty("/MyRecord");
		StringBuilder builder = new StringBuilder();
		StringBuilder scrictBuilder = new StringBuilder();
		StringBuilder parmBuilder = new StringBuilder();
		builder.append("<input id=\"");
		if (id != null && StringUtils.isNotBlank(id.getAsString())) {
			builder.append(id);
		} else if (name != null && StringUtils.isNotBlank(name.getAsString())) {
			builder.append(name);
			id = name;
		}
		builder.append("\"");
		if (name != null && StringUtils.isNotBlank(name.getAsString())) {
			builder.append(" name=\"").append(name).append("\"");
		}
		builder.append(" class=\"easyui-combobox");
		if (classess != null && StringUtils.isNotBlank(classess.getAsString())) {
			builder.append("  ").append(classess).append(" ");
		} 
		builder.append("\" ");
		if (style != null && StringUtils.isNotBlank(style.getAsString())) {
			builder.append(" style=\"").append(style).append("\"");
		}
		
		builder.append(">");
		
		if (type != null && StringUtils.isNotBlank(type.getAsString())) {
			parmBuilder.append("sqlCode:'SYS.CommonMapper.SelectCodeByCodeType',codetype:'").append(type.getAsString()).append("',");
			if (value != null && StringUtils.isNotBlank(value.getAsString())) {
				parmBuilder.append("defaultValue:'").append(value.getAsString()).append("',");
			}
		}else if(sqlcode != null && StringUtils.isNotBlank(sqlcode.getAsString())){
			parmBuilder.append("sqlCode:'").append(sqlcode.getAsString()).append("',");
			if (value != null && StringUtils.isNotBlank(value.getAsString())) {
				parmBuilder.append("defaultValue:'").append(value.getAsString()).append("',");
			}
			if (param != null && StringUtils.isNotBlank(param.getAsString())) {
				parmBuilder.append(param);
			}
		}
		StringBuilder scrictBuilder2 = new StringBuilder();
		if (parent != null && StringUtils.isNotBlank(parent.getAsString())) {
			
			scrictBuilder2.append("<script type=\"text/javascript\">");
			scrictBuilder2.append("$(function(){");
			scrictBuilder2.append("$('#").append(parent.getAsString()).append("').combobox({");
			scrictBuilder2.append("onChange:function(n,o){ ");
			scrictBuilder2.append(" var queryParams =$('#").append(id.getAsString()).append("').combobox('options').queryParams;");
			scrictBuilder2.append(" queryParams._parent = n; ");
			scrictBuilder2.append(" $('#").append(id.getAsString()).append("').combobox('clear'); ");
			scrictBuilder2.append(" $('#").append(id.getAsString()).append("').combobox('reload','").append(base).append("/admin/commonCreatSelect.do'); ");
			scrictBuilder2.append("}");
			scrictBuilder2.append("});");
			scrictBuilder2.append("});");
			scrictBuilder2.append("</script>");
		}
		scrictBuilder.append("<script type=\"text/javascript\">");
		scrictBuilder.append("$(function(){");
		scrictBuilder.append("$('#").append(id.getAsString()).append("').combobox({");
		if (parent != null && StringUtils.isNotBlank(parent.getAsString())) {
			scrictBuilder.append("url:'', ");//级联菜单 默认不加载 上一级改变后，会自动加载
		}else{
//			scrictBuilder.append("url:'").append(base).append("/admin/commonCreatSelect.do', ");
			scrictBuilder.append("url:'").append("/MyRecord/test/ui01.do', ");
		}
		scrictBuilder.append(" valueField:'CODE',");
		scrictBuilder.append(" textField:'CODENAME', ");
		scrictBuilder.append(" queryParams:{").append(parmBuilder).append("}");
		scrictBuilder.append("});");
		scrictBuilder.append("});");
		scrictBuilder.append("</script>");
		builder.append(scrictBuilder);
		builder.append(scrictBuilder2);
		out.write(builder.toString());
	}



}
