package paymentStrategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentStrategyTest {

    @Test
    void testCashPayment_NoChange() {
        PaymentStrategy cash = new Cash();
        double amount = 100.0;
        double result = cash.calculateFinalAmount(amount);
        assertEquals(100.0, result);
    }

    @Test
    void testCreditCardPayment_ThreePercentFee() {
        PaymentStrategy cc = new CreditCard();
        double amount = 100.0;
        double result = cc.calculateFinalAmount(amount);
        double expected = 103.0;
        assertEquals(expected, result);
    }

    @Test
    void testPaymentPlan_TenPercentInterest() {
        PaymentStrategy plan = new PaymentPlan();
        double amount = 100.0;
        double result = plan.calculateFinalAmount(amount);
        double expected = 110.0;
        assertEquals(expected, result, .01);
    }
}
