/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages;

import hudson.gwtmarketplace.client.ajaxfeeds.EntryDiv;
import hudson.gwtmarketplace.client.ajaxfeeds.Feed;
import hudson.gwtmarketplace.client.ajaxfeeds.FeedListener;
import hudson.gwtmarketplace.client.commands.GetTopsCommand;
import hudson.gwtmarketplace.client.components.Section;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.Top10Lists;
import hudson.gwtmarketplace.client.pages.product.ProductSectionEntry;

import java.util.List;

import com.google.gwt.gwtpages.client.PageRequestSession;
import com.google.gwt.gwtpages.client.page.AsyncPageCallback;
import com.google.gwt.gwtpages.client.page.impl.UiBoundPage;
import com.google.gwt.gwtpages.client.page.parameters.PageParameters;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class MainPage extends UiBoundPage<FlowPanel> implements FeedListener {

	private Top10Lists top10Lists;
	private boolean blogfeedLoaded = false;

	@UiField
	Section newAndUpdated;
	@UiField
	Section topRated;
	@UiField
	Section mostActive;
	@UiField
	HorizontalPanel horizPanel;
	@UiField
	Section overview;
	@UiField
	Section blogFeed;

	@Override
	protected void onConstruct(FlowPanel view) {
		horizPanel.setCellWidth(horizPanel.getWidget(0), "33%");
		horizPanel.setCellWidth(horizPanel.getWidget(1), "34%");
		horizPanel.setCellWidth(horizPanel.getWidget(2), "33%");
		blogFeed.addStyleName("mainSectionContainer");
		overview.addStyleName("mainSectionContainer");
	}

	@Override
	public void onEnterPage(PageParameters parameters,
			PageRequestSession session, final AsyncPageCallback callback) {
		callback.waitForAsync();
		new GetTopsCommand() {
			@Override
			public void onSuccess(Top10Lists result) {
				callback.onSuccess();
				refresh(result);
			}
			
			public void onFailure(Throwable e) {
				super.onFailure(e);
				callback.onFailure(e);
			};
		}.execute();
		
		if (!blogfeedLoaded) {
			Feed f = new Feed();
			f.getFeed("http://feeds.feedburner.com/blogspot/NWLT?format=xml", this);
		}		
	}

	@Override
	public void onExitPage() {
	}

	public void refresh(Top10Lists top10Lists) {
		this.top10Lists = top10Lists;
		newAndUpdated.clear();
		topRated.clear();
		mostActive.clear();

		List<Product> products = top10Lists.getRecentUpdates();
		for (int i=0; i<products.size(); i++)
			newAndUpdated.add(new ProductSectionEntry(products.get(i), ProductSectionEntry.TYPE_UPDATE_AGE, i));
		products = top10Lists.getHighestRated();
		for (int i=0; i<products.size(); i++)
			topRated.add(new ProductSectionEntry(products.get(i), ProductSectionEntry.TYPE_RATING, i));
		products = top10Lists.getMostViewed();
		for (int i=0; i<products.size(); i++)
			mostActive.add(new ProductSectionEntry(products.get(i), ProductSectionEntry.TYPE_ACTIVE_VIEWS, i));
	}

	@Override
	public void onSuccess(Feed feed) {
		blogfeedLoaded = true;
		for (int i=0; i<feed.getEntries().size(); i++)
			blogFeed.add(new EntryDiv(feed.getEntries().get(i), i));
	}

	@Override
	public void onFailure() {
	}
}