package ncstate.csc540.proj.ui.executors;

import java.sql.SQLException;
import java.util.Scanner;

import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.CourseEnrollment;
import ncstate.csc540.proj.services.EnrollmentService;

public class DropCourseExecutor  implements IExecutor{

	@Override
	public boolean execute() {
		EnrollmentService enrollment = new EnrollmentService();
		CourseEnrollment courseEnrollment = new CourseEnrollment();
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter Student ID : ");
		courseEnrollment.setStudentId(scanner.nextLine());
		
	//	System.out.print("Enter Course ID : ");
		courseEnrollment.setCourseId(UserSession.getCurrentCourseID());
		
		try {
			enrollment.delete(courseEnrollment);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	//	scanner.close();
		
		return true;
	}

}
