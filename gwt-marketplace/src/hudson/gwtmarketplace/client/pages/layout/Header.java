/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages.layout;

import hudson.gwtmarketplace.client.PageLoader;
import hudson.gwtmarketplace.client.Session;
import hudson.gwtmarketplace.client.commands.LoginCommand;
import hudson.gwtmarketplace.client.model.UserInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.gwtpages.client.Pages;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;

public class Header extends Composite implements ClickHandler, KeyUpHandler {

	interface MyUiBinder extends UiBinder<FlowPanel, Header> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	Anchor loginLogout;
	@UiField
	TextBox searchBox;
	@UiField
	Image searchImg;
	Boolean isLoggedIn;

	public Header() {
		initWidget(uiBinder.createAndBindUi(this));
		loginLogout.setVisible(false);
		loginLogout.addClickHandler(this);
		searchImg.addClickHandler(this);
		searchImg.setUrl("images/search.png");
		searchBox.addKeyUpHandler(this);
		new LoginCommand() {

			@Override
			public void onSuccess(UserInfo result) {
				loginLogout.setVisible(true);
				isLoggedIn = result.isLoggedIn();
				if (result.isLoggedIn()) {
					loginLogout.setText("logout");
				} else {
					loginLogout.setText("login");
				}
			}
		}.execute();
	}

	private void onLoginLogout() {
		if (isLoggedIn) {
			Window.Location.assign(Session.get().getLogoutUrl());
		} else {
			Window.Location.assign(Session.get().getLoginUrl());
		}
	}

	private void onSearch() {
		Pages.get().goTo(PageLoader.PAGE_SEARCH, searchBox.getValue())
				.execute();
		searchBox.setValue(null);
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource().equals(loginLogout)) {
			onLoginLogout();
		} else if (event.getSource().equals(searchImg)) {
			onSearch();
		}
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		if (event.getSource().equals(searchBox)) {
			if (KeyCodes.KEY_ENTER == event.getNativeKeyCode()) {
				onSearch();
			}
		}
	}
}
