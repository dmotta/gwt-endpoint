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
package com.google.code.gwt.rest.client.util;

import java.util.ArrayList;

import com.google.code.gwt.rest.client.util.token.ParameterToken;
import com.google.code.gwt.rest.client.util.token.StringToken;
import com.google.code.gwt.rest.client.util.token.Token;

/**
 * @author Takeshi Kondo
 */
public class TemplateParser {
	enum STATUS {
		IN_PARAM, OUT_PARAM;
	}

	static public SimpleTemplate parse(String template) {
		ArrayList<Token> tokens = new ArrayList<Token>();
		STATUS status = STATUS.OUT_PARAM;
		char[] value = template.toCharArray();
		int i = 0;
		StringBuilder temp = new StringBuilder();
		while (i < value.length) {
			// escape
			if (value[i] == '\\') {
				i++;
				if (i < value.length) {
					temp.append(value[i]);
					i++;
				}
				continue;
			}
			
			// replaced token
			switch (status) {
			case OUT_PARAM:
				if (value[i] == '{') {
					if (temp.length() > 0) {
						StringToken st = new StringToken(temp.toString());
						tokens.add(st);
						temp = new StringBuilder();
					}
					status = STATUS.IN_PARAM;
				} else {
					temp.append(value[i]);
				}
				break;
			case IN_PARAM:
				if (value[i] == '}') {
					ParameterToken st = new ParameterToken(temp.toString(),
							template);
					tokens.add(st);
					temp = new StringBuilder();
					status = STATUS.OUT_PARAM;
				} else {
					temp.append(value[i]);
				}
				break;
			}
			i++;
		}
		
		if (status == STATUS.IN_PARAM) {
			throw new IllegalArgumentException(template + " is illegal.");
		}
		
		if (temp.length() > 0) {
			StringToken st = new StringToken(temp.toString());
			tokens.add(st);
			temp = new StringBuilder();
		}
		return new SimpleTemplate(tokens);
	}
}
