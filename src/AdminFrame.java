import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminFrame extends JFrame {

    private DefaultTableModel dtmBooks;
    private JTextField barcodeField;
    private JTextField titleField;
    private JTextField dateField;
    private JTextField quantityField;
    private JTextField priceField;
    private JTextField extraField1;
    private JComboBox languageCB;
    private JComboBox genreCB;
    private JComboBox extraCB;

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
        JPanel contentPane = new JPanel();
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
        JTable bookTable = new JTable();
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

        // Create a panel for adding a hardcover book
        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Add eBook", null, panel_2, null);
        panel_2.setLayout(null);

        // Create a panel for adding an audiobook
        JPanel panel_3 = new JPanel();
        tabbedPane.addTab("Add audiobook", null, panel_3, null);
        panel_3.setLayout(null);

        // Add an event listener to the tabbed pane to update when different "Add books" options are selected
        tabbedPane.addChangeListener(e -> {
            int chosenTab = tabbedPane.getSelectedIndex();
            if (chosenTab == 1) {
                panel_2.removeAll();
                panel_3.removeAll();
                createPanel(panel_1, 1);
                panel_1.revalidate();
                panel_1.repaint();
            } else if (chosenTab == 2) {
                panel_1.removeAll();
                panel_3.removeAll();
                createPanel(panel_2, 2);
                panel_2.revalidate();
                panel_2.repaint();
            } else {
                panel_1.removeAll();
                panel_2.removeAll();
                createPanel(panel_3, 3);
                panel_3.revalidate();
                panel_3.repaint();
            }
        });
    }

    private void createPanel(JPanel panel, int chosenTab) {
        // Add labels and input fields for the general attributes of a book
        JLabel panelTitleLabel = new JLabel();
        panelTitleLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        panelTitleLabel.setBounds(332, 11, 191, 53);
        panel.add(panelTitleLabel);

        JLabel barcodeLabel = new JLabel("Barcode");
        barcodeLabel.setBounds(25, 74, 86, 17);
        panel.add(barcodeLabel);

        barcodeField = new JTextField();
        barcodeField.setBounds(147, 71, 86, 20);
        panel.add(barcodeField);
        barcodeField.setColumns(10);

        JLabel titleLabel = new JLabel("Title");
        titleLabel.setBounds(25, 124, 86, 17);
        panel.add(titleLabel);

        titleField = new JTextField();
        titleField.setBounds(147, 121, 86, 20);
        panel.add(titleField);
        titleField.setColumns(10);

        JLabel languageLabel = new JLabel("Language");
        languageLabel.setBounds(25, 168, 86, 17);
        panel.add(languageLabel);

        languageCB = new JComboBox(BookLanguage.values());
        languageCB.setBounds(147, 165, 124, 20);
        panel.add(languageCB);

        JLabel genreLabel = new JLabel("Genre");
        genreLabel.setBounds(25, 219, 86, 18);
        panel.add(genreLabel);

        genreCB = new JComboBox(BookGenre.values());
        genreCB.setBounds(147, 215, 124, 22);
        panel.add(genreCB);

        JLabel dateLabel = new JLabel("Date");
        dateLabel.setBounds(25, 267, 86, 17);
        panel.add(dateLabel);

        dateField = new JTextField();
        dateField.setBounds(147, 264, 86, 20);
        panel.add(dateField);
        dateField.setColumns(10);

        JLabel quantityLabel = new JLabel("Quantity");
        quantityLabel.setBounds(25, 316, 86, 17);
        panel.add(quantityLabel);

        quantityField = new JTextField();
        quantityField.setBounds(147, 313, 86, 20);
        panel.add(quantityField);
        quantityField.setColumns(10);

        JLabel priceLabel = new JLabel("Retail Price");
        priceLabel.setBounds(25, 364, 86, 17);
        panel.add(priceLabel);

        priceField = new JTextField();
        priceField.setBounds(147, 361, 86, 20);
        panel.add(priceField);
        priceField.setColumns(10);

        JLabel extraLabel1 = new JLabel();
        extraLabel1.setBounds(303, 267, 86, 17);
        panel.add(extraLabel1);

        extraField1 = new JTextField();
        extraField1.setBounds(399, 265, 86, 20);
        panel.add(extraField1);
        extraField1.setColumns(10);

        JLabel extraLabel2 = new JLabel();
        extraLabel2.setBounds(303, 215, 86, 17);
        panel.add(extraLabel2);

        // Create a button to add the book to the list
        JButton btnAddBook = new JButton("Add Book");
        btnAddBook.setBounds(332, 361, 191, 23);
        panel.add(btnAddBook);


        if (chosenTab == 1) {
            panelTitleLabel.setText("Add Paperback");
            extraLabel1.setText("Pages");
            extraLabel2.setText("Condition");

            extraCB = new JComboBox(Condition.values());
            extraCB.setBounds(399, 215, 124, 20);
            panel.add(extraCB);

            // Add an action listener to the button
            btnAddBook.addActionListener(e -> addPaperback());

        } else if (chosenTab == 2) {
            panelTitleLabel.setText("Add eBook");
            extraLabel1.setText("Pages");
            extraLabel2.setText("Format");

            extraCB = new JComboBox(Format.values());
            extraCB.setBounds(399, 215, 124, 20);
            panel.add(extraCB);

            // Add an action listener to the button
            btnAddBook.addActionListener(e -> addEbook());
        } else {
            panelTitleLabel.setText("Add Audiobook");
            extraLabel1.setText("Length");
            extraLabel2.setText("Format");

            extraCB = new JComboBox(Format.values());
            extraCB.setBounds(399, 215, 124, 20);
            panel.add(extraCB);

            // Add an action listener to the button
            btnAddBook.addActionListener(e -> addAudiobook());
        }
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
            int pages = Integer.parseInt(extraField1.getText());
            Condition condition = (Condition) extraCB.getSelectedItem();

            FileReadWrite.writeBook(new Paperback(barcode, title, language, genre, date, quantity, price, pages, condition));
            JOptionPane.showMessageDialog(null, "Book added successfully");
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(null, "Please enter valid data");
        }
    }

    private void addEbook() {
        try {
            long barcode = Long.parseLong(barcodeField.getText());
            String title = titleField.getText();
            BookLanguage language = (BookLanguage) languageCB.getSelectedItem();
            BookGenre genre = (BookGenre) genreCB.getSelectedItem();
            Date date = Helper.transformToDate(dateField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            float price = Float.parseFloat(priceField.getText());
            int pages = Integer.parseInt(extraField1.getText());
            Format format = (Format) extraCB.getSelectedItem();

            FileReadWrite.writeBook(new eBook(barcode, title, language, genre, date, quantity, price, pages, format));
            JOptionPane.showMessageDialog(null, "Book added successfully");
        }

        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Please enter valid data");
        }
    }

    private void addAudiobook() {
        try {
            long barcode = Long.parseLong(barcodeField.getText());
            String title = titleField.getText();
            BookLanguage language = (BookLanguage) languageCB.getSelectedItem();
            BookGenre genre = (BookGenre) genreCB.getSelectedItem();
            Date date = Helper.transformToDate(dateField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            float price = Float.parseFloat(priceField.getText());
            float length = Float.parseFloat(extraField1.getText());
            Format format = (Format) extraCB.getSelectedItem();

            FileReadWrite.writeBook(new Audiobook(barcode, title, language, genre, date, quantity, price, length, format));
            JOptionPane.showMessageDialog(null, "Book added successfully");
        } catch (Exception e) {
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
