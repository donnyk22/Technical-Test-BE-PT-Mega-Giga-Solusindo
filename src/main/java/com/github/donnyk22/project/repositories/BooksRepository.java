package com.github.donnyk22.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.entities.customs.ReportsPricesData;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer>, JpaSpecificationExecutor<Books> {

    @Query(value = "SELECT max(price) AS max_price, min(price) AS min_price, ROUND(AVG(price), 2) AS avg_price FROM books", nativeQuery = true)
    ReportsPricesData getPriceData();

}
