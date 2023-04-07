import java.util.Date;

public abstract class Book {
	private final long barcode;
	private String title;
	private BookLanguage language;
	private BookGenre genre;
	private Date releaseDate;
	private int quantity;
	private float retailPrice;
	
	public Book(long barcode, String title, BookLanguage language, BookGenre genre, Date releaseDate, int quantity, float retailPrice) {
		this.barcode = barcode;
		this.title = title;
		this.language = language;
		this.genre = genre;
		this.releaseDate = releaseDate;
		this.quantity = quantity;
		this.retailPrice = retailPrice;
	}
	
	public long getBarcode() {
		return this.barcode;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public BookLanguage getLanguage() {
		return this.language;
	}
	
	public BookGenre getGenre() {
		return this.genre;
	}
	
	public Date getDate() {
		return this.releaseDate;
	}
	
	public int getQuantity() {
		return this.quantity;
	}
	
	public float getPrice() {
		return this.retailPrice;
	}

	public void setQuantity(int quantity) {
		this.quantity -= quantity;
	}
	
	public abstract String toString();
}