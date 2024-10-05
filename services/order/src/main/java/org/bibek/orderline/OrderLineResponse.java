package org.bibek.orderline;

public record OrderLineResponse(
        Integer id,
        double quantity
) { }