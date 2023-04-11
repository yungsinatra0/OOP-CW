import java.util.Date;

public class Paperback extends Book {
	private int pages;
	private Condition condition;

	public Paperback(long barcode, String title, BookLanguage language, BookGenre genre, Date releaseDate, int quantity, float retailPrice, int pages, Condition condition) {
		super(barcode, title, language, genre, releaseDate, quantity, retailPrice);
		this.pages = pages;
		this.condition = condition;		
	}

	/**
	 * Returns the number of pages in the book
	 * @return int pages
	 */
	public int getPages() {
		return this.pages;
	}

	/**
	 * Returns the condition of the book
	 * @return Condition
	 */
	public Condition getCondition() {
		return this.condition;
	}

	/**
	 * Returns a string representation of the book
	 * @return String
	 */
	public String toString() {
		String language =  Helper.capitalize(getLanguage().name().toLowerCase());
		String genre = Helper.transformGenre(getGenre());
		String date = Helper.transformFromDate(getDate());
		String condition = this.condition.name().toLowerCase();
		
		return String.format("%d, paperback, %s, %s, %s, %s, %d, %.2f, %d, %s", getBarcode(), getTitle(), language, genre, date, getQuantity(), getPrice(), pages, condition);
	}
}
