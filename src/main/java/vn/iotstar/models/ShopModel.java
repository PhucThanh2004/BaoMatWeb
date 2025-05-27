package vn.iotstar.models;

import java.time.LocalDateTime;

public class ShopModel extends  BaseModel{
    private int id;
    private String name;
    private String address;
    private String description;
    private int accountId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public ShopModel() {
    }

    public ShopModel(String description, int id, String name, String address, int accountId) {
        this.description = description;
        this.id = id;
        this.name = name;
        this.address = address;
        this.accountId = accountId;
    }

    public ShopModel(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, String description, int id, String name, String address, int accountId) {
        super(createdAt, updatedAt, deletedAt);
        this.description = description;
        this.id = id;
        this.name = name;
        this.address = address;
        this.accountId = accountId;
    }
}
