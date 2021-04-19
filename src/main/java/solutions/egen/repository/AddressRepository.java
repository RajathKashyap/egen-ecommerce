package solutions.egen.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import solutions.egen.model.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	
	public Optional<Address> findFirstByAddressLine1AndAddressLine2AndCityAndStateAndZip(String line1, String line2, String city, String state, String zip);

}
