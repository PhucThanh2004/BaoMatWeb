package vn.iotstar.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import vn.iotstar.models.AccountModel;
import vn.iotstar.models.AddressModel;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.IAddressService;
import vn.iotstar.service.impl.AccountServiceImpl;
import vn.iotstar.service.impl.AddressServiceImpl;
import vn.iotstar.utils.Constant;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = { "/account", "/account/updateAvatar", "/account/updateAccount", "/account/updateCoverImage",
		"/account/addAddress", "/account/deleteAddress" })
public class AccountController extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public IAccountService accountService = new AccountServiceImpl();
	public IAddressService addressService = new AddressServiceImpl();

	public String sanitize(String input) {
		if (input == null)
			return null;
		return Jsoup.clean(input, Safelist.none()); // Loại bỏ hoàn toàn thẻ HTML
	}
	
	private boolean hasXSSRisk(String input) {
	    if (input == null) return false;
	    // So sánh chuỗi gốc và chuỗi đã clean nếu khác nhau => có khả năng XSS
	    return !input.equals(Jsoup.clean(input, Safelist.none()));
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();

		if (url.contains("/account")) {
			String email = req.getParameter("email");
			AccountModel acc = accountService.findByUserName(email);
			List<AddressModel> addresslist = addressService.findAllById(email);

			req.setAttribute("account", acc);
			req.setAttribute("address", addresslist);
			req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);

		} else if (url.contains("/account/deleteAddress")) {

			String email = req.getParameter("email");
			int addressId = Integer.parseInt(req.getParameter("id"));
			System.out.println("Email: " + email);
			System.out.println("Address ID: " + addressId);

			addressService.delete(addressId);

			resp.sendRedirect(req.getContextPath() + "/account?email=" + email);

		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();

		if (url.contains("/account/updateAvatar")) {
			String email = req.getParameter("email");
			System.out.println(email);

			Part avatarPart = req.getPart("avatar");

			if (avatarPart != null && avatarPart.getSize() > 0) {

				String fileName = Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();
				String uploadDirectory = "C:/upload2"; // Thư mục lưu ảnh trên ổ E
				File uploadDir = new File(uploadDirectory);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}

				// Lưu ảnh vào thư mục upload2
				String filePath = uploadDirectory + File.separator + fileName;
				avatarPart.write(filePath);

				AccountModel acc = new AccountModel(email, fileName);

				accountService.updateAvatar(acc);
			}
			resp.sendRedirect(req.getContextPath() + "/account?email=" + email);
		}

		if (url.contains("/account/updateAccount")) {
			 String email = req.getParameter("email");
			    String fullNameRaw = req.getParameter("fullName");
			    String phoneRaw = req.getParameter("phone");

			    // Kiểm tra XSS
			    if (hasXSSRisk(fullNameRaw) || hasXSSRisk(phoneRaw)) {
			        req.setAttribute("errorMessage", "Dữ liệu nhập không hợp lệ, chứa ký tự nguy hiểm.");
			        req.setAttribute("account", accountService.findByUserName(email));
			        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
			        return; // Dừng xử lý
			    }

			    // Nếu không có XSS thì sanitize rồi lưu
			    String name = sanitize(fullNameRaw);
			    String phone = sanitize(phoneRaw);

			AccountModel acc = new AccountModel(name, phone, email);

			System.out.println(email);

			accountService.updateAccount(acc);
			resp.sendRedirect(req.getContextPath() + "/account?email=" + email);
		}

		if (url.contains("/account/updateCoverImage")) {
			String email = req.getParameter("email");
			System.out.println(email);

			Part avatarPart = req.getPart("cover_image");

			if (avatarPart != null && avatarPart.getSize() > 0) {

				String fileName = Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();
				String uploadDirectory = "E:/upload2"; // Thư mục lưu ảnh trên ổ E
				File uploadDir = new File(uploadDirectory);
				if (!uploadDir.exists()) {
					uploadDir.mkdirs();
				}

				// Lưu ảnh vào thư mục upload2
				String filePath = uploadDirectory + File.separator + fileName;
				avatarPart.write(filePath);

				AccountModel acc = new AccountModel();
				acc.setEmail(email);
				acc.setCover_image(fileName);

				accountService.updateCoverImage(acc);
			}
			resp.sendRedirect(req.getContextPath() + "/account?email=" + email);
		} else if (url.contains("/account/addAddress")) {
			 String email = req.getParameter("email");
			    String provinceRaw = req.getParameter("province");
			    String districtRaw = req.getParameter("district");
			    String wardsRaw = req.getParameter("wards");
			    String detailRaw = req.getParameter("detail");
			    String phoneRaw = req.getParameter("phone");

			    if (hasXSSRisk(provinceRaw) || hasXSSRisk(districtRaw) || hasXSSRisk(wardsRaw) || hasXSSRisk(detailRaw) || hasXSSRisk(phoneRaw)) {
			        req.setAttribute("errorMessage", "Dữ liệu nhập không hợp lệ, chứa ký tự nguy hiểm.");
			        req.setAttribute("account", accountService.findByUserName(email));
			        req.setAttribute("address", addressService.findAllById(email));
			        req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);
			        return;
			    }

			    // Nếu không có XSS thì sanitize
			    String province = sanitize(provinceRaw);
			    String district = sanitize(districtRaw);
			    String wards = sanitize(wardsRaw);
			    String detail = sanitize(detailRaw);
			    String phone = sanitize(phoneRaw);

			AddressModel address = new AddressModel(email, province, district, wards, detail, phone);

			addressService.insert(address);

			resp.sendRedirect(req.getContextPath() + "/account?email=" + email);

		}

		if (url.contains("/account/deleteAddress")) {
			String email = req.getParameter("email");
			int addressId = Integer.parseInt(req.getParameter("id"));
			System.out.println("Email: " + email);
			System.out.println("Address ID: " + addressId);

			addressService.delete(addressId); // Gọi service để xóa địa chỉ

			resp.sendRedirect(req.getContextPath() + "/account?email=" + email); // Redirect về trang profile
		}

	}
}
