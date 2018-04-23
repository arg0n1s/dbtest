package grammar;

public class WordFactory {
	
	public static Word create(WordType type, String baseForm, int ID) {
		Word word = null;
		switch(type) {
			case article: {
				word = new Article(ID, baseForm);
			}
			case noun: {
				word = new Noun(ID, baseForm);
			}
			case verb: {
				word = new Verb(ID, baseForm);
			}
			case adjective: {
				word = new Adjective(ID, baseForm);
			}
		}
		
		return word;
	}
	
	public static Word create(String type, String baseForm, int ID) throws RuntimeException {
		WordType t = WordType.fromString(type);
		if(t == null) throw new RuntimeException("Given type: "+type+" does not exist.");
		return create(t, baseForm, ID);
	}
	
	public static Word createNoun(int ID, String baseForm, Gender gender) {
		Noun n = (Noun)create(WordType.noun, baseForm, ID);
		n.setGender(gender);
		return n;
	}
	
	public static Word createArticle(int ID, String baseForm, Gender gender, Case grammaticalCase) {
		Article a = (Article)create(WordType.article, baseForm, ID);
		a.setGender(gender);
		a.setGrammaticalCase(grammaticalCase);
		return a;
	}
	
	public static Word createVerb(int ID, String baseForm, boolean regular) {
		Verb v = (Verb)create(WordType.verb, baseForm, ID);
		v.setRegular(regular);
		return v;
	}
	
	public static Word createAdjective(int ID, String baseForm) {
		Adjective ad = (Adjective)create(WordType.adjective, baseForm, ID);
		return ad;
	}
}
