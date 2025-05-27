package vn.iotstar.models;

import java.time.LocalDateTime;

public class CategoryModel extends  BaseModel{
    private int id;
    private String name;
    private String thumbnail;

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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public CategoryModel() {
    }

    public CategoryModel(int id, String name, String thumbnail) {
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }

    public CategoryModel(LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt, int id, String name, String thumbnail) {
        super(createdAt, updatedAt, deletedAt);
        this.id = id;
        this.name = name;
        this.thumbnail = thumbnail;
    }
}
