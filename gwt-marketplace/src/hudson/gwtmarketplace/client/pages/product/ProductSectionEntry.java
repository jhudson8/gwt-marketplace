/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages.product;

import hudson.gwtmarketplace.client.model.Product;

import com.google.gwt.core.client.GWT;
import com.google.gwt.gwtpages.client.Pages;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class ProductSectionEntry extends Composite {

	public static final int TYPE_UPDATE_AGE = 1;
	public static final int TYPE_RATING = 2;
	public static final int TYPE_ACTIVE_VIEWS = 3;

	interface Binder extends UiBinder<HorizontalPanel, ProductSectionEntry> {
	}

	private static Binder uiBinder = GWT.create(Binder.class);

	private static final DateTimeFormat dateFormat = DateTimeFormat
			.getShortDateFormat();
	private static final NumberFormat numberFormat = NumberFormat
			.getFormat("0.00");

	@UiField
	Hyperlink productLink;
	@UiField
	SimplePanel custom;

	public ProductSectionEntry(Product product, int type, int index) {
		HorizontalPanel panel = uiBinder.createAndBindUi(this);
		if (index % 2 == 0)
			panel.addStyleName("feed feed-even");
		else
			panel.addStyleName("feed feed-odd");
		initWidget(panel);
		panel.setCellHorizontalAlignment(panel.getWidget(1),
				HorizontalPanel.ALIGN_RIGHT);
		productLink.setText(product.getName());
		productLink.setTargetHistoryToken(Pages.get().createHistoryToken(
				product.getAlias()));
		if (type == TYPE_UPDATE_AGE)
			custom.add(new Label(dateFormat.format(product.getUpdatedDate())));
		else if (type == TYPE_RATING)
			custom.add(new Label(numberFormat.format(product.getRating())
					+ " (" + product.getTotalRatings() + ")"));
		else if (type == TYPE_ACTIVE_VIEWS)
			custom.add(new Label(Integer.toString(product.getNumDailyViews())));
	}
}