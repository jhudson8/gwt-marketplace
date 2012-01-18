/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages;

import hudson.gwtmarketplace.client.pages.layout.Header;
import hudson.gwtmarketplace.client.pages.layout.LeftNav;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gwtpages.client.Pages;
import com.google.gwt.gwtpages.client.message.ui.MessagePanel;
import com.google.gwt.gwtpages.client.page.ApplicationPresenter;
import com.google.gwt.gwtpages.client.page.LoadedPageContainer;
import com.google.gwt.gwtpages.client.page.parameters.PageParameters;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LayoutPage extends Composite implements ApplicationPresenter {

	interface Binder extends UiBinder<FlowPanel, LayoutPage> {
	}

	private static Binder uiBinder = GWT.create(Binder.class);
	
	@UiField
	SimplePanel headContainer;
	@UiField
	SimplePanel navContainer;
	@UiField
	SimplePanel bodyContainer;
	@UiField
	SimplePanel messagesContainer;
	
	public LayoutPage() {		
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void showPage(LoadedPageContainer pageContainer, PageParameters parameters,
			com.google.gwt.gwtpages.client.PageRequestSession session) {
		Widget widget = pageContainer.getPage().asWidget();
		Widget current = (bodyContainer.getWidget());
		if (null != current) bodyContainer.remove(current);
		bodyContainer.add(widget);
	}

	@Override
	public void clearCurrentPage() {
		bodyContainer.clear();
	}
	
	@Override
	public void init(Pages settings) {
		headContainer.add(new Header());
		navContainer.add(new LeftNav());
		messagesContainer.add(new MessagePanel());
	}

	@Override
	public Widget asWidget() {
		return this;
	}
}