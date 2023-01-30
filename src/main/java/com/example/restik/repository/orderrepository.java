package com.example.restik.repository;

import com.example.restik.models.cart;
import com.example.restik.models.orders;
import com.example.restik.models.user;
import org.springframework.data.repository.CrudRepository;

public interface orderrepository extends CrudRepository<orders, Long> {
    Iterable<orders> findByUser_idOrderByDateDesc(Long id);
    Iterable<orders> findAllByOrderByDateDesc();

}
