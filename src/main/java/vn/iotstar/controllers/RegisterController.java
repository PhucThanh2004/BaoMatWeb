package vn.iotstar.controllers;

import java.io.IOException;
import java.io.PrintWriter;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.iotstar.models.AccountModel;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.impl.AccountServiceImpl;

@WebServlet(urlPatterns = { "/register", "/VerifyCode" })
public class RegisterController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	IAccountService userService = new AccountServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURL().toString();
		if (url.contains("register")) {
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
		} else if (url.contains("VerifyCode")) {
			req.getRequestDispatcher("/views/verify.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURL().toString();
		if (url.contains("register")) {
			postRegister(req, resp);
		} else if (url.contains("VerifyCode")) {
			postVerifyCode(req, resp);
		}

	}

	

	private void postVerifyCode(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    resp.setContentType("text/html; charset=UTF-8");
	    try (PrintWriter out = resp.getWriter()) {
	        HttpSession session = req.getSession();
	        AccountModel user = (AccountModel) session.getAttribute("account");

	        String code = req.getParameter("authcode");

	        // Kiểm tra mã xác nhận từ database
	        if (code.equals(user.getCode())) {
	            user.setStatus(1);  // Đánh dấu tài khoản đã được xác nhận
	            userService.updatestatus(user);

	            out.println("<div class=\"container\"><br/>" +
	                    "        <br/>" +
	                    "        <br/>Kích hoạt tài khoản thành công!<br/>" +
	                    "        <br/>" +
	                    "        <br/></div>");
	        } else {
	            out.println("<div class=\"container\"><br/>" +
	                    "        <br/>" +
	                    "        <br/>Sai mã kích hoạt, vui lòng kiểm tra lại!<br/>" +
	                    "        <br/>" +
	                    "        <br/></div>");
	        }
	    }
	}


	private void postRegister(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
	    resp.setContentType("text/html");
	    req.setCharacterEncoding("UTF-8");
	    resp.setCharacterEncoding("UTF-8");

	    // Lấy tham số từ view
	    String email = req.getParameter("email");
	    String name = req.getParameter("name");
	    String phone = req.getParameter("phone");
	    String password = req.getParameter("password");
	    String confirmPassword = req.getParameter("confirmPassword");

	    String alertMsg = "";

	    if (userService.checkExistEmail(email)) {
	        alertMsg = "Email đã tồn tại!";
	        req.setAttribute("error", alertMsg);
	        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
	    } else {
	        // Tạo mã xác nhận ngẫu nhiên 6 chữ số
	        String code = String.format("%06d", (int) (Math.random() * 1000000));

	        // Tạo đối tượng AccountModel
	        AccountModel user = new AccountModel(name, email, code,true);

	        // Gọi phương thức để lưu tài khoản vào database và cập nhật mã xác nhận
	        boolean isSuccess = userService.register(email, password, name, phone, null, null, code);

	        if (isSuccess) {
	            HttpSession session = req.getSession();
	            session.setAttribute("account", user);

	            // Chuyển đến trang xác nhận
	            resp.sendRedirect(req.getContextPath() + "/VerifyCode");
	        } else {
	            alertMsg = "Lỗi hệ thống!";
	            req.setAttribute("error", alertMsg);
	            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
	        }
	    }
	}

}
