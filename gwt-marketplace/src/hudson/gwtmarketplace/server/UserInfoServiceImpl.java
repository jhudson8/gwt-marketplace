/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.server;

import net.sf.jsr107cache.Cache;
import hudson.gwtmarketplace.client.model.UserInfo;
import hudson.gwtmarketplace.client.service.UserInfoService;
import hudson.gwtmarketplace.domain.manager.AbstractManager;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class UserInfoServiceImpl extends RemoteServiceServlet implements
		UserInfoService {

	public UserInfo login(String loginCallbackUri, String logoutCallbackUri) {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		UserInfo loginInfo = new UserInfo();

		if (user != null) {
			loginInfo.setLoggedIn(true);
			loginInfo.setEmailAddress(user.getEmail());
			loginInfo.setNickname(user.getNickname());
			loginInfo.setId(user.getUserId());
			loginInfo.setLogoutUrl(userService.createLogoutURL(logoutCallbackUri));
		} else {
			loginInfo.setLoggedIn(false);
			loginInfo.setLoginUrl(userService.createLoginURL(loginCallbackUri));
		}
		return loginInfo;
	}

	public String getLoginUrl(String loginCallbackUri) {
		UserService userService = UserServiceFactory.getUserService();
		return userService.createLoginURL(loginCallbackUri);
	}

	public void clearCache() {
		Cache cache = AbstractManager.getCache();
		cache.clear();
	}
}