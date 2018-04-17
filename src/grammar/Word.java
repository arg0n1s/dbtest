package grammar;

public abstract class Word {
	private int ID;
	private String baseForm;
	private WordType type;
	private String language;
	private int languageLevel;
	
	protected Word(String baseForm, int ID, WordType type) {
		this.baseForm = baseForm;
		this.ID = ID;
		this.type = type;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getBaseForm() {
		return baseForm;
	}

	public void setBaseForm(String baseForm) {
		this.baseForm = baseForm;
	}

	public WordType getType() {
		return type;
	}

	public void setType(WordType type) {
		this.type = type;
	}

	public String getLanguageID() {
		return language;
	}

	public void setLanguageID(String language) {
		this.language = language;
	}

	public int getLanguageLevel() {
		return languageLevel;
	}

	public void setLanguageLevel(int languageLevel) {
		this.languageLevel = languageLevel;
	}
	
	
}
