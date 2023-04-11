
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

	/**
	 * Return the uid of the user
	 * @return int uid
	 */
	public int getUid() {
		return this.uid;
	}

	/**
	 * Return the username of the user
	 * @return String username
	 */
	public String getUsername() {
		return this.username;
	}

	/**
	 * Return the surname of the user
	 * @return String surname
	 */
	public String getSurname() {
		return this.surname;
	}

	/**
	 * Return the house number of the user
	 * @return int houseNumber
	 */
	public int getHouseNumber() {
		return this.houseNumber;
	}

	/**
	 * Return the postcode of the user
	 * @return String postcode
	 */
	public String getPostcode() {
		return this.postcode;
	}

	/**
	 * Return the city of the user
	 * @return String city
	 */
	public String getCity() {
		return this.city;
	}

	/**
	 * Return the address of the user
	 * @return String address
	 */
	public String getAddress() {
		return String.format("Number: %s, City: %s, Postcode: %s", Integer.toString(this.houseNumber), this.city, this.postcode);
	}
	public abstract String toString();
}
