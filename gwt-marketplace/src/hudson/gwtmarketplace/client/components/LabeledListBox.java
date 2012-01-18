/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.components;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.i18n.client.HasDirection;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.ListBox;

public class LabeledListBox extends LabeledContainer<ListBox> implements
		HasDirection {

	public LabeledListBox() {
		super(null, new ListBox());
	}

	public LabeledListBox(String label) {
		super(label, new ListBox());
	}

	public LabeledListBox(String label, boolean multipleChoice) {
		super(label, new ListBox(multipleChoice));
	}

	public Direction getDirection() {
		return Direction.RTL;
	}

	public void setDirection(Direction direction) {
	}

	 /**
	   * Adds an item to the list box. This method has the same effect as
	   * 
	   * <pre>
	   * addItem(item, item)
	   * </pre>
	   * 
	   * @param item the text of the item to be added
	   */
	  public void addItem(String item) {
	    getComponent().addItem(item);
	  }

	  /**
	   * Adds an item to the list box, specifying an initial value for the item.
	   * 
	   * @param item the text of the item to be added
	   * @param value the item's value, to be submitted if it is part of a
	   *          {@link FormPanel}; cannot be <code>null</code>
	   */
	  public void addItem(String item, String value) {
		  getComponent().addItem(item, value);
	  }

	  /**
	   * Removes all items from the list box.
	   */
	  public void clear() {
	    getComponent().clear();
	  }

	  /**
	   * Gets the number of items present in the list box.
	   * 
	   * @return the number of items
	   */
	  public int getItemCount() {
	    return getComponent().getItemCount();
	  }

	  /**
	   * Gets the text associated with the item at the specified index.
	   * 
	   * @param index the index of the item whose text is to be retrieved
	   * @return the text associated with the item
	   * @throws IndexOutOfBoundsException if the index is out of range
	   */
	  public String getItemText(int index) {
	    return getComponent().getItemText(index);
	  }

	  public String getName() {
	    return getComponent().getName();
	  }

	  /**
	   * Gets the currently-selected item. If multiple items are selected, this
	   * method will return the first selected item ({@link #isItemSelected(int)}
	   * can be used to query individual items).
	   * 
	   * @return the selected index, or <code>-1</code> if none is selected
	   */
	  public int getSelectedIndex() {
	    return getComponent().getSelectedIndex();
	  }

	  /**
	   * Gets the value associated with the item at a given index.
	   * 
	   * @param index the index of the item to be retrieved
	   * @return the item's associated value
	   * @throws IndexOutOfBoundsException if the index is out of range
	   */
	  public String getValue(int index) {
	    return getComponent().getValue(index);
	  }

	  /**
	   * Gets the number of items that are visible. If only one item is visible,
	   * then the box will be displayed as a drop-down list.
	   * 
	   * @return the visible item count
	   */
	  public int getVisibleItemCount() {
	    return getComponent().getVisibleItemCount();
	  }

	  /**
	   * Inserts an item into the list box. Has the same effect as
	   * 
	   * <pre>
	   * insertItem(item, item, index)
	   * </pre>
	   * 
	   * @param item the text of the item to be inserted
	   * @param index the index at which to insert it
	   */
	  public void insertItem(String item, int index) {
	    getComponent().insertItem(item, index);
	  }

	  /**
	   * Inserts an item into the list box, specifying an initial value for the
	   * item. If the index is less than zero, or greater than or equal to the
	   * length of the list, then the item will be appended to the end of the list.
	   * 
	   * @param item the text of the item to be inserted
	   * @param value the item's value, to be submitted if it is part of a
	   *          {@link FormPanel}.
	   * @param index the index at which to insert it
	   */
	  public void insertItem(String item, String value, int index) {
	    getComponent().insertItem(item, value, index);
	  }

	  /**
	   * Determines whether an individual list item is selected.
	   * 
	   * @param index the index of the item to be tested
	   * @return <code>true</code> if the item is selected
	   * @throws IndexOutOfBoundsException if the index is out of range
	   */
	  public boolean isItemSelected(int index) {
	    return getComponent().isItemSelected(index);
	  }

	  /**
	   * Gets whether this list allows multiple selection.
	   * 
	   * @return <code>true</code> if multiple selection is allowed
	   */
	  public boolean isMultipleSelect() {
	    return getComponent().isMultipleSelect();
	  }

	  /**
	   * Removes the item at the specified index.
	   * 
	   * @param index the index of the item to be removed
	   * @throws IndexOutOfBoundsException if the index is out of range
	   */
	  public void removeItem(int index) {
	    getComponent().removeItem(index);
	  }

	  /**
	   * Sets whether an individual list item is selected.
	   * 
	   * <p>
	   * Note that setting the selection programmatically does <em>not</em> cause
	   * the {@link ChangeHandler#onChange(ChangeEvent)} event to be fired.
	   * </p>
	   * 
	   * @param index the index of the item to be selected or unselected
	   * @param selected <code>true</code> to select the item
	   * @throws IndexOutOfBoundsException if the index is out of range
	   */
	  public void setItemSelected(int index, boolean selected) {
	    getComponent().setItemSelected(index, selected);
	  }

	  /**
	   * Sets the text associated with the item at a given index.
	   * 
	   * @param index the index of the item to be set
	   * @param text the item's new text
	   * @throws IndexOutOfBoundsException if the index is out of range
	   */
	  public void setItemText(int index, String text) {
	    getComponent().setItemText(index, text);
	  }


	  public void setName(String name) {
	    getComponent().setName(name);
	  }

	  /**
	   * Sets the currently selected index.
	   * 
	   * After calling this method, only the specified item in the list will remain
	   * selected. For a ListBox with multiple selection enabled, see
	   * {@link #setItemSelected(int, boolean)} to select multiple items at a time.
	   * 
	   * <p>
	   * Note that setting the selected index programmatically does <em>not</em>
	   * cause the {@link ChangeHandler#onChange(ChangeEvent)} event to be fired.
	   * </p>
	   * 
	   * @param index the index of the item to be selected
	   */
	  public void setSelectedIndex(int index) {
	    getComponent().setSelectedIndex(index);
	  }

	  /**
	   * Sets the value associated with the item at a given index. This value can be
	   * used for any purpose, but is also what is passed to the server when the
	   * list box is submitted as part of a {@link FormPanel}.
	   * 
	   * @param index the index of the item to be set
	   * @param value the item's new value; cannot be <code>null</code>
	   * @throws IndexOutOfBoundsException if the index is out of range
	   */
	  public void setValue(int index, String value) {
	    getComponent().setValue(index, value);
	  }

	  /**
	   * Sets the number of items that are visible. If only one item is visible,
	   * then the box will be displayed as a drop-down list.
	   * 
	   * @param visibleItems the visible item count
	   */
	  public void setVisibleItemCount(int visibleItems) {
	    getComponent().setVisibleItemCount(visibleItems);
	  }

	  public void addChangeHandler(ChangeHandler handler) {
		  getComponent().addChangeHandler(handler);
	  }
}
