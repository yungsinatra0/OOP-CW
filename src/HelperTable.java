import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Comparator;

public class HelperTable {
    public static void fillTable(ArrayList<Book> bookList, DefaultTableModel dtm) {
        dtm.setRowCount(0);
        for (Book tempBook : bookList) {
            if (tempBook instanceof Paperback) {
                Object[] rowdata = new Object[]{tempBook.getBarcode(), BookType.PAPERBACK, tempBook.getTitle(), tempBook.getLanguage(), tempBook.getGenre(), tempBook.getDate(), tempBook.getQuantity(), tempBook.getPrice(), ((Paperback) tempBook).getPages(), null, null, ((Paperback) tempBook).getCondition()};
                dtm.addRow(rowdata);
            } else if (tempBook instanceof eBook) {
                Object[] rowdata = new Object[]{tempBook.getBarcode(), BookType.EBOOK, tempBook.getTitle(), tempBook.getLanguage(), tempBook.getGenre(), tempBook.getDate(), tempBook.getQuantity(), tempBook.getPrice(), ((eBook) tempBook).getPages(), null, ((eBook) tempBook).getFormat(), null};
                dtm.addRow(rowdata);
            } else {
                Object[] rowdata = new Object[]{tempBook.getBarcode(), BookType.AUDIOBOOK, tempBook.getTitle(), tempBook.getLanguage(), tempBook.getGenre(), tempBook.getDate(), tempBook.getQuantity(), tempBook.getPrice(), null, ((Audiobook) tempBook).getLength(), ((Audiobook) tempBook).getFormat(), null};
                dtm.addRow(rowdata);
            }
        }
    }

    public static void updateTable(DefaultTableModel dtmBooks, Boolean sortByQuantity) {
        ArrayList<Book> bookList = Helper.convertHashMapToArrayList(FileReadWrite.readBooks());
        dtmBooks.setRowCount(0);
        Comparator<Book> comparator;
        if (sortByQuantity) {
           comparator = new QuantityCompare();
        } else {
            comparator = new PriceCompare();
        }
        bookList.sort(comparator);
        fillTable(bookList, dtmBooks);
    }
}
