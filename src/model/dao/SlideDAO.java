package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.bean.Slide;
import util.DBConnectionUtil;
import util.DefineUtil;

public class SlideDAO {
	private ResultSet rs;
	private Statement st;
	private PreparedStatement pst;
	private Connection conn;

	public static void main(String[] args) {
		ArrayList<Slide> list = new SlideDAO().getItems(3);
		for (Slide item : list) {
			System.out.println(item);
		}
	}

	public ArrayList<Slide> getItems(int size) {
		ArrayList<Slide> listSlide = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM slide WHERE active = 1 ORDER BY sort ASC LIMIT ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, size);
			rs = pst.executeQuery();
			while (rs.next()) {
				listSlide.add(new Slide(rs.getInt("id"), rs.getString("name"), rs.getString("picture"),
						rs.getString("link"), rs.getInt("sort"), rs.getInt("active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listSlide;
	}

	public int countItems() {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(id) AS count FROM slide";
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

	public ArrayList<Slide> getItemsPagination(int offset) {
		ArrayList<Slide> listSlide = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM slide ORDER BY id DESC LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, offset);
			pst.setInt(2, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listSlide.add(new Slide(rs.getInt("id"), rs.getString("name"), rs.getString("picture"),
						rs.getString("link"), rs.getInt("sort"), rs.getInt("active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listSlide;
	}

	public int addItem(Slide slide) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO slide(name, picture, link, sort, active) VALUES(?, ?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, slide.getName());
			pst.setString(2, slide.getPicture());
			pst.setString(3, slide.getLink());
			pst.setInt(4, slide.getSort());
			pst.setInt(5, slide.getActive());
			result = pst.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public Slide getItem(int id) {
		Slide Slide = null;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM slide WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				Slide = new Slide(rs.getInt("id"), rs.getString("name"), rs.getString("picture"), rs.getString("link"),
						rs.getInt("sort"), rs.getInt("active"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return Slide;
	}

	public int editItem(Slide slide) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE slide SET name = ?, picture = ?, link = ?, sort = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, slide.getName());
			pst.setString(2, slide.getPicture());
			pst.setString(3, slide.getLink());
			pst.setInt(4, slide.getSort());
			pst.setInt(5, slide.getId());
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
		String sql = "DELETE FROM slide WHERE id = ?";
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

	public int changeStatusItem(int id, int statusChange) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE slide SET active = ? WHERE id = ?";
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

	public int countItemsSearch(String name) {
		int count = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(id) AS count FROM slide WHERE name LIKE ?";
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

	public ArrayList<Slide> getItemsSearchPaginaiton(String name, int offset) {
		ArrayList<Slide> listSlide = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM slide WHERE name LIKE ? ORDER BY id DESC LIMIT ?, ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, "%" + name + "%");
			pst.setInt(2, offset);
			pst.setInt(3, DefineUtil.NUMBER_PER_PAGE);
			rs = pst.executeQuery();
			while (rs.next()) {
				listSlide.add(new Slide(rs.getInt("id"), rs.getString("name"), rs.getString("picture"),
						rs.getString("link"), rs.getInt("sort"), rs.getInt("active")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listSlide;
	}

}
