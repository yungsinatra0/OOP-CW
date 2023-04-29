import java.util.Date;

public class eBook extends Book {
	private int pages;
	private Format format;

	public eBook(long barcode, String title, BookLanguage language, BookGenre genre, Date releaseDate, int quantity, float retailPrice, int pages, Format format) {
		super(barcode, title, language, genre, releaseDate, quantity, retailPrice);
		this.pages = pages;
		this.format = format;
	}

	public eBook(eBook book) {
		super(book.getBarcode(), book.getTitle(), book.getLanguage(), book.getGenre(), book.getDate(), book.getQuantity(), book.getPrice());
		this.pages = book.getPages();
		this.format = book.getFormat();
	}

	/**
	 * Get the pages of the eBook
	 * @return int pages
	 */
	public int getPages() {
		return this.pages;
	}

	/**
	 * Get the format of the eBook
	 * @return Format format
	 */
	public Format getFormat() {
		return this.format;
	}

	/**
	 * Return eBook as a string
	 * @return String eBook
	 */
	public String toString() {
		String language =  Helper.capitalize(getLanguage().name().toLowerCase());
		String genre = Helper.transformGenre(getGenre());
		String date = Helper.transformFromDate(getDate());
		
		return String.format("%d, ebook, %s, %s, %s, %s, %d, %.2f, %d, %s", getBarcode(), getTitle(), language, genre, date, getQuantity(), getPrice(), pages, format.name());
	}
}
