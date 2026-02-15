package com.github.donnyk22.project.models.forms;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class OrderAddForm {
    @Valid
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemsAddForm> items;
}
