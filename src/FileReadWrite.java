import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class FileReadWrite {
    public static HashMap<String, User> readUsers() {
        HashMap<String, User> userList = new HashMap<String, User>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("UserAccounts.txt"));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(", ");
                if (elements[7].equals("admin")) {
                    Admin tempUser = new Admin(Integer.parseInt(elements[0]), elements[1], elements[2], Integer.parseInt(elements[3]), elements[4], elements[5]);
                    userList.put(tempUser.getUsername(), tempUser);
                } else {
                    Customer tempUser = new Customer(Integer.parseInt(elements[0]), elements[1], elements[2], Integer.parseInt(elements[3]), elements[4], elements[5], Float.parseFloat(elements[6]), new ArrayList<Book>());
                    userList.put(tempUser.getUsername(), tempUser);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static void updateBalance(User user) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("UserAccounts.txt"));
            String line;
            String input = "";
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(", ");
                int uid = Integer.parseInt(elements[0]);
                if (uid == user.getUid()) {
                    input += user.toString() + "\n";
                } else {
                    input += line + "\n";
                }
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter("UserAccounts.txt"));
            writer.write(input);
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }


    public static HashMap<Long, Book> readBooks() {
        HashMap<Long, Book> bookMap = new HashMap<Long, Book>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Stock.txt"));
            String line = null;

            while ((line = reader.readLine()) != null) {

                String[] elements = line.split(", ");
                long barcode = Long.parseLong(elements[0]);
                String title = elements[2];
                BookLanguage language = BookLanguage.valueOf(elements[3].toUpperCase().trim());
                BookGenre genre = BookGenre.valueOf(elements[4].toUpperCase().trim().replaceAll("\\s+", ""));
                Date releaseDate = Helper.transformToDate(elements[5]);
                int quantity = Integer.parseInt(elements[6]);
                float retailPrice = Float.parseFloat(elements[7]);

                if (elements[1].equals("paperback")) {
                    int length = Integer.parseInt(elements[8]);
                    Condition bookCondition = Condition.valueOf(elements[9].toUpperCase().trim());
                    Paperback tempBook = new Paperback(barcode, title, language, genre, releaseDate, quantity, retailPrice, length, bookCondition);
                    bookMap.put(tempBook.getBarcode(), tempBook);
                } else if (elements[1].equals("ebook")) {
                    int length = Integer.parseInt(elements[8]);
                    Format format = Format.valueOf(elements[9].toUpperCase().trim());
                    eBook tempBook = new eBook(barcode, title, language, genre, releaseDate, quantity, retailPrice, length, format);
                    bookMap.put(tempBook.getBarcode(), tempBook);
                } else {
                    float length = Float.parseFloat(elements[8]);
                    Format format = Format.valueOf(elements[9].toUpperCase().trim());
                    Audiobook tempBook = new Audiobook(barcode, title, language, genre, releaseDate, quantity, retailPrice, length, format);
                    bookMap.put(tempBook.getBarcode(), tempBook);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bookMap;
    }

    public static void writeBook(Book book) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Stock.txt", true));
            writer.newLine();
            writer.write(book.toString());
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public static void updateStock(ArrayList<Book> books) {
        // Get book object and update the quantity of that book in stock.txt
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Stock.txt"));
            String line = null;
            boolean found = false;
            String input = "";
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(", ");
                found = false;
                for (Book book : books) {
                    if (Long.parseLong(elements[0]) == book.getBarcode()) {
                        input += book.toString() + "\n";
                        found = true;
                    }
                }
                if (!found) {
                    input += line + "\n";
                }
            }
            reader.close();
            BufferedWriter writer = new BufferedWriter(new FileWriter("Stock.txt"));
            writer.write(input);
            writer.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
