package com.google.code.gwt.rest;

import org.junit.Assert;
import org.junit.Test;

import com.google.code.gwt.rest.client.REST;
import com.google.code.gwt.rest.client.impl.RestClientBuilder.RestRequestClientImpl;

public class RestTemplateTest {

	@Test
	public void testParser() {
		RestRequestClientImpl request = (RestRequestClientImpl) REST.get("/test/{key}")//
				.path("key", "sam")//
				.param("id", "sample");
		Assert.assertEquals("/test/sam?id=sample", request.buildUrl());
		Assert.assertEquals("id=sample", request.buildParam());
	}

}
