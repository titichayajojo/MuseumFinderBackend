import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import io.github.cdimascio.dotenv.Dotenv;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Dotenv dotenv = Dotenv.load();
		
		Connection connection = null;
		String url = dotenv.get("URL");
		String user = dotenv.get("USER");
		String password = dotenv.get("ROOT_PASSWORD");
		
		try {
			connection = DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Successfully connected to database");
	}

}
