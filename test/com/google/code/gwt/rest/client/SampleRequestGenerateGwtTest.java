package com.google.code.gwt.rest.client;

import org.junit.Test;

import com.google.gwt.core.client.GWT;

public class SampleRequestGenerateGwtTest extends GwtTestBase {

	@Test
	public void test() {
		GWT.create(SampleRestRequestFactory.class);
	}

}
