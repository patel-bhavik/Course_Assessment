package ncstate.csc540.proj.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.DBUtil;
import ncstate.csc540.proj.common.Nature;
import ncstate.csc540.proj.entities.Answer;
import ncstate.csc540.proj.entities.Question;

public class QuestionService {

	// public static void main(String[] args) throws SQLException {
	// Scanner scanner = new Scanner(System.in);
	//
	// Question question = new Question();
	//
	// question.setId(DBUtil.getNextID(Question.getDBTableName()));
	//
	// System.out.print("Enter question text : ");
	// question.setText(scanner.nextLine());
	//
	// System.out.print("Enter question hint (optional) : ");
	// question.setHint(scanner.nextLine());
	//
	// System.out.print("Enter question difficulty level (1 - 6): ");
	// question.setDifficultyLevel(scanner.nextInt());
	//
	// QuestionService questionService = new QuestionService();
	// questionService.create(question);
	//
	// }

	public boolean create(Question entity, Nature type) throws SQLException {

		Statement stmt = null;

		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate(

				DBUtil.prepareInsertString(Question.getDBTableName(), entity.getId(), "" + entity.getDifficultyLevel(),
						entity.getHint(), entity.getExplanation(), type.name()));

		switch (type) {

		case FIXED:

			Statement nstmt = DBFacade.getConnection().createStatement();
			nstmt.executeUpdate(

					DBUtil.prepareInsertString("FIXED_QUESTION", entity.getId(), entity.getText()));
			break;

		case PARAMETERIZED:

			Statement nStmt = DBFacade.getConnection().createStatement();
			nStmt.executeUpdate(DBUtil.prepareInsertString("PARAMETERIZED_QUESTION", entity.getId(), entity.getText()));

			break;

		}

		return true;

	}

	public Question read(String id) throws SQLException {

		List<Question> questions = search("ID", id);

		if (questions.size() == 1)
			return questions.get(0);

		return null;

	}

	public List<Answer> getAnswers(String questionID) throws SQLException {

		AnswerService answerService = new AnswerService();

		return answerService.fetch("QUESTION_ID", questionID);
	}

	public boolean update(Question entity) throws SQLException {
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate(
				"UPDATE " + Question.getDBTableName() + " SET TEXT = '" + ((Question) entity).getText() + "'");

		return true;
	}

	public boolean delete(String id) throws SQLException {
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate("DELETE FROM " + Question.getDBTableName() + " WHERE ID=" + id);

		return true;
	}

	public List<Question> search(String colName, String value) throws SQLException {

		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM " + Question.getDBTableName() + " where " + colName + " = " + value);

		Question question = new Question();

		List<Question> entityList = new LinkedList<Question>();

		while (rs.next()) {

			question.setId(rs.getString("ID"));

			((Question) question).setText(getText(rs.getString("ID")));

			question.setHint(rs.getString("HINT"));
			question.setDifficultyLevel(rs.getInt("DIFFICULTY_LEVEL"));

			question.setExplanation(rs.getString("EXPLANATION"));
			
			question.setType(rs.getString("TYPE"));

			entityList.add(question);

		}

		return entityList;

	}

//	private String getText(String id) throws SQLException {
//
//		String query = "SELECT FIXED_TEXT FROM FIXED_QUESTION where QUESTION_ID = '" + id + "'";
//
//		Statement stmt = DBFacade.getConnection().createStatement();
//		ResultSet rs = null;
//
//		rs = stmt.executeQuery(query);
//
//		while (rs.next()) {
//
//			return rs.getString("FIXED_TEXT");
//
//		}
//
//		query = "SELECT PARAMETERIZED_TEXT FROM PARAMETERIZED_QUESTION where QUESTION_ID = '" + id + "'";
//
//		stmt = DBFacade.getConnection().createStatement();
//		rs = stmt.executeQuery(query);
//
//		while (rs.next()) {
//
//			return rs.getString("PARAMETERIZED_TEXT");
//
//		}
//
//		
//		
//		return "";
//	}

	
	private String getText(String id) throws SQLException {

		String query = "SELECT FIXED_TEXT FROM FIXED_QUESTION where QUESTION_ID = '" + id + "'";

		Statement stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = null, rs1 = null;
		String parameterized_text;
		String parameters_string, values_string;
		String[] parameters;
		String[] values;

		rs = stmt.executeQuery(query);

		while (rs.next()) {

			return rs.getString("FIXED_TEXT");

		}

		query = "SELECT PARAMETERIZED_TEXT FROM PARAMETERIZED_QUESTION where QUESTION_ID = '" + id + "'";

		stmt = DBFacade.getConnection().createStatement();
		rs = stmt.executeQuery(query);

		while (rs.next()) {
			parameterized_text = rs.getString("PARAMETERIZED_TEXT");
			
			return parameterized_text;

		}


		
		
		return "";
	}
}
