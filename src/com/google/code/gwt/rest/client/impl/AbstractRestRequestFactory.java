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

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.code.gwt.rest.client.RestRequestFactory;

/**
 * @author Takeshi Kondo
 */
public class AbstractRestRequestFactory implements RestRequestFactory {

	private Map<String, Object> defualtPathParam = new LinkedHashMap<String, Object>();
	private Map<String, Object> defualtParam = new LinkedHashMap<String, Object>();

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> getDefaultParams() {
		return defualtParam;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> getDefaultPathParams() {
		return defualtPathParam;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDefaultParam(Map<String, Object> param) {
		this.defualtParam.putAll(param);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setDefaultPathParam(Map<String, Object> paths) {
		this.defualtPathParam.putAll(paths);
	}

}
