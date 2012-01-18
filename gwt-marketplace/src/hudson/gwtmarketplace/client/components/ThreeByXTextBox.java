/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.components;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;

public class ThreeByXTextBox extends Composite implements ClickHandler {

	private Button addButton;
	private Grid grid;

	public ThreeByXTextBox() {
		grid = new Grid(1, 2);
		grid.setCellPadding(0);
		grid.setCellSpacing(0);
		grid.setWidget(0, 0, new ThreeTextBox());
		grid.setWidget(0, 1, this.addButton = new Button("Add Row", this));
		initWidget(grid);
	}

	public void onClick(ClickEvent event) {
		if (event.getSource().equals(addButton)) {
			// add a new row
			int rowCount = grid.getRowCount();
			grid.resizeRows(rowCount+1);
			grid.setWidget(rowCount, 0, new ThreeTextBox());
			grid.setWidget(rowCount, 1, new Button("Remove Row", this));
		}
		else {
			// delete the row
			int rowCount = grid.getRowCount();
			for (int i=0; i<rowCount; i++) {
				if (grid.getWidget(i, 1).equals(event.getSource())) {
					grid.removeRow(i);
					return;
				}
			}
		}
	}

	public ArrayList<String> getValues() {
		ArrayList<String> rtn = new ArrayList<String>();
		int rowCount = grid.getRowCount();
		for (int i=0; i<rowCount; i++) {
			((ThreeTextBox) grid.getWidget(i, 0)).addValues(rtn);
		}
		return rtn;
	}

	public void setValues(String[] values) {
		if (null == values || values.length == 0) {
			grid.resize(1, 2);
			grid.setWidget(0, 0, new ThreeTextBox());
			grid.setWidget(0, 1, this.addButton = new Button("Add Row", this));
		}
		else {
			int numRows = values.length / 3;
			if (values.length % 3 != 0) numRows ++;
			grid.resize(numRows, 2);
			for (int i=0; i<numRows; i++) {
				ThreeTextBox ttb = new ThreeTextBox();
				ttb.setValues(values, 3*i);
				grid.setWidget(i, 0, ttb);
				if (i == (numRows-1))
					grid.setWidget(i, 1, this.addButton = new Button("Add Row", this));
				else
					grid.setWidget(i, 1, null);
			}
		}
		
	}
}
