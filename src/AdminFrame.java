

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminFrame extends JFrame {

    private JPanel contentPane;
    private JTable bookTable;
    private DefaultTableModel dtmBooks;
    private JTextField barcodeField;
    private JTextField titleField;
    private JTextField dateField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField pagesField;
    private JComboBox<BookLanguage> languageCB;
    private JComboBox<BookGenre> genreCB;
    private JComboBox<Condition> conditionCB;

    /**
     * Create the frame.
     */
    public AdminFrame(User currentUser) {
        // Read in the list of books from file and sort them by quantity in descending order
        ArrayList<Book> bookList = FileReadWrite.readBooks();
        QuantityCompare qCompare = new QuantityCompare();
        bookList.sort(qCompare);

        // Set up the main frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 871, 553);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Create a tabbed pane for the different views
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 11, 835, 492);
        contentPane.add(tabbedPane);

        // Create a panel for viewing books
        JPanel panel = new JPanel();
        tabbedPane.addTab("View Books", null, panel, null);
        panel.setLayout(null);

        // Add a scroll pane and table to display the list of books
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 830, 464);
        panel.add(scrollPane);
        bookTable = new JTable();
        scrollPane.setViewportView(bookTable);

        // Set up the table model with column headers
        dtmBooks = new DefaultTableModel();
        dtmBooks.setColumnIdentifiers(new Object[]{"Barcode", "Book type", "Title", "Language", "Genre", "Date", "Quantity", "Retail Price", "Pages", "Hours", "Format", "Condition"});
        bookTable.setModel(dtmBooks);
        fillTable(bookList);

        // Create a panel for adding a paperback book
        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Add Paperback", null, panel_1, null);
        panel_1.setLayout(null);

        // Add labels and input fields for the different attributes of a paperback book
        JLabel lblNewLabel = new JLabel("Add Paperback Book");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setBounds(332, 11, 191, 53);
        panel_1.add(lblNewLabel);

        JLabel barcodeLabel = new JLabel("Barcode");
        barcodeLabel.setBounds(25, 74, 86, 17);
        panel_1.add(barcodeLabel);

        barcodeField = new JTextField();
        barcodeField.setBounds(147, 71, 86, 20);
        panel_1.add(barcodeField);
        barcodeField.setColumns(10);

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(25, 124, 86, 17);
        panel_1.add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(147, 121, 86, 20);
        panel_1.add(titleField);
        titleField.setColumns(10);

        JLabel languageLabel = new JLabel("Language");
        languageLabel.setBounds(25, 168, 86, 17);
        panel_1.add(languageLabel);

        languageCB = new JComboBox(BookLanguage.values());
        languageCB.setBounds(147, 165, 124, 20);
        panel_1.add(languageCB);

        JLabel genreLabel = new JLabel("Genre");
        genreLabel.setBounds(25, 219, 86, 18);
        panel_1.add(genreLabel);

        genreCB = new JComboBox(BookGenre.values());
        genreCB.setBounds(147, 215, 124, 22);
        panel_1.add(genreCB);

        JLabel dateLabel = new JLabel("Date");
        dateLabel.setBounds(25, 267, 86, 17);
        panel_1.add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(147, 264, 86, 20);
        panel_1.add(dateField);
        dateField.setColumns(10);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setBounds(25, 316, 86, 17);
        panel_1.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(147, 313, 86, 20);
        panel_1.add(quantityField);
        quantityField.setColumns(10);

        JLabel priceLabel = new JLabel("Retail Price");
        priceLabel.setBounds(25, 364, 86, 17);
        panel_1.add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(147, 361, 86, 20);
        panel_1.add(priceField);
        priceField.setColumns(10);

        JLabel pagesLabel = new JLabel("Pages");
        pagesLabel.setBounds(25, 412, 86, 17);
        panel_1.add(pagesLabel);

        pagesField = new JTextField();
        pagesField.setBounds(147, 409, 86, 20);
        panel_1.add(pagesField);
        pagesField.setColumns(10);

        JLabel conditionLabel = new JLabel("Condition");
        conditionLabel.setBounds(25, 460, 86, 17);
        panel_1.add(conditionLabel);

        conditionCB = new JComboBox(Condition.values());
        conditionCB.setBounds(147, 457, 124, 20);
        panel_1.add(conditionCB);

        // Create a button to add the book to the list
        JButton btnAddPaperback = new JButton("Add Paperback");

        // Add an action listener to the button
        btnAddPaperback.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addPaperback();
            }
        });

        btnAddPaperback.setBounds(332, 460, 191, 23);
        panel_1.add(btnAddPaperback);

        // Create a panel for adding a hardcover book
        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Add Hardcover", null, panel_2, null);
        panel_2.setLayout(null);

        // Create a panel for adding an audiobook
        JPanel panel_3 = new JPanel();
        tabbedPane.addTab("Add audiobook", null, panel_3, null);
        panel_3.setLayout(null);
    }

    private void addPaperback() {
        try {
            long barcode = Long.parseLong(barcodeField.getText());
            String title = titleField.getText();
            BookLanguage language = (BookLanguage) languageCB.getSelectedItem();
            BookGenre genre = (BookGenre) genreCB.getSelectedItem();
            Date date = Helper.transformToDate(dateField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            float price = Float.parseFloat(priceField.getText());
            int pages = Integer.parseInt(pagesField.getText());
            Condition condition = (Condition) conditionCB.getSelectedItem();

            FileReadWrite.writeBook(new Paperback(barcode, title, language, genre, date, quantity, price, pages, condition));
            JOptionPane.showMessageDialog(null, "Book added successfully");
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Please enter valid data");
        }
    }

    private void fillTable(ArrayList<Book> bookList) {
        for (Book tempBook : bookList) {
            if (tempBook instanceof Paperback) {
                Object[] rowdata = new Object[]{tempBook.getBarcode(), BookType.PAPERBACK, tempBook.getTitle(), tempBook.getLanguage(), tempBook.getGenre(), tempBook.getDate(), tempBook.getQuantity(), tempBook.getPrice(), ((Paperback) tempBook).getPages(), null, null, ((Paperback) tempBook).getCondition()};
                dtmBooks.addRow(rowdata);
            } else if (tempBook instanceof eBook) {
                Object[] rowdata = new Object[]{tempBook.getBarcode(), BookType.EBOOK, tempBook.getTitle(), tempBook.getLanguage(), tempBook.getGenre(), tempBook.getDate(), tempBook.getQuantity(), tempBook.getPrice(), ((eBook) tempBook).getPages(), null, ((eBook) tempBook).getFormat(), null};
                dtmBooks.addRow(rowdata);
            } else {
                Object[] rowdata = new Object[]{tempBook.getBarcode(), BookType.AUDIOBOOK, tempBook.getTitle(), tempBook.getLanguage(), tempBook.getGenre(), tempBook.getDate(), tempBook.getQuantity(), tempBook.getPrice(), null, ((Audiobook) tempBook).getLength(), ((Audiobook) tempBook).getFormat(), null};
                dtmBooks.addRow(rowdata);
            }
        }
    }
}
