/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.components;

import java.util.Iterator;

import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Section extends Composite implements HasWidgets {

	FlowPanel container;
	Label title;
	FlowPanel body;

	public Section() {
		this("");
	}

	public Section(String title) {
		container = new FlowPanel();
		container.addStyleName("section");
		container.add(this.title = new Label(title));
		this.title.addStyleName("section-title");
		body = new FlowPanel();
		body.addStyleName("section-body");
		container.add(body);
		initWidget(container);
	}
	
	public Section(String title, Widget... widgets) {
		container = new FlowPanel();
		container.addStyleName("section");
		container.add(this.title = new Label(title));
		this.title.addStyleName("section-title");
		body = new FlowPanel();
		body.addStyleName("section-body");
		container.add(body);
		if (null != widgets)
			for (Widget w : widgets)
				body.add(w);
		initWidget(container);
	}

	public void setTitle(String title) {
		this.title.setText(title);
	}

	public void add(Widget widget) {
		body.add(widget);
	}

	public boolean remove(Widget widget) {
		return body.remove(widget);
	}

	@Override
	public Iterator<Widget> iterator() {
		return body.iterator();
	}
	
	public void remove(int index) {
		body.remove(index);
	}

	public FlowPanel getBody() {
		return body;
	}
	
	public void clear() {
		body.clear();
	}

	public void setStretch() {
		container.getElement().getStyle().setPosition(Position.ABSOLUTE);
		title.getElement().getStyle().setPosition(Position.ABSOLUTE);
		title.getElement().getStyle().setRight(0, Unit.PX);
		title.getElement().getStyle().setLeft(0, Unit.PX);
		body.getElement().getStyle().setPosition(Position.ABSOLUTE);
		body.getElement().getStyle().setTop(24, Unit.PX);
		body.getElement().getStyle().setLeft(0, Unit.PX);
		body.getElement().getStyle().setBottom(0, Unit.PX);
		body.getElement().getStyle().setRight(0, Unit.PX);
	}
}
