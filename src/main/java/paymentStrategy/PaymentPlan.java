package paymentStrategy;

public class PaymentPlan implements PaymentStrategy {

    @Override
    public double calculateFinalAmount(double cartTotal){
        return cartTotal * 1.1;
    }
}
