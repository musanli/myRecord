package com.main.util.freemarker;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class SelectDirectiveTag implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars,
		TemplateDirectiveBody body) throws TemplateException, IOException {
		SimpleScalar id = (SimpleScalar) params.get("id");
		SimpleScalar  name = (SimpleScalar) params.get("name");
		SimpleScalar type = (SimpleScalar) params.get("type");
		SimpleScalar classess = (SimpleScalar) params.get("class");
		SimpleScalar value = (SimpleScalar) params.get("value");
		SimpleScalar sty = (SimpleScalar) params.get("style");
		SimpleScalar sqlcode = (SimpleScalar) params.get("sqlcode");
		SimpleScalar param = (SimpleScalar) params.get("pm");
		SimpleScalar parent = (SimpleScalar) params.get("parent");
		Writer out = env.getOut();
		StringBuilder builder = new StringBuilder();
		StringBuilder scrictBuilder = new StringBuilder();
		builder.append("<select id=\"");
		if (id != null && StringUtils.isNotBlank(id.getAsString())) {
			builder.append(id);
		} else if (name != null && StringUtils.isNotBlank(name.getAsString())) {
			builder.append(name);
		}
		builder.append("\"");
		if (name != null && StringUtils.isNotBlank(name.getAsString())) {
			builder.append(" name=\"").append(name).append("\"");
		}
		builder.append(" class=\"  ");
		if (classess != null && StringUtils.isNotBlank(classess.getAsString())) {
			builder.append("  ").append(classess).append(" ");
		} 
		builder.append(" \"");
		if (sty != null && StringUtils.isNotBlank(sty.getAsString())) {
			builder.append(" style=\"").append(sty).append("\"");
		}
		if (sqlcode != null && StringUtils.isNotBlank(sqlcode.getAsString())) {
			builder.append(" sqlcode=\"").append(sqlcode).append("\"");
		}
		if (param != null && StringUtils.isNotBlank(param.getAsString())){
			builder.append(" param=\"").append(param).append("\"");
		}
		builder.append(">");
		List<Map<String,String>> dictList = new ArrayList<Map<String,String>>();
		if (parent != null && StringUtils.isNotBlank(parent.getAsString())) {
			scrictBuilder.append("<script type=\"text/javascript\">");
			scrictBuilder.append("$(function(){");
			scrictBuilder.append("$('#").append(parent.getAsString()).append("').change(function(){");
			scrictBuilder.append(" doSelectPost('").append(parent.getAsString()).append("','").append(id.getAsString()).append("');");
			scrictBuilder.append("});");
			scrictBuilder.append("$('#").append(parent.getAsString()).append("').change();");
			scrictBuilder.append("});");
			scrictBuilder.append("</script>");
		}else{
//			if (type != null && StringUtils.isNotBlank(type.getAsString())) {
//				dictList = cs.getInstance().getDict(type.getAsString());
//				if(dictList == null){
//					dictList = reader.queryForList("SYS.CommonMapper.SelectCodeByCodeType", type.getAsString());
//				}
//			}else if (sqlcode != null && StringUtils.isNotBlank(sqlcode.getAsString())) {
//				Dto prmDto = new BaseDto();
//				if (param != null && StringUtils.isNotBlank(param.getAsString())){
//					prmDto = JsonHelper.parseSingleJson2Dto(param.getAsString());
//				}
//				dictList = reader.queryForList(sqlcode.getAsString(), prmDto);
//			}
		}
		
		if (dictList != null) {
			boolean flag = false;
			if(value != null){
				flag = true;
			}
			for (Map<String,String> s : dictList) {
				builder.append("<option value=\"").append(s.get("CODE")).append("\"");
				if(flag){
					if (value.getAsString().equals(s.get("CODE"))){
						builder.append(" selected");
					}
				}
				builder.append(">").append(s.get("CODENAME")).append("</option>");
			}
		}
		
		builder.append("</select>");
		builder.append(scrictBuilder);
		out.write(builder.toString());
	}

}
