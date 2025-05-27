package vn.iotstar.controllers.shop.product;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.models.CategoryModel;
import vn.iotstar.models.ProductImageModel;
import vn.iotstar.models.ProductModel;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductImageService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.ProductImageServiceImpl;
import vn.iotstar.service.impl.ProductServiceImpl;
import vn.iotstar.utils.Constant;
import vn.iotstar.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@WebServlet(urlPatterns = {"/shop/product/delete"})
@MultipartConfig
public class DeleteProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IProductService productService;
    private ICategoryService categoryService;
    private IProductImageService productImageService;

    @Override
    public void init() throws ServletException {
        productService = new ProductServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
        	int shopId = Integer.parseInt(req.getParameter("shopId")); 
            int productId = Integer.parseInt(req.getParameter("id")); // Lấy productId từ URL
            boolean isDeleted = productService.deleteProduct(productId);
            

            if (isDeleted) {
                req.getSession().setAttribute("message", "Xóa sản phẩm thành công!");
            } else {
                req.getSession().setAttribute("error", "Xóa sản phẩm thất bại!");
            }

            // Redirect về trang danh sách sản phẩm
            resp.sendRedirect(req.getContextPath() + "/shop/product/list-product?id="+ shopId);
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Đã xảy ra lỗi khi xóa sản phẩm: " + e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/shop/product/list-product");
            throw new RuntimeException("Lỗi khi xóa sản phẩm", e);
        }
    }
}



	