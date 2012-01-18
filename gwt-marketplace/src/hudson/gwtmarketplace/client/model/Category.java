/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;

public class Category implements Serializable, DisplayEntity {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@Column
	private String alias;
	@Column
	private String name;
	@Column
	private Integer numProducts;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumProducts() {
		return numProducts;
	}

	public void setNumProducts(Integer numProducts) {
		this.numProducts = numProducts;
	}

	@Override
	public String getIdValue() {
		return (null != alias) ? alias.toString() : null;
	}

	@Override
	public String getDisplayValue() {
		return getName();
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}
}