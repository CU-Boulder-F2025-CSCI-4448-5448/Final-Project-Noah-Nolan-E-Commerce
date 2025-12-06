package paymentStrategy;

public class Cash implements PaymentStrategy {
    @Override
    public double calculateFinalAmount(double cartTotal){
        return cartTotal;
    }
}
