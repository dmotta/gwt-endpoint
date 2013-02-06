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

/**
 * @author Takeshi Kondo
 */
public abstract class StringCallBack extends RestRequestCallBack<String> {

	/**
	 * {@inheritDoc}
	 */
	protected void onMessage(int code, String text) {
		if (getSuccessCodes().contains(code)) {
			onSuccess(text);
			return;
		}
		if (getValidationErrorCodes().contains(code)) {
			onValidationError(text);
			return;
		}
		onDefault(code, text);
	}

	/**
	 * Call back method when http responce is received with success.
	 * 
	 * @param text
	 *            http response text
	 */
	abstract public void onSuccess(String text);
}
