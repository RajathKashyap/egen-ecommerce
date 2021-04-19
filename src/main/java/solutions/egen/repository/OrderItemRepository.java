package solutions.egen.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import solutions.egen.model.OrderItem;


@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
