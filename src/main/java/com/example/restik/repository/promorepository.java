package com.example.restik.repository;

import com.example.restik.models.promos;
import org.springframework.data.repository.CrudRepository;

public interface promorepository extends CrudRepository<promos, Long> {
    promos findByTitle(String title);

}
