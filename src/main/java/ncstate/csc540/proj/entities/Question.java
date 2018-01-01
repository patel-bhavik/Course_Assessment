package ncstate.csc540.proj.entities;

public class Question {

	private String id;
	
	private String text;

	private String hint;

	private int difficultyLevel;
	
	private String explanation;
	
	private String type;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", text=" + text + ", hint=" + hint + ", difficultyLevel=" + difficultyLevel
				+ ", explanation=" + explanation + "]";
	}

	public String getExplanation() {
		return explanation;
	}
	
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	
	public String getHint() {
		return hint;
	}

	public static String getDBTableName() {
		return "QUESTIONS";
	}

	public void setHint(String string) {

		this.hint = string;
		
	}

	public void setDifficultyLevel(int level) {
		this.difficultyLevel = level;
	}
	
	public int getDifficultyLevel() {
		return difficultyLevel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
