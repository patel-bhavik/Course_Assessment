package ncstate.csc540.proj.ui.executors;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.entities.Topic;
import ncstate.csc540.proj.services.QuestionService;
import ncstate.csc540.proj.services.TopicService;

public class SearchQuestionByTopicExecutor implements IExecutor {

	@Override
	public boolean execute() {

		Scanner scanner = new Scanner(System.in);

		String choice = "0";

		while (choice.equals("0")) {

			System.out.println("1.\t Search by Topic ID");
			System.out.println("2.\t Search by Topic text");

			System.out.print("Choose an option (1/2):");

			try {

				choice = scanner.nextLine();

			} catch (Exception e) {
				choice = "0";
				System.out.println("Enter valid choice!");
				continue;
			}

		}

		if (choice.equals("1")) {

			System.out.print("Enter Topic ID : ");

			String topicID = scanner.nextLine();

			return processById(topicID);

		} else {

			System.out.println("Enter Topic text : ");

			String topicText = scanner.nextLine();

			Set<String> topicIDs;
			try {
				topicIDs = getTopicIds(topicText);

				for (String id : topicIDs) {

					processById(id);
					System.out.println("\n");
				}

				return true;
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}

		}

	}

	private Set<String> getTopicIds(String topicText) throws SQLException {

		Set<String> relevantTopicIds = new HashSet<String>();

		String query_1 = "SELECT ID FROM TOPICS WHERE upper(TEXT) LIKE '%" + topicText.toUpperCase() + "%'";

		Statement stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = null;

		rs = stmt.executeQuery(query_1);

		while (rs.next()) {
			relevantTopicIds.add(rs.getString("ID"));

		}

		return relevantTopicIds;
	}

	private boolean processById(String topicID) {

		TopicService topicService = new TopicService();

		try {
			Topic topic = topicService.read(topicID);

			Set<String> questionIds = getQuestionIds(topic.getId());

			printQuestions(questionIds, topic);

			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	private void printQuestions(Set<String> questionIds, Topic topic) throws SQLException {

		System.out.println("Questions relevant to " + topic + " : " + "\n");

		QuestionService questionService = new QuestionService();

		for (String qid : questionIds) {
			System.out.println(questionService.read(qid));
		}

	}

	// public static void printFixedQuestions(Set<String> questionIds) throws
	// SQLException {
	//
	// String idsAsCSV = convertToCSV(questionIds);
	//
	// String paramTable = "SELECT QUESTION_ID,FIXED_TEXT FROM FIXED_QUESTION where
	// QUESTION_ID IN (" + idsAsCSV + ")";
	//
	// Statement stmt = DBFacade.getConnection().createStatement();
	// ResultSet rs = null;
	//
	// rs = stmt.executeQuery(paramTable);
	//
	// while (rs.next()) {
	//
	// System.out.println(rs.getString("QUESTION_ID") + ":\t" +
	// rs.getString("FIXED_TEXT"));
	//
	// }
	// }

	// public static void printParametrizedQuestions(Set<String> questionIds) throws
	// SQLException {
	// String idsAsCSV = convertToCSV(questionIds);
	//
	// String paramTable = "SELECT QUESTION_ID,PARMETRIZED_TEXT FROM
	// PARAMETRIZED_QUESTION where QUESTION_ID IN ("
	// + idsAsCSV + ")";
	//
	// Statement stmt = DBFacade.getConnection().createStatement();
	// ResultSet rs = null;
	//
	// rs = stmt.executeQuery(paramTable);
	//
	// while (rs.next()) {
	//
	// System.out.println(rs.getString("QUESTION_ID") + ":\t" +
	// rs.getString("PARMETRIZED_TEXT"));
	//
	// }
	// }

	// public static void main(String[] args) {
	//
	// HashSet s = new HashSet<String>();
	// s.add("1");
	// s.add("2");
	// System.out.println(convertToCSV(s));
	// }

	// private static String convertToCSV(Set<String> questionIds) {
	//
	// String c = questionIds.toString().replace("[", "");
	// c = c.replace("]", "");
	//
	// return c;
	// }

	private Set<String> getQuestionIds(String id) throws SQLException {

		Set<String> questionIDSet = new HashSet<String>();

		String query_1 = "SELECT QUESTION_ID FROM QUESTION_BANK WHERE TOPIC_ID = '" + id + "'";

		Statement stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = null;

		rs = stmt.executeQuery(query_1);

		while (rs.next()) {
			questionIDSet.add(rs.getString("QUESTION_ID"));

		}

		return questionIDSet;
	}

}
