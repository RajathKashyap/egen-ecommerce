package solutions.egen.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import solutions.egen.model.Shipping;


@Repository
public interface ShippingRepository extends JpaRepository<Shipping, Long> {

}
