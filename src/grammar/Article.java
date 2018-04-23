package grammar;

public class Article extends Word {
	private Gender gender;
	private Case grammaticalCase;
	
	public Article(int ID, String baseForm) {
		super(ID, baseForm, WordType.article);
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