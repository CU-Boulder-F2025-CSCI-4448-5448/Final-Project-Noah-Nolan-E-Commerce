package paymentStrategy;

public class PaymentPlan implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        double monthlyPay = amount /12;
        return "Payment Plan set up: Pay $" + monthlyPay + " for the next 12 months.";
    }
}
