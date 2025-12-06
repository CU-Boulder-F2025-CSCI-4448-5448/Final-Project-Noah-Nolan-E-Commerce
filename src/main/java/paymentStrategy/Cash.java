package paymentStrategy;

public class Cash implements PaymentStrategy {
    @Override
    public String pay(double amount) {
        String response = "We have received your order. \n";
        response = response + "To pay with cash, please send the total amount owed, $" + amount + ", to: \n";
        response = response + "73 Fairy Ln\n";
        response = response + "Duloc, Far Far Away\n";
        response = response + "When we receive your payment, we will initiate your order fullfilment. Thank you.\n";
        return response;
    }
}
