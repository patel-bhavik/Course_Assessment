package ncstate.csc540.proj.ui.executors;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.HomeworkExercise;
import ncstate.csc540.proj.entities.User;
import ncstate.csc540.proj.services.HomeworkExerciseService;

public class ViewExerciseExecutor implements IExecutor{

	@Override
	public boolean execute() {
		String courseId = UserSession.getCurrentCourseID();
		User instructor = UserSession.getLoggedInUser();
		String instructorId = instructor.getID();
		Boolean isHomeworkChosen = false;
		
		Scanner scanner = new Scanner(System.in);
		
		List<HomeworkExercise> homeworks = null;
		HomeworkExercise chosenHomework = new HomeworkExercise();
		String choiceOfHomework;
		
		HomeworkExerciseService homeworkService = new HomeworkExerciseService();
		
		try {
			homeworks = homeworkService.search(instructorId, courseId);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Could not perform operation because " + e.getMessage());
		}
		System.out.println("\n\n");
		System.out.println("List of Homework IDs: ");
		if(homeworks != null){
			for(HomeworkExercise homework : homeworks){
				System.out.println(homework.getId() + " - " + homework.getName());
			}

			System.out.println("\n\n");
		}
		else{
			System.out.println("No exercises found. Please add exercises for this course.");
			return false;
		}
		
		System.out.println("Enter Homework ID: ");
		choiceOfHomework = scanner.nextLine();
		
		for(HomeworkExercise homework : homeworks){
			if(homework.getId().equals(choiceOfHomework)){
				chosenHomework = homework;
				isHomeworkChosen = true;
				break;
			}
		}
		
		if(!isHomeworkChosen){
			System.out.println("Invalid ID entered.");
			return false;
		}
		
		System.out.println("Homework Name : " + chosenHomework.getName());
		System.out.println("Correct Answer Points : " + chosenHomework.getCorrectAnswerPoints());
		System.out.println("Penalty Points : " + chosenHomework.getPenaltyPoints());
		System.out.println("Start Date : " + chosenHomework.getStartDate().toString());
		System.out.println("End Date : " + chosenHomework.getEndDate().toString());
		System.out.println("Scoring Policy : " + chosenHomework.getScoringPolicy());
		System.out.println("Number of Questions : " + chosenHomework.getNumberOfQuestions());
		System.out.println("Number of Retries : " + chosenHomework.getNumberOfRetries());
		System.out.println("Mode : " + chosenHomework.getMode());
		
		UserSession.setCurrentHomeworkID(chosenHomework.getId());
		return true;
	}

}

