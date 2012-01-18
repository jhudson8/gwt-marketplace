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

@Entity(name="prdt_cmt")
public class ProductComment implements Serializable {
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
	private Boolean unableToRate;
	@Column
	private String commentText;
	@Column
	private String userAlias;

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
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public String getUserAlias() {
		return userAlias;
	}
	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	public boolean equals(Object obj) {
		if (null == getId()) return false;
		if (obj instanceof ProductComment) {
			ProductComment _p = (ProductComment) obj;
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
	public Boolean getUnableToRate() {
		return unableToRate;
	}
	public void setUnableToRate(Boolean unableToRate) {
		this.unableToRate = unableToRate;
	}
}