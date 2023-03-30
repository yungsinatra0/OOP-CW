import java.util.Comparator;

public class QuantityCompare implements Comparator<Book> {
	public int compare(Book book1, Book book2) {
		return Integer.valueOf(book1.getQuantity()).compareTo(Integer.valueOf(book2.getQuantity()));
	}
}
