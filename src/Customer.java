import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Customer extends User {
    private float credits;
    ArrayList<Book> basket;

    public Customer(int uid, String username, String surname, int houseNumber, String postcode, String city, float credits, ArrayList<Book> basket) {
        super(uid, username, surname, houseNumber, postcode, city);
        this.credits = credits;
        this.basket = basket;
    }

    /**
     * Adds a book to the customer basket
     * @param book Book to add
     */
    public void addItem(Book book) {
        // Change the quantity of the book in the basket if book already exists if not set quantity to 1
        for (Book b : this.basket) {
            if (b.getBarcode() == book.getBarcode()) {
                b.setQuantity(1, true);
                return;
            }
        }
        book.setQuantity(book.getQuantity() - 1, false);
        this.basket.add(book);
    }

    /**
     * Gets the customer basket
     * @return ArrayList of books in the basket
     */
    public ArrayList<Book> getBasket() {
        return this.basket;
    }

    /**
     * Removes all books from customer basket
     */
    public void clearBasket() {
        this.basket.clear();
    }

    /**
     * Pays for the basket and updates the stock and balance
     * @throws IllegalArgumentException if the customer does not have enough credits to pay for the basket
     */
    public void payBasket() {
        float total = this.getBasketTotal();
        if (this.credits >= total) {
            this.setCredits(total);
            String message = String.format("Thank you for the purchase! £%.2f paid and your remaining credit balance is £%.2f. Your delivery address is %s.", this.getBasketTotal(), this.getCredits(), this.getAddress());
            JOptionPane.showMessageDialog(null, message);
            FileReadWrite.updateBalance(this);
            FileReadWrite.updateStock(this.basket);
            this.clearBasket();
        } else {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    /**
     * Gets the total price of the basket
     * @return float of the total price
     */
    public float getBasketTotal() {
        float total = 0;
        for (Book book : this.basket) {
            total += (book.getPrice() * book.getQuantity());
        }

        return total;
    }

    /**
     * Gets the customer balance
     * @return float of the customer balance
     */
    public float getCredits() {
        return this.credits;
    }

    /**
     * Sets the customer balance by subtracting the amount
     * @param amount float of the amount to set the balance to
     */
    public void setCredits(float amount) {
        this.credits -= amount;
    }

    /**
     * Returns the customer details as a string
     * @return String of the customer details
     */
    public String toString() {
        return String.format("%d, %s, %s, %d, %s, %s, %.2f, customer", this.getUid(), this.getUsername(), this.getSurname(), this.getHouseNumber(), this.getPostcode(), this.getCity(), this.getCredits());
    }

    /**
     * Updates the stock of the books in the basket
     * @param bookList HashMap of all books
     */
    public void updateStock(HashMap<Long, Book> bookList) {
        for (Book book : this.basket) {
            bookList.get(book.getBarcode()).setQuantity(1, false);
        }
    }
}
