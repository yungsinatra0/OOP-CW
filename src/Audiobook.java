import java.util.Date;

public class Audiobook extends Book{
	private float length;
	private Format format;

	public Audiobook(long barcode, String title, BookLanguage language, BookGenre genre, Date releaseDate, int quantity, float retailPrice, float length, Format format) {
		super(barcode, title, language, genre, releaseDate, quantity, retailPrice);
		this.length = length;
		this.format = format;
	}
	
	public float getLength() {
		return this.length;
	}
	
	public Format getFormat() {
		return this.format;
	}
	
	public String toString() {
		String language =  Helper.capitalize(getLanguage().name());
		String genre = Helper.capitalize(getGenre().name());
		String date = Helper.transformFromDate(getDate());
		
		return String.format("%d, ebook, %s, %s, %s, %s, %d, %f, %f, %s", getBarcode(), getTitle(), language, genre, date, getQuantity(), getPrice(), length, format.name());
	}
}
