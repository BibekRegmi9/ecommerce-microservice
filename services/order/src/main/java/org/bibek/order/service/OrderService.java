package org.bibek.order.service;

import org.bibek.order.OrderRequest;
import org.bibek.order.OrderResponse;

import java.util.List;

public interface OrderService {
    public Integer createOrder(OrderRequest orderRequest);

    public List<OrderResponse> findAllOrders();

    public OrderResponse findById(Long id);
}
