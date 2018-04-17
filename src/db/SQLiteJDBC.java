package db;
import java.sql.*;

public class SQLiteJDBC {
	private static SQLiteJDBC instance;
	private Connection c;
	private boolean connected;
	
	private SQLiteJDBC() {};
	
	public synchronized static SQLiteJDBC instance() {
		if(instance == null) {
			instance = new SQLiteJDBC();
		}
		return instance;
	}
	
	public void connect(String dbPath) throws SQLException {
		c = DriverManager.getConnection("jdbc:sqlite:"+dbPath);
		connected = true;
	}
	
	public void disconnect() throws SQLException {
		if(!connected) throw new SQLException("Not connected to any data base!");
		c.close();
	}
	
	public void smallUpdate(String sqlCommand) throws SQLException {
		if(!connected) throw new SQLException("Not connected to any data base!");
		Statement stmt = c.createStatement();
		stmt.executeUpdate(sqlCommand);
		stmt.close();
	}
	
	public void largeUpdate(String[] sqlCommands) throws SQLException {
		if(!connected) throw new SQLException("Not connected to any data base!");
		c.setAutoCommit(false);
		Statement stmt = c.createStatement();
		
		for(String cmd : sqlCommands) {
			stmt.executeUpdate(cmd);
		}
		
		stmt.close();
		c.commit();
		c.setAutoCommit(true);
	}
	
	public ResultSet query(String sqlCommand) throws SQLException {
		if(!connected) throw new SQLException("Not connected to any data base!");
		Statement stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(sqlCommand);
        return rs;
	}
	
}
