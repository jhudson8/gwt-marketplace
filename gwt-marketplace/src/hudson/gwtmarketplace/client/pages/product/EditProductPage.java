/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.pages.product;

import hudson.gwtmarketplace.client.commands.GetProductCategoriesCommand;
import hudson.gwtmarketplace.client.commands.SaveProductCommand;
import hudson.gwtmarketplace.client.components.LabeledContainer;
import hudson.gwtmarketplace.client.components.LabeledListBox;
import hudson.gwtmarketplace.client.components.LabeledTextBox;
import hudson.gwtmarketplace.client.components.RichTextToolbar;
import hudson.gwtmarketplace.client.components.ThreeByXTextBox;
import hudson.gwtmarketplace.client.model.Category;
import hudson.gwtmarketplace.client.model.License;
import hudson.gwtmarketplace.client.model.Pair;
import hudson.gwtmarketplace.client.model.Product;
import hudson.gwtmarketplace.client.model.Status;
import hudson.gwtmarketplace.client.service.ProductService;
import hudson.gwtmarketplace.client.service.ProductServiceAsync;
import hudson.gwtmarketplace.client.util.WidgetUtil;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.ImageElement;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.gwtpages.client.PageRequestSession;
import com.google.gwt.gwtpages.client.message.Message;
import com.google.gwt.gwtpages.client.message.Messages;
import com.google.gwt.gwtpages.client.message.PageRequestSessionWithMessage;
import com.google.gwt.gwtpages.client.page.AsyncPageCallback;
import com.google.gwt.gwtpages.client.page.impl.UiBoundPage;
import com.google.gwt.gwtpages.client.page.parameters.PageParameters;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.SimplePanel;

public class EditProductPage extends UiBoundPage<HorizontalPanel> implements
		ClickHandler, SubmitCompleteHandler {

	interface MyUiBinder extends UiBinder<HorizontalPanel, EditProductPage> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private static ProductServiceAsync productService = GWT
			.create(ProductService.class);

	Product product;

	HorizontalPanel containerPanel;
	@UiField
	ImageElement icon;
	@UiField
	FlowPanel descriptionToolbarContainer;
	@UiField
	RichTextArea description;
	@UiField
	ThreeByXTextBox tags;
	@UiField
	LabeledTextBox name;
	@UiField
	LabeledTextBox versionNumber;
	@UiField
	LabeledTextBox organization;
	@UiField
	LabeledListBox status;
	@UiField
	LabeledListBox category;
	@UiField
	LabeledListBox license;
	@UiField
	LabeledTextBox webpageUrl;
	@UiField
	LabeledTextBox wikiUrl;
	@UiField
	LabeledTextBox downloadUrl;
	@UiField
	LabeledTextBox showcaseUrl;
	@UiField
	LabeledTextBox issueTrackerUrl;
	@UiField
	LabeledTextBox forumUrl;
	@UiField
	LabeledTextBox newsfeedUrl;
	@UiField
	Button saveBtn;
	@UiField
	Button cancelBtn;
	@UiField
	SimplePanel uploaderContainer;

	@Override
	protected void onConstruct(HorizontalPanel view) {
		name.getComponent().setEnabled(false);
		this.containerPanel = view;
		descriptionToolbarContainer.add(new RichTextToolbar(description));
		WidgetUtil
				.load(status.getComponent(), Status.VALUES, "Choose a status");
		WidgetUtil.load(license.getComponent(), License.VALUES,
				"Choose a license");
		saveBtn.addClickHandler(this);
		cancelBtn.addClickHandler(this);
		new GetProductCategoriesCommand() {
			@Override
			public void onSuccess(ArrayList<Category> result) {
				WidgetUtil.load(category.getComponent(), result,
						"Select a category");
			}
		}.execute();
	}

	@Override
	public void onEnterPage(PageParameters parameters,
			PageRequestSession session, final AsyncPageCallback callback) {
		callback.waitForAsync();
		if (parameters.getParameters().length > 0) {
			productService.getForEditing(parameters.asString(0),
					new AsyncCallback<Pair<Product, String>>() {

						@Override
						public void onSuccess(Pair<Product, String> result) {
							show(result);
							callback.onSuccess();
						}

						@Override
						public void onFailure(Throwable caught) {
							Messages.get().error(caught.getMessage(), null);
						}
					});
		}
	}

	@Override
	public void onExitPage() {
		show(new Pair<Product, String>(new Product(), null));
	}

	public void show(final Pair<Product, String> productPair) {
		this.product = productPair.getEntity1();
		if (null != product.getDescription())
			description.setHTML(product.getDescription());
		else
			description.setText("");
		tags.setValues(product.getTags());
		name.setValue(product.getName());
		versionNumber.setValue(product.getVersionNumber());
		organization.setValue(product.getOrganizationName());
		webpageUrl.setValue(product.getWebsiteUrl());
		wikiUrl.setValue(product.getWikiUrl());
		downloadUrl.setValue(product.getDownloadUrl());
		showcaseUrl.setValue(product.getDemoUrl());
		forumUrl.setValue(product.getForumUrl());
		issueTrackerUrl.setValue(product.getIssueTrackerUrl());
		newsfeedUrl.setValue(product.getNewsUrl());
		WidgetUtil.selectValue(license.getComponent(), product.getLicense());
		WidgetUtil.selectValue(status.getComponent(), product.getStatus());
		WidgetUtil
				.selectValue(category.getComponent(), product.getCategoryId());
		new GetProductCategoriesCommand() {

			@Override
			public void onSuccess(ArrayList<Category> result) {
				WidgetUtil.selectValue(category.getComponent(),
						product.getCategoryId());
			}
		}.execute();
		resetIcon();
		uploaderContainer.clear();
		if (null != product && null != product.getId()) {
			uploaderContainer.add(new ProductImageUploadPanel(product, this));
		}
	}

	@Override
	public void onClick(ClickEvent event) {
		if (event.getSource().equals(saveBtn)) {
			onSave();
		} else if (event.getSource().equals(cancelBtn)) {
			onCancel();
		}
	}

	private boolean isNull(String s) {
		return (null == s || s.trim().length() == 0);
	}

	public void onSave() {
		ArrayList<Message> messages = new ArrayList<Message>();
		WidgetUtil.checkNull(new LabeledContainer[] { name, category, status,
				license, webpageUrl }, messages);
		if (isNull(description.getText())) {
			messages.add(Message.error("Please enter the description",
					description));
		}
		if (messages.size() > 0) {
			Messages.get().setMessages(null, messages);
			return;
		}

		product.setDescription(description.getHTML());
		if (null == product.getId())
			product.setName(name.getComponent().getValue());
		List<String> _tags = tags.getValues();
		if (null != _tags && _tags.size() > 0) {
			product.setTags(_tags.toArray(new String[_tags.size()]));
		} else {
			product.setTags(null);
		}
		product.setOrganizationName(organization.getValue());
		product.setVersionNumber(versionNumber.getComponent().getValue());
		product.setStatus(status.getValue(status.getSelectedIndex()));
		product.setLicense(license.getValue(license.getSelectedIndex()));
		product.setCategoryId(WidgetUtil.getSelectedValue(category
				.getComponent()));
		product.setWebsiteUrl(webpageUrl.getValue());
		product.setDownloadUrl(downloadUrl.getValue());
		product.setWikiUrl(wikiUrl.getValue());
		product.setDemoUrl(showcaseUrl.getValue());
		product.setIssueTrackerUrl(issueTrackerUrl.getValue());
		product.setForumUrl(forumUrl.getValue());
		product.setNewsUrl(newsfeedUrl.getValue());
		new SaveProductCommand(product) {
			@Override
			public void onSuccess(Product result) {
				pages
						.goTo(result.getAlias(),
								new PageRequestSessionWithMessage(
										"The product details were saved."))
						.execute();
			}
		}.execute();
	}

	public void onCancel() {
		if (Window.confirm("Are you sure you want to cancel?")) {
			pages.goTo(product.getAlias()).execute();
		}
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	private void resetIcon() {
		if (null == product.getIconKey()) {
			icon.setSrc("images/noicon.gif");
			containerPanel.getWidget(0).setVisible(false);
		} else {
			containerPanel.getWidget(0).setVisible(true);
			icon.setSrc("gwt_marketplace/productImage?key=" + product.getId()
					+ "&ik=" + product.getIconKey());
			icon.getStyle().setDisplay(Display.BLOCK);
		}
	}

	@Override
	public void onSubmitComplete(SubmitCompleteEvent event) {
		String key = event.getResults();
		if (null != key && key.length() > 0) {
			product.setIconKey(key);
			resetIcon();
		}
		uploaderContainer.clear();
		uploaderContainer.add(new ProductImageUploadPanel(product, this));
	}
}