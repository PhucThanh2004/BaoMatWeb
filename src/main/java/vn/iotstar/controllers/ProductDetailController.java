package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import vn.iotstar.models.CategoryModel;
import vn.iotstar.models.ProductModel;
import vn.iotstar.models.ReviewModel;
import vn.iotstar.models.ShopModel;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IReviewService;
import vn.iotstar.service.IShopService;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.ProductServiceImpl;
import vn.iotstar.service.impl.ReviewServiceImpl;
import vn.iotstar.service.impl.ShopServiceImpl;
import vn.iotstar.utils.Constant;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/product/detail"})
public class ProductDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IProductService productService;
    private ICategoryService categoryService;
    private IReviewService reviewService;
    private IShopService shopService;

    @Override
    public void init() throws ServletException {
        productService = new ProductServiceImpl();
        categoryService = new CategoryServiceImpl();
        reviewService = new ReviewServiceImpl(); 
        shopService = new ShopServiceImpl();

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            int productId = Integer.parseInt(req.getParameter("id"));

            categoryService = new CategoryServiceImpl();
            List<CategoryModel> categories = categoryService.getCategories();
            int countReview = reviewService.countReview(productId);

            req.setAttribute("categories", categories);

            ProductModel product = productService.getProductById(productId);
            req.setAttribute("product", product);
            
            int shopId = product.getShopId();
            ShopModel shop = shopService.findByShopId(shopId);
            
            List<ReviewModel> reviews = reviewService.getReviewsWithAuthorByProductId(productId);
            req.setAttribute("reviews", reviews); 
            req.setAttribute("shop", shop); 
            req.setAttribute("countReview", countReview);

            req.getRequestDispatcher(Constant.USER_PRODUCT_DETAIL).forward(req, resp);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   

}
