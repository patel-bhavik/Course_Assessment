package ncstate.csc540.proj.services;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.User;

public class UserManagement {
	public static int UpdateFname(String fname) throws SQLException{
		//create database connection
		Connection conn = DBFacade.getConnection();
		
		User currentUser = UserSession.getLoggedInUser();
		String id = currentUser.getID();
		String role = currentUser.getRole().toString();
		String query = null;
		
		//create and execute select query
		if (role.compareToIgnoreCase("Student")==0) {
			query = "Update Students set first_name ='"+fname+"' where id ='"+id+"'";
		}
		else if(role.compareToIgnoreCase("Instructor")==0) {
			query = "Update Instructors set first_name ='"+fname+"' where id ='"+id+"'";
		}
		else if(role.compareToIgnoreCase("TA")==0) {
			System.out.print("\nPlease update first name from Student account!");
			return 0;
		}
		
		Statement stmnt = conn.createStatement();
		int row = stmnt.executeUpdate(query);	
		return row;
	}
	
	public static int UpdateLname(String lname) throws SQLException{
		//create database connection
		Connection conn = DBFacade.getConnection();
				
		User currentUser = UserSession.getLoggedInUser();
		String id = currentUser.getID();
		String role = currentUser.getRole().toString();
		String query = null;
		
		//create and execute select query
		if (role.compareToIgnoreCase("Student")==0) {
			query = "Update Students set last_name ='"+lname+"' where id ='"+id+"'";
		}
		else if(role.compareToIgnoreCase("Instructor")==0) {
			query = "Update Instructors set last_name ='"+lname+"' where id ='"+id+"'";
		}
		else if(role.compareToIgnoreCase("TA")==0) {
			System.out.print("\nPlease update last name from Student account!");
			return 0;
		}
				
		Statement stmnt = conn.createStatement();
		int row = stmnt.executeUpdate(query);		
		return row;
	}
	
	public static int UpdatePwd(String password) throws SQLException{
		//create database connection
		Connection conn = DBFacade.getConnection();
		
		User currentUser = UserSession.getLoggedInUser();
		String id = currentUser.getID();
		
		//create and execute select query
		String query = "Update Users set password ='"+password+"' where id ='"+id+"'";
				
		Statement stmnt = conn.createStatement();
		int row = stmnt.executeUpdate(query);		
		return row;
	}
}
