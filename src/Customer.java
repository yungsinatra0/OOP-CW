import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Customer extends User {
    private float credits;
    ArrayList<Book> basket;

    public Customer(int uid, String username, String surname, int houseNumber, String postcode, String city, float credits, ArrayList<Book> basket) {
        super(uid, username, surname, houseNumber, postcode, city);
        this.credits = credits;
        this.basket = basket;
    }

    public void addItem(Book book) {
        this.basket.add(book);
    }

    public ArrayList<Book> getBasket() {
        return this.basket;
    }

    public void clearBasket() {
        this.basket.clear();
    }

    public String viewBasket() {
        return this.basket.toString(); // Need a better implementation down the line!
    }

    public void payBasket() {
        float total = this.getBasketTotal();
        if (this.credits >= total) {
            this.setCredits(total);
            // TODO: Add code to remove books from stock
            String message = String.format("Thank you for the purchase! £%.2f paid and your remaining credit balance is £%.2f. Your delivery address is %s.", this.getBasketTotal(), this.getCredits(), this.getAddress());
            JOptionPane.showMessageDialog(null, message);
            this.clearBasket();
        } else {
            throw new IllegalArgumentException("Insufficient funds");
        }
    }

    public float getBasketTotal() {
        float total = 0;
        for (Book book : this.basket) {
            total += book.getPrice();
        }
        return total;
    }

    public float getCredits() {
        return this.credits;
    }

    private void setCredits(float amount) {
        this.credits -= amount;
    }

}
