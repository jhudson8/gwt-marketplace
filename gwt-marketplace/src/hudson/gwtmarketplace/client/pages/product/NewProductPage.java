/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages.product;

import hudson.gwtmarketplace.client.model.Pair;
import hudson.gwtmarketplace.client.model.Product;

import com.google.gwt.gwtpages.client.PageRequestSession;
import com.google.gwt.gwtpages.client.page.AsyncPageCallback;
import com.google.gwt.gwtpages.client.page.parameters.PageParameters;
import com.google.gwt.user.client.Window;

public class NewProductPage extends EditProductPage {

	protected void onConstruct(com.google.gwt.user.client.ui.HorizontalPanel view) {
		super.onConstruct(view);
		name.getComponent().setEnabled(true);
	};

	@Override
	public void onCancel() {
		if (Window.confirm("Are you sure you want to cancel?")) {
			pages.showStartPage(false);
		}
	}

	@Override
	public void onEnterPage(PageParameters parameters,
			PageRequestSession session, AsyncPageCallback callback) {
		show(new Pair<Product, String>(new Product(), null));
	}
}