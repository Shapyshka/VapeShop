package com.example.restik.repository;

import com.example.restik.models.cart;
import com.example.restik.models.products;
import com.example.restik.models.user;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface cartrepository extends CrudRepository<cart, Long> {

    @Query("Select c From cart c Where c.user = ?1 and c.status = ?2")
    Iterable<cart> findByUser_idAndStatus(user user, String status);

    @Query("Select c From cart c Where c.user = ?1 and c.status = ?2 and c.optIliRozn = ?3")
    Iterable<cart> findByUser_idAndStatusAndOptIliRozn(user user, String status, String optIliRozn);

    @Query("Select c From cart c Where c.user = ?1 and c.product = ?2 and c.status = ?3")
    Iterable<cart> findByUser_idAndProduct_IdAndStatus(user user, Optional<products> products, String status);
    Iterable<cart> findByProduct_id(Long id);

    Iterable<cart> findByOrder_id(Long id);



}
