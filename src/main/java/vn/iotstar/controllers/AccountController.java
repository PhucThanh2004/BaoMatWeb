package vn.iotstar.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import vn.iotstar.models.AccountModel;
import vn.iotstar.models.AddressModel;
import vn.iotstar.service.IAccountService;
import vn.iotstar.service.IAddressService;
import vn.iotstar.service.impl.AccountServiceImpl;
import vn.iotstar.service.impl.AddressServiceImpl;
import vn.iotstar.utils.Constant;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
@WebServlet(urlPatterns = { "/account", "/account/updateAvatar", "/account/updateAccount", "/account/updateCoverImage",
        "/account/addAddress", "/account/deleteAddress" })
public class AccountController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public IAccountService accountService = new AccountServiceImpl();
    public IAddressService addressService = new AddressServiceImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();

        // Lấy email từ session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Vui lòng đăng nhập!");
            return;
        }
        String sessionEmail = (String) session.getAttribute("email");

        // Tạo token CSRF và lưu vào session
        String csrfToken = generateCsrfToken();
        session.setAttribute("csrf_token", csrfToken);

        if (url.contains("/account")) {
            // Kiểm tra tham số email từ URL
            String paramEmail = req.getParameter("email");
            if (paramEmail != null && !paramEmail.equals(sessionEmail)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền truy cập tài khoản này!");
                return;
            }

            // Lấy thông tin tài khoản từ sessionEmail
            AccountModel acc = accountService.findByUserName(sessionEmail);
            if (acc == null) {
                resp.sendRedirect(req.getContextPath() + "/login?error=Tài khoản không tồn tại");
                return;
            }
            List<AddressModel> addressList = addressService.findAllById(sessionEmail);

            req.setAttribute("account", acc);
            req.setAttribute("address", addressList);
            req.getRequestDispatcher("/views/profile.jsp").forward(req, resp);

        }else if (url.contains("/account/deleteAddress")) {
            // Kiểm tra CSRF token
            String csrfTokenParam = req.getParameter("csrf_token");
            String sessionToken = (String) session.getAttribute("csrf_token");
            if (csrfTokenParam == null || !csrfTokenParam.equals(sessionToken)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token không hợp lệ!");
                return;
            }

            int addressId = Integer.parseInt(req.getParameter("id"));

            // Kiểm tra quyền sở hữu địa chỉ
            AddressModel address = addressService.findByIdAddress(addressId);
            if (address == null || !address.getEmail().equals(sessionEmail)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền xóa địa chỉ này!");
                return;
            }

            addressService.delete(addressId);
            resp.sendRedirect(req.getContextPath() + "/account");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String url = req.getRequestURI();

        // Lấy email từ session
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Vui lòng đăng nhập!");
            return;
        }
        String sessionEmail = (String) session.getAttribute("email");

        // Kiểm tra CSRF token
        String csrfToken = req.getParameter("csrf_token");
        String sessionToken = (String) session.getAttribute("csrf_token");
        if (csrfToken == null || !csrfToken.equals(sessionToken)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "CSRF token không hợp lệ!");
            return;
        }

        if (url.contains("/account/updateAvatar")) {
            Part avatarPart = req.getPart("avatar");

            if (avatarPart != null && avatarPart.getSize() > 0) {
                String fileName = Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();
                String uploadDirectory = "C:/upload2";
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String filePath = uploadDirectory + File.separator + fileName;
                avatarPart.write(filePath);

                AccountModel acc = new AccountModel(sessionEmail, fileName);
                accountService.updateAvatar(acc);
            }
            resp.sendRedirect(req.getContextPath() + "/account");

        } else if (url.contains("/account/updateAccount")) {
            String name = req.getParameter("fullName");
            String phone = req.getParameter("phone");

            AccountModel acc = new AccountModel(name, phone, sessionEmail);
            accountService.updateAccount(acc);
            resp.sendRedirect(req.getContextPath() + "/account");

        } else if (url.contains("/account/updateCoverImage")) {
            Part avatarPart = req.getPart("cover_image");

            if (avatarPart != null && avatarPart.getSize() > 0) {
                String fileName = Paths.get(avatarPart.getSubmittedFileName()).getFileName().toString();
                String uploadDirectory = "E:/upload2";
                File uploadDir = new File(uploadDirectory);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String filePath = uploadDirectory + File.separator + fileName;
                avatarPart.write(filePath);

                AccountModel acc = new AccountModel();
                acc.setEmail(sessionEmail);
                acc.setCover_image(fileName);
                accountService.updateCoverImage(acc);
            }
            resp.sendRedirect(req.getContextPath() + "/account");

        } else if (url.contains("/account/addAddress")) {
            String province = req.getParameter("province");
            String district = req.getParameter("district");
            String wards = req.getParameter("wards");
            String detail = req.getParameter("detail");
            String phone = req.getParameter("phone");

            AddressModel address = new AddressModel(sessionEmail, province, district, wards, detail, phone);
            addressService.insert(address);
            resp.sendRedirect(req.getContextPath() + "/account");

        } else if (url.contains("/account/deleteAddress")) {
            int addressId = Integer.parseInt(req.getParameter("id"));

            // Kiểm tra quyền sở hữu địa chỉ
            AddressModel address = addressService.findByIdAddress(addressId);
            if (address == null || !address.getEmail().equals(sessionEmail)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Không có quyền xóa địa chỉ này!");
                return;
            }

            addressService.delete(addressId);
            resp.sendRedirect(req.getContextPath() + "/account");
        }
    }

    // Phương thức tạo token CSRF ngẫu nhiên
    private String generateCsrfToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[16];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}