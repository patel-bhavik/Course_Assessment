package ncstate.csc540.proj.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ncstate.csc540.proj.common.AppException;
import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.LoginInfo;
import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.Instructor;
import ncstate.csc540.proj.entities.Student;
import ncstate.csc540.proj.entities.TA;
import ncstate.csc540.proj.entities.User;

/**
 * 
 * @author Team
 *
 */
public class UserAccessController {

/*	public static void main(String[] args) {

		LoginInfo info = new LoginInfo();
		info.setPassword("1243");
		info.setUserName("shrikanth");

		User user = null;

		UserAccessController accessController = new UserAccessController();
		try {
			user = UserAccessController.login(info);
		} catch (AppException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.println("The user returned is " + user);
	}*/

	public static User login(LoginInfo info) throws AppException, SQLException {

		/**
		 * Recognize if user is an Instructor, TA, or a Student and return accordingly.
		 * or null or throw exception ( Refer program flow shared by 540 TA)
		 */

		User user = null;

		Statement stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = null;

		rs = stmt.executeQuery("SELECT *  FROM USERS");

		while (rs.next()) {
			String uname = rs.getString("ID");
			String password = rs.getString("PASSWORD");
			String role = rs.getString("ROLE_ID");

			if (info.getUserName().equals(uname) && info.getPassword().equals(password)) {

				if (role.equalsIgnoreCase("1")) {
					user = new TA();
				} else if (role.equalsIgnoreCase("3")) {
					user = new Student();
				} else if (role.equalsIgnoreCase("2")) {
					user = new Instructor();
				}

				user.setID(uname);
				user.setPassword(password);
				break;
			}

		}

		return user;
	}

	public static void exit() {

		UserSession.invalidate();
	}
}
