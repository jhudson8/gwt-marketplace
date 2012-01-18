/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Top10Lists implements Serializable {
	private static final long serialVersionUID = 1L;

	private ArrayList<Long> highestRatedIds;
	private ArrayList<Long> recentlyUpdatedIds;
	private ArrayList<Long> mostViewedIds;
	private HashMap<Long, Product> productMap;
	private Date maxDate;

	public Top10Lists() {
	}

	public Top10Lists(ArrayList<Product> highestRated,
			ArrayList<Product> recentlyUpdated, ArrayList<Product> mostViewed) {
		productMap = new HashMap<Long, Product>();
		highestRatedIds = cache(highestRated);
		recentlyUpdatedIds = cache(recentlyUpdated);
		mostViewedIds = cache(mostViewed);
		if (null == maxDate)
			maxDate = new Date();
	}

	private ArrayList<Long> cache(ArrayList<Product> l) {
		ArrayList<Long> ids = new ArrayList<Long>();
		for (Product p : l) {
			Product _p = productMap.get(p.getId());
			if (null == _p
					|| p.getActivityDate().getTime() > _p.getActivityDate()
							.getTime()
					|| p.getNumDailyViews() > _p.getNumDailyViews()) {
				productMap.put(p.getId(), p);
			}

			ids.add(p.getId());
			if (null == maxDate
					|| maxDate.getTime() < p.getActivityDate().getTime()
					|| maxDate.getTime() < p.getUpdatedDate().getTime())
				if (p.getUpdatedDate().getTime() > p.getActivityDate()
						.getTime())
					maxDate = p.getUpdatedDate();
				else
					maxDate = p.getActivityDate();
		}
		return ids;
	}

	public ArrayList<Product> getHighestRated() {
		ArrayList<Product> rtn = new ArrayList<Product>(highestRatedIds.size());
		for (Long id : highestRatedIds)
			rtn.add(productMap.get(id));
		return rtn;
	}

	public ArrayList<Product> getRecentUpdates() {
		ArrayList<Product> rtn = new ArrayList<Product>(
				recentlyUpdatedIds.size());
		for (Long id : recentlyUpdatedIds)
			rtn.add(productMap.get(id));
		return rtn;
	}

	public ArrayList<Product> getMostViewed() {
		ArrayList<Product> rtn = new ArrayList<Product>(mostViewedIds.size());
		for (Long id : mostViewedIds)
			rtn.add(productMap.get(id));
		return rtn;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
}
