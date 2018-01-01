package ncstate.csc540.proj.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.DBUtil;
import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.Course;
import ncstate.csc540.proj.entities.HomeworkExercise;
import ncstate.csc540.proj.entities.Instructor;
import ncstate.csc540.proj.entities.Topic;

public class HomeworkExerciseService{
	
	public boolean create(HomeworkExercise entity) throws SQLException {
		// If no instructor, the instructor id has to be set to NULL before calling this API.
		Statement stmt = null;
		
		String query = "INSERT INTO " + HomeworkExercise.getDBTableName() + "(ID, NAME, CORR_ANS_PTS, PENALTY_POINTS, SCORING_POLICY, HW_MODE, NO_OF_QUES, RETRIES, START_DATE, END_DATE, INSTRUCTOR_ID, COURSE_ID)  VALUES ('" + entity.getId() + "' , '" + entity.getName() + "' , '" + entity.getCorrectAnswerPoints() + "' ,'" + entity.getPenaltyPoints() + "' , '"  + entity.getScoringPolicy() + "' , '" + entity.getMode() + "' , '" + entity.getNumberOfQuestions() + "' , '" + entity.getNumberOfRetries() + "' , TO_DATE('" + entity.getStartDate().toString() + "', 'YYYY-MM-DD') , TO_DATE('"+ entity.getEndDate().toString() + "', 'YYYY-MM-DD') , '" + entity.getInstructorId() + "' , '" + entity.getCourseId() + "')";

		stmt = DBFacade.getConnection().createStatement();
		
		System.out.println(query);
		stmt.executeUpdate(query);
		
		return true;

	}
	
	public HomeworkExercise read(String id) throws SQLException{
		HomeworkExercise hw = new HomeworkExercise();
		
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + HomeworkExercise.getDBTableName() + " where ID =" + id);
		
		while (rs.next()) {
			hw.setId(rs.getString("ID"));
			hw.setName(rs.getString("NAME"));
			hw.setCorrectAnswerPoints(rs.getString("CORR_ANS_PTS"));
			hw.setPenaltyPoints(rs.getString("PENALTY_POINTS"));
			hw.setStartDate(rs.getDate("START_DATE").toLocalDate());
			hw.setEndDate(rs.getDate("END_DATE").toLocalDate());
			hw.setScoringPolicy(rs.getString("SCORING_POLICY"));
			hw.setNumberOfQuestions(rs.getString("NO_OF_QUES"));
			hw.setNumberOfRetries(rs.getString("RETRIES"));
			hw.setMode(rs.getString("HW_MODE"));
			hw.setInstructorId(rs.getString("INSTRUCTOR_ID"));
			hw.setCourseId(rs.getString("COURSE_ID"));

		}
		
		return hw;
	}
	
	public boolean setInstructor(String id, String instructor_id) throws SQLException{
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate("UPDATE " + HomeworkExercise.getDBTableName() + " SET INSTRUCTOR_ID = '" + instructor_id + "'");

		return true;
	}
	
	public boolean setCourse(String id, String course_id) throws SQLException{
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate("UPDATE " + HomeworkExercise.getDBTableName() + " SET COURSE_ID = '" + course_id + "'");

		return true;
	}
	
	public List<HomeworkExercise> search(String instructorId, String courseId) throws SQLException{
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		String role = String.valueOf(UserSession.getLoggedInUser().getRole());
		
		String query = null;
		
		if(role.compareToIgnoreCase("INSTRUCTOR")==0 || role.compareToIgnoreCase("TA")==0 ) {
			query = "SELECT * FROM " + HomeworkExercise.getDBTableName() + " where COURSE_ID = '" + courseId + "'";
		}
		else {
			query = "SELECT * FROM " + HomeworkExercise.getDBTableName() + " where COURSE_ID = '" + courseId + "' and Start_date <= Sysdate and end_date >=Sysdate";
		}
		
		ResultSet rs = stmt.executeQuery(query);
		List<HomeworkExercise> entityList = new LinkedList<HomeworkExercise>();

		while (rs.next()) {
			HomeworkExercise homework = new HomeworkExercise();
			
			homework.setId(rs.getString("ID"));
			homework.setName(rs.getString("NAME"));
			homework.setCorrectAnswerPoints(rs.getString("CORR_ANS_PTS"));
			homework.setPenaltyPoints(rs.getString("PENALTY_POINTS"));
			homework.setStartDate(rs.getDate("START_DATE").toLocalDate());
			homework.setEndDate(rs.getDate("END_DATE").toLocalDate());
			homework.setScoringPolicy(rs.getString("SCORING_POLICY"));
			homework.setNumberOfQuestions(rs.getString("NO_OF_QUES"));
			homework.setNumberOfRetries(rs.getString("RETRIES"));
			homework.setMode(rs.getString("HW_MODE"));
			homework.setInstructorId(rs.getString("INSTRUCTOR_ID"));
			homework.setCourseId(rs.getString("COURSE_ID"));

			entityList.add(homework);

		}

		return entityList;
	}
}
