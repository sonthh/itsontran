package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Category;
import model.dao.CategoryDAO;

public class AdminAddCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;
       
    public AdminAddCatController() {
        super();
        categoryDAO = new CategoryDAO();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}*/
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			/*response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0&page=" + currentPage);
			return;*/
		}
		//nếu không có danh mục cha thì id = 0
		Category category = categoryDAO.getItem(id);
		/*if (category == null) {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0");
			return;
		}*/
		request.setAttribute("category", category);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/cat/add.jsp?page=");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
		}
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("parent-category"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0&page=" + currentPage);
			return;
		}
		
		String name = request.getParameter("name");
		if (name == null || "".equals(name)) {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0");
			return;
		}
		
		//nếu không có danh mục cha thì id cha = 0
		Category category = new Category(0, name, id);
		if (categoryDAO.addItem(category) > 0) {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=1&page=" + currentPage);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/categories?msg=0" + currentPage);
			return;
		}
	}

}
