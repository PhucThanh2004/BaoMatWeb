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
import java.util.Collection;
import java.util.List;

@WebServlet(urlPatterns = {"/shop/product/edit"})
@MultipartConfig
public class EditProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IProductService productService;
    private ICategoryService categoryService;
    private IProductImageService productImageService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
        	int shopId = Integer.parseInt(req.getParameter("shopId"));
            int productId = Integer.parseInt(req.getParameter("id"));

            productService = new ProductServiceImpl();
            categoryService = new CategoryServiceImpl();

            ProductModel product = productService.getProductById(productId);
            List<CategoryModel> categories = categoryService.getCategories();

            req.setAttribute("product", product);
            req.setAttribute("categories", categories);
            req.setAttribute("shop", shopId);

            req.getRequestDispatcher(Constant.SHOP_EDIT_PRODUCT).forward(req, resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
        	int shopId = Integer.parseInt(req.getParameter("shopId"));
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            String description = req.getParameter("description");
            double price = Double.parseDouble(req.getParameter("price"));
            int stockQuantity = Integer.parseInt(req.getParameter("stock_quantity"));
            int categoryId = Integer.parseInt(req.getParameter("category_id"));

            ProductModel product = new ProductModel();
            product.setId(id);
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStockQuantity(stockQuantity);
            product.setCategoryId(categoryId);

            productService = new ProductServiceImpl();
            productService.updateProduct(product);

            // Xử lý ảnh nếu có
            Collection<Part> fileParts = req.getParts();
            productImageService = new ProductImageServiceImpl();
            for (Part filePart : fileParts) {
                if ("product_images".equals(filePart.getName()) && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

                    String webappPath = getServletContext().getRealPath("/");
                   
                    String uploadPath = Constant.DIR; 

                    File uploadFile = new File(uploadPath);
                    if (!uploadFile.exists()) {
                        uploadFile.mkdir();
                    }
                    String newFileName = new Utils().generateUniqueFileName(fileName);
                    String filePath = uploadPath + File.separator + newFileName;
                    filePart.write(filePath);

                    ProductImageModel productImage = new ProductImageModel();
                    productImage.setProductId(product.getId());
                    productImage.setProductImage(filePath);
                    productImageService.addProductImage(productImage);
                }
            }

            req.getSession().setAttribute("message", "Cập nhật sản phẩm thành công!");
            resp.sendRedirect(req.getContextPath() + "/shop/product/list-product?id="+ shopId);
        } catch (Exception e) {
            req.getSession().setAttribute("error", "Cập nhật sản phẩm thất bại!");
            resp.sendRedirect(req.getContextPath() + "/shop/product/list-product");
            throw new RuntimeException(e);
        }
    }
}
