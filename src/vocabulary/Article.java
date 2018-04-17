package vocabulary;

public class Article extends Word {
	private Gender gender;
	private Case grammaticalCase;
	
	public Article(String baseForm, int ID) {
		super(baseForm, ID, WordType.article);
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Case getGrammaticalCase() {
		return grammaticalCase;
	}

	public void setGrammaticalCase(Case grammaticalCase) {
		this.grammaticalCase = grammaticalCase;
	}
	
	
}

/* https://developer.android.com/training/data-storage/sqlite.html#java
 * https://www.tutorialspoint.com/sqlite/sqlite_java.htm
 * http://www.sqlitetutorial.net/sqlite-java/sqlite-jdbc-driver/
 * http://www.oracle.com/technetwork/java/javase/jdbc/index.html
 */