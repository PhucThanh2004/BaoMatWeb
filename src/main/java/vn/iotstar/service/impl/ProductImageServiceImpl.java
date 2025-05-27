package vn.iotstar.service.impl;

import vn.iotstar.dao.IProductImageDao;
import vn.iotstar.dao.impl.ProductImageDao;
import vn.iotstar.models.ProductImageModel;
import vn.iotstar.service.IProductImageService;

public class ProductImageServiceImpl implements IProductImageService {
    private IProductImageDao productImageDao;

    @Override
    public void addProductImage(ProductImageModel productImageModel) throws Exception {
        productImageDao = new ProductImageDao();
        productImageDao.addProductImage(productImageModel);
    }
}
