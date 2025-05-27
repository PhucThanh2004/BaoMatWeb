package vn.iotstar.service;

import vn.iotstar.models.ProductModel;

import java.util.List;

public interface IProductService {
    List<ProductModel> getProductsByShop(int shop_id, int page, int pageSize) throws Exception;

    int getProductCountByShop(int shop_id) throws Exception;

    void addProduct(ProductModel product) throws Exception;

    boolean deleteProduct(int productId) throws Exception;

    ProductModel getProductById(int id) throws Exception;

    void updateProduct(ProductModel product) throws Exception;

    List<ProductModel> getProductsByCategory(int categoryId, int limit) throws Exception;

    List<ProductModel> getProductsNew( int limit) throws Exception;
    
    List<ProductModel> searchProductsByName(String name) throws Exception;
}
