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
import vn.iotstar.models.ShopModel;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IProductImageService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.IShopService;
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

@WebServlet(urlPatterns = {"/shop/product/add-product"})
@MultipartConfig
public class AddProductController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private IProductService productService;
    private ICategoryService categoryService;
    private IProductImageService productImageService;
    private IShopService shopService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            categoryService = new CategoryServiceImpl();
            List<CategoryModel> categories = categoryService.getCategories();

            String shopId = req.getParameter("id"); // Hoặc "shop", tùy theo tên tham số bạn truyền vào

            // Kiểm tra nếu idShop có giá trị hợp lệ
            if (shopId == null || shopId.isEmpty()) {
                req.getSession().setAttribute("error", "Không tìm thấy ID shop.");
                resp.sendRedirect(req.getContextPath() + "/shop/product/list-product");
                return;
            }
            
            req.setAttribute("categories", categories);
            req.setAttribute("shop", shopId);
          
            req.getRequestDispatcher(Constant.SHOP_ADD_PRODUCT).forward(req, resp);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    	 int accountId = 0;
    	 try {
             String name = req.getParameter("name");
             String description = req.getParameter("description");
             double price = Double.parseDouble(req.getParameter("price"));
             int stockQuantity = Integer.parseInt(req.getParameter("stock_quantity"));
             int categoryId = Integer.parseInt(req.getParameter("category_id"));
             int shopId = Integer.parseInt(req.getParameter("shop"));
            

             ProductModel product = new ProductModel();
             product.setName(name);
             product.setDescription(description);
             product.setPrice(price);
             product.setStockQuantity(stockQuantity);
             product.setCategoryId(categoryId);
             product.setShopId(shopId); // Bạn có thể thay shopId ở đây.
             product.setCreatedAt(LocalDateTime.now());

             productService = new ProductServiceImpl();
             productService.addProduct(product);

             // Xử lý ảnh sản phẩm
             for (Part filePart : req.getParts()) {
                 if ("product_images".equals(filePart.getName())) {
                     String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                     String uploadPath = Constant.DIR; // Đường dẫn lưu ảnh

                     File uploadFile = new File(uploadPath);
                     if (!uploadFile.exists()) {
                         uploadFile.mkdir();
                     }

                     // Tạo tên file duy nhất cho ảnh
                     String newFileName = new Utils().generateUniqueFileName(fileName);
                     String filePath = uploadPath + File.separator + newFileName;

                     // Lưu file vào thư mục
                     filePart.write(filePath);

                     // Thêm thông tin ảnh vào cơ sở dữ liệu
                     ProductImageModel productImage = new ProductImageModel();
                     productImage.setProductId(product.getId());
                     productImage.setProductImage(newFileName);

                     productImageService = new ProductImageServiceImpl();
                     productImageService.addProductImage(productImage);
                 }
             }
             
          
             
             
     		

             // Hiển thị thông báo thành công
             req.getSession().setAttribute("message", "Thêm mới sản phẩm thành công!");
             resp.sendRedirect(req.getContextPath() + "/shop/product/list-product?id="+ shopId);

         } catch (Exception e) {
             req.getSession().setAttribute("error", "Thêm mới sản phẩm thất bại!");
             resp.sendRedirect(req.getContextPath() + "/shop/product/list-product?id="+ accountId);
             throw new RuntimeException(e);
         }
    }

}



