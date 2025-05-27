package vn.iotstar.service.impl;

import vn.iotstar.dao.IProductDao;
import vn.iotstar.dao.impl.ProductDaoImpl;
import vn.iotstar.models.ProductModel;
import vn.iotstar.service.IProductService;

import java.util.List;

public class ProductServiceImpl implements IProductService {

    private IProductDao productDao;

    public ProductServiceImpl() {
        productDao = new ProductDaoImpl();
    }

    @Override
    public List<ProductModel> getProductsByShop(int shop_id, int page, int pageSize) throws Exception {
        return productDao.getAllProductOfShop(shop_id, page, pageSize);
    }

    @Override
    public int getProductCountByShop(int shopId) throws Exception {
        return productDao.getTotalProductOfShop(shopId);
    }

    @Override
    public void addProduct(ProductModel product) throws Exception {
        productDao.addProduct(product);
    }

    @Override
    public boolean deleteProduct(int productId) throws Exception {
        return productDao.deleteProduct(productId);
    }

    @Override
    public ProductModel getProductById(int id) throws Exception {
        return productDao.getProductById(id);
    }

    @Override
    public void updateProduct(ProductModel product) throws Exception {
        productDao.updateProduct(product);
    }

    @Override
    public List<ProductModel> getProductsByCategory(int categoryId, int limit) throws Exception {
        return  productDao.getProductsByCategory(categoryId, limit);
    }

    @Override
    public List<ProductModel> getProductsNew(int limit) throws Exception {
        return productDao.getProductsNew(limit);
    }
    
    @Override
	public List<ProductModel> searchProductsByName(String name) throws Exception {
		return productDao.searchProductsByName(name);
	}
}
