package vocabulary;

public class WordFactory {
	private static Word create(WordType type, String baseForm, int ID) {
		Word word = null;
		switch(type) {
			case article: {
				word = new Article(baseForm, ID);
			}
			case noun: {
				word = new Noun(baseForm, ID);
			}
			case verb: {
				word = new Verb(baseForm, ID);
			}
			case adjective: {
				word = new Adjective(baseForm, ID);
			}
		}
		
		return word;
	}
	
	public static Word createNoun(String baseForm, int ID, Gender gender) {
		Noun n = (Noun)create(WordType.noun, baseForm, ID);
		n.setGender(gender);
		return n;
	}
	
	public static Word createArticle(String baseForm, int ID, Gender gender, Case grammaticalCase) {
		Article a = (Article)create(WordType.article, baseForm, ID);
		a.setGender(gender);
		a.setGrammaticalCase(grammaticalCase);
		return a;
	}
	
	public static Word createVerb(String baseForm, int ID, boolean regular) {
		Verb v = (Verb)create(WordType.verb, baseForm, ID);
		v.setRegular(regular);
		return v;
	}
	
	public static Word createAdjective(String baseForm, int ID) {
		Adjective ad = (Adjective)create(WordType.adjective, baseForm, ID);
		return ad;
	}
}
