package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.dao.UserDAO;

public class AdminCheckExistUserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
       
    public AdminCheckExistUserController() {
        super();
        userDAO = new UserDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AdminCheckExistUserController.doPost()");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("ausername");
		if (userDAO.hasUser(username)) {
			out.print(1);
			System.out.println(1);
		} else {
			out.print(0);
			System.out.println(0);
		}
	}

}
