package com.github.donnyk22.project.models.entities;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "order_items")
public class OrderItems {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    @Column(name = "order_id", insertable = false, updatable = false)
    private Integer orderId;
    @Column(name = "book_id", insertable = false, updatable = false)
    private Integer bookId;
    private Integer quantity;
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Orders order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Books book;

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
        this.order = new Orders().setId(orderId);
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
        this.book = new Books().setId(bookId);
    }
}
