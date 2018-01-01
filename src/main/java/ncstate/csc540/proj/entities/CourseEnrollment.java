package ncstate.csc540.proj.entities;


public class CourseEnrollment{
	private String studentId;
	private String courseId;
	
	public String getStudentId(){
		return studentId;
	}
	
	public void setStudentId(String studentId){
		this.studentId = studentId;
	}
	
	public String getCourseId(){
		return courseId;
	}
	
	public void setCourseId(String courseId){
		this.courseId = courseId;
	}
	
	public static String getDBTableName() {
		return "COURSE_ENROLLMENT";
	}
	
	@Override
	public String toString() {
		return "Enrollment: [CourseID = "+courseId+", studentID = "+studentId+"]";
	}
}
