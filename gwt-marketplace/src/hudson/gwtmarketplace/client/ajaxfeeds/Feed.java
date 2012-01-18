/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.ajaxfeeds;

import java.util.ArrayList;
import java.util.List;

public class Feed {
	private String title;

	private String description;

	private String url;

	List<Entry> entries = new ArrayList<Entry>();

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Entry> getEntries() {
		return entries;
	}

	private FeedListener feedListener = null;

	public void activateListener() {
		feedListener.onSuccess(this);
	}

	public void activateError() {
		feedListener.onFailure();
	}

	public void addEntry(String in_title, String in_link, String in_content,
			String in_contentSnippet, String in_publishedDate) {
		Entry entry = new Entry();
		entry.setTitle(in_title);
		entry.setLink(in_link);
		entry.setContent(in_content);
		entry.setContentSnippet(in_contentSnippet);
		entry.setPublishedDate(in_publishedDate);
		entries.add(entry);
	}

	public native void getFeed(String url, FeedListener in_feedListener) /*-{
		var feed = new $wnd.google.feeds.Feed(url);
		this.@hudson.gwtmarketplace.client.ajaxfeeds.Feed::feedListener = in_feedListener;
		var thisss = this;
		feed.load(function(result) {
			if (!result.error) {
				var feed = result.feed;
				thisss.@hudson.gwtmarketplace.client.ajaxfeeds.Feed::setTitle(Ljava/lang/String;)(feed.title);
				thisss.@hudson.gwtmarketplace.client.ajaxfeeds.Feed::setUrl(Ljava/lang/String;)(feed.url);
				thisss.@hudson.gwtmarketplace.client.ajaxfeeds.Feed::setDescription(Ljava/lang/String;)(feed.description);
				for (i = 0; i < result.feed.entries.length; i++) {
					var entry = result.feed.entries[i];
					thisss.@hudson.gwtmarketplace.client.ajaxfeeds.Feed::addEntry(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)(entry.title, entry.link, entry.content, entry.contentSnippet, entry.publishedDate);
				}
				thisss.@hudson.gwtmarketplace.client.ajaxfeeds.Feed::activateListener()();
			}
			else {
				thisss.@hudson.gwtmarketplace.client.ajaxfeeds.Feed::activateError()();
			}
		});
	}-*/;
}
