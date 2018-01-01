package ncstate.csc540.proj.services;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import ncstate.csc540.proj.common.*;
import ncstate.csc540.proj.entities.Course;
import ncstate.csc540.proj.entities.User;

public class CourseAscessController {
	
	//View Course by courseid functionality
	public Course view(String courseId){
		
		//create database connection
		Connection conn = DBFacade.getConnection();
		Course course = null;
		
		//create and execute select query
		String query = "SELECT c.id, c.name, c.start_date, c.end_date, c.instructor_id, i.first_name, i.last_name  " +
						"FROM COURSES c, Instructors i " +
						"WHERE UPPER(c.id) = UPPER(?) " +
							"AND c.instructor_id = i.id";
		try {
			PreparedStatement findCourse = conn.prepareStatement(query);
			findCourse.setString(1,courseId);
			ResultSet resSet;
			resSet = findCourse.executeQuery();
			//return the course
			if(resSet.next()){
				course = new Course();
				course.setCourseId(resSet.getString("id"));
				course.setCourseName(resSet.getString("name"));
				course.setStartDate(resSet.getDate("start_date").toLocalDate());
				course.setEndDate(resSet.getDate("end_date").toLocalDate());
				course.setInstructorId(resSet.getString("instructor_id"));
				String instructorFirstName = resSet.getString("first_name");
				String instructorLastName = resSet.getString("last_name");
				course.setInstructorName(instructorFirstName+" "+instructorLastName);
			}
			return course;
		}catch (SQLException exp) {
			System.out.println(exp.getMessage());
			course = new Course();
			course.setCourseName("ERROR");
			return course;
		}
	}
	
	//View all courses of Instructor
	public ArrayList<Course> viewAll(String userId, String role){
		
		//create database connection
		Connection conn = DBFacade.getConnection();
		ArrayList<Course> allCourses = new ArrayList<Course>();
		//create and execute select query
		String query = null;
		if (role.compareToIgnoreCase("INSTRUCTOR") == 0){
			query = "SELECT c.id, c.name " +
					"FROM COURSES c " +
					"WHERE UPPER(c.instructor_id) = UPPER(?)";
		}else if (role.compareToIgnoreCase("STUDENT") == 0){
			query = "SELECT c.id, c.name " +
					"FROM COURSES c, COURSE_ENROLLMENT e " +
					"WHERE UPPER(e.student_id) = UPPER(?)" +
					"AND e.course_id = c.id";
		}else if (role.compareToIgnoreCase("TA") == 0){
			query = "SELECT c.id, c.name " +
					"FROM COURSES c, TA t " +
					"WHERE UPPER(t.id) = UPPER(?)" +
					"AND t.course_id = c.id";
		}
		PreparedStatement findAllCourses;
		Course course = null;
		try {
			findAllCourses = conn.prepareStatement(query);
			findAllCourses.setString(1,userId);
			ResultSet resSet = findAllCourses.executeQuery();
			
			//return the course
			while(resSet.next()){
				course = new Course();
				course.setCourseId(resSet.getString("id"));
				course.setCourseName(resSet.getString("name"));
				allCourses.add(course);
			}
			return allCourses;
		}catch (SQLException exp) {
			System.out.println(exp.getMessage());
			course = new Course();
			course.setCourseName("ERROR");
			allCourses.add(course);
			return allCourses;
		}
	}
	
	//Add course functionality
	public boolean add(String courseId, String courseName, LocalDate startDate, LocalDate endDate){
		
		//create database connection
		Connection conn = DBFacade.getConnection();
		User currentUser = UserSession.getLoggedInUser();
		if(currentUser.getRole().toString().compareToIgnoreCase("INSTRUCTOR") != 0){
			System.out.println("You are not authorized to add the course.");
			return false;
		}else{
			int numRows = 0;
			//create and execute select query
			String query = "INSERT INTO COURSES (id, name, start_date, end_date, instructor_id) VALUES (?,?,?,?,?)";
			PreparedStatement addCourse;
			try {
				addCourse = conn.prepareStatement(query);
				addCourse.setString(1,courseId);
				addCourse.setString(2,courseName);
				addCourse.setDate(3,java.sql.Date.valueOf(startDate));
				addCourse.setDate(4,java.sql.Date.valueOf(endDate));
				addCourse.setString(5,currentUser.getID());
				numRows = addCourse.executeUpdate();
				if(numRows > 0)
					return true;
				else
					return false;
			}catch (SQLException exp) {
				System.out.println(exp.getMessage());
				return false;
			}
		}
	}
}
