/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.event;

import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.ProductComment;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;

public class ProductCommentEvent extends
		GwtEvent<ProductCommentEvent.ProductCommentHandler> {

	public interface ProductCommentHandler extends EventHandler {
		void onProductCommentAdded(Product product, ProductComment comment);
	}

	private ProductComment comment;
	private Product product;

	public ProductCommentEvent(Product product, ProductComment comment) {
		this.product = product;
		this.comment = comment;
	}

	public static final GwtEvent.Type<ProductCommentEvent.ProductCommentHandler> TYPE = new GwtEvent.Type<ProductCommentHandler>();

	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<ProductCommentHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ProductCommentEvent.ProductCommentHandler handler) {
		handler.onProductCommentAdded(product, comment);
	}

}
