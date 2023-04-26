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

	/**
	 * Getter for barcode
	 * @return long
	 */
	public long getBarcode() {
		return this.barcode;
	}

	/**
	 * Getter for title
	 * @return String
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Getter for language
	 * @return BookLanguage
	 */
	public BookLanguage getLanguage() {
		return this.language;
	}

	/**
	 * Getter for genre
	 * @return BookGenre
	 */
	public BookGenre getGenre() {
		return this.genre;
	}

	/**
	 * Getter for releaseDate
	 * @return Date
	 */
	public Date getDate() {
		return this.releaseDate;
	}

	/**
	 * Getter for quantity
	 * @return int
	 */
	public int getQuantity() {
		return this.quantity;
	}

	/**
	 * Getter for retailPrice
	 * @return float
	 */
	public float getPrice() {
		return this.retailPrice;
	}

	/**
	 * Setter for quantity (used for stock). Decrease or increase the quantity of the book by the given quantity based
	 * on the given boolean.
	 * @param quantity int
	 * @param isAdd boolean
	 */
	public void setQuantity(int quantity, boolean isAdd) {
		if (isAdd) {
			this.quantity += quantity;
		} else {
			this.quantity -= quantity;
		}
	}
	
	public abstract String toString();
}