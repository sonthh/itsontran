package controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.bean.User;
import model.dao.UserDAO;
import util.AuthUtil;
import util.DefineUtil;
import util.StringUtil;

public class AuthLoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

	public AuthLoginController() {
		super();
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * if done login -> go to amdin page. else go to login page.
		 */
		if (AuthUtil.checkDoneLogin(request)) {
			response.sendRedirect(request.getContextPath() + "/admin");
			return;
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/auth/login.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = StringUtil.md5(request.getParameter("password"));

		User userLogin = userDAO.getItemByUsernamePasswordActive(username, password);

		/*
		 * if login correct -> go to admin page. else go to /login-incorrect. urlrewite
		 * login-incorrect -> /login?msg=0
		 */
		if (userLogin != null) {
			HttpSession session = request.getSession();
			session.setAttribute(DefineUtil.USER_LOGIN, userLogin);
			session.setAttribute("loginTime", new Date());
			String isRememberMe = request.getParameter("remember-me");
			/* Add cookie if remember me is checked and login succesful */
			// System.out.println("isRemember: " + isRememberMe);
			if (isRememberMe != null) {
				System.out.println("add cookie");
				Cookie cookie = new Cookie("userCookie", userLogin.getId() + "");
				cookie.setMaxAge(24 * 60 * 60);
				response.addCookie(cookie);
			}

			if (userLogin.getUserPermissions().getName().equals("user")) {
				response.sendRedirect(request.getContextPath() + "/");
				return;
			} else {
				response.sendRedirect(request.getContextPath() + "/admin");
				return;
			}
		} else {
			response.sendRedirect(request.getContextPath() + "/login-incorrect");
			return;
		}
	}

}
