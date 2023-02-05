package com.example.restik.repository;

import com.example.restik.models.comment;
import com.example.restik.models.products;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface commentrepository extends CrudRepository <comment, Long> {
    List<comment> findByZapis_idOrderByDateDesc(Long id);

    @Query("Select c From comment c Where c.zapis = ?1 and c.rating = ?2")
    List<comment> findByZapisAndRating(products zapis, Boolean rating);

    Iterable<comment> findAllByOrderByDateDesc();

}