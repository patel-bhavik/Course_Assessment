package ncstate.csc540.proj.main;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import ncstate.csc540.proj.common.ROLE;
import ncstate.csc540.proj.common.UserSession;
import ncstate.csc540.proj.ui.executors.IExecutor;
import ncstate.csc540.proj.ui.executors.ViewAllCoursesExecutor;
import ncstate.csc540.proj.ui.executors.ViewCourseExecutor;
import ncstate.csc540.proj.ui.executors.ViewExerciseExecutor;

/**
 * 
 * @author Team
 *
 */
public class Menu implements IOption {

	private Set<ROLE> applicableRoles = new HashSet<>();

	private IOption parent;

	private List<Menu> menus = new LinkedList<>();

	private List<Action> actions = new LinkedList<>();

	private String title;

	public List<Menu> getMenus() {
		return menus;
	}

	public Menu(String title, ROLE... roles) {

		if (roles == null || roles.length <= 0) {

			roles = ROLE.values();
		}

		this.title = title;

		for (ROLE r : roles) {
			applicableRoles.add(r);
		}

		Action backAction = new Action("< Back", new IExecutor() {

			@Override
			public boolean execute() {
				goBack();
				return true;
			}
		});

		backAction.setAccessByPass(true);
		this.actions.add(backAction);

	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	private void goBack() {
		if (getTitle().equalsIgnoreCase("View Exercise")){
			UserSession.setCurrentHomeworkID(null);
		}
		if (getTitle().equalsIgnoreCase("View Course")){
			UserSession.setCurrentCourseID(null);
		}
		((Menu) getParent()).display();
	}

	public Set<ROLE> getApplicableRoles() {
		return applicableRoles;
	}

	public void addMenu(Menu menu) {

		menus.add(menu);
	}

	public void addAction(Action action) {

		actions.add(action);
	}

	void display() {
		
		if (getTitle().startsWith("View Course") && UserSession.getCurrentCourseID() == null) {

			ViewAllCoursesExecutor viewAll = new ViewAllCoursesExecutor();
			if(viewAll.execute()) {
				ViewCourseExecutor courseExecutor = new ViewCourseExecutor();
				if (!courseExecutor.execute()) {
					goBack();
				}
			}else {
				goBack();
			}
		}
		
		if ((getTitle().equalsIgnoreCase("View Exercise") || getTitle().equalsIgnoreCase("View Current Homework")) && UserSession.getCurrentHomeworkID() == null){
			
			ViewExerciseExecutor exerciseExecutor = new ViewExerciseExecutor();
			
			if (!exerciseExecutor.execute()){
				goBack();
			}
		}


		genericDisplay();

	}

	private void genericDisplay() {
		HashMap<Integer, IOption> keyOption = new HashMap<>();

		System.out.println("\n******************* Please Select one of the option below ********************\n\n");

		int option = 1;
		for (Menu menu : menus) {

			if (canAllow(menu)) {
				System.out.println(option + ":\t " + menu.getTitle() + "\n");

				keyOption.put(option, menu);
				option++;
			}

		}

		Collections.sort(actions);

		for (Action action : actions) {

			if (canAllow(action)) {
				System.out.println(option + ":\t " + action.getTitle() + "\n");

				keyOption.put(option, action);

				option++;
			}
		}

		System.out.print("Enter your option : ");
		int selected = 0;
		while (selected == 0) {
			try {
				Scanner scanner = new Scanner(System.in);
				selected = scanner.nextInt();
			} catch (Exception e) {
				System.out.println("Invalid option, re-enter : ");
				selected = 0;

			}

		}

		IOption selectedOption = keyOption.get(selected);

		if (selectedOption instanceof Menu) {
			((Menu) selectedOption).display();
		} else {

			((Action) selectedOption).execute();
			display();

		}
	}

	private boolean canAllow(Action action) {

		return action.byPassAccess() || (UserSession.getLoggedInUser() != null
				&& action.getApplicableRoles().contains(UserSession.getLoggedInUser().getRole()));
	}

	private boolean canAllow(Menu menu) {

		return UserSession.getLoggedInUser() != null
				&& menu.getApplicableRoles().contains(UserSession.getLoggedInUser().getRole());
	}

	@Override
	public String toString() {
		return "Menu [title=" + title + "]";
	}

	public IOption getParent() {
		return parent;
	}

	public void setParent(IOption option) {
		this.parent = option;
	}
}