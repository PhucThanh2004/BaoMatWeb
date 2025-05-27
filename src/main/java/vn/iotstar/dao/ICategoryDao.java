package vn.iotstar.dao;

import vn.iotstar.models.CategoryModel;

import java.util.List;

public interface ICategoryDao {
    List<CategoryModel> findAll() throws Exception;

    CategoryModel findById(int id) throws Exception;
}
