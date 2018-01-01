package ncstate.csc540.proj.ui.executors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.UserSession;

public class ViewPastHomeworkExecutor implements IExecutor {
	
	private static Connection connection = DBFacade.getConnection();

	@Override
	public boolean execute() {

		try {
			String userSelectedPastHWId = getPastHomeworkID();

			int attempts_total = printHomeworkDetails(userSelectedPastHWId);

			int attempts_taken = getAttemptsTaken(userSelectedPastHWId, attempts_total);

			int attempts_remaining = attempts_total - attempts_taken;

			System.out.println("Attempts remaining:" + attempts_remaining);

			String[] atid = getAttemptIDs(attempts_taken, userSelectedPastHWId);
			
			printAttemptDetails(userSelectedPastHWId, attempts_taken, atid);

			return true;

		} catch (SQLException th) {
			System.out.println(th.getMessage());
			th.printStackTrace();
		}

		return false;

	}

	private String getPastHomeworkID() {

		Scanner sc = new Scanner(System.in);

		String query1 = "SELECT H.NAME, H.ID FROM HW_EX H WHERE H.END_DATE <  ( SELECT SYSDATE FROM DUAL )";

		PreparedStatement listoptions;
		try {
			listoptions = connection.prepareStatement(query1);
			ResultSet rs1 = listoptions.executeQuery();
			while (rs1.next()) {
				System.out.println(rs1.getString("id") + " -> " + rs1.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		System.out.print("Select your homework (above): ");

		String selectedHWId = sc.nextLine();

		return selectedHWId;
	}
	
	private int printHomeworkDetails(String selectedHWId) throws SQLException {
		
		String query2 = "SELECT * FROM HW_EX" + " WHERE id = ?";
		PreparedStatement listhwdetails = connection.prepareStatement(query2);
		listhwdetails.setString(1, selectedHWId);
		ResultSet rs2 = listhwdetails.executeQuery();
		int attempts_available = 0;
		if (rs2.next()) {
			System.out.println("\nHomework details");
			//System.out.println("\nHw id" + rs2.getString(1));
			System.out.println("\nName:" + rs2.getString(2));
			System.out.println("Correct answer points:" + rs2.getString(3));
			System.out.println("Penalty points:" + rs2.getString(4));
			System.out.println("Start date:" + rs2.getDate(5).toLocalDate());
			System.out.println("End Date:" + rs2.getDate(6).toLocalDate());
			System.out.println("Scoring Policy:" + rs2.getString(7));
			System.out.println("Number of questions:" + rs2.getString(8));
			System.out.println("Type of exercise:" + rs2.getString(10));
			attempts_available = Integer.parseInt(rs2.getString(9));
			System.out.println("Total attempts:" + attempts_available);
		}
		return attempts_available;
	}
	
	private int getAttemptsTaken(String selectedHWId, int attempts_available) throws SQLException {

		String studentId = UserSession.getLoggedInUser().getID();
		String query3 = "SELECT COUNT(ID) FROM ATTEMPTS WHERE STUD_ID = ? AND HW_ID = ?";
		PreparedStatement countattempts = connection.prepareStatement(query3);
		countattempts.setString(1, studentId);
		countattempts.setString(2, selectedHWId);
		ResultSet rs3 = countattempts.executeQuery();
		int attempts_taken = 0;
		if (rs3.next()) {
			attempts_taken = rs3.getInt(1);
		}

		return attempts_taken;
	}
	
	private String[] getAttemptIDs(int attempts_taken, String userSelectedPastHWId) throws SQLException {

		String[] atid = new String[attempts_taken];

		int k = 0;
		String studentId = UserSession.getLoggedInUser().getID();
		String query4 = "SELECT ID FROM ATTEMPTS WHERE HW_ID = ? AND STUD_ID = ?";
		PreparedStatement listattempts = connection.prepareStatement(query4);
		listattempts.setString(1, userSelectedPastHWId);
		listattempts.setString(2, studentId);
		ResultSet rs4 = listattempts.executeQuery();
		while (rs4.next()) {
			atid[k++] = rs4.getString(1);
		}
		/*System.out.println("this module is fine");
		for(int i=0 ;i<atid.length; i++)
		{
			System.out.println(atid[i]);
		}*/
		return atid;
	}
	
	private void printAttemptDetails(String userSelectedPastHWId, int attempts_taken, String[] atid) throws SQLException {

		if(attempts_taken == 0)
		{
			System.out.println("\nNo attempts taken");
		}
		else
		{
			System.out.println("\nAttempt details for the current homework");
		}
		for (int i = 0; i < attempts_taken; i++) {
			String query5 = "SELECT ID, ATTEMPT_NO, SUBMISSION_TIME, TOTAL_SCORE FROM ATTEMPTS WHERE ID = ?";
			PreparedStatement displayattempt = connection.prepareStatement(query5);
			displayattempt.setString(1, atid[i]);
			ResultSet rs5 = displayattempt.executeQuery();
			if (rs5.next()) {
				System.out.println("\n\nAttempt id:" + rs5.getString(1));
				System.out.println("Attempt no:" + rs5.getString(2));
				System.out.println("Submission time:" + rs5.getString(3));
				System.out.println("Total Score:" + rs5.getString(4));

			}
			String query6 = "SELECT QUES_ID, ANS_ID, IS_CORRECT FROM ATTEMPT_INFO WHERE ATTEMPT_ID = ?";
			String query7 = "SELECT ANSWER_ID FROM FIXED_ANSWERS FA WHERE FA.QUESTION_ID = ? AND FA.IS_CORRECT = ?";
			String query8 = "SELECT PA.ANSWER_ID FROM PARAMETERS P, PARAMETERIZED_ANSWERS PA WHERE P.ID = PA.PARAMETER_ID AND P.QUESTION_ID = ? AND PA.IS_CORRECT = ?";
			String query9 = "SELECT CORR_ANS_PTS, PENALTY_POINTS FROM HW_EX WHERE ID = ?  ";
			
			PreparedStatement getpoints = connection.prepareStatement(query9);
			getpoints.setString(1, userSelectedPastHWId);
			ResultSet rs9 = getpoints.executeQuery();
			String pos_points = null;
			String neg_points = null;
			if(rs9.next())
			{
				pos_points = rs9.getString(1);
				neg_points = rs9.getString(2);
			}
			
			PreparedStatement displayattemptinfo = connection.prepareStatement(query6);
			displayattemptinfo.setString(1, atid[i]);
			ResultSet rs6 = displayattemptinfo.executeQuery();

			System.out.println("Question id  Answer id ISCORRECT(1/0) Solution Points");
			while (rs6.next()) {
				String qid = rs6.getString(1);
				int iscorr = rs6.getInt(3);
				String points;
				if(iscorr == 1)
				{
					points = pos_points;
				}
				else
				{
					points = String.valueOf((Integer.parseInt(neg_points) * -1 ));
				}
				int setans = 1;
				PreparedStatement displaysolution = connection.prepareStatement(query7);
				displaysolution.setString(1, qid);
				displaysolution.setInt(2, setans);
				ResultSet rs7 = displaysolution.executeQuery();
				
				PreparedStatement displaysolution2 = connection.prepareStatement(query8);
				displaysolution2.setString(1, qid);
				displaysolution2.setInt(2, setans);
				ResultSet rs8 = displaysolution2.executeQuery();
				
				if (rs7.next()) 
				{
					System.out.println("   " + qid + "            " + rs6.getString(2) + "          " + iscorr
						    + "            " + rs7.getString(1) + "        " + points );
				} else if (rs8.next()) 
				{
					System.out.println("   " + qid + "            " + rs6.getString(2) + "          " + iscorr
							+ "            " + rs8.getString(1) + "        " + points);
				}
			}

		}
	}

}


