package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.bean.Slide;
import model.dao.SlideDAO;
import util.DefineUtil;

public class AdminDelSlideController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SlideDAO slideDAO;

	public AdminDelSlideController() {
		super();
		slideDAO = new SlideDAO();
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
			response.sendRedirect(request.getContextPath() + "/admin/slides?msg=0&page=" + currentPage);
			return;
		}
		// check lỗi id ko tồn tại
		Slide slide = slideDAO.getItem(id);
		if (slide == null) {
			response.sendRedirect(request.getContextPath() + "/admin/slides?msg=0&page=" + currentPage);
			return;
		}

		if (slideDAO.deleteItem(id) > 0) {
			String picture = slide.getPicture();
			if (!"".equals(picture)) {
				String path = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD + File.separator + picture;
				File file = new File(path);
				file.delete();
			}
			response.sendRedirect(request.getContextPath() + "/admin/slides?msg=3&page=" + currentPage);
			return;
		} else {
			response.sendRedirect(request.getContextPath() + "/admin/slides?msg=0&page=" + currentPage);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}
