package com.github.donnyk22.project.models.forms;

import java.util.List;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderAddForm {
    private Integer userId;
    private String status;
    private List<OrderItemsAddForm> items;
}
