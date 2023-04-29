import java.util.Date;

public class Audiobook extends Book{
	private float length;
	private Format format;

	public Audiobook(long barcode, String title, BookLanguage language, BookGenre genre, Date releaseDate, int quantity, float retailPrice, float length, Format format) {
		super(barcode, title, language, genre, releaseDate, quantity, retailPrice);
		this.length = length;
		this.format = format;
	}

	public Audiobook(Audiobook book) {
		super(book.getBarcode(), book.getTitle(), book.getLanguage(), book.getGenre(), book.getDate(), book.getQuantity(), book.getPrice());
		this.length = book.getLength();
		this.format = book.getFormat();
	}
	/**
	 * Getter for length
	 * @return the length
	 */
	public float getLength() {
		return this.length;
	}

	/**
	 * Getter for format
	 * @return the format
	 */
	public Format getFormat() {
		return this.format;
	}

	/**
	 * Method to return a string representation of the object
	 * @return String
	 */
	public String toString() {
		String language =  Helper.capitalize(getLanguage().name().toLowerCase());
		String genre = Helper.transformGenre(getGenre());
		String date = Helper.transformFromDate(getDate());
		
		return String.format("%d, audiobook, %s, %s, %s, %s, %d, %.2f, %.2f, %s", getBarcode(), getTitle(), language, genre, date, getQuantity(), getPrice(), length, format.name());
	}
}
