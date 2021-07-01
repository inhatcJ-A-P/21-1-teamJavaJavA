package bookproject.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBconnect {
	private Connection conn;
	private Statement stat;
	public DBconnect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			 conn = DriverManager.getConnection(
					"jdbc:oracle:thin:@114.71.137.174:53994:XE", "JAVAJO", "5555");
			 //System.out.println("연동");
			 stat = conn.createStatement();
			
			
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 없음");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("연동실패");
			e.printStackTrace();
		}
	}
	public Statement getStat() {
		return stat;
	}
	public static void main(String[] args) {
		DBconnect db = new DBconnect();
	}
}
