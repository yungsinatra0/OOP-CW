import java.awt.EventQueue;
import java.util.HashMap;

public class Main {
	private static HashMap<String, User> userList = new HashMap<String, User>();
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
					userList = FileReadWrite.readUsers();
					LoginFrame frame = new LoginFrame(userList);
					frame.setVisible(true);
					}
			});
	}
}
