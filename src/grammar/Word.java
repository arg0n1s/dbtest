package grammar;

public abstract class Word {
	
	protected int ID;
	protected String baseForm;
	protected WordType type;
	protected String language;
	protected int languageLevel;
	
	protected Word(String baseForm, WordType type, String language, int level) {
		this.ID = createID(baseForm, language, type);
		this.baseForm = baseForm;
		this.type = type;
		this.language = language;
		this.languageLevel = level;
	}
	
	@Override
	public String toString() {
		String out = "[Word: "+baseForm+", ID: "+ID+", Type: "+type.toString()+
				", Language: "+language+", Level:"+languageLevel+"]";
		return out;
	}
	
	static public int createID(String baseForm, String language, WordType type) {
		String key = baseForm+language+type.toString();
		return key.hashCode();
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
