package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.UserPermissions;
import util.DBConnectionUtil;

public class UserPermissionsDAO {
	private ResultSet rs;
	private Statement st;
	//private PreparedStatement pst;
	private Connection conn;
	
	public static void main(String[] args) {
		UserPermissionsDAO dao = new UserPermissionsDAO();
		ArrayList<UserPermissions> list = dao.getItems();
		for (UserPermissions item : list) {
			System.out.println(item);
		}
	}

	public ArrayList<UserPermissions> getItems() {
		ArrayList<UserPermissions> listUserPermissions = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM user_permissions ORDER BY id DESC";
		try {
			st = conn.createStatement();
			rs = st.executeQuery(sql);
			while (rs.next()) {
				listUserPermissions.add(new UserPermissions(rs.getInt("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, st, conn);
		}
		return listUserPermissions;
	}
}
