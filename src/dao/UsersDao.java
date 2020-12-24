package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.catalina.User;
import org.apache.catalina.connector.Response;

import com.cos.hello.config.DBConn;
import com.cos.hello.model.Users;

public class UsersDao {

	public int insert(Users user) {
		StringBuffer sb = new StringBuffer(); // String 전용 컬렉션 (동기화)
		sb.append("INSERT INTO users(username, password, email) ");
		sb.append("VALUES(?,?,?)");
		String sql = sb.toString();

		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			pstmt.setString(3, user.getEmail());
			int result = pstmt.executeUpdate(); // 변경된 행의 개수를 리턴
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public Users login(Users user) {
		String sql = "SELECT  id, username, email FROM users WHERE username = ? AND password = ? ";

		Connection conn = DBConn.getInstance();
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUsername());
			pstmt.setString(2, user.getPassword());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Users userEntity = Users.builder()
						.id(rs.getInt("id"))
						.username(rs.getString("username"))
						.email(rs.getString("email"))
						.build();
				return userEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}