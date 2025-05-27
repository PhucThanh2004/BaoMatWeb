package vn.iotstar.dao;

import vn.iotstar.models.ProductModel;

import java.util.List;

public interface IProductDao {
    List<ProductModel> getAllProductOfShop(int shopId, int page, int pageSize) throws Exception;

    int getTotalProductOfShop(int shopId) throws Exception;

    void addProduct(ProductModel productModel) throws Exception;

    boolean deleteProduct(int productId) throws Exception;

    ProductModel getProductById(int id) throws Exception;

    void updateProduct(ProductModel productModel) throws Exception;

    List<ProductModel> getProductsByCategory(int categoryId, int limit) throws Exception;

    List<ProductModel> getProductsNew(int limit) throws Exception;
    
    List<ProductModel> searchProductsByName(String name) throws Exception;

}
