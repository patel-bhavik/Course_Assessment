package ncstate.csc540.proj.ui.executors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.UserSession;

public class viewTAexecutor implements IExecutor{
	@Override
	public boolean execute() {
		String currCourse = UserSession.getCurrentCourseID();
		
		try {
			if(displayTADetails(currCourse)==1) {
				return true;
			}
			else if(displayTADetails(currCourse)==0) {
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
		
	}
	
	public static int displayTADetails(String courseId) throws SQLException {
		ResultSet rs = null;

		// create database connection
		Connection conn = DBFacade.getConnection();

		String query = "Select S.first_name, S.last_name from Students S, TA T where S.id = T.student_id and T.Course_id = '"+courseId+"'";
		Statement stmnt = conn.createStatement();
		rs = stmnt.executeQuery(query);

		if (!rs.isBeforeFirst()) {
			System.out.print("\nNo TAs allocated for this course. Please add TA.\n");
			return 0;
		} else {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			System.out.print("\n");
		/*	for (int i = 1; i <= columnsNumber; i++) {
				System.out.print(rsmd.getColumnName(i) + "     ");
			}
			System.out.print("\n");*/

			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					System.out.print(columnValue + " ");
				}
				System.out.println("");
			}
			return 1;
		}
	}

}
