/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.components;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;

public class ThreeTextBox extends Composite {

	private TextBox tb1;
	private TextBox tb2;
	private TextBox tb3;

	public ThreeTextBox() {
		FlowPanel panel = new FlowPanel();
		panel.add(this.tb1 = new TextBox());
		panel.add(this.tb2 = new TextBox());
		panel.add(this.tb3 = new TextBox());
		this.tb1.addStyleName("field-short");
		this.tb2.addStyleName("field-short");
		this.tb3.addStyleName("field-short");
		this.tb1.getElement().getStyle().setMarginRight(4, Unit.PX);
		this.tb2.getElement().getStyle().setMarginRight(4, Unit.PX);
		this.tb3.getElement().getStyle().setMarginRight(4, Unit.PX);
		initWidget(panel);
	}

	public List<String> getValues() {
		List<String> rtn = new ArrayList<String>();
		addValue(tb1, rtn);
		addValue(tb2, rtn);
		addValue(tb3, rtn);
		return rtn;
	}

	void addValues(List<String> rtn) {
		addValue(tb1, rtn);
		addValue(tb2, rtn);
		addValue(tb3, rtn);
	}

	private void addValue(TextBox tb, List<String> rtn) {
		if (null != tb.getValue() && tb.getValue().length() > 0)
			rtn.add(tb.getValue());
	}

	public void setValues(String[] values) {
		setValues(values, 0);
	}
	
	public void setValues(String[] values, int index) {
		tb1.setValue(null);
		tb2.setValue(null);
		tb3.setValue(null);
		if (null != values) {
			for (int i=index; i<values.length; i++) {
				if (i == index) tb1.setValue(values[i]);
				else if (i == (index+1)) tb2.setValue(values[i]);
				else if (i == (index+2)) tb3.setValue(values[i]);
				else break;
			}
		}
	}
}