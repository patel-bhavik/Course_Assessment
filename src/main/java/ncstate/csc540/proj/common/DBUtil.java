package ncstate.csc540.proj.common;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

	public static String prepareInsertString(String tableName, String... values) {

		String str = "INSERT INTO " + tableName + " VALUES (";

		for (String v : values) {

			str = str + "'" + v + "',";
		}

		str = str.substring(0, str.length() - 1) + ")";
		return str;

	}

	public static int getCount(String tablename, String colName, String value) throws SQLException {

		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT count(*) FROM " + tablename + " where " + colName + " = " + value);

		rs.next();

		return rs.getInt("count(*)");
	}

	
	public static String getNextID(String ID_COL_NAME, String dbTableName) throws SQLException {

		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT "+ID_COL_NAME+" FROM " + dbTableName);

		int max = -1;

		while (rs.next()) {

			max = Math.max(max, Integer.parseInt(rs.getString(ID_COL_NAME)));
		}

		return "" + (max + 1);
	}
	
	public static String getNextID(String dbTableName) throws SQLException {

		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT ID FROM " + dbTableName);

		int max = -1;

		while (rs.next()) {

			max = Math.max(max, Integer.parseInt(rs.getString("ID")));
		}

		return "" + (max + 1);
	}

	public static boolean deleteTuples(String tableName, String column, String value) throws SQLException {
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate("DELETE FROM " + tableName + " WHERE " + column + " = " + value);

		return true;
	}

}
