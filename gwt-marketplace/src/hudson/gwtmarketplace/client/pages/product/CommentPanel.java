/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages.product;

import hudson.gwtmarketplace.client.components.ProductRating;
import hudson.gwtmarketplace.client.model.ProductComment;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

public class CommentPanel extends Composite {

	interface MyUiBinder extends UiBinder<FlowPanel, CommentPanel> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private static DateTimeFormat dateFormat = DateTimeFormat.getMediumDateTimeFormat();

	@UiField
	Label comment;
	@UiField
	Label commentFooter;
	@UiField
	SimplePanel ratingContainer;
	@UiField
	SimplePanel dislosureContainer;

	public CommentPanel(ProductComment comment) {
		initWidget(uiBinder.createAndBindUi(this));
		this.comment.setText(comment.getCommentText());
		String footer = null;
		if (null != comment.getUserId()) {
			footer = "Posted by " + comment.getUserAlias() + " on " + dateFormat.format(comment.getCreatedDate());
		}
		else {
			footer = "Posted  " + dateFormat.format(comment.getCreatedDate());
		}
		commentFooter.setText(footer);

		if (comment.getRating() != null) {
			ProductRating rating = new ProductRating(comment.getRating(), true);
			ratingContainer.add(rating);
			if (null != comment.getUnableToRate() && comment.getUnableToRate()) {
				dislosureContainer.add(new HTML("(duplicate user rating - does not alter overall product rating)<br/><br/>"));
			}
			else {
				dislosureContainer.setVisible(false);
			}
		}
		else {
			ratingContainer.setVisible(false);
		}
	}
}
