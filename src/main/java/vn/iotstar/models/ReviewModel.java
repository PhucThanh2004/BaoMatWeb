package vn.iotstar.models;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ReviewModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private int review_id;
	private String content;
	private String image;
	private String video;
	private int product_id;
	private int account_id;
	private String authorName; // Tên người viết review
    private String productName; // Tên sản phẩm
	private LocalDateTime createdAt;
	
	


	public ReviewModel(int review_id, String content, String image, String video, int product_id, int account_id,
			String authorName, String productName, LocalDateTime createdAt) {
		super();
		this.review_id = review_id;
		this.content = content;
		this.image = image;
		this.video = video;
		this.product_id = product_id;
		this.account_id = account_id;
		this.authorName = authorName;
		this.productName = productName;
		this.createdAt = createdAt;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public ReviewModel() {
		super();
	}


	


	public int getReview_id() {
		return review_id;
	}


	public void setReview_id(int review_id) {
		this.review_id = review_id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getVideo() {
		return video;
	}


	public void setVideo(String video) {
		this.video = video;
	}


	public int getProduct_id() {
		return product_id;
	}


	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}


	public int getAccount_id() {
		return account_id;
	}


	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}


	public String getAuthorName() {
		return authorName;
	}


	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	
	
}


