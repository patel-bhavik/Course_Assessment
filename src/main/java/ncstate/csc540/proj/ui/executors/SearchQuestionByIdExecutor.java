package ncstate.csc540.proj.ui.executors;

import java.sql.SQLException;
import java.util.Scanner;

import ncstate.csc540.proj.entities.Question;
import ncstate.csc540.proj.services.QuestionService;

public class SearchQuestionByIdExecutor implements IExecutor {

	@Override
	public boolean execute() {

		QuestionService questionService = new QuestionService();

		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter question ID : ");
		String questionId = scanner.nextLine();

		try {
			Question question = questionService.read(questionId);
			System.out.println("Question : " + question);
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
		}

		return false;

	}

}
