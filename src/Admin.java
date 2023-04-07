import java.util.HashMap;

public class Admin extends User {

	public Admin(int uid, String username, String surname, int houseNumber, String postcode, String city) {
		super(uid, username, surname, houseNumber, postcode, city);
	}

	public void addBook(HashMap<Long, Book> bookList, Book book) {
		bookList.put(book.getBarcode(), book);
		FileReadWrite.writeBook(book);
	}

	public String toString () {
		return String.format("%d, %s, %s, %d, %s, %s, , customer", this.getUid(), this.getUsername(), this.getSurname(), this.getHouseNumber(), this.getPostcode(), this.getCity());
	}
}
