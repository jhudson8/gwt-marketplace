/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.ajaxfeeds;

public interface FeedListener {

	public void onSuccess(Feed feed);

	public void onFailure();
}
