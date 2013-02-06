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
package com.google.code.gwt.rest.client.binding;

import java.util.Map;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author Takeshi Kondo
 */
public interface JsonpPathBinding<T, R extends JavaScriptObject> extends
		JsonpParamBinding<T, R> {

	JsonpPathBinding<T, R> path(String key, Object value);

	JsonpParamBinding<T, R> paths(Map<String, ?> values);

}
