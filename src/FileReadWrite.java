import javax.swing.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FileReadWrite {
    /**
     * Reads the user accounts from the UserAccounts.txt file and returns a HashMap of the users.
     *
     * @return HashMap of users
     */
    public static HashMap<String, User> readUsers() {
        HashMap<String, User> userList = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("UserAccounts.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(", ");
                // If the user is an admin, create an admin object and add it to the HashMap
                if (elements[7].equals("admin")) {
                    Admin tempUser = new Admin(Integer.parseInt(elements[0]), elements[1], elements[2], Integer.parseInt(elements[3]), elements[4], elements[5]);
                    userList.put(tempUser.getUsername(), tempUser);
                } else {
                    // If the user is a customer, create a customer object and add it to the HashMap
                    Customer tempUser = new Customer(Integer.parseInt(elements[0]), elements[1], elements[2], Integer.parseInt(elements[3]), elements[4], elements[5], Float.parseFloat(elements[6]), new ArrayList<>());
                    userList.put(tempUser.getUsername(), tempUser);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * Writes the new user account balance to the UserAccounts.txt file after a transaction has been made.
     *
     * @param user User that has made a transaction
     */
    public static void updateBalance(User user) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("UserAccounts.txt"));
            String line;
            String input = "";
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(", ");
                int uid = Integer.parseInt(elements[0]);
                // If the user id matches the user id of the user that has made a transaction, update the balance
                if (uid == user.getUid()) {
                    input += user.toString() + "\n";
                } else {
                    // If the user id does not match, add the line to the input string
                    input += line + "\n";
                }
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter("UserAccounts.txt"));
            // Write the new input string to the file (overwriting the old file)
            writer.write(input);
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Reads the books from the Stock.txt file and returns a HashMap of the books.
     *
     * @return HashMap of books
     */
    public static HashMap<Long, Book> readBooks() {
        HashMap<Long, Book> bookMap = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Stock.txt"));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(", ");
                // Read the common book details from the file
                long barcode = Long.parseLong(elements[0]);
                String title = elements[2];
                BookLanguage language = BookLanguage.valueOf(elements[3].toUpperCase().trim());
                BookGenre genre = BookGenre.valueOf(elements[4].toUpperCase().trim().replaceAll("\\s+", ""));
                Date releaseDate = Helper.transformToDate(elements[5]);
                int quantity = Integer.parseInt(elements[6]);
                float retailPrice = Float.parseFloat(elements[7]);

                if (elements[1].equals("paperback")) {
                    // If the book is a paperback, read the paperback specific details from the file
                    int length = Integer.parseInt(elements[8]);
                    Condition bookCondition = Condition.valueOf(elements[9].toUpperCase().trim());
                    Paperback tempBook = new Paperback(barcode, title, language, genre, releaseDate, quantity, retailPrice, length, bookCondition);
                    bookMap.put(tempBook.getBarcode(), tempBook);
                } else if (elements[1].equals("ebook")) {
                    // If the book is an ebook, read the ebook specific details from the file
                    int length = Integer.parseInt(elements[8]);
                    Format format = Format.valueOf(elements[9].toUpperCase().trim());
                    eBook tempBook = new eBook(barcode, title, language, genre, releaseDate, quantity, retailPrice, length, format);
                    bookMap.put(tempBook.getBarcode(), tempBook);
                } else {
                    // If the book is an audiobook, read the audiobook specific details from the file
                    float length = Float.parseFloat(elements[8]);
                    Format format = Format.valueOf(elements[9].toUpperCase().trim());
                    Audiobook tempBook = new Audiobook(barcode, title, language, genre, releaseDate, quantity, retailPrice, length, format);
                    bookMap.put(tempBook.getBarcode(), tempBook);
                }
            }
            reader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return bookMap;
    }

    /**
     * Writes a newly added book to the Stock.txt file.
     *
     * @param book Book to be written to the file
     */
    public static void writeBook(Book book) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Stock.txt", true));
            writer.newLine();
            writer.write(book.toString());
            writer.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Updates the quantity of a book in the Stock.txt file.
     *
     * @param books ArrayList of books that have been purchased
     */
    public static void updateStock(ArrayList<Book> books) {
        // Get book object and update the quantity of that book in stock.txt
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Stock.txt"));
            String line;
            boolean found;
            String input = "";
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(", ");
                found = false;
                for (Book book : books) {
                    // If the barcode of the book purchased matches the barcode of the book in the file, update the line
                    // with the new book details
                    if (Long.parseLong(elements[0]) == book.getBarcode()) {
                        // Some freaky mathematics to update the quantity of the book in the file using the quantity of
                        // the book purchased and the quantity of the book in the file
                        // ex: if quantity in file is 17 and quantity in basket is 2, the remaining books should be:
                        // 2 + 17 - 2 * 2 = 15
                        int quantity = Integer.parseInt(elements[6]);
                        book.setQuantity(book.getQuantity() * 2, false);
                        book.setQuantity(quantity, true);
                        input += book.toString() + "\n";
                        found = true; // Set found to true so that the book object is added to the input line instead
                    }
                }
                if (!found) {
                    // If the barcode of the book purchased does not match the barcode of the book in the file,
                    // just add the old line
                    input += line + "\n";
                }
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter("Stock.txt"));
            // Overwrite the old file with the new input string
            writer.write(input);
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
