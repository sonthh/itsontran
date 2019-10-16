package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Category;
import model.bean.News;
import model.dao.CategoryDAO;
import model.dao.NewsDAO;
import util.DefineUtil;

public class PublicCatController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CategoryDAO categoryDAO;
	private NewsDAO newsDAO;

	public PublicCatController() {
		super();
		categoryDAO = new CategoryDAO();
		newsDAO = new NewsDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int id = 0;
		try {
			id = Integer.parseInt(request.getParameter("id"));
		} catch (NumberFormatException e) {
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}

		int numberOfItems = newsDAO.countItemsByCatId(id);
		if (numberOfItems > 0) {
			int numberOfPages = (int) Math.ceil((float) numberOfItems / DefineUtil.NUMBER_PER_PAGE);
			int currentPage = 1;
			try {
				currentPage = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e) {
			}
			if (currentPage < 1 || currentPage > numberOfPages)
				currentPage = 1;
			int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE;

			request.setAttribute("numberOfPages", numberOfPages);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("numberOfItems", currentPage);

			ArrayList<News> listNewses = newsDAO.getItemsByCatIdPagination(id, offset);
			request.setAttribute("listNewses", listNewses);

		}
		Category category = categoryDAO.getItem(id);
		request.setAttribute("category", category);
		RequestDispatcher rd = request.getRequestDispatcher("/public/category.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
