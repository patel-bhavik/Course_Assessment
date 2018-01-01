package ncstate.csc540.proj.ui.executors;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.HomeworkExercise;

public class AddQuestionToHomeworkExecutor implements IExecutor{

	@Override
	public boolean execute() {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Question Id: ");
		String questionId = scanner.nextLine();
		Statement stmt = null;
		String query = "INSERT INTO HW_EX_QUESTIONS VALUES ('" + UserSession.getCurrentHomeworkID() + "', '" + questionId + "')";

		try {
			stmt = DBFacade.getConnection().createStatement();
			int updated = stmt.executeUpdate(query);
		} catch (SQLException e) {
			System.out.println("Question not added because " + e.getMessage());
		}
		return true;
	}

}
