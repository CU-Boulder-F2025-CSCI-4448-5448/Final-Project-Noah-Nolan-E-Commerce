package paymentStrategy;

public class Cash implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        return "Paid $" + amount + " with cash.";
    }
}
