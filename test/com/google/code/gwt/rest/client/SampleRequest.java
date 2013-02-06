package com.google.code.gwt.rest.client;

import com.google.code.gwt.rest.client.URLMapping.METHOD;

@URLMapping("/sample")
public interface SampleRequest {

	@URLMapping("/{key}")
	public RestRequestClient<SampleJson> find(@URLParam("id") Long id);

	@URLMapping(method = METHOD.POST, value = "/new")
	public RestRequestClient<Long> create(SampleJson json);

	@URLMapping(method = METHOD.DELETE, value = "/{key}")
	public RestRequestClient<Void> delete(@URLParam("id") Long id);

	@URLMapping(method = METHOD.PUT, value = "/{key}")
	public RestRequestClient<String> update(SampleJson json);

	@URLMapping("/{key}")
	public JsonpRequestClient<SampleJson> find2(@URLParam("id") Long id);

}
