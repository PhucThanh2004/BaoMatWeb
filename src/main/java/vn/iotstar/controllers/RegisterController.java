package vn.iotstar.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.SecureRandom;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;

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
		// Tạo token CSRF và lưu vào session
        String csrfToken = generateCsrfToken();
        req.getSession().setAttribute("csrf_token", csrfToken);
		
		String url = req.getRequestURL().toString();
		if (url.contains("register")) {
			req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
		} else if (url.contains("VerifyCode")) {
			req.getRequestDispatcher("/views/verify.jsp").forward(req, resp);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// Kiểm tra token CSRF
        String csrfToken = req.getParameter("csrf_token");
        String sessionToken = (String) req.getSession().getAttribute("csrf_token");
        if (csrfToken == null || !csrfToken.equals(sessionToken)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token không hợp lệ!");
            return;
        }
		
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
	    String rawPassword = req.getParameter("password");
	    String password = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
	    String confirmPassword = req.getParameter("confirmPassword");

	    String alertMsg = "";
	    
	    // Kiểm tra định dạng email
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            alertMsg = "Email không hợp lệ!";
            req.setAttribute("error", alertMsg);
            req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
            return;
        }
        
        String errorMsg = null;

	    // Kiểm tra dữ liệu đầu vào
	    if (!isValidEmail(email) || !isValidName(name) || !isValidPhone(phone) ||
	        !isValidPassword(password)) {
	        errorMsg = "Dữ liệu không hợp lệ!";
	    } else if (userService.checkExistEmail(email)) {
	        errorMsg = "Email đã tồn tại!";
	    }

	    if (errorMsg != null) {
	        req.setAttribute("error", errorMsg);
	        req.getRequestDispatcher("/views/register.jsp").forward(req, resp);
	        return;
	    }

	    if (userService.checkExistEmail(email)) {
	    	// Mã hóa email trước khi hiển thị trong thông báo lỗi
            String safeEmail = htmlEscape(email);
            alertMsg = "Email " + safeEmail + " đã tồn tại!";
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
	
	// Phương thức tạo token CSRF ngẫu nhiên
    private String generateCsrfToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
    
	// Phương thức mã hóa HTML để ngăn chặn XSS
    private String htmlEscape(String input) {
        if (input == null) return "";
        if (input.isEmpty()) return "";

        // Thay thế các ký tự đặc biệt theo thứ tự an toàn
        String escaped = input;
        escaped = escaped.replace("&", "&amp;"); // Thay & trước tiên
        escaped = escaped.replace("<", "&lt;");  // Thay < trước >
        escaped = escaped.replace(">", "&gt;");
        escaped = escaped.replace("\"", "&quot;"); // Thay " bằng &quot;
        escaped = escaped.replace("'", "&#39;");   // Thay ' bằng thực thể HTML
        escaped = escaped.replace("/", "&#47;");   // Thêm / để ngăn chặn </script>

        return escaped;
    }
    
    // Kiểm tra email
 	private boolean isValidEmail(String email) {
 	    String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
 	    return email != null && email.matches(emailRegex);
 	}

 	// Kiểm tra tên
 	private boolean isValidName(String name) {
 	    return name != null && name.matches("^[a-zA-Z\\s]{1,50}$");
 	}
 	
 	// Kiểm tra số điện thoại
 	private boolean isValidPhone(String phone) {
 	    return phone != null && phone.matches("^\\d{10,15}$");
 	}

 	// Kiểm tra mật khẩu
 	private boolean isValidPassword(String password) {
 	    if (password == null || password.length() < 8) return false;
 	    // Chỉ cho phép chữ hoa, chữ thường, số và một số ký tự đặc biệt an toàn
 	    return password.matches("^[a-zA-Z0-9@#$%^&+=!]*$");
 	}

}
