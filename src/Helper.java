import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Helper {

	/**
	 * Transforms a string to a date
	 * @param stringDate String to be transformed
	 * @return Date object
	 */
	public static Date transformToDate(String stringDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		try {
			return formatter.parse(stringDate);
		}
		catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Transforms a date to a string
	 * @param date Date to be transformed
	 * @return String object
	 */
	public static String transformFromDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		return formatter.format(date);
	}

	/**
	 * Capitalizes the first letter of a word
	 * @param word Word to be capitalized
	 * @return Capitalized word
	 */
	public static String capitalize(String word) {
		return (word.substring(0,1).toUpperCase() + word.substring(1));
	}

	/**
	 * Transforms a book genre to a string
	 * @param genre Book genre to be transformed
	 * @return String object
	 */
	public static String transformGenre(BookGenre genre) {
		// Special case for Computer Science
		if (genre == BookGenre.COMPUTERSCIENCE) {
			return "Computer Science";
		}
		else {
			return capitalize(genre.name().toLowerCase());
		}
	}

	/**
	 * Convert a HashMap to an ArrayList (used for sorting)
	 * @param bookMap HashMap to be converted
	 * @return ArrayList object
	 */
	public static ArrayList<Book> convertHashMapToArrayList(HashMap<Long, Book> bookMap) {
		Collection<Book> values = bookMap.values();

		return new ArrayList<>(values);
	}
}
