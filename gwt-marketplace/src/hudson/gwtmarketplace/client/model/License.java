/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.model;

import java.util.Arrays;
import java.util.List;

public enum License implements DisplayEntity {

	APACHE_1, APACHE_2, LGPL, GPL_1, GPL_2, GPL_3, MIT, MOZILLA, BSD, EPL, OTHER, ITS_COMPLICATED;

	public static final String CODE_APACHE_1 = "A1";
	public static final String CODE_APACHE_2 = "A2";
	public static final String CODE_LGPL = "LGPL";
	public static final String CODE_GPL_1 = "GPL1";
	public static final String CODE_GPL_2 = "GPL2";
	public static final String CODE_GPL_3 = "GPL3";
	public static final String CODE_MIT = "MIT";
	public static final String CODE_MOZILLA = "MOZILLA";
	public static final String CODE_BSD = "BSD";
	public static final String CODE_EPL = "EPL";
	public static final String CODE_OTHER = "O";
	public static final String CODE_ITS_COMPLICATED = "IC";

	public static License fromCode(String code) {
		if (null == code) return null;
		else if (code.equals(CODE_APACHE_1)) return APACHE_1;
		else if (code.equals(CODE_APACHE_2)) return APACHE_2;
		else if (code.equals(CODE_LGPL)) return LGPL;
		else if (code.equals(CODE_GPL_1)) return GPL_1;
		else if (code.equals(CODE_GPL_2)) return GPL_2;
		else if (code.equals(CODE_GPL_3)) return GPL_3;
		else if (code.equals(CODE_MIT)) return MIT;
		else if (code.equals(CODE_MOZILLA)) return MOZILLA;
		else if (code.equals(CODE_BSD)) return BSD;
		else if (code.equals(CODE_EPL)) return EPL;
		else if (code.equals(CODE_OTHER)) return OTHER;
		else if (code.equals(CODE_ITS_COMPLICATED)) return ITS_COMPLICATED;
		else return null;
	}

	public static List<License> VALUES = Arrays.asList(new License[] {
			APACHE_1, APACHE_2, LGPL, GPL_1, GPL_2, GPL_3, MIT, MOZILLA,
			BSD, EPL, ITS_COMPLICATED, OTHER
	});

	public String toCode() {
		if (this.equals(APACHE_1)) return CODE_APACHE_1;
		else if (this.equals(APACHE_2)) return CODE_APACHE_2;
		else if (this.equals(LGPL)) return CODE_LGPL;
		else if (this.equals(GPL_1)) return CODE_GPL_1;
		else if (this.equals(GPL_2)) return CODE_GPL_2;
		else if (this.equals(GPL_3)) return CODE_GPL_3;
		else if (this.equals(MIT)) return CODE_MIT;
		else if (this.equals(MOZILLA)) return CODE_MOZILLA;
		else if (this.equals(BSD)) return CODE_BSD;
		else if (this.equals(EPL)) return CODE_EPL;
		else if (this.equals(OTHER)) return CODE_OTHER;
		else if (this.equals(ITS_COMPLICATED)) return CODE_ITS_COMPLICATED;
		else return null;
	}

	public static String getDisplayValue(String code) {
		License license = fromCode(code);
		if (null == license) return "";
		else return license.getDisplayValue();
	}

	public String getDisplayValue() {
		if (this.equals(APACHE_1)) return "Apache 1";
		else if (this.equals(APACHE_2)) return "Apache 2";
		else if (this.equals(LGPL)) return "GNU Lesser General Public License";
		else if (this.equals(GPL_1)) return "GNU General Public License v1";
		else if (this.equals(GPL_2)) return "GNU General Public License v2";
		else if (this.equals(GPL_3)) return "GNU General Public License v3";
		else if (this.equals(MIT)) return "MIT License";
		else if (this.equals(MOZILLA)) return "Mozilla Public License 1.1";
		else if (this.equals(BSD)) return "New BSD License";
		else if (this.equals(EPL)) return "Eclipse Public License 1.0";
		else if (this.equals(OTHER)) return "Other";
		else if (this.equals(ITS_COMPLICATED)) return "It's Complicated";
		else return null;
	}

	@Override
	public String getIdValue() {
		return toCode();
	}
}
