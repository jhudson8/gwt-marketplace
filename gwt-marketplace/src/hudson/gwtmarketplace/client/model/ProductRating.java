/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;

@Entity(name="prdt_rtg")
public class ProductRating implements Serializable {
	private static final long serialVersionUID = -1L;

	@Id
	private Long id;
	@Parent
	@Column
	private Key<Product> productId;
	@Column
	private String userId;
	@Column
	private Date createdDate;
	@Column
	private Integer rating;
	@Column
	private String userAlias;
	@Column
	private String ipAddress;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Key<Product> getProductId() {
		return productId;
	}
	public void setProductId(Key<Product> productId) {
		this.productId = productId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public String getUserAlias() {
		return userAlias;
	}
	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	public boolean equals(Object obj) {
		if (null == getId()) return false;
		if (obj instanceof ProductRating) {
			ProductRating _p = (ProductRating) obj;
			if (null == _p.getId()) return false;
			else return _p.getId().equals(getId());
		}
		else return false;
	}

	@Override
	public int hashCode() {
		if (null == getId()) return super.hashCode();
		else return getId().toString().hashCode();
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
}