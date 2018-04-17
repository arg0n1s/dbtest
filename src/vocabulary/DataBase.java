package vocabulary;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.SQLiteJDBC;

public class DataBase {
	private SQLiteJDBC sqlDataBase;
	private String dataBaseFolder;
	
	public DataBase() {
		sqlDataBase = SQLiteJDBC.instance();
		dataBaseFolder = "dbs/";
	}
	
	public boolean openDataBase(String name) {
		try {
			sqlDataBase.connect(dataBaseFolder+name);
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean initialize() {
		String cmd = "SELECT COUNT(*) FROM sqlite_master WHERE name='LANGUAGES'";
		boolean dbIsPresent = false;
		
		try {
			ResultSet rs = sqlDataBase.query(cmd);
			if(rs.getInt("COUNT(*)") > 0) {
				dbIsPresent = true;
				
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		
		if(!dbIsPresent) {
			cmd =	"CREATE TABLE LANGUAGES " +
					"(LANGUAGE	TEXT	PRIMARY KEY		NOT NULL, " + 
					" LEVEL		INT     				NOT NULL, " +
					" UNIQUE	(LANGUAGE, 	LEVEL)	)";
			try {
				sqlDataBase.smallUpdate(cmd);
			} catch (SQLException e) {
				System.out.println(e.getClass().getName() + ": " + e.getMessage());
				return false;
			}
		}
		return true;
	}
	
	public boolean addLanguage(String name, int level) {
		String cmd = "INSERT INTO LANGUAGES (LANGUAGE, LEVEL) " +
                "VALUES ('" + name + "', " + level + ");";
		try {
			sqlDataBase.smallUpdate(cmd);
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		
		return true;
	}
}
