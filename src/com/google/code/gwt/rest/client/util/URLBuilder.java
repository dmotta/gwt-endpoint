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
package com.google.code.gwt.rest.client.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;

/**
 * @author Takeshi Kondo
 */
public class URLBuilder {

	final Map<String, Object> paths = new HashMap<String, Object>();
	final Map<String, Object> params = new HashMap<String, Object>();

	private RequestBuilder.Method method;

	private SimpleTemplate template;

	public URLBuilder(Method method, SimpleTemplate template) {
		this.method = method;
		this.template = template;
	}

	public URLBuilder(SimpleTemplate template) {
		this(RequestBuilder.GET, template);
	}

	public String buildParam() {
		if (params.isEmpty()) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (Entry<String, Object> entry : params.entrySet()) {
			if (entry.getValue() == null) {
				continue;
			}
			sb.append(entry.getKey());
			sb.append("=");
			sb.append(entry.getValue());
			sb.append("&");
		}
		sb.delete(sb.length() - 1, sb.length());
		return sb.toString();
	}

	public String buildUrl() {
		StringBuilder sb = new StringBuilder();
		sb.append(template.resolve(paths));
		if (params.size() > 0) {
			if (method.equals(RequestBuilder.GET)) {
				if (sb.toString().contains("?")) {
					sb.append("&");
				} else {
					sb.append("?");
				}
				sb.append(buildParam());
			}
		}
		String uri = sb.toString();
		return uri;
	}

	public Method getMethod() {
		return method;
	}

	public void param(String key, Object value) {
		this.params.put(key, value);
	}

	public void params(Map<String, ?> params) {
		this.params.putAll(params);
	}

	public void path(String key, Object value) {
		this.paths.put(key, value);
	}

	public void paths(Map<String, ? extends Object> values) {
		this.paths.putAll(values);
	}

}
