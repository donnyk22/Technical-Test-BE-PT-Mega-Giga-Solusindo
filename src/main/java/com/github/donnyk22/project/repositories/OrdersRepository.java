package com.github.donnyk22.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.github.donnyk22.project.models.entities.Books;
import com.github.donnyk22.project.models.entities.Orders;
import com.github.donnyk22.project.models.entities.customs.ReportsRevenueStats;
import com.github.donnyk22.project.models.entities.customs.ReportsTopThreeSales;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Books> {

    @Query(value = "SELECT COALESCE(SUM(total_price), 0) AS revenue, " +
                "COALESCE(SUM(quantity), 0) AS sell_items FROM orders " +
                "LEFT JOIN order_items ON order_items.order_id = orders.id " +
                "WHERE STATUS <> 'CANCELED'",
    nativeQuery = true)
    ReportsRevenueStats getRevenueStats();

    @Query(value = "SELECT b.title, " +
                "SUM(oi.quantity) AS sell_items " +
                "FROM orders o " +
                "LEFT JOIN order_items oi ON oi.order_id = o.id " +
                "LEFT JOIN books b ON oi.book_id = b.id " +
                "WHERE STATUS <> 'CANCELED' " +
                "GROUP BY b.title " +
                "ORDER BY SUM(oi.quantity) DESC limit 3",
    nativeQuery = true)
    List<ReportsTopThreeSales> getTopThree();

}
