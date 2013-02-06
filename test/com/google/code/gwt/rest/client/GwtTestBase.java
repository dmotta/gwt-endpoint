package com.google.code.gwt.rest.client;

import com.google.gwt.junit.client.GWTTestCase;

public abstract class GwtTestBase extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.google.code.gwt.rest.REST_TEST";
	}

}
