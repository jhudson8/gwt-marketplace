package hudson.gwtmarketplace.server.model;

import hudson.gwtmarketplace.client.model.Product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Blob;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Parent;
import com.googlecode.objectify.annotation.Unindexed;

@Entity(name="img")
public class ProductImage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@Column(name="pid")
	@Parent
	private Key<Product> productId;
	@Column(name="data")
	@Unindexed
	private Blob data;

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
	public Blob getData() {
		return data;
	}
	public void setData(Blob data) {
		this.data = data;
	}
}
