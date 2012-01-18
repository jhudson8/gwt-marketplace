/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.model;

import javax.persistence.Entity;

import com.googlecode.objectify.Key;

@Entity(name = "prdct_sht")
public class ProductSnapshot extends Product {
	private static final long serialVersionUID = 1L;

	private Key<Product> productKey;

	public Key<Product> getProductKey() {
		return productKey;
	}

	public void setProductKey(Key<Product> productKey) {
		this.productKey = productKey;
	}
}
