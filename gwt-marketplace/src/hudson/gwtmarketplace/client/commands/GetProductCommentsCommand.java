/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.commands;

import hudson.gwtmarketplace.client.model.ProductComment;
import hudson.gwtmarketplace.client.model.search.SearchResults;

public abstract class GetProductCommentsCommand extends
		AbstractAsyncCommand<SearchResults<ProductComment>> {

	private Long productId;
	private int pageNumber;
	private int pageSize;

	public GetProductCommentsCommand(Long productId, int pageNumber,
			int pageSize) {
		this.productId = productId;
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}

	@Override
	public void execute() {
		productService().getComments(productId, pageNumber, pageSize,
				new AsyncCommandCallback());
	}
}
