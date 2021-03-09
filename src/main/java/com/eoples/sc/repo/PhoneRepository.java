package com.eoples.sc.repo;

import com.eoples.sc.models.Phone;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PhoneRepository extends CrudRepository<Phone, Long> {

    @Query(value = "SELECT * FROM phone u WHERE u.value >= :priceFrom and u.value <= :priceTo", nativeQuery = true)
    public List<Phone> getPhonesByPrice(@Param("priceFrom") int priceFrom, @Param("priceTo") int priceTo);

    @Query(value = "SELECT * FROM phone u WHERE u.id = :id", nativeQuery = true)
    public Phone getPhoneById(@Param("id") long id);


}
