/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.domain.manager;

import hudson.gwtmarketplace.client.model.Category;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.ProductComment;
import hudson.gwtmarketplace.client.model.ProductRating;
import hudson.gwtmarketplace.client.model.search.SearchResults;
import hudson.gwtmarketplace.server.model.ProductImage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.sf.jsr107cache.Cache;
import net.sf.jsr107cache.CacheException;
import net.sf.jsr107cache.CacheFactory;
import net.sf.jsr107cache.CacheManager;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

public abstract class AbstractManager {

	private static Cache cache;

	static {
		ObjectifyService.register(Product.class);
		ObjectifyService.register(ProductComment.class);
		ObjectifyService.register(ProductRating.class);
		ObjectifyService.register(ProductImage.class);
		ObjectifyService.register(Category.class);

		try {
			CacheFactory cacheFactory = CacheManager.getInstance()
					.getCacheFactory();
			cache = cacheFactory.createCache(new HashMap());
		} catch (CacheException e) {
			e.printStackTrace();
		}
	}

	public User getCurrentUser() {
		return UserServiceFactory.getUserService().getCurrentUser();
	}

	public static Cache getCache() {
		return cache;
	}

	protected void wrap(Exception e) {
		if (e instanceof RuntimeException)
			throw (RuntimeException) e;
		else
			throw new RuntimeException(e);
	}

	public static Objectify noTx() {
		Objectify ofy = ObjectifyService.begin();
		return ofy;
	}

	protected Objectify tx() {
		Objectify ofy = ObjectifyService.beginTransaction();
		return ofy;
	}

	protected void commit(Objectify ofy) {
		ofy.getTxn().commit();
	}

	protected <T extends Serializable> T singleResult(Query<T> query) {
		Iterator<T> i = query.iterator();
		if (i.hasNext())
			return i.next();
		else
			return null;
	}

	protected <T extends Serializable> ArrayList<T> toList(Query<T> query) {
		Iterator<T> i = query.iterator();
		ArrayList<T> rtn = new ArrayList<T>();
		while (i.hasNext())
			rtn.add(i.next());
		return rtn;
	}

	protected <T extends Serializable> SearchResults<T> toSearchResults(
			Query<T> query, Integer knownCount) {
		int count = (knownCount != null) ? knownCount : query.countAll();
		Iterator<T> i = query.iterator();
		ArrayList<T> rtn = new ArrayList<T>();
		while (i.hasNext())
			rtn.add(i.next());
		return new SearchResults<T>(rtn, count);
	}

	protected <T> Query<T> addOrdering(Query<T> query, String ordering, boolean ascending, String defaultOrdering, boolean defaultAscending) {
		if (null == ordering) {
			if (null == defaultOrdering) return query;
			else {
				if (defaultAscending) query.order(defaultOrdering);
				else query.order("-"+defaultOrdering);
			}
		}
		else {
			if (ascending) query.order(ordering);
			else query.order("-"+ordering);
		}
		return query;
	}
}