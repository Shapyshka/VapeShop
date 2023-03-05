package com.example.restik.repository;


import com.example.restik.models.news;
import org.springframework.data.repository.CrudRepository;

public interface newsrepository extends CrudRepository <news, Long> {

}
