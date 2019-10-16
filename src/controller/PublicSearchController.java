package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.News;
import model.dao.NewsDAO;
import util.DefineUtil;

public class PublicSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewsDAO newsDAO;

	public PublicSearchController() {
		super();
		newsDAO = new NewsDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		if (name == null || "".equals(name)) {
			response.sendRedirect(request.getContextPath() + "/");
			return;
		}
		name = name.trim();

		int numberOfItems = newsDAO.countItemsSearch(name);
		if (numberOfItems > 0) {
			int numberOfPages = (int) Math.ceil((float) numberOfItems / DefineUtil.NUMBER_PER_PAGE);
			int currentPage = 1;
			try {
				currentPage = Integer.parseInt(request.getParameter("page"));
			} catch (NumberFormatException e) {
			}
			if (currentPage < 1)
				currentPage = 1;
			else if (currentPage > numberOfPages)
				currentPage = numberOfPages;
			int offset = (currentPage - 1) * DefineUtil.NUMBER_PER_PAGE;

			ArrayList<News> listNewses = newsDAO.getItemsSearchPaginaiton(name, offset);
			request.setAttribute("listNewses", listNewses);

			request.setAttribute("numberOfPages", numberOfPages);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("numberOfItems", numberOfItems);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/public/search.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
