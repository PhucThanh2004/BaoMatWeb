package vn.iotstar.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.AccountModel;
import vn.iotstar.models.AddressModel;
import vn.iotstar.models.CartDetailWithProduct;
import vn.iotstar.models.CartModel;
import vn.iotstar.models.CategoryModel;
import vn.iotstar.models.ProductModel;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.IAddressService;
import vn.iotstar.service.ICartService;
import vn.iotstar.service.ICategoryService;
import vn.iotstar.service.IOrderService;
import vn.iotstar.service.IProductService;
import vn.iotstar.service.impl.AccountServiceImpl;
import vn.iotstar.service.impl.AddressServiceImpl;
import vn.iotstar.service.impl.CartServiceImpl;
import vn.iotstar.service.impl.CategoryServiceImpl;
import vn.iotstar.service.impl.OrderServiceImpl;
import vn.iotstar.service.impl.ProductServiceImpl;
import vn.iotstar.utils.Constant;

import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = { "/checkout" })
public class CheckoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private IProductService productService;
	private ICategoryService categoryService;
	private IOrderService orderService;
	private ICartService cartService;
	public IAccountService accountService;
	IAddressService addressService;


	@Override
	public void init() throws ServletException {
		productService = new ProductServiceImpl();
		categoryService = new CategoryServiceImpl();
		orderService = new OrderServiceImpl();
		cartService = new CartServiceImpl();
		accountService = new AccountServiceImpl();
		addressService = new AddressServiceImpl();

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			HttpSession session = req.getSession();
			AccountModel account = (AccountModel) session.getAttribute("account");
			int accountId = account.getId();
			String email = account.getEmail();
			
			
			AccountModel acc = accountService.findByUserName(email);
			List<AddressModel> address = addressService.findAllById(account.getEmail());
			
			
			CartModel cart = cartService.findCartByAccountId(accountId);
			if (cart == null) {
				resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found cart");
				return;
			}
			List<CartDetailWithProduct> carts = cartService.getCartDetailsWithProducts(cart.getId());
			double totalAmount = 0;

			for (CartDetailWithProduct cartDetail : carts) {
				double productPrice = cartDetail.getProductPrice();
				int quantity = cartDetail.getQuantity();
				totalAmount += productPrice * quantity;
			}

			req.setAttribute("carts", carts);
			req.setAttribute("totalAmount", totalAmount);
			req.setAttribute("account", acc);
			req.setAttribute("address", address);
			req.getRequestDispatcher(Constant.USER_CHECKOUT).forward(req, resp);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession();
			AccountModel account = (AccountModel) session.getAttribute("account");
			int accountId = account.getId();
			String paymentMethod = "COD";
			String note = request.getParameter("note");
			int addressId = Integer.parseInt(request.getParameter("selectedAddress"));

			System.out.print(addressId);

			List<Integer> orderIds = orderService.checkoutCart(accountId, paymentMethod, note);
			
			for (Integer orderId : orderIds) {
	            orderService.updateAddress(orderId, addressId);
	        }

			// Sau khi checkout thành công, chuyển hướng về trang đơn hàng
			request.getSession().setAttribute("successMessage",
					"Checkout thành công với " + orderIds.size() + " đơn hàng!");
			response.sendRedirect("orders"); // Trang quản lý đơn hàng của user
		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("errorMessage", "Có lỗi xảy ra khi checkout!");
			response.sendRedirect("/cart/views"); // Quay lại trang giỏ hàng nếu lỗi
		}
	}
}
