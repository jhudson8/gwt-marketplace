/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.model;

import java.util.Arrays;
import java.util.List;

public enum Status implements DisplayEntity {

	ALPHA, BETA, STABLE, NOT_MAINTAINED;

	public static final String CODE_ALPHA = "A";
	public static final String CODE_BETA = "B";
	public static final String CODE_STABLE = "S";
	public static final String CODE_NOT_MAINTAINED = "N";

	public static List<Status> VALUES = Arrays.asList(new Status[] {
			ALPHA, BETA, STABLE, NOT_MAINTAINED
	});
	
	public static Status fromCode(String code) {
		if (null == code) return null;
		else if (code.equals(CODE_ALPHA)) return ALPHA;
		else if (code.equals(CODE_BETA)) return BETA;
		else if (code.equals(CODE_STABLE)) return STABLE;
		else if (code.equals(CODE_NOT_MAINTAINED)) return NOT_MAINTAINED;
		else return null;
	}

	public String toCode() {
		if (this == ALPHA) return CODE_ALPHA;
		else if (this == BETA) return CODE_BETA;
		else if (this == STABLE) return CODE_STABLE;
		else if (this == NOT_MAINTAINED) return CODE_NOT_MAINTAINED;
		else return null;
	}

	public static String getDisplayValue(String code) {
		Status status = fromCode(code);
		if (null == status) return "";
		else return status.getDisplayValue();
	}
	
	public String getDisplayValue() {
		if (this == ALPHA) return "Alpha";
		else if (this == BETA) return "Beta";
		else if (this == STABLE) return "Stable";
		else if (this == NOT_MAINTAINED) return "Not Maintained";
		else return null;
	}

	@Override
	public String getIdValue() {
		return toCode();
	}
}