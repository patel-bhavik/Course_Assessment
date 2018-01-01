package ncstate.csc540.proj.ui.executors;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

import ncstate.csc540.proj.common.DBUtil;
import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.Course;
import ncstate.csc540.proj.entities.HomeworkExercise;
import ncstate.csc540.proj.entities.Instructor;
import ncstate.csc540.proj.services.HomeworkExerciseService;

public class AddExerciseExecutor implements IExecutor{

	@Override
	public boolean execute() {
		
		HomeworkExerciseService homeworkService = new HomeworkExerciseService();
		HomeworkExercise homework = new HomeworkExercise();
		
		String startDate;
		String endDate;
		
		try {
			homework.setId(DBUtil.getNextID(HomeworkExercise.getDBTableName()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Enter Name of Homework : ");
		homework.setName(scanner.nextLine());
		
		System.out.print("Enter Points for Correct Answer : ");
		homework.setCorrectAnswerPoints(scanner.nextLine());
		
		System.out.print("Enter Penalty Points : ");
		homework.setPenaltyPoints(scanner.nextLine());
		
	//	System.out.print("Enter Points for Correct Answer : ");
	//	homework.setScoringPolicy(scanner.nextLine());
		
		System.out.print("Enter Number of Questions : ");
		homework.setNumberOfQuestions(scanner.nextLine());
		
		System.out.print("Enter Number of Retries : ");
		homework.setNumberOfRetries(scanner.nextLine());
		
		System.out.print("Enter Homework Mode : ");
		homework.setMode(scanner.nextLine());
		
		System.out.println("Enter Scoring Policy: ");
		homework.setScoringPolicy(scanner.nextLine());
		
		homework.setInstructorId(UserSession.getLoggedInUser().getID());
		
		homework.setCourseId(UserSession.getCurrentCourseID());
		
		System.out.print("Enter Start Date: ");
		startDate = scanner.nextLine();
		
		System.out.print("Enter End Date: ");
		endDate = scanner.nextLine();
		
		String[] startDateParams = startDate.split("-");
		String[] endDateParams = endDate.split("-");
		
		if(startDateParams.length == 3 && endDateParams.length == 3){
			
			LocalDate start = LocalDate.of(Integer.parseInt(startDateParams[2].trim()), Integer.parseInt(startDateParams[0].trim()), Integer.parseInt(startDateParams[1].trim()));
			LocalDate end = LocalDate.of(Integer.parseInt(endDateParams[2].trim()), Integer.parseInt(endDateParams[0].trim()), Integer.parseInt(endDateParams[1].trim()));
			
			homework.setStartDate(start);
			homework.setEndDate(end);
			
			try {
				homeworkService.create(homework);
			} catch (SQLException e) {
				System.out.println("The operation could not be completed because " + e.getMessage());
				e.printStackTrace();
			}
			
		}
		else{
			System.out.print("Invalid Date Entry.");
		}
		
	//	scanner.close();
		return true;
	}

}
