/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.commands;

import hudson.gwtmarketplace.client.event.ProductUpdatedEvent;
import hudson.gwtmarketplace.client.event.TopsDateCheckEvent;
import hudson.gwtmarketplace.client.model.Pair;
import hudson.gwtmarketplace.client.model.Product;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gwt.gwtpages.client.Pages;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class GetProductDetailsCommand extends
		AbstractAsyncCommand<Product> {

	public static Map<String, Product> productAliasMap = new HashMap<String, Product>();
	public static Map<Long, Product> productIdMap = new HashMap<Long, Product>();

	static {
		Pages
				.get()
				.getEventBus()
				.addHandler(ProductUpdatedEvent.TYPE,
						new ProductUpdatedEvent.ProductUpdateHandler() {
							@Override
							public void onProductUpdated(Product product) {
								cache(product);
							}
						});
	}

	private Long productId;
	private String alias;
	private boolean forViewing = true;

	public GetProductDetailsCommand(long productId) {
		this.productId = productId;
	}

	public GetProductDetailsCommand(String alias) {
		this.alias = alias;
	}

	public GetProductDetailsCommand(String alias, boolean forViewing) {
		this.alias = alias;
		this.forViewing = forViewing;
	}

	@Override
	public void execute() {
		Product p = null;
		boolean successCalled = false;
		if (null != productId)
			p = productIdMap.get(productId);
		else if (null != alias)
			p = productAliasMap.get(alias);
		if (null != p) {
			successCalled = true;
			onSuccess(p);
		}

		if (forViewing) {
			final boolean _successCalled = successCalled;
			// we may need to update the view count so hit the server
			productService().getForViewing(alias,
					new AsyncCallback<Pair<Product, Date>>() {

						@Override
						public void onSuccess(Pair<Product, Date> result) {
							if (null != result && null != result.getEntity2())
								Pages.get()
										.getEventBus()
										.fireEvent(
												new TopsDateCheckEvent(result
														.getEntity2()));
							cache(result.getEntity1());
							if (!_successCalled)
								GetProductDetailsCommand.this.onSuccess(result.getEntity1());
						}

						@Override
						public void onFailure(Throwable caught) {
							if (!_successCalled)
								GetProductDetailsCommand.this.onFailure(caught);
						}
					});
		}
	}

	public static void cache(Product p) {
		if (null == p)
			return;
		productAliasMap.put(p.getAlias(), p);
		productIdMap.put(p.getId(), p);
	}
}