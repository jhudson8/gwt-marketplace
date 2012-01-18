/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.commands;

import hudson.gwtmarketplace.client.event.CategoriesUpdatedEvent;
import hudson.gwtmarketplace.client.model.Category;
import hudson.gwtmarketplace.client.service.ProductService;
import hudson.gwtmarketplace.client.service.ProductServiceAsync;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gwtpages.client.Pages;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class GetProductCategoriesCommand extends
		AbstractAsyncCommand<ArrayList<Category>> {

	private static ProductServiceAsync service = GWT
			.create(ProductService.class);

	private static ArrayList<Category> categories;

	public static void refresh() {
		GetProductCategoriesCommand.categories = null;
		service.getCategories(new AsyncCallback<ArrayList<Category>>() {

			@Override
			public void onSuccess(ArrayList<Category> result) {
				GetProductCategoriesCommand.categories = result;
				Pages.get().getEventBus()
						.fireEvent(new CategoriesUpdatedEvent(result));
			}

			@Override
			public void onFailure(Throwable caught) {
			}
		});
	}

	public GetProductCategoriesCommand() {
	}

	@Override
	public void execute() {
		if (null != categories) {
			onSuccess(categories);
		} else {
			service.getCategories(new AsyncCallback<ArrayList<Category>>() {

				@Override
				public void onSuccess(ArrayList<Category> result) {
					GetProductCategoriesCommand.categories = result;
					GetProductCategoriesCommand.this.onSuccess(result);
				}

				@Override
				public void onFailure(Throwable caught) {
					GetProductCategoriesCommand.this.onFailure(caught);
				}
			});
		}
	}
}