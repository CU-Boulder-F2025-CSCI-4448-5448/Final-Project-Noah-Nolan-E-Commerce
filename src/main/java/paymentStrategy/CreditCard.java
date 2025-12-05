package paymentStrategy;

public class CreditCard implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        return "Paid $" + amount + " with credit card.";
    }
}
