package grammar;

import java.util.HashMap;

public class Adjective extends Word {
	
	private HashMap<Gender, String> singularDeclinations;
	private HashMap<Gender, String> pluralDeclinations;
	
	public Adjective(String baseForm, int ID) {
		super(baseForm, ID, WordType.adjective);
	}

	public HashMap<Gender, String> getSingularDeclinations() {
		return singularDeclinations;
	}

	public void setSingularDeclinations(HashMap<Gender, String> singularDeclinations) {
		this.singularDeclinations = singularDeclinations;
	}

	public HashMap<Gender, String> getPluralDeclinations() {
		return pluralDeclinations;
	}

	public void setPluralDeclinations(HashMap<Gender, String> pluralDeclinations) {
		this.pluralDeclinations = pluralDeclinations;
	}
	
	
}
