package vocabulary;

import java.util.HashMap;

public class Verb extends Word {
	
	private boolean regular;
	
	private HashMap<Person, String> presentConjugation;
	
	public Verb(String infinitive, int ID) {
		super(infinitive, ID, WordType.verb);
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
