package ncstate.csc540.proj.ui.executors;

import java.sql.SQLException;

import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.Attempt;
import ncstate.csc540.proj.services.AttemptService;

public class AttemptHomeworkExecutor implements IExecutor {

	@Override
	public boolean execute() {

		AttemptService attemptservice = new AttemptService();
		Attempt curr_attempt = new Attempt();

		curr_attempt.setStudentId(UserSession.getLoggedInUser().getID());
		curr_attempt.setHomeworkId(UserSession.getCurrentHomeworkID());
		
		try {
			Attempt attempt = attemptservice.create(curr_attempt);
			attemptservice.displayQuestion(attempt);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}

}