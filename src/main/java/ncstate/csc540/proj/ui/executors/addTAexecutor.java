package ncstate.csc540.proj.ui.executors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.UserSession;

public class addTAexecutor implements IExecutor{
	@Override
	public boolean execute() {
		String currCourse = UserSession.getCurrentCourseID();
		
		try {
			if(addTADetails(currCourse)==1) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	public static int addTADetails(String courseId) throws SQLException {
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter TA id:");
		String TAid = sc.nextLine();
				
		Scanner sc1 = new Scanner(System.in);
		System.out.print("Enter Student id:");
		String studId = sc1.nextLine();

		// create database connection
		Connection conn = DBFacade.getConnection();
		
		String selquery = "Select * from Users where Id = '"+TAid+"'";
		Statement stmnt1 = conn.createStatement();
		ResultSet rs = stmnt1.executeQuery(selquery);
		
		if (!rs.isBeforeFirst()) {
			String query = "Insert into Users values ('"+TAid+"', '12345', '1')";
			Statement stmnt = conn.createStatement();
			int row = stmnt.executeUpdate(query);
		}
		
		String query1 = "Insert into TA values ('"+TAid+"', '"+courseId+"', '"+studId+"')";
		Statement stmnt2 = conn.createStatement();
		int row1 = stmnt2.executeUpdate(query1);

		if (row1==1){
			System.out.print("\nTA added successfully\n");
			return row1;
		}
		else {
			System.out.print("\nUnable to add TA!\n");
			return row1;
		}
	}

}
