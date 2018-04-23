package grammar;

public enum WordType {
	
	article("article"), noun("noun"), verb("verb"), adjective("adjective");
	
	private String name;
	
	WordType(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	public static WordType fromString(String type) {
		if(type.equals(article.toString())) {
			return article;
		}
		else if(type.equals(noun.toString())) {
			return noun;
		}
		else if(type.equals(verb.toString())) {
			return verb;
		}
		else if(type.equals(adjective.toString())) {
			return adjective;
		}
		else {
			return null;
		}
	}
	
}
