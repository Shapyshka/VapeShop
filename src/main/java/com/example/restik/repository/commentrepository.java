package com.example.restik.repository;

import com.example.restik.models.comment;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface commentrepository extends CrudRepository <comment, Long> {
    List<comment> findByZapis_idOrderByDateDesc(Long id);

    Iterable<comment> findAllByOrderByDateDesc();

}