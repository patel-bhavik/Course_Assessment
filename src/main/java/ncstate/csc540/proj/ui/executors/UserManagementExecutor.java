package ncstate.csc540.proj.ui.executors;

import java.sql.SQLException;
import java.util.Scanner;

import ncstate.csc540.proj.services.UserManagement;

public class UserManagementExecutor implements IExecutor {

	@Override
	public boolean execute() {
		
		System.out.print("\nPress 1 to change firstname.\n\nPress 2 to change lastname.\n\nPress 3 to change password.");
		Scanner scan = new Scanner(System.in);
		String in = scan.next();
		int row = 0;
	//	System.out.print(in);
		if(in.compareTo("1")==0) {
			System.out.print("\nEnter new first name:");
			Scanner scan1 = new Scanner(System.in);
			String fname = scan1.next();
			try {
				row = UserManagement.UpdateFname(fname);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		else if(in.compareTo("2")==0) {
			System.out.print("\nEnter new last name:");
			Scanner scan1 = new Scanner(System.in);
			String lname = scan1.next();
			try {
				row = UserManagement.UpdateLname(lname);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		else if(in.compareTo("3")==0) {
			System.out.print("\nEnter new password:");
			Scanner scan1 = new Scanner(System.in);
			String pwd = scan1.next();
			try {
				row = UserManagement.UpdatePwd(pwd);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		else {
			System.out.print("\nInvalid choice");
		}
		
		if(row!=0) {
			System.out.print("\nUpdated user details successfully!");
			return true;
		}
		else {
			System.out.print("\n\nUpdated failed!\n");
			return false;
		}
		
	}
}

