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

import com.google.gwt.core.client.GWT;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * JSONP Request Client.
 * 
 * @author Takeshi Kondo
 */
public abstract class JsonpRequestCallback<T> implements AsyncCallback<T> {

	/**
	 * Callback method when http request is failed.
	 */
	@Override
	public void onFailure(Throwable caught) {
		GWT.log("JSONP Request Error ", caught);
	}

	/**
	 * Callback method when http response is received with success code.
	 */
	@Override
	abstract public void onSuccess(T result);

	/**
	 * Callback method before http request is send.
	 * 
	 * @param builder
	 */
	public void preRequest(JsonpRequestBuilder builder) {
	}

}
