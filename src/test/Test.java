package test;

import java.sql.ResultSet;
import vocabulary.DataBase;

import db.SQLiteJDBC;

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
		if(db.addLanguage("Italian", 1)) {
			System.out.println("Language added Successfully");
		}
		if(db.closeDataBase()) {
			System.out.println("Closed database successfully");
		}
		
	}
	
	public static void main( String args[] ) {
		test2();
	}
}
