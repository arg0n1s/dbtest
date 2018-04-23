package grammar;

public enum Case {
	nominative("nominative"), genitive("genitive"), dative("dative"), accusative("accusative");
	
	private String name;
	
	Case(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
