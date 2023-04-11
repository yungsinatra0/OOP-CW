import java.util.Comparator;
public class PriceCompare implements Comparator<Book> {
    /**
     * Compares two books by price
     * @param book1
     * @param book2
     * @return 0 if equal, 1 if book1 is greater, -1 if book2 is greater
     */
    public int compare(Book book1, Book book2) {
        return Float.valueOf(book1.getPrice()).compareTo(Float.valueOf(book2.getPrice()));
    }
}
