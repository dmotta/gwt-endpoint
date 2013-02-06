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

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.jsonp.client.JsonpRequest;

/**
 * JSONP Request Client.
 * 
 * @author Takeshi Kondo
 */
public interface JsonpRequestClient<T extends JavaScriptObject> {

	/**
	 * Send JSONP request with response callback which receives HTTP response
	 * text as JavaScriptObject.
	 */
	JsonpRequest<T> request(JsonpRequestCallback<T> callback);

	/**
	 * Send JSONP request with response callback which receives HTTP response
	 * text as Boolean.
	 */
	JsonpRequest<Boolean> requestBoolean(JsonpRequestCallback<Boolean> callback);

	/**
	 * Send JSONP request with response callback which receives HTTP response
	 * text as Double.
	 */
	JsonpRequest<Double> requestDouble(JsonpRequestCallback<Double> callback);

	/**
	 * Send JSONP request with response callback which receives HTTP response
	 * text as Integer.
	 */
	JsonpRequest<Integer> requestInteger(JsonpRequestCallback<Integer> callback);

	/**
	 * Send JSONP request with response callback which receives HTTP response
	 * text as String.
	 */
	JsonpRequest<String> requestString(JsonpRequestCallback<String> callback);

	/**
	 * Send JSONP request with no response callback.
	 */
	void send();

	/**
	 * Send JSONP request with response callback which ignores HTTP response
	 * text.
	 */
	JsonpRequest<Void> send(JsonpRequestCallback<Void> callback);

}
