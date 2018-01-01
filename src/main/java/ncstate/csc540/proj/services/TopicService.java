package ncstate.csc540.proj.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ncstate.csc540.proj.common.DBFacade;
import ncstate.csc540.proj.common.DBUtil;
import ncstate.csc540.proj.entities.Topic;

/**
 * 
 * @author Team
 *
 */
public class TopicService {

	public static void main(String[] args) {

		try {
			testTopicService();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static void testTopicService() throws SQLException {

		Topic topic = new Topic();

		topic.setId(DBUtil.getNextID(topic.getDBTableName()));
		topic.setText(" normalization 3 ");

		TopicService service = new TopicService();

		System.out.println("Creating new topic " + service.create(topic));

		System.out.println("Reading created topic = " + service.read(topic.getId()));

		topic.setText("Normalization 4");

		System.out.println("Updating above topic .. " + service.update(topic));

		System.out.println("Reading updated topic = " + service.read(topic.getId()));

		System.out.println("Deleting updated topic = " + service.delete(topic.getId()));

		System.out.println("Trying to reading deleted topic = " + service.read(topic.getId()));
	}

	public boolean create(Topic entity) throws SQLException {

		Statement stmt = null;

		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate(
				DBUtil.prepareInsertString(Topic.getDBTableName(), entity.getId(), ((Topic) entity).getText()));

		return true;

	}

	public Topic read(String id) throws SQLException {

		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM " + Topic.getDBTableName() + " where ID =" + id);

		Topic topic = new Topic();

		while (rs.next()) {

			topic.setId(rs.getString("ID"));
			((Topic) topic).setText(rs.getString("TEXT"));

		}

		return topic;

	}

	public boolean update(Topic entity) throws SQLException {
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate("UPDATE " + Topic.getDBTableName() + " SET TEXT = '" + ((Topic) entity).getText() + "'");

		return true;
	}

	public boolean delete(String id) throws SQLException {
		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		stmt.executeUpdate("DELETE FROM " + Topic.getDBTableName() + " WHERE ID=" + id);

		return true;
	}

	public List<Topic> search(String colName, String value) throws SQLException {

		Statement stmt = null;
		stmt = DBFacade.getConnection().createStatement();
		ResultSet rs = stmt
				.executeQuery("SELECT * FROM " + Topic.getDBTableName() + " where " + colName + " = " + value);

		Topic topic = new Topic();

		List<Topic> entityList = new LinkedList<Topic>();

		while (rs.next()) {

			topic.setId(rs.getString("ID"));
			((Topic) topic).setText(rs.getString("TEXT"));

			entityList.add(topic);

		}

		return entityList;

	}

}
