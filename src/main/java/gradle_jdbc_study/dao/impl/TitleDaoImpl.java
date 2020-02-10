package gradle_jdbc_study.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gradle_jdbc_study.dao.TitleDao;
import gradle_jdbc_study.ds.MySqlDataSource;
import gradle_jdbc_study.dto.Title;
import gradle_jdbc_study.util.LogUtil;

public class TitleDaoImpl implements TitleDao {
	private static final TitleDaoImpl instance = new TitleDaoImpl();

	private TitleDaoImpl() {
	};

	public static TitleDaoImpl getInstance() {
		return instance;
	}

	@Override
	public Title selectTitleByNo(Title title) {
		String sql = "select title_no,title_name from title where title_no = ?";
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, title.getTitleNo());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					return getTitle(rs);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Title> selectTitleByAll() {
		String sql = "select title_no,title_name from title";
		try (Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			List<Title> lists = new ArrayList<>();
			while (rs.next()) {
				lists.add(getTitle(rs));
			}
			return lists;
		} catch (SQLException e) {
			e.getStackTrace();
		}
		return null;
	}

	private Title getTitle(ResultSet rs) throws SQLException {
		int titleNo = rs.getInt("title_no");
		String titleName = rs.getString("title_name");
		return new Title(titleNo, titleName);
	}

	@Override
	public int insertTitle(Title title) {
		int res = 0;
		String sql = "insert into title(title_no,title_name) values(?,?)";
		try(Connection con = MySqlDataSource.getConnection();
			PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, title.getTitleNo());
			pstmt.setString(2, title.getTitleName());
			res = pstmt.executeUpdate();
			return res;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int updateTitle(Title title) {
		int res = 0;
		String sql = "update title set title_name = ? where title_no = ?";
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, title.getTitleName());
			pstmt.setInt(2, title.getTitleNo());
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

	@Override
	public int deleteTitle(Title title) {
		int res = 0;
		String sql = "delete from title where title_no = ?";
		try(Connection con = MySqlDataSource.getConnection();
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, title.getTitleNo());
			res = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return res;
	}

}
