/**
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.google.code.gwt.rest.rebind;

import static com.google.code.gwt.rest.rebind.RebindUtil.isGetter;
import static com.google.code.gwt.rest.rebind.RebindUtil.isReturnTypeJdkTypeOrPrimitiveType;
import static com.google.code.gwt.rest.rebind.RebindUtil.toPropertyName;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.code.gwt.rest.client.BeanConverter;
import com.google.code.gwt.rest.client.impl.AbstractBeanConverter;
import com.google.code.gwt.rest.client.impl.MapParamConverter;
import com.google.code.gwt.rest.client.util.SimpleTemplate;
import com.google.code.gwt.rest.client.util.TemplateBuilder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * Generator to create {@link BeanConverter}.
 * 
 * @author Takeshi Kondo
 */
public class BeanConverterGenerator extends Generator {

	static public Type logLevel = Type.DEBUG;

	static final SimpleTemplate MAIN_CLASS = new TemplateBuilder() {
		{
			$();
			$("{converterDeclarations}");
			$();
			$("public java.util.Map<String, Object> toMap(String prefix,{typeName} object)\\{");
			$("   java.util.Map<String,Object> map = new java.util.LinkedHashMap<String,Object>();");
			$("   if(object == null)\\{");
			$("    return map;");
			$("   \\}");
			$("{convertLogic}");
			$("  return map;");
			$("\\}");
			$();
		}
	}.parseToken();

	static final SimpleTemplate PROPERTY_COMVERTER = new TemplateBuilder() {
		{
			$(" interface Converter__{propertyName} extends ");
			$("      {FormParamConverter}<{propertyType}> \\{\\}");
		}
	}.parseToken();

	static final SimpleTemplate ACTUAL_TYPE_CONVERTER = new TemplateBuilder() {
		{
			$(" interface Converter__{propertyName} extends ");
			$("      {FormParamConverter}<{actualType}> \\{\\}");
		}
	}.parseToken();

	static final SimpleTemplate PRIMITIVE_TYPE_CONVERTER = new TemplateBuilder() {
		{
			$("  map.put(prefix+\"{propertyName}\",object.{getterName}());");
		}
	}.parseToken();

	static final SimpleTemplate MAP_CONVERTER = new TemplateBuilder() {
		{
			$("  map.putAll(new {MapParamConverter}().toMap(prefix+\"{propertyName}.\",object.{getterName}()));");
		}
	}.parseToken();

	static final SimpleTemplate LIST_CONVERTER = new TemplateBuilder() {
		{
			$(" Converter__{propertyName}  converter__{propertyName} = ");
			$("  {GWT}.create(Converter__{propertyName}.class);");
			$("  if(object.{getterName}() != null)\\{");
			$("    for(int i = 0 ; i<object.{getterName}().length();i++)\\{");
			$("      {actualType} o = object.{getterName}().get(i);");
			$("      map.putAll(converter__{propertyName}.toMap(prefix+\"{propertyName}[\"+i+\"].\",o));");
			$("  \\}");
			$("\\}");
		}
	}.parseToken();

	static final SimpleTemplate BEAN_CONVERTER = new TemplateBuilder() {
		{
			$(" Converter__{propertyName}  converter__{propertyName} = ");
			$("     {GWT}.create(Converter__{propertyName}.class);");
			$("  map.putAll(converter__{propertyName}.toMap(prefix+\"{propertyName}.\",object.{getterName}()));");
		}
	}.parseToken();

	static final SimpleTemplate SUPERTYPE_CONERTER = new TemplateBuilder() {
		{
			$(" Converter__{propertyName}  converter__{propertyName} = ");
			$("     {GWT}.create(Converter__{propertyName}.class);");
			$("  map.putAll(converter__{propertyName}.toMap(object));");
		}
	}.parseToken();

	static public Map<String, Object> createContextMap() {
		// set static values
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("FormParamConverter", BeanConverter.class.getName());
		map.put("MapParamConverter", MapParamConverter.class.getName());
		map.put("GWT", GWT.class.getName());

		return map;
	}

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {

		Map<String, Object> contextMap = createContextMap();

		try {
			TypeOracle oracle = context.getTypeOracle();
			JClassType type = oracle.findType(typeName);

			// Define creation class name
			String packageName = type.getPackage().getName();
			String simpleSourceName = type.getName().replace('.', '_') + "Impl";

			PrintWriter pw = context.tryCreate(logger, packageName,
					simpleSourceName);
			if (pw == null) {
				return packageName + "." + simpleSourceName;
			}

			// set SuperClass and Interface
			JClassType beanType = type.getImplementedInterfaces()[0]
					.isParameterized().getTypeArgs()[0];

			ClassSourceFileComposerFactory factory = new ClassSourceFileComposerFactory(
					packageName, simpleSourceName);
			if (type.isInterface() != null) {
				factory.setSuperclass(AbstractBeanConverter.class.getName()
						+ "<" + beanType.getQualifiedSourceName() + ">");
				factory.addImplementedInterface(typeName);
			} else {
				factory.setSuperclass(typeName);
			}

			logger.log(logLevel, "create FormParamConverter " + beanType);

			SourceWriter sw = factory.createSourceWriter(context, pw);

			StringBuilder converterDeclarations = new StringBuilder();
			StringBuilder convertLogic = new StringBuilder();

			contextMap.put("typeName", beanType.getQualifiedSourceName());

			// call super class FormParamConverter

			JClassType superClass = beanType.getSuperclass();
			if (superClass != null) {
				if (!superClass.getQualifiedSourceName().equals(
						Object.class.getName())) {
					contextMap.put("actualType",
							superClass.getQualifiedSourceName());
					contextMap.put("propertyName", "__super__");
					converterDeclarations.append(ACTUAL_TYPE_CONVERTER
							.resolve(contextMap));
					convertLogic.append(SUPERTYPE_CONERTER.resolve(contextMap));
				}
			}

			for (JMethod method : beanType.getMethods()) {

				if (!isGetter(method)) {
					continue;
				}

				JClassType returnType = method.getReturnType().isClass();
				if (returnType == null) {
					returnType = method.getReturnType().isInterface();
				}

				contextMap.put("propertyName", toPropertyName(method));
				contextMap.put("getterName", method.getName());
				contextMap.put("propertyType", method.getReturnType()
						.getQualifiedSourceName());

				if (returnType != null
						&& returnType.isAssignableTo(oracle.findType(Map.class
								.getName()))) {
					convertLogic.append(MAP_CONVERTER.resolve(contextMap));
					continue;
				}

				if (returnType != null
						&& returnType.isAssignableTo(oracle
								.findType(JsArray.class.getName()))) {
					JClassType actualType = returnType.isParameterized()
							.getTypeArgs()[0];

					contextMap.put("actualType",
							actualType.getQualifiedSourceName());

					converterDeclarations.append(ACTUAL_TYPE_CONVERTER
							.resolve(contextMap));
					convertLogic.append(LIST_CONVERTER.resolve(contextMap));
					continue;
				}

				if (isReturnTypeJdkTypeOrPrimitiveType(method)) {
					convertLogic.append(PRIMITIVE_TYPE_CONVERTER
							.resolve(contextMap));
					continue;
				}
				converterDeclarations.append(PROPERTY_COMVERTER
						.resolve(contextMap));
				convertLogic.append(BEAN_CONVERTER.resolve(contextMap));

			}

			contextMap.put("converterDeclarations", converterDeclarations);
			contextMap.put("convertLogic", convertLogic);

			String mainCode = MAIN_CLASS.resolve(contextMap);
			logger.log(logLevel, "create souce " + simpleSourceName + " \n"
					+ mainCode);
			sw.print(mainCode);
			sw.commit(logger);

			return packageName + "." + simpleSourceName;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

}
