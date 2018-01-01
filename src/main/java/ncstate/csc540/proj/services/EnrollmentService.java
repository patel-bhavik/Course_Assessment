package ncstate.csc540.proj.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.DBUtil;
import ncstate.csc540.proj.entities.CourseEnrollment;

public class EnrollmentService {
	
/*	public static void main(String[] args) {

		try {
			testEnrollmentService();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void testEnrollmentService() throws SQLException {

		CourseEnrollment courseEnrollment = new CourseEnrollment();

		courseEnrollment.setCourseId("CSC540");
		courseEnrollment.setStudentId("abhatt22");

		EnrollmentService service = new EnrollmentService();

		System.out.println("Creating new topic " + service.create(courseEnrollment));

//		System.out.println("Reading created topic = " + service.read(courseEnrollment.getId()));

//		courseEnrollment.setCourseId("CSC540");

//		System.out.println("Updating above topic .. " + service.update(courseEnrollment));
//
//		System.out.println("Reading updated topic = " + service.read(courseEnrollment.getId()));

		System.out.println("Deleting updated topic = " + service.delete(courseEnrollment));

//		System.out.println("Trying to reading deleted topic = " + service.read(courseEnrollment.getId()));
	}*/
	
	public boolean create(CourseEnrollment courseEnrollment) throws SQLException {
		Statement stmt = null;

		stmt = DBFacade.getConnection().createStatement();
	//	System.out.println(DBUtil.prepareInsertString(CourseEnrollment.getDBTableName(), courseEnrollment.getStudentId(), courseEnrollment.getCourseId()));
		int row = stmt.executeUpdate(DBUtil.prepareInsertString(CourseEnrollment.getDBTableName(), courseEnrollment.getStudentId(), courseEnrollment.getCourseId()));
		if (row == 1) {
			System.out.print("Student enrolled successfully!");
		}
		return true;
	}

//	public CourseEnrollment read(String courseId, String studentId) throws SQLException {
//		Statement stmt = null;
//		stmt = DBFacade.getConnection().createStatement();
//		ResultSet rs = stmt.executeQuery("SELECT * FROM " + CourseEnrollment.getDBTableName() + " where course_id = '" + courseId + "' and " + " student_id = '" + studentId + "'");
//
//		CourseEnrollment courseEnrollment = new CourseEnrollment();
//
//		while (rs.next()) {
//
//			courseEnrollment.setCourseId(rs.getString("courseId"));
//			courseEnrollment.setStudentId(rs.getString("studentId"));
//
//		}
//
//		return courseEnrollment;
//	}

//	public boolean updateStudentId(CourseEnrollment courseEnrollment) throws SQLException {
//		Statement stmt = null;
//		stmt = DBFacade.getConnection().createStatement();
//		stmt.executeUpdate("UPDATE " + CourseEnrollment.getDBTableName() + " SET student_id = '" + courseEnrollment.getStudentId() + "' WHERE course_id = '" + courseEnrollment.getCourseId() + "'");
//
//		return true;
//	}
//	
//	public boolean updateCourseId(CourseEnrollment courseEnrollment) throws SQLException {
//		Statement stmt = null;
//		stmt = DBFacade.getConnection().createStatement();
//		stmt.executeUpdate("UPDATE " + CourseEnrollment.getDBTableName() + " SET course_id = '" + courseEnrollment.getCourseId() + "' WHERE student_id = '" + courseEnrollment.getStudentId() + "'");
//
//		return true;
//	}

	public boolean delete(CourseEnrollment courseEnrollment) throws SQLException {
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		int row = stmt.executeUpdate("DELETE FROM " + CourseEnrollment.getDBTableName() + " WHERE course_id='" + courseEnrollment.getCourseId() + "' AND student_id='" + courseEnrollment.getStudentId() + "'");
		if (row == 1) {
			System.out.print("Student dropped from course successfully!");
		}
		return true;
	}

	public List<CourseEnrollment> search(String colName, String value) throws SQLException {
		// TODO Auto-generated method stub
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM " + CourseEnrollment.getDBTableName() + " where " + colName + " = " + value);

		CourseEnrollment courseEnrollment = new CourseEnrollment();

		List<CourseEnrollment> entityList = new LinkedList<CourseEnrollment>();

		while (rs.next()) {

			courseEnrollment.setCourseId(rs.getString("courseId"));
			courseEnrollment.setStudentId(rs.getString("studentId"));

			entityList.add(courseEnrollment);

		}

		return entityList;
	}

}
