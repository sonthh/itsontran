package controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import model.bean.Category;
import model.bean.News;
import model.bean.User;
import model.dao.CategoryDAO;
import model.dao.NewsDAO;
import util.AuthUtil;
import util.DefineUtil;
import util.FileUtil;

@MultipartConfig
public class AdminAddNewsController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NewsDAO newsDAO;

	public AdminAddNewsController() {
		super();
		newsDAO = new NewsDAO();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/admin/news/add.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get param in form
		String name = request.getParameter("name");
		int catId = Integer.parseInt(request.getParameter("category"));
		String preview = request.getParameter("preview");
		String detail = request.getParameter("detail");
		// get filename from part
		Part part = request.getPart("image");
		String fileName = FileUtil.rename(part.getSubmittedFileName());
		User userLogin = AuthUtil.getUserLogin(request);
		Category category = new CategoryDAO().getItem(catId);
		News news = new News(0, name, preview, detail, new Timestamp(new Date().getTime()), userLogin.getId(), fileName,
				category, 1);

		if (newsDAO.addItem(news) > 0) {
			// handle upload file
			if (!"".equals(fileName)) {
				String dirPath = request.getServletContext().getRealPath("") + DefineUtil.DIR_UPLOAD;
				File file = new File(dirPath);
				if (!file.exists()) {
					file.mkdir();
				}
				part.write(dirPath + File.separator + fileName);
			}

			response.sendRedirect(request.getContextPath() + "/admin/newses?msg=1");
			return;
		} else {
			RequestDispatcher rd = request.getRequestDispatcher("/admin/news/add.jsp?msg=0");
			rd.forward(request, response);
			return;
		}
	}

}
