/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.commands;

import hudson.gwtmarketplace.client.Session;
import hudson.gwtmarketplace.client.model.UserInfo;
import hudson.gwtmarketplace.client.service.UserInfoService;
import hudson.gwtmarketplace.client.service.UserInfoServiceAsync;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class LoginCommand extends
		AbstractAsyncCommand<UserInfo> {

	private static UserInfoServiceAsync service = GWT.create(UserInfoService.class);

	private String loginUrl;
	private String logoutUrl;

	private static boolean executing = false;
	private static List<LoginCommand> additionalCommands = new ArrayList<LoginCommand>();

	public LoginCommand() {
		loginUrl = Window.Location.getHref();
		logoutUrl = loginUrl;
		if (logoutUrl.indexOf('#') > 0)
			logoutUrl = logoutUrl.substring(0, logoutUrl.indexOf('#'));
	}
	
	public LoginCommand(String loginUrl, String logoutUrl) {
		this.loginUrl = loginUrl;
		this.logoutUrl = logoutUrl;
	}

	@Override
	public void execute() {
		if (executing) {
			additionalCommands.add(this);
		}
		else {
			executing = true;
			service.login(loginUrl, logoutUrl, new AsyncCallback<UserInfo>() {
				@Override
				public void onSuccess(UserInfo result) {
					executing = false;
					Session.get().setLoginUrl(result.getLoginUrl());
					Session.get().setLogoutUrl(result.getLogoutUrl());
					if (result.isLoggedIn()) {
						Session.get().setLoggedInUser(result);
					}
					for (LoginCommand cmd : additionalCommands)
						cmd.onSuccess(result);
					additionalCommands.clear();
					LoginCommand.this.onSuccess(result);
				}
				@Override
				public void onFailure(Throwable caught) {
					executing = false;
					for (LoginCommand cmd : additionalCommands)
						cmd.onFailure(caught);
					additionalCommands.clear();
					LoginCommand.this.onFailure(caught);
					
				}
			});
		}
	}
}