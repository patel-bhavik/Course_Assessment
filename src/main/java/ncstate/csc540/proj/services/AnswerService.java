package ncstate.csc540.proj.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.DBUtil;
import ncstate.csc540.proj.entities.Answer;

public class AnswerService {

	public boolean createFixed(Answer answer) throws SQLException {

		Statement stmt = null;

		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate(DBUtil.prepareInsertString("FIXED_ANSWERS", answer.getText(), answer.getQuestionId(),
				"" + answer.isCorrectAnswer(), answer.getId()));

		return true;

	}

	public boolean createParameterized(Answer answer, String parameterId) throws SQLException {

		Statement stmt = null;

		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate(DBUtil.prepareInsertString("PARAMETERIZED_ANSWERS", parameterId, answer.getId(),
				answer.getText(), "" + answer.isCorrectAnswer(), answer.getQuestionId()));

		return true;

	}

	public Answer read(String id) throws SQLException {

		List<Answer> Answers = fetch("ID", id);

		if (Answers.size() == 1)
			return Answers.get(0);

		return null;

	}

	public boolean update(Answer entity) throws SQLException {
		
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate("UPDATE " + Answer.getDBTableName() + " SET TEXT = '" + ((Answer) entity).getText() + "'");

		return true;
	}

	public boolean delete(String id) throws SQLException {
		
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate("DELETE FROM " + Answer.getDBTableName() + " WHERE ID =" + id);

		return true;
	}

	public List<Answer> fetch(String colName, String value) throws SQLException {

		List<Answer> entityList = new LinkedList<Answer>();

		addAnswers(colName, value, entityList, "FIXED_ANSWERS");

		addAnswers(colName, value, entityList, "PARAMETERIZED_ANSWERS");

		return entityList;

	}

	private void addAnswers(String colName, String value, List<Answer> entityList, String tableName)
			throws SQLException {
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + tableName + " where " + colName + " = " + value);

		

		while (rs.next()) {

			Answer answer = new Answer();
			answer.setId(rs.getString("ANSWER_ID"));
			answer.setQuestionId(rs.getString("QUESTION_ID"));
			answer.setText(rs.getString("TEXT"));
			answer.setCorrectAnswer(rs.getInt("IS_CORRECT")==1? true : false);

			entityList.add(answer);

		}
	}

}

