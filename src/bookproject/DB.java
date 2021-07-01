package bookproject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

	public static Connection conn;
	public static Statement stmt;

	// 오라클 연결
	public static void init() {

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

			conn = DriverManager.getConnection("jdbc:oracle:thin:@114.71.137.174:53994:XE", "javajo", "5555");

			stmt = conn.createStatement();
			System.out.println("DB 연결 성공");
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("해당 드라이버가 없습니다.");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("접속 오류 / SQL 오류");
			e.printStackTrace();
		}
	}

	// 조회
	public static ResultSet getResultSet(String sql) {
		try {
			//Statement stmt = conn.createStatement();
			return stmt.executeQuery(sql);
	
		}catch(Exception e){
			System.out.println(e);	//오류 메시지
			return null;
		}
	}
	
	// 삽입, 수정, 삭제 
	public static void executeQuery(String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
