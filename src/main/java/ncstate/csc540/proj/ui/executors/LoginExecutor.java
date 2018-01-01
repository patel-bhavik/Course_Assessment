package ncstate.csc540.proj.ui.executors;

import java.sql.SQLException;
import java.util.Scanner;

import ncstate.csc540.proj.common.AppException;
import ncstate.csc540.proj.common.LoginInfo;
import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.User;
import ncstate.csc540.proj.services.UserAccessController;

public class LoginExecutor implements IExecutor {

	@Override
	public boolean execute() {

		UserAccessController accessController = new UserAccessController();

		LoginInfo info = new LoginInfo();

		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Username : ");
		String userName = scanner.nextLine();
		System.out.print("Password : ");
		String password = scanner.nextLine();

		System.out.println("Please wait...");
		info.setUserName(userName);
		info.setPassword(password);

		User user = null;
		try {
			user = accessController.login(info);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (user != null) {
			System.out.println("\n\t\t******* Welcome "+user.getID()+" ********");
			UserSession.setCurrentUser(user);
			return true;
		} else {
			System.out.println("Invalid user");
			return false;
		}

	}

}
