package vn.iotstar.controllers;

import java.io.IOException;
import java.util.List;

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

@WebServlet(urlPatterns = {"/home"})
public class HomeController extends HttpServlet {

    private ICategoryService categoryService;
    private IProductService productService;
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            categoryService = new CategoryServiceImpl();
            productService = new ProductServiceImpl();

            // Lấy danh mục
            List<CategoryModel> categories = categoryService.getCategories();
            req.setAttribute("categories", categories);

            // Kiểm tra từ khóa tìm kiếm
            String keyword = req.getParameter("keyword");
            if (keyword != null && !keyword.trim().isEmpty()) {
                // Tìm kiếm sản phẩm theo từ khóa
                List<ProductModel> searchResults = productService.searchProductsByName(keyword);
                req.setAttribute("products", searchResults);
            } else {
                // Hiển thị sản phẩm mới nếu không có từ khóa
                List<ProductModel> productNew = productService.getProductsNew(20);
                req.setAttribute("products", productNew);
            }

            // Chuyển hướng đến trang chủ
            req.getRequestDispatcher(Constant.USER_HOME).forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}