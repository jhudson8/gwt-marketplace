package hudson.gwtmarketplace.client;

import hudson.gwtmarketplace.client.service.UserInfoService;
import hudson.gwtmarketplace.client.service.UserInfoServiceAsync;

import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gwtpages.client.GotoPageCommand;
import com.google.gwt.gwtpages.client.PageRequestSession;
import com.google.gwt.gwtpages.client.Pages;
import com.google.gwt.gwtpages.client.page.LoadedPageContainer;
import com.google.gwt.gwtpages.client.page.PageAttributes;
import com.google.gwt.gwtpages.client.page.event.PageRequestEventHandler;
import com.google.gwt.gwtpages.client.page.parameters.PageParameters;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AuthenticationPageEventHandler implements PageRequestEventHandler {

	private static UserInfoServiceAsync svc = null;

	@Override
	public void init(Pages pages) {
		svc = GWT.create(UserInfoService.class);
	}

	@Override
	public void onAfterPageEnter(LoadedPageContainer pageLoadResult,
			PageParameters parameters, GotoPageCommand command) {
	}

	@Override
	public void onPageEnterSuccess(LoadedPageContainer pageLoadResult,
			PageParameters parameters, GotoPageCommand command) {
	}

	@Override
	public void onPageRequest(String pageToken, String historyToken,
			PageRequestSession session) {
	}

	@Override
	public void onBeforePageEnter(LoadedPageContainer pageLoadResult,
			PageParameters parameters, GotoPageCommand command) {
		if (null != pageLoadResult.getAttributes()
				&& null != pageLoadResult.getAttributes().get(AuthenticationData.class)) {
			// this is a secure page
			if (null == Session.get().getLoggedInUser()) {
				StringBuilder callbackUri = new StringBuilder();
				callbackUri.append(Window.Location.getProtocol()).append("//")
						.append(Window.Location.getHost())
						.append(Window.Location.getPath());
				Map<String, List<String>> parameterMap = Window.Location
						.getParameterMap();
				if (parameterMap.size() > 0) {
					boolean started = false;
					callbackUri.append("?");
					for (Map.Entry<String, List<String>> entry : parameterMap
							.entrySet()) {
						for (String s : entry.getValue()) {
							if (started)
								callbackUri.append("&");
							else
								started = true;
							callbackUri.append(entry.getKey()).append("=")
									.append(s);
						}
					}
				}
				callbackUri.append("#").append(History.getToken());
				svc.getLoginUrl(callbackUri.toString(),
						new AsyncCallback<String>() {

							@Override
							public void onSuccess(String result) {
								Window.Location.assign(result);
							}

							@Override
							public void onFailure(Throwable caught) {
								Window.Location.assign(Session.get()
										.getLoginUrl());
							}
						});
				Pages.get().stopRequest();
			}
		}
	}

	@Override
	public void onPageLoaded(LoadedPageContainer result) {
	}

	@Override
	public void onPageWaitForAsync(LoadedPageContainer loadedPageContainer,
			PageParameters parameters, GotoPageCommand command) {
	}

	@Override
	public void onPageForward(LoadedPageContainer currentLoadedPageContainer,
			PageParameters currentParameters, GotoPageCommand currentCommand,
			GotoPageCommand forwardCommand) {
	}

	public static PageAttributes createPageAttributes() {
		return new PageAttributes().put(AuthenticationData.class,
				new AuthenticationData() {
				});
	}

	private interface AuthenticationData {
	}
}