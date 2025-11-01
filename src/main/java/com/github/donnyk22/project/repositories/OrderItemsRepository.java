package com.github.donnyk22.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.entities.OrderItems;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItems, Integer>, JpaSpecificationExecutor<Books> {
}
