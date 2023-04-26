import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.util.HashMap;

public class AdminFrame extends JFrame {

    private DefaultTableModel dtmBooks;
    private QuantityCompare qCompare;
    private ArrayList<Book> bookList;
    private HashMap<Long, Book> bookMap;
    private JTextField barcodeField;
    private JTextField titleField;
    private JTextField dateField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField extraField1;
    private JComboBox languageCB;
    private JComboBox genreCB;
    private JComboBox extraCB;
    private JComboBox bookType;
    private Admin currentUser;
    private JPanel contentPane;

    /**
     * Create the frame.
     */
    public AdminFrame(Admin currentUser) {
        // Initialize variables
        this.currentUser = currentUser;

        // Sort the list of books by quantity in ascending order
        qCompare = new QuantityCompare();
        bookMap = FileReadWrite.readBooks();
        bookList = Helper.convertHashMapToArrayList(bookMap);
        bookList.sort(qCompare);

        // Set up the main frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1143, 674);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Add button for going back to log in frame
        JButton logOutButton = new JButton("Log out");
        logOutButton.setBounds(1030, 7, 89, 23);
        contentPane.add(logOutButton);
        logOutButton.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame(FileReadWrite.readUsers());
            loginFrame.setVisible(true);
            dispose();
        });

        // Set up the table model with column headers
        dtmBooks = new DefaultTableModel();
        dtmBooks.setColumnIdentifiers(new Object[]{"Barcode", "Book type", "Title", "Language", "Genre", "Date", "Quantity", "Retail Price", "Pages", "Hours", "Format", "Condition"});
        HelperTable.fillTable(bookList, dtmBooks);

        // Add a scroll pane and table to display the list of books
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 40, 1109, 360);
        contentPane.add(scrollPane);
        JTable bookTable = new JTable();
        scrollPane.setViewportView(bookTable);
        bookTable.setModel(dtmBooks);

        // Add labels and input fields for the 'general attributes' of a book
        JLabel panelTitleLabel = new JLabel();
        panelTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        panelTitleLabel.setBounds(389, 410, 191, 53);
        contentPane.add(panelTitleLabel);
        panelTitleLabel.setText("Add a new book");

        // Label and input field for title
        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(36, 506, 86, 17);
        contentPane.add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(132, 503, 86, 20);
        contentPane.add(titleField);
        titleField.setColumns(10);

        // Label and combo box for Genre
        JLabel genreLabel = new JLabel("Genre");
        genreLabel.setBounds(36, 567, 86, 18);
        contentPane.add(genreLabel);

        genreCB = new JComboBox(BookGenre.values());
        genreCB.setBounds(132, 563, 124, 22);
        contentPane.add(genreCB);

        // Label and input field for Quantity
        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setBounds(293, 506, 86, 17);
        contentPane.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(389, 506, 86, 20);
        contentPane.add(quantityField);
        quantityField.setColumns(10);

        // Label and input field for barcode
        JLabel barcodeLabel = new JLabel("Barcode");
        barcodeLabel.setBounds(36, 476, 86, 17);
        contentPane.add(barcodeLabel);

        barcodeField = new JTextField();
        barcodeField.setBounds(132, 473, 86, 20);
        contentPane.add(barcodeField);
        barcodeField.setColumns(10);

        // Label and input field for Retail Price
        JLabel priceLabel = new JLabel("Retail Price");
        priceLabel.setBounds(293, 536, 86, 17);
        contentPane.add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(389, 536, 86, 20);
        contentPane.add(priceField);
        priceField.setColumns(10);

        // Label and input field for Date
        JLabel dateLabel = new JLabel("Date");
        dateLabel.setBounds(293, 476, 86, 17);
        contentPane.add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(389, 473, 86, 20);
        contentPane.add(dateField);
        dateField.setColumns(10);

        // Label and combo box for Language
        JLabel languageLabel = new JLabel("Language");
        languageLabel.setBounds(36, 536, 86, 17);
        contentPane.add(languageLabel);

        languageCB = new JComboBox(BookLanguage.values());
        languageCB.setBounds(132, 533, 124, 20);
        contentPane.add(languageCB);

        // Label and field that will contain either length or hours
        JLabel extraLabel1 = new JLabel();
        extraLabel1.setBounds(494, 476, 86, 17);
        contentPane.add(extraLabel1);
        extraLabel1.setText("Length");

        extraField1 = new JTextField();
        extraField1.setBounds(589, 475, 86, 20);
        contentPane.add(extraField1);
        extraField1.setColumns(10);

        // Label and combo box that will contain either format or condition
        JLabel extraLabel2 = new JLabel();
        extraLabel2.setBounds(494, 506, 86, 17);
        contentPane.add(extraLabel2);
        extraLabel2.setText("Condition");

        extraCB = new JComboBox(Condition.values());
        extraCB.setBounds(589, 504, 124, 20);
        contentPane.add(extraCB);

        // Create combo box to choose the type of book
        JLabel bookTypeLabel = new JLabel("Book Type");
        bookTypeLabel.setBounds(292, 568, 86, 17);
        contentPane.add(bookTypeLabel);

        bookType = new JComboBox(BookType.values());
        bookType.setBounds(388, 565, 124, 20);
        contentPane.add(bookType);

        // Create a button to add the book to the list
        JButton btnAddBook = new JButton("Add Book");
        btnAddBook.setBounds(770, 565, 191, 23);
        contentPane.add(btnAddBook);

        bookType.addActionListener(e -> {
            BookType chosenType = (BookType) bookType.getSelectedItem();
            if (chosenType == BookType.PAPERBACK) {
                // Change the title of the panel and the labels and input fields for the 'specific attributes' of a paperback
                extraLabel1.setText("Pages");
                extraLabel2.setText("Condition");
                extraCB.removeAllItems();
                for (Condition bookCondition : Condition.values()) {
                    extraCB.addItem(bookCondition);
                }

                // Add an action listener to add book, clear the fields and update the table
                btnAddBook.addActionListener(event -> {
                    addBook(BookType.AUDIOBOOK);
                    clearFields();
                    HelperTable.updateTable(dtmBooks, true);
                });


            } else if (chosenType == BookType.EBOOK) {
                // Change the title of the panel and the labels and input fields for the 'specific attributes' of an eBook
                extraLabel1.setText("Pages");
                extraLabel2.setText("Format");
                extraCB.removeAllItems();
                for (Format bookFormat : Format.values()) {
                    extraCB.addItem(bookFormat);
                }

                // Add an action listener to the button to add book, clear the fields and update the table
                btnAddBook.addActionListener(event -> {
                    addBook(BookType.EBOOK);
                    clearFields();
                    HelperTable.updateTable(dtmBooks, true);
                });

            } else if (chosenType == BookType.AUDIOBOOK) {
                // Change the title of the panel and the labels and input fields for the 'specific attributes' of an audiobook
                extraLabel1.setText("Length");
                extraLabel2.setText("Format");
                extraCB.removeAllItems();
                for (Format bookFormat : Format.values()) {
                    extraCB.addItem(bookFormat);
                }

                // Add an action listener to the button to add book, clear the fields and update the table
                btnAddBook.addActionListener(event -> {
                    addBook(BookType.PAPERBACK);
                    clearFields();
                    HelperTable.updateTable(dtmBooks, true);
                });
            }
        });
    }

    /**
     * Method to add a book to the list
     *
     * @param type the type of book to add
     */
    private void addBook(BookType type) {
        try {
            long barcode = Long.parseLong(barcodeField.getText());

            // Check if barcode already exists in book list using hashmap
            if (bookMap.containsKey(barcode)) {
                JOptionPane.showMessageDialog(null, "Barcode already exists");
                return;
            }

            String title = titleField.getText();
            BookLanguage language = (BookLanguage) languageCB.getSelectedItem();
            BookGenre genre = (BookGenre) genreCB.getSelectedItem();
            Date date = Helper.transformToDate(dateField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            float price = Float.parseFloat(priceField.getText());
            int pages = Integer.parseInt(extraField1.getText());
            Format format = (Format) extraCB.getSelectedItem();

            // Add book to the list depending on the type of book (using different constructors)
            switch (type) {
                case PAPERBACK:
                    Condition condition = (Condition) extraCB.getSelectedItem();
                    currentUser.addBook(bookMap, new Paperback(barcode, title, language, genre, date, quantity, price, pages, condition));
                    break;
                case EBOOK:
                    currentUser.addBook(bookMap, new eBook(barcode, title, language, genre, date, quantity, price, pages, format));
                    break;
                case AUDIOBOOK:
                    float length = Float.parseFloat(extraField1.getText());
                    currentUser.addBook(bookMap, new Audiobook(barcode, title, language, genre, date, quantity, price, length, format));
                    break;
            }
            JOptionPane.showMessageDialog(null, "Book added successfully");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please enter valid data");
        }
    }

    /**
     * Method to clear the fields
     */
    private void clearFields() {
        barcodeField.setText("");
        titleField.setText("");
        languageCB.setSelectedIndex(0);
        genreCB.setSelectedIndex(0);
        dateField.setText("");
        quantityField.setText("");
        priceField.setText("");
        extraField1.setText("");
        extraCB.setSelectedIndex(0);
    }
}
