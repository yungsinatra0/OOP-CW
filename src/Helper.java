import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Helper {

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
	
	public static String transformFromDate(Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		return formatter.format(date);
	}
	
	public static String capitalize(String word) {
		return (word.substring(0,1).toUpperCase() + word.substring(1));
	}

	public static String transformGenre(BookGenre genre) {
		if (genre == BookGenre.COMPUTERSCIENCE) {
			return "Computer Science";
		}
		else {
			return capitalize(genre.name().toLowerCase());
		}
	}

	public static ArrayList<Book> convertHashMapToArrayList(HashMap<Long, Book> bookMap) {
		Collection<Book> values = bookMap.values();

		return new ArrayList<>(values);
	}
}
