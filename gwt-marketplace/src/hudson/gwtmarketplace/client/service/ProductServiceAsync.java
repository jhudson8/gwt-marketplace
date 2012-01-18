/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.service;

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

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ProductServiceAsync {

	public void getCategories(AsyncCallback<ArrayList<Category>> callback);

	public void getByAlias(String alias, AsyncCallback<Product> callback);

	public void getById(long id, AsyncCallback<Product> callback);

	public void getForEditing(String alias, AsyncCallback<Pair<Product, String>> callback);

	public void getForViewing(String alias, AsyncCallback<Pair<Product, Date>> callback);

	public void getTops(Date maxKnownDate, AsyncCallback<Top10Lists> callback);

	public void addRating(long productId, int rating, Long userId, AsyncCallback<Pair<Product, Date>> callback);

	public void addComment(long productId, ProductComment comment, AsyncCallback<Triple<ProductComment, Product, Date>> callback);

	public void update(Product product, AsyncCallback<Void> callback);

	public void save(Product product, AsyncCallback<Product> callback);

	public void getComments(long productId,
			int startIndex, int pageSize, AsyncCallback<SearchResults<ProductComment>> callback);

	public void search(
			HashMap<String, String> namedParameters,
			ArrayList<String> generalParameters, int startIndex, int limit,
			String ordering, boolean ascending, Integer knownRowCount, AsyncCallback<SearchResults<Product>> callback);
}