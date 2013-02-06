package com.google.code.gwt.rest;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.code.gwt.rest.client.util.SimpleTemplate;
import com.google.code.gwt.rest.client.util.TemplateParser;

public class TokenParserTest {

	@Test
	public void parserTest() {
		SimpleTemplate tokens = TemplateParser.parse("/test/{key}/value");
		System.out.println(tokens.getTokens());
		Assert.assertEquals(3, tokens.getTokens().size());
	}

	@Test
	public void resolveTest() {

		SimpleTemplate tokens = TemplateParser.parse("/test/{key}/value");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("key", 1);
		String resolve = tokens.resolve(map);
		Assert.assertEquals("/test/1/value", resolve);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parserErrorTest() {
		SimpleTemplate tokens = TemplateParser.parse("/test/{key}/{value");
		System.out.println(tokens.getTokens());
		Assert.assertEquals(3, tokens.getTokens().size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void resolveErrorTest() {
		SimpleTemplate tokens = TemplateParser.parse("/test/{key}/value");
		String resolve = tokens.resolve(new HashMap<String, Object>());
		Assert.assertEquals("/test/1/value", resolve);
	}
}
