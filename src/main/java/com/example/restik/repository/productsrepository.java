package com.example.restik.repository;

import com.example.restik.models.products;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface productsrepository extends CrudRepository <products, Long> {
    Iterable<products> findByOptIliRoznOrderByDateDesc(String optIliRozn);

    @Query("Select l From products l Where l.oldprice != null and l.optIliRozn = ?1")
    Iterable<products> findSpecials(String optIliRozn);

    @Query("Select p From products p Where p.typeofproduct = ?1 and p.optIliRozn = ?2")
    Iterable<products> findByTypeofproductAndOptIliRozn(String type, String optOtRozn, Sort date);


//    @Query("Select p From products p Where p.typeofproduct = ?1 and p.optIliRozn = ?2")
//    Iterable<products> findByTypeofproductAndOptIliRoznSortByPriceAsc(String type, String optOtRozn, Sort price);
//
//    @Query("Select p From products p Where p.typeofproduct = ?1 and p.optIliRozn = ?2")
//    Iterable<products> findByTypeofproductAndOptIliRoznSortByPriceDesc(String type, String optOtRozn, Sort price);
}