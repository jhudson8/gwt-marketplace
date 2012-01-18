/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.server;

import hudson.gwtmarketplace.client.exception.ExistingEntityException;
import hudson.gwtmarketplace.client.exception.InvalidAccessException;
import hudson.gwtmarketplace.client.model.Category;
import hudson.gwtmarketplace.client.model.Pair;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.ProductComment;
import hudson.gwtmarketplace.client.model.Top10Lists;
import hudson.gwtmarketplace.client.model.Triple;
import hudson.gwtmarketplace.client.model.search.SearchResults;
import hudson.gwtmarketplace.client.service.ProductService;
import hudson.gwtmarketplace.domain.manager.ProductManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class ProductServiceImpl extends RemoteServiceServlet implements
		ProductService {

	private ProductManager mgr = new ProductManager();

	@Override
	public Triple<ProductComment, Product, Date> addComment(long productId,
			ProductComment comment) {
		return mgr.addComment(productId, comment, getThreadLocalRequest().getRemoteAddr());
	}

	@Override
	public Pair<Product, Date> addRating(long productId, int rating, Long userId) {
		return mgr.addRating(productId, rating, userId);
	}

	@Override
	public ArrayList<Category> getCategories() {
		return mgr.getCategories();
	}

	@Override
	public Product getByAlias(String alias) {
		return mgr.getByAlias(alias);
	}

	@Override
	public Product getById(long id) {
		return mgr.getById(id);
	}

	@Override
	public Pair<Product, String> getForEditing(String alias) throws InvalidAccessException {
		return mgr
				.getForEditing(alias);
	}

	@Override
	public Pair<Product, Date> getForViewing(String alias) {
		return mgr
				.getForViewing(alias, getThreadLocalRequest().getRemoteAddr());
	}

	@Override
	public Top10Lists getTops(Date maxKnownDate) {
		return mgr.getTops(maxKnownDate);
	}

	@Override
	public Product save(Product product) throws ExistingEntityException,
			InvalidAccessException {
		return mgr.save(product);
	}

	@Override
	public void update(Product product) throws InvalidAccessException {
		mgr.update(product);
	}

	@Override
	public SearchResults<ProductComment> getComments(long productId,
			int startIndex, int pageSize) {
		return mgr.getComments(productId, startIndex, pageSize);
	}

	public SearchResults<Product> search(
			HashMap<String, String> namedParameters,
			ArrayList<String> generalParameters, int startIndex, int limit,
			String ordering, boolean ascending, Integer knownRowCount) {
		return mgr.search(namedParameters, generalParameters, startIndex,
				limit, ordering, ascending, knownRowCount);
	}
}