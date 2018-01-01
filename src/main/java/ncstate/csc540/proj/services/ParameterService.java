package ncstate.csc540.proj.services;

import java.sql.SQLException;
import java.sql.Statement;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.DBUtil;
import ncstate.csc540.proj.entities.Parameter;

public class ParameterService {

	public static void main(String[] args) {

		Parameter parameter = new Parameter();
		parameter.addParam("p1", "20");
		parameter.addParam("p2", "10");
		parameter.addParam("p3", "54");

		String parameters = parameter.getParamValueMap().keySet().toString();
		parameters = parameters.replace("[", "");
		parameters = parameters.replace("]", "");

		String values = parameter.getParamValueMap().values().toString();
 
		values = values.replace("[", "");
		values = values.replace("]", "");

		System.out.println(parameters);
		System.out.println(values);

	}

	public boolean createParameter(Parameter parameter) throws SQLException {

		Statement stmt = null;

		stmt = DBFacade.getConnection().createStatement();

		String parameters = parameter.getParamValueMap().keySet().toString();
		parameters = parameters.replace("[", "");
		parameters = parameters.replace("]", "");

		String values = parameter.getParamValueMap().values().toString();

		values = values.replace("[", "");
		values = values.replace("]", "");

		stmt.executeUpdate(DBUtil.prepareInsertString("PARAMETERS", parameter.getId(), parameter.getQuestionId(),
				parameters, values));

		return true;

	}

}
