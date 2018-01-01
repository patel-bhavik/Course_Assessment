package ncstate.csc540.proj.common;

import java.sql.ResultSet;
import java.sql.Statement;

public class UserPopulator {

	public static void main(String[] args) {
		try {

			Statement stmt = null;
			ResultSet rs = null;

			try {

				stmt = DBFacade.getConnection().createStatement();

				stmt.executeUpdate("DROP TABLE GRADIANCE_USERS ");

				stmt.executeUpdate("CREATE TABLE GRADIANCE_USERS "
						+ "(USER_NAME VARCHAR(32), PASSWORD VARCHAR(32), ROLE VARCHAR(32)) ");

				stmt.executeUpdate("INSERT INTO GRADIANCE_USERS " + "VALUES ('abisha', '456', 'TA')");
				stmt.executeUpdate("INSERT INTO GRADIANCE_USERS " + "VALUES ('shrikanth', '123', 'Instructor')");
				stmt.executeUpdate("INSERT INTO GRADIANCE_USERS " + "VALUES ('arun', '789', 'Student')");

				rs = stmt.executeQuery("SELECT *  FROM GRADIANCE_USERS");

				while (rs.next()) {
					String uname = rs.getString("USER_NAME");
					String password = rs.getString("PASSWORD");
					String role = rs.getString("ROLE");
					System.out.println(uname + " : " + password + " : " + "" + role);
				}

			} 
				finally {
				close(rs);
				close(stmt);
				}
			}  
				catch (Throwable oops) {
				oops.printStackTrace();
		}
	}

	static void close(Statement st) {
		if (st != null) {
			try {
				st.close();
			} catch (Throwable whatever) {
			}
		}
	}

	static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Throwable whatever) {
			}
		}
	}
}