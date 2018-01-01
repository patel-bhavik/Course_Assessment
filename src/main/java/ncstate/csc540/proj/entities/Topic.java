package ncstate.csc540.proj.entities;

public class Topic {

	private String id;
	private String text;

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
		return "Topic [id=" + id + ", text=" + text + "]";
	}

	public static String getDBTableName() {
		return "TOPICS";
	}

}
