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

/**
 * @author Takeshi Kondo
 */
public interface RestPathBinding<T, R> extends RestParamBinding<T, R> {

	RestPathBinding<T, R> path(String key, Object value);

	RestParamBinding<T, R> paths(Map<String, ?> values);

}
