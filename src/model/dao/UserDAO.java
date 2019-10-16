package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.User;
import model.bean.UserPermissions;
import util.DBConnectionUtil;
import util.DefineUtil;

public class UserDAO {
	private ResultSet rs;
	private Statement st;
	private PreparedStatement pst;
	private Connection conn;

	public static void main(String[] args) {
		UserDAO dao = new UserDAO();
		ArrayList<User> list = dao.getItemsPaginaiton(0);
		for (User item : list) {
			System.out.println(item);
		}
	}

	public ArrayList<User> getItemsPaginaiton(int offset) {
		ArrayList<User> listUsers = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, p.name AS pname FROM user AS u INNER JOIN user_permissions AS p ON u.pers_id = p.id ORDER BY u.id DESC LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listUsers.add(new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("fullname"), rs.getInt("active"),
						new UserPermissions(rs.getInt("pers_id"), rs.getString("pname"))));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listUsers;
	}

	public int countItems() {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(id) AS count FROM user";
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

	public int changeStatusItem(int id, int statusChange) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE user SET active = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, statusChange);
			pst.setInt(2, id);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public int addItem(User user) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO user(username, password, fullname, active, pers_id) VALUES (?, ?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUsername());
			pst.setString(2, user.getPassword());
			pst.setString(3, user.getFullname());
			pst.setInt(4, user.getActive());
			pst.setInt(5, user.getUserPermissions().getId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public User getItem(int id) {
		User user = null;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, p.name AS pname FROM user AS u INNER JOIN user_permissions AS p ON u.pers_id = p.id WHERE u.id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("fullname"), rs.getInt("active"),
						new UserPermissions(rs.getInt("pers_id"), rs.getString("pname")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return user;
	}

	public int editItem(User user) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE user SET password = ?, fullname = ?, pers_id = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getPassword());
			pst.setString(2, user.getFullname());
			pst.setInt(3, user.getUserPermissions().getId());
			pst.setInt(4, user.getId());
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
		String sql = "DELETE FROM user WHERE id = ?";
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

	public boolean hasUser(String username) {
		boolean result = false;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM user WHERE username = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return result;
	}
	public boolean hasUser(String username, String password) {
		boolean result = false;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			rs = pst.executeQuery();
			if (rs.next()) {
				result = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return result;
	}

	public int countItemsSearch(String name) {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM user WHERE username LIKE ?";
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

	public ArrayList<User> getItemsSearchPaginaiton(String name, int offset) {
		ArrayList<User> listUsers = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, p.name AS pname FROM user AS u INNER JOIN user_permissions AS p ON u.pers_id = p.id WHERE u.username LIKE ? ORDER BY u.id DESC LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + name + "%");
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				User itemUser = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("fullname"), rs.getInt("active"),
						new UserPermissions(rs.getInt("pers_id"), rs.getString("pname")));
				listUsers.add(itemUser);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listUsers;
	}

	public User getItemByUsernamePasswordActive(String username, String password) {
		User user = null;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT *, p.name AS pname FROM user AS u INNER JOIN user_permissions AS p ON u.pers_id = p.id WHERE u.username = ? AND u.password = ? AND u.active = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setInt(3, 1);
			rs = pst.executeQuery();
			if (rs.next()) {
				user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"),
						rs.getString("fullname"), rs.getInt("active"),
						new UserPermissions(rs.getInt("pers_id"), rs.getString("pname")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return user;
	}

	public int changeStatusItem(String username, String password, int status) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE user SET active = ? WHERE username = ? AND password = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, status);
			pst.setString(2, username);
			pst.setString(3, password);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}
}
