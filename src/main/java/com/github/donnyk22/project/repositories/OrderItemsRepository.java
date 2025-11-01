package com.github.donnyk22.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.github.donnyk22.project.models.entities.OrderItems;

@Repository
public interface OrderItemsRepository extends CrudRepository<OrderItems, Integer> {
}
