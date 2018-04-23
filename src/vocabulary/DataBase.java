package vocabulary;
import grammar.Word;
import grammar.WordFactory;
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
	
	public boolean closeDataBase() {
		try {
			sqlDataBase.disconnect();
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
			
			cmd =	"CREATE TABLE WORDS " +
					"(ID			INT		PRIMARY KEY		NOT NULL, " + 
					" BASE_FORM		TEXT     				NOT NULL, " +
					" LANGUAGE		TEXT     				NOT NULL, " +
					" LEVEL			INT     				NOT NULL, " +
					" TYPE			TEXT     				NOT NULL )";
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
	
	public boolean addWord(Word word) {
		String cmd = "INSERT INTO WORDS (ID, BASE_FORM, LANGUAGE, LEVEL, TYPE) " +
                "VALUES ("	+ word.getID() + ", '" 
							+ word.getBaseForm() + "', '" 
							+ word.getLanguage() + "', "
							+ word.getLanguageLevel() + ", '"
							+ word.getType().toString() + "' );";
		try {
			sqlDataBase.smallUpdate(cmd);
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public Word getWord(int ID) {
		String cmd = "SELECT * FROM WORDS WHERE ID = "+ID+";";
		ResultSet result;
		try {
			result = sqlDataBase.query(cmd);
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		
		Word word = null;
		try {
			word = WordFactory.create(result.getString("TYPE"), result.getString("BASE_FORM"), result.getInt("ID"));
			word.setLanguage(result.getString("LANGUAGE"));
			word.setLanguageLevel(result.getInt("LEVEL"));
		} catch (RuntimeException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return word;
	}
}
