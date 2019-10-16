package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.bean.Slide;
import model.dao.SlideDAO;
import util.DefineUtil;
import util.FileUtil;

@MultipartConfig
public class AdminEditSlideController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SlideDAO slideDAO;

	public AdminEditSlideController() {
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
		//id không tồn tại
		Slide slide = slideDAO.getItem(id);
		if (slide == null) {
			response.sendRedirect(request.getContextPath() + "/admin/slides?msg=0&page=" + currentPage);
			return;
		}
		
		request.setAttribute("slide", slide);
		RequestDispatcher rd = request.getRequestDispatcher("/admin/slide/edit.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
		//id không tồn tại
		Slide slide = slideDAO.getItem(id);
		if (slide == null) {
			response.sendRedirect(request.getContextPath() + "/admin/slides?msg=0&page=" + currentPage);
			return;
		}
		String oldFileName = slide.getPicture();
		
		// get param in form
		String name = request.getParameter("name");
		String link = request.getParameter("link");
		int sort = Integer.parseInt(request.getParameter("sort"));
		// get filename from part
		Part part = request.getPart("image");
		String fileName = FileUtil.rename(part.getSubmittedFileName());
		slide.setName(name);
		slide.setLink(link);
		slide.setSort(sort);
		if (!"".equals(fileName)) {
			slide.setPicture(fileName);
		}
		
		if (slideDAO.editItem(slide) > 0) {
			if (!"".equals(fileName)) {
				// xóa file cũ
				String path = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD + File.separator
						+ oldFileName;
				File oldFile = new File(path);
				oldFile.delete();

				// up file mới
				String dirPath = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD;
				File newFile = new File(dirPath);
				if (!newFile.exists()) {
					newFile.mkdir();
				}
				part.write(dirPath + File.separator + fileName);
			} 
			response.sendRedirect(request.getContextPath() + "/admin/slides?msg=2&page=" + currentPage);
			return;
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/slide/edit.jsp?msg=0");
			rd.forward(request, response);
		}
	}

}
