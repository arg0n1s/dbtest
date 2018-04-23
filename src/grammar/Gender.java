package grammar;

public enum Gender {
	male("male"), female("female"), neutral("neutral");
	
	private String name;
	
	Gender(String name) {
		this.name = name;
	}
	
	@Override
	public String toString(){
		return name;
	}
}
