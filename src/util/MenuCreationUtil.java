package util;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;

import model.bean.Category;
import model.dao.CategoryDAO;

public class MenuCreationUtil {
	private static CategoryDAO categoryDAO = new CategoryDAO();

	/* Tạo ra menu con parentId là menu cha */
	private static void createChildMenu(int parentId, JspWriter out, HttpServletRequest request) {
		ArrayList<Category> listCategories = categoryDAO.getItemsByParentId(parentId);
		try {
			if (listCategories.size() > 0) {
				out.println("<ul class='nav-menu'>");
				for (Category category : listCategories) {
					out.println("<li>");
					String href = request.getContextPath() + "/category/" + StringUtil.makeSlug(category.getName()) + "-" + category.getId();
					out.println("<a href='" + href + "'>" + category.getName() + "</a>");
					createChildMenu(category.getId(), out, request);
				}
				out.println("</ul>");
			} else {
				out.println("</li>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* create full menu */
	public static void createMenu(JspWriter out, HttpServletRequest request) {
		/* Tìm tất cả menu cấp 0 tức là parent id = 0 */
		ArrayList<Category> listCategories = categoryDAO.getItemsByParentId(0);
		try {
			out.println("<ul class='nav-menu'>");

			for (Category category : listCategories) {
				out.println("<li>");
				// String href = request.getContextPath() + "/category?id=" + category.getId();
				String href = request.getContextPath() + "/category/" + StringUtil.makeSlug(category.getName()) + "-" + category.getId();
				out.println("<a href='" + href + "'>" + category.getName() + "</a>");
				/*
				 * Tạo ra những menu con trong cái menu cấp 0 này rồi qua createChildMenu đệ qui
				 */
				createChildMenu(category.getId(), out, request);
			}
			out.println("</ul>");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*--------------------------------------------------------------------------------------------------------------------*/

	private static void createChildMenuStyle(JspWriter out, HttpServletRequest request, int parentId, int currentPage) {
		ArrayList<Category> listCategories = categoryDAO.getItemsByParentId(parentId);
		try {
			if (listCategories.size() > 0) {
				out.println("<ul class='nav-menu'>");
				for (Category category : listCategories) {
					String urlAdd = request.getContextPath() + "/admin/category/add?id=" + category.getId() + "&page="
							+ currentPage;
					String urlEdit = request.getContextPath() + "/admin/category/edit?id=" + category.getId() + "&page="
							+ currentPage;
					String urlDel = request.getContextPath() + "/admin/category/del?id=" + category.getId() + "&page="
							+ currentPage;
					out.println("<li>");
					out.println("<span>" + category.getName() + "</span>");
					out.println("<span class='controls'>");
					out.println("<a href='" + urlAdd + "'><i class='text-primary fas fa-plus'></i></a>");
					out.println("<a href='" + urlEdit + "'><i class='text-info fas fa-edit'></i></a>");
					String confirm = " onclick=\"return confirm('Are you delete " + category.getName() + "')\"";
					out.println(
							"<a href='" + urlDel + "'" + confirm + "><i class='text-danger fas fa-trash-alt'></i></a>");
					out.println("</span>");
					createChildMenuStyle(out, request, category.getId(), currentPage);
				}
				out.println("</ul>");
			} else {
				out.println("</li>");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* create menu item */
	public static void createMenuItem(JspWriter out, HttpServletRequest request, int idCategory, int currentPage) {
		/* Tìm tất cả menu cấp 0 tức là parent id = 0 */
		Category category = categoryDAO.getItem(idCategory);
		if (category == null || category.getParentCategoryId() != 0)
			return;
		try {
			out.println("<ul class='nav-menu'>");

			if (category.getParentCategoryId() == 0) {
				String urlAdd = request.getContextPath() + "/admin/category/add?id=" + category.getId() + "&page="
						+ currentPage;
				String urlEdit = request.getContextPath() + "/admin/category/edit?id=" + category.getId() + "&page="
						+ currentPage;
				String urlDel = request.getContextPath() + "/admin/category/del?id=" + category.getId() + "&page="
						+ currentPage;
				out.println("<li>");
				out.println("<span>" + category.getName() + "</span>");
				out.println("<span class='controls'>");
				out.println("<a href='" + urlAdd + "'><i class='text-primary fas fa-plus'></i></a>");
				out.println("<a href='" + urlEdit + "'><i class='text-info fas fa-edit'></i></a>");
				String confirm = " onclick=\"return confirm('Are you delete " + category.getName() + "')\"";
				out.println("<a href='" + urlDel + "'" + confirm + "><i class='text-danger fas fa-trash-alt'></i></a>");
				out.println("</span>");
				/*
				 * Tạo ra những menu con trong cái menu cấp 0 này rồi qua createChildMenu đệ qui
				 */
				createChildMenuStyle(out, request, category.getId(), currentPage);
			}
			out.println("</ul>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*--------------------------------------------------------------------------------------------------------------------*/

	/* Tạo ra menu con parentId là menu cha */
	private static void createChildOption(int parentId, JspWriter out, int level, int idSelected, int idDisable) {
		ArrayList<Category> listCategories = categoryDAO.getItemsByParentId(parentId);
		try {
			if (listCategories.size() > 0) {
				for (Category category : listCategories) {
					String style = "style='font-size: " + (1.0 - (level * 0.05)) + "rem;'";
					if (idSelected == category.getId())
						out.println("<option " + style + " selected value=" + category.getId() + ">");
					else if (idDisable == category.getId()) {
						out.println("<option " + style + " disabled value=" + category.getId() + ">");
						for (int i = 0; i < level; i++) {
							out.print("&raquo;");
						}
						out.println(category.getName());
						out.print("</option>");
						continue;
					} else
						out.println("<option " + style + " value=" + category.getId() + ">");
					for (int i = 0; i < level; i++) {
						out.print("&raquo;");
					}
					out.println(category.getName());
					out.print("</option>");
					createChildOption(category.getId(), out, level + 1, idSelected, idDisable);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* create full menu */
	public static void createOption(JspWriter out, int idSelected, int idDisable) {
		/* Tìm tất cả menu cấp 0 tức là parent id = 0 */
		ArrayList<Category> listCategories = categoryDAO.getItemsByParentId(0);
		try {

			for (Category category : listCategories) {
				if (idSelected == category.getId())
					out.println("<option selected value=" + category.getId() + ">");
				else if (idDisable == category.getId())
					out.println("<option disabled value=" + category.getId() + ">");
				else
					out.println("<option value=" + category.getId() + ">");
				out.println(category.getName());
				out.print("</option>");
				/*
				 * Tạo ra những menu con trong cái menu cấp 0 này rồi qua createChildMenu đệ qui
				 */
				createChildOption(category.getId(), out, 1, idSelected, idDisable);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
