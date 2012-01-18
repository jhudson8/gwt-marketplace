/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages.product;

import hudson.gwtmarketplace.client.PageLoader;
import hudson.gwtmarketplace.client.Session;
import hudson.gwtmarketplace.client.ajaxfeeds.EntryDiv;
import hudson.gwtmarketplace.client.ajaxfeeds.Feed;
import hudson.gwtmarketplace.client.ajaxfeeds.FeedListener;
import hudson.gwtmarketplace.client.event.ProductUpdatedEvent;
import hudson.gwtmarketplace.client.event.ProductUpdatedEvent.ProductUpdateHandler;
import hudson.gwtmarketplace.client.model.License;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.Status;
import hudson.gwtmarketplace.client.model.UserInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.DivElement;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.dom.client.TableRowElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gwtpages.client.Pages;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProductDetailsPanel extends Composite implements FeedListener,
		ClickHandler, ProductUpdateHandler {

	interface MyUiBinder extends UiBinder<HorizontalPanel, ProductDetailsPanel> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private static DateTimeFormat dateFormat = DateTimeFormat
			.getMediumDateFormat();
	private static NumberFormat ratingFormat = NumberFormat.getFormat("0.00");

	private Product product;

	@UiField
	DivElement description;
	@UiField
	DivElement tags;
	@UiField
	VerticalPanel links;
	@UiField
	SpanElement versionNumber;
	@UiField
	SpanElement category;
	@UiField
	SpanElement organization;
	@UiField
	SpanElement createdDate;
	@UiField
	SpanElement lastUpdatedDate;
	@UiField
	SpanElement developmentStatus;
	@UiField
	SpanElement license;
	@UiField
	SpanElement views;
	@UiField
	ImageElement icon;
	@UiField
	FlowPanel newsfeedContainer;
	@UiField
	FlowPanel newsfeed;
	@UiField
	TableRowElement categoryRow;
	@UiField
	TableRowElement versionNumberRow;
	@UiField
	TableRowElement organizationRow;
	@UiField
	TableRowElement createdDateRow;
	@UiField
	TableRowElement lastUpdatedDateRow;
	@UiField
	TableRowElement developmentStatusRow;
	@UiField
	TableRowElement licenseRow;
	@UiField
	HTMLPanel additionalDetailsContainer;
	@UiField
	HTMLPanel ratingContainer;
	@UiField
	Anchor rateIt;

	public ProductDetailsPanel() {
		HorizontalPanel container = uiBinder.createAndBindUi(this);
		initWidget(container);
		container.setCellWidth(container.getWidget(0), "132px");
		container.getWidget(0).getElement().getStyle()
				.setPaddingRight(12, Unit.PX);
		rateIt.addClickHandler(this);
		Pages.get().getEventBus().addHandler(ProductUpdatedEvent.TYPE, this);
	}

	@Override
	public void onProductUpdated(Product product) {
		if (null != this.product && product.equals(this.product)) {
			show(product);
		}
	}

	public void show(Product product) {
		Product _previous = this.product;
		this.product = product;
		links.clear();
		if (null == product) {
			newsfeedContainer.setVisible(false);
			newsfeed.clear();
			description.setInnerText("");
			tags.setInnerHTML("");
			category.setInnerText("");
			versionNumber.setInnerText("");
			views.setInnerText("");
			organization.setInnerText("");
			createdDate.setInnerText("");
			lastUpdatedDate.setInnerText("");
			icon.getStyle().setDisplay(Display.NONE);
			ratingContainer.getElement().setInnerText("");
		} else {
			if (!isNull(product.getWebsiteUrl())) {
				links.add(new Anchor("Website", product.getWebsiteUrl(),
						"_blank"));
			}
			if (!isNull(product.getDemoUrl())) {
				links.add(new Anchor("Showcase", product.getDemoUrl(), "_blank"));
			}
			if (!isNull(product.getDownloadUrl())) {
				links.add(new Anchor("Download", product.getDownloadUrl(),
						"_blank"));
			}
			if (!isNull(product.getWikiUrl())) {
				links.add(new Anchor("Wiki", product.getWikiUrl(), "_blank"));
			}
			if (!isNull(product.getForumUrl())) {
				links.add(new Anchor("Forum", product.getForumUrl(), "_blank"));
			}
			if (!isNull(product.getIssueTrackerUrl())) {
				links.add(new Anchor("Issue Tracker", product
						.getIssueTrackerUrl(), "_blank"));
			}
			UserInfo userInfo = Session.get().getLoggedInUser();
			if (null != userInfo
					&& userInfo.getId().equals(product.getUserId())) {
				links.add(new HTML("<br/>"));
				links.add(new Hyperlink("Edit settings", Pages.get().createHistoryToken(
						PageLoader.PAGE_EDIT_PRODUCT,
								product.getAlias())));
			}
			description.setInnerHTML(product.getDescription());
			tags.setInnerHTML(createTagHtml(product.getTags()));
			category.setInnerText(product.getCategoryName());
			versionNumber.setInnerText(product.getVersionNumber());
			organization.setInnerText(product.getOrganizationName());
			createdDate
					.setInnerText(dateFormat.format(product.getCreatedDate()));
			lastUpdatedDate.setInnerText(dateFormat.format(product
					.getUpdatedDate()));
			if (null == product.getIconKey()) {
				icon.setSrc("images/noicon.gif");
				icon.getStyle().setDisplay(Display.NONE);
			} else {
				icon.setSrc("gwt_marketplace/productImage?key="
						+ product.getId() + "&ik=" + product.getIconKey());
				icon.getStyle().setDisplay(Display.BLOCK);
			}
			if (null != product.getRating()) {
				ratingContainer.getElement().setInnerText(
						ratingFormat.format(product.getRating()) + " of 5");
			} else {
				ratingContainer.getElement().setInnerText("");
			}
			developmentStatus.setInnerText(Status.getDisplayValue(product
					.getStatus()));
			license.setInnerHTML(License.getDisplayValue(product.getLicense()));
			views.setInnerText(product.getNumDailyViews() + " / "
					+ product.getNumMonthlyViews());

			if (null == _previous || !_previous.equals(product)) {
				newsfeed.clear();
				if (null != product.getNewsUrl()
						&& product.getNewsUrl().length() > 0) {
					newsfeedContainer.setVisible(true);
					Feed f = new Feed();
					f.getFeed(product.getNewsUrl(), this);
				} else {
					newsfeedContainer.setVisible(false);
				}
			}
		}
	}

	private boolean isNull(String s) {
		return (null == s || s.length() == 0);
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource().equals(rateIt)) {
			onRateIt();
		}
	}

	protected void onRateIt() {
		// FIXME add rate it code
	}

	public String createTagHtml(String[] tags) {
		if (null == tags || tags.length == 0)
			return "(none)";
		else {
			StringBuilder sb = new StringBuilder();
			for (String s : tags) {
				if (sb.length() > 0)
					sb.append(", ");
				sb.append("<a class=\"tag\" href=\"#")
						.append(PageLoader.PAGE_SEARCH).append("/tag:").append(s)
						.append("\">").append(s).append("</a>");
			}
			return sb.toString();
		}
	}

	@Override
	public void onSuccess(Feed feed) {
		for (int i = 0; i < feed.getEntries().size(); i++) {
			newsfeed.add(new EntryDiv(feed.getEntries().get(i), i));
		}
	}

	@Override
	public void onFailure() {
		Window.alert("Unable to load news feed");
	}
}