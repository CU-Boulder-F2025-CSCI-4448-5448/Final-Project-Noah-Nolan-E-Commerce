package paymentStrategy;

public class PaymentPlan extends PayStragety {
    @Override
    public void pay(Double amount) {
        double monthlyPay = amount /12;
        System.out.println("Payment Plan set up: Pay $" + monthlyPay + " for the next 12 months.");

    }
}
