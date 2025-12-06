package paymentStrategy;

public class CreditCard implements PaymentStrategy {
    @Override
    public double calculateFinalAmount(double cartTotal){
        return cartTotal * 1.03;
    }
}
