package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Contact;
import model.dao.ContactDAO;

public class AdminDelContactController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ContactDAO contactDAO;

	public AdminDelContactController() {
		super();
		contactDAO = new ContactDAO();
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
			response.sendRedirect(request.getContextPath() + "/admin/contacts?msg=0&page=" + currentPage);
			return;
		}
		// check lỗi id ko tồn tại
		Contact contact = contactDAO.getItem(id);
		if (contact == null) {
			response.sendRedirect(request.getContextPath() + "/admin/contacts?msg=5&page=" + currentPage);
			return;
		}

		if (contactDAO.deleteItem(id) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/contacts?msg=3&page=" + currentPage);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/contacts?msg=0&page=" + currentPage);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
