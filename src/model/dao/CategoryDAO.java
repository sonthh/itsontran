package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Category;
import util.DBConnectionUtil;
import util.DefineUtil;

public class CategoryDAO {
	private ResultSet rs;
	private Statement st;
	private PreparedStatement pst;
	private Connection conn;

	public static void main(String[] args) {
		CategoryDAO dao = new CategoryDAO();
		ArrayList<Category> list = dao.getItemsAllAndSort();
		for (Category item : list) {
			System.out.println(item);
		}
	}

	/***************************************************************************/
	public ArrayList<Category> getItemsAll(int parentId) {
		ArrayList<Category> result = new ArrayList<>();
		ArrayList<Category> list = this.getItemsByParentId(parentId);
		for (Category item : list) {
			result.add(item);
			result.addAll(this.getItemsAll(item.getId()));
		}
		return result;
	}
	
	public ArrayList<Category> getItemsAllAndSort() {
		return this.getItemsAll(0);
	}

	/***************************************************************************/

	public Category getItem(int id) {
		Category category = null;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM cat_list WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				category = new Category(rs.getInt("id"), rs.getString("name"), rs.getInt("parent_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return category;
	}

	public ArrayList<Category> getItems() {
		ArrayList<Category> listCategories = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM cat_list";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				listCategories.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getInt("parent_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, st, conn);
		}
		return listCategories;
	}

	public ArrayList<Category> getItemsByParentId(int id) {
		ArrayList<Category> listCategories = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM cat_list WHERE parent_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				listCategories.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getInt("parent_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listCategories;
	}

	public int countItems() {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM cat_list WHERE parent_id = 0";
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return result;
	}

	public ArrayList<Category> getItemsPaginaiton(int offset) {
		ArrayList<Category> listCategories = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM cat_list WHERE parent_id = ? LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, 0);
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listCategories.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getInt("parent_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listCategories;
	}

	public int countItemsSearch(String name) {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM cat_list WHERE name LIKE ? AND parent_id = 0";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + name + "%");
			rs = pst.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return count;
	}

	public ArrayList<Category> getItemsSearchPaginaiton(String name, int offset) {
		ArrayList<Category> listCategories = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM cat_list WHERE name LIKE ? AND parent_id = 0 LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + name + "%");
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listCategories.add(new Category(rs.getInt("id"), rs.getString("name"), rs.getInt("parent_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listCategories;
	}

	public int addItem(Category category) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO cat_list(name, parent_id) VALUES(?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, category.getName());
			pst.setInt(2, category.getParentCategoryId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	/* use in deleteParentItemAndSubItems method */
	private int delItem(int id) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "DELETE FROM cat_list WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public int deleteParentItemAndSubItems(int parentId) {
		int result = 0;
		ArrayList<Category> listCategories = this.getItemsByParentId(parentId);
		for (Category category : listCategories) {
			result += this.deleteParentItemAndSubItems(category.getId());
		}
		result += this.delItem(parentId);
		return result;
	}

	public int editItem(Category category) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE cat_list SET name = ?, parent_id = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, category.getName());
			pst.setInt(2, category.getParentCategoryId());
			pst.setInt(3, category.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public int countItemsAll() {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM cat_list";
		try {
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return result;
	}
}
