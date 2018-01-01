package ncstate.csc540.proj.entities;

public class QuestionBank {

	private String topicID;

	private String questionID;

	private String courseID;

	public static String getDBTableName() {
		return "QUESTION_BANK";
	}

	public String getTopicID() {
		return topicID;
	}

	public void setTopicID(String topicID) {
		this.topicID = topicID;
	}

	public String getQuestionID() {
		return questionID;
	}

	public void setQuestionID(String questionID) {
		this.questionID = questionID;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	@Override
	public String toString() {
		return "QuestionBank [topicID=" + topicID + ", questionID=" + questionID + ", courseID=" + courseID + "]";
	}
	
	
	

}
