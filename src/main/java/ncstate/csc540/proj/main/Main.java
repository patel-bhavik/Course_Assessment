package ncstate.csc540.proj.main;

import java.util.Scanner;

import ncstate.csc540.proj.common.ROLE;
import ncstate.csc540.proj.ui.executors.AddCourseExecutor;
import ncstate.csc540.proj.ui.executors.AddExerciseExecutor;
import ncstate.csc540.proj.ui.executors.AddQuestionToBankExecutor;
import ncstate.csc540.proj.ui.executors.AddQuestionToHomeworkExecutor;
import ncstate.csc540.proj.ui.executors.AttemptHomeworkExecutor;
import ncstate.csc540.proj.ui.executors.DropCourseExecutor;
import ncstate.csc540.proj.ui.executors.EnrollmentExecutor;
import ncstate.csc540.proj.ui.executors.LoginExecutor;
import ncstate.csc540.proj.ui.executors.RemoveQuestionFromHomeworkExecutor;
import ncstate.csc540.proj.ui.executors.SearchQuestionByIdExecutor;
import ncstate.csc540.proj.ui.executors.SearchQuestionByTopicExecutor;
import ncstate.csc540.proj.ui.executors.UserManagementExecutor;
import ncstate.csc540.proj.ui.executors.ViewPastHomeworkExecutor;
import ncstate.csc540.proj.ui.executors.ViewReportExecutor;
import ncstate.csc540.proj.ui.executors.ViewStudentDetailsExecutor;
import ncstate.csc540.proj.ui.executors.addTAexecutor;
import ncstate.csc540.proj.ui.executors.viewTAexecutor;

/**
 * 
 * @author Team
 *
 */
public class Main {

	public static void main(String[] args) {

		new Main().start();
	}

	private void start() {

		while (true) {

			System.out.println("\n******************* Please Select one of the option below ********************\n\n");

			System.out.println("\t1. Login\n\n");
			System.out.println("\t2. Exit\n\n");

			System.out.print("Enter your option : ");
			Scanner scanner = new Scanner(System.in);

			while (true) {

				try {
					processLogin(Integer.parseInt(scanner.nextLine().trim()));
					break;
				} catch (Exception e) {

					System.out.println("Enter valid option!");
				}
			}

		}

	}

	private void processLogin(int selected) {
		if (selected == 1) {
			LoginExecutor loginExecutor = new LoginExecutor();

			boolean authResult = loginExecutor.execute();

			try {

				if (authResult) {

					Menu applicationRoot = new Menu("Application");
					buildFlow(applicationRoot);

					applicationRoot.display();

				}
			} catch (Exception e) {
				return;
			}
		} else if (selected == 2) {

			System.out.println("Exited!");
			System.exit(0);

		} else {
			System.out.println("Invalid option.");
			return;
		}
	}

	private void buildFlow(Menu applicationRoot) {

		Menu viewCourseStudent = new Menu("View Course(Student)", ROLE.STUDENT);

		applicationRoot.addMenu(viewCourseStudent);

		Menu viewHW = new Menu("View Homework", ROLE.STUDENT);

		viewCourseStudent.addMenu(viewHW);

		Menu viewCurrentHW = new Menu("View Current Homework", ROLE.STUDENT);

		Action viewPastHW = new Action("View Past Homework", new ViewPastHomeworkExecutor(), ROLE.STUDENT);

		Action attemptHW = new Action("Attempt Homework", new AttemptHomeworkExecutor(), ROLE.STUDENT);

		// viewCourseStudent.addMenu(viewHW);

		viewHW.addMenu(viewCurrentHW);
		viewHW.addAction(viewPastHW);

		viewCurrentHW.addAction(attemptHW);

		UserManagementExecutor userExec = new UserManagementExecutor();

		Action userManagement = new Action("User Management", userExec);
		applicationRoot.addAction(userManagement);

		Menu courseManagement = new Menu("Course Management", ROLE.INSTRUCTOR, ROLE.TA);

		courseManagement.addAction(new Action("Add Course", new AddCourseExecutor(), ROLE.INSTRUCTOR));
		Menu viewCourse = new Menu("View Course", ROLE.INSTRUCTOR, ROLE.TA);
		courseManagement.addMenu(viewCourse);

		ViewStudentDetailsExecutor viewStudExec = new ViewStudentDetailsExecutor();

		Action viewStudentDetails = new Action("View Student details", viewStudExec, ROLE.INSTRUCTOR, ROLE.TA);
		Action viewReport = new Action("View Report", new ViewReportExecutor(), ROLE.INSTRUCTOR, ROLE.TA);
		Action viewTA = new Action("View TA", new viewTAexecutor(), ROLE.INSTRUCTOR, ROLE.TA);
		Action addTA = new Action("Add TA", new addTAexecutor(), ROLE.INSTRUCTOR, ROLE.TA);

		viewCourse.addAction(viewStudentDetails);
		viewCourse.addAction(viewReport);
		viewCourse.addAction(viewTA);
		viewCourse.addAction(addTA);
		viewCourse.addAction(new Action("Add Exercise", new AddExerciseExecutor(), ROLE.TA, ROLE.INSTRUCTOR));

		Menu viewExercise = new Menu("View Exercise", ROLE.TA, ROLE.INSTRUCTOR);

		viewExercise
				.addAction(new Action("Add Question", new AddQuestionToHomeworkExecutor(), ROLE.TA, ROLE.INSTRUCTOR));
		viewExercise.addAction(
				new Action("Remove Question", new RemoveQuestionFromHomeworkExecutor(), ROLE.TA, ROLE.INSTRUCTOR));

		viewCourse.addMenu(viewExercise);

		applicationRoot.addMenu(courseManagement);

		Menu enrollment = new Menu("Enrollment", ROLE.TA, ROLE.INSTRUCTOR);

		enrollment.addAction(new Action("Enroll", new EnrollmentExecutor(), ROLE.TA, ROLE.INSTRUCTOR));
		enrollment.addAction(new Action("Drop", new DropCourseExecutor(), ROLE.TA, ROLE.INSTRUCTOR));

		viewCourse.addMenu(enrollment);

		Menu questionBank = new Menu("Question Bank", ROLE.INSTRUCTOR);
		applicationRoot.addMenu(questionBank);

		questionBank.addAction(new Action("Add Question", new AddQuestionToBankExecutor(), ROLE.INSTRUCTOR));

		Menu viewQuestionBank = new Menu("View Question bank", ROLE.INSTRUCTOR);

		questionBank.addMenu(viewQuestionBank);

		viewQuestionBank
				.addAction(new Action("Search (Question Id)", new SearchQuestionByIdExecutor(), ROLE.INSTRUCTOR));
		viewQuestionBank
				.addAction(new Action("Search (by Topic)", new SearchQuestionByTopicExecutor(), ROLE.INSTRUCTOR));

		setParent(null, applicationRoot);

	}

	private void setParent(Menu parent, Menu current) {

		current.setParent(parent);

		for (Menu m : current.getMenus()) {

			setParent(current, m);

		}
	}

}
