package ncstate.csc540.proj.main;

import java.util.HashSet;
import java.util.Set;

import ncstate.csc540.proj.common.ROLE;
import ncstate.csc540.proj.ui.executors.IExecutor;

public class Action implements IOption, Comparable {

	private Set<ROLE> applicableRoles = new HashSet<>();

	private boolean byPassAccess = false;

	private IOption parent;

	public IOption getParent() {
		return parent;
	}

	public void setParent(IOption option) {
		this.parent = option;
	}

	private String title;

	private IExecutor executable;

	public Action(String title, IExecutor executable, ROLE... roles) {
		this.title = title;
		this.executable = executable;

		if (roles == null || roles.length <= 0) {

			roles = ROLE.values();
		}

		for (ROLE r : roles) {
			applicableRoles.add(r);
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean execute() {

		if (this.executable == null) {
			System.out.println("Service not yet implemented!");
			return false;
		}

		return this.executable.execute();
	}

	public Set<ROLE> getApplicableRoles() {
		return applicableRoles;
	}

	public boolean byPassAccess() {
		return byPassAccess;
	}

	public void setAccessByPass(boolean accessCheckApplicable) {
		this.byPassAccess = accessCheckApplicable;
	}

	@Override
	public String toString() {
		return "Action [title=" + title + "]";
	}

	@Override
	public int compareTo(Object arg0) {

		if (arg0 instanceof Action) {
			
			if (((Action) arg0).getTitle().contains("Back")) {
				return -1;
			}
		}
		
		return 0;
	}

}
