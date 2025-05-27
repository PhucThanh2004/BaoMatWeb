package vn.iotstar.dao;

import vn.iotstar.models.ProductImageModel;
import vn.iotstar.models.ProductModel;

import java.util.List;

public interface IProductImageDao {
    void addProductImage(ProductImageModel productImageModel) throws Exception;

    void loadProductImages(List<ProductModel> products) throws Exception;
}
