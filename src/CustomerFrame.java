import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class CustomerFrame extends JFrame {

    private JPanel contentPane;
    private JTable bookTable;
    private PriceCompare priceCompare;
    private ArrayList<Book> bookList;
    private HashMap<Long, Book> bookMap;
    private DefaultTableModel dtmBooks;
    private DefaultTableModel dtmBasket;
    private JTextField barcodeField;
    private JSpinner filterSpinner;
    private Customer currentUser;
    private JTable basketTable;

    /**
     * Create the frame.
     */
    public CustomerFrame(Customer currentUser) {
        this.currentUser = currentUser;

        // Initialize variables
        priceCompare = new PriceCompare();
        bookMap = FileReadWrite.readBooks();
        bookList = Helper.convertHashMapToArrayList(bookMap);
        bookList.sort(priceCompare);

        // Set up frame and content pane
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 870, 552);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Set up tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 11, 834, 501);
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
        scrollPane.setBounds(10, 60, 809, 402);
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
        searchLabel.setBounds(10, 11, 109, 14);
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
        filterSpinner.setBounds(358, 30, 47, 17);
        panel.add(filterSpinner);

        // Add refresh button to panel
        JButton refreshButton = new JButton("Refresh table");
        refreshButton.setBounds(695, 7, 124, 23);
        panel.add(refreshButton);

        // Add action listener to refresh button
        refreshButton.addActionListener(e -> {
            // Clear Spinner and text field
            barcodeField.setText("");
            filterSpinner.setValue(0);
            // Refresh table
            HelperTable.updateTable(dtmBooks, false);
        });

        // Add button for adding book to basket
        JButton addBasketButton = new JButton("Add to Basket");
        addBasketButton.addActionListener(e -> {
            addToBasket();
        });
        addBasketButton.setBounds(493, 27, 124, 23);
        panel.add(addBasketButton);

        // Add label to panel for asking user to select books
        JLabel lblNewLabel = new JLabel("Select the books you want to purchase");
        lblNewLabel.setBounds(466, 8, 188, 20);
        panel.add(lblNewLabel);

        JCheckBox filterAudiobooks = new JCheckBox("Filter Audiobooks");
        filterAudiobooks.setBounds(241, 28, 109, 21);
        panel.add(filterAudiobooks);

        // Add button for going back to log in frame
        JButton logOutButton = new JButton("Log out");
        logOutButton.setBounds(755, 7, 89, 23);
        contentPane.add(logOutButton);
        logOutButton.addActionListener(e -> {
            LoginFrame loginFrame = new LoginFrame(FileReadWrite.readUsers());
            loginFrame.setVisible(true);
            dispose();
        });

        // Set up table model and fill it with book basket
        JScrollPane scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(10, 60, 809, 402);
        panel_1.add(scrollPane_1);

        // Add action listener to filter audiobooks checkbox
        filterAudiobooks.addActionListener(e -> {
            if (filterAudiobooks.isSelected()) {
                filterBooks();
            }
            if (!filterAudiobooks.isSelected()) {
                // Refresh table
                HelperTable.updateTable(dtmBooks, false);
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
        basketTable = new JTable();
        scrollPane_1.setViewportView(basketTable);
        dtmBasket = new DefaultTableModel();
        dtmBasket.setColumnIdentifiers(new Object[]{"Barcode", "Type", "Title", "Language", "Genre", "Date", "Quantity", "Price", "Pages", "Hours", "Format", "Condition"});
        basketTable.setModel(dtmBasket);

        // Add buttons to panel
        JButton cancelBasketButton = new JButton("Cancel basket");
        cancelBasketButton.setBounds(10, 11, 134, 23);
        panel_1.add(cancelBasketButton);

        // Add action listener to cancel basket button to clear basket and table
        cancelBasketButton.addActionListener(e -> {
            currentUser.clearBasket();
            dtmBasket.setRowCount(0);
        });

        // Add button to pay for basket
        JButton payBasketButton = new JButton("Pay for basket");
        payBasketButton.setBounds(168, 11, 117, 23);
        panel_1.add(payBasketButton);

        // Add action listener to pay basket button to pay for basket and clear basket and table
        payBasketButton.addActionListener(e -> {
            try {
                currentUser.updateStock(bookMap);
                currentUser.payBasket();
                dtmBasket.setRowCount(0);
                HelperTable.updateTable(dtmBooks, false);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage());
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
                Book book = bookMap.get(barcode);
                if (book.getQuantity() == 0) {
                    throw new Exception(String.format("Book %s is out of stock", book.getTitle()));
                } else {
                    // Add book to basket
                    currentUser.addItem(book);
                }
            }
            JOptionPane.showMessageDialog(null, "Book(s) successfully added to basket");
            // Update basket table
            HelperTable.fillTable(currentUser.getBasket(), dtmBasket);

            //Clear selection
            bookTable.clearSelection();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
