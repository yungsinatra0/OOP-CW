import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
	private static HashMap<String, User> userList = new HashMap<String, User>();
	private static ArrayList<Book> bookList = new ArrayList<Book>();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					userList = FileReadWrite.readUsers();
					bookList = FileReadWrite.readBooks();
					LoginFrame frame = new LoginFrame(userList);
					frame.setVisible(true);
					}
			});
	}
	
	
}
