package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.User;
import model.bean.UserPermissions;
import model.dao.UserDAO;
import util.StringUtil;

public class AdminAddUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

	public AdminAddUserController() {
		super();
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/admin/user/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		if (userDAO.hasUser(username)) {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/user/add.jsp?msg=0");
			rd.forward(request, response);
			return;
		}

		String password = StringUtil.md5(request.getParameter("password"));
		String fullname = request.getParameter("fullname");
		int userPermissionsId = 3;
		try {
			userPermissionsId = Integer.parseInt(request.getParameter("user-permissions"));
		} catch (NumberFormatException e) {
		}
		
		User user = new User(0, username, password, fullname, 1, new UserPermissions(userPermissionsId, null));
		if (userDAO.addItem(user) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/users?msg=1");
			return;
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/user/add.jsp?msg=0");
			rd.forward(request, response);
		}
	}

}
