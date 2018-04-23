package test;

import java.sql.ResultSet;
import java.util.LinkedList;

import vocabulary.DataBase;

import db.SQLiteJDBC;
import grammar.Case;
import grammar.Gender;
import grammar.Word;
import grammar.WordFactory;

public class Test {
	
	public static void test1() {
		SQLiteJDBC driver = SQLiteJDBC.instance();
	      
	      String sql = "CREATE TABLE COMPANY " +
                "(ID INT PRIMARY KEY     NOT NULL," +
                " NAME           TEXT    NOT NULL, " + 
                " AGE            INT     NOT NULL, " + 
                " ADDRESS        CHAR(50), " + 
                " SALARY         REAL)";
	      
	      String[] sqls = new String[4];
	      sqls[0] = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                "VALUES (1, 'Paul', 32, 'California', 20000.00 );";
	      sqls[1] = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                "VALUES (2, 'Allen', 25, 'Texas', 15000.00 );";
	      sqls[2] = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                "VALUES (3, 'Teddy', 23, 'Norway', 20000.00 );";
	      sqls[3] = "INSERT INTO COMPANY (ID,NAME,AGE,ADDRESS,SALARY) " +
                "VALUES (4, 'Mark', 25, 'Rich-Mond ', 65000.00 );";
	      
	      try {
	    	  driver.connect("dbs/test.db");
	    	  System.out.println("Opened database successfully");
	         
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	      
	      try {
	    	  driver.smallUpdate(sql);
	    	  System.out.println("Inserted table successfully");
	         
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	      
	      try {
	    	  driver.largeUpdate(sqls);
	    	  System.out.println("Inserted 4 table entries");
	         
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	      
	      try {
	    	  ResultSet rs = driver.query("SELECT * FROM COMPANY;");
	    	  System.out.println("Retrieved Query: \n");
	    	  
	    	  while ( rs.next() ) {
		            int id = rs.getInt("id");
		            String  name = rs.getString("name");
		            int age  = rs.getInt("age");
		            String  address = rs.getString("address");
		            float salary = rs.getFloat("salary");
		            
		            System.out.println( "ID = " + id );
		            System.out.println( "NAME = " + name );
		            System.out.println( "AGE = " + age );
		            System.out.println( "ADDRESS = " + address );
		            System.out.println( "SALARY = " + salary );
		            System.out.println();
		         }
		         rs.close();
	         
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	      
	      try {
	    	  driver.disconnect();
	    	  System.out.println("Disconnected");
	         
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      }
	}
	
	public static void test2() {
		DataBase db = new DataBase();
		if(db.openDataBase("test2.db")) {
			System.out.println("Opened database successfully");
		}
		if(db.initialize()) {
			System.out.println("Initialized database successfully");
		}
		if(db.addLanguage("Italian", 1)) {
			System.out.println("Language added Successfully");
		}
		if(db.addLanguage("German", 1)) {
			System.out.println("Language added Successfully");
		}
		Word w1 = WordFactory.createAdjective("facile", "Italian", 1);
		Word w2 = WordFactory.createArticle("il", "Italian", 1, Gender.male, Case.nominative);
		Word w3 = WordFactory.createNoun("macchina", "Italian", 1, Gender.female);
		Word w4 = WordFactory.createVerb("andare", "Italian", 1, false);
		
		Word w5 = WordFactory.createAdjective("einfach", "German", 1);
		Word w6 = WordFactory.createArticle("der", "German", 1, Gender.male, Case.nominative);
		Word w7 = WordFactory.createNoun("Auto", "German", 1, Gender.male);
		Word w8 = WordFactory.createVerb("gehen", "German", 1, true);
		
		if(db.addWord(w1)) {
			System.out.println("Word1 added Successfully");
		}
		if(db.addWord(w2)) {
			System.out.println("Word2 added Successfully");
		}
		if(db.addWord(w3)) {
			System.out.println("Word3 added Successfully");
		}
		if(db.addWord(w4)) {
			System.out.println("Word4 added Successfully");
		}
		
		if(db.addWord(w5)) {
			System.out.println("Word5 added Successfully");
		}
		if(db.addWord(w6)) {
			System.out.println("Word6 added Successfully");
		}
		if(db.addWord(w7)) {
			System.out.println("Word7 added Successfully");
		}
		if(db.addWord(w8)) {
			System.out.println("Word8 added Successfully");
		}
		
		w1 = db.getWord(w1.getID());
		w2 = db.getWord(w2.getID());
		w3 = db.getWord(w3.getID());
		w4 = db.getWord(w4.getID());
		if(w1 != null) {
			System.out.println("Word1 retrieved: \n"+w1.toString());
		}
		if(w2 != null) {
			System.out.println("Word2 retrieved: \n"+w2.toString());
		}
		if(w3 != null) {
			System.out.println("Word3 retrieved: \n"+w3.toString());
		}
		if(w4 != null) {
			System.out.println("Word4 retrieved: \n"+w4.toString());
		}
		
		w5 = db.getWord(w5.getID());
		w6 = db.getWord(w6.getID());
		w7 = db.getWord(w7.getID());
		w8 = db.getWord(w8.getID());
		if(w5 != null) {
			System.out.println("Word5 retrieved: \n"+w5.toString());
		}
		if(w6 != null) {
			System.out.println("Word6 retrieved: \n"+w6.toString());
		}
		if(w7 != null) {
			System.out.println("Word7 retrieved: \n"+w7.toString());
		}
		if(w8 != null) {
			System.out.println("Word8 retrieved: \n"+w8.toString());
		}
		
		if(db.addTranslation(w1, w5)) {
			System.out.println("Translation from word1 to word5 added Successfully");
		}
		LinkedList<Word> words = db.getTranslations(w1, "German");
		if(words == null) {
			System.out.println("Retrieving translation from word1 failed.");
		} else {
			
			for(Word word : words) {
				if(word != null) {
					System.out.println("Translation from: \n"+w1.toString()+"\n to: \n"+word.toString()+"\n");
				}else {
					System.out.println("Something weird happened during translation..");
				}
			}
		}
		
		
		if(db.closeDataBase()) {
			System.out.println("Closed database successfully");
		}
		
	}
	
	public static void main( String args[] ) {
		test2();
	}
}
