package ncstate.csc540.proj.entities;

import java.util.HashMap;
import java.util.Map;

public class Parameter {

	private String id;

	private String questionId;

	private Map<String, String> paramValueMap = new HashMap<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}
	
	public void addParam(String param, String value) {
		this.paramValueMap.put(param, value);
	}
	
	public Map<String, String> getParamValueMap() {
		return paramValueMap;
	}
	
}
