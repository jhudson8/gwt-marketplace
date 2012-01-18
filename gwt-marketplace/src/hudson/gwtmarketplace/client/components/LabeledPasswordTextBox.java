/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.components;

import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBoxBase.TextAlignConstant;

public class LabeledPasswordTextBox extends LabeledContainer<PasswordTextBox> implements
		HasDirection {

	public LabeledPasswordTextBox() {
		super(null, new PasswordTextBox());
	}
	
	public LabeledPasswordTextBox(String label) {
		super(label, new PasswordTextBox());
	}

	public Direction getDirection() {
		return getComponent().getDirection();
	}

	public void setDirection(Direction direction) {
		getComponent().setDirection(direction);
	}

	/**
	 * Gets the maximum allowable length of the text box.
	 * 
	 * @return the maximum length, in characters
	 */
	public int getMaxLength() {
		return getComponent().getMaxLength();
	}

	/**
	 * Gets the number of visible characters in the text box.
	 * 
	 * @return the number of visible characters
	 */
	public int getVisibleLength() {
		return getComponent().getVisibleLength();
	}

	/**
	 * Sets the maximum allowable length of the text box.
	 * 
	 * @param length
	 *            the maximum length, in characters
	 */
	public LabeledPasswordTextBox setMaxLength(int length) {
		getComponent().setMaxLength(length);
		return this;
	}

	/**
	 * Sets the number of visible characters in the text box.
	 * 
	 * @param length
	 *            the number of visible characters
	 */
	public LabeledPasswordTextBox setVisibleLength(int length) {
		getComponent().setVisibleLength(length);
		return this;
	}

	/**
	 * If a keyboard event is currently being handled on this text box, calling
	 * this method will suppress it. This allows listeners to easily filter
	 * keyboard input.
	 */
	public LabeledPasswordTextBox cancelKey() {
		getComponent().cancelKey();
		return this;
	}

	/**
	 * Gets the current position of the cursor (this also serves as the
	 * beginning of the text selection).
	 * 
	 * @return the cursor's position
	 */
	public int getCursorPos() {
		return getComponent().getCursorPos();
	}

	public String getName() {
		return getComponent().getName();
	}

	/**
	 * Gets the text currently selected within this text box.
	 * 
	 * @return the selected text, or an empty string if none is selected
	 */
	public String getSelectedText() {
		return getComponent().getSelectedText();
	}

	/**
	 * Gets the length of the current text selection.
	 * 
	 * @return the text selection length
	 */
	public int getSelectionLength() {
		return getComponent().getSelectionLength();
	}

	public String getText() {
		return getComponent().getText();
	}

	public String getValue() {
		return getComponent().getValue();
	}

	/**
	 * Determines whether or not the widget is read-only.
	 * 
	 * @return <code>true</code> if the widget is currently read-only,
	 *         <code>false</code> if the widget is currently editable
	 */
	public boolean isReadOnly() {
		return getComponent().isReadOnly();
	}

	@Override
	public void onBrowserEvent(Event event) {
		getComponent().onBrowserEvent(event);
	}

	/**
	 * Selects all of the text in the box.
	 * 
	 * This will only work when the widget is attached to the document and not
	 * hidden.
	 */
	public LabeledPasswordTextBox selectAll() {
		getComponent().selectAll();
		return this;
	}

	/**
	 * Sets the cursor position.
	 * 
	 * This will only work when the widget is attached to the document and not
	 * hidden.
	 * 
	 * @param pos
	 *            the new cursor position
	 */
	public LabeledPasswordTextBox setCursorPos(int pos) {
		getComponent().setCursorPos(pos);
		return this;
	}

	public LabeledPasswordTextBox setName(String name) {
		getComponent().setName(name);
		return this;
	}

	/**
	 * Turns read-only mode on or off.
	 * 
	 * @param readOnly
	 *            if <code>true</code>, the widget becomes read-only; if
	 *            <code>false</code> the widget becomes editable
	 */
	public LabeledPasswordTextBox setReadOnly(boolean readOnly) {
		getComponent().setReadOnly(readOnly);
		return this;
	}

	/**
	 * Sets the range of text to be selected.
	 * 
	 * This will only work when the widget is attached to the document and not
	 * hidden.
	 * 
	 * @param pos
	 *            the position of the first character to be selected
	 * @param length
	 *            the number of characters to be selected
	 */
	public LabeledPasswordTextBox setSelectionRange(int pos, int length) {
		getComponent().setSelectionRange(pos, length);
		return this;
	}

	/**
	 * Sets this object's text. Note that some browsers will manipulate the text
	 * before adding it to the widget. For example, most browsers will strip all
	 * <code>\r</code> from the text, except IE which will add a <code>\r</code>
	 * before each <code>\n</code>. Use {@link #getText()} to get the text
	 * directly from the widget.
	 * 
	 * @param text
	 *            the object's new text
	 */
	public LabeledPasswordTextBox setText(String text) {
		getComponent().setText(text);
		return this;
	}

	/**
	 * Sets the alignment of the text in the text box.
	 * 
	 * @param align
	 *            the text alignment (as specified by {@link #ALIGN_CENTER},
	 *            {@link #ALIGN_JUSTIFY}, {@link #ALIGN_LEFT}, and
	 *            {@link #ALIGN_RIGHT})
	 */
	public LabeledPasswordTextBox setTextAlignment(TextAlignConstant align) {
		getComponent().setTextAlignment(align);
		return this;
	}

	public LabeledPasswordTextBox setValue(String value) {
		getComponent().setValue(value);
		return this;
	}

	public LabeledPasswordTextBox setValue(String value, boolean fireEvents) {
		getComponent().setValue(value, fireEvents);
		return this;
	}
}
