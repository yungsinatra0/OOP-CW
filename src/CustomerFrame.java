import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CustomerFrame extends JFrame {

    private JTable bookTable;
    private ArrayList<Book> bookList;
    private HashMap<Long, Book> bookMap;
    private DefaultTableModel dtmBooks;
    private DefaultTableModel dtmBasket;
    private JTextField barcodeField;
    private Customer currentUser;
    private JSpinner filterSpinner;
    private JLabel basketTotal;

    /**
     * Create the frame.
     */
    public CustomerFrame(Customer currentUser) {
        this.currentUser = currentUser;

        // Initialize variables
        PriceCompare priceCompare = new PriceCompare();
        bookMap = FileReadWrite.readBooks();
        bookList = Helper.convertHashMapToArrayList(bookMap);
        bookList.sort(priceCompare);

        // Set up frame and content pane
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1145, 640);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Set up tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 11, 1111, 582);
        contentPane.add(tabbedPane);

        // Add "View books" panel to tabbed pane
        JPanel panel = new JPanel();
        tabbedPane.addTab("View books", null, panel, null);
        panel.setLayout(null);

        // Add "View basket" panel to tabbed pane
        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("View Basket", null, panel_1, null);
        panel_1.setLayout(null);

        // Add table to panel
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(10, 60, 1086, 485);
        panel.add(scrollPane);

        // Set up table model and fill it with book data
        bookTable = new JTable();
        scrollPane.setViewportView(bookTable);
        dtmBooks = new DefaultTableModel();
        dtmBooks.setColumnIdentifiers(new Object[]{"Barcode", "Type", "Title", "Language", "Genre", "Date", "Quantity", "Price", "Pages", "Hours", "Format", "Condition"});
        bookTable.setModel(dtmBooks);
        HelperTable.fillTable(bookList, dtmBooks);

        // Add labels and text fields for search to panel
        JLabel searchLabel = new JLabel("Search using barcode");
        searchLabel.setBounds(10, 11, 179, 14);
        panel.add(searchLabel);

        // Add text field for barcode search
        barcodeField = new JTextField();
        barcodeField.setBounds(10, 29, 109, 20);
        panel.add(barcodeField);
        barcodeField.setColumns(10);

        // Add search button to panel
        JButton searchButton = new JButton("Search");
        searchButton.setBounds(129, 28, 89, 23);
        panel.add(searchButton);

        // Add action listener to search button
        searchButton.addActionListener(e -> {
            // Check if input string is a number and is not blank
            if (Helper.isNumeric(barcodeField.getText()) && !barcodeField.getText().equals("")) {
                // Search for book with barcode
                searchBarcode();
            } else {
                // Show error message
                JOptionPane.showMessageDialog(null, "Please enter a valid barcode");
            }
        });

        // Add spinner for filtering by length of AudioBook
        filterSpinner = new JSpinner();
        filterSpinner.setBounds(402, 30, 47, 17);
        panel.add(filterSpinner);
        filterSpinner.setEnabled(false);

        // Add button for adding book to basket
        JButton addBasketButton = new JButton("Add to Basket");
        addBasketButton.setBounds(656, 27, 124, 23);
        panel.add(addBasketButton);

        // Add label to panel for asking user to select books
        JLabel lblNewLabel = new JLabel("Select the books you want to purchase");
        lblNewLabel.setBounds(620, 8, 274, 20);
        panel.add(lblNewLabel);

        JCheckBox filterAudiobooks = new JCheckBox("Filter Audiobooks");
        filterAudiobooks.setBounds(241, 28, 135, 21);
        panel.add(filterAudiobooks);

        // Add refresh button to panel
        JButton refreshButton = new JButton("Refresh table");
        refreshButton.setBounds(972, 7, 124, 23);
        panel.add(refreshButton);

        // Add action listener to refresh button
        refreshButton.addActionListener(e -> {
            // Clear Spinner and text field
            barcodeField.setText("");
            filterSpinner.setValue(0);
            filterSpinner.setEnabled(false);
            filterAudiobooks.setSelected(false);
            // Refresh table
            HelperTable.updateTable(dtmBooks, false);
        });

        // Add button for going back to log in frame
        JButton logOutButton = new JButton("Log out");
        logOutButton.setBounds(1032, 10, 89, 23);
        contentPane.add(logOutButton);
        logOutButton.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame(FileReadWrite.readUsers());
            loginFrame.setVisible(true);
            dispose();
        });

        // Set up table model and fill it with book basket
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(10, 60, 1086, 485);
        panel_1.add(scrollPane_1);

        // Add action listener to filter audiobooks checkbox
        filterAudiobooks.addActionListener(e -> {
            if (filterAudiobooks.isSelected()) {
                filterBooks();
                filterSpinner.setEnabled(true);
            }
            if (!filterAudiobooks.isSelected()) {
                // Refresh table
                HelperTable.updateTable(dtmBooks, false);
                filterSpinner.setEnabled(false);
            }
        });

        // Add change listener to filter spinner
        filterSpinner.addChangeListener(e -> {
            // Check if value is negative and set it to 0 if it is
            if ((int) filterSpinner.getValue() < 0) {
                filterSpinner.setValue(0);
            }
            if (filterAudiobooks.isSelected()) {
                filterBooks();
            }
        });

        // Set up table model in another tab and fill it with basket data
        JTable basketTable = new JTable();
        scrollPane_1.setViewportView(basketTable);
        dtmBasket = new DefaultTableModel();
        dtmBasket.setColumnIdentifiers(new Object[]{"Barcode", "Type", "Title", "Language", "Genre", "Date", "Quantity (in basket)", "Price", "Pages", "Hours", "Format", "Condition"});
        basketTable.setModel(dtmBasket);

        // Add label to show current balance
        JLabel userBalance = new JLabel(String.format("Current balance: %.2f", currentUser.getCredits()));
        userBalance.setBounds(632, 16, 172, 13);
        panel_1.add(userBalance);

        // Add label to show total basket price
        basketTotal = new JLabel("Total basket price: 0.00");
        basketTotal.setBounds(632, 37, 172, 13);
        panel_1.add(basketTotal);

        // Add buttons to panel
        JButton cancelBasketButton = new JButton("Cancel basket");
        cancelBasketButton.setBounds(10, 11, 134, 23);
        panel_1.add(cancelBasketButton);

        // Add action listener to cancel basket button to clear basket and table
        cancelBasketButton.addActionListener(e -> {
            currentUser.clearBasket();
            dtmBasket.setRowCount(0);
            basketTotal.setText("Total basket price: 0.00");
        });

        // Add button to pay for basket
        JButton payBasketButton = new JButton("Pay for basket");
        payBasketButton.setBounds(168, 11, 117, 23);
        panel_1.add(payBasketButton);

        // Add action listener to add to basket button to add book to basket and update table
        addBasketButton.addActionListener(e -> {
            addToBasket();
        });

        // Add action listener to pay basket button to pay for basket and clear basket and table
        payBasketButton.addActionListener(e -> {
            try {
                //TODO: Add method to check if quantity of books in basket is not bigger than stock
                if (currentUser.getBasket().size() == 0) {
                    JOptionPane.showMessageDialog(null, "Basket is empty!");
                    throw new Exception("Basket is empty!");
                } else if (isBasketMoreThanStock()) {
                    JOptionPane.showMessageDialog(null, "There is not enough stock for the books in your basket!");
                    throw new Exception("There is not enough stock for the books in your basket!");
                } else {
                    currentUser.payBasket(); // Pay for basket
                    currentUser.updateStock(bookMap); // Update stock in the bookMap
                    dtmBasket.setRowCount(0); // Clear basket table
                    HelperTable.updateTable(dtmBooks, false); // Refresh book table with updated stock
                    basketTotal.setText(String.format("Total basket price: %.2f", currentUser.getBasketTotal()));
                    userBalance.setText(String.format("Current balance: %.2f", currentUser.getCredits()));
                }
            } catch (Exception exception) {
                // JOptionPane.showMessageDialog(null, exception.getMessage());
            }
        });
    }

    /**
     * Method to search for book by barcode
     */
    private void searchBarcode() {
        ArrayList<Book> searchResult = new ArrayList<Book>();
        // Use bookMap to search for barcode
        long barcode = Long.parseLong(barcodeField.getText());
        if (bookMap.containsKey(barcode)) {
            searchResult.add(bookMap.get(barcode));
            dtmBooks.setRowCount(0);
            HelperTable.fillTable(searchResult, dtmBooks);
        } else {
            JOptionPane.showMessageDialog(null, "Barcode not found");
        }
    }

    /**
     * Method to filter books by length of audiobook
     */
    private void filterBooks() {
        // Get value from spinner
        int hours = (int) filterSpinner.getValue();
        ArrayList<Book> searchResult = new ArrayList<Book>();

        for (Book book : bookList) {
            if (book instanceof Audiobook) {
                if (((Audiobook) book).getLength() > hours) {
                    searchResult.add(book);
                }
            }
        }
        dtmBooks.setRowCount(0);
        HelperTable.fillTable(searchResult, dtmBooks);
    }

    /**
     * Method to add books to basket
     */
    private void addToBasket() {
        try {
            // Get selected rows
            int[] rows = bookTable.getSelectedRows();
            if (rows.length == 0) {
                throw new Exception("Please select at least one book!");
            }
            // Get barcode from selected rows
            for (int row : rows) {
                // Get book from barcode
                long barcode = (long) bookTable.getValueAt(row, 0);
                // Make a copy of the book
                // TODO: Make a copy of the book instead of being a reference to bookMap maybe?
                Book book = bookMap.get(barcode);
                if (book.getQuantity() == 0) {
                    // throw new Exception(String.format("Book %s is out of stock", book.getTitle()));
                    JOptionPane.showMessageDialog(null, String.format("Book %s is out of stock", book.getTitle()));
                } else {
                    // Need to make copy of the book otherwise it references the bookMap
                    if (book instanceof Paperback) {
                        Paperback tempBook = new Paperback((Paperback) book);
                        currentUser.addItem(tempBook);
                    } else if (book instanceof eBook) {
                        eBook tempBook = new eBook((eBook) book);
                        currentUser.addItem(tempBook);
                    } else if (book instanceof Audiobook) {
                        Audiobook tempBook = new Audiobook((Audiobook) book);
                        currentUser.addItem(tempBook);
                    }
                }
            }
            // Update basket table
            HelperTable.fillTable(currentUser.getBasket(), dtmBasket);
            JOptionPane.showMessageDialog(null, "Book(s) successfully added to basket");
            basketTotal.setText(String.format("Total basket price: %.2f", currentUser.getBasketTotal()));
            //Clear selection
            bookTable.clearSelection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    private boolean isBasketMoreThanStock() {
        for (Book book : currentUser.getBasket()) {
            if (book.getQuantity() > bookMap.get(book.getBarcode()).getQuantity()) {
                JOptionPane.showMessageDialog(null, String.format("Book %s has more quantity in basket than stock!", book.getTitle()));
                return true;
            }
        }
        return false;
    }
}
