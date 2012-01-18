/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.commands;

import hudson.gwtmarketplace.client.service.ProductService;
import hudson.gwtmarketplace.client.service.ProductServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gwtpages.client.message.Messages;
import com.google.gwt.user.client.rpc.AsyncCallback;

public abstract class AbstractAsyncCommand<T> implements AsyncCommand<T> {

	public AbstractAsyncCommand () {
		preExecute();
	}

	@Override
	public void preExecute() {
	}

	@Override
	public void onFailure(Throwable e) {
		if (null == e.getMessage())
			Messages.get().error("An error occured while processing your request");
		else
			Messages.get().error("An error occured while processing your request: " + e.getMessage());
	}

	private static ProductServiceAsync productService;
	protected static ProductServiceAsync productService() {
		if (null == productService) {
			productService = GWT.create(ProductService.class);
		}
		return productService;
	}

	public class AsyncCommandCallback implements AsyncCallback<T> {
		public void onSuccess(T result) {
			AbstractAsyncCommand.this.onSuccess(result);
		};
		@Override
		public void onFailure(Throwable caught) {
			AbstractAsyncCommand.this.onFailure(caught);
		}
	}
}