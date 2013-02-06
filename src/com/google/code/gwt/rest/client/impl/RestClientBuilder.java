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
package com.google.code.gwt.rest.client.impl;

import java.util.Map;

import com.google.code.gwt.rest.client.RestRequestCallBack;
import com.google.code.gwt.rest.client.binding.RestParamBinding;
import com.google.code.gwt.rest.client.binding.RestPathBinding;
import com.google.code.gwt.rest.client.util.TemplateParser;
import com.google.code.gwt.rest.client.util.URLBuilder;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.RequestException;

/**
 * @author Takeshi Kondo
 */
public class RestClientBuilder<T, R> {

	public class RestRequestClientImpl implements RestPathBinding<T, R> {
		URLBuilder builder;

		public RestRequestClientImpl(String uriTemplate, Method method) {
			this.builder = new URLBuilder(method,
					TemplateParser.parse(uriTemplate));
		}

		public String buildParam() {
			return builder.buildParam();
		}

		public Object buildUrl() {
			return builder.buildUrl();
		}

		protected Request callRequest(RestRequestCallBack<R> callback,
				String uri) {
			Method method = builder.getMethod();
			RequestBuilder builder = new RequestBuilder(method, uri);
			builder.setHeader("Accept", "application/json;charset=utf-8;");
			try {
				if (method.equals(RequestBuilder.GET)) {
					builder.setCallback(callback);
					callback.preRequest(builder);
					return builder.send();
				} else {
					builder.setHeader("Content-Type",
							"application/x-www-form-urlencoded");
					callback.preRequest(builder);
					return builder.sendRequest(this.builder.buildParam(),
							callback);
				}
			} catch (RequestException e) {
				throw new IllegalArgumentException(e);
			}
		}

		@Override
		public RestParamBinding<T, R> param(String key, Object value) {
			builder.param(key, value);
			return this;
		}

		@Override
		public RestParamBinding<T, R> params(Map<String, ?> params) {
			builder.params(params);
			return this;
		}

		@Override
		public RestPathBinding<T, R> path(String key, Object value) {
			builder.path(key, value);
			return this;
		}

		@Override
		public RestPathBinding<T, R> paths(Map<String, ? extends Object> values) {
			builder.paths(values);
			return this;
		}

		@Override
		public Request send() {
			return send(new RestRequestCallBack<R>() {
				@Override
				protected void onMessage(int status, String text) {
					GWT.log("RestRequest success status=" + status + " text="
							+ text);
				}
			});
		}

		@Override
		public Request send(RestRequestCallBack<R> callback) {
			String uri = builder.buildUrl();
			return callRequest(callback, uri);
		}
	}

	public RestPathBinding<T, R> createDeleteRequest(String uriTemplate) {
		return new RestRequestClientImpl(uriTemplate, RequestBuilder.DELETE);
	}

	public RestPathBinding<T, R> createGetRequest(String uriTemplate) {
		return new RestRequestClientImpl(uriTemplate, RequestBuilder.GET);
	}

	public RestPathBinding<T, R> createPostRequest(String uriTemplate) {
		return new RestRequestClientImpl(uriTemplate, RequestBuilder.POST);
	}

	public RestPathBinding<T, R> createPutRequest(String uriTemplate) {
		return new RestRequestClientImpl(uriTemplate, RequestBuilder.PUT);
	}
}
