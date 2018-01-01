package ncstate.csc540.proj.entities;
import java.sql.*;
import java.time.LocalDate;
public class HomeworkExercise {
	private String id;
	private String name;
	private String correctAnswerPoints;
	private String penaltyPoints;
	private String scoringPolicy;
	private String mode;
	private String numberOfQuestions;
	private String numberOfRetries;
	private LocalDate startDate;
	private LocalDate endDate;
	private String instructorId;
	private String courseId;
	public String getId ()
	{
		return this.id;
	}
	public void setId (String id)
	{
		this.id = id;
	}
	public String getName ()
	{
		return this.name;
	}
	public void setName (String hname)
	{
		this.name = hname;
	}
	public String getCorrectAnswerPoints ()
	{
		return this.correctAnswerPoints;
	}
	public void setCorrectAnswerPoints (String hcap)
	{
		this.correctAnswerPoints = hcap;
	}
	public String getPenaltyPoints ()
	{
		return this.penaltyPoints;
	}
	public void setPenaltyPoints (String hpp)
	{
		this.penaltyPoints = hpp;
	}
	public String getScoringPolicy ()
	{
		return this.scoringPolicy;
	}
	public void setScoringPolicy (String hsp)
	{
		this.scoringPolicy = hsp;
	}
	public String getMode ()
	{
		return this.mode;
	}
	public void setMode (String hm)
	{
		this.mode = hm;
	}
	public String getNumberOfQuestions ()
	{
		return this.numberOfQuestions;
	}
	public void setNumberOfQuestions (String hnoq)
	{
		this.numberOfQuestions = hnoq;
	}
	public String getNumberOfRetries ()
	{
		return this.numberOfRetries;
	}
	public void setNumberOfRetries (String hnoret)
	{
		this.numberOfRetries = hnoret;
	}
	public LocalDate getStartDate ()
	{
		return this.startDate;
	}
	public void setStartDate (LocalDate hsd)
	{
		this.startDate = hsd;
	}
	public LocalDate getEndDate ()
	{
		return this.endDate;
	}
	public void setEndDate (LocalDate hed)
	{
		this.endDate = hed;
	}
	
	public static String getDBTableName() {
		return "HW_EX";
	} 
	
	public String getInstructorId() {
		return this.instructorId;
	}
	
	public void setInstructorId(String id){
		this.instructorId = id;
	}
	
	public String getCourseId(){
		return this.courseId;
	}
	
	public void setCourseId(String id){
		this.courseId = id;
	}
	
}
