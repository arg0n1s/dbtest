package grammar;

import java.util.HashMap;

public class Verb extends Word {
	
	private boolean regular;
	
	private HashMap<Person, String> presentConjugation;
	
	public Verb(String infinitive, String language, int level) {
		super(infinitive, WordType.verb, language, level);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}

	public boolean isRegular() {
		return regular;
	}

	public void setRegular(boolean regular) {
		this.regular = regular;
	}

	public HashMap<Person, String> getPresentConjugation() {
		return presentConjugation;
	}

	public void setPresentConjugation(HashMap<Person, String> presentConjugation) {
		this.presentConjugation = presentConjugation;
	}
	
	
}
