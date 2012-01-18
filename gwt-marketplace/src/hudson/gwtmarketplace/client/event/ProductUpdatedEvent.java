/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.event;

import hudson.gwtmarketplace.client.model.Product;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ProductUpdatedEvent extends
		GwtEvent<ProductUpdatedEvent.ProductUpdateHandler> {

	public interface ProductUpdateHandler extends EventHandler {
		void onProductUpdated(Product product);
	}

	private Product product;

	public ProductUpdatedEvent(Product product) {
		this.product = product;
	}

	public static final GwtEvent.Type<ProductUpdatedEvent.ProductUpdateHandler> TYPE = new GwtEvent.Type<ProductUpdateHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ProductUpdateHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ProductUpdatedEvent.ProductUpdateHandler handler) {
		handler.onProductUpdated(product);
	}

}
