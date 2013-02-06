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
package com.google.code.gwt.rest.client;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.code.gwt.rest.client.binding.JsonpPathBinding;
import com.google.code.gwt.rest.client.binding.RestPathBinding;
import com.google.code.gwt.rest.client.impl.JsonpClientBuilder;
import com.google.code.gwt.rest.client.impl.RestClientBuilder;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.http.client.Response;

/**
 * The facade to call gwt-rest-client API.
 * 
 * @author Takeshi Kondo
 */
public class REST {

	/**
	 * The status codes are treated as response success.
	 */
	static public final Set<Integer> SUCCESS_CODE = new HashSet<Integer>(
			Arrays.asList(Response.SC_ACCEPTED, Response.SC_OK,
					Response.SC_CREATED));

	/**
	 * The status codes are treated as validation error.
	 */
	static public final Set<Integer> VALIDATION_ERROR_CODE = new HashSet<Integer>(
			Arrays.asList(Response.SC_BAD_REQUEST));

	/**
	 * Create Rest Request Client which send delete method.
	 */
	static public <T, R> RestPathBinding<T, R> delete(String uriTemplate) {
		return new RestClientBuilder<T, R>().createDeleteRequest(uriTemplate);
	}

	/**
	 * Create Rest Request Client which send get method.
	 */
	static public <T, R> RestPathBinding<T, R> get(String uriTemplate) {
		return new RestClientBuilder<T, R>().createGetRequest(uriTemplate);
	}

	/**
	 * Create Rest Request Client which send jsonp request.
	 */
	static public <T, R extends JavaScriptObject> JsonpPathBinding<T, R> jsonp(
			String uriTemplate) {
		return new JsonpClientBuilder<T, R>().createGetRequest(uriTemplate);
	}

	/**
	 * Transform JSON Text into JSON Object.
	 */
	public static native <T extends JavaScriptObject> T parseJson(String jsonStr) /*-{
		return eval('(' + jsonStr + ')');
	}-*/;

	/**
	 * Create Rest Request Client which send post method.
	 */
	static public <T, R> RestPathBinding<T, R> post(String uriTemplate) {
		return new RestClientBuilder<T, R>().createPostRequest(uriTemplate);
	}

	/**
	 * Create Rest Request Client which send put method.
	 */
	static public <T, R> RestPathBinding<T, R> put(String uriTemplate) {
		return new RestClientBuilder<T, R>().createPutRequest(uriTemplate);
	}

}
