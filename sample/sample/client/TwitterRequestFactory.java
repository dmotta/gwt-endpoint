package sample.client;

import com.google.code.gwt.rest.client.RestRequestFactory;

public interface TwitterRequestFactory extends RestRequestFactory {
	TwitterRequest create();
}
