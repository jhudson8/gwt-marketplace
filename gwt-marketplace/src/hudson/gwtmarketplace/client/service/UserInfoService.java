/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.service;

import hudson.gwtmarketplace.client.model.UserInfo;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("user")
public interface UserInfoService extends RemoteService {

	public UserInfo login(String loginCallbackUri, String logoutCallbackUri);
	
	public String getLoginUrl(String loginCallbackUri);

	public void clearCache();
}
