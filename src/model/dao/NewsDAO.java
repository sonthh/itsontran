package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.bean.Category;
import model.bean.News;
import util.DBConnectionUtil;
import util.DefineUtil;

public class NewsDAO {
	private ResultSet rs;
	private Statement st;
	private PreparedStatement pst;
	private Connection conn;

	public static void main(String[] args) {
		//NewsDAO dao = new NewsDAO();

	}

	public int countItems() {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(id) AS count FROM news";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, st, conn);
		}
		return count;
	}

	public ArrayList<News> getItemsPaginaiton(int offset) {
		ArrayList<News> listNewses = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, c.name AS c_name, c.parent_id AS c_parent_id FROM news AS n INNER JOIN cat_list AS c ON n.cat_id = c.id ORDER BY n.id DESC LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listNewses.add(
						new News(rs.getInt("id"), rs.getString("name"), rs.getString("preview"), rs.getString("detail"),
								rs.getTimestamp("date_create"), rs.getInt("created_by"), rs.getString("picture"),
								new Category(rs.getInt("cat_id"), rs.getString("c_name"), rs.getInt("c_parent_id")),
								rs.getInt("active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listNewses;
	}

	public int countItemsOfUser(int id) {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(id) AS count FROM news WHERE created_by = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				count = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, st, conn);
		}
		return count;
	}

	public ArrayList<News> getItemsPaginaitonOfUser(int offset, int id) {
		ArrayList<News> listNewses = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, c.name AS c_name, c.parent_id AS c_parent_id FROM news AS n INNER JOIN cat_list AS c ON n.cat_id = c.id WHERE created_by = ? ORDER BY n.id DESC LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listNewses.add(
						new News(rs.getInt("id"), rs.getString("name"), rs.getString("preview"), rs.getString("detail"),
								rs.getTimestamp("date_create"), rs.getInt("created_by"), rs.getString("picture"),
								new Category(rs.getInt("cat_id"), rs.getString("c_name"), rs.getInt("c_parent_id")),
								rs.getInt("active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listNewses;
	}

	public int changeStatusItem(int id, int statusChange, Timestamp dateCreate) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE news SET active = ?, date_create = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, statusChange);
			pst.setTimestamp(2, dateCreate);
			pst.setInt(3, id);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, st, conn);
		}
		return result;
	}

	public News getItem(int id) {
		News news = null;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, c.name AS c_name, c.parent_id AS c_parent_id FROM news AS n INNER JOIN cat_list AS c ON n.cat_id = c.id WHERE n.id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				news = new News(rs.getInt("id"), rs.getString("name"), rs.getString("preview"), rs.getString("detail"),
						rs.getTimestamp("date_create"), rs.getInt("created_by"), rs.getString("picture"),
						rs.getInt("views"),
						new Category(rs.getInt("cat_id"), rs.getString("c_name"), rs.getInt("c_parent_id")),
						rs.getInt("active"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return news;
	}

	public int countItemsSearch(String name) {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(id) AS count FROM news WHERE name LIKE ?";
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

	public int countItemsSearchOfUser(String name, int id) {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(id) AS count FROM news WHERE name LIKE ? AND created_by = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + name + "%");
			pst.setInt(2, id);
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

	public ArrayList<News> getItemsSearchPaginaiton(String name, int offset) {
		ArrayList<News> listNewses = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, c.name AS c_name, c.parent_id AS c_parent_id FROM news AS n INNER JOIN cat_list AS c ON n.cat_id = c.id WHERE n.name LIKE ? ORDER BY n.id DESC LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + name + "%");
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listNewses.add(
						new News(rs.getInt("id"), rs.getString("name"), rs.getString("preview"), rs.getString("detail"),
								rs.getTimestamp("date_create"), rs.getInt("created_by"), rs.getString("picture"),
								new Category(rs.getInt("cat_id"), rs.getString("c_name"), rs.getInt("c_parent_id")),
								rs.getInt("active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listNewses;
	}

	public ArrayList<News> getItemsSearchPaginaitonOfUser(String name, int id, int offset) {
		ArrayList<News> listNewses = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, c.name AS c_name, c.parent_id AS c_parent_id FROM news AS n INNER JOIN cat_list AS c ON n.cat_id = c.id WHERE n.name LIKE ? AND n.created_by = ? ORDER BY n.id DESC LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + name + "%");
			pst.setInt(2, id);
			pst.setInt(3, offset);
			pst.setInt(4, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listNewses.add(
						new News(rs.getInt("id"), rs.getString("name"), rs.getString("preview"), rs.getString("detail"),
								rs.getTimestamp("date_create"), rs.getInt("created_by"), rs.getString("picture"),
								new Category(rs.getInt("cat_id"), rs.getString("c_name"), rs.getInt("c_parent_id")),
								rs.getInt("active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listNewses;
	}

	public int addItem(News news) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO news(name, preview, detail, date_create, created_by, picture, cat_id, active)"
				+ " VALUE(?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, news.getName());
			pst.setString(2, news.getPreview());
			pst.setString(3, news.getDetail());
			pst.setTimestamp(4, news.getDateCreate());
			pst.setInt(5, news.getCreateBy());
			pst.setString(6, news.getPicture());
			pst.setInt(7, news.getCategory().getId());
			pst.setInt(8, 1);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public int editItem(News news) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE news SET name = ?, preview = ?, detail = ?, date_create = ?, picture = ?, cat_id = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, news.getName());
			pst.setString(2, news.getPreview());
			pst.setString(3, news.getDetail());
			pst.setTimestamp(4, news.getDateCreate());
			pst.setString(5, news.getPicture());
			pst.setInt(6, news.getCategory().getId());
			pst.setInt(7, news.getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public int deleteItem(int id) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "DELETE FROM news WHERE id = ?";
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

	public ArrayList<News> getLastedItems(int limit) {
		ArrayList<News> listNewses = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, c.name AS c_name, c.parent_id AS c_parent_id FROM news AS n INNER JOIN cat_list AS c ON n.cat_id = c.id ORDER BY n.date_create DESC LIMIT ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, limit);
			rs = pst.executeQuery();
			while (rs.next()) {
				listNewses.add(new News(rs.getInt("id"), rs.getString("name"), rs.getString("preview"),
						rs.getString("detail"), rs.getTimestamp("date_create"), rs.getInt("created_by"),
						rs.getString("picture"), rs.getInt("views"),
						new Category(rs.getInt("cat_id"), rs.getString("c_name"), rs.getInt("c_parent_id")),
						rs.getInt("active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listNewses;
	}

	public ArrayList<News> getPopularItems(int limit) {
		ArrayList<News> listNewses = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, c.name AS c_name, c.parent_id AS c_parent_id FROM news AS n INNER JOIN cat_list AS c ON n.cat_id = c.id ORDER BY n.views DESC LIMIT ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, limit);
			rs = pst.executeQuery();
			while (rs.next()) {
				listNewses.add(new News(rs.getInt("id"), rs.getString("name"), rs.getString("preview"),
						rs.getString("detail"), rs.getTimestamp("date_create"), rs.getInt("created_by"),
						rs.getString("picture"), rs.getInt("views"),
						new Category(rs.getInt("cat_id"), rs.getString("c_name"), rs.getInt("c_parent_id")),
						rs.getInt("active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listNewses;
	}

	public int countItemsByCatId(int id) {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM news WHERE cat_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
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

	
	//phương thức này chỉ lấy những tin mà chỉnh xác có id danh mục là id
	public ArrayList<News> getItemsByCatIdPagination(int id, int offset) {
		ArrayList<News> listNewses = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, c.name AS c_name, c.parent_id AS c_parent_id" + " FROM news AS n"
				+ " INNER JOIN cat_list AS c" + " ON n.cat_id = c.id" + " WHERE n.cat_id = ?"
				+ " ORDER BY n.date_create DESC" + " LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listNewses.add(new News(rs.getInt("id"), rs.getString("name"), rs.getString("preview"),
						rs.getString("detail"), rs.getTimestamp("date_create"), rs.getInt("created_by"),
						rs.getString("picture"), rs.getInt("views"),
						new Category(rs.getInt("cat_id"), rs.getString("c_name"), rs.getInt("c_parent_id")),
						rs.getInt("active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listNewses;
	}

	public int increaseView(int id) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE news SET views = views + 1 WHERE id = ?";
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

	public int deleteItemByCatId(int id) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "DELETE FROM news WHERE cat_id = ?";
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

	//chú ý khong join bảng cat_list và cho category bị null luôn
	public ArrayList<News> getItemsByCatId(int id) {
		ArrayList<News> listNewses = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM news  WHERE cat_id = ? ORDER BY id DESC";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				listNewses.add(new News(rs.getInt("id"), rs.getString("name"), rs.getString("preview"),
						rs.getString("detail"), rs.getTimestamp("date_create"), rs.getInt("created_by"),
						rs.getString("picture"), null, rs.getInt("active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listNewses;
	}

}
