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
    private ArrayList<Book> basketList;
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

        barcodeField = new JTextField();
        barcodeField.setBounds(10, 29, 109, 20);
        panel.add(barcodeField);
        barcodeField.setColumns(10);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(129, 28, 89, 23);
        panel.add(searchButton);

        // Add action listener to search button
        searchButton.addActionListener(e -> searchBarcode());

        // Add labels and spinners for filtering to panel
        JLabel filterLabel = new JLabel("Search by length");
        filterLabel.setBounds(249, 11, 98, 14);
        panel.add(filterLabel);

        filterSpinner = new JSpinner();
        filterSpinner.setBounds(249, 30, 47, 17);
        panel.add(filterSpinner);

        JButton filterButton = new JButton("Filter");
        filterButton.setBounds(326, 26, 89, 23);
        panel.add(filterButton);

        // Add action listener to filter button
        filterButton.addActionListener(e -> filterBooks());

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
        addBasketButton.setBounds(493, 28, 124, 23);
        panel.add(addBasketButton);
        
        JLabel lblNewLabel = new JLabel("Select the books you want to purchase");
        lblNewLabel.setBounds(466, 8, 188, 20);
        panel.add(lblNewLabel);

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
        scrollPane_1.setBounds(10, 60, 725, 402);
        panel_1.add(scrollPane_1);

        basketTable = new JTable();
        scrollPane_1.setViewportView(basketTable);
        dtmBasket = new DefaultTableModel();
        dtmBasket.setColumnIdentifiers(new Object[]{"Barcode", "Type", "Title", "Language", "Genre", "Date", "Quantity", "Price", "Pages", "Hours", "Format", "Condition"});
        basketTable.setModel(dtmBasket);

        // Add buttons to panel
        JButton cancelBasketButton = new JButton("Cancel basket");
        cancelBasketButton.setBounds(10, 11, 134, 23);
        panel_1.add(cancelBasketButton);

        cancelBasketButton.addActionListener(e -> {
            currentUser.clearBasket();
            dtmBasket.setRowCount(0);
        });

        JButton payBasketButton = new JButton("Pay for basket");
        payBasketButton.setBounds(168, 11, 117, 23);
        panel_1.add(payBasketButton);

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
