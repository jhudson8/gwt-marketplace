/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.service;

import hudson.gwtmarketplace.client.exception.ExistingEntityException;
import hudson.gwtmarketplace.client.exception.InvalidAccessException;
import hudson.gwtmarketplace.client.model.Category;
import hudson.gwtmarketplace.client.model.Pair;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.ProductComment;
import hudson.gwtmarketplace.client.model.Top10Lists;
import hudson.gwtmarketplace.client.model.Triple;
import hudson.gwtmarketplace.client.model.search.SearchResults;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("product")
public interface ProductService extends RemoteService {

	public ArrayList<Category> getCategories();
	
	public Product getByAlias(String alias);

	public Product getById(long id);

	public Pair<Product, Date> getForViewing(String alias);

	public Pair<Product, String> getForEditing(String alias) throws InvalidAccessException;

	public Top10Lists getTops(Date maxKnownDate);

	public Pair<Product, Date> addRating(long productId, int rating, Long userId);

	public Triple<ProductComment, Product, Date> addComment(long productId, ProductComment comment);

	public void update(Product product) throws InvalidAccessException;

	public Product save(Product product)
			throws ExistingEntityException, InvalidAccessException;

	public SearchResults<ProductComment> getComments(long productId,
			int startIndex, int pageSize);

	public SearchResults<Product> search(
			HashMap<String, String> namedParameters,
			ArrayList<String> generalParameters, int startIndex, int limit,
			String ordering, boolean ascending, Integer knownRowCount);
}
