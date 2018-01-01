package ncstate.csc540.proj.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.entities.Attempt;
import ncstate.csc540.proj.entities.AttemptInfo;

public class AttemptInfoService {
	public boolean create(AttemptInfo attemptInfo) throws SQLException{
		Statement stmt = null;
		
		String query = "INSERT INTO " + AttemptInfo.getDBTableName() + " VALUES (" + attemptInfo.getAttemptId() + ", " + attemptInfo.getQuestionId() + ", " + attemptInfo.getAnswerId() + ", " + attemptInfo.getIsCorrect() +  ")";
		//System.out.print(query);
		stmt = DBFacade.getConnection().createStatement();
		
		stmt.executeUpdate(query);
		
		return true;
	}
	
	public AttemptInfo read(String attemptId, String questionId) throws SQLException{
		AttemptInfo attemptInfo = new AttemptInfo();
		
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + Attempt.getDBTableName() + " where ATTEMPT_ID =" + attemptId + " AND QUES_ID = " + questionId);
		
		while(rs.next()){
			attemptInfo.setAttemptId(rs.getString("ATTEMPT_INFO"));
			attemptInfo.setQuestionId(rs.getString("QUES_ID"));
			attemptInfo.setAnswerId(rs.getString("ANS_ID"));
			attemptInfo.setIsCorrect(rs.getInt("IS_CORRECT"));
		}
		
		return attemptInfo;
	}
}
