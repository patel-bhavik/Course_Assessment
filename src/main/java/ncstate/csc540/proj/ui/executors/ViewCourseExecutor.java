package ncstate.csc540.proj.ui.executors;

import java.util.Scanner;

import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.Course;
import ncstate.csc540.proj.services.CourseAscessController;

public class ViewCourseExecutor implements IExecutor {

	public boolean execute() {
		Scanner sc = new Scanner(System.in);
		CourseAscessController courseAccess = new CourseAscessController();
		Course course = null;
		
		//Get Course ID from user
		System.out.print("Please enter the Course ID: ");
		String courseId = sc.next();
	//	sc.close();
		
		//Call view service of CourseAccessController
		course = courseAccess.view(courseId);
		if(course == null){
			System.out.println("Course with Course ID = "+courseId+" does not exist.");
			return false;
		}else{
			if(course.getCourseName() == "ERROR")
				return false;
			
			System.out.println(course);
			UserSession.setCurrentCourseID(courseId);
			return true;
		}
	}
}
