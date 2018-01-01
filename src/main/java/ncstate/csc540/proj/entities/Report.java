package ncstate.csc540.proj.entities;

public class Report {
	private String attempt_id;
	private String corr_ans_count;
	private String total_score;
	public String get_Attempt_id() {
		return attempt_id;
	}
	public void set_Attempt_id(String attempt_id) {
		this.attempt_id = attempt_id;
	}
	public String get_Total_score() {
		return total_score;
	}
	public void set_Total_score(String total_score) {
		this.total_score = total_score;
	}
	public String get_Corr_ans_count() {
		return corr_ans_count;
	}
	public void set_Corr_ans_count(String corr_ans_count) {
		this.corr_ans_count = corr_ans_count;
	}
}
