package solutions.egen.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import solutions.egen.model.Item;


@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

}
