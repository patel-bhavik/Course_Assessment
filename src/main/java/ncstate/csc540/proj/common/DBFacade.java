package ncstate.csc540.proj.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

/**
 * 
 * @author Team
 *
 */
public class DBFacade {

	static final String jdbcURL = "jdbc:oracle:thin:@//orca.csc.ncsu.edu:1521/orcl.csc.ncsu.edu"; // Using SERVICE_NAME

	private static Connection connection;

	static {

		try {
			connection = connect();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

	}

	public static Connection connect() throws ClassNotFoundException, SQLException {

		Class.forName("oracle.jdbc.driver.OracleDriver");

		String user = "abhatt22"; // your unity id
		String passwd = "200128625"; // your student id

		return DriverManager.getConnection(jdbcURL, user, passwd);
	}
	
	public static void closeConnection(Connection conn) throws ClassNotFoundException, SQLException {
		conn.close();
	}

	public static Connection getConnection() {
		return connection;
	}

}
