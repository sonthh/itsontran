package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.User;
import model.dao.CommentDAO;
import model.dao.UserDAO;

public class AdminDelUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	private CommentDAO commentDAO;

	public AdminDelUserController() {
		super();
		userDAO = new UserDAO();
		commentDAO = new CommentDAO();
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
		// check lỗi id ko tồn tại
		User user = userDAO.getItem(id);
		if (user == null || user.getUserPermissions().getName().equals("admin")) {
			response.sendRedirect(request.getContextPath() + "/admin/users?msg=5&page=" + currentPage);
			return;
		}

		if (userDAO.deleteItem(id) > 0) {
			//xóa những comment của user này
			commentDAO.deleteItemByUserId(id);
			
			response.sendRedirect(request.getContextPath() + "/admin/users?msg=3&page=" + currentPage);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/users?msg=0&page=" + currentPage);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
