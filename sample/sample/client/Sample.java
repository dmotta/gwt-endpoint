package sample.client;

import java.util.Date;

import com.google.code.gwt.rest.client.JsonpRequestCallback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class Sample implements EntryPoint {

	TwitterRequestFactory factory = GWT.create(TwitterRequestFactory.class);

	public void onModuleLoad() {
		TwitterRequest twitterRequest = factory.create();

		twitterRequest.timeline("dmotta", 20L).request(
				new JsonpRequestCallback<JsArray<TwitterTimeline>>() {
					@Override
					public void onSuccess(JsArray<TwitterTimeline> result) {
						RootPanel rootPanel = RootPanel.get("demo");
						rootPanel.clear();
						StringBuilder sb = new StringBuilder();
						sb.append("<p>   update time " + new Date() + "</p>");
						sb.append("<ul>");
						for (int i = 0; i < result.length(); i++) {
							sb.append("<li>" + result.get(i).getText()
									+ "</li>");
						}
						sb.append("</ul>");
						HTMLPanel panel = new HTMLPanel(sb.toString());
						rootPanel.add(panel);
					}
				});
	}
}
