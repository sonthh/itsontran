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
public class AdminAddSlideController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private SlideDAO slideDAO;

	public AdminAddSlideController() {
		super();
		slideDAO = new SlideDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/admin/slide/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("name");
		String link = request.getParameter("link");
		int sort = Integer.parseInt(request.getParameter("sort"));
		Part part = request.getPart("image");
		String fileName = FileUtil.rename(part.getSubmittedFileName());
		
		Slide slide = new Slide(0, name, fileName, link, sort, 1);
		if (slideDAO.addItem(slide) > 0) {
			if (!"".equals(fileName)) {
				String dirPath = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD;
				File file = new File(dirPath);
				if (!file.exists()) {
					file.mkdir();
				}
				part.write(dirPath + File.separator + fileName);
			}
			response.sendRedirect(request.getContextPath() + "/admin/slides?msg=1");
			return;
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/slide/add.jsp?msg=0");
			rd.forward(request, response);
		}
	}

}
