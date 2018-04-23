package grammar;

public abstract class Word {
	
	private int ID;
	private String baseForm;
	private WordType type;
	private String language;
	private int languageLevel;
	
	protected Word(int ID, String baseForm, WordType type) {
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
	
	public void setType(String type) throws RuntimeException {
		WordType t = WordType.fromString(type);
		if(t==null) throw new RuntimeException("Given type: "+type+" does not exist.");
		this.type = t;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getLanguageLevel() {
		return languageLevel;
	}

	public void setLanguageLevel(int languageLevel) {
		this.languageLevel = languageLevel;
	}
	
	
}
