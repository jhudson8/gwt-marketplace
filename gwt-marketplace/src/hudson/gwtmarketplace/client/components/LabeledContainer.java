/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.components;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusWidget;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

public class LabeledContainer<T extends Widget> extends Composite implements FocusHandler, BlurHandler {

	private HorizontalPanel panel;
	private InlineLabel formLabel;
	private T component;
	private String label;

	public LabeledContainer(String label, T component) {
		this.label = label;
		this.component = component;
		this.panel = new HorizontalPanel();
		this.panel.addStyleName("labeled-field");
		this.formLabel = new InlineLabel(label);
		this.formLabel.addStyleName("field-label");
		this.panel.add(this.formLabel);
		this.panel.add(this.component);
		this.component = component;
		if (component instanceof FocusWidget && !(component instanceof Anchor)) {
			((FocusWidget) this.component).addFocusHandler(( this));
			((FocusWidget) this.component).addBlurHandler(this);
		}
		String componentId = DOM.createUniqueId();
		this.component.getElement().setAttribute("id", componentId);
		this.formLabel.getElement().setAttribute("for", componentId);

		initWidget(this.panel);
	}

	@Override
	public void addStyleName(String style) {
		getComponent().addStyleName(style);
	}
	
	@Override
	public void setStyleName(String style) {
		getComponent().addStyleName(style);
	}

	public void onFocus(FocusEvent event) {
		this.panel.addStyleName("field-focus");
	}

	public void onBlur(BlurEvent event) {
		this.panel.removeStyleName("field-focus");
	}

	public T getComponent() {
		return component;
	}

	public void setLabel(String label) {
		formLabel.setText(label);
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
}