/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages.product;

import hudson.gwtmarketplace.client.commands.GetProductDetailsCommand;
import hudson.gwtmarketplace.client.event.ProductCommentEvent;
import hudson.gwtmarketplace.client.event.ProductCommentEvent.ProductCommentHandler;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.ProductComment;

import com.google.gwt.dom.client.HeadingElement;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.gwtpages.client.PageRequestSession;
import com.google.gwt.gwtpages.client.Pages;
import com.google.gwt.gwtpages.client.page.AsyncPageCallback;
import com.google.gwt.gwtpages.client.page.impl.UiBoundPage;
import com.google.gwt.gwtpages.client.page.parameters.PageParameters;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TabPanel;

public class ViewProductPage extends UiBoundPage<FlowPanel> implements
		SelectionHandler<Integer>, ProductCommentHandler {

	private Product product;

	@UiField
	HeadingElement siteTitle;

	@UiField
	TabPanel tabs;

	ProductDetailsPanel productDetails;
	ProductCommentsPanel productComments;

	@Override
	protected void onConstruct(FlowPanel view) {
		tabs.add(this.productDetails = new ProductDetailsPanel() {
			protected void onRateIt() {
				tabs.selectTab(1);
			};
		}, "Details", false);
		tabs.add(this.productComments = new ProductCommentsPanel(), "Comments",
				false);
		tabs.addSelectionHandler(this);
		tabs.selectTab(0);
		Pages.get().getEventBus().addHandler(ProductCommentEvent.TYPE, this);
	}

	public void show(String alias, final AsyncPageCallback callback) {
		new GetProductDetailsCommand(alias) {
			@Override
			public void onSuccess(Product product) {
				show(product, callback);
			}
		}.execute();
	}

	public void show(Product product, AsyncPageCallback callback) {
		this.product = product;
		tabs.selectTab(0);
		if (null != product)
			siteTitle.setInnerText(product.getName());
		else
			siteTitle.setInnerText("Loading Details...");
		productDetails.show(product);
		resetCommentTabTitle();
		callback.onSuccess();
	}

	private void resetCommentTabTitle() {
		if (null == product || product.getNumComments() == 0)
			tabs.getTabBar().setTabText(1, "Comments");
		else
			tabs.getTabBar().setTabText(1,
					"Comments (" + product.getNumComments() + ")");
	}

	@Override
	public void onSelection(SelectionEvent<Integer> event) {
		if (event.getSource().equals(tabs)) {
			if (event.getSelectedItem().equals(1))
				productComments.show(product);
		}
	}

	@Override
	public void onProductCommentAdded(Product product, ProductComment comment) {
		if (null != product
				&& comment.getProductId().getId() == product.getId()
						.longValue()) {
			this.product = product;
			resetCommentTabTitle();
		}
	}

	@Override
	public void onEnterPage(PageParameters parameters,
			PageRequestSession session, AsyncPageCallback callback) {
		callback.waitForAsync();
		if (parameters.listSize() > 0)
			show(parameters.asString(0), callback);
		else if (!parameters.getHistoryToken().startsWith("_"))
			show(parameters.getHistoryToken(), callback);
	}
}