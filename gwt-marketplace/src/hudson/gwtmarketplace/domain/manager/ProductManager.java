/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.domain.manager;

import hudson.gwtmarketplace.client.exception.ExistingEntityException;
import hudson.gwtmarketplace.client.exception.InvalidAccessException;
import hudson.gwtmarketplace.client.model.Category;
import hudson.gwtmarketplace.client.model.Pair;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.ProductComment;
import hudson.gwtmarketplace.client.model.ProductRating;
import hudson.gwtmarketplace.client.model.Top10Lists;
import hudson.gwtmarketplace.client.model.Triple;
import hudson.gwtmarketplace.client.model.search.SearchResults;
import hudson.gwtmarketplace.server.model.ProductImage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.Query;

public class ProductManager extends AbstractManager {

	private static final String TOKEN_CATEGORIES = "categories";
	private static final String TOKEN_TOP10_MOST_VIEWED = "top10MostViewed";
	public static final String TOKEN_VIEWS_BY_IP = "viewsByIP";
	private static final String TOKEN_TOP10_HIGHEST_RATED = "top10HighestRates";
	private static final String TOKEN_TOP10_RECENT_UPDATED = "top10RecentUpdated";
	private static final String TOKEN_RATINGS_BY_IP = "ratingsByPi";

	private static final Logger log = Logger.getLogger(ProductManager.class
			.getName());

	private static final Comparator<Product> top10MostViewedComparator = new Comparator<Product>() {
		public int compare(Product obj1, Product obj2) {
			if (null == obj1)
				return -1;
			else if (null == obj2)
				return 1;
			else
				return -1
						* obj1.getNumDailyViews().compareTo(
								obj2.getNumDailyViews());
		};
	};
	private static final Comparator<Product> top10BestRatedComparator = new Comparator<Product>() {
		public int compare(Product obj1, Product obj2) {
			if (null == obj1 || null == obj1.getRating())
				return -1;
			else if (null == obj2 || null == obj2.getRating())
				return 1;
			else {
				if (obj1.getRating().equals(obj2.getRating())) {
					return (-1 * obj1.getTotalRatings().compareTo(
							obj2.getTotalRatings()));
				} else {
					return -1 * obj1.getRating().compareTo(obj2.getRating());
				}
			}
		};
	};
	private static final Comparator<Product> top10RecentUpdatesComparator = new Comparator<Product>() {
		public int compare(Product obj1, Product obj2) {
			if (null == obj1)
				return -1;
			else if (null == obj2)
				return 1;
			else
				return -1
						* (obj1.getUpdatedDate().compareTo(obj2
								.getUpdatedDate()));
		};
	};

	public void resetDailyViews() {
		List<Product> products = toList(noTx().query(Product.class));
		for (Product p : products) {
			p.setNumDailyViews(0);
		}
		noTx().put(products);
		getCache().clear();
	}

	public byte[] getImageData(long productId) {
		String cacheKey = "thumbs:" + Long.toString(productId);
		byte[] data = (byte[]) getCache().get(cacheKey);
		if (null == data) {
			ProductImage image = singleResult(noTx().query(ProductImage.class)
					.ancestor(new Key<Product>(Product.class, productId)));
			if (null != image) {
				data = image.getData().getBytes();
				getCache().put(cacheKey, data);
			}
		}
		return data;
	}

	public String setImageData(long productId, byte[] data)
			throws InvalidAccessException {
		Key<Product> productKey = new Key<Product>(Product.class, productId);
		Product product = getById(productId);
		if (null == product || null == data)
			return null;

		String iconKey = Long.toString(System.currentTimeMillis());
		product.setIconKey(iconKey);
		update(product);

		Iterator<ProductImage> images = AbstractManager.noTx()
				.query(ProductImage.class).ancestor(productKey).fetch()
				.iterator();
		while (images.hasNext()) {
			AbstractManager.noTx().delete(images.next());
		}
		ProductImage img = new ProductImage();
		img.setProductId(productKey);
		img.setData(new Blob(data));
		AbstractManager.noTx().put(img);

		String cacheKey = "thumbs:" + productId;
		AbstractManager.getCache().put(cacheKey, data);
		return iconKey;
	}

	public ArrayList<Category> getCategories() {
		ArrayList<Category> categories = (ArrayList<Category>) getCache().get(
				TOKEN_CATEGORIES);
		if (null == categories) {
			categories = toList(noTx().query(Category.class).order("name"));
			getCache().put(TOKEN_CATEGORIES, categories);
		}
		return categories;
	}

	public Top10Lists getTops(Date highestKnownDate) {
		Top10Lists rtn = new Top10Lists(getTop10BestRated(),
				getTop10RecentUpdates(), getTop10MostViewed());
		if (null == rtn.getMaxDate() || null == highestKnownDate
				|| rtn.getMaxDate().getTime() > highestKnownDate.getTime())
			return rtn;
		else
			return rtn;
	}

	public SearchResults<ProductComment> getComments(long productId,
			int pageNumber, int pageSize) {
		return toSearchResults(
				noTx().query(ProductComment.class)
						.ancestor(new Key<Product>(Product.class, productId))
						.order("-createdDate").limit(pageSize)
						.offset(pageNumber * pageSize), null);
	}

	public Product getByAlias(String alias) {
		String cacheKey = "productByAlias:" + alias;
		if (getCache().containsKey(cacheKey))
			return (Product) getCache().get(cacheKey);
		return getByAlias(alias, noTx());
	}

	Product getByAlias(String alias, Objectify ofy) {
		String cacheKey = "productByAlias:" + alias;
		Product product = singleResult(ofy.query(Product.class).filter("alias",
				alias));
		getCache().put(cacheKey, product);
		return product;
	}

	public Product getById(long id) {
		String cacheKey = "productByKey:" + id;
		if (getCache().containsKey(cacheKey))
			return (Product) getCache().get(cacheKey);
		return getById(id, noTx());
	}

	public Product getById(long id, Objectify ofy) {
		String cacheKey = "productByKey:" + id;
		Product product = ofy.find(new Key<Product>(Product.class, id));
		getCache().put(cacheKey, product);
		return product;
	}

	public Pair<Product, Date> getForViewing(String alias, String ipAddress) {
		Product product = getByAlias(alias);
		if (null == product)
			return null;
		String prodToken = alias + ":" + ipAddress;
		Map<String, Boolean> viewCache = (Map<String, Boolean>) getCache().get(
				TOKEN_VIEWS_BY_IP);
		if (null == viewCache) {
			viewCache = new HashMap<String, Boolean>();
			// we don't need to put it here because we will once we add the
			// entry
		}

		if (null == viewCache.get(prodToken)) {
			viewCache.put(prodToken, Boolean.TRUE);
			getCache().put(TOKEN_VIEWS_BY_IP, viewCache);
			if (null == product.getNumMonthlyViews())
				product.setNumMonthlyViews(0);
			product.setNumDailyViews(product.getNumDailyViews().intValue() + 1);
			product.setNumMonthlyViews(product.getNumMonthlyViews().intValue() + 1);

			try {
				boolean reordered = updateTop10MostViewed(product);
				product.setActivityDate(new Date());
				updateCache(product);
				noTx().put(product);

				return new Pair<Product, Date>(product,
						reordered ? product.getActivityDate()
								: toTopsDate(product));
			} catch (Exception e) {
				wrap(e);
				return null;
			}
		} else {
			return new Pair<Product, Date>(product, toTopsDate(product));
		}
	}

	public Pair<Product, String> getForEditing(String alias)
			throws InvalidAccessException {
		Product product = getByAlias(alias);
		if (null == product)
			return null;
		User user = UserServiceFactory.getUserService().getCurrentUser();
		if (null == user || !user.getUserId().equals(product.getUserId()))
			throw new InvalidAccessException();
		// String uploadKey =
		// BlobstoreServiceFactory.getBlobstoreService().createUploadUrl(
		// "/gwt_marketplace/uploadImage");
		// return new Pair<Product, String>(product, uploadKey);
		return new Pair<Product, String>(product, null);
	}

	public SearchResults<Product> search(
			HashMap<String, String> namedParameters,
			ArrayList<String> generalParameters, int startIndex, int limit,
			String ordering, boolean ascending, Integer knownRowCount) {

		Query<Product> query = noTx().query(Product.class);
		addOrdering(query, ordering, ascending, "name", true);
		if (null != namedParameters && namedParameters.size() > 0) {
			for (Map.Entry<String, String> param : namedParameters.entrySet()) {
				if (param.getKey().equals("category")) {
					query.filter("categoryId", param.getValue());
				} else if (param.getKey().equals("status")) {
					query.filter("status", param.getValue());
				} else if (param.getKey().equals("license")) {
					query.filter("license", param.getValue());
				} else if (param.getKey().equals("name")) {
					query.filter("name", param.getValue());
				} else if (param.getKey().equals("tag")) {
					query.filter("tags", param.getValue());
				}
			}
		}
		if (null != generalParameters && generalParameters.size() > 0) {
			query.filter("searchFields in", generalParameters);
		}
		return toSearchResults(query, knownRowCount);
	}

	public ArrayList<Product> getTop10MostViewed() {
		String cacheKey = TOKEN_TOP10_MOST_VIEWED;
		ArrayList<Product> products = (ArrayList<Product>) getCache().get(
				cacheKey);
		if (null == products) {
			products = toList(noTx().query(Product.class)
					.order("-numDailyViews").limit(10));
			// for some reason, the > 0 filter isn't working
			for (int i = products.size() - 1; i >= 0; i--) {
				if (products.get(i).getNumDailyViews() == 0)
					products.remove(i);
			}
			Collections.sort(products, top10MostViewedComparator);
			getCache().put(cacheKey, products);
		}
		return products;
	}

	public ArrayList<Product> getTop10BestRated() {
		ArrayList<Product> products = null;
		String cacheKey = TOKEN_TOP10_HIGHEST_RATED;
		if (getCache().containsKey(cacheKey)) {
			products = (ArrayList<Product>) getCache().get(cacheKey);
		} else {
			products = toList(noTx().query(Product.class)
					.filter("rating !=", null).order("-rating").limit(10));
			Collections.sort(products, top10BestRatedComparator);
			getCache().put(cacheKey, products);
		}
		return products;
	}

	public ArrayList<Product> getTop10RecentUpdates() {
		ArrayList<Product> products = null;
		String cacheKey = TOKEN_TOP10_RECENT_UPDATED;
		if (getCache().containsKey(cacheKey)) {
			products = (ArrayList<Product>) getCache().get(cacheKey);
		} else {
			products = toList(noTx().query(Product.class).order("-updatedDate")
					.limit(10));
			getCache().put(cacheKey, products);
		}
		return products;
	}

	public Pair<Product, Date> addRating(long productId, int rating, Long userId) {
		Objectify ofy = tx();
		try {
			Key<Product> productKey = new Key<Product>(Product.class, productId);
			Product product = ofy.find(productKey);
			product.setTotalRatings(product.getTotalRatings() + 1);
			product.setTotalRatingScore(product.getTotalRatingScore() + rating);
			product.setRating((float) ((float) product.getTotalRatingScore() / (float) product
					.getTotalRatings()));
			product.setUpdatedDate(new Date());
			product.setActivityDate(new Date());
			ofy.put(product);
			updateCache(product);
			ofy.getTxn().commit();
			return new Pair<Product, Date>(product, toTopsDate(product));
		} catch (Exception e) {
			ofy.getTxn().rollback();
			wrap(e);
			return null;
		}
	}

	public Triple<ProductComment, Product, Date> addComment(long productId,
			ProductComment comment, String ipAddress) {
		User user = UserServiceFactory.getUserService().getCurrentUser();
		Objectify ofy = tx();
		try {
			Key<Product> productKey = new Key<Product>(Product.class, productId);
			Product product = ofy.find(productKey);
			List<Serializable> thingsToSave = new ArrayList<Serializable>();
			boolean addComment = false;
			if (null != comment.getCommentText()
					&& comment.getCommentText().length() > 0) {
				if (null != user) {
					comment.setUserAlias(user.getNickname());
					comment.setUserId(user.getUserId());
				}
				comment.setCreatedDate(new Date());
				comment.setProductId(productKey);
				thingsToSave.add(comment);
				addComment = true;
			}

			// deal with the rating
			if (null != comment.getRating()
					&& comment.getRating().intValue() > 0) {
				String ratingCacheKey = TOKEN_RATINGS_BY_IP + ":" + productId;
				Map<String, Integer> ratingsCache = (Map<String, Integer>) getCache()
						.get(ratingCacheKey);
				if (null == ratingsCache) {
					ratingsCache = new HashMap<String, Integer>();
					ArrayList<ProductRating> ratings = toList(noTx().query(
							ProductRating.class).ancestor(productKey));
					for (ProductRating rating : ratings) {
						ratingsCache.put(rating.getIpAddress(),
								rating.getRating());
					}
					getCache().put(ratingCacheKey, ratingsCache);
				}
				if (null == ratingsCache.get(ipAddress)) {
					int rating = comment.getRating().intValue();
					if (null == product.getTotalRatings())
						product.setTotalRatings(1);
					else
						product.setTotalRatings(product.getTotalRatings() + 1);
					if (null == product.getTotalRatingScore())
						product.setTotalRatingScore(rating);
					else
						product.setTotalRatingScore(product
								.getTotalRatingScore() + rating);
					product.setRating((float) ((float) product
							.getTotalRatingScore() / (float) product
							.getTotalRatings()));
					product.setUpdatedDate(new Date());
					getCache().remove(TOKEN_TOP10_HIGHEST_RATED);

					ProductRating productRating = new ProductRating();
					productRating.setCreatedDate(new Date());
					productRating.setIpAddress(ipAddress);
					productRating.setProductId(productKey);
					productRating.setRating(rating);
					if (null != user) {
						productRating.setUserAlias(user.getNickname());
						productRating.setUserId(user.getUserId());
					}
					thingsToSave.add(productRating);
					ratingsCache.put(ipAddress, rating);
					getCache().put(ratingCacheKey, ratingsCache);
				} else {
					// we can't add the rating
					comment.setUnableToRate(Boolean.TRUE);
				}
			}

			if (thingsToSave.size() > 0) {
				if (addComment) {
					if (null == product.getNumComments())
						product.setNumComments(1);
					else
						product.setNumComments(product.getNumComments() + 1);
				}
				product.setActivityDate(new Date());
				thingsToSave.add(product);
				ofy.put((Iterable) thingsToSave);
				updateCache(product);
				ofy.getTxn().commit();
				return new Triple<ProductComment, Product, Date>(comment,
						product, toTopsDate(product));
			} else {
				return new Triple<ProductComment, Product, Date>(null, product,
						null);
			}
		} catch (Exception e) {
			ofy.getTxn().rollback();
			wrap(e);
			return null;
		}
	}

	public void update(Product product) throws InvalidAccessException {
		log.info("Updating product '" + product.getAlias() + "'");
		try {
			User user = UserServiceFactory.getUserService().getCurrentUser();
			Product orig = noTx().get(
					new Key<Product>(Product.class, product.getId()));
			if (null == user || !user.getUserId().equals(orig.getUserId()))
				throw new InvalidAccessException();
			if (!orig.getCategoryId().equals(product.getCategoryId())) {
				log.info("Category '" + product.getCategoryId()
						+ "' does not equal '" + orig.getCategoryId() + "'");
				Category category1 = singleResult(noTx().query(Category.class)
						.filter("alias", orig.getCategoryId()));
				if (null != category1) {
					if (null == category1.getNumProducts())
						category1.setNumProducts(0);
					else {
						category1.setNumProducts(category1.getNumProducts()
								.intValue() - 1);
					}
					log.info("Previous category products: '"
							+ category1.getNumProducts());
				}
				Category category2 = singleResult(noTx().query(Category.class)
						.filter("alias", product.getCategoryId()));
				if (null == category2.getNumProducts())
					category2.setNumProducts(1);
				else {
					category2.setNumProducts(category2.getNumProducts()
							.intValue() + 1);
				}
				log.info("New category products: '"
						+ category2.getNumProducts());
				List<Category> toUpdate = new ArrayList<Category>();
				toUpdate.add(category1);
				toUpdate.add(category2);
				noTx().put(toUpdate);
				getCache().remove(TOKEN_CATEGORIES);
				product.setCategoryName(category2.getName());
			}
		} catch (EntityNotFoundException e) {
			// this is a problem - we should be saving here
			throw new RuntimeException(e);
		}

		product.setUpdatedDate(new Date());
		updateProductSearchFields(product);
		noTx().put(product);
		updateCache(product);
		getCache().remove(TOKEN_TOP10_RECENT_UPDATED);
	}

	public Product save(Product product) throws ExistingEntityException,
			InvalidAccessException {
		User user = getCurrentUser();
		if (null == user)
			throw new InvalidAccessException();
		product.setUserId(user.getUserId());
		Date date = new Date();
		product.setUpdatedDate(date);
		product.setCreatedDate(date);
		product.setActivityDate(date);
		Integer zero = new Integer(0);
		product.setNumComments(zero);
		product.setNumDailyViews(zero);
		product.setNumMonthlyViews(zero);
		product.setTotalRatings(zero);
		product.setAlias(product.getName().replace(' ', '_').toLowerCase());
		while (product.getAlias().startsWith("_"))
			product.setAlias(product.getAlias().substring(1));
		Product another = getByAlias(product.getAlias());
		if (null != another) {
			throw new ExistingEntityException("alias");
		}
		Category category = singleResult(noTx().query(Category.class).filter(
				"alias", product.getCategoryId()));
		product.setCategoryName(category.getName());
		updateProductSearchFields(product);
		noTx().put(product);
		updateCache(product);
		category.setNumProducts(category.getNumProducts().intValue() + 1);
		noTx().put(category);
		getCache().remove(TOKEN_CATEGORIES);
		getCache().remove(TOKEN_TOP10_RECENT_UPDATED);
		return product;
	}

	private void updateProductSearchFields(Product product) {
		List<String> searchFields = new ArrayList<String>();
		for (String s : product.getName().split(" "))
			searchFields.add(s);
		if (null != product.getTags()) {
			for (String s : product.getTags())
				searchFields.add(s);
		}
		product.setSearchFields(searchFields.toArray(new String[searchFields
				.size()]));
	}

	private Date toTopsDate(Product product) {
		long maxTime = -1;
		ArrayList<Product> products = getTop10MostViewed();
		for (Product _product : products) {
			if (_product.getActivityDate().getTime() > maxTime)
				maxTime = _product.getActivityDate().getTime();
		}
		products = getTop10BestRated();
		for (Product _product : products) {
			if (_product.getActivityDate().getTime() > maxTime)
				maxTime = _product.getActivityDate().getTime();
		}
		products = getTop10RecentUpdates();
		for (Product _product : products) {
			if (_product.getActivityDate().getTime() > maxTime)
				maxTime = _product.getActivityDate().getTime();
		}

		if (maxTime == -1)
			return null;
		else
			return new Date(maxTime);
	}

	private void updateCache(Product product) {
		String cacheKey = "productByKey:" + product.getId();
		getCache().put(cacheKey, product);
		cacheKey = "productByAlias:" + product.getAlias();
		getCache().put(cacheKey, product);
	}

	private boolean updateTop10MostViewed(Product product) {
		int dailyViews = product.getNumDailyViews();
		List<Product> top10 = getTop10MostViewed();
		boolean needsReordering = false;
		if (top10.size() < 10)
			needsReordering = true;
		else if (top10.get(9).getNumDailyViews() < product.getNumDailyViews())
			needsReordering = true;
		synchronized (ProductManager.class) {
			if (needsReordering) {
				if (!top10.contains(product)) {
					if (top10.size() < 10)
						top10.add(product);
					else {
						top10.remove(9);
						top10.add(product);
					}
				} else {
					top10.remove(product);
					top10.add(product);
				}
				Collections.sort(top10, top10MostViewedComparator);
				String cacheKey = TOKEN_TOP10_MOST_VIEWED;
				getCache().put(cacheKey, top10);
				return true;
			} else {
				return false;
			}
		}
	}

	private void updateTop10HighestRated(Product product) {
		if (null == product.getRating())
			return;
		float rating = product.getRating();
		List<Product> top10 = getTop10BestRated();
		// see if we need to re-order
		boolean needsReordering = false;
		boolean entityFound = false;
		for (int i = 0; i < top10.size(); i++) {
			Product _p = top10.get(i);
			if (_p.equals(product)) {
				entityFound = true;
				if (i > 0) {
					_p = top10.get(i - 1);
					float _rating = _p.getRating();
					if (_rating < rating)
						needsReordering = true;
				}
				if (i < (top10.size() - 1)) {
					_p = top10.get(i + 1);
					float _rating = _p.getRating();
					if (_rating > rating)
						needsReordering = true;
				}
				break;
			}
		}
		if (!entityFound) {
			if (top10.size() < 10) {
				needsReordering = true;
			} else if (top10.get(9).getRating() < rating) {
				needsReordering = true;
			}
		}
		synchronized (ProductManager.class) {
			if (needsReordering) {
				if (!entityFound) {
					top10.add(product);
				}
				Collections.sort(top10, top10BestRatedComparator);
				String cacheKey = TOKEN_TOP10_HIGHEST_RATED;
				getCache().put(cacheKey, top10);
			}
		}
	}

	private void updateTop10RecentUpdated(Product product) {
		long date = product.getUpdatedDate().getTime();
		List<Product> top10 = getTop10RecentUpdates();
		// see if we need to re-order
		boolean needsReordering = false;
		boolean entityFound = false;
		for (int i = 0; i < top10.size(); i++) {
			Product _p = top10.get(i);
			if (_p.equals(product)) {
				entityFound = true;
				if (i > 0) {
					_p = top10.get(i - 1);
					long _date = _p.getUpdatedDate().getTime();
					if (_date < date)
						needsReordering = true;
				}
				if (i < (top10.size() - 1)) {
					_p = top10.get(i + 1);
					long _date = _p.getUpdatedDate().getTime();
					if (_date > date)
						needsReordering = true;
				}
				break;
			}
		}
		if (!entityFound) {
			if (top10.size() < 10) {
				needsReordering = true;
			} else if (top10.get(9).getUpdatedDate().getTime() < date) {
				needsReordering = true;
			}
		}
		synchronized (ProductManager.class) {
			if (needsReordering) {
				if (!entityFound) {
					top10.add(product);
				}
				Collections.sort(top10, top10RecentUpdatesComparator);
				String cacheKey = TOKEN_TOP10_RECENT_UPDATED;
				getCache().put(cacheKey, top10);
			}
		}
	}
}