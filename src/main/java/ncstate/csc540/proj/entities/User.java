package ncstate.csc540.proj.entities;

import ncstate.csc540.proj.common.ROLE;

public abstract class User {

	protected String firstname;
	protected String lastname;
	protected String id;
	protected String password;
	
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getID() {
		return id;
	}

	public void setID(String ID) {
		this.id = ID;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [ID=" + id + ", password=" + password + "]";
	}
	
	public abstract ROLE getRole();
	

}
