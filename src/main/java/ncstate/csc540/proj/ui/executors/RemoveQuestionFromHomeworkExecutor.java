package ncstate.csc540.proj.ui.executors;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.UserSession;

public class RemoveQuestionFromHomeworkExecutor implements IExecutor{

	@Override
	public boolean execute() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Question Id: ");
		String questionId = scanner.nextLine();
		Statement stmt = null;
		String query = "DELETE FROM HW_EX_QUESTIONS WHERE HW_ID = " + UserSession.getCurrentHomeworkID() + " AND QUESTION_ID = " + questionId;

		try {
			stmt = DBFacade.getConnection().createStatement();
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Question not deleted because " + e.getMessage());
		}
		
		return true;
	}

}
