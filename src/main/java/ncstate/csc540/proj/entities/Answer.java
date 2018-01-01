package ncstate.csc540.proj.entities;

public class Answer {

	private String text;

	private String id;

	private int correctAnswer;

	private String questionId;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int isCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean answer) {
		this.correctAnswer = answer == true ? 1 : 0;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Answer "+id +".)\t "+text;
	}

	public static String getDBTableName() {
		return "ANSWER";
	}

}
