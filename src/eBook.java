import java.util.Date;

public class eBook extends Book {
	private int pages;
	private Format format;

	public eBook(long barcode, String title, BookLanguage language, BookGenre genre, Date releaseDate, int quantity, float retailPrice, int pages, Format format) {
		super(barcode, title, language, genre, releaseDate, quantity, retailPrice);
		this.pages = pages;
		this.format = format;
	}
	
	public int getPages() {
		return this.pages;
	}
	
	public Format getFormat() {
		return this.format;
	}
	
	public String toString() {
		String language =  Helper.capitalize(getLanguage().name());
		String genre = Helper.capitalize(getGenre().name());
		String date = Helper.transformFromDate(getDate());
		
		return String.format("%d, ebook, %s, %s, %s, %s, %d, %f, %d, %s", getBarcode(), getTitle(), language, genre, date, getQuantity(), getPrice(), pages, format.name());
	}
}
