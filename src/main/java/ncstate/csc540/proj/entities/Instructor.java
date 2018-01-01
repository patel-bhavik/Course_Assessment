package ncstate.csc540.proj.entities;

import ncstate.csc540.proj.common.ROLE;

public class Instructor extends User {

	@Override
	public ROLE getRole() {
		return ROLE.INSTRUCTOR;
	}
	
}
