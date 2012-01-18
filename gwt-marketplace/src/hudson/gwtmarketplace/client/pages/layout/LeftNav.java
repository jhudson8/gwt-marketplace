/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages.layout;

import hudson.gwtmarketplace.client.PageLoader;
import hudson.gwtmarketplace.client.commands.GetProductCategoriesCommand;
import hudson.gwtmarketplace.client.components.Section;
import hudson.gwtmarketplace.client.event.CategoriesUpdatedEvent;
import hudson.gwtmarketplace.client.event.CategoriesUpdatedEvent.CategoriesUpdateHandler;
import hudson.gwtmarketplace.client.model.Category;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.gwtpages.client.Pages;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Hyperlink;

public class LeftNav extends Composite implements CategoriesUpdateHandler {

	interface MyUiBinder extends UiBinder<FlowPanel, LeftNav> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	@UiField
	Section categories;

	public LeftNav() {
		initWidget(uiBinder.createAndBindUi(this));
		reloadCategories();
		Pages.get().getEventBus()
				.addHandler(CategoriesUpdatedEvent.TYPE, this);
	}

	private void reloadCategories() {
		new GetProductCategoriesCommand() {

			@Override
			public void onSuccess(ArrayList<Category> result) {
				reloadCategories(result);

			}
		}.execute();
	}

	private void reloadCategories(ArrayList<Category> categories) {
		LeftNav.this.categories.clear();
		for (Category c : categories) {
			FlowPanel fp = new FlowPanel();
			fp.getElement().getStyle().setPaddingTop(4, Unit.PX);
			String link = Pages.get().createHistoryToken(
					PageLoader.PAGE_SEARCH, "category:" + c.getAlias());
			fp.add(new Hyperlink(c.getName() + " (" + c.getNumProducts() + ")",
					link));
			LeftNav.this.categories.add(fp);
		}
	}

	@Override
	public void onCategoriesUpdated(ArrayList<Category> categories) {
		reloadCategories(categories);
	}
}