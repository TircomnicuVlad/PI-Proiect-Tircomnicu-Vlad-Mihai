package package1;

public class Card {
	
	String name;
	int value;
	String shape;
	boolean used = false;
	int id;
	String symbol;
	
	public Card(int n, String s, int z) {
		if (n >= 1 && n <= 10) {
			this.name = Integer.toString(n);
			this.value = n;
			this.symbol = this.name;
		} else if (n>=-10 && n<=-1) {
			this.name = Integer.toString(n);
			this.value = n;
			this.symbol = this.name;
		}
		this.shape = s;
		this.id = z;
	//	System.out.println("New Card : " + name + " of " + shape + " (id = " + id + ")");
	}
	
	public void setUsed() {
		used = true;
	//	System.out.println("The Card  " + name + " of " + shape + " is now used");
	}
	
	public void setNotUsed() {
		used = false;
	//	System.out.println("The Card  " + name + " of " + shape + " is now not used");
	}
	
	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	

	
}