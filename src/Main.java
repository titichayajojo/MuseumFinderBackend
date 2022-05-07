import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Connection connection = null;
		String url = "jdbc:mariadb://localhost:3306/test";
		String user = "root";
		String password = "password";
		
		try {
			connection = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Successfully connected to database");
	}

}
