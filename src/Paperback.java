import java.util.Date;

public class Paperback extends Book {
	private int pages;
	private Condition condition;

	public Paperback(long barcode, String title, BookLanguage language, BookGenre genre, Date releaseDate, int quantity, float retailPrice, int pages, Condition condition) {
		super(barcode, title, language, genre, releaseDate, quantity, retailPrice);
		this.pages = pages;
		this.condition = condition;		
	}
	
	public int getPages() {
		return this.pages;
	}
	
	public Condition getCondition() {
		return this.condition;
	}

	public String toString() {
		String language =  Helper.capitalize(getLanguage().name().toLowerCase());
		String genre = Helper.transformGenre(getGenre());
		String date = Helper.transformFromDate(getDate());
		String condition = this.condition.name().toLowerCase();
		
		return String.format("%d, paperback, %s, %s, %s, %s, %d, %f, %d, %s", getBarcode(), getTitle(), language, genre, date, getQuantity(), getPrice(), pages, condition);
	}
}
