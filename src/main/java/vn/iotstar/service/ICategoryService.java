package vn.iotstar.service;

import vn.iotstar.models.CategoryModel;

import java.util.List;

public interface ICategoryService {
    List<CategoryModel> getCategories() throws Exception;

    CategoryModel findById(int id) throws Exception;

}
