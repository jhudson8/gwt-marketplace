/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.model.search;

import java.io.Serializable;
import java.util.ArrayList;

public class SearchResults<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;

	private int totalRowCount;
	private ArrayList<T> entries;

	public SearchResults() {}

	public SearchResults(ArrayList<T> entries) {
		this.entries = entries;
		this.totalRowCount = entries.size();
	}
	
	public SearchResults(ArrayList<T> entries, int totalRowCount) {
		this.entries = entries;
		this.totalRowCount = totalRowCount;
	}

	public int getTotalRowCount() {
		return totalRowCount;
	}
	public void setTotalRowCount(int totalRowCount) {
		this.totalRowCount = totalRowCount;
	}
	public ArrayList<T> getEntries() {
		return entries;
	}
	public void setEntries(ArrayList<T> entries) {
		this.entries = entries;
	}
}
