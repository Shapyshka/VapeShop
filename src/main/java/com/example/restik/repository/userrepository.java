package com.example.restik.repository;

import com.example.restik.models.user;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface userrepository extends CrudRepository<user, Long> {

    user findByUsername(String username);
    user findByEmail(String email);
    user findByPhonenumber(String phonenumber);

    List<user> findAll();

}
