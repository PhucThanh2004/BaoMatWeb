package vn.iotstar.models;

import java.time.LocalDateTime;
import java.util.List;

public class ProductModel extends BaseModel{
    private int id;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
    private int categoryId;
    private int shopId;
    private List<String> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public ProductModel() {
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public ProductModel(int id, String name, String description, double price, int stockQuantity, int categoryId, int shopId, List<String> images) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.shopId = shopId;
        this.images = images;
    }

    public ProductModel(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, int id, String name, String description, double price, int stockQuantity, int categoryId, int shopId, List<String> images) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.shopId = shopId;
        this.images = images;
    }

    public ProductModel(int id, String name, String description, double price, int stockQuantity, int categoryId, int shopId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.shopId = shopId;
    }

    public ProductModel(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, int id, String name, String description, double price, int stockQuantity, int categoryId, int shopId) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
        this.shopId = shopId;
    }
}
