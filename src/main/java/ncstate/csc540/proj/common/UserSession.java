package ncstate.csc540.proj.common;

import ncstate.csc540.proj.entities.User;

public class UserSession {

	private static User current;

	private static String currentCourseID;
	
	private static String currentHomeworkID;

	public static void setCurrentUser(User current) {
		UserSession.current = current;
	}

	public static User getLoggedInUser() {
		return current;
	}

	public static void invalidate() {
		setCurrentUser(null);
	}

	public static String getCurrentCourseID() {
		return currentCourseID;
	}

	public static void setCurrentCourseID(String currentCourseID) {
		UserSession.currentCourseID = currentCourseID;
	}

	public static String getCurrentHomeworkID() {
		return currentHomeworkID;
	}

	public static void setCurrentHomeworkID(String currentHomeworkID) {
		UserSession.currentHomeworkID = currentHomeworkID;
	}

}
