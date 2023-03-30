import java.util.ArrayList;

public class Customer extends User {
	private float credits;
	ArrayList<Book> basket = new ArrayList<Book>();

	public Customer(int uid, String username, String surname, int houseNumber, String postcode, String city, UserType type, float credits, ArrayList<Book> basket) {
		super(uid, username, surname, houseNumber, postcode, city, type);
		this.credits = credits;
		this.basket = basket;
	}
	
	public void addItem(Book book) {
		this.basket.add(book);
	}
	
	public void payBasket() {
		
	}
	
	public void cancelBasket() {
		this.basket.clear();
	}
	
	public String viewBasket() {
		return this.basket.toString(); // Need a better implementation down the line!
	}
	
	public void searchBook() {
		
	}
	
	public float getCredits() {
		return this.credits;
	}
	
	public void setCredits(int amount, boolean isAddition) {
		if (isAddition) {
			this.credits += amount;
		}
		else {
			this.credits -= amount;
		}
	}

}
