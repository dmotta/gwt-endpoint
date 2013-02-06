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

import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;

/**
 * RestRequestCallBack has REST request callback methods.
 * 
 * @author Takeshi Kondo
 */
public abstract class RestRequestCallBack<R> implements RequestCallback {

	/**
	 * Call back method when http response isn't success or validation error or
	 * request error.
	 * 
	 * @param code
	 *            http response code.
	 * @param text
	 *            http response text.
	 */
	protected void onDefault(int code, String text) {
		GWT.log("status code=" + code + " text=" + text);
	}

	/**
	 * Call back method when http request are failed.
	 */
	@Override
	public void onError(Request request, Throwable exception) {
		GWT.log("RestRequestCallBack Error " + request, exception);
	}

	/**
	 * Call back method when http response are received.
	 * 
	 * @param code
	 *            http response code.
	 * @param text
	 *            http response text.
	 */
	abstract protected void onMessage(int status, String text);

	/**
	 * Call back method when http response are recieved.
	 */
	@Override
	public void onResponseReceived(Request request, Response response) {
		onMessage(response.getStatusCode(), response.getText());
	}

	/**
	 * Call back method when response is received with validation error.
	 * 
	 * @param text
	 */
	protected void onValidationError(String text) {
		GWT.log("validationError text=" + text);
	}

	/**
	 * Call back method before http request are send. We can customize
	 * {@link RequestBuilder} in this method.
	 * 
	 */
	public void preRequest(RequestBuilder builder) {
	}

	/**
	 * Get the status codes which are treated as success.
	 */
	protected Set<Integer> getSuccessCodes() {
		return REST.SUCCESS_CODE;
	}

	/**
	 * Get the status codes which are treated as validation error.
	 */
	protected Set<Integer> getValidationErrorCodes() {
		return REST.VALIDATION_ERROR_CODE;
	}

}
