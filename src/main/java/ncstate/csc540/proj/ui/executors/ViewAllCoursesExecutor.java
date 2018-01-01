package ncstate.csc540.proj.ui.executors;
import java.util.ArrayList;

import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.entities.Course;
import ncstate.csc540.proj.entities.User;
import ncstate.csc540.proj.services.CourseAscessController;

public class ViewAllCoursesExecutor implements IExecutor{

	public boolean execute() {
		
		CourseAscessController courseAccess = new CourseAscessController();
		ArrayList<Course> allCourses = new ArrayList<Course>();
		User currentUser = UserSession.getLoggedInUser();
		
		//Call view all courses service of CourseAccessController
		allCourses = courseAccess.viewAll(currentUser.getID(),currentUser.getRole().toString());
		if(allCourses.size() == 0){
			System.out.println("You don't have any courses.");
			return false;
		}else{
			if(allCourses.get(allCourses.size() - 1).getCourseName() == "ERROR")
				return false;
			System.out.println("Your Courses");
			for(Course course : allCourses){
				System.out.println(course.getCourseId()+" - "+course.getCourseName());
			}
			return true;
		}
	}
}
