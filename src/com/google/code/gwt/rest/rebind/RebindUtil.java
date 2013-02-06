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
package com.google.code.gwt.rest.rebind;

import com.google.gwt.core.ext.typeinfo.JMethod;

/**
 * @author Takeshi Kondo
 */
public class RebindUtil {

	static public boolean isGetter(JMethod method) {
		if (method.getParameters().length != 0) {
			return false;
		}
		if (method.getReturnType().getParameterizedQualifiedSourceName()
				.equals("boolean")
				|| method.getReturnType().getParameterizedQualifiedSourceName()
						.equals(Boolean.class.getName())) {
			if (method.getName().startsWith("is")
					&& method.getName().length() > 2) {
				return true;
			}
		}
		if (method.getName().startsWith("get") && method.getName().length() > 3) {
			return true;
		}
		return false;
	}

	static public boolean isReturnTypeJdkTypeOrPrimitiveType(JMethod method) {
		if (method.getReturnType().getParameterizedQualifiedSourceName()
				.startsWith("java")) {
			return true;
		}
		return method.getReturnType().isPrimitive() != null;
	}

	static public String toPropertyName(JMethod method) {
		String name = method.getName();
		String propertyName;
		if (name.startsWith("is")) {
			propertyName = name.substring(2);
		} else {
			propertyName = name.substring(3);
		}
		if (propertyName.length() == 1) {
			char c = Character.toLowerCase(propertyName.charAt(0));
			return new String(new char[] { c });
		}
		if (Character.isUpperCase(propertyName.charAt(1))) {
			return propertyName;
		}
		return Character.toLowerCase(propertyName.charAt(0))
				+ propertyName.substring(1);
	}
}
