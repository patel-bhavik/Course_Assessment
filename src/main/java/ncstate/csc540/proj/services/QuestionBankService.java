package ncstate.csc540.proj.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.DBUtil;
import ncstate.csc540.proj.entities.Question;
import ncstate.csc540.proj.entities.QuestionBank;

/**
 * 
 * @author Team
 *
 */
public class QuestionBankService  {

	public boolean create(QuestionBank questionBank) throws SQLException {

		Statement stmt = null;

		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate(

				DBUtil.prepareInsertString(QuestionBank.getDBTableName(), questionBank.getTopicID(),
						questionBank.getQuestionID(), questionBank.getCourseID()));

		return true;

	}

	public List<QuestionBank> read(String courseID, String topicID) throws SQLException {

		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + QuestionBank.getDBTableName() + " where COURSE_ID ="
				+ courseID + " and TOPIC_ID = " + topicID);

		List<QuestionBank> questionBanks = new LinkedList<QuestionBank>();

		while (rs.next()) {

			QuestionBank qBank = new QuestionBank();

			qBank.setCourseID(rs.getString("COURSE_ID"));
			qBank.setTopicID(rs.getString("TOPIC_ID"));
			qBank.setQuestionID("QUESTION_ID");

			questionBanks.add(qBank);
		}

		return questionBanks;

	}

	public List<Question> getAllQuestions(String courseID, String topicID) throws SQLException {
		List<QuestionBank> questionBanks = read(courseID, topicID);

		QuestionService questionService = new QuestionService();

		List<Question> questions = new LinkedList<Question>();

		for (QuestionBank questionBank : questionBanks) {
			questions.add(questionService.read(questionBank.getQuestionID()));

		}

		return questions;
	}


}
