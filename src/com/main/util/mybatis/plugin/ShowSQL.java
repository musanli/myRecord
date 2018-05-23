package com.main.util.mybatis.plugin;

import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandlerRegistry;

public class ShowSQL {

	
	public static String getSql(Configuration configuration, BoundSql boundSql,
			String sqlId, long time) {
		String sql = showSql(configuration, boundSql);
		StringBuilder str = new StringBuilder(100);
		str.append("[");
		str.append(time);
		str.append("ms]");
		str.append(sqlId);
		str.append(":");
		str.append(sql);
		return str.toString();
	}
    
    public static String showSql(Configuration configuration, BoundSql boundSql) {
		Object parameterObject = boundSql.getParameterObject();
		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
		if (parameterMappings.size() > 0 && parameterObject != null) {
			TypeHandlerRegistry typeHandlerRegistry = configuration
					.getTypeHandlerRegistry();
			if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
				sql = sql.replaceFirst("\\?",
						getParameterValue(parameterObject));

			} else {
				MetaObject metaObject = configuration
						.newMetaObject(parameterObject);
				for (ParameterMapping parameterMapping : parameterMappings) {
					String propertyName = parameterMapping.getProperty();
					if (metaObject.hasGetter(propertyName)) {
						Object obj = metaObject.getValue(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						Object obj = boundSql
								.getAdditionalParameter(propertyName);
						sql = sql.replaceFirst("\\?", getParameterValue(obj));
					}
				}
			}
		}
		return sql;
	}
    
//    public Object plugin(Object target) {
//		return Plugin.wrap(target, this);
//	}

	private static String getParameterValue(Object obj) {
		String value = null;
		if (obj instanceof String) {
			value = "'" + obj.toString() + "'";
		} else if (obj instanceof Date) {
			DateFormat formatter = DateFormat.getDateTimeInstance(
					DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
			value = "'" + formatter.format(new Date()) + "'";
		} else {
			if (obj != null) {
				value = obj.toString();
			} else {
				value = "";
			}

		}
		return value;
	}

	public static Object proceed(Invocation invocation) throws InvocationTargetException, IllegalAccessException {

		MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
		
		 long start = System.currentTimeMillis();
         Object parameter = null;
 		if (invocation.getArgs().length > 1) {
 			parameter = invocation.getArgs()[1];
 		}
 		String sqlId = ms.getId();
 		BoundSql boundSql = ms.getBoundSql(parameter);
 		Object result = invocation.proceed();
		 long end = System.currentTimeMillis();
 		long time = (end - start);
// 		if (time > 1) {
 			String sql = getSql(ms.getConfiguration(), boundSql, sqlId, time);
 			System.out.println(sql);
// 		}
		return result;
	}
}
