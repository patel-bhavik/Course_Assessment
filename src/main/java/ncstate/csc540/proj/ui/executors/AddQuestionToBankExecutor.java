package ncstate.csc540.proj.ui.executors;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.DBUtil;
import ncstate.csc540.proj.common.Nature;
import ncstate.csc540.proj.entities.Answer;
import ncstate.csc540.proj.entities.Parameter;
import ncstate.csc540.proj.entities.Question;
import ncstate.csc540.proj.entities.QuestionBank;
import ncstate.csc540.proj.services.AnswerService;
import ncstate.csc540.proj.services.ParameterService;
import ncstate.csc540.proj.services.QuestionBankService;
import ncstate.csc540.proj.services.QuestionService;

public class AddQuestionToBankExecutor implements IExecutor {

	@Override
	public boolean execute() {

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Do you have the question id ? (y/n/exit) ");
			String answer = scanner.nextLine();

			if (answer.trim().equalsIgnoreCase("y")) {

				System.out.print("Enter question ID : ");

				String questionID = scanner.nextLine();

				boolean validQuestionID = false;

				try {
					validQuestionID = isValidQuestionID(questionID);
				} catch (SQLException e) {
					validQuestionID = false;
				}

				if (!validQuestionID) {
					System.out.println("Invalid question, try again!");
					continue;
				}

				associateToBank(questionID);

				return true;
			} else if (answer.trim().equalsIgnoreCase("n")) {

				String newQuestionID;
				try {
					newQuestionID = createNewQuestion();
					associateToBank(newQuestionID);
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}

				return true;
			} else if (answer.trim().equalsIgnoreCase("exit")) {

				return true;
			} else {
				continue;
			}

		}

	}

	private boolean isValidQuestionID(String questionID) throws SQLException {
		return DBUtil.getCount(Question.getDBTableName(), "ID", questionID) > 0;
	}

	private String createNewQuestion() throws SQLException {

		Scanner scanner = new Scanner(System.in);

		Question question = new Question();

		question.setId(DBUtil.getNextID(Question.getDBTableName()));

		System.out.print("Enter question text : ");
		question.setText(scanner.nextLine());

		System.out.print("Enter question hint (optional) : ");
		question.setHint(scanner.nextLine());

		System.out.print("Enter question explanation (optional) : ");
		question.setExplanation(scanner.nextLine());

		System.out.print("Enter question difficulty level (1 - 6): ");
		question.setDifficultyLevel(scanner.nextInt());

		String questionType = null;

		do {
			System.out.print("Question type Fixed [F] or Parametrized [P] ? : ");
			questionType = scanner.nextLine();

		} while (questionType == null || !(questionType.equals("F") || questionType.equals("P")));

		QuestionService questionService = new QuestionService();

		if ("P".equalsIgnoreCase(questionType)) {
			questionService.create(question, Nature.PARAMETERIZED);
			createAnswers(question.getId(), Nature.PARAMETERIZED);
		} else {
			questionService.create(question, Nature.FIXED);
			createAnswers(question.getId(), Nature.FIXED);

		}

		System.out.println(question + " inserted successfully!");

		return question.getId();

	}

	private void createAnswers(String id, Nature nature) throws SQLException {

		switch (nature) {

		case FIXED:
			createAnswerSet(id, "FIXED_ANSWERS");
			break;
		case PARAMETERIZED:
			createAnswerSet(id, "PARAMETERIZED_ANSWERS");
			break;
		}
	}

	// private void createParametrizedAnswer(String questionId) throws SQLException
	// {
	//
	// Scanner scanner = new Scanner(System.in);
	//
	// AnswerService answerService = new AnswerService();
	//
	// Answer answer = new Answer();
	//
	// answer.setId(DBUtil.getNextID("ANSWER_ID", "PARAMETERIZED_ANSWERS"));
	// answer.setQuestionId(questionId);
	//
	// String correctAnswer = "";
	// while (!("true".equalsIgnoreCase(correctAnswer) ||
	// "false".equalsIgnoreCase(correctAnswer))) {
	// System.out.print("Correct answer enter (true or false) : ");
	// correctAnswer = scanner.nextLine();
	// }
	// answer.setCorrectAnswer(Boolean.valueOf(correctAnswer.toLowerCase()));
	//
	// System.out.print("Enter answer text : ");
	// String ansText = scanner.nextLine();
	//
	// answer.setText(ansText);
	//
	// answerService.createParameterized(answer, createParameter(questionId));
	//
	// }

	private String createParameter(String questionId) throws SQLException {

		Parameter parameter = new Parameter();
		parameter.setId(DBUtil.getNextID("PARAMETERS"));
		parameter.setQuestionId(questionId);

		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.print("Enter valid parameter,value like A,20 (type 'Q' to exit) : ");
			String userentry = scanner.nextLine();

			if ("Q".equalsIgnoreCase(userentry)) {
				break;
			}

			try {
				parameter.addParam(userentry.split(",")[0], userentry.split(",")[1]);
				System.out.println("Added!");
			} catch (Exception e) {
				System.out.println("Invalid format!");
			}

		}

		new ParameterService().createParameter(parameter);

		return parameter.getId();
	}

	private void createAnswerSet(String id, String nature) throws SQLException {

		Scanner scanner = new Scanner(System.in);

		AnswerService answerService = new AnswerService();

		Answer oneCorrectAnswer = new Answer();

		oneCorrectAnswer.setId(DBUtil.getNextID("ANSWER_ID", nature));
		oneCorrectAnswer.setQuestionId(id);
		oneCorrectAnswer.setCorrectAnswer(true);
		System.out.print("Enter correct answer text : ");
		String correctAnswerText = scanner.nextLine();

		oneCorrectAnswer.setText(correctAnswerText);

		if (nature.equals("PARAMETERIZED_ANSWERS")) {
			answerService.createParameterized(oneCorrectAnswer, createParameter(id));
		} else {
			answerService.createFixed(oneCorrectAnswer);
		}

		int incorrectAnswers = 3;

		while (incorrectAnswers-- > 0) {

			Answer incorrectAnswer = new Answer();

			incorrectAnswer.setId(DBUtil.getNextID("ANSWER_ID", nature));
			incorrectAnswer.setQuestionId(id);
			incorrectAnswer.setCorrectAnswer(false);
			System.out.print("Enter incorrect answer text : ");
			String incorrectAnswerText = scanner.nextLine();

			incorrectAnswer.setText(incorrectAnswerText);

			if (nature.equals("PARAMETERIZED_ANSWERS")) {
				answerService.createParameterized(incorrectAnswer, createParameter(id));
			} else {
				answerService.createFixed(incorrectAnswer);
			}

		}

		// String correctAnswer = "";
		// while (!("true".equalsIgnoreCase(correctAnswer) ||
		// "false".equalsIgnoreCase(correctAnswer))) {
		// System.out.print("Correct answer ? enter true or false : ");
		// correctAnswer = scanner.nextLine();
		// }

	}

	private void associateToBank(String questionId) {

		System.out.println("\n");

		Scanner scanner = new Scanner(System.in);

		if (questionId == null) {

			System.out.print("Enter question ID : ");
			questionId = scanner.nextLine();

		}

		System.out.print("Enter topic ID : ");
		String topicID = scanner.nextLine();

		QuestionBankService bankService = new QuestionBankService();

		QuestionBank questionBank = new QuestionBank();

		System.out.print("Enter valid course ID : ");
		String courseID = scanner.nextLine();

		questionBank.setCourseID(courseID);
		questionBank.setQuestionID(questionId);
		questionBank.setTopicID(topicID);

		try {
			bankService.create(questionBank);

			System.out.println(questionBank + " inserted successfully!");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}


