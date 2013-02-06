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

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.code.gwt.rest.client.BeanConverter;
import com.google.code.gwt.rest.client.QueryParam;
import com.google.code.gwt.rest.client.REST;
import com.google.code.gwt.rest.client.RestRequestClient;
import com.google.code.gwt.rest.client.RestRequestFactory;
import com.google.code.gwt.rest.client.URLMapping;
import com.google.code.gwt.rest.client.URLParam;
import com.google.code.gwt.rest.client.binding.JsonpPathBinding;
import com.google.code.gwt.rest.client.binding.RestPathBinding;
import com.google.code.gwt.rest.client.impl.AbstractRestRequestFactory;
import com.google.code.gwt.rest.client.util.SimpleTemplate;
import com.google.code.gwt.rest.client.util.TemplateBuilder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JParameterizedType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * Generator to create {@link RestRequestFactory}.
 * 
 * @author Takeshi Kondo
 */
public class RestRequestFactoryGenerator extends Generator {

	static public Type logLevel = Type.DEBUG;

	static final SimpleTemplate MAIN_CLASS = new TemplateBuilder() {
		{
			$();
			$("{converters}");
			$();
			$("{factoryMethods}");
			$();
		}
	}.parseToken();

	static final public SimpleTemplate CONVERTER = new TemplateBuilder() {
		{
			$("interface Converter_{methodName}_{impleMethodName}_{parameter}");
			$("              extends {FormParamConverter}<{parameterType}>\\{\\}");
		}
	}.parseToken();

	static final public SimpleTemplate FACTORY_METHOD = new TemplateBuilder() {
		{
			$("public {returnType} {methodName}()\\{");
			$("  {classImpl}");
			$("  return new {implClassName}();");
			$("\\}");
		}
	}.parseToken();

	static final public SimpleTemplate REQUEST_CLASS = new TemplateBuilder() {
		{
			$("class {implClassName} implements {returnType}\\{");
			$("  {methods}");
			$("\\}");
		}
	}.parseToken();

	static final public SimpleTemplate REST_REQUEST_CLASS_METHOD = new TemplateBuilder() {
		{
			$("   public {PathBinding} {impleMethodName}({parameters})\\{");
			$("      {PathBinding} binding = {REST}.{restMethod}(\"{url}\");");
			$("      binding.paths(getDefaultPathParams());");
			$("      binding.params(getDefaultParams());");
			$("      {bindingLogic} ");
			$("      return binding; ");
			$("   \\}");
		}
	}.parseToken();

	static final public SimpleTemplate JSONP_REQUEST_CLASS_METHOD = new TemplateBuilder() {
		{
			$("   public {JsonpPathBinding} {impleMethodName}({parameters})\\{");
			$("      {JsonpPathBinding} binding = {REST}.jsonp(\"{url}\");");
			$("      binding.paths(getDefaultPathParams());");
			$("      binding.params(getDefaultParams());");
			$("      {bindingLogic} ");
			$("      return binding; ");
			$("   \\}");
		}
	}.parseToken();

	static final public SimpleTemplate CALL_CONVERTER = new TemplateBuilder() {
		{
			$("   \\{");
			$("    Converter_{methodName}_{impleMethodName}_{parameter} converter");
			$("              = {GWT}.create(Converter_{methodName}_{impleMethodName}_{parameter}.class);");
			$("    binding.params(converter.toMap({parameter}));");
			$("   \\}");
		}
	}.parseToken();

	static final public SimpleTemplate CALL_PATH_BINDING = new TemplateBuilder() {
		{
			$("binding.path(\"{key}\",{parameter});");
		}
	}.parseToken();

	static final public SimpleTemplate CALL_QUERY_BINDING = new TemplateBuilder() {
		{
			$("binding.param(\"{key}\",{parameter});");
		}
	}.parseToken();

	static private Map<String, Object> createContextMethod() {
		Map<String, Object> contextMap = new HashMap<String, Object>();
		contextMap.put("FormParamConverter", BeanConverter.class.getName());

		contextMap.put("PathBinding", RestPathBinding.class.getName());
		contextMap.put("REST", REST.class.getName());
		contextMap.put("GWT", GWT.class.getName());
		contextMap.put("JsonpPathBinding", JsonpPathBinding.class.getName());

		return contextMap;
	}

	private ClassSourceFileComposerFactory createClassSourceFileComposerFactory(
			String typeName, String packageName, String simpleSourceName) {
		ClassSourceFileComposerFactory factory = new ClassSourceFileComposerFactory(
				packageName, simpleSourceName);

		factory.setSuperclass(AbstractRestRequestFactory.class
				.getCanonicalName());
		factory.addImplementedInterface(typeName);
		return factory;
	}

	@Override
	public String generate(TreeLogger logger, GeneratorContext context,
			String typeName) throws UnableToCompleteException {
		TypeOracle oracle = context.getTypeOracle();
		JClassType type = oracle.findType(typeName);
		logger.log(logLevel, "create type " + type);

		String packageName = type.getPackage().getName();
		String simpleSourceName = type.getName().replace('.', '_') + "Impl";

		PrintWriter pw = context.tryCreate(logger, packageName,
				simpleSourceName);

		if (pw == null) {
			return packageName + "." + simpleSourceName;
		}

		ClassSourceFileComposerFactory factory = createClassSourceFileComposerFactory(
				typeName, packageName, simpleSourceName);

		SourceWriter sw = factory.createSourceWriter(context, pw);

		Map<String, Object> contextMap = createContextMethod();

		StringBuilder converters = new StringBuilder();
		StringBuilder factoryMethods = new StringBuilder();

		for (JMethod method : type.getMethods()) {

			if (method.getParameters().length != 0) {
				logger.log(Type.ERROR,
						" Factory Method must be zero paraemeter method. "
								+ method);
				return null;
			}

			JClassType requestClass = method.getReturnType().isInterface();

			String implClassName = requestClass.getSimpleSourceName() + "_Impl";
			contextMap.put("returnType", requestClass.getQualifiedSourceName());
			contextMap.put("methodName", method.getName());
			contextMap.put("implClassName", implClassName);
			StringBuilder methods = new StringBuilder();

			String baseUrl = resolveBaseUrl(requestClass);

			for (JMethod requestClassMethod : requestClass.getMethods()) {
				URLMapping mapping = requestClassMethod
						.getAnnotation(URLMapping.class);
				if (mapping == null) {
					logger.log(Type.ERROR, "method " + requestClassMethod
							+ " don't have " + URLMapping.class.getSimpleName()
							+ " annotation.");
					throw new IllegalArgumentException();
				}

				SimpleTemplate requestClassMethodTemplate = selectRequestMethodTemplate(requestClassMethod
						.getReturnType().isInterface());

				String url = baseUrl + mapping.value();
				String restMethod = mapping.method().name().toLowerCase();

				contextMap.put("url", url);
				contextMap.put("restMethod", restMethod);
				JParameterizedType jtype = requestClassMethod.getReturnType()
						.isParameterized();
				if (jtype != null) {
					contextMap.put("implReturnType",
							jtype.getQualifiedSourceName());
				} else {
					contextMap.put("implReturnType",
							JavaScriptObject.class.getName());
				}
				contextMap.put("beanType",
						requestClassMethod.getReturnType().isParameterized()
								.getTypeArgs()[0].getQualifiedSourceName());
				contextMap.put("impleMethodName", requestClassMethod.getName());

				String parameters = "";
				String bindingLogic = "";
				int i = 0;
				for (JParameter parameter : requestClassMethod.getParameters()) {
					if (i != 0) {
						parameters += ",";
					}
					parameters += parameter.getType().getQualifiedSourceName()
							+ " arg" + i;
					if (parameter.isAnnotationPresent(URLParam.class)) {

						contextMap
								.put("key",
										parameter.getAnnotation(URLParam.class)
												.value());
						contextMap.put("parameter", "arg" + i);
						bindingLogic += CALL_PATH_BINDING.resolve(contextMap);

					} else if (parameter.isAnnotationPresent(QueryParam.class)) {
						contextMap.put("key",
								parameter.getAnnotation(QueryParam.class)
										.value());
						contextMap.put("parameter", "arg" + i);
						bindingLogic += CALL_QUERY_BINDING.resolve(contextMap);

					} else {
						contextMap.put("parameter", "arg" + i);
						contextMap.put("parameterType", parameter.getType()
								.getQualifiedSourceName());

						bindingLogic += CALL_CONVERTER.resolve(contextMap);
						converters.append(CONVERTER.resolve(contextMap));
					}
					i++;
				}

				contextMap.put("parameters", parameters);
				contextMap.put("bindingLogic", bindingLogic);
				methods.append(requestClassMethodTemplate.resolve(contextMap));
			}

			contextMap.put("methods", methods);
			String classImpl;
			if (method.getReturnType().getQualifiedSourceName()
					.equals(RestRequestClient.class.getName())) {

			}

			classImpl = REQUEST_CLASS.resolve(contextMap);

			contextMap.put("classImpl", classImpl);
			factoryMethods.append(FACTORY_METHOD.resolve(contextMap));
		}

		contextMap.put("factoryMethods", factoryMethods.toString());
		contextMap.put("converters", converters.toString());

		logger.log(logLevel, "create class \n" + MAIN_CLASS.resolve(contextMap));
		sw.print(MAIN_CLASS.resolve(contextMap));

		sw.commit(logger);
		return packageName + "." + simpleSourceName;
	}

	private String resolveBaseUrl(JClassType requestClass) {
		String baseUrl = "";

		URLMapping mapping = requestClass.getAnnotation(URLMapping.class);
		if (mapping != null) {
			baseUrl = mapping.value();
		}
		return baseUrl;
	}

	private SimpleTemplate selectRequestMethodTemplate(JClassType requestClass) {
		if (requestClass.getQualifiedSourceName().equals(
				RestRequestClient.class.getName())) {
			return REST_REQUEST_CLASS_METHOD;
		} else {
			return JSONP_REQUEST_CLASS_METHOD;
		}
	}

}
