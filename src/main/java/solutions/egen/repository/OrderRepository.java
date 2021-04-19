package solutions.egen.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import solutions.egen.model.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

}
