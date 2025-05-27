package vn.iotstar.service.impl;

import vn.iotstar.dao.ICategoryDao;
import vn.iotstar.dao.impl.CategoryDaoImpl;
import vn.iotstar.models.CategoryModel;
import vn.iotstar.service.ICategoryService;

import java.util.List;

public class CategoryServiceImpl implements ICategoryService {
    private ICategoryDao categoryDao;

    public CategoryServiceImpl() {
        categoryDao = new CategoryDaoImpl();
    }

    @Override
    public List<CategoryModel> getCategories() throws Exception {
        return categoryDao.findAll();
    }

    @Override
    public CategoryModel findById(int id) throws Exception {
        return categoryDao.findById(id);
    }
}
