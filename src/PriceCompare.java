import java.util.Comparator;
public class PriceCompare {
    public int compare(Book book1, Book book2) {
        return Float.valueOf(book1.getPrice()).compareTo(Float.valueOf(book2.getPrice()));
    }
}
