package org.bibek.orderline.repository;

import org.bibek.orderline.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}
