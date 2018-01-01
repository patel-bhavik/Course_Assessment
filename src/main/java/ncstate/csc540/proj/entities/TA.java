package ncstate.csc540.proj.entities;

import ncstate.csc540.proj.common.ROLE;

public class TA extends User {

	@Override
	public ROLE getRole() {
		return ROLE.TA;
	}

}
