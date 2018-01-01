package ncstate.csc540.proj.ui.executors;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import ncstate.csc540.proj.common.DBFacade;

public class ViewStudentDetailsExecutor implements IExecutor {

	@Override
	public boolean execute() {
		System.out.print("\nPlease enter student id : ");
		Scanner scan = new Scanner(System.in);
		String id = scan.next();
		int row = 0;
		try {
			row = displayStudentDetails(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		if (row != 0) {
			return true;
		} else {
			return false;
		}
	}

	public static int displayStudentDetails(String id) throws SQLException {
		ResultSet rs = null;

		// create database connection
		Connection conn = DBFacade.getConnection();

		String query = "Select S.first_name, S.last_name, E.Course_id, E.Name, A.Total_Score from Students S, Attempts A, Hw_ex E where S.id ='"
				+ id + "' and S.id=A.stud_id and A.hw_id=E.id";
		Statement stmnt = conn.createStatement();
		rs = stmnt.executeQuery(query);

		if (!rs.isBeforeFirst()) {
			System.out.print("\nIncorrect Student id! Please try again.\n");
			return 0;
		} else {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			System.out.print("\n");
			for (int i = 1; i <= columnsNumber; i++) {
				System.out.print(rsmd.getColumnName(i) + "     ");
			}
			System.out.print("\n");

			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					System.out.print(columnValue + "     ");
				}
				System.out.println("");
			}
			return 1;
		}
	}

}
