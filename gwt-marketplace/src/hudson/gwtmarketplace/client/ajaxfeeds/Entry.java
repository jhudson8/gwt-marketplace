/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.ajaxfeeds;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;

public class Entry {
	
	// [Fri, 16 Jul 2010 12:45:00 -0700]
	private static final DateTimeFormat inFormat = DateTimeFormat.getFormat("EEE, dd MMM yyyy HH:mm:ss Z");
	private static final DateTimeFormat outFormat = DateTimeFormat.getShortDateTimeFormat();
	
	private String title;
	private String link;
	private String content;
	private String contentSnippet;
	private String publishedDate;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentSnippet() {
		return contentSnippet;
	}

	public void setContentSnippet(String contentSnippet) {
		this.contentSnippet = contentSnippet;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getPublishedDate() {
		return publishedDate;
	}

	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFormattedPublishedDate() {
		if (null != publishedDate) {
			try {
				Date d = inFormat.parse(publishedDate);
				return outFormat.format(d);
			}
			catch (Exception e) {
				
			}
		}
		return getPublishedDate();
	}
}