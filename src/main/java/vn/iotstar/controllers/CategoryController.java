package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.CategoryModel;
import vn.iotstar.models.ProductModel;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.ProductServiceImpl;
import vn.iotstar.utils.Constant;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/category"})
public class CategoryController extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private IProductService productService;
    private ICategoryService categoryService;
    @Override
    public void init() throws ServletException {
        productService = new ProductServiceImpl();
        categoryService = new CategoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            int categoryId = Integer.parseInt(req.getParameter("id"));
            int limit = 20;
            CategoryModel category = categoryService.findById(categoryId);

            categoryService = new CategoryServiceImpl();
            List<CategoryModel> categories = categoryService.getCategories();

            req.setAttribute("categories", categories);

            List<ProductModel> products = productService.getProductsByCategory(categoryId, limit);
            req.setAttribute("products", products);
            req.setAttribute("category", category);

            req.getRequestDispatcher(Constant.USER_CATEGORY).forward(req, resp);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
