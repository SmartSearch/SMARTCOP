package eu.smart.cdbm.dao;



import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

public abstract class Dao {
	
	public static final Logger log = Logger.getLogger(DBUtil.class);
	
	


	public Connection getConnection() throws Exception {
		Connection conn = null;
		// Obtain our environment naming context
		Context initCtx;
		
		initCtx = new InitialContext();
		
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		
		// Look up our data source
		DataSource ds = (DataSource) envCtx.lookup("jdbc/smartcop");
		
		// Allocate and use a connection from the pool
		conn = ds.getConnection();
		
		
		if (conn == null)
			throw new Exception("Impossibile creare una connessione");
		return conn;
	}


	protected Connection getDesktopConnection() {
		Connection conn = null;

		try {
			String userName = "root";
			String password = "root";
			String url = "jdbc:mysql://localhost/smartcop";

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, userName, password);
			conn.setAutoCommit(true);
		} catch (Exception e) {
			System.err.println("Cannot connect to database server");
		}
		return conn;
	}
	
	



}
