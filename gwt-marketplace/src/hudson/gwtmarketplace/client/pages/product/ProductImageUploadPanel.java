package hudson.gwtmarketplace.client.pages.product;

import hudson.gwtmarketplace.client.model.Product;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.Hidden;

public class ProductImageUploadPanel extends Composite implements ChangeHandler {

	interface MyUiBinder extends UiBinder<FormPanel, ProductImageUploadPanel> {
	}

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	FormPanel formPanel;
	@UiField
	Hidden imageUploadKey;
	@UiField
	FileUpload fileUpload;

	public ProductImageUploadPanel(Product product, SubmitCompleteHandler handler) {
		formPanel = uiBinder.createAndBindUi(this);
		initWidget(formPanel);
		imageUploadKey.setValue(product.getId().toString());
		formPanel.addSubmitCompleteHandler(handler);
		fileUpload.addChangeHandler(this);
	}

	@Override
	public void onChange(ChangeEvent event) {
		if (null != fileUpload.getFilename() && fileUpload.getFilename().length() > 0) {
			setVisible(false);
			formPanel.submit();
		}
	}
}
