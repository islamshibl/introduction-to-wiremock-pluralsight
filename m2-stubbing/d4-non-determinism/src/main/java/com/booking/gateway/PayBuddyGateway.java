package com.booking.gateway;

import com.booking.util.PayBuddyRestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PayBuddyGateway {

    private final PayBuddyRestTemplate restTemplate = new PayBuddyRestTemplate();
    private final String payBuddyBaseUrl;

    public PayBuddyGateway(final String payBuddyHost, final int payBuddyPort) {
        this.payBuddyBaseUrl = String.format("http://%s:%s/", payBuddyHost, payBuddyPort);
    }

    public PayBuddyPaymentResponse makePayment(String paymentId, String creditCardNumber, LocalDate creditCardExpiry, BigDecimal amount) {
        final PayBuddyPaymentRequest request = new PayBuddyPaymentRequest(paymentId, creditCardNumber, creditCardExpiry, amount);
        return restTemplate.postForObject(payBuddyBaseUrl + "/payments", request, PayBuddyPaymentResponse.class);
    }

    public PayBuddyFraudCheckResponse fraudCheck(String creditCardNumber) {
        return restTemplate.getForObject(payBuddyBaseUrl + "/blacklisted-cards/" + creditCardNumber, PayBuddyFraudCheckResponse.class);
    }
}
