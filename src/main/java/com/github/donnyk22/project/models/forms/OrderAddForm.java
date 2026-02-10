package com.github.donnyk22.project.models.forms;

import java.util.List;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Setter
@Getter
@Accessors(chain = true)
public class OrderAddForm {
    @Valid
    private List<OrderItemsAddForm> items;
}
