package org.bibek.order.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.bibek.customer.CustomerClient;
import org.bibek.exception.BusinessException;
import org.bibek.kafka.OrderConfirmation;
import org.bibek.kafka.OrderProducer;
import org.bibek.order.OrderMapper;
import org.bibek.order.OrderRequest;
import org.bibek.order.OrderResponse;
import org.bibek.order.repository.OrderRepository;
import org.bibek.order.service.OrderService;
import org.bibek.orderline.OrderLineRequest;
import org.bibek.orderline.service.OrderLineService;
import org.bibek.product.ProductClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Transactional
    @Override
    public Integer createOrder(OrderRequest orderRequest) {

        //fetch customer details from customer service
        var customer = this.customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new BusinessException("Cannot create order, Custome don't exist"));

        var purchasedProduct = this.productClient.purchaseProducts(orderRequest.products());

        // save order request
        var order = this.orderRepository.save(orderMapper.toOrder(orderRequest));

        orderRequest.products().forEach(product -> {
            orderLineService.saveOrderLine(new OrderLineRequest(
                    null,
                    order.getId(),
                    product.productId(),
                    product.quantity()
            ));
        });

        // todo payment process

        // order notification using kafka
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProduct
                )
        );

        return order.getId();

    }

    @Override
    public List<OrderResponse> findAllOrders() {
        return this.orderRepository.findAll()
                .stream().map(orderMapper::fromOrder)
                .collect(Collectors.toList());
    }

    @Override
    public OrderResponse findById(Long id) {
        return this.orderRepository.findById(id)
                .map(this.orderMapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", id)));
    }
}
