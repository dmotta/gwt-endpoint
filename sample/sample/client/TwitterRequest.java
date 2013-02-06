package sample.client;

import com.google.code.gwt.rest.client.JsonpRequestClient;
import com.google.code.gwt.rest.client.QueryParam;
import com.google.code.gwt.rest.client.URLMapping;
import com.google.code.gwt.rest.client.URLParam;
import com.google.gwt.core.client.JsArray;

@URLMapping("http://twitter.com/")
public interface TwitterRequest {

	@URLMapping("statuses/user_timeline/{user}.json")
	JsonpRequestClient<JsArray<TwitterTimeline>> timeline(
			@URLParam("user") String path, @QueryParam("count") Long count);

}
