package ncstate.csc540.proj.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.entities.Report;

public class Reporting {
	public static boolean add_report (String attempt_id) throws SQLException {
		int c_ans_count = 0;
		int att_count = 0;
		int cpts = 0;
		int ppts = 0;
		Connection conn = DBFacade.getConnection();
		String query = "SELECT COUNT (*) FROM ATTEMPT_INFO A1, ATTEMPT_INFO A2, FIXED_ANSWERS FA, PARAMETERS PQ, PARAMETERIZED_ANSWERS PA WHERE attempt_id = ? AND ((A1.ques_id = FA.ques_id AND A1.ans_id = FA.answ_id) OR (A2.ques_id = PQ.ques_id AND A2.ans_id = PA.answ_id AND PQ.id = PA.parameter_id)  )  ";
		String query1 = "SELECT COUNT(A.ques_id) AS COUNT FROM ATTEMPT_INFO WHERE A.id = ? ";
		String query2 = "SELECT corr_ans_pts FROM ATTEMPTS A, HW_EX H WHERE A.hw_id = H.id AND A.id = ?";
		String query3 = "SELECT penalty_pts FROM ATTEMPTS A, HW_EX H WHERE A.hw_id = H.id AND A.id = ?";

		
		PreparedStatement findCorrAnsCount = conn.prepareStatement(query);
		findCorrAnsCount.setString (1,attempt_id);
		ResultSet rs = findCorrAnsCount.executeQuery();
		if(rs.next())
		{
			c_ans_count = rs.getInt("COUNT(*)");
		}
		
		PreparedStatement findAttemptedques = conn.prepareStatement(query1);
		findAttemptedques.setString (1,attempt_id);
		ResultSet rs1 = findCorrAnsCount.executeQuery();
		if(rs1.next())
		{
			att_count = rs1.getInt("COUNT");
		}
		
		PreparedStatement findCorrAnsPts = conn.prepareStatement(query2);
		findCorrAnsPts.setString (1,attempt_id);
		ResultSet rs2 = findCorrAnsPts.executeQuery();
		if(rs2.next())
		{
			cpts = Integer.parseInt(rs2.getString("corr_ans_pts"));
		}
		
		PreparedStatement findPenAnsPts = conn.prepareStatement(query3);
		findPenAnsPts.setString (1,attempt_id);
		ResultSet rs3 = findPenAnsPts.executeQuery();
		if(rs3.next())
		{
			ppts = Integer.parseInt(rs3.getString("corr_ans_pts"));
		}
		
		String inquery = "INSERT INTO REPORTS (attempt_id, corr_ans_count, total_Score) VALUES (?,?,?)";
		PreparedStatement reportins = conn.prepareStatement(inquery);
		reportins.setString (1,attempt_id);
		reportins.setString (2,Integer.toString (c_ans_count));
		reportins.setString (3,Integer.toString ((c_ans_count * cpts) + ((att_count - c_ans_count) * ppts)));
		int numRows = reportins.executeUpdate();
		
		//return the flag
		if(numRows > 0)
			return true;
		else
			return false;
	}
	
public static Report view_report (String attempt_id) throws SQLException{
		
		Connection conn = DBFacade.getConnection();
		String query = "SELECT * FROM REPORT R WHERE R.attempt_id = attempt_id";
		PreparedStatement findReport = conn.prepareStatement(query);
		findReport.setString(1,attempt_id);
		ResultSet resSet = findReport.executeQuery();
		
		Report report = new Report();
		if(resSet.next()){
			report.set_Attempt_id (resSet.getString("attempt_id"));
			report.set_Corr_ans_count (resSet.getString("corr_ans_count"));
			report.set_Total_score (resSet.getString("total_score"));	
		}else{
			System.out.println("No report with attempt id = "+attempt_id+" exists.");
		}
		return report;
	}

}
