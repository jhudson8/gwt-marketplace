/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client;

import hudson.gwtmarketplace.client.model.UserInfo;

public class Session {

	private static final Session instance = new Session();

	public static final Session get() {
		return instance;
	}

	private Session() {
	}

	private UserInfo loggedInUser;
	private String loginUrl;
	private String logoutUrl;

	public UserInfo getLoggedInUser() {
		return loggedInUser;
	}

	public void setLoggedInUser(UserInfo loggedInUser) {
		this.loggedInUser = loggedInUser;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
}