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

import java.util.Map;

/**
 * The factory interface to create {@link RestRequestClient} and
 * {@link JsonpRequestClient}.
 * 
 * @author Takeshi Kondo
 */
public interface RestRequestFactory {

	/**
	 * Get default parameters.
	 */
	public Map<String, Object> getDefaultParams();

	/**
	 * Get default path parameters.
	 */
	public Map<String, Object> getDefaultPathParams();

	/**
	 * Set parameters which are used by all request instance created from this
	 * factory.
	 */
	public void setDefaultParam(Map<String, Object> param);

	/**
	 * Set path parameters which are used by all request instance create from
	 * this factory.
	 */
	public void setDefaultPathParam(Map<String, Object> params);

}
