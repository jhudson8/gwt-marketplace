/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.ajaxfeeds;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;

public class EntryDiv extends FlowPanel {

	public EntryDiv (Entry entry, int index) {
		StringBuilder title = new StringBuilder();
		String publishedDate = entry.getFormattedPublishedDate();
		if (null != publishedDate) {
			title.append('[').append(publishedDate).append("] ");
		}
		title.append(entry.getTitle());
		add(new Anchor(title.toString(), entry.getLink(), "_blank"));
		if (index % 2 == 0) addStyleName("feed feed-even");
		else addStyleName("feed feed-odd");
	}
}
