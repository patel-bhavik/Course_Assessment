package ncstate.csc540.proj.ui.executors;
import java.time.LocalDate;
import java.util.Scanner;

import ncstate.csc540.proj.services.CourseAscessController;

public class AddCourseExecutor implements IExecutor{

	public boolean execute() {
		
		Scanner sc = new Scanner(System.in);
		CourseAscessController courseAccess = new CourseAscessController();
		
		//Get course details from user
		System.out.print("Please enter the Course ID: ");
		String courseId = sc.nextLine();
		System.out.print("Please enter the Course Name: ");
		String courseName = sc.nextLine();
		System.out.print("Please enter the Start Date of the course(MM-DD-YYYY): ");
		String strStartDate = sc.nextLine();
		System.out.print("Please enter the End Date of the course(MM-DD-YYYY): ");
		String strEndDate = sc.nextLine();
		
		boolean isAdded = false;
		boolean isValidInput = false;
		do{
			String[] startDateParams = strStartDate.split("-");
			String[] endDateParams = strEndDate.split("-");
			if(startDateParams.length == 3 && endDateParams.length == 3){
				isValidInput = true;
				LocalDate startDate = LocalDate.of(Integer.parseInt(startDateParams[2].trim()), Integer.parseInt(startDateParams[0].trim()), Integer.parseInt(startDateParams[1].trim()));
				LocalDate endDate = LocalDate.of(Integer.parseInt(endDateParams[2].trim()), Integer.parseInt(endDateParams[0].trim()), Integer.parseInt(endDateParams[1].trim()));
				
				//Call add service of CourseAccessController
				isAdded = courseAccess.add(courseId.toUpperCase(), courseName, startDate, endDate);
							
				if(isAdded)
					System.out.println("New course successfully added.");
				else
					System.out.println("Adding of course failed becuase of above error.");
			}else{
				System.out.println("Invalid dates. Please enter dates in specified formats.");
				System.out.print("Please enter the Start Date of the course(MM-DD-YYYY): ");
				strStartDate = sc.next();
				System.out.print("Please enter the End Date of the course(MM-DD-YYYY): ");
				strEndDate = sc.next();
			}
		}while(!isValidInput);
	//	sc.close();
		return isAdded;
	}
}
