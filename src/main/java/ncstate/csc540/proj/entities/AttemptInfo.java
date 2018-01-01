package ncstate.csc540.proj.entities;

public class AttemptInfo {
	private String attemptId;
	private String questionId;
	private String answerId;
	private int isCorrect;
	
	public String getAttemptId() {
		return attemptId;
	}
	public void setAttemptId(String attemptId) {
		this.attemptId = attemptId;
	}
	public String getQuestionId() {
		return questionId;
	}
	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public int getIsCorrect() {
		return isCorrect;
	}
	public void setIsCorrect(int i) {
		this.isCorrect = i;
	}
	
	public static String getDBTableName(){
		return "ATTEMPT_INFO";
	}
	
	
}
