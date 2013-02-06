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

import com.google.code.gwt.rest.rebind.BeanConverterGenerator;
import com.google.gwt.core.client.GWT;

/**
 * 
 * The generic converter from JavaBean to Map.
 * 
 * @author Takeshi Kondo
 */
public interface BeanConverter<T> {

	/**
	 * Convert from JavaBean to Map.
	 * 
	 * A converted Map has keys which are named by expression language value
	 * binding naming rule.
	 * 
	 */
	Map<String, Object> toMap(String prefix, T object);

	/**
	 * This method are generated from {@link BeanConverterGenerator}. To get a
	 * concrete instance , call {@link GWT#create(Class)}.<br/>
	 * 
	 * <p>
	 * <label>Example</label>
	 * 
	 * 
	 * <pre>
	 *  // Sample Bean
	 *  public class SampleBean{
	 *     String name;
	 *     // getters and setters
	 *     ...
	 *  }
	 *  
	 *  // Sample Converter.
	 *  public interface SampleBeanConverter extends BeanConverter<SampleBean>{}
	 *  
	 *  // Get a SampleBeanConverter instance.
	 *  SampleBeanConverter converter = GWT.create(SampleBeanConverter.class);
	 * 
	 * </pre>
	 * 
	 * </p>
	 */
	Map<String, Object> toMap(T object);

}
