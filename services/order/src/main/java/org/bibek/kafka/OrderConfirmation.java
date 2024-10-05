package org.bibek.kafka;

import org.bibek.customer.CustomerResponse;
import org.bibek.order.PaymentMethod;
import org.bibek.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation (
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products

) {
}