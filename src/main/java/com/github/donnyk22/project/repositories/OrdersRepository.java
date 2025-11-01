package com.github.donnyk22.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.entities.Orders;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Books> {
}
