package ncstate.csc540.proj.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.Answer;
import ncstate.csc540.proj.entities.Attempt;
import ncstate.csc540.proj.entities.AttemptInfo;
import ncstate.csc540.proj.entities.Parameter;
import ncstate.csc540.proj.entities.Question;

public class AttemptService {

	public Attempt create(Attempt attempt) throws SQLException {
		Statement stmt = null;

		String query = "select max(attempt_no)+1 as curr_attempt from attempts where stud_id = '" + attempt.getStudentId()
				+ "' and hw_id = '" + attempt.getHomeworkId() + "' group by (stud_id, hw_id)";
		// System.out.print(query);
		String query2 = "select (last_number-increment_by) as current_number FROM user_sequences WHERE sequence_name = 'ATTEMPT_ID'";

		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery(query);
		
		if(!rs.isBeforeFirst()) {
			attempt.setAttemptNumber("1");
		}
		else {
		while (rs.next()) {
			// System.out.print(rs.getString(1));
			attempt.setAttemptNumber(rs.getString("curr_attempt"));
		}
		}

		String query1 = "INSERT INTO Attempts (Stud_id, Hw_id, id, Attempt_no) VALUES ('" + attempt.getStudentId()
				+ "', '" + attempt.getHomeworkId() + "', attempt_id.nextval , '" + attempt.getAttemptNumber() + "')";
		// String query1 = DBUtil.prepareInsertString("Attempts",
		// attempt.getStudentId(), attempt.getHomeworkId(), "attempt_id.nextval"
		// ,attempt.getAttemptNumber(), null, null, null );
		// System.out.print(query1);
		stmt.executeUpdate(query1);
		ResultSet rs1 = stmt.executeQuery(query2);
		if (rs1.next()) {
			attempt.setId(rs1.getString("current_number"));
		}

		return attempt;
	}

	public Attempt read(String id) throws SQLException {
		Attempt attempt = new Attempt();

		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + Attempt.getDBTableName() + " where ID =" + id);

		while (rs.next()) {
			attempt.setId(rs.getString("ID"));
			attempt.setStudentId(rs.getString("STUDENT_ID"));
			attempt.setHomeworkId(rs.getString("HW_ID"));
			attempt.setAttemptNumber(rs.getString("ATTEMPT_NO"));
			attempt.setSubmissionTime(rs.getDate("SUBMISSION_TIME").toLocalDate());
			attempt.setTotalScore(rs.getInt("TOTAL_SCORE"));
			attempt.setTotalQuestions(rs.getInt("TOTAL_QUESTIONS"));
		}

		return attempt;
	}

	public void displayQuestion(Attempt attempt) throws SQLException {

		AttemptInfoService attemptInfoService = new AttemptInfoService();

		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		String query = "Select QUESTION_ID from HW_EX_QUESTIONS where HW_ID = '" + UserSession.getCurrentHomeworkID()
				+ "'";
//		System.out.print(query);
		ResultSet rs = stmt.executeQuery(query);

		List<String> questionIds = new ArrayList<String>();

		while (rs.next()) {
			questionIds.add(rs.getString("QUESTION_ID"));
		}

//		System.out.print(questionIds);

		String query1 = "Select HW_MODE from hw_ex where id = " + UserSession.getCurrentHomeworkID();

		String hwmode = "";
		stmt = DBFacade.getConnection().createStatement();

		ResultSet rs1 = stmt.executeQuery(query1);
		while (rs1.next()) {
			hwmode = rs1.getString("HW_MODE");
			break;
		}

		if ("ADAPTIVE".equalsIgnoreCase(hwmode)) {
			displayQuestionsAdaptive(attempt, attemptInfoService, questionIds);
		} else {
			displayQuestionsStandard(attempt, attemptInfoService, questionIds);
		}

	}

	private void displayQuestionsAdaptive(Attempt attempt, AttemptInfoService attemptInfoService,
			List<String> questionIds) throws SQLException {

		Map<String, Boolean> displayedQuestionsMap = new HashMap<>();

		for (String qid : questionIds) {
			displayedQuestionsMap.put(qid, false);
		}

		boolean answeredCorrectly = true;

		int counter = questionIds.size();
		while (counter-- > 0) {

			String nextQuestion = getNextQuestion(displayedQuestionsMap, answeredCorrectly);

			Answer userSelectedAnswer = showQuestionAndAnswer(attempt, attemptInfoService, nextQuestion);

			displayedQuestionsMap.put(nextQuestion, true);

			answeredCorrectly = userSelectedAnswer.isCorrectAnswer() == 1 ? true : false;

			if (!answeredCorrectly) {
				System.out.println("Answered incorrectly, picking easy question...\n");
			}

		}
	}

	private String getNextQuestion(Map<String, Boolean> displayedQuestionsMap, boolean answeredCorrectly)
			throws SQLException {

		List<Question> questions = new ArrayList<>();

		for (String qid : displayedQuestionsMap.keySet()) {

			if (displayedQuestionsMap.get(qid) == false) {

				questions.add(new QuestionService().read(qid));

			}
		}

		Collections.sort(questions, new Comparator<Question>() {

			@Override
			public int compare(Question o1, Question o2) {
				return o1.getDifficultyLevel() - o2.getDifficultyLevel();
			}

		});

		if (questions.isEmpty() == false) {

			if (answeredCorrectly) {

				return questions.get(questions.size() - 1).getId();

			} else {

				return questions.get(0).getId();

			}
		}

		return null;
	}

	private void displayQuestionsStandard(Attempt attempt, AttemptInfoService attemptInfoService,
			List<String> questionIds) throws SQLException {
		for (String questionId : questionIds) {

			showQuestionAndAnswer(attempt, attemptInfoService, questionId);

		}
	}

	private Answer showQuestionAndAnswer(Attempt attempt, AttemptInfoService attemptInfoService, String questionId)
			throws SQLException {
		AttemptInfo attemptinfo = new AttemptInfo();
		String parameters_string, values_string;
		String[] parameters, values;

		Parameter parameter_entity = new Parameter();

		QuestionService questionService = new QuestionService();
		Question question = questionService.read(questionId);

		String questionText = question.getText();

		if (question.getType().equalsIgnoreCase("PARAMETERIZED")) {
			String query = "SELECT * FROM ( SELECT * FROM parameters WHERE question_id = " + question.getId()
					+ " ORDER BY dbms_random.value) WHERE rownum = 1";
			Statement stmt = DBFacade.getConnection().createStatement();
			ResultSet rs1 = stmt.executeQuery(query);
			
			while(rs1.next()){
				parameters_string = rs1.getString("PARAMETERS");
				values_string = rs1.getString("VALUE");
				
				parameters = parameters_string.split(",");
				values = values_string.split(",");

				for (int i = 0; i < values.length; i++) {
					questionText = questionText.replaceAll('#' + parameters[i], values[i]);
					parameter_entity.addParam(parameters[i], values[i]);
				}
				
				parameter_entity.setQuestionId(rs1.getString("QUESTION_ID"));
				parameter_entity.setId(rs1.getString("ID"));
			}
		}

		System.out.println("\n\tQuestion > "+questionText);

		AnswerService answerService = new AnswerService();
		List<Answer> answers = answerService.fetch("QUESTION_ID", questionId);
		Collections.shuffle(answers);

		System.out.println();
		for (Answer answer : answers) {

			System.out.println("\t\t> ID "+answer.getId()+" : "+answer.getText());

		}

		Scanner scanner = new Scanner(System.in);

		System.out.print("\n\tChoose your right answer by entering the answer id : ");
		
		String ansId = scanner.nextLine();
		attemptinfo.setAnswerId(ansId);
		attemptinfo.setAttemptId(attempt.getId());
		attemptinfo.setQuestionId(questionId);
		for (Answer answer : answers) {
			if (answer.getId().compareToIgnoreCase(ansId)==0) {
				attemptinfo.setIsCorrect(answer.isCorrectAnswer());
			}
		}
		attemptInfoService.create(attemptinfo);

		return getSelectedAnswer(attemptinfo.getAnswerId(), answers);
	}

	private Answer getSelectedAnswer(String answerId, List<Answer> answers) {

		for (Answer answer : answers) {
			if (answer.getId().equals(answerId)) {
				return answer;
			}
		}
		return null;
	}

}
