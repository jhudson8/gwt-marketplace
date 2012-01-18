/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.components;

import org.cobogw.gwt.user.client.ui.Rating;

public class ProductRating extends Rating {

	public ProductRating() {
		super(0, 5);
	}

	public ProductRating(int initRating) {
		super(convertToRatingValue(initRating), 5);
	}

	public ProductRating(int initRating, boolean readOnly) {
		super(convertToRatingValue(initRating), 5);
		if (readOnly)
			setReadOnly(true);
	}

	private static int convertToRatingValue(float val) {
		if (val == 0) return 0;
		else return (int) val;
	}


	public Integer getRatingValue() {
		Integer val = getValue();
		if (val == null || val.intValue() == 0) return null;
		else return val.intValue();
	}

	public void setRatingValue(float val) {
		super.setValue(convertToRatingValue(val), false);
	}
}
