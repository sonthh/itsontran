package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.DefineUtil;

public class AuthLogoutController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AuthLogoutController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute(DefineUtil.USER_LOGIN) != null) {
			session.removeAttribute(DefineUtil.USER_LOGIN);
			session.removeAttribute("timeLogin");
		}
		Cookie[] arCookies = request.getCookies();
		for (Cookie cookie : arCookies) {
			if (cookie.getName().equals("userCookie")) {
				cookie.setMaxAge(0);
				response.addCookie(cookie);
				break;
			}
		}
		response.sendRedirect(request.getContextPath() + "/");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
