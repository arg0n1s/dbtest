package grammar;

public class WordFactory {
	
	public static Word create(String baseForm, WordType type, String language, int level) {
		switch(type) {
			case article: {
				return new Article(baseForm, language, level);
			}
			case noun: {
				return new Noun(baseForm, language, level);
			}
			case verb: {
				return new Verb(baseForm, language, level);
			}
			case adjective: {
				return new Adjective(baseForm, language, level);
			}
			default : return null;
		}
	}
	
	public static Word create(String baseForm, String type, String language, int level) throws RuntimeException {
		WordType t = WordType.fromString(type);
		if(t == null) throw new RuntimeException("Given type: "+type+" does not exist.");
		return create(baseForm, t, language, level);
	}
	
	public static Word createNoun(String baseForm, String language, int level, Gender gender) {
		Noun n = (Noun)create(baseForm, WordType.noun, language, level);
		n.setGender(gender);
		return n;
	}
	
	public static Word createArticle(String baseForm, String language, int level, Gender gender, Case grammaticalCase) {
		Article a = (Article)create(baseForm, WordType.article, language, level);
		a.setGender(gender);
		a.setGrammaticalCase(grammaticalCase);
		return a;
	}
	
	public static Word createVerb(String baseForm, String language, int level, boolean regular) {
		Verb v = (Verb)create(baseForm, WordType.verb, language, level);
		v.setRegular(regular);
		return v;
	}
	
	public static Word createAdjective(String baseForm, String language, int level) {
		Adjective ad = (Adjective)create(baseForm, WordType.adjective, language, level);
		return ad;
	}
}
