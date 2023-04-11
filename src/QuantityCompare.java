import java.util.Comparator;

public class QuantityCompare implements Comparator<Book> {
	/**
	 * Compares two Book objects by quantity.
	 * @param book1 the first object to be compared.
	 * @param book2 the second object to be compared.
	 * @return 0 if equal, 1 if book1 is greater, -1 if book2 is greater.
	 */
	public int compare(Book book1, Book book2) {
		return Integer.valueOf(book1.getQuantity()).compareTo(Integer.valueOf(book2.getQuantity()));
	}
}
