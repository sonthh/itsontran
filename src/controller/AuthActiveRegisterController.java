package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.UserDAO;

public class AuthActiveRegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;

	public AuthActiveRegisterController() {
		super();
		userDAO = new UserDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("u");
		String password = request.getParameter("p");
		System.out.println(username + "-" + password);
		boolean check = userDAO.hasUser(username, password);
		PrintWriter out = response.getWriter();
		if (check) {
			if (userDAO.changeStatusItem(username, password, 1) > 0) {
				System.out.println("active register successful");

				out.println("Active <span style='color: red'>" + username + "</span>" + " successful. Go to <a href='"
						+ request.getContextPath() + "/login'>login</a>");
			} else {
				System.out.println("active register fail: error query db");
				out.println("Active <span style='color: red'>" + username + "</span>" + " fail.");
			}
		} else {
			out.println("Active <span style='color: red'>" + username + "</span>" + " fail.");
		}
		/*
		 * RequestDispatcher rd = request.getRequestDispatcher("/auth/register.jsp");
		 * rd.forward(request, response);
		 */
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
