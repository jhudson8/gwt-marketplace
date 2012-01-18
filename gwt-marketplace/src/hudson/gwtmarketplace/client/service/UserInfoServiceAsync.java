/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.service;

import hudson.gwtmarketplace.client.model.UserInfo;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserInfoServiceAsync {

	public void login(String loginCallbackUri, String logoutCallbackUri, AsyncCallback<UserInfo> callback);
	
	public void getLoginUrl(String loginCallbackUri, AsyncCallback<String> callback);

	public void clearCache(AsyncCallback<Void> callback);
}