package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Contact;
import model.dao.ContactDAO;

public class PublicContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContactDAO contactDAO;

	public PublicContactController() {
		super();
		contactDAO = new ContactDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/public/contact.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		
		Contact contact = new Contact(0, name, email, subject, message);
		PrintWriter out = response.getWriter();
		if (contactDAO.addItem(contact) > 0) {
			out.print("Add contact successful");
		} else {
			out.print("Have a error");
		}
	}

}
