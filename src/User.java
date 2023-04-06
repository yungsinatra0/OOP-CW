
public abstract class User {
	private final int uid;
	private String username;
	private String surname;
	private int houseNumber;
	private String postcode;
	private String city;
	

	public User(int uid, String username, String surname, int houseNumber, String postcode, String city) {
		this.uid = uid;
		this.username = username;
		this.surname = surname;
		this.houseNumber = houseNumber;
		this.postcode = postcode;
		this.city = city;
	}
	
	public int getUid() {
		return this.uid;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getSurname() {
		return this.surname;
	}
	
	public String getAddress() {
		return String.format("Number: %s, City: %s, Postcode: %s", Integer.toString(this.houseNumber), this.city, this.postcode);
	}
}
