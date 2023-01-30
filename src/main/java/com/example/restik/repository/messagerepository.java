package com.example.restik.repository;

import com.example.restik.models.message;
import com.example.restik.models.user;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface messagerepository extends CrudRepository <message, Long> {
    Iterable<message> findByFrom_idOrTo_idOrderByDateDesc(Long fromid, Long toid);
    Iterable<message> findAllByOrderByDateDesc();

    @Query("Select m From message m Where m.from = ?1 and m.to = ?2")
    List<message> findFromMeToDude(Optional<user> myid, Optional<user> hisid, Sort s);

    @Query("Select m From message m Where m.from = ?2 and m.to = ?1")
    List<message> findToMeFromDude(Optional<user> myid, Optional<user> hisid, Sort s);

}