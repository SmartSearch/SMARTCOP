package eu.smart.cdbm.dao;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

public class DBUtil {

	private static final Logger log = Logger.getLogger(DBUtil.class);
	
	/**
	 * Chiude un oggetto SQL generico
	 * 
	 * @param sqlObject
	 *            L'oggetto sul quale effettuare la close
	 */
	public static void close(Object sqlObject) {
		if (sqlObject != null) {
			if (sqlObject instanceof Connection)
				closeConnection((Connection) sqlObject);
			if (sqlObject instanceof Statement)
				closeStatement((Statement) sqlObject);
			if (sqlObject instanceof PreparedStatement)
				closePreparedStatement((PreparedStatement) sqlObject);
			if (sqlObject instanceof ResultSet)
				closeResultSet((ResultSet) sqlObject);
		}
	}

	private static void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException sqle) {
			log.warn("Impossibile chiudere la connessione SQL", sqle);
		}
	}

	private static void closeStatement(Statement statement) {
		try {
			statement.close();
		} catch (SQLException sqle) {
			log.warn("Impossibile chiudere lo statement SQL", sqle);
		}
	}

	private static void closePreparedStatement(
			PreparedStatement preparedStatement) {
		try {
			preparedStatement.close();
		} catch (SQLException sqle) {
			log.warn("Impossibile chiudere il preparedStatement SQL", sqle);
		}
	}

	private static void closeResultSet(ResultSet resultSet) {
		try {
			resultSet.close();
		} catch (SQLException sqle) {
			log.warn("Impossibile chiudere il resultSet SQL", sqle);
		}
	}

	public static String nullToEmptyString(String value) {
		if (value == null)
			return " ";
		return value;
	}

	public static void rollback(Connection connection) {
		try {
			connection.rollback();
		} catch (SQLException e1) {

		}

	}
	
	/**
	 * Restituisce una connessione al KMS - usato per Test interni
	 * 
	 * @param connectionProperties
	 * @param instanceNumber
	 * @return
	 * @throws DBException
	 */
public static Connection getDesktopConnectionKMS() {
		Connection conn = null;

		try {
			String userName = "root";
			String password = "root";
			String url = "jdbc:mysql://localhost/kms";
		

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
			conn.setAutoCommit(true);
		} catch (Exception e) {
			System.err.println("Cannot connect to database server");
		}
		return conn;
	}
}
