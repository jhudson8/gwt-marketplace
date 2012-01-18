/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.commands;

import hudson.gwtmarketplace.client.event.ProductUpdatedEvent;
import hudson.gwtmarketplace.client.model.Product;

import com.google.gwt.gwtpages.client.Pages;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class SaveProductCommand extends AbstractAsyncCommand<Product> {

	private Product product;

	public SaveProductCommand(Product product) {
		this.product = product;
	}

	@Override
	public void execute() {
		if (null == product.getId()) {
			productService().save(product, new AsyncCommandCallback() {
				@Override
				public void onSuccess(Product result) {
					Pages.get().getEventBus()
							.fireEvent(new ProductUpdatedEvent(result));
					super.onSuccess(result);
				}
			});
		} else {
			productService().update(product, new AsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					Pages.get().getEventBus()
							.fireEvent(new ProductUpdatedEvent(product));
					SaveProductCommand.this.onSuccess(product);
					GetProductCategoriesCommand.refresh();
				}

				@Override
				public void onFailure(Throwable caught) {
					SaveProductCommand.this.onFailure(caught);
				}
			});
		}
	}
}
