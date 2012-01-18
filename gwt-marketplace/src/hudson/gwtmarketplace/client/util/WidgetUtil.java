/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.util;

import hudson.gwtmarketplace.client.components.LabeledContainer;
import hudson.gwtmarketplace.client.model.DisplayEntity;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.event.shared.HasHandlers;
import com.google.gwt.gwtpages.client.message.Message;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class WidgetUtil {

	public static <T extends DisplayEntity> void load(ListBox widget,
			List<T> l, String emptyText) {
		widget.clear();
		if (null != emptyText)
			widget.addItem(emptyText, "");
		for (DisplayEntity de : l)
			widget.addItem(de.getDisplayValue(), de.getIdValue());
	}

	public static <T extends DisplayEntity> void load(ListBox widget, T[] l,
			String emptyText) {
		widget.clear();
		if (null != emptyText)
			widget.addItem(emptyText, "");
		for (DisplayEntity de : l)
			widget.addItem(de.getDisplayValue(), de.getIdValue());
	}

	public static <T extends DisplayEntity> ArrayList<T> getAllValues(
			ListBox widget, List<T> options) {
		int count = widget.getItemCount();
		ArrayList<T> rtn = new ArrayList<T>(count);
		for (int i = 0; i < count; i++) {
			String val = widget.getValue(i);
			for (T option : options) {
				if (option.getIdValue().equals(val)) {
					rtn.add(option);
					break;
				}
			}
		}
		return rtn;
	}

	public static <T extends DisplayEntity> T getSelectedValue(ListBox widget,
			List<T> options) {
		int selectedIndex = widget.getSelectedIndex();
		if (selectedIndex >= 0) {
			String val = widget.getValue(selectedIndex);
			for (T option : options) {
				if (option.getIdValue().equals(val)) {
					return option;
				}
			}
		}
		return null;
	}

	public static <T extends DisplayEntity> Long getSelectedValueAsLong(
			ListBox widget) {
		String s = widget.getValue(widget.getSelectedIndex());
		if (null != s && s.length() > 0)
			return new Long(s);
		else
			return null;
	}

	public static <T extends DisplayEntity> String getSelectedValue(
			ListBox widget) {
		String s = widget.getValue(widget.getSelectedIndex());
		if (null != s && s.length() > 0)
			return s;
		else
			return null;
	}

	public static <T extends DisplayEntity> void selectValue(ListBox widget,
			String value) {
		if (null == value) {
			if (widget.getItemCount() > 0 && widget.getValue(0).equals(""))
				widget.setSelectedIndex(0);
			else
				widget.setSelectedIndex(-1);
			return;
		}
		for (int i = 0; i < widget.getItemCount(); i++) {
			String s = widget.getValue(i);
			if (null != s && s.equals(value)) {
				widget.setSelectedIndex(i);
				return;
			}
		}
		if (widget.getItemCount() > 0 && widget.getValue(0).equals(""))
			widget.setSelectedIndex(0);
		else
			widget.setSelectedIndex(-1);
	}

	public static <T extends DisplayEntity> void selectValue(ListBox widget,
			T value) {
		if (null == value) {
			if (widget.getItemCount() > 0 && widget.getValue(0).equals(""))
				widget.setSelectedIndex(0);
			else
				widget.setSelectedIndex(-1);
		} else {
			String idValue = value.getIdValue();
			int count = widget.getItemCount();
			for (int i = 0; i < count; i++) {
				if (widget.getValue(i).equals(idValue)) {
					widget.setSelectedIndex(i);
					return;
				}
			}
			if (widget.getItemCount() > 0 && widget.getValue(0).equals(""))
				widget.setSelectedIndex(0);
			else
				widget.setSelectedIndex(-1);
		}
	}

	public static void checkNull(LabeledContainer[] components,
			List<Message> messages) {
		for (LabeledContainer lc : components) {
			Widget component = lc.getComponent();
			if (component instanceof TextBox) {
				if (isNull(((TextBox) component).getValue()))
					messages.add(Message.error("'" + lc.getLabel()
							+ "' is a required field", (component instanceof HasHandlers)?(HasHandlers)component:null));
			} else if (component instanceof ListBox) {
				if (((ListBox) component).getSelectedIndex() < 0
						|| isNull(((ListBox) component)
								.getValue(((ListBox) component)
										.getSelectedIndex()))) {
					messages.add(Message.error("'" + lc.getLabel()
							+ "' is a required field", (component instanceof HasHandlers)?(HasHandlers)component:null));
				}
			}
		}
	}

	private static boolean isNull(String s) {
		return (null == s || s.length() == 0);
	}
}