/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages.product;

import hudson.gwtmarketplace.client.commands.AddProductCommentCommand;
import hudson.gwtmarketplace.client.commands.GetProductCommentsCommand;
import hudson.gwtmarketplace.client.components.ProductRating;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.ProductComment;
import hudson.gwtmarketplace.client.model.Triple;
import hudson.gwtmarketplace.client.model.search.SearchResults;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ProductCommentsPanel extends Composite implements ClickHandler {

	interface MyUiBinder extends UiBinder<VerticalPanel, ProductCommentsPanel> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private static DateTimeFormat dateFormat = DateTimeFormat
			.getMediumDateFormat();

	private Product product;

	@UiField
	FlowPanel ratingContainer;
	@UiField
	TextArea comment;
	@UiField
	FlowPanel commentsContainer;
	@UiField
	HorizontalPanel addCommentContainer;
	ProductRating rating;
	@UiField
	Button saveBtn;

	public ProductCommentsPanel() {
		initWidget(uiBinder.createAndBindUi(this));
		ratingContainer.add(this.rating = new ProductRating());
		saveBtn.addClickHandler(this);
	}

	public void show(Product product) {
		if (null == this.product || null == product
				|| !this.product.equals(product)) {
			this.product = product;
			refreshComments();
		}
	}

	private void refreshComments() {
		commentsContainer.clear();
		commentsContainer.add(new Label("Loading comments..."));
		new GetProductCommentsCommand(product.getId(), 0, 20) {

			@Override
			public void onSuccess(SearchResults<ProductComment> result) {
				commentsContainer.clear();
				for (ProductComment comment : result.getEntries()) {
					commentsContainer.add(new CommentPanel(comment));
				}
			}
		}.execute();
	}

	private void onAddComment() {
		ProductComment comment = new ProductComment();
		comment.setCommentText(this.comment.getValue());
		comment.setRating(rating.getRatingValue());
		new AddProductCommentCommand(product, comment) {
			public void onSuccess(Triple<ProductComment, Product, Date> result) {
				rating.setValue(0);
				ProductCommentsPanel.this.comment.setValue(null);
				if (null != result.getEntity1()) {
					commentsContainer.insert(
							new CommentPanel(result.getEntity1()), 0);
				}
			};
		}.execute();
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource().equals(saveBtn)) {
			onAddComment();
		}
	}
}