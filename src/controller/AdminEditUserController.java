package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.User;
import model.dao.UserDAO;
import util.StringUtil;

public class AdminEditUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

	public AdminEditUserController() {
		super();
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}

		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/users?msg=0&page=" + currentPage);
			return;
		}

		User user = userDAO.getItem(id);
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/admin/users?msg=0&page=" + currentPage);
			return;
		}
		request.setAttribute("user", user);

		RequestDispatcher rd = request.getRequestDispatcher("/admin/user/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}

		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/users?msg=0&page=" + currentPage);
			return;
		}

		User user = userDAO.getItem(id);
		if (user == null) {
			response.sendRedirect(request.getContextPath() + "/admin/users?msg=0&page=" + currentPage);
			return;
		}

		// lấy dữ liệu từ form
		String password = request.getParameter("password");
		String fullname = request.getParameter("fullname");
		int userPermissionsId = 3;
		try {
			userPermissionsId = Integer.parseInt(request.getParameter("user-permissions"));
		} catch (NumberFormatException e) {
		}

		if (!"".equals(password)) {
			user.setPassword(StringUtil.md5(password));
		}
		user.setFullname(fullname);
		user.getUserPermissions().setId(userPermissionsId);

		/*
		 * HttpSession session = request.getSession(); User userInfo = (User)
		 * session.getAttribute("userInfo");
		 */
		/* admin hoặc chính chủ mới sửa được thông tin user */
		/*
		 * if (userInfo.getUsername().equals("admin") ||
		 * userInfo.getUsername().equals(user.getUsername())) {
		 */
		// dao
		if (userDAO.editItem(user) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/users?msg=2&page=" + currentPage);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/users?msg=0&page=" + currentPage);
			return;
		}
		/*
		 * } else { response.sendRedirect(request.getContextPath() +
		 * "/admin/users?page=" + currentPage); return; }
		 */
	}

}
