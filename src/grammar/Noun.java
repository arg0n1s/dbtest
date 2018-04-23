package grammar;
import java.util.HashMap;

public class Noun extends Word {
	private Gender gender;
	private HashMap<Case, String> singularCases;
	private HashMap<Case, String> pluralCases;
	private HashMap<Case, Integer> singularCaseArticles;
	private HashMap<Case, Integer> pluralCaseArticles;
	
	public Noun(int ID, String baseForm) {
		super(ID, baseForm, WordType.noun);
	};
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public HashMap<Case, String> getSingularCases() {
		return singularCases;
	}

	public void setSingularCases(HashMap<Case, String> singularCases) {
		this.singularCases = singularCases;
	}

	public HashMap<Case, String> getPluralCases() {
		return pluralCases;
	}

	public void setPluralCases(HashMap<Case, String> pluralCases) {
		this.pluralCases = pluralCases;
	}

	public HashMap<Case, Integer> getSingularCaseArticles() {
		return singularCaseArticles;
	}

	public void setSingularCaseArticles(HashMap<Case, Integer> singularCaseArticles) {
		this.singularCaseArticles = singularCaseArticles;
	}

	public HashMap<Case, Integer> getPluralCaseArticles() {
		return pluralCaseArticles;
	}

	public void setPluralCaseArticles(HashMap<Case, Integer> pluralCaseArticles) {
		this.pluralCaseArticles = pluralCaseArticles;
	}
	
}
