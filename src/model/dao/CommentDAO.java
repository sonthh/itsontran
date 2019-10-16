package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.bean.Comment;
import util.DBConnectionUtil;

public class CommentDAO {
	private ResultSet rs;
	// private Statement st;
	private PreparedStatement pst;
	private Connection conn;

	public static void main(String[] args) {
		CommentDAO dao = new CommentDAO();
		/* System.out.println(dao.getItem(43)); */
		/* dao.activeItem(commentId, status) */

		/*
		 * int result = dao.deleteItem(0, 6); System.out.println(result);
		 */

		ArrayList<Comment> l = dao.getActiveItemsByCommentParentIdOfNews(43, 12);
		for (Comment item : l) {
			System.out.println(item);
			System.out.println("--------------");
		}
		System.out.println("\n===============================================");

		ArrayList<Comment> l1 = dao.getActiveItemsByCommentParentIdOfNews(0, 12);
		for (Comment item : l1) {
			System.out.println(item);
			System.out.println("--------------");
		}
		System.out.println("\n===============================================");

		/*ArrayList<Comment> list = dao.getItemsByNewsIdAndSort(12);
		for (Comment item : list) {
			System.out.println(item);
			System.out.println("--------------");
		}*/
		System.out.println("\n===============================================");

	}

	/****************************************
	 * Start Phục vụ cho comment ở detail.jsp
	 ***************************************/
	/*
	 * Lấy tất cả các comment có parentId của newsId: tức là chỉ 1 cấp thôi
	 */
	public ArrayList<Comment> getItemsByCommentParentIdOfNews(int commentParentId, int newsId) {
		ArrayList<Comment> listComments = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM comment WHERE parent_id = ? AND news_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, commentParentId);
			pst.setInt(2, newsId);
			rs = pst.executeQuery();
			while (rs.next()) {
				listComments.add(new Comment(rs.getInt("id"), rs.getString("content"), rs.getInt("user_id"),
						rs.getTimestamp("date_create"), rs.getInt("parent_id"), rs.getInt("news_id"),
						rs.getInt("status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listComments;
	}

	/*
	 * Lấy tất cả các comment con có parentId của newsId: và lấy tiếp các comment
	 * con lun -> dùng để quy
	 */
	public ArrayList<Comment> getItemsByCommentParentIdOfNewsAll(int commentParentId, int newsId) {
		ArrayList<Comment> listComments = this.getItemsByCommentParentIdOfNews(commentParentId, newsId);
		ArrayList<Comment> result = new ArrayList<>();
		for (Comment comment : listComments) {
			result.add(comment);
			result.addAll(getItemsByCommentParentIdOfNewsAll(comment.getId(), newsId));
		}
		return result;
	}

	/*
	 * Lấy tất cả các comment của một tin luôn, dùng lại phương thức
	 * getItemsByCommentParentIdOfNewsAll
	 */
	public ArrayList<Comment> getItemsByNewsIdAndSort(int newsId) {
		return this.getItemsByCommentParentIdOfNewsAll(0, newsId);
	}

	/****************************************
	 * End Phục vụ cho comment ở detail.jsp
	 ***************************************/
	
	
	/*********************************************************/
	public ArrayList<Comment> getActiveItemsByCommentParentIdOfNews(int commentParentId, int newsId) {
		ArrayList<Comment> listComments = new ArrayList<>();
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM comment WHERE status = 1 AND parent_id = ? AND news_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, commentParentId);
			pst.setInt(2, newsId);
			rs = pst.executeQuery();
			while (rs.next()) {
				listComments.add(new Comment(rs.getInt("id"), rs.getString("content"), rs.getInt("user_id"),
						rs.getTimestamp("date_create"), rs.getInt("parent_id"), rs.getInt("news_id"),
						rs.getInt("status")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return listComments;
	}

	public ArrayList<Comment> getActiveItemsByCommentParentIdOfNewsAll(int commentParentId, int newsId) {
		ArrayList<Comment> listComments = this.getActiveItemsByCommentParentIdOfNews(commentParentId, newsId);
		ArrayList<Comment> result = new ArrayList<>();
		for (Comment comment : listComments) {
			result.add(comment);
			result.addAll(getActiveItemsByCommentParentIdOfNewsAll(comment.getId(), newsId));
		}
		return result;
	}
	/*********************************************************/
	
	public Comment getItem(int id) {
		Comment comment = null;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT * FROM comment WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			if (rs.next()) {
				comment = new Comment(id, rs.getString("content"), rs.getInt("user_id"), rs.getTimestamp("date_create"),
						rs.getInt("parent_id"), rs.getInt("news_id"), rs.getInt("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return comment;
	}

	public int addItem(Comment comment) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "INSERT INTO comment(content, user_id, date_create, parent_id, news_id) VALUE(?, ?, ?, ?, ?)";
		try {
			pst = conn.prepareStatement(sql);
			pst.setString(1, comment.getContent());
			pst.setInt(2, comment.getUserId());
			pst.setTimestamp(3, comment.getDateCreate());
			pst.setInt(4, comment.getParentId());
			pst.setInt(5, comment.getNewsId());
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(rs, pst, conn);
		}
		return result;
	}

	/*------------------------------------*/
	/* Xoa 1 comment */
	private int deleteItem(int commentParentId) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "DELETE FROM comment WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, commentParentId);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	/* Xoa comment và các con của comment */
	public int deleteItem(int commentParentId, int newsId) {
		int result = 0;
		ArrayList<Comment> listComments = this.getItemsByCommentParentIdOfNews(commentParentId, newsId);
		for (Comment comment : listComments) {
			result += this.deleteItem(comment.getId(), newsId);
		}
		result += this.deleteItem(commentParentId);
		return result;
	}

	public int countCommentByNewsId(int newsId) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "SELECT COUNT(*) AS count FROM comment WHERE news_id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, newsId);
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

	private int activeItem(int commentId, int status) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "UPDATE comment SET status = ? WHERE id = ?";
		try {
			pst = conn.prepareStatement(sql);
			pst.setInt(1, status);
			pst.setInt(2, commentId);
			result = pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBConnectionUtil.close(pst, conn);
		}
		return result;
	}

	public int activeItemAndSubItem(int commentId, int newsId, int status) {
		int result = 0;
		ArrayList<Comment> list = new ArrayList<>();
		list.add(this.getItem(commentId));
		list.addAll(this.getItemsByCommentParentIdOfNewsAll(commentId, newsId));
		for (Comment item : list) {
			result += this.activeItem(item.getId(), status);
		}
		return result;
	}

	public int deleteItemByNewsId(int id) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "DELETE FROM comment WHERE news_id = ?";
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

	public int deleteItemByUserId(int id) {
		int result = 0;
		conn = DBConnectionUtil.getConnection();
		String sql = "DELETE FROM comment WHERE user_id = ?";
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
