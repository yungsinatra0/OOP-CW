import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FileReadWrite {
	private static HashMap<String, User> userList = new HashMap<String, User>();
	private static ArrayList<Book> bookList = new ArrayList<Book>();

	public static HashMap<String, User> readUsers() {
		try {
			BufferedReader reader = new BufferedReader(new FileReader("UserAccounts.txt"));
			String line = null;
			while ((line = reader.readLine()) != null ) {
				String[] elements = line.split(", ");
				if (elements[7].equals("admin")) {
					Admin tempUser = new Admin(Integer.parseInt(elements[0]),elements[1],elements[2],Integer.parseInt(elements[3]), elements[4], elements[5], UserType.ADMIN);
					userList.put(tempUser.getUsername(),tempUser);
				}
				else {
					Customer tempUser = new Customer(Integer.parseInt(elements[0]),elements[1],elements[2],Integer.parseInt(elements[3]), elements[4], elements[5], UserType.CUSTOMER, Float.parseFloat(elements[6]), new ArrayList<Book>() );
					userList.put(tempUser.getUsername(),tempUser);
				}
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userList;
	}
	
	public static ArrayList<Book> readBooks() {
		try {
			
			BufferedReader reader = new BufferedReader(new FileReader("Stock.txt"));
			String line = null;
			
			while ((line = reader.readLine()) != null ) {
				
				String[] elements = line.split(", ");
				long barcode = Long.parseLong(elements[0]);
				String title = elements[2];
				BookLanguage language = BookLanguage.valueOf(elements[3].toUpperCase().trim());
				BookGenre genre = BookGenre.valueOf(elements[4].toUpperCase().trim().replaceAll("\\s+",""));
				Date releaseDate = Helper.transformToDate(elements[5]);				
				int quantity = Integer.parseInt(elements[6]);
				float retailPrice = Float.parseFloat(elements[7]);
					
				if (elements[1].equals("paperback")) {
					int length = Integer.parseInt(elements[8]);
					Condition bookCondition = Condition.valueOf(elements[9].toUpperCase().trim());
					Paperback tempBook = new Paperback(barcode, title, language, genre, releaseDate, quantity, retailPrice, length, bookCondition);
					bookList.add(tempBook);
				}
				else if (elements[1].equals("ebook")) {
					int length = Integer.parseInt(elements[8]);
					Format format = Format.valueOf(elements[9].toUpperCase().trim());
					eBook tempBook = new eBook(barcode, title, language, genre, releaseDate, quantity, retailPrice, length, format);
					bookList.add(tempBook);
				}
				
				else {
					float length = Float.parseFloat(elements[8]);
					Format format = Format.valueOf(elements[9].toUpperCase().trim());
					Audiobook tempBook = new Audiobook(barcode, title, language, genre, releaseDate, quantity, retailPrice, length, format);
					bookList.add(tempBook);
				}
			}
			reader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bookList;
	}
	
	public static void writeBook(Book book) {
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("Stock.txt"));
			writer.write(book.toString());
			writer.close();
		}
		catch(IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	


}
