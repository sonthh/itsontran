package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Category;
import model.dao.CategoryDAO;

public class AdminEditCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;

	public AdminEditCatController() {
		super();
		categoryDAO = new CategoryDAO();
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
		}

		Category category = categoryDAO.getItem(id);
		if (category == null) {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0&page=" + currentPage);
			return;
		}
		request.setAttribute("category", category);

		RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}

		// id của danh mục muốn sửa
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0&page=" + currentPage);
			return;
		}

		//name lấy từ người dùng
		String name = request.getParameter("name");
		if (name == null || "".equals(name)) {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0");
			return;
		}

		// id của danh mục cha lấy từ người dùng
		int parentCategoryId = 0;
		try {
			parentCategoryId = Integer.parseInt(request.getParameter("parent-category"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0&page=" + currentPage);
			return;
		}
		
		Category category = categoryDAO.getItem(id);
		if (category == null) {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0&page=" + currentPage);
			return;
		}

		category.setName(name);
		category.setParentCategoryId(parentCategoryId);
		if (categoryDAO.editItem(category) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=2&page=" + currentPage);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0&page=" + currentPage);
			return;
		}
	}

}
