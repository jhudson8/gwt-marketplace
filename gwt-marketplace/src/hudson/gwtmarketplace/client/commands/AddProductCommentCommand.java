/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.commands;

import hudson.gwtmarketplace.client.event.ProductCommentEvent;
import hudson.gwtmarketplace.client.event.ProductUpdatedEvent;
import hudson.gwtmarketplace.client.event.TopsDateCheckEvent;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.ProductComment;
import hudson.gwtmarketplace.client.model.Triple;

import java.util.Date;

import com.google.gwt.gwtpages.client.Pages;

public abstract class AddProductCommentCommand extends
		AbstractAsyncCommand<Triple<ProductComment, Product, Date>> {

	private ProductComment comment;
	private Product product;

	public AddProductCommentCommand(Product product, ProductComment comment) {
		this.comment = comment;
		this.product = product;
	}

	public void execute() {
		if (null != comment && null != comment.getCommentText()
				&& comment.getCommentText().length() > 0) {
			productService().addComment(product.getId(), comment,
					new AsyncCommandCallback() {
						@Override
						public void onSuccess(
								Triple<ProductComment, Product, Date> result) {
							if (null != result.getEntity3())
								Pages
										.get()
										.getEventBus()
										.fireEvent(
												new TopsDateCheckEvent(result
														.getEntity3()));
							if (null != result.getEntity2()) {
								Pages
										.get()
										.getEventBus()
										.fireEvent(
												new ProductUpdatedEvent(result
														.getEntity2()));
							}
							Pages
									.get()
									.getEventBus()
									.fireEvent(
											new ProductCommentEvent(result
													.getEntity2(), result
													.getEntity1()));
							super.onSuccess(result);
						}
					});
		}
	};
}
