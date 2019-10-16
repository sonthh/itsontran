package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Contact;
import util.DBConnectionUtil;
import util.DefineUtil;

public class ContactDAO {
	private ResultSet rs;
	private Statement st;
	private PreparedStatement pst;
	private Connection conn;

	public int addItem(Contact contact) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO contact(name, email, subject, message) VALUE(?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, contact.getName());
			pst.setString(2, contact.getEmail());
			pst.setString(3, contact.getSubject());
			pst.setString(4, contact.getMessage());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public int countItems() {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM contact";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			if (rs.next()) {
				result = rs.getInt("count");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, st, conn);
		}
		return result;
	}

	public ArrayList<Contact> getItemsPaginaiton(int offset) {
		ArrayList<Contact> listContacts = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM contact ORDER BY id DESC LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listContacts.add(new Contact(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
						rs.getString("subject"), rs.getString("message")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listContacts;
	}

	public int countItemsSearch(String name) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM contact WHERE name LIKE ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + name + "%");
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

	public ArrayList<Contact> getItemsSearchPaginaiton(String name, int offset) {
		ArrayList<Contact> listContacts = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM contact WHERE name LIKE ? ORDER BY id DESC LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + name + "%");
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listContacts.add(new Contact(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
						rs.getString("subject"), rs.getString("message")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listContacts;
	}

	public Contact getItem(int id) {
		Contact contact = null;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM contact WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while (rs.next()) {
				contact = new Contact(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
						rs.getString("subject"), rs.getString("message"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return contact;
	}

	public int deleteItem(int id) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "DELETE FROM contact WHERE id = ?";
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

}
