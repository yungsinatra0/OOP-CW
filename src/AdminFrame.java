

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.io.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.Font;
import java.awt.CardLayout;
import javax.swing.JLayeredPane;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminFrame extends JFrame {

    private JPanel contentPane;
    private JTable bookTable;
    private DefaultTableModel dtmBooks;
    private User user;
    private ArrayList<Book> bookList = FileReadWrite.readBooks();
    private QuantityCompare qCompare = new QuantityCompare();
    private JTextField barcodeField;
    private JTextField titleField;
    private JTextField dateField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField pagesField;

    /**
     * Create the frame.
     */
    public AdminFrame(User currentUser) {
        this.user = currentUser;
        Collections.sort(bookList, qCompare);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 871, 553);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 11, 835, 492);
        contentPane.add(tabbedPane);

        JPanel panel = new JPanel();
        tabbedPane.addTab("View Books", null, panel, null);
        panel.setLayout(null);


        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 0, 830, 464);
        panel.add(scrollPane);

        bookTable = new JTable();
        scrollPane.setViewportView(bookTable);

        dtmBooks = new DefaultTableModel();
        dtmBooks.setColumnIdentifiers(new Object[]{"Barcode", "Book type", "Title", "Language", "Genre", "Date", "Quantity", "Retail Price", "Pages", "Hours", "Format", "Condition"});
        bookTable.setModel(dtmBooks);

        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Add paperback", null, panel_1, null);
        panel_1.setLayout(null);

        JLabel lblNewLabel = new JLabel("Add Paperback book");
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        lblNewLabel.setBounds(332, 11, 191, 53);
        panel_1.add(lblNewLabel);

        barcodeField = new JTextField();
        barcodeField.setBounds(147, 71, 86, 20);
        panel_1.add(barcodeField);
        barcodeField.setColumns(10);

        titleField = new JTextField();
        titleField.setBounds(147, 121, 86, 20);
        panel_1.add(titleField);
        titleField.setColumns(10);

        JComboBox languageCB = new JComboBox(BookLanguage.values());
        languageCB.setBounds(147, 165, 124, 20);
        panel_1.add(languageCB);

        JComboBox genreCB = new JComboBox(BookGenre.values());
        genreCB.setBounds(147, 215, 124, 22);
        panel_1.add(genreCB);

        dateField = new JTextField();
        dateField.setBounds(147, 260, 86, 20);
        panel_1.add(dateField);
        dateField.setColumns(10);

        quantityField = new JTextField();
        quantityField.setBounds(147, 310, 86, 20);
        panel_1.add(quantityField);
        quantityField.setColumns(10);

        priceField = new JTextField();
        priceField.setBounds(147, 363, 86, 20);
        panel_1.add(priceField);
        priceField.setColumns(10);

        pagesField = new JTextField();
        pagesField.setBounds(440, 165, 86, 20);
        panel_1.add(pagesField);
        pagesField.setColumns(10);

        JComboBox conditionCB = new JComboBox(Condition.values());
        conditionCB.setBounds(440, 215, 86, 22);
        panel_1.add(conditionCB);

        JLabel barcodeLabel = new JLabel("Barcode");
        barcodeLabel.setBounds(25, 74, 86, 17);
        panel_1.add(barcodeLabel);

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(25, 124, 86, 17);
        panel_1.add(titleLabel);

        JLabel languageLabel = new JLabel("Language");
        languageLabel.setBounds(25, 168, 86, 17);
        panel_1.add(languageLabel);

        JLabel genreLabel = new JLabel("Genre");
        genreLabel.setBounds(25, 219, 86, 18);
        panel_1.add(genreLabel);

        JLabel releaseDateLabel = new JLabel("Release Date");
        releaseDateLabel.setBounds(25, 263, 86, 17);
        panel_1.add(releaseDateLabel);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setBounds(25, 313, 86, 17);
        panel_1.add(quantityLabel);

        JLabel retailPriceLabel = new JLabel("Retail Price");
        retailPriceLabel.setBounds(25, 366, 86, 17);
        panel_1.add(retailPriceLabel);

        JLabel pagesLabel = new JLabel("Pages");
        pagesLabel.setBounds(332, 168, 86, 17);
        panel_1.add(pagesLabel);

        JLabel conditionLabel = new JLabel("Condition");
        conditionLabel.setBounds(332, 219, 86, 18);
        panel_1.add(conditionLabel);

        JButton btnNewButton = new JButton("Add Paperback");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                FileReadWrite.writeBook(new Paperback(13124125, "Test title", BookLanguage.ENGLISH, BookGenre.COMPUTERSCIENCE, Helper.transformToDate("18-03-2001"), 10, (float) 69.9, 100, Condition.USED));
            }
        });
        btnNewButton.setBounds(399, 363, 124, 20);
        panel_1.add(btnNewButton);

        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Add eBook", null, panel_2, null);

        JPanel panel_3 = new JPanel();
        tabbedPane.addTab("Add audiobook", null, panel_3, null);

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
