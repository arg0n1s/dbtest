package vocabulary;
import grammar.Word;
import grammar.WordFactory;
import grammar.WordType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

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
		boolean mainTableExists = false, wordTableExists = false, relationTableExists = false;
		
		try {
			ResultSet rs = sqlDataBase.query(cmd);
			if(rs.getInt("COUNT(*)") > 0) {
				mainTableExists = true;
				
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		
		cmd = "SELECT COUNT(*) FROM sqlite_master WHERE name='WORDS'";
		try {
			ResultSet rs = sqlDataBase.query(cmd);
			if(rs.getInt("COUNT(*)") > 0) {
				wordTableExists = true;
				
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		
		cmd = "SELECT COUNT(*) FROM sqlite_master WHERE name='RELATIONS'";
		try {
			ResultSet rs = sqlDataBase.query(cmd);
			if(rs.getInt("COUNT(*)") > 0) {
				relationTableExists = true;
				
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		
		if(!mainTableExists) {
			cmd =	"CREATE TABLE LANGUAGES " +
					"(LANGUAGE	TEXT	NOT NULL, " + 
					" LEVEL		INT     NOT NULL, " +
					" PRIMARY KEY (LANGUAGE, LEVEL) )";
			try {
				sqlDataBase.smallUpdate(cmd);
			} catch (SQLException e) {
				System.out.println(e.getClass().getName() + ": " + e.getMessage());
				return false;
			}
			
			
		}
		
		if(!wordTableExists) {
			cmd =	"CREATE TABLE WORDS " +
					"(ID			INT		PRIMARY KEY		NOT NULL, " + 
					" BASE_FORM		TEXT     				NOT NULL, " +
					" LANGUAGE		TEXT     				NOT NULL, " +
					" LEVEL			INT     				NOT NULL, " +
					" TYPE			TEXT     				NOT NULL, " +
					" FOREIGN KEY (LANGUAGE) REFERENCES LANGUAGES (LANGUAGE) " +
					" ON DELETE CASCADE ON UPDATE NO ACTION, " +
					" FOREIGN KEY (LEVEL) REFERENCES LANGUAGES (LEVEL) " +
					" ON DELETE CASCADE ON UPDATE NO ACTION )";
			try {
				sqlDataBase.smallUpdate(cmd);
			} catch (SQLException e) {
				System.out.println(e.getClass().getName() + ": " + e.getMessage());
				return false;
			}
		}
		
		if(!relationTableExists) {
			cmd =	"CREATE TABLE RELATIONS " +
					"(ID1			INT		NOT NULL, " + 
					" ID2			INT		NOT NULL, " +
					" LANGUAGE1		TEXT	NOT NULL, " +
					" LANGUAGE2		TEXT    NOT NULL, " +
					" PRIMARY KEY (ID1, ID2) " +
					" FOREIGN KEY (ID1) REFERENCES WORDS (ID) " +
					" ON DELETE CASCADE ON UPDATE NO ACTION, " +
					" FOREIGN KEY (ID2) REFERENCES WORDS (ID) " +
					" ON DELETE CASCADE ON UPDATE NO ACTION, " +
					" FOREIGN KEY (LANGUAGE1) REFERENCES LANGUAGES (LANGUAGE) " +
					" ON DELETE CASCADE ON UPDATE NO ACTION, " +
					" FOREIGN KEY (LANGUAGE2) REFERENCES LANGUAGES (LANGUAGE) " +
					" ON DELETE CASCADE ON UPDATE NO ACTION )";
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
			word = WordFactory.create(result.getString("BASE_FORM"), result.getString("TYPE"), 
					result.getString("LANGUAGE"), result.getInt("LEVEL"));
		} catch (RuntimeException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
		}
		
		return word;
	}
	
	public Word getWord(String baseForm, String language, WordType type) {
		return getWord(Word.createID(baseForm, language, type));
	}
	
	public boolean addTranslation(Word word1, Word word2) {
		String cmd = "INSERT INTO RELATIONS (ID1, ID2, LANGUAGE1, LANGUAGE2) " +
                "VALUES ("	+ word1.getID() + ", " 
							+ word2.getID() + ", '" 
							+ word1.getLanguage() + "', '"
							+ word2.getLanguage() + "' );";
		try {
			sqlDataBase.smallUpdate(cmd);
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return false;
		}
		
		return true;
	}
	
	public LinkedList<Word> getTranslations(Word word, String language) {
		/*
		String cmd = "SELECT * FROM RELATIONS WHERE ID1 = "+word.getID()+" AND LANGUAGE1 = '"+word.getLanguage()
					+"' AND LANGUAGE2 = '"+language+"';";
		ResultSet result;
		LinkedList<Integer> retrievedIDs = new LinkedList<Integer>();
		try {
			result = sqlDataBase.query(cmd);
			while(result.next()) {
				retrievedIDs.add(result.getInt("ID2"));
			}
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		ArrayList<Word> retrievedWords = new ArrayList<Word>();
		for(Integer id : retrievedIDs) {
			retrievedWords.add(getWord(id));
		}
		return retrievedWords;
		*/
		String cmd = "SELECT W.BASE_FORM AS BASE_FORM, W.TYPE AS TYPE, W.LANGUAGE AS LANGUAGE, W.LEVEL AS LEVEL " +
		"FROM WORDS AS W " +
		"INNER JOIN RELATIONS AS REL ON (REL.ID1 = W.ID) " + 
		"INNER JOIN RELATIONS AS REL ON (REL.ID2 = W.ID) " + 
		"INNER JOIN RELATIONS AS REL ON (REL.LANGUAGE2 = W.LANGUAGE) " +
		"WHERE REL.ID1 = "+word.getID()+" AND REL.LANGUAGE2 = '"+language+"';";
		ResultSet result;
		LinkedList<Word> retrievedWords = new LinkedList<Word>();
		try {
			result = sqlDataBase.query(cmd);
			while(result.next()) {
				Word w = null;
				try {
					w = WordFactory.create(result.getString("BASE_FORM"), result.getString("TYPE"), 
							result.getString("LANGUAGE"), result.getInt("LEVEL"));
				} catch (RuntimeException e) {
					System.out.println(e.getClass().getName() + ": " + e.getMessage());
				} catch (SQLException e) {
					System.out.println(e.getClass().getName() + ": " + e.getMessage());
				}
				if(w!=null) {
					retrievedWords.add(w);
				}
			}
		} catch (SQLException e) {
			System.out.println(e.getClass().getName() + ": " + e.getMessage());
			return null;
		}
		return retrievedWords;
	}
}
