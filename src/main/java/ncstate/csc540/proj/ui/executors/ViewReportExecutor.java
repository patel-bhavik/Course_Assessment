package ncstate.csc540.proj.ui.executors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import ncstate.csc540.proj.common.DBFacade;

public class ViewReportExecutor implements IExecutor {
	
	private static Connection connection = DBFacade.getConnection();

	@Override
	public boolean execute() {
		// TODO Auto-generated method stub
		try {
			String query1 = "SELECT * FROM STUDENTS";
			PreparedStatement getstudentid = connection.prepareStatement(query1);
			ResultSet rs1 = getstudentid.executeQuery();
			while (rs1.next()) 
			{
				String stud_id = rs1.getString(1);
				System.out.println("\nStudent id : " + stud_id);
				System.out.println("First Name : " + rs1.getString(2));
				System.out.println("Last Name : " + rs1.getString(3));
				
				String query2 = "SELECT HW_ID FROM ATTEMPTS WHERE STUD_ID = ? GROUP BY HW_ID";
				PreparedStatement gethwid = connection.prepareStatement(query2);
				gethwid.setString(1, stud_id);
				ResultSet rs2 = gethwid.executeQuery();
				int flag = 1;
				while(rs2.next())
				{
					flag = 0;
					String hw_id = rs2.getString(1);
					//System.out.println("\nHomework id : " + hw_id);
					String query3 = "SELECT NAME, SCORING_POLICY FROM HW_EX WHERE ID = ?";
					
					PreparedStatement gethwname = connection.prepareStatement(query3);
					gethwname.setString(1, hw_id);
					ResultSet rs3 = gethwname.executeQuery();
					String scoring_policy = null;
					if(rs3.next())
					{
						System.out.println("Homework Name : " + rs3.getString(1));
						scoring_policy = rs3.getString(2); 
					}
					
					String query4 = "SELECT TOTAL_SCORE FROM ATTEMPTS WHERE STUD_ID = ? AND HW_ID = ?";
					PreparedStatement gettotalscore = connection.prepareStatement(query4);
					gettotalscore.setString(1, stud_id);
					gettotalscore.setString(2, hw_id);
					String grade = "0";
					ResultSet rs4 = gettotalscore.executeQuery();
					if(scoring_policy.equalsIgnoreCase("maximum_score"))
					{
						System.out.println("Max Score grading");
						while(rs4.next())
						{
							int sum = Integer.parseInt(grade);
							if(Integer.parseInt(rs4.getString(1))  >  sum)
							{
								grade = rs4.getString(1);
							}
						}
					}
					if(scoring_policy.equalsIgnoreCase("latest_attempt"))
					{
						System.out.println("Latest attempt grading");
						while(rs4.next())
						{
								grade = rs4.getString(1);
						}
					}
					System.out.println("Homework grade : " + grade);
					
				}
				if(flag == 1)
				{
					System.out.println("\n No homeworks recorded");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return false;
	}

}
