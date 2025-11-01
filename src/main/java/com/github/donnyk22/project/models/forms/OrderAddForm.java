package com.github.donnyk22.project.models.forms;

import java.util.List;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class OrderAddForm {
    @Valid
    private List<OrderItemsAddForm> items;
}
