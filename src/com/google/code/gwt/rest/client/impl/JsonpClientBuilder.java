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

import com.google.code.gwt.rest.client.JsonpRequestCallback;
import com.google.code.gwt.rest.client.binding.JsonpParamBinding;
import com.google.code.gwt.rest.client.binding.JsonpPathBinding;
import com.google.code.gwt.rest.client.util.TemplateParser;
import com.google.code.gwt.rest.client.util.URLBuilder;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.jsonp.client.JsonpRequest;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;

/**
 * @author Takeshi Kondo
 */
public class JsonpClientBuilder<T, R extends JavaScriptObject> {

	public class JsonpRequestClientImpl implements JsonpPathBinding<T, R> {
		URLBuilder builder;

		public JsonpRequestClientImpl(String uriTemplate) {
			this.builder = new URLBuilder(RequestBuilder.GET,
					TemplateParser.parse(uriTemplate));
		}

		@Override
		public JsonpPathBinding<T, R> param(String key, Object value) {
			builder.param(key, value);
			return this;
		}

		@Override
		public JsonpPathBinding<T, R> params(Map<String, ?> params) {
			builder.params(params);
			return this;
		}

		@Override
		public JsonpPathBinding<T, R> path(String key, Object value) {
			builder.path(key, value);
			return this;
		}

		@Override
		public JsonpParamBinding<T, R> paths(Map<String, ?> values) {
			builder.paths(values);
			return this;
		}

		@Override
		public JsonpRequest<R> request(JsonpRequestCallback<R> callback) {
			String url = builder.buildUrl();
			JsonpRequestBuilder builder = new JsonpRequestBuilder();
			callback.preRequest(builder);
			return builder.requestObject(url, callback);
		}

		@Override
		public JsonpRequest<Boolean> requestBoolean(
				JsonpRequestCallback<Boolean> callback) {
			String url = builder.buildUrl();
			JsonpRequestBuilder builder = new JsonpRequestBuilder();
			callback.preRequest(builder);
			return builder.requestBoolean(url, callback);
		}

		@Override
		public JsonpRequest<Double> requestDouble(
				JsonpRequestCallback<Double> callback) {
			String url = builder.buildUrl();
			JsonpRequestBuilder builder = new JsonpRequestBuilder();
			callback.preRequest(builder);
			return builder.requestDouble(url, callback);
		}

		@Override
		public JsonpRequest<Integer> requestInteger(
				JsonpRequestCallback<Integer> callback) {
			String url = builder.buildUrl();
			JsonpRequestBuilder builder = new JsonpRequestBuilder();
			callback.preRequest(builder);
			return builder.requestInteger(url, callback);
		}

		@Override
		public JsonpRequest<String> requestString(
				JsonpRequestCallback<String> callback) {
			String url = builder.buildUrl();
			JsonpRequestBuilder builder = new JsonpRequestBuilder();
			callback.preRequest(builder);
			return builder.requestString(url, callback);
		}

		@Override
		public void send() {
			String url = builder.buildUrl();
			JsonpRequestBuilder builder = new JsonpRequestBuilder();
			builder.send(url);
		}

		@Override
		public JsonpRequest<Void> send(JsonpRequestCallback<Void> callback) {
			String url = builder.buildUrl();
			JsonpRequestBuilder builder = new JsonpRequestBuilder();
			callback.preRequest(builder);
			return builder.send(url, callback);
		}
	}

	public JsonpPathBinding<T, R> createGetRequest(String uriTemplate) {
		return new JsonpRequestClientImpl(uriTemplate);
	}
}
