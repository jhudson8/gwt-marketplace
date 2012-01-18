/*
 * GNU GENERAL PUBLIC LICENSE, Version 3, 29 June 2007
 */
package hudson.gwtmarketplace.client.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.googlecode.objectify.annotation.Unindexed;

@Entity(name = "prdct")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	@Column
	private String userId;
	@Column
	private String name;
	@Column
	String categoryId;
	@Column
	String iconKey;
	@Column
	String categoryName;
	@Column
	private String alias;	
	@Column
	private String organizationName;
	@Column
	@Unindexed
	private String description;
	@Column
	private Date createdDate;
	@Column
	private Date updatedDate;
	@Column
	private Date activityDate;
	@Column
	private Integer numComments;
	@Column
	private Float rating;
	@Column
	private Integer totalRatings;
	@Column
	private Integer totalRatingScore;
	@Column
	@Unindexed
	private String websiteUrl;
	@Column
	@Unindexed
	private String wikiUrl;
	@Column
	@Unindexed
	private String downloadUrl;
	@Column
	@Unindexed
	private String forumUrl;
	@Column
	@Unindexed
	private String issueTrackerUrl;
	@Column
	@Unindexed
	private String newsUrl;
	@Column
	@Unindexed
	private String demoUrl;
	@Column
	private String[] tags;
	@Column
	private String[] searchFields;
	@Column
	@Unindexed
	private String versionNumber;
	@Column
	private String license;
	@Column
	private String status;
	@Column
	private Integer numDailyViews;
	@Column
	private Integer numMonthlyViews;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public Float getRating() {
		return rating;
	}

	public void setRating(Float rating) {
		this.rating = rating;
	}

	public Integer getTotalRatings() {
		return totalRatings;
	}

	public void setTotalRatings(Integer totalRatings) {
		this.totalRatings = totalRatings;
	}

	public Integer getTotalRatingScore() {
		return totalRatingScore;
	}

	public void setTotalRatingScore(Integer totalRatingScore) {
		this.totalRatingScore = totalRatingScore;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(String versionNumber) {
		this.versionNumber = versionNumber;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getDemoUrl() {
		return demoUrl;
	}

	public void setDemoUrl(String demoUrl) {
		this.demoUrl = demoUrl;
	}

	public String getForumUrl() {
		return forumUrl;
	}

	public void setForumUrl(String forumUrl) {
		this.forumUrl = forumUrl;
	}

	public String getNewsUrl() {
		return newsUrl;
	}

	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}

	public Integer getNumComments() {
		return numComments;
	}

	public void setNumComments(Integer numComments) {
		this.numComments = numComments;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}

	public Integer getNumDailyViews() {
		return numDailyViews;
	}

	public void setNumDailyViews(Integer numDailyViews) {
		this.numDailyViews = numDailyViews;
	}

	public Integer getNumMonthlyViews() {
		return numMonthlyViews;
	}

	public void setNumMonthlyViews(Integer numMonthlyViews) {
		this.numMonthlyViews = numMonthlyViews;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String[] getSearchFields() {
		return searchFields;
	}

	public void setSearchFields(String[] searchFields) {
		this.searchFields = searchFields;
	}

	public String getIssueTrackerUrl() {
		return issueTrackerUrl;
	}

	public void setIssueTrackerUrl(String issueTrackerUrl) {
		this.issueTrackerUrl = issueTrackerUrl;
	}

	public boolean equals(Object obj) {
		if (null == getId()) return false;
		if (obj instanceof Product) {
			Product _p = (Product) obj;
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

	public String getIconKey() {
		return iconKey;
	}

	public void setIconKey(String iconKey) {
		this.iconKey = iconKey;
	}

	public String getWikiUrl() {
		return wikiUrl;
	}

	public void setWikiUrl(String wikiUrl) {
		this.wikiUrl = wikiUrl;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
}