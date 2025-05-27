package vn.iotstar.models;

import java.time.LocalDateTime;

public class ProductImageModel extends BaseModel{
    private int id;
    private int productId;
    private String productImage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public ProductImageModel() {
    }

    public ProductImageModel(int id, int productId, String productImage) {
        this.id = id;
        this.productId = productId;
        this.productImage = productImage;
    }

    public ProductImageModel(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, int id, int productId, String productImage) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.productId = productId;
        this.productImage = productImage;
    }
}
