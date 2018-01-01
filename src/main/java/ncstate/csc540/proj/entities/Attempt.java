package ncstate.csc540.proj.entities;

import java.time.LocalDate;

public class Attempt {
	private String studentId;
	private String homeworkId;
	private String id;
	private String attemptNumber;
	private LocalDate submissionTime;
	private int totalScore;
	private int totalQuestions;
	
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getHomeworkId() {
		return homeworkId;
	}
	public void setHomeworkId(String homeworkId) {
		this.homeworkId = homeworkId;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getAttemptNumber() {
		return attemptNumber;
	}
	public void setAttemptNumber(String attemptNumber) {
		this.attemptNumber = attemptNumber;
	}
	public LocalDate getSubmissionTime() {
		return submissionTime;
	}
	public void setSubmissionTime(LocalDate submissionTime) {
		this.submissionTime = submissionTime;
	}
	public int getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(int totalScore) {
		this.totalScore = totalScore;
	}
	public int getTotalQuestions() {
		return totalQuestions;
	}
	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}
	public static String getDBTableName() {
		return "ATTEMPT";
	} 
	
}
