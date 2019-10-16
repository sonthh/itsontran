package util;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.bean.User;
import model.dao.UserDAO;

public class AuthUtil {
	public static boolean checkDoneLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(DefineUtil.USER_LOGIN) != null) {
			return true;
		}
		Cookie[] arCookies = request.getCookies();
		for (Cookie cookie : arCookies) {
			if (cookie.getName().equals("userCookie") && cookie.getMaxAge() != 0) {
				//System.out.println("tìm thấy: " + cookie.getName() + "-" + cookie.getValue());
				UserDAO userDAO = new UserDAO();
				session.setAttribute(DefineUtil.USER_LOGIN, userDAO.getItem(Integer.parseInt(cookie.getValue())));
				session.setAttribute("loginTime", new Date());
				return true;
			}
		}
		return false;
	}

	public static User getUserLogin(HttpServletRequest request) {
		HttpSession session = request.getSession();
		if (session.getAttribute(DefineUtil.USER_LOGIN) == null) {
			return null;
		} else {
			return (User) session.getAttribute(DefineUtil.USER_LOGIN);
		}
	}
	public static User getUserLoginPublic(HttpServletRequest request) {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(DefineUtil.USER_LOGIN);
		if (user != null)
			return user;
		Cookie[] arCookies = request.getCookies();
		for (Cookie cookie : arCookies) {
			if (cookie.getName().equals("userCookie") && cookie.getMaxAge() != 0) {
				//System.out.println("tìm thấy: " + cookie.getName() + "-" + cookie.getValue());
				UserDAO userDAO = new UserDAO();
				return userDAO.getItem(Integer.parseInt(cookie.getValue()));
			}
		}
		return null;
	}
}
