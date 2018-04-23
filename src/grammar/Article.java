package grammar;

public class Article extends Word {
	private Gender gender;
	private Case grammaticalCase;
	
	public Article(String baseForm, String language, int level) {
		super(baseForm, WordType.article, language, level);
	}
	
	@Override
	public String toString() {
		return super.toString();
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