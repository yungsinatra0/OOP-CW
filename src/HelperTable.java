import javax.lang.model.element.QualifiedNameable;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class HelperTable {
    public static void fillTable(ArrayList<Book> bookList, DefaultTableModel dtmBooks) {
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

    public static void updateTable(DefaultTableModel dtmBooks, Boolean sortByQuantity) {
        ArrayList<Book> bookList = Helper.convertHashMapToArrayList(FileReadWrite.readBooks());
        dtmBooks.setRowCount(0);
        Comparator<Book> comparator = null;
        if (sortByQuantity) {
           comparator = new QuantityCompare();
        } else {
            comparator = new PriceCompare();
        }
        bookList.sort(comparator);
        fillTable(bookList, dtmBooks);
    }
}
